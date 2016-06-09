package com.sweroad.service.count.impl;

import com.sweroad.model.CountResult;
import com.sweroad.model.Crash;
import com.sweroad.model.VehicleFailureType;
import com.sweroad.service.GenericManager;
import com.sweroad.service.count.CountAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 5/31/16.
 */
@Service("countVehicleFailureTypeService")
public class CountVehicleFailureTypeServiceImpl implements CountAttributeService {

    @Autowired
    private GenericManager<VehicleFailureType, Long> vehicleFailureTypeManager;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<VehicleFailureType> vehicleFailureTypes = vehicleFailureTypeManager.getAllDistinct();
        vehicleFailureTypes.forEach(vehicleFailureType -> countResults.add(countOccurrences(vehicleFailureType, crashes)));
        return countResults;
    }

    private CountResult countOccurrences(VehicleFailureType vehicleFailureType, List<Crash> crashes) {
        long crashCount = 0, vehicleCount = 0, casualtyCount = 0;
        for (Crash crash : crashes) {
            if (crash.getVehicleFailureType().equals(vehicleFailureType)) {
                crashCount++;
                vehicleCount += crash.getVehicleCount();
                casualtyCount += crash.getCasualtyCount();
            }
        }
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        return countResultBuilder.setAttribute(vehicleFailureType).setCrashCount(crashCount)
                .setVehicleCount(vehicleCount).setCasualtyCount(casualtyCount).build();
    }
}