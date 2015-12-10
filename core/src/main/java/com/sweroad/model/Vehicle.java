package com.sweroad.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity(name = "vehicle")
public class Vehicle extends BaseModel implements Comparable<Vehicle> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2725420586814515518L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private Integer number;
	@ManyToOne
	@JoinColumn(name = "vehicle_type_id")
	private VehicleType vehicleType;
	@ManyToOne
	@JoinColumn(name = "driver_id", nullable = false)
	private Driver driver;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicle", cascade = CascadeType.ALL)
    private Set<Casualty> casualties = new HashSet<>();
	@Column(name = "company_name")
	private String companyName;
	@Column(name = "date_created", nullable = false)
	private Date dateCreated;
	@Column(name = "date_updated")
	private Date dateUpdated;
	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;
	@ManyToOne
	@JoinColumn(name = "updated_by")
	private User updatedBy;
    @Transient
    private Crash crash;

    public Vehicle() { }

    public Vehicle(Long id) { this.setId(id); }

	/**
	 * @return the number
	 */
	public Integer getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}

	/**
	 * @return the driver
	 */
	public Driver getDriver() {
		return driver;
	}

	/**
	 * @param driver
	 *            the driver to set
	 */
	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the vehicleType
	 */
	public VehicleType getVehicleType() {
		return vehicleType;
	}

	/**
	 * @param vehicleType
	 *            the vehicleType to set
	 */
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName
	 *            the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

    public Set<Casualty> getCasualties() {
        return casualties;
    }

    public void setCasualties(Set<Casualty> casualties) {
        this.casualties = casualties;
    }

    /**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @param dateCreated
	 *            the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @return the dateUpdated
	 */
	public Date getDateUpdated() {
		return dateUpdated;
	}

	/**
	 * @param dateUpdated
	 *            the dateUpdated to set
	 */
	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	/**
	 * @return the createdBy
	 */
	public User getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedBy
	 */
	public User getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            the updatedBy to set
	 */
	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

    public Crash getCrash() {
        return crash;
    }

    public void setCrash(Crash crash) {
        this.crash = crash;
    }

	@Override
	public String toString() {
		return String.format("Vehicle {%s}", number);
	}

    @Override
    public String toJSON() {
        StringBuilder json = new StringBuilder("{");
        json.append(toJsonProperty(this.id, "id")).append(",");
        json.append(toJsonProperty(this.number, "number")).append(",");
        json.append(toJsonProperty(this.driver, "driver")).append(",");
        json.append(toJsonProperty(this.vehicleType, "vehicleType")).append(",");
        json.append(toJsonProperty(this.companyName, "companyName")).append("}");
        return json.toString();
    }

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Vehicle)) {
			return false;
		}
        return this.id != null && this.id.equals(((Vehicle)o).getId());
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public int compareTo(Vehicle vehicle) {
		final int BEFORE = -1;
		final int EQUAL = 0;
		final int AFTER = 1;
		if (vehicle.getNumber() == null || number == null) {
			return BEFORE;
		}
		if (number.intValue() == vehicle.getNumber().intValue()) {
			return EQUAL;
		}
		if (number.intValue() < vehicle.getNumber().intValue()) {
			return BEFORE;
		}
		return AFTER;
	}
}