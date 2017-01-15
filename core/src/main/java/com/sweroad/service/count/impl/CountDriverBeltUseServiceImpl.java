package com.sweroad.service.count.impl;

import com.sweroad.model.*;
import com.sweroad.service.LookupService;
import com.sweroad.service.count.CountAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Frank on 7/16/16.
 */
@Service("countDriverBeltUseService")
public class CountDriverBeltUseServiceImpl extends BaseCountService implements CountAttributeService {

    @Autowired
    private LookupService lookupService;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<QuadstateWrapper> beltUseOptions = lookupService.getAllQuadstateOptions(false);
        beltUseOptions.forEach(option -> countResults.add(countOccurrences(option, crashes)));
        countResults.add(countOccurrences(NameIdModel.createNotSpecifiedInstance(), crashes));
        return countResults;
    }

    private CountResult countOccurrences(NameIdModel beltUseOption, List<Crash> crashes) {
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        crashes.stream().forEach(crash ->
                this.incrementCounts(countResultBuilder, this.countVehicles(beltUseOption, crash)));
        return countResultBuilder.setAttribute(beltUseOption).build();
    }

    private Countable countVehicles(NameIdModel beltUseOption, Crash crash) {
        List<Vehicle> vehicles = crash.getVehicles().stream().filter(vehicle ->
                this.matchQuadstateOptions(beltUseOption, vehicle.getDriver().getBeltUsed()))
                .collect(Collectors.toList());
        return getCountsFromVehicles(crash, vehicles);
    }
}
