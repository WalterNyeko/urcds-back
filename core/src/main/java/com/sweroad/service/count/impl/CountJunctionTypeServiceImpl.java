package com.sweroad.service.count.impl;

import com.sweroad.model.CountResult;
import com.sweroad.model.Crash;
import com.sweroad.model.JunctionType;
import com.sweroad.service.GenericManager;
import com.sweroad.service.count.CountAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 5/31/16.
 */
@Service("countJunctionTypeService")
public class CountJunctionTypeServiceImpl implements CountAttributeService {

    @Autowired
    private GenericManager<JunctionType, Long> junctionTypeManager;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<JunctionType> junctionTypes = junctionTypeManager.getAllDistinct();
        junctionTypes.forEach(junctionType -> countResults.add(countOccurrences(junctionType, crashes)));
        return countResults;
    }

    private CountResult countOccurrences(JunctionType junctionType, List<Crash> crashes) {
        long crashCount = 0, vehicleCount = 0, casualtyCount = 0;
        for (Crash crash : crashes) {
            if (crash.getJunctionType().equals(junctionType)) {
                crashCount++;
                vehicleCount += crash.getVehicleCount();
                casualtyCount += crash.getCasualtyCount();
            }
        }
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        return countResultBuilder.setAttribute(junctionType).setCrashCount(crashCount)
                .setVehicleCount(vehicleCount).setCasualtyCount(casualtyCount).build();
    }
}