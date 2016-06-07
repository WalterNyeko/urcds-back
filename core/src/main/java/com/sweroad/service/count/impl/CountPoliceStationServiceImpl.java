package com.sweroad.service.count.impl;

import com.sweroad.model.CountResult;
import com.sweroad.model.Crash;
import com.sweroad.model.PoliceStation;
import com.sweroad.service.GenericManager;
import com.sweroad.service.count.CountAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 5/31/16.
 */
@Service("countPoliceStationService")
public class CountPoliceStationServiceImpl implements CountAttributeService {

    @Autowired
    private GenericManager<PoliceStation, Long> policeStationManager;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<PoliceStation> policeStations = policeStationManager.getAllDistinct();
        policeStations.forEach(policeStation -> countResults.add(countOccurrences(policeStation, crashes)));
        return countResults;
    }

    private CountResult countOccurrences(PoliceStation policeStation, List<Crash> crashes) {
        long crashCount = 0, vehicleCount = 0, casualtyCount = 0;
        for (Crash crash : crashes) {
            if (crash.getPoliceStation().equals(policeStation)) {
                crashCount++;
                vehicleCount += crash.getVehicleCount();
                casualtyCount += crash.getCasualtyCount();
            }
        }
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        return countResultBuilder.setAttribute(policeStation).setCrashCount(crashCount)
                .setVehicleCount(vehicleCount).setCasualtyCount(casualtyCount).build();
    }
}