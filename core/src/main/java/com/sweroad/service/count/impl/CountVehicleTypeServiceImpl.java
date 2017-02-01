package com.sweroad.service.count.impl;

import com.sweroad.model.*;
import com.sweroad.service.GenericService;
import com.sweroad.service.count.CountAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Frank on 5/31/16.
 */
@Service("countVehicleTypeService")
public class CountVehicleTypeServiceImpl extends BaseCountService implements CountAttributeService {

    @Autowired
    private GenericService<VehicleType, Long> vehicleTypeService;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<VehicleType> vehicleTypes = vehicleTypeService.getAllDistinct();
        vehicleTypes.forEach(vehicleType -> countResults.add(countOccurrences(vehicleType, crashes)));
        countResults.add(countOccurrences(NameIdModel.createNotSpecifiedInstance(), crashes));
        return countResults;
    }

    private CountResult countOccurrences(NameIdModel vehicleType, List<Crash> crashes) {
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        crashes.stream().forEach(crash ->
                this.incrementCounts(countResultBuilder, this.countVehicles(vehicleType, crash)));
        return countResultBuilder.setAttribute(vehicleType).build();
    }

    private Countable countVehicles(NameIdModel vehicleType, Crash crash) {
        List<Vehicle> vehicles = crash.getVehicles().stream().filter(vehicle ->
                this.matchAttributes(vehicleType, vehicle.getVehicleType()))
                .collect(Collectors.toList());
        return getCountsFromVehicles(crash, vehicles);
    }
}