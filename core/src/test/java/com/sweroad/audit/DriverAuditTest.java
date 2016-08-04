package com.sweroad.audit;

import com.sweroad.model.CasualtyType;
import com.sweroad.model.Driver;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by Frank on 11/16/15.
 */
public class DriverAuditTest {

    @Test
    public void testThatAgeChangeIsDetected() {
        Driver driver1 = new Driver(1L);
        Driver driver2 = new Driver(1L);
        driver1.setAge(23);
        driver2.setAge(25);
        assertTrue(DriverAudit.hasChanges(driver1, driver2));
    }

    @Test
    public void testThatBeltHelmetChangeIsDetected() {
        Driver driver1 = new Driver(1L);
        Driver driver2 = new Driver(1L);
        driver1.setBeltUsed(1);
        driver2.setBeltUsed(0);
        assertTrue(DriverAudit.hasChanges(driver1, driver2));
    }

    @Test
    public void testThatGenderChangeIsDetected() {
        Driver driver1 = new Driver(1L);
        Driver driver2 = new Driver(1L);
        driver1.setGender("F");
        driver2.setGender("M");
        assertTrue(DriverAudit.hasChanges(driver1, driver2));
    }

    @Test
    public void testThatLicenseNumberChangeIsDetected() {
        Driver driver1 = new Driver(1L);
        Driver driver2 = new Driver(1L);
        driver1.setLicenseNumber("12345678");
        driver2.setLicenseNumber("87654321");
        assertTrue(DriverAudit.hasChanges(driver1, driver2));
    }

    @Test
    public void testThatLicenseValidChangeIsDetected() {
        Driver driver1 = new Driver(1L);
        Driver driver2 = new Driver(1L);
        driver1.setLicenseValid(1);
        driver2.setLicenseValid(0);
        assertTrue(DriverAudit.hasChanges(driver1, driver2));
    }

    @Test
    public void testThatCasualtyTypeChangeIsDetected() {
        Driver driver1 = new Driver(1L);
        Driver driver2 = new Driver(1L);
        driver1.setCasualtyType(null);
        driver2.setCasualtyType(new CasualtyType(1L));
        assertTrue(DriverAudit.hasChanges(driver1, driver2));
    }

    @Test
    public void testThatDifferentDriverIsNotDetectedAsChange() {
        Driver driver1 = new Driver(1L);
        Driver driver2 = new Driver(2L);
        assertFalse(DriverAudit.hasChanges(driver1, driver2));
    }

    @Test
    public void testThatUnchangedDriverHasNoChanges() {
        Driver driver1 = new Driver(1L);
        Driver driver2 = new Driver(1L);
        assertFalse(DriverAudit.hasChanges(driver1, driver2));
    }
}
