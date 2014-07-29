package com.sweroad.service;

import com.sweroad.model.Crash;

public interface CrashManager extends GenericManager<Crash, Long> {

	Crash findByTarNo(String tarNo);
}
