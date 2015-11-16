package com.sweroad.model;

import javax.persistence.*;

/**
 * Created by Frank on 3/12/15.
 */
@Entity(name = "patient_disposition")
public class PatientDisposition extends NameIdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;

    public PatientDisposition() { }

    public PatientDisposition(Long id) { this.setId(id); }

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

    @Override
    public String toString() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PatientDisposition)) {
            return false;
        }
        return this.id != null && this.id.equals(((PatientDisposition)o).getId());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
