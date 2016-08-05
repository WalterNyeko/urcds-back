package com.sweroad.service.count.impl;

import com.sweroad.model.*;
import com.sweroad.service.LookupManager;
import com.sweroad.service.count.CountAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Frank on 7/16/16.
 */
@Service("countDriverGenderService")
public class CountDriverGenderServiceImpl extends BaseCountService implements CountAttributeService {

    @Autowired
    private LookupManager lookupManager;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<LabelValue> genders = lookupManager.getAllGenders();
        genders.forEach(gender -> countResults.add(countOccurrences(gender, crashes)));
        countResults.add(countOccurrences(NameIdModel.createNotSpecifiedInstance(), crashes));
        return countResults;
    }

    private CountResult countOccurrences(NameIdModel gender, List<Crash> crashes) {
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        crashes.stream().forEach(crash ->
                this.incrementCounts(countResultBuilder, this.countVehicles(gender, crash)));
        return countResultBuilder.setAttribute(gender).build();
    }

    private Countable countVehicles(NameIdModel gender, Crash crash) {
        List<Vehicle> vehicles = crash.getVehicles().stream().filter(vehicle ->
                this.matchGender(gender, vehicle.getDriver().getGender()))
                .collect(Collectors.toList());
        return getCounts(crash, vehicles);
    }
}
