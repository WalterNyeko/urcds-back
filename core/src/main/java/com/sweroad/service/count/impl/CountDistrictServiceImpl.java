package com.sweroad.service.count.impl;

import com.sweroad.model.CountResult;
import com.sweroad.model.Crash;
import com.sweroad.model.District;
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
@Service("countDistrictService")
public class CountDistrictServiceImpl implements CountAttributeService {

    @Autowired
    private GenericManager<District, Long> districtManager;
    @Autowired
    private CountAttributeService countPoliceStationService;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<CountResult> policeStationCounts = countPoliceStationService.countCrashes(crashes);
        List<District> districts = districtManager.getAllDistinct();
        districts.forEach(district -> countResults.add(countOccurrences(district, policeStationCounts)));
        return countResults;
    }

    private CountResult countOccurrences(District district, List<CountResult> policeStationCounts) {
        long crashCount = 0, vehicleCount = 0, casualtyCount = 0;
        for (CountResult result : policeStationCounts) {
            PoliceStation policeStation = (PoliceStation)result.getAttribute();
            if (policeStation != null && policeStation.getDistrict().equals(district)) {
                crashCount += result.getCrashCount();
                vehicleCount += result.getVehicleCount();
                casualtyCount += result.getCasualtyCount();
            }
        }
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        return countResultBuilder.setAttribute(district).setCrashCount(crashCount)
                .setVehicleCount(vehicleCount).setCasualtyCount(casualtyCount).build();
    }
}