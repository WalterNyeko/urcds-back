package com.sweroad.service.count.impl;

import com.sweroad.model.CountResult;
import com.sweroad.model.Crash;
import com.sweroad.model.CrashSeverity;
import com.sweroad.service.GenericManager;
import com.sweroad.service.count.CountAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 5/31/16.
 */
@Service("countCrashSeverityService")
public class CountCrashSeverityServiceImpl implements CountAttributeService {

    @Autowired
    private GenericManager<CrashSeverity, Long> crashSeverityManager;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<CrashSeverity> crashSeverities = crashSeverityManager.getAllDistinct();
        crashSeverities.forEach(crashSeverity -> countResults.add(countOccurrences(crashSeverity, crashes)));
        return countResults;
    }

    private CountResult countOccurrences(CrashSeverity crashSeverity, List<Crash> crashes) {
        long crashCount = 0, vehicleCount = 0, casualtyCount = 0;
        for (Crash crash : crashes) {
            if (crash.getCrashSeverity().equals(crashSeverity)) {
                crashCount++;
                vehicleCount += crash.getVehicleCount();
                casualtyCount += crash.getCasualtyCount();
            }
        }
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        return countResultBuilder.setAttribute(crashSeverity).setCrashCount(crashCount)
                .setVehicleCount(vehicleCount).setCasualtyCount(casualtyCount).build();
    }
}