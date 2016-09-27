package com.sweroad.query;

/**
 * Created by Frank on 3/17/15.
 */
public class LiteralQueryable {

    private CrashQuery.CrashQueryBuilder.CrashJoinType joinType;
    private String filterPart;

    public LiteralQueryable(CrashQuery.CrashQueryBuilder.CrashJoinType joinType, String filterPart) {
        this.joinType = joinType;
        this.filterPart = filterPart;
    }

    public CrashQuery.CrashQueryBuilder.CrashJoinType getJoinType() {
        return joinType;
    }

    public String getFilterPart() {
        return filterPart;
    }
}
