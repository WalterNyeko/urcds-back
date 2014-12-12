package com.sweroad.model;

import com.sweroad.query.Queryable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="roadway_character")
public class RoadwayCharacter extends BaseModel implements Comparable<RoadwayCharacter>, Queryable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2725420586814515518L;
	
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
		return String.format("RoadwayCharacter {%s}", name);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
            return true;
        }
        if (!(o instanceof RoadwayCharacter)) {
            return false;
        }

        final RoadwayCharacter roadwayCharacter = (RoadwayCharacter) o;

        return roadwayCharacter != null ? id.equals(roadwayCharacter.getId()) : false;
	}
	
	@Override
	public int hashCode() {
		return 0;
	}

    @Override
    public int compareTo(RoadwayCharacter rc) {
        if (rc == null || rc.getName() == null) {
            return -1;
        }
        return this.name.compareTo(rc.getName());
    }

    @Override
    public String getEntityName() {
        return this.getClass().getSimpleName();
    }
}