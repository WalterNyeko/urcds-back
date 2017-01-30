package com.sweroad.service.count.impl;

import com.sweroad.model.*;
import com.sweroad.service.GenericManager;
import com.sweroad.service.count.CountAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Frank on 7/16/16.
 */
@Service("countCasualtyTypeService")
public class CountCasualtyTypeServiceImpl extends BaseCountService implements CountAttributeService {

    @Autowired
    private GenericManager<CasualtyType, Long> casualtyTypeService;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<CasualtyType> casualtyTypes = casualtyTypeService.getAllDistinct();
        casualtyTypes.forEach(casualtyType -> countResults.add(countOccurrences(casualtyType, crashes)));
        countResults.add(countOccurrences(NameIdModel.createNotSpecifiedInstance(), crashes));
        return countResults;
    }

    private CountResult countOccurrences(NameIdModel casualtyType, List<Crash> crashes) {
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        crashes.stream().forEach(crash ->
                this.incrementCounts(countResultBuilder, this.countCasualties(casualtyType, crash)));
        return countResultBuilder.setAttribute(casualtyType).build();
    }

    private Countable countCasualties(NameIdModel casualtyType, Crash crash) {
        List<Casualty> casualties = crash.getCasualties().stream().filter(casualty ->
                this.matchAttributes(casualtyType, casualty.getCasualtyType()))
                .collect(Collectors.toList());
        crash.getVehicles().forEach(vehicle -> this.appendDriver(casualties, casualtyType, vehicle));
        return getCountsFromCasualties(casualties);
    }

    private void appendDriver(List<Casualty> casualties, NameIdModel casualtyType, Vehicle vehicle) {
        Driver driver = vehicle.getDriver();
        if (casualtyType.equals(driver.getCasualtyType())) {
            casualties.add(driver.toCasualty(vehicle));
        }
    }
}
