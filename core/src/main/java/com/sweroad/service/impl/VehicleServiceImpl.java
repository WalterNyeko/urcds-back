package com.sweroad.service.impl;

import com.sweroad.dao.GenericDao;
import com.sweroad.model.Crash;
import com.sweroad.model.Vehicle;
import com.sweroad.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 2/19/15.
 */
@Service("vehicleService")
public class VehicleServiceImpl extends GenericManagerImpl<Vehicle, Long> implements VehicleService {

    @Autowired
    public VehicleServiceImpl(GenericDao<Vehicle, Long> vehicleDao) {
        super(vehicleDao);
    }

    @Override
    public List<Vehicle> extractVehiclesFromCrashList(List<Crash> crashes) {
        List<Vehicle> vehicles = new ArrayList<>();
        for(Crash crash : crashes) {
            addCrashVehiclesToVehicleList(crash, vehicles);
        }
        return vehicles;
    }

    private void addCrashVehiclesToVehicleList(Crash crash, List<Vehicle> vehicles) {
        for(Vehicle vehicle : crash.getVehicles()) {
            vehicle.setCrash(crash);
            vehicles.add(vehicle);
        }
    }
}
