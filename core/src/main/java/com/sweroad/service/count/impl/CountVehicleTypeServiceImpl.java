package com.sweroad.service.count.impl;

import com.sweroad.Constants;
import com.sweroad.model.*;
import com.sweroad.service.GenericManager;
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
    private GenericManager<VehicleType, Long> vehicleTypeManager;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<VehicleType> vehicleTypes = vehicleTypeManager.getAllDistinct();
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
                this.matchAttributes(vehicleType, vehicle.getVehicleType())).collect(Collectors.toList());
        final int vehicleCount = vehicles.size();
        final long crashCount = vehicleCount > 0 ? 1 : 0;
        final long casualtyCount = this.countCasualties(crash, vehicles);
        return new Countable() {
            @Override
            public long getCrashCount() {
                return crashCount;
            }

            @Override
            public long getVehicleCount() {
                return vehicleCount;
            }

            @Override
            public long getCasualtyCount() {
                return casualtyCount;
            }
        };
    }

    private long countCasualties(Crash crash, List<Vehicle> vehicles) {
        List<Long> vehicleIds = vehicles.stream().map(Vehicle::getId).collect(Collectors.toList());
        long casualtyCount = crash.getCasualties().stream().filter(casualty ->
                casualty.getVehicle() != null && vehicleIds.contains(casualty.getVehicle().getId())).count();
        casualtyCount += vehicles.stream().map(Vehicle::getDriver).filter(driver->
                driver.getCasualtyType() != null && !driver.getCasualtyType().getId().equals(Constants.NOT_INJURED_ID)).count();
        return casualtyCount;
    }
}