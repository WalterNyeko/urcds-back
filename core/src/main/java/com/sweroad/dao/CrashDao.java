package com.sweroad.dao;

import com.sweroad.model.Crash;
import com.sweroad.query.QueryCrash;

import org.hibernate.Query;
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
     * @param queryCrash
     * @return
     */
    List<Crash> findCrashesByQueryCrash(QueryCrash queryCrash);
}