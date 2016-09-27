/**
 * 
 */
package com.sweroad.model;

import com.google.common.base.CaseFormat;
import com.sweroad.query.Queryable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Frank
 *
 */
@Entity(name = "surface_condition")
public class SurfaceCondition extends NameIdModel implements Comparable<SurfaceCondition>, Queryable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5401626174027626340L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String name;
    @Column(columnDefinition = "bit not null default 1")
    private boolean active;

    public SurfaceCondition() { }

    public SurfaceCondition(Long id) { this.setId(id); }

    public SurfaceCondition(String name) {
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
		return String.format("Surface Condition: {%s}", name);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SurfaceCondition)) {
            return false;
        }
        return this.id != null && this.id.equals(((SurfaceCondition)o).getId());
	}
	
	@Override
	public int hashCode() {
		return 0;
	}

    @Override
    public int compareTo(SurfaceCondition sc) {
        if (sc == null || sc.getName() == null) {
            return -1;
        }
        return this.name.compareTo(sc.getName());
    }

    @Override
    public String getEntityName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String getNameForQuery() {
        return Crash.CRASH_ALIAS_DOT
                .concat(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, getEntityName()));
    }
}
