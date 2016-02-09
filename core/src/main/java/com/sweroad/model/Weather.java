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
@Entity(name = "weather")
public class Weather extends NameIdModel implements Comparable<Weather>, Queryable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6015115060520130082L;
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String name;
    @Column(columnDefinition = "bit not null default 1")
    private boolean active;

    public Weather() { }

    public Weather(Long id) { this.setId(id); }

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
		return String.format("Weather: {%s}", name);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Weather)) {
            return false;
        }
        return this.id != null && this.id.equals(((Weather)o).getId());
	}
	
	@Override
	public int hashCode() {
		return 0;
	}

    @Override
    public int compareTo(Weather w) {
        if (w == null || w.getName() == null) {
            return -1;
        }
        return this.name.compareTo(w.getName());
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
