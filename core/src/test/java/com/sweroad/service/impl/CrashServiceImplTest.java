package com.sweroad.service.impl;

import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.sweroad.dao.CrashDao;
import com.sweroad.model.Crash;

public class CrashServiceImplTest extends BaseManagerMockTestCase {

	@InjectMocks
	private CrashServiceImpl crashService;
	
	@Mock
	private CrashDao crashDao;
	
	@Test
	public void testThatGetCrashWorks() {
		log.debug("testing get...");
		//given
		final Long id = 7L;
		final Crash crash = new Crash();
		given(crashDao.get(id)).willReturn(crash);
		//when
		Crash result = crashService.get(id);
		//then
		assertSame(crash, result);
	}
	
	@Test
	public void testThatGetCrashesWorks() {
		log.debug("testing get all...");
		//given
		final List<Crash> crashes = new ArrayList<Crash>();
		given(crashDao.getAll()).willReturn(crashes);
		//when
		List<Crash> result = crashService.getAll();
		//then
		assertSame(crashes, result);
	}
}
