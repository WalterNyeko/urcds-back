package com.sweroad.query;

import com.google.common.base.CaseFormat;
import com.sweroad.model.BaseModel;
import com.sweroad.model.Crash;

import java.util.*;

/**
 * Created by Frank on 12/9/14.
 */
public class CrashQuery extends BaseModel {

    private CrashQuery(CrashQueryBuilder builder) {
        this.queryables = builder.queryables;
        this.parameters = builder.parameters;
        this.customQueryables = builder.customQueryables;
        this.useMonth = builder.useMonth;
        this.useYear = builder.useYear;
        this.joinCasualties = builder.joinCasualties;
        this.joinVehicles = builder.joinVehicles;
    }

    private Long id;

    private Map<String, List<? extends Queryable>> queryables;

    private Map<String, Object> parameters;

    private Map<String, Map<String, Object>> customQueryables;

    private boolean useTime;

    private boolean useMonth;

    private boolean useYear;

    private boolean joinCasualties;

    private boolean joinVehicles;

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
        private boolean useMonth;
        private boolean useYear;
        private boolean joinCasualties;
        private boolean joinVehicles;

        public enum CrashJoinType {CASUALTY, VEHICLE}

        public CrashQueryBuilder() {
            queryables = new TreeMap<String, List<? extends Queryable>>();
            parameters = new TreeMap<String, Object>();
            customQueryables = new TreeMap<String, Map<String, Object>>();
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

        public CrashQueryBuilder addStartDate(Date startDate) {
            parameters.put("startDate", startDate);
            return this;
        }

        public CrashQueryBuilder addEndDate(Date endDate) {
            endDate = maximizeEndDate(endDate);
            parameters.put("endDate", endDate);
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

        public CrashQueryBuilder addCustomQueryable(CrashJoinType joinType, String property, Comparison comparison,
                                                    String paramName, Object paramValue, boolean encloseInParenthesis) {
            StringBuilder comparisonClause = new StringBuilder("");
            if (joinType == CrashJoinType.CASUALTY) {
                this.joinCasualties(true);
                property = Crash.CASUALTY_ALIAS_DOT.concat(property);
            } else if (joinType == CrashJoinType.VEHICLE) {
                this.joinVehicles(true);
                property = Crash.VEHICLE_ALIAS_DOT.concat(property);
            }

            comparisonClause.append(property).append(comparison.getSymbol());
            if (encloseInParenthesis) {
                comparisonClause.append("(");
            }
            comparisonClause.append(":").append(paramName);
            if (encloseInParenthesis) {
                comparisonClause.append(")");
            }
            customQueryables.put(comparisonClause.toString(), new TreeMap<String, Object>());
            customQueryables.get(comparisonClause.toString()).put(paramName, paramValue);
            return this;
        }

        public CrashQueryBuilder setUseMonth(boolean useMonth) {
            this.useMonth = useMonth;
            return this;
        }

        public CrashQueryBuilder setUseYear(boolean useYear) {
            this.useYear = useYear;
            return this;
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
        private final String hqlStart = "Select c from Crash c";
        private final String casualtyJoin = " join c.casualties i";
        private final String vehicleJoin = " join c.vehicles v";

        private String generateHQL() {
            StringBuilder query = new StringBuilder(hqlStart);
            appendQueryables(query);
            appendParameters(query);
            addCustomQueryables(query);
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
            if (queryables.size() > 0) {
                appendJoins(query);
                query.append(" where ");
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

        private void appendParameters(StringBuilder query) {
            String hql = query.toString().trim();
            if (parameters.size() > 0) {
                if (hql.equals(hqlStart)) {
                    appendJoins(query);
                    query.append(" where ");
                } else {
                    query.append(" and ");
                }
                if (parameters.containsKey("startDate") && parameters.containsKey("endDate")) {
                    appendDateRange(query);
                } else if (parameters.containsKey("startDate")) {
                    appendStartDate(query);
                } else if (parameters.containsKey("endDate")) {
                    appendEndDate(query);
                }
                if (parameters.containsKey("startHour") && parameters.containsKey("endHour")) {
                    appendHourRange(query);
                } else if (parameters.containsKey("startHour")) {
                    appendStartHour(query);
                } else if (parameters.containsKey("endHour")) {
                    appendEndHour(query);
                }
                stripLastAnd(query);
            }
        }

        private void appendDateRange(StringBuilder query) {
            if (!useMonth && !useYear) {
                query.append("c.crashDateTime between :startDate and :endDate and ");
                return;
            } else if (useYear) {
                query.append("year(c.crashDateTime) between year(:startDate) and year(:endDate) and ");
                return;
            } else if (useMonth) {
                query.append("(");
                appendStartDate(query);
                stripLastAnd(query);
                query.append(") and (");
                appendEndDate(query);
                stripLastAnd(query);
                query.append(") and ");
            }
        }

        private void appendStartDate(StringBuilder query) {
            if (!useMonth && !useYear) {
                query.append("c.crashDateTime >= :startDate and ");
                return;
            }
            if (useMonth) {
                query.append("((month(c.crashDateTime) >= month(:startDate) and ")
                        .append("year(c.crashDateTime) = year(:startDate)) or ")
                        .append("year(c.crashDateTime) > year(:startDate)) and ");
                return;
            }
            if (useYear) {
                query.append("year(c.crashDateTime) >= year(:startDate) and ");
            }
        }

        private void appendEndDate(StringBuilder query) {
            if (!useMonth && !useYear) {
                query.append("c.crashDateTime <= :endDate and ");
                return;
            }
            if (useMonth) {
                query.append("((month(c.crashDateTime) <= month(:endDate) and ")
                        .append("year(c.crashDateTime) = year(:endDate)) or ")
                        .append("year(c.crashDateTime) < year(:endDate)) and ");
                return;
            }
            if (useYear) {
                query.append("year(c.crashDateTime) <= year(:endDate) and ");
            }
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
    }
}