package com.sweroad.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.sweroad.model.Crash;

public interface CrashManager extends GenericManager<Crash, Long> {

	/**
	 * This method is monitored by CrashSecurityAdvice and should only be called when getting
	 * crashes for display. Use getAll() otherwise.
	 * @return
	 */
	List<Crash> getCrashes();
	
	Crash findByTarNo(String tarNo);
	
	@Transactional
	Crash saveCrash(Crash crash);
	
	@SuppressWarnings("rawtypes")
	Map<String, List> getReferenceData();
	
	void removeCasualtyFromCrash(Crash crash, Long casualtyId);

	void removeVehicleFromCrash(Crash crash, Long vehicleId);
	
	void removeCasualtiesFromCrash(Crash crash, List<Long> casualtyIds);
	
	void generateCrashDataExcel(String filename) throws IOException;
}
