package com.sweroad.query.service;

import com.sweroad.query.CrashQuery;

/**
 * Created by Frank on 3/9/15.
 */
public interface CustomQueryableService {

    void addToCrashQueryBuilder(CrashQuery.CrashQueryBuilder crashQueryBuilder);
}