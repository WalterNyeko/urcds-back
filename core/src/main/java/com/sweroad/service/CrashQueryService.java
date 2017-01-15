package com.sweroad.service;

import com.sweroad.model.Crash;
import com.sweroad.model.Query;
import com.sweroad.query.CrashQuery;
import com.sweroad.query.CrashSearch;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by Frank on 12/16/14.
 */
public interface CrashQueryService {

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
    void processCrashSearch(CrashSearch crashSearch) throws ParseException;

    Map<String, List> getCrashQueryReferenceData();

    List<Query> getCurrentUserQueries();

    void saveQuery(Query query);

    Query getQueryById(Long queryId);

    void removeQueryById(Long queryId);
}
