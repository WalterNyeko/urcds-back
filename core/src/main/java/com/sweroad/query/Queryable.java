package com.sweroad.query;

/**
 * This interface defines what can be queryable
 * Created by Frank on 12/9/14.
 */
public interface Queryable {

    /**
     * Get id of Queryable
     *
     * @return
     */
    Long getId();

    /**
     * Gets name of entity
     *
     * @return
     */
    String getEntityName();

    /**
     * Gets name to be used within query from Crash POV.
     *
     * @return name for query
     */
    String getNameForQuery();
}
