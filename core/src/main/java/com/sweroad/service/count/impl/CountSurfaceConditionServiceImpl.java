package com.sweroad.service.count.impl;

import com.sweroad.model.CountResult;
import com.sweroad.model.Crash;
import com.sweroad.model.SurfaceCondition;
import com.sweroad.service.GenericManager;
import com.sweroad.service.count.CountAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 5/31/16.
 */
@Service("countSurfaceConditionService")
public class CountSurfaceConditionServiceImpl implements CountAttributeService {

    @Autowired
    private GenericManager<SurfaceCondition, Long> surfaceConditionManager;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<SurfaceCondition> surfaceConditions = surfaceConditionManager.getAllDistinct();
        surfaceConditions.forEach(surfaceCondition -> countResults.add(countOccurrences(surfaceCondition, crashes)));
        return countResults;
    }

    private CountResult countOccurrences(SurfaceCondition surfaceCondition, List<Crash> crashes) {
        long crashCount = 0, vehicleCount = 0, casualtyCount = 0;
        for (Crash crash : crashes) {
            if (crash.getSurfaceCondition().equals(surfaceCondition)) {
                crashCount++;
                vehicleCount += crash.getVehicleCount();
                casualtyCount += crash.getCasualtyCount();
            }
        }
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        return countResultBuilder.setAttribute(surfaceCondition).setCrashCount(crashCount)
                .setVehicleCount(vehicleCount).setCasualtyCount(casualtyCount).build();
    }
}