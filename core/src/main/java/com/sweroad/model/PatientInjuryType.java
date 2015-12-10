package com.sweroad.model;

import javax.persistence.*;

/**
 * Created by Frank on 11/6/15.
 */
@Entity
@Table(name = "patient_injury_type")
@AssociationOverrides({
        @AssociationOverride(name = "pk.patient", joinColumns = @JoinColumn(name = "patient_id")),
        @AssociationOverride(name = "pk.injuryType", joinColumns = @JoinColumn(name = "injury_type_id"))
})
public class PatientInjuryType extends BaseModel implements Cloneable {

    private PatientInjuryTypeId pk = new PatientInjuryTypeId();

    private Boolean ais;

    public PatientInjuryType() {

    }

    @EmbeddedId
    public PatientInjuryTypeId getPk() {
        return pk;
    }

    public void setPk(PatientInjuryTypeId pk) {
        this.pk = pk;
    }

    @Transient
    public Patient getPatient() {
        return this.getPk().getPatient();
    }

    public void setPatient(Patient patient) {
        this.getPk().setPatient(patient);
    }

    @Transient
    public InjuryType getInjuryType() {
        return this.getPk().getInjuryType();
    }

    public void setInjuryType(InjuryType injuryType) {
        this.getPk().setInjuryType(injuryType);
    }

    @Column
    public Boolean getAis() {
        return ais;
    }

    public void setAis(Boolean ais) {
        this.ais = ais;
    }

    @Override
    public String toString() {
        return "PatientInjuryType";
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
        if (!(o instanceof PatientInjuryType)) {
            return false;
        }
        PatientInjuryType that = (PatientInjuryType) o;
        if (this.getPatient() == null || this.getInjuryType() == null) {
            return false;
        }
        return this.getPatient().equals(that.getPatient()) && this.getInjuryType().equals(that.getInjuryType());
    }

    @Override
    public int hashCode() {
        return this.getPatient().hashCode() + this.getInjuryType().hashCode();
    }

    @Override
    public PatientInjuryType clone() throws CloneNotSupportedException {
        return (PatientInjuryType) super.clone();
    }


}
