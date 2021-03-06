package com.sweroad.model;

import javax.annotation.Generated;
import javax.persistence.*;

/**
 * Created by Frank on 3/12/15.
 */
@Entity(name = "road_user_type")
public class RoadUserType extends NameIdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @Column(columnDefinition = "bit not null default 1")
    private boolean active;

    public RoadUserType() { }

    public RoadUserType(Long id) { this.setId(id); }

    public RoadUserType(String name) {
        this.setName(name);
        this.setActive(true);
    }

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
        if (!(o instanceof RoadUserType)) {
            return false;
        }
        return this.id != null && this.id.equals(((RoadUserType)o).getId());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
