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
@Entity(name = "road_surface")
public class RoadSurface extends NameIdModel implements Comparable<RoadSurface>, Queryable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047408599579285778L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String name;

    public RoadSurface() { }

    public RoadSurface(Long id) { this.setId(id); }

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
	
	@Override
	public String toString() {
		return String.format("Road Surface: {%s}", name);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof RoadSurface)) {
            return false;
        }
        return this.id != null && this.id.equals(((RoadSurface)o).getId());
	}
	
	@Override
	public int hashCode() {
		return 0;
	}

    @Override
    public int compareTo(RoadSurface rs) {
        if (rs == null || rs.getName() == null) {
            return -1;
        }
        return this.name.compareTo(rs.getName());
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
