package com.sweroad.model;

import javax.persistence.*;

/**
 * Created by Frank on 3/12/15.
 */
@Entity(name = "hospital")
public class Hospital extends NameIdModel implements Comparable<Hospital> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "district_id")
    private District district;
    @Column(columnDefinition = "bit not null default 1")
    private boolean active;

    public Hospital() { }

    public Hospital(Long id) { this.setId(id); }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return String.format("Hospital {%s}", name);
    }

    @Override
    public String toJSON() {
        StringBuilder json = new StringBuilder("{");
        json.append(toJsonProperty(this.id, "id")).append(",");
        json.append(toJsonProperty(this.name, "name")).append(",");
        json.append(toJsonProperty(this.district, "district")).append("}");
        return json.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Hospital)) {
            return false;
        }
        return this.id != null && this.id.equals(((Hospital) o).getId());
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public int compareTo(Hospital hospital) {
        if (hospital == null || hospital.getName() == null) {
            return -1;
        }
        return this.name.compareTo(hospital.getName());
    }
}
