package com.sweroad.service.count.impl;

import com.sweroad.model.CountResult;
import com.sweroad.model.Crash;
import com.sweroad.model.RoadSurface;
import com.sweroad.service.GenericManager;
import com.sweroad.service.count.CountAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 5/31/16.
 */
@Service("countRoadSurfaceService")
public class CountRoadSurfaceServiceImpl implements CountAttributeService {

    @Autowired
    private GenericManager<RoadSurface, Long> roadSurfaceManager;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<RoadSurface> roadSurfaces = roadSurfaceManager.getAllDistinct();
        roadSurfaces.forEach(roadSurface -> countResults.add(countOccurrences(roadSurface, crashes)));
        return countResults;
    }

    private CountResult countOccurrences(RoadSurface roadSurface, List<Crash> crashes) {
        long crashCount = 0, vehicleCount = 0, casualtyCount = 0;
        for (Crash crash : crashes) {
            if (crash.getRoadSurface().equals(roadSurface)) {
                crashCount++;
                vehicleCount += crash.getVehicleCount();
                casualtyCount += crash.getCasualtyCount();
            }
        }
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        return countResultBuilder.setAttribute(roadSurface).setCrashCount(crashCount)
                .setVehicleCount(vehicleCount).setCasualtyCount(casualtyCount).build();
    }
}