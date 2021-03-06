package com.sweroad.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.sweroad.model.Crash;

public interface CrashService extends GenericService<Crash, Long> {

	/**
	 * This method is monitored by CrashSecurityAdvice and should only be called when getting
	 * crashes for display. Use getAll() otherwise.
	 * @return
	 */
	List<Crash> getCrashes(boolean latestOnly);

    /**
     * Gets crashes whose ids are in the passed list
     * @param ids
     * @return
     */
    List<Crash> getCrashes(List<Long> ids);

    /**
     * Gets only available crashes (non-removed).
     * @return
     */
    List<Crash> getAvailableCrashes(boolean latestOnly);

    /**
     * Gets only removed crashes (unavailable)
     * @return
     */
    List<Crash> getRemovedCrashes();

    /**
     * Gets Crash for view.
     * @param id
     * @return
     */
    Crash getCrashForView(Long id);

    /**
     * Saves crash and related data in transaction.
     * @param crash
     * @return
     */
	@Transactional
	Crash saveCrash(Crash crash);

    /**
     * Gets lists for use in crash related forms.
     * @return
     */
	@SuppressWarnings("rawtypes")
	Map<String, List> getReferenceData();

    /**
     * Gets ordered lists for use in crash related forms.
     * @return
     */
    Map<String, List> getOrderedRefData();

    /**
     * Removes casualty from crash by casualty id.
     * @param crash
     * @param casualtyId
     */
	void removeCasualtyFromCrash(Crash crash, Long casualtyId);

    /**
     * Removes vehicle from crash by vehicle id.
     * @param crash
     * @param vehicleId
     */
	void removeVehicleFromCrash(Crash crash, Long vehicleId);

    /**
     * Generates excel file containing passed list of crashes.
     * @param crashes
     * @param filename
     * @throws IOException
     */
	void generateCrashDataExcel(List<Crash> crashes, String filename) throws IOException;

    /**
     * Removes crash by id
     * @param id
     */
	void removeCrashById(Long id);

    /**
     * Restores removed crash by id
     * @param id
     */
	void restoreCrashById(Long id);

    List<Crash> findByTarNo(String tarNo);
}
