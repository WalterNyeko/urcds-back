package com.sweroad.audit;

import com.sweroad.model.Patient;
import com.sweroad.model.PatientInjuryType;

/**
 * Created by Frank on 11/16/15.
 */
public class PatientAudit extends BaseAudit {

    public static final boolean hasChanges(Patient dbPatient, Patient patient) {
        if (dbPatient != null && dbPatient.equals(patient)) {
            if (isDifferent(dbPatient.getAge(), patient.getAge())) {
                return true;
            }
            if (isDifferent(dbPatient.getGender(), patient.getGender())) {
                return true;
            }
            if (isDifferent(dbPatient.getVillage(), patient.getVillage())) {
                return true;
            }
            if (isDifferent(dbPatient.getHospital(), patient.getHospital())) {
                return true;
            }
            if (isDifferent(dbPatient.getBeltUsed(), patient.getBeltUsed())) {
                return true;
            }
            if (isDifferent(dbPatient.getDistrict(), patient.getDistrict())) {
                return true;
            }
            if (isDifferent(dbPatient.getFormFillDate(), patient.getFormFillDate())) {
                return true;
            }
            if (isDifferent(dbPatient.getFormFilledBy(), patient.getFormFilledBy())) {
                return true;
            }
            if (isDifferent(dbPatient.getRoadUserType(), patient.getRoadUserType())) {
                return true;
            }
            if (isDifferent(dbPatient.getPatientStatus(), patient.getPatientStatus())) {
                return true;
            }
            if (isDifferent(dbPatient.getTransportMode(), patient.getTransportMode())) {
                return true;
            }
            if (isDifferent(dbPatient.getFormCheckDate(), patient.getFormCheckDate())) {
                return true;
            }
            if (isDifferent(dbPatient.getFormCheckedBy(), patient.getFormCheckedBy())) {
                return true;
            }
            if (isDifferent(dbPatient.getPatientDisposition(), patient.getPatientDisposition())) {
                return true;
            }
            if (isDifferent(dbPatient.getHospitalInpatientNo(), patient.getHospitalInpatientNo())) {
                return true;
            }
            if (dbPatient.getPatientInjuryTypes().size() != patient.getPatientInjuryTypes().size()) {
                return true;
            }
            if (isDifferent(dbPatient.getInjuryDateTimeString(), patient.getInjuryDateTimeString())) {
                return true;
            }
            if (isDifferent(dbPatient.getHospitalOutpatientNo(), patient.getHospitalOutpatientNo())) {
                return true;
            }
            if (isDifferent(dbPatient.getCounterpartTransportMode(), patient.getCounterpartTransportMode())) {
                return true;
            }
            for (int i = 0; i < dbPatient.getPatientInjuryTypes().size(); i++) {
                PatientInjuryType patientInjury = patient.getPatientInjuryTypes().get(i);
                PatientInjuryType dbPatientInjury = dbPatient.getPatientInjuryTypes().get(i);
                if (dbPatientInjury.equals(patientInjury)) {
                    if (isDifferent(dbPatientInjury.getAis(), patientInjury.getAis())) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }
}