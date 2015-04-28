package com.sweroad.query;

import com.google.common.base.CaseFormat;
import com.sweroad.model.BaseModel;
import com.sweroad.model.Crash;
import com.sweroad.model.DateRangable;

import java.util.*;

/**
 * Created by Frank on 12/9/14.
 */
public class CrashQuery extends BaseModel {

    private Long id;
    private Map<String, List<? extends Queryable>> queryables;
    private Map<String, Object> parameters;
    private Map<String, Map<String, Object>> customQueryables;
    private List<LiteralQueryable> literalQueryables;
    private boolean joinCasualties;
    private boolean joinVehicles;

    private CrashQuery(CrashQueryBuilder builder) {
        this.queryables = builder.queryables;
        this.parameters = builder.parameters;
        this.customQueryables = builder.customQueryables;
        this.literalQueryables = builder.literalQueryables;
        this.joinCasualties = builder.joinCasualties;
        this.joinVehicles = builder.joinVehicles;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, List<? extends Queryable>> getQueryables() {
        return queryables;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public Map<String, Map<String, Object>> getCustomQueryables() {
        return customQueryables;
    }

    private static String getParamName(String attribute) {
        attribute = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, attribute);
        return attribute.concat("List");
    }

    private static String getNameForQuery(List<? extends Queryable> list) {
        return list.get(0).getNameForQuery();
    }

    @Override
    public String toString() {
        return new HQLBuilder().generateHQL();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof CrashQuery)) {
            return false;
        }
        return ((CrashQuery) o).id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public static class CrashQueryBuilder {
        private final Map<String, List<? extends Queryable>> queryables;
        private final Map<String, Object> parameters;
        private Map<String, Map<String, Object>> customQueryables;
        private List<LiteralQueryable> literalQueryables;
        private boolean joinCasualties;
        private boolean joinVehicles;

        public enum CrashJoinType {CASUALTY, VEHICLE}

        public CrashQueryBuilder() {
            queryables = new TreeMap<String, List<? extends Queryable>>();
            parameters = new TreeMap<String, Object>();
            customQueryables = new TreeMap<String, Map<String, Object>>();
            literalQueryables = new ArrayList<LiteralQueryable>();
        }

        public CrashQueryBuilder addQueryable(List<? extends Queryable> queryableList) {
            if (queryableList != null && queryableList.size() > 0) {
                queryables.put(getParamName(queryableList.get(0).getEntityName()), queryableList);
            }
            return this;
        }

        public CrashQueryBuilder addStartHour(int startHour) {
            parameters.put("startHour", startHour);
            return this;
        }

        public CrashQueryBuilder addEndHour(int endHour) {
            parameters.put("endHour", endHour);
            return this;
        }

        public CrashQueryBuilder addStartMonth(int startMonth) {
            parameters.put("startMonth", startMonth);
            return this;
        }

        public CrashQueryBuilder addEndMonth(int endMonth) {
            parameters.put("endMonth", endMonth);
            return this;
        }

        public CrashQueryBuilder addStartDate(Date startDate) {
            if (startDate != null) {
                parameters.put("startDate", startDate);
            }
            return this;
        }

        public CrashQueryBuilder addEndDate(Date endDate) {
            if (endDate != null) {
                endDate = maximizeEndDate(endDate);
                parameters.put("endDate", endDate);
            }
            return this;
        }

        private Date maximizeEndDate(Date endDate) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(endDate);
            cal.add(Calendar.HOUR, 23);
            cal.add(Calendar.MINUTE, 59);
            cal.add(Calendar.SECOND, 59);
            return cal.getTime();
        }

        public CrashQueryBuilder addLiteralQueryable(LiteralQueryable literalQueryable) {
            if (literalQueryable.getJoinType().equals(CrashJoinType.CASUALTY)) {
                this.joinCasualties(true);
            } else if (literalQueryable.getJoinType().equals(CrashJoinType.VEHICLE)) {
                this.joinVehicles(true);
            }
            literalQueryables.add(literalQueryable);
            return this;
        }

        public CrashQueryBuilder addCustomQueryable(CustomQueryable customQueryable) {
            StringBuilder comparisonClause = new StringBuilder("");
            prefixQueryableProperty(customQueryable, comparisonClause);
            appendParamNameOrValue(customQueryable, comparisonClause);
            addToCustomQueryables(customQueryable, comparisonClause);
            return this;
        }

