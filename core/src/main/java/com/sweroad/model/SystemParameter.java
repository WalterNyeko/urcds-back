package com.sweroad.model;

import com.google.common.base.CaseFormat;
import com.sweroad.query.Queryable;

import javax.persistence.*;

@Entity(name = "system_parameter")
public class SystemParameter extends NameIdModel implements Comparable<SystemParameter> {

    /**
     *
     */
    private static final long serialVersionUID = 2725420586814515518L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String code;
    @Column(columnDefinition = "bit not null default 1")
    private boolean active;

    public SystemParameter() { }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return String.format("SystemParameter {%s}", name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SystemParameter)) {
            return false;
        }

        final SystemParameter casualtyClass = (SystemParameter) o;

        return casualtyClass != null ? id.equals(casualtyClass.getId()) : false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(SystemParameter cc) {
        if (cc == null || cc.getName() == null) {
            return -1;
        }
        return this.name.compareTo(cc.getName());
    }
}