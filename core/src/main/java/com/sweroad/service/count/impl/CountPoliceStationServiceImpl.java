package com.sweroad.service.count.impl;

import com.sweroad.model.*;
import com.sweroad.service.GenericService;
import com.sweroad.service.count.CountAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 5/31/16.
 */
@Service("countPoliceStationService")
public class CountPoliceStationServiceImpl extends BaseCountService implements CountAttributeService {

    @Autowired
    private GenericService<PoliceStation, Long> policeStationService;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<PoliceStation> policeStations = policeStationService.getAllDistinct();
        policeStations.forEach(policeStation -> countResults.add(countOccurrences(policeStation, crashes)));
        return countResults;
    }

    private CountResult countOccurrences(PoliceStation policeStation, List<Crash> crashes) {
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        crashes.stream().filter(crash -> policeStation.equals(crash.getPoliceStation()))
                .forEach(crash -> this.incrementCounts(countResultBuilder, crash));
        return countResultBuilder.setAttribute(policeStation).build();
    }
}