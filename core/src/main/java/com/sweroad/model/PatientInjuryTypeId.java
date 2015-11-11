package com.sweroad.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * Created by Frank on 11/6/15.
 */
@Embeddable
public class PatientInjuryTypeId extends BaseModel {

    private Patient patient;
    private InjuryType injuryType;

    @ManyToOne
    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @ManyToOne
    public InjuryType getInjuryType() {
        return this.injuryType;
    }

    public void setInjuryType(InjuryType injuryType) {
        this.injuryType = injuryType;
    }

    @Override
    public String toString() {
        return String.format("PatientInjuryTypeId: Patient {%s}; InjuryType {%s}", patient.getId(), injuryType.getId());
    }

    @Override
    public String toJSON() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientInjuryTypeId)) {
            return false;
        }
        PatientInjuryTypeId that = (PatientInjuryTypeId) o;
        return this.patient.equals(that.patient) && this.injuryType.equals(that.injuryType);
    }

    @Override
    public int hashCode() {
        return this.patient.hashCode() + this.injuryType.hashCode();
    }
}
