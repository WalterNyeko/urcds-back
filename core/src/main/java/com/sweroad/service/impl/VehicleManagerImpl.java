package com.sweroad.service.impl;

import com.sweroad.dao.GenericDao;
import com.sweroad.model.Crash;
import com.sweroad.model.Vehicle;
import com.sweroad.service.VehicleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 2/19/15.
 */
@Service("vehicleManager")
public class VehicleManagerImpl extends GenericManagerImpl<Vehicle, Long> implements VehicleManager {

    @Autowired
    public VehicleManagerImpl(GenericDao<Vehicle, Long> vehicleDao) {
        super(vehicleDao);
    }

    @Override
    public List<Vehicle> extractVehiclesFromCrashList(List<Crash> crashes) {
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
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
