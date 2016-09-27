package com.sweroad.query;

/**
 * Created by Frank on 3/5/15.
 */
public class CustomQueryable {

    private CrashQuery.CrashQueryBuilder.CrashJoinType joinType;
    private String property;
    private Comparison comparison;
    private String parameterName;
    private Object parameterValue;
    private boolean encloseInParenthesis;
    private boolean useLiterals;
    private boolean includeNulls;

    public CustomQueryable(CustomQueryableBuilder builder) {
        this.joinType = builder.joinType;
        this.property = builder.property;
        this.comparison = builder.comparison;
        this.parameterName = builder.parameterName;
        this.parameterValue = builder.parameterValue;
        this.encloseInParenthesis = builder.encloseInParenthesis;
        this.useLiterals = builder.useLiterals;
        this.includeNulls = builder.includeNulls;
    }

    public CrashQuery.CrashQueryBuilder.CrashJoinType getJoinType() {
        return joinType;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Comparison getComparison() {
        return comparison;
    }

    public String getParameterName() {
        return parameterName;
    }

    public Object getParameterValue() {
        return parameterValue;
    }

    public boolean shouldEncloseInParenthesis() {
        return encloseInParenthesis;
    }

    public boolean shouldUseLiterals() {
        return useLiterals;
    }

    public boolean shouldIncludeNulls() {
        return includeNulls;
    }

    public static class CustomQueryableBuilder {

        private CrashQuery.CrashQueryBuilder.CrashJoinType joinType;
        private String property;
        private Comparison comparison;
        private String parameterName;
        private Object parameterValue;
        private boolean encloseInParenthesis;
        private boolean useLiterals;
        private boolean includeNulls;

        public CustomQueryableBuilder addCrashJoinType(CrashQuery.CrashQueryBuilder.CrashJoinType joinType) {
            this.joinType = joinType;
            return this;
        }

        public CustomQueryableBuilder addProperty(String property) {
            this.property = property;
            return this;
        }

        public CustomQueryableBuilder addComparison( Comparison comparison) {
            this.comparison = comparison;
            return this;
        }

        public CustomQueryableBuilder addParameterName(String parameterName) {
            this.parameterName = parameterName;
            return this;
        }

        public CustomQueryableBuilder addParameterValue(Object parameterValue) {
            this.parameterValue = parameterValue;
            return this;
        }

        public CustomQueryableBuilder shouldEncloseInParenthesis(boolean encloseInParenthesis) {
            this.encloseInParenthesis = encloseInParenthesis;
            return this;
        }

        public CustomQueryableBuilder shouldUseLiterals(boolean useLiterals) {
            this.useLiterals = useLiterals;
            return this;
        }

        public CustomQueryableBuilder shouldIncludeNulls(boolean includeNulls) {
            this.includeNulls = includeNulls;
            return this;
        }

        public CustomQueryable build() {
            return new CustomQueryable(this);
        }
    }
}
