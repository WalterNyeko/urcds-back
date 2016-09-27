package com.sweroad.service;

import com.sweroad.model.Crash;
import com.sweroad.model.Vehicle;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * Created by Frank on 2/19/15.
 */
public class VehicleManagerTest extends BaseManagerTestCase {
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private CrashService crashService;

    @Test
    public void testExtractVehiclesFromCrashList() {
        List<Crash> crashes = crashService.getAllDistinct();
        assertEquals(25, vehicleService.extractVehiclesFromCrashList(crashes).size());
    }

    @Test
    public void testThatAllVehiclesExtractedHaveNonNullCrash() {
        List<Crash> crashes = crashService.getAllDistinct();
        List<Vehicle> vehicles = vehicleService.extractVehiclesFromCrashList(crashes);
        boolean nullCrashExists = false;
        for(Vehicle vehicle : vehicles) {
            if(vehicle.getCrash() == null) {
                nullCrashExists = true;
                break;
            }
        }
        assertFalse(nullCrashExists);
    }
}
