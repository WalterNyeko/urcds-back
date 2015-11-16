package com.sweroad.audit;

import com.sweroad.model.*;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Frank on 11/16/15.
 */
public class PatientAuditTest {

    @Test
    public void testThatAgeChangeIsDetected() {
        Patient patient1 = new Patient(1L);
        Patient patient2 = new Patient(1L);
        patient1.setAge(25);
        patient2.setAge(26);
        assertTrue(PatientAudit.hasChanges(patient1, patient2));
    }

    @Test
    public void testThatGenderChangeIsDetected() {
        Patient patient1 = new Patient(1L);
        Patient patient2 = new Patient(1L);
        patient1.setGender("M");
        patient2.setGender("F");
        assertTrue(PatientAudit.hasChanges(patient1, patient2));
    }

    @Test
    public void testThatInpatientNumberChangeIsDetected() {
        Patient patient1 = new Patient(1L);
        Patient patient2 = new Patient(1L);
        patient1.setHospitalInpatientNo("123456");
        patient2.setHospitalInpatientNo("654321");
        assertTrue(PatientAudit.hasChanges(patient1, patient2));
    }

    @Test
    public void testThatOutpatientNumberChangeIsDetected() {
        Patient patient1 = new Patient(1L);
        Patient patient2 = new Patient(1L);
        patient1.setHospitalOutpatientNo("123456");
        patient2.setHospitalOutpatientNo("654321");
        assertTrue(PatientAudit.hasChanges(patient1, patient2));
    }

    @Test
    public void testThatInjuryDateChangeIsDetected() {
        Patient patient1 = new Patient(1L);
        Patient patient2 = new Patient(1L);
        patient1.setInjuryDateTimeString("2015-11-16 15:03");
        patient2.setInjuryDateTimeString("2015-11-17 15:03");
        assertTrue(PatientAudit.hasChanges(patient1, patient2));
    }

    @Test
    public void testThatVillageChangeIsDetected() {
        Patient patient1 = new Patient(1L);
        Patient patient2 = new Patient(1L);
        patient1.setVillage("village1");
        patient2.setVillage("village2");
        assertTrue(PatientAudit.hasChanges(patient1, patient2));
    }

    @Test
    public void testThatTransportModeChangeIsDetected() {
        Patient patient1 = new Patient(1L);
        Patient patient2 = new Patient(1L);
        patient1.setTransportMode(new TransportMode(1L));
        patient2.setTransportMode(new TransportMode(2L));
        assertTrue(PatientAudit.hasChanges(patient1, patient2));
    }

    @Test
    public void testThatCounterpartTransportModeChangeIsDetected() {
        Patient patient1 = new Patient(1L);
        Patient patient2 = new Patient(1L);
        patient1.setCounterpartTransportMode(new TransportMode(1L));
        patient2.setCounterpartTransportMode(new TransportMode(2L));
        assertTrue(PatientAudit.hasChanges(patient1, patient2));
    }

    @Test
    public void testDistrictChangeIsDetected() {
        Patient patient1 = new Patient(1L);
        Patient patient2 = new Patient(1L);
        patient1.setDistrict(new District(1L));
        patient2.setDistrict(new District(2L));
        assertTrue(PatientAudit.hasChanges(patient1, patient2));
    }

    @Test
    public void testHospitalChangeIsDetected() {
        Patient patient1 = new Patient(1L);
        Patient patient2 = new Patient(1L);
        patient1.setHospital(new Hospital(1L));
        patient2.setHospital(new Hospital(2L));
        assertTrue(PatientAudit.hasChanges(patient1, patient2));
    }

    @Test
    public void testPatientDispositionChangeIsDetected() {
        Patient patient1 = new Patient(1L);
        Patient patient2 = new Patient(1L);
        patient1.setPatientDisposition(new PatientDisposition(1L));
        patient2.setPatientDisposition(new PatientDisposition(2L));
        assertTrue(PatientAudit.hasChanges(patient1, patient2));
    }

    @Test
    public void testPatientStatusChangeIsDetected() {
        Patient patient1 = new Patient(1L);
        Patient patient2 = new Patient(1L);
        patient1.setPatientStatus(new PatientStatus(1L));
        patient2.setPatientStatus(new PatientStatus(2L));
        assertTrue(PatientAudit.hasChanges(patient1, patient2));
    }

