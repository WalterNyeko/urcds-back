package com.sweroad.model;

import com.google.common.base.CaseFormat;
import com.sweroad.query.Queryable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="junction_type")
public class JunctionType extends NameIdModel implements Comparable<JunctionType>, Queryable {

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

    public JunctionType() { }

    public JunctionType(Long id) { this.setId(id); }

    public JunctionType(String name) {
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
		return String.format("JunctionType {%s}", name);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof JunctionType)) {
            return false;
        }
        return this.id != null && this.id.equals(((JunctionType)o).getId());
	}
	
	@Override
	public int hashCode() {
		return 0;
	}

    @Override
    public int compareTo(JunctionType j) {
        if (j == null || j.getName() == null) {
            return -1;
        }
        return this.name.compareTo(j.getName());
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
        return Crash.CRASH_ALIAS_DOT
                .concat(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, getEntityName()));
    }
}