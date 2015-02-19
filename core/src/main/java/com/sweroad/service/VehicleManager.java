package com.sweroad.service;

import java.util.List;

import com.sweroad.model.Crash;
import com.sweroad.model.Vehicle;

public interface VehicleManager extends GenericManager<Vehicle, Long> {

    List<Vehicle> extractVehiclesFromCrashList(List<Crash> crashes);
}