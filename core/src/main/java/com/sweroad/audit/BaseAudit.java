package com.sweroad.audit;

/**
 * Created by Frank on 11/16/15.
 */
public class BaseAudit {

    protected static final boolean isDifferent(Object value1, Object value2) {
        if (value1 == null && value2 != null) {
            return true;
        }
        if (value1 != null && !value1.equals(value2)) {
            return true;
        }
        return false;
    }
}
