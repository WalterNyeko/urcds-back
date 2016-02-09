package com.sweroad.model;

import javax.annotation.Generated;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Frank on 3/12/15.
 */
@Entity
@Table(name = "injury_type")
public class InjuryType extends NameIdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pk.injuryType", cascade = CascadeType.DETACH)
    private Set<PatientInjuryType> patientInjuryTypes = new HashSet(0);
    @Column(columnDefinition = "bit not null default 1")
    private boolean active;

    public InjuryType() { }

    public InjuryType(Long id) { this.setId(id); }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PatientInjuryType> getPatientInjuryTypes() {
        return patientInjuryTypes;
    }

    public void setPatientInjuryTypes(Set<PatientInjuryType> patientInjuryTypes) {
        this.patientInjuryTypes = patientInjuryTypes;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof InjuryType)) {
            return false;
        }
        return this.id != null && this.id.equals(((InjuryType)o).getId());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