    @Test
    public void testRoadUserTypeChangeIsDetected() {
        Patient patient1 = new Patient(1L);
        Patient patient2 = new Patient(1L);
        patient1.setRoadUserType(new RoadUserType(1L));
        patient2.setRoadUserType(new RoadUserType(2L));
        assertTrue(PatientAudit.hasChanges(patient1, patient2));
    }

    @Test
    public void testThatPatientInjuryNumberChangeIsDetected() {
        Patient patient1 = new Patient(1L);
        Patient patient2 = new Patient(1L);
        patient1.addPatientInjuryType(new PatientInjuryType());
        patient1.addPatientInjuryType(new PatientInjuryType());
        patient2.addPatientInjuryType(new PatientInjuryType());
        assertTrue(PatientAudit.hasChanges(patient1, patient2));
    }

    @Test
    public void testThatPatientInjuryDataChangeIsDetected() {
        Patient patient1 = new Patient(1L);
        Patient patient2 = new Patient(1L);
        patient1.addPatientInjuryType(new PatientInjuryType());
        patient2.addPatientInjuryType(new PatientInjuryType());
        patient1.getPatientInjuryTypes().get(0).setPatient(patient1);
        patient1.getPatientInjuryTypes().get(0).setPatient(patient2);
        patient1.getPatientInjuryTypes().get(0).setInjuryType(new InjuryType(1L));
        patient2.getPatientInjuryTypes().get(0).setInjuryType(new InjuryType(2L));
        assertTrue(PatientAudit.hasChanges(patient1, patient2));
    }

    @Test
    public void testThatPatientInjuryAisChangeIsDetected() {
        Patient patient1 = new Patient(1L);
        Patient patient2 = new Patient(1L);
        patient1.addPatientInjuryType(new PatientInjuryType());
        patient2.addPatientInjuryType(new PatientInjuryType());
        patient1.getPatientInjuryTypes().get(0).setPatient(patient1);
        patient1.getPatientInjuryTypes().get(0).setPatient(patient2);
        patient1.getPatientInjuryTypes().get(0).setInjuryType(new InjuryType(1L));
        patient2.getPatientInjuryTypes().get(0).setInjuryType(new InjuryType(1L));
        patient1.getPatientInjuryTypes().get(0).setAis(true);
        patient2.getPatientInjuryTypes().get(0).setAis(false);
        assertTrue(PatientAudit.hasChanges(patient1, patient2));
    }

    @Test
    public void testThatBeltHelmetChangeIsDetected() {
        Patient patient1 = new Patient(1L);
        Patient patient2 = new Patient(1L);
        patient1.setBeltUsedOption(Quadrian.NO);
        patient2.setBeltUsedOption(Quadrian.YES);
        assertTrue(PatientAudit.hasChanges(patient1, patient2));
    }

    @Test
    public void testThatFormCheckedByChangeIsDetected() {
        Patient patient1 = new Patient(1L);
        Patient patient2 = new Patient(1L);
        patient1.setFormCheckedBy("checker1");
        patient2.setFormCheckedBy("checker2");
        assertTrue(PatientAudit.hasChanges(patient1, patient2));
    }

    @Test
    public void testThatFormCheckedOnChangeIsDetected() {
        Patient patient1 = new Patient(1L);
        Patient patient2 = new Patient(1L);
        patient1.setFormCheckDate("2015-11-16");
        patient2.setFormCheckDate("2015-11-17");
        assertTrue(PatientAudit.hasChanges(patient1, patient2));
    }

    @Test
    public void testThatFormFilledByChangeIsDetected() {
        Patient patient1 = new Patient(1L);
        Patient patient2 = new Patient(1L);
        patient1.setFormFilledBy("Filler1");
        patient2.setFormFilledBy("Filler2");
        assertTrue(PatientAudit.hasChanges(patient1, patient2));
    }

    @Test
    public void testThatFormFilledOnChangeIsDetected() {
        Patient patient1 = new Patient(1L);
        Patient patient2 = new Patient(1L);
        patient1.setFormFillDate("2015-11-16");
        patient2.setFormFillDate("2015-11-17");
        assertTrue(PatientAudit.hasChanges(patient1, patient2));
    }

    @Test
    public void testThatDifferentCrashIsNotDetectedAsChange() {
        Patient patient1 = new Patient(1L);
        Patient patient2 = new Patient(2L);
        assertFalse(PatientAudit.hasChanges(patient1, patient2));
    }

    @Test
    public void testThatUnchangedCrashHasNoChanges() {
        Patient patient1 = new Patient(1L);
        Patient patient2 = new Patient(1L);
        assertFalse(PatientAudit.hasChanges(patient1, patient2));
    }
}
