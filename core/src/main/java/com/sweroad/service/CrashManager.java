package com.sweroad.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.sweroad.model.Crash;

public interface CrashManager extends GenericManager<Crash, Long> {

	Crash findByTarNo(String tarNo);
	
	@Transactional
	Crash saveCrash(Crash crash);
	
	@SuppressWarnings("rawtypes")
	Map<String, List> getReferenceData();
}
