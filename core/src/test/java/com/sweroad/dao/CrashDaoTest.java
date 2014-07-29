package com.sweroad.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sweroad.model.Crash;

public class CrashDaoTest extends BaseDaoTestCase {
	
	@Autowired
	private CrashDao crashDao;
	
	@Test
	public void testThatFindCrashByTaNoWorks() {
		Crash crash = crashDao.findByTarNo("A1509/LGZ");
		assertNotNull(crash);
	}
}
