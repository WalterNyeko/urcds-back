package com.sweroad.model;

import com.google.common.base.CaseFormat;
import com.sweroad.query.Queryable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="vehicle_failure_type")
public class VehicleFailureType extends BaseModel implements Comparable<VehicleFailureType>, Queryable {

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
		return String.format("VehicleFailureType {%s}", name);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleFailureType)) {
            return false;
        }

        final VehicleFailureType vehicleFailureType = (VehicleFailureType) o;

        return vehicleFailureType != null ? name.equalsIgnoreCase(vehicleFailureType.getName()) : false;
	}
	
	@Override
	public int hashCode() {
		return 0;
	}

    @Override
    public int compareTo(VehicleFailureType vft) {
        if (vft == null || vft.getName() == null) {
            return -1;
        }
        return this.name.compareTo(vft.getName());
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
