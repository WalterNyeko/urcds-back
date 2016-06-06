package com.sweroad.service.count.impl;

import com.sweroad.model.CountResult;
import com.sweroad.model.Crash;
import com.sweroad.model.CrashCause;
import com.sweroad.service.GenericManager;
import com.sweroad.service.count.CountAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 5/31/16.
 */
@Service("countCrashCauseService")
public class CountCrashCauseServiceImpl implements CountAttributeService {

    @Autowired
    private GenericManager<CrashCause, Long> crashCauseManager;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<CrashCause> crashCauses = crashCauseManager.getAllDistinct();
        crashCauses.forEach(crashCause -> countResults.add(countOccurrences(crashCause, crashes)));
        return countResults;
    }

    private CountResult countOccurrences(CrashCause crashCause, List<Crash> crashes) {
        long crashCount = 0, vehicleCount = 0, casualtyCount = 0;
        for (Crash crash : crashes) {
            if (crash.getCrashCause().equals(crashCause)) {
                crashCount++;
                vehicleCount += crash.getVehicleCount();
                casualtyCount += crash.getCasualtyCount();
            }
        }
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        return countResultBuilder.setAttribute(crashCause).setCrashCount(crashCount)
                .setVehicleCount(vehicleCount).setCasualtyCount(casualtyCount).build();
    }
}