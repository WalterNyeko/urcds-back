package com.sweroad.query;

import com.sweroad.model.BaseModel;

import javax.management.Query;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Frank on 12/9/14.
 */
public class QueryCrash extends BaseModel {

    private QueryCrash(QueryCrashBuilder builder) {
        this.queryables = builder.queryables;
        this.parameters = builder.parameters;
        this.useMonth = builder.useMonth;
        this.useYear = builder.useYear;
    }

    private Long id;

    private Map<String, List<? extends Queryable>> queryables;

    private Map<String, Object> parameters;

    private boolean useMonth;

    private boolean useYear;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, List<? extends Queryable>> getQueryables() {
        return queryables;
    }

    public String generateHQL() {
        return new HQLBuilder().generateHQL();
    }

    private static String getParamName(String attribute) {
        String firstChar = attribute.substring(0, 1).toLowerCase();
        return firstChar.concat(attribute.substring(1)).concat("List");
    }

    private static String getEntityNameFromParamName (String paramName) {
        if(paramName.endsWith("List")) {
            paramName = paramName.replaceAll("List", "");
        }
        return paramName;
    }

    @Override
    public String toString() {
        return generateHQL();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof QueryCrash)) {
            return false;
        }
        return ((QueryCrash) o).id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public static class QueryCrashBuilder {
        private final Map<String, List<? extends Queryable>> queryables;
        private final Map<String, Object> parameters;
        private boolean useMonth;
        private boolean useYear;

        public QueryCrashBuilder() {
            queryables = new TreeMap<String, List<? extends Queryable>>();
            parameters = new TreeMap<String, Object>();
        }

        public QueryCrashBuilder addQueryable(List<? extends Queryable> queryableList) {
            if (queryableList.size() > 0) {
                queryables.put(getParamName(queryableList.get(0).getEntityName()), queryableList);
            }
            return this;
        }

        public QueryCrashBuilder addStartDate(Date startDate) {
            parameters.put("startDate", startDate);
            return this;
        }

        public QueryCrashBuilder addEndDate(Date endDate) {
            parameters.put("endDate", endDate);
            return this;
        }

        public QueryCrashBuilder setUseMonth(boolean useMonth) {
            this.useMonth = useMonth;
            return this;
        }

        public QueryCrashBuilder setUseYear(boolean useYear) {
            this.useYear = useYear;
            return this;
        }

        public QueryCrash build() {
            return new QueryCrash(this);
        }
    }

    private class HQLBuilder {
        private String hqlStart = "from Crash c";
        private String generateHQL() {
            StringBuilder query = new StringBuilder(hqlStart);
            appendQueryables(query);
            appendParameters(query);
            return query.toString().trim();
        }

        private void appendQueryables(StringBuilder query) {
            if (queryables.size() > 0) {
                query.append(" where ");
                for (String attribute : queryables.keySet()) {
                    query.append("c.")
                            .append(getEntityNameFromParamName(attribute))
                            .append(" in (:")
                            .append(attribute)
                            .append(") and ");
                }
                stripLastAnd(query);
            }
        }

        private void appendParameters(StringBuilder query) {
            String hql = query.toString().trim();
            if(parameters.size() > 0) {
                if(hql.equals(hqlStart)) {
                    query.append(" where ");
                } else  {
                    query.append(" and ");
                }
                if(parameters.containsKey("startDate")) {
                    appendStartDate(query);
                }
                if(parameters.containsKey("endDate")) {
                    appendEndDate(query);
                }
                stripLastAnd(query);
            }
        }

        private void appendStartDate(StringBuilder query) {
            if(!useMonth && !useYear) {
                query.append("c.crashDateTime >= :startDate and ");
                return;
            }
            if(useMonth) {
                query.append("((month(c.crashDateTime) >= month(:startDate) and ")
                        .append("year(c.crashDateTime) = year(:startDate)) or ")
                        .append("year(c.crashDateTime) > year(:startDate) and ");
                return;
            }
            if(useYear) {
                query.append("year(c.crashDateTime) >= year(:startDate) and ");
            }
        }

        private void appendEndDate(StringBuilder query) {
            if(!useMonth && !useYear) {
                query.append("c.crashDateTime >= :endDate and ");
                return;
            }
            if(useMonth) {
                query.append("((month(c.crashDateTime) <= month(:endDate) and ")
                        .append("year(c.crashDateTime) = year(:endDate)) or ")
                        .append("year(c.crashDateTime) < year(:endDate) and ");
                return;
            }
            if(useYear) {
                query.append("year(c.crashDateTime) <= year(:startDate) and ");
            }
        }

        private void stripLastAnd(StringBuilder query) {
            query.delete(query.length() - 5, query.length());
        }
    }
}