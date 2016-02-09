package com.sweroad.model;

import com.google.common.base.CaseFormat;
import com.sweroad.query.Queryable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "casualty_class")
public class CasualtyClass extends NameIdModel implements Comparable<CasualtyClass>, Queryable {

    /**
     *
     */
    private static final long serialVersionUID = 2725420586814515518L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "bit not null default 1")
    private boolean active;

    public CasualtyClass() { }

    public CasualtyClass(Long id) { this.setId(id); }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
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
        return String.format("CasualtyClass {%s}", name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CasualtyClass)) {
            return false;
        }

        final CasualtyClass casualtyClass = (CasualtyClass) o;

        return casualtyClass != null ? id.equals(casualtyClass.getId()) : false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(CasualtyClass cc) {
        if (cc == null || cc.getName() == null) {
            return -1;
        }
        return this.name.compareTo(cc.getName());
    }

    @Override
    public String getEntityName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String getNameForQuery() {
        return Crash.CASUALTY_ALIAS_DOT
                .concat(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, getEntityName()));
    }
}