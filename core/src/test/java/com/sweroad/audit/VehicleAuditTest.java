package com.sweroad.audit;

import com.sweroad.model.Driver;
import com.sweroad.model.Vehicle;
import com.sweroad.model.VehicleType;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Frank on 11/16/15.
 */
public class VehicleAuditTest {

    @Test
    public void testThatVehicleNumberChangeIsDetected() {
        Vehicle vehicle1 = new Vehicle(1L);
        Vehicle vehicle2 = new Vehicle(1L);
        vehicle1.setNumber(1);
        vehicle2.setNumber(2);
        assertTrue(VehicleAudit.hasChanges(vehicle1, vehicle2));
    }

    @Test
    public void testThatVehicleTypeChangeIsDetected() {
        Vehicle vehicle1 = new Vehicle(1L);
        Vehicle vehicle2 = new Vehicle(1L);
        vehicle1.setVehicleType(new VehicleType(1L));
        vehicle2.setVehicleType(new VehicleType(2L));
        assertTrue(VehicleAudit.hasChanges(vehicle1, vehicle2));
    }

    @Test
    public void testThatCompanyNameChangeIsDetected() {
        Vehicle vehicle1 = new Vehicle(1L);
        Vehicle vehicle2 = new Vehicle(1L);
        vehicle1.setCompanyName("Company1");
        vehicle2.setCompanyName("Company2");
        assertTrue(VehicleAudit.hasChanges(vehicle1, vehicle2));
    }

    @Test
    public void testThatDriverChangeIsDetected() {
        Vehicle vehicle1 = new Vehicle(1L);
        Vehicle vehicle2 = new Vehicle(1L);
        vehicle1.setDriver(new Driver(1L));
        vehicle2.setDriver(new Driver(2L));
        assertTrue(VehicleAudit.hasChanges(vehicle1, vehicle2));
    }

    @Test
    public void testThatDifferentVehicleIsNotDetectedAsChange() {
        Vehicle vehicle1 = new Vehicle(1L);
        Vehicle vehicle2 = new Vehicle(2L);
        assertFalse(VehicleAudit.hasChanges(vehicle1, vehicle2));
    }

    @Test
    public void testThatUnchangedVehicleHasNoChanges() {
        Vehicle vehicle1 = new Vehicle(1L);
        Vehicle vehicle2 = new Vehicle(1L);
        assertFalse(VehicleAudit.hasChanges(vehicle1, vehicle2));
    }
}
