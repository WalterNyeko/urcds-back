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
@Service("countDriverLicenseTypeService")
public class CountDriverLicenseTypeServiceImpl extends BaseCountService implements CountAttributeService {

    @Autowired
    private LookupManager lookupManager;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<QuadstateWrapper> licenseTypes = lookupManager.getAllQuadstateOptions(false);
        licenseTypes.forEach(licenseType -> countResults.add(countOccurrences(licenseType, crashes)));
        countResults.add(countOccurrences(NameIdModel.createNotSpecifiedInstance(), crashes));
        return countResults;
    }

    private CountResult countOccurrences(NameIdModel licenseType, List<Crash> crashes) {
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        crashes.stream().forEach(crash ->
                this.incrementCounts(countResultBuilder, this.countVehicles(licenseType, crash)));
        return countResultBuilder.setAttribute(licenseType).build();
    }

    private Countable countVehicles(NameIdModel licenseType, Crash crash) {
        List<Vehicle> vehicles = crash.getVehicles().stream().filter(vehicle ->
                this.matchQuadstateOptions(licenseType, vehicle.getDriver().getLicenseValid()))
                .collect(Collectors.toList());
        return getCountsFromVehicles(crash, vehicles);
    }
}
