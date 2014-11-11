package com.sweroad.dao;

import com.sweroad.model.Crash;

public interface CrashDao extends GenericDao<Crash, Long> {

    Crash findByTarNo(String tarNo);
}