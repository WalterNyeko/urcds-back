package com.sweroad.service;

import com.sweroad.model.Crash;
import com.sweroad.query.QueryCrash;

import java.util.List;

/**
 * Created by Frank on 12/16/14.
 */
public interface QueryCrashManager {

    /**
     * Gets crashes that meet queryCrash criteria.
     *
     * @param queryCrash QueryCrash
     * @return List of Crashes
     */
    List<Crash> getCrashesByQuery(QueryCrash queryCrash);
}