        private void addToCustomQueryables(CustomQueryable customQueryable, StringBuilder comparisonClause) {
            customQueryables.put(comparisonClause.toString(), new TreeMap<String, Object>());
            if (!customQueryable.shouldUseLiterals()) {
                customQueryables.get(comparisonClause.toString()).put(customQueryable.getParameterName(),
                        customQueryable.getParameterValue());
            }
        }

        private void appendNullValueInclusion(CustomQueryable customQueryable, StringBuilder comparisonClause) {
            if (customQueryable.shouldIncludeNulls()) {
                comparisonClause.append(" or ").append(customQueryable.getProperty()).append(" is NULL");
            }
        }

        private void appendParamNameOrValue(CustomQueryable customQueryable, StringBuilder comparisonClause) {
            String paramOrValue = customQueryable.shouldUseLiterals() ? customQueryable.getParameterValue().toString()
                    : ":".concat(getCustomQueryableParamName(customQueryable));
            if (customQueryable.shouldEncloseInParenthesis()) {
                comparisonClause.append("(");
                comparisonClause.append(paramOrValue);
                comparisonClause.append(")");
            } else {
                comparisonClause.append(paramOrValue);
            }
            appendNullValueInclusion(customQueryable, comparisonClause);
        }

        private String getCustomQueryableParamName(CustomQueryable customQueryable) {
            if (customQueryable.getParameterValue() instanceof List) {
                return getParamName(customQueryable.getParameterName());
            }
            return customQueryable.getParameterName();
        }

        private void prefixQueryableProperty(CustomQueryable customQueryable, StringBuilder comparisonClause) {
            if (customQueryable.getJoinType() == CrashJoinType.CASUALTY) {
                this.joinCasualties(true);
                customQueryable.setProperty(Crash.CASUALTY_ALIAS_DOT.concat(customQueryable.getProperty()));
            } else if (customQueryable.getJoinType() == CrashJoinType.VEHICLE) {
                this.joinVehicles(true);
                customQueryable.setProperty(Crash.VEHICLE_ALIAS_DOT.concat(customQueryable.getProperty()));
            }
            comparisonClause.append(customQueryable.getProperty()).append(customQueryable.getComparison().getSymbol());
        }

        public CrashQueryBuilder joinCasualties(boolean joinCasualties) {
            this.joinCasualties = joinCasualties;
            return this;
        }

        public CrashQueryBuilder joinVehicles(boolean joinVehicles) {
            this.joinVehicles = joinVehicles;
            return this;
        }

