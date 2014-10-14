package com.sweroad.audit;

import java.io.Serializable;

/**
 * Created by Frank on 10/13/14.
 */
public interface IAuditable extends Serializable {
    public static final String OPERATION_INSERT = "Insert";
    public static final String OPERATION_UPDATE = "Update";
    public static final String OPERATION_DELETE = "Delete";
    public static final String OPERATION_REMOVE = "Remove";
    public static final String OPERATION_RESTORE = "Restore";
    Long getId();
    boolean isRemoved();
}
