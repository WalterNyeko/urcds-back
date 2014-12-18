package com.sweroad.dao;

import com.sweroad.model.Crash;
import com.sweroad.query.CrashQuery;

import java.util.List;

public interface CrashDao extends GenericDao<Crash, Long> {

    /**
     * find Crash by tarNo
     * @param tarNo
     * @return
     */
    Crash findByTarNo(String tarNo);

    /**
     * Gets list of crashes that fit crash criteria
     * @param crashQuery
     * @return
     */
    List<Crash> findCrashesByQueryCrash(CrashQuery crashQuery);
}