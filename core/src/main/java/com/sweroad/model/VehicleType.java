package com.sweroad.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="vehicle_type")
public class VehicleType extends BaseModel {

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
		return String.format("VehicleType {%s}", name);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleType)) {
            return false;
        }

        final VehicleType vehicleType = (VehicleType) o;

        return vehicleType != null ? name.equalsIgnoreCase(vehicleType.getName()) : false;
	}
	
	@Override
	public int hashCode() {
		return 0;
	}
}