package com.sweroad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sweroad.dao.CrashDao;
import com.sweroad.model.Crash;
import com.sweroad.service.CrashManager;

@Service("crashManager")
public class CrashManagerImpl extends GenericManagerImpl<Crash, Long> implements CrashManager {

	private CrashDao crashDao;
	
	@Autowired
	public CrashManagerImpl(CrashDao crashDao) {
		super(crashDao);
		this.crashDao = crashDao;
	}
	@Override
	public Crash findByTarNo(String tarNo) {
		return crashDao.findByTarNo(tarNo);
	}

}
