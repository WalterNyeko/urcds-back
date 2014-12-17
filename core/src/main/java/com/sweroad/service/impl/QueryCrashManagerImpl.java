package com.sweroad.service.impl;

import com.sweroad.dao.CrashDao;
import com.sweroad.model.Crash;
import com.sweroad.query.QueryCrash;
import com.sweroad.service.QueryCrashManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Frank on 12/16/14.
 */
@Service("queryCrashManager")
public class QueryCrashManagerImpl implements QueryCrashManager {
    @Autowired
    private CrashDao crashDao;

    @Override
    public List<Crash> getCrashesByQuery(QueryCrash queryCrash) {
        return crashDao.findCrashesByQueryCrash(queryCrash);
    }
}
