package com.sweroad.service;

import com.sweroad.model.Crash;
import com.sweroad.query.CrashQuery;
import com.sweroad.query.CrashSearch;

import java.util.List;

/**
 * Created by Frank on 12/16/14.
 */
public interface CrashQueryManager {

    /**
     * Gets crashes that meet crashQuery criteria.
     *
     * @param crashQuery CrashQuery
     * @return List of Crashes
     */
    List<Crash> getCrashesByQuery(CrashQuery crashQuery);

    /**
     * Processes CrashSearch object before CrashQuery is generated
     *
     * @param crashSearch
     */
    void processCrashSearch(CrashSearch crashSearch);
}
