package com.sweroad.model;

import com.google.common.base.CaseFormat;
import com.sweroad.query.Queryable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="crash_cause")
public class CrashCause extends NameIdModel implements Comparable<CrashCause>, Queryable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2725420586814515518L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String name;
    @Column(columnDefinition = "bit not null default 1")
    private boolean active;

    public CrashCause() { }

    public CrashCause(Long id) { this.setId(id); }

    public CrashCause(String name) {
        this.setName(name);
        this.setActive(true);
    }

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
		return String.format("CrashCause {%s}", name);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof CrashCause)) {
            return false;
        }
        return this.id != null && this.id.equals(((CrashCause)o).getId());
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

    @Override
    public int compareTo(CrashCause cc) {
        if (cc == null || cc.getName() == null) {
            return -1;
        }
        return this.name.compareTo(cc.getName());
    }

    @Override
    public String getEntityName() {
        return this.getClass().getSimpleName();
    }

    /**
     * Gets name to be used within query from Crash POV.
     *
     * @return name for query
     */
    @Override
    public String getNameForQuery() {
        return Crash.CRASH_ALIAS_DOT.concat(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, getEntityName()));
    }
}
