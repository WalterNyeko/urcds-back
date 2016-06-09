package com.sweroad.service.count.impl;

import com.sweroad.model.CountResult;
import com.sweroad.model.Crash;
import com.sweroad.model.SurfaceType;
import com.sweroad.service.GenericManager;
import com.sweroad.service.count.CountAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 5/31/16.
 */
@Service("countSurfaceTypeService")
public class CountSurfaceTypeServiceImpl implements CountAttributeService {

    @Autowired
    private GenericManager<SurfaceType, Long> surfaceTypeManager;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<SurfaceType> surfaceTypes = surfaceTypeManager.getAllDistinct();
        surfaceTypes.forEach(surfaceType -> countResults.add(countOccurrences(surfaceType, crashes)));
        return countResults;
    }

    private CountResult countOccurrences(SurfaceType surfaceType, List<Crash> crashes) {
        long crashCount = 0, vehicleCount = 0, casualtyCount = 0;
        for (Crash crash : crashes) {
            if (crash.getSurfaceType().equals(surfaceType)) {
                crashCount++;
                vehicleCount += crash.getVehicleCount();
                casualtyCount += crash.getCasualtyCount();
            }
        }
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        return countResultBuilder.setAttribute(surfaceType).setCrashCount(crashCount)
                .setVehicleCount(vehicleCount).setCasualtyCount(casualtyCount).build();
    }
}