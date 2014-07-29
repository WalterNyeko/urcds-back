package com.sweroad.model;

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
public class RoadSurface extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047408599579285778L;

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
		return String.format("Road Surface: {%s}", name);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
            return true;
        }
        if (!(o instanceof RoadSurface)) {
            return false;
        }

        final RoadSurface roadSurface = (RoadSurface) o;

        return roadSurface != null ? id.equals(roadSurface.getId()) : false;
	}
	
	@Override
	public int hashCode() {
		return 0;
	}
}
