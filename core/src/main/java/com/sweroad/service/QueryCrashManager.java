package com.sweroad.service;

import com.sweroad.model.Crash;
import com.sweroad.query.CrashQuery;

import java.util.List;

/**
 * Created by Frank on 12/16/14.
 */
public interface QueryCrashManager {

    /**
     * Gets crashes that meet crashQuery criteria.
     *
     * @param crashQuery CrashQuery
     * @return List of Crashes
     */
    List<Crash> getCrashesByQuery(CrashQuery crashQuery);
}