        public CrashQuery build() {
            return new CrashQuery(this);
        }
    }

    private class HQLBuilder {
        private final String hqlStart = "Select DISTINCT c from Crash c";
        private final String casualtyJoin = " join c.casualties i";
        private final String vehicleJoin = " join c.vehicles v";

        private String generateHQL() {
            StringBuilder query = new StringBuilder(hqlStart);
            appendQueryables(query);
            appendParameters(query);
            addCustomQueryables(query);
            addLiteralQueryables(query);
            excludeRemovedCrashes(query);
            return query.toString().trim();
        }

        private void appendJoins(StringBuilder query) {
            if (joinCasualties && !query.toString().contains(casualtyJoin)) {
                query.append(casualtyJoin);
            }
            if (joinVehicles && !query.toString().contains(vehicleJoin)) {
                query.append(vehicleJoin);
            }
        }

        private void appendQueryables(StringBuilder query) {
            String hql = query.toString().trim();
            if (queryables.size() > 0) {
                if (hql.equals(hqlStart)) {
                    appendJoins(query);
                    query.append(" where ");
                } else {
                    query.append(" and ");
                }
                for (String attribute : queryables.keySet()) {
                    query.append(getNameForQuery(queryables.get(attribute)))
                            .append(" in (:")
                            .append(attribute)
                            .append(") and ");
                }
                stripLastAnd(query);
            }
        }

        private void addCustomQueryables(StringBuilder query) {
            String hql = query.toString().trim();
            if (customQueryables.size() > 0) {
                if (hql.equals(hqlStart)) {
                    appendJoins(query);
                    query.append(" where ");
                } else {
                    query.append(" and ");
                }
                for (String key : customQueryables.keySet()) {
                    query.append(key).append(" and ");
                }
                stripLastAnd(query);
            }
        }

        private void addLiteralQueryables(StringBuilder query) {
            String hql = query.toString().trim();
            if (literalQueryables.size() > 0) {
                if (hql.equals(hqlStart)) {
                    appendJoins(query);
                    query.append(" where ");
                } else {
                    query.append(" and ");
                }
                for (LiteralQueryable literalQueryable : literalQueryables) {
                    query.append(literalQueryable.getFilterPart());
                    query.append(" and ");
                }
                stripLastAnd(query);
            }
        }

        private void excludeRemovedCrashes(StringBuilder query) {
            String hql = query.toString().trim();
            if (hql.equals(hqlStart)) {
                appendJoins(query);
                query.append(" where ");
            } else {
                query.append(" and ");
            }
            query.append("c.isRemoved is false");
        }

        private void appendParameters(StringBuilder query) {
            String hql = query.toString().trim();
            if (parameters.size() > 0) {
                if (hql.equals(hqlStart)) {
                    appendJoins(query);
                    query.append(" where ");
                } else {
                    query.append(" and ");
                }
                if (startDateDefined() || endDateDefined()) {
                    appendDateRanges(query);
                } else if (startMonthDefined() || endMonthDefined()) {
                    appendMonthRanges(query);
                }
                if (startHourDefined() || endHourDefined()) {
                    appendTimeRanges(query);
                }
                stripLastAnd(query);
            }
        }

        private void appendMonthRanges(StringBuilder query) {
            if (startMonthDefined() && endMonthDefined()) {
                appendMonthRange(query);
            } else if (startMonthDefined()) {
                appendStartMonth(query);
            } else if (endMonthDefined()) {
                appendEndMonth(query);
            }
        }

        private void appendDateRanges(StringBuilder query) {
            if (startDateDefined() && endDateDefined()) {
                appendDateRange(query);
            } else if (startDateDefined()) {
                appendStartDate(query);
            } else if (endDateDefined()) {
                appendEndDate(query);
            }
        }

        private void appendTimeRanges(StringBuilder query) {
            if (startHourDefined() && endHourDefined()) {
                appendHourRange(query);
            } else if (startHourDefined()) {
                appendStartHour(query);
            } else if (endHourDefined()) {
                appendEndHour(query);
            }
        }

        private void appendDateRange(StringBuilder query) {
            query.append("c.crashDateTime between :startDate and :endDate and ");
        }

        private void appendStartDate(StringBuilder query) {
            query.append("c.crashDateTime >= :startDate and ");
        }

        private void appendEndDate(StringBuilder query) {
            query.append("c.crashDateTime <= :endDate and ");
        }

        private void appendMonthRange(StringBuilder query) {
            query.append("(month(c.crashDateTime) between :startMonth and :endMonth) and ");
        }

        private void appendStartMonth(StringBuilder query) {
            query.append("month(c.crashDateTime) >= :startMonth and ");
        }

        private void appendEndMonth(StringBuilder query) {
            query.append("month(c.crashDateTime) <= :endMonth and ");
        }

        private void appendHourRange(StringBuilder query) {
            query.append("hour(c.crashDateTime) between :startHour and :endHour and ");
        }

        private void appendStartHour(StringBuilder query) {
            query.append("hour(c.crashDateTime) >= :startHour and ");
        }

        private void appendEndHour(StringBuilder query) {
            query.append("hour(c.crashDateTime) <= :endHour and ");
        }

        private void stripLastAnd(StringBuilder query) {
            query.delete(query.length() - 5, query.length());
        }

        private boolean startDateDefined() {
            return parameters.containsKey("startDate");
        }

        private boolean endDateDefined() {
            return parameters.containsKey("endDate");
        }

        private boolean startHourDefined() {
            return parameters.containsKey("startHour");
        }

        private boolean endHourDefined() {
            return parameters.containsKey("endHour");
        }

        private boolean startMonthDefined() {
            return parameters.containsKey("startMonth");
        }

        private boolean endMonthDefined() {
            return parameters.containsKey("endMonth");
        }
    }
}