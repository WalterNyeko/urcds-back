package com.sweroad.audit;

import com.sweroad.model.Casualty;
import com.sweroad.model.CasualtyClass;
import com.sweroad.model.CasualtyType;
import com.sweroad.model.Vehicle;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by Frank on 11/16/15.
 */
public class CasualtyAuditTest {

    @Test
    public void testThatAgeChangeIsDetected() {
        Casualty casualty1 = new Casualty(1L);
        Casualty casualty2 = new Casualty(1L);
        casualty1.setAge(15);
        casualty2.setAge(16);
        assertTrue(CasualtyAudit.hasChanges(casualty1, casualty2));
    }

    @Test
    public void testThatGenderChangeIsDetected() {
        Casualty casualty1 = new Casualty(1L);
        Casualty casualty2 = new Casualty(1L);
        casualty1.setGender("M");
        casualty2.setGender("F");
        assertTrue(CasualtyAudit.hasChanges(casualty1, casualty2));
    }

    @Test
    public void testThatBeltHelmetChangeIsDetected() {
        Casualty casualty1 = new Casualty(1L);
        Casualty casualty2 = new Casualty(1L);
        casualty1.setBeltOrHelmetUsed(true);
        casualty2.setBeltOrHelmetUsed(false);
        assertTrue(CasualtyAudit.hasChanges(casualty1, casualty2));
    }

    @Test
    public void testThatCasualtyClassChangeIsDetected() {
        Casualty casualty1 = new Casualty(1L);
        Casualty casualty2 = new Casualty(1L);
        casualty1.setCasualtyClass(new CasualtyClass(1L));
        casualty2.setCasualtyClass(new CasualtyClass(2L));
        assertTrue(CasualtyAudit.hasChanges(casualty1, casualty2));
    }

    @Test
    public void testThatCasualtyTypeChangeIsDetected() {
        Casualty casualty1 = new Casualty(1L);
        Casualty casualty2 = new Casualty(1L);
        casualty1.setCasualtyType(new CasualtyType(1L));
        casualty2.setCasualtyType(new CasualtyType(2L));
        assertTrue(CasualtyAudit.hasChanges(casualty1, casualty2));
    }

    @Test
    public void testThatVehicleChangeIsDetected() {
        Casualty casualty1 = new Casualty(1L);
        Casualty casualty2 = new Casualty(1L);
        casualty1.setVehicle(new Vehicle(1L));
        casualty2.setVehicle(new Vehicle(2L));
        assertTrue(CasualtyAudit.hasChanges(casualty1, casualty2));
    }

    @Test
    public void testThatDifferentCasualtyIsNotDetectedAsChange() {
        Casualty casualty1 = new Casualty(1L);
        Casualty casualty2 = new Casualty(2L);
        assertFalse(CasualtyAudit.hasChanges(casualty1, casualty2));
    }

    @Test
    public void testThatUnchangedCasualtyHasNoChanges() {
        Casualty casualty1 = new Casualty(1L);
        Casualty casualty2 = new Casualty(1L);
        assertFalse(CasualtyAudit.hasChanges(casualty1, casualty2));
    }
}
