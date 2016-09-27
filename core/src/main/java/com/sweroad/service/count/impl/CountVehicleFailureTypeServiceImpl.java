package com.sweroad.service.count.impl;

import com.sweroad.model.CountResult;
import com.sweroad.model.Crash;
import com.sweroad.model.NameIdModel;
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
public class CountVehicleFailureTypeServiceImpl extends BaseCountService implements CountAttributeService {

    @Autowired
    private GenericManager<VehicleFailureType, Long> vehicleFailureTypeManager;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<NameIdModel> vehicleFailureTypes = this.prepareAttributes(vehicleFailureTypeManager.getAllDistinct());
        vehicleFailureTypes.forEach(vehicleFailureType -> countResults.add(countOccurrences(vehicleFailureType, crashes)));
        return countResults;
    }

    private CountResult countOccurrences(NameIdModel vehicleFailureType, List<Crash> crashes) {
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        crashes.stream().filter(crash -> this.matchAttributes(vehicleFailureType, crash.getVehicleFailureType()))
                .forEach(crash -> this.incrementCounts(countResultBuilder, crash));
        return countResultBuilder.setAttribute(vehicleFailureType).build();
    }
}