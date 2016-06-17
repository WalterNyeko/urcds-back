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
public class CountDistrictServiceImpl extends BaseCountService implements CountAttributeService {

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
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        policeStationCounts.stream().filter(result -> this.belongsToDistrict(result, district))
                .forEach(result -> this.incrementCounts(countResultBuilder, result));
        return countResultBuilder.setAttribute(district).build();
    }

    private boolean belongsToDistrict(CountResult policeStationCount, District district) {
        PoliceStation policeStation = (PoliceStation)policeStationCount.getAttribute();
        return policeStation != null && policeStation.getDistrict().equals(district);
    }
}