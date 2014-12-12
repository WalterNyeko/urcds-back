/**
 * 
 */
package com.sweroad.model;

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
@Entity(name = "surface_type")
public class SurfaceType extends BaseModel implements Comparable<SurfaceType>, Queryable {


	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String name;

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
		return String.format("Surface Type: {%s}", name);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
            return true;
        }
        if (!(o instanceof SurfaceType)) {
            return false;
        }

        final SurfaceType surfaceType = (SurfaceType) o;

        return surfaceType != null ? name.equalsIgnoreCase(surfaceType.getName()) : false;
	}
	
	@Override
	public int hashCode() {
		return 0;
	}

    @Override
    public int compareTo(SurfaceType st) {
        if (st == null || st.getName() == null) {
            return -1;
        }
        return this.name.compareTo(st.getName());
    }

    @Override
    public String getEntityName() {
        return this.getClass().getSimpleName();
    }
}
