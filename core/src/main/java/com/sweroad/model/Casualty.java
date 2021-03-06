package com.sweroad.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "casualty")
public class Casualty extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2725420586814515518L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "gender")
	private String gender;
	@Column(name = "age")
	private Integer age;
	@Column(name = "belt_or_helmet_used")
	private Integer beltOrHelmetUsed;
    @Transient
    private Quadstate beltOrHelmetUsedOption;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "casualty_type_id", nullable = false)
	private CasualtyType casualtyType;
	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "vehicle_id")
	private Vehicle vehicle;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "casualty_class_id", nullable = false)
	private CasualtyClass casualtyClass;
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

    public Casualty() { }

    public Casualty(Long id) { this.setId(id); }

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
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the age
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * @return the beltOrHelmetUsed
	 */
	public Integer getBeltOrHelmetUsed() {
		return beltOrHelmetUsed;
	}

	/**
	 * @param beltOrHelmetUsed
	 *            the beltOrHelmetUsed to set
	 */
	public void setBeltOrHelmetUsed(Integer beltOrHelmetUsed) {
		this.beltOrHelmetUsed = beltOrHelmetUsed;
	}

    public Quadstate getBeltOrHelmetUsedOption() {
        if (this.beltOrHelmetUsedOption == null && this.beltOrHelmetUsed != null) {
            this.beltOrHelmetUsedOption = Quadstate.getByValue(this.beltOrHelmetUsed);
        }
        return beltOrHelmetUsedOption;
    }

    public void setBeltOrHelmetUsedOption(Quadstate beltOrHelmetUsedOption) {
        this.beltOrHelmetUsedOption = beltOrHelmetUsedOption;
        this.beltOrHelmetUsed = beltOrHelmetUsedOption.getValue();
    }

    /**
	 * @return the casualtyType
	 */
	public CasualtyType getCasualtyType() {
		return casualtyType;
	}

	/**
	 * @param casualtyType
	 *            the casualtyType to set
	 */
	public void setCasualtyType(CasualtyType casualtyType) {
		this.casualtyType = casualtyType;
	}

	/**
	 * @return the casualtyClass
	 */
	public CasualtyClass getCasualtyClass() {
		return casualtyClass;
	}

	/**
	 * @param casualtyClass
	 *            the casualtyClass to set
	 */
	public void setCasualtyClass(CasualtyClass casualtyClass) {
		this.casualtyClass = casualtyClass;
	}

	/**
	 * @return the vehicle
	 */
	public Vehicle getVehicle() {
		return vehicle;
	}

    public boolean isPassenger() {
        return vehicle != null;
    }

	/**
	 * @param vehicle
	 *            the vehicle to set
	 */
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
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
		return String.format("Casualty {%s}", casualtyType.getName());
	}

    @Override
    public String toJSON() {
        StringBuilder json = new StringBuilder("{");
        json.append(toJsonProperty(this.id, "id")).append(",");
        json.append(toJsonProperty(this.age, "age")).append(",");
        json.append(toJsonProperty(this.gender, "gender")).append(",");
        json.append(toJsonProperty(this.beltOrHelmetUsed, "beltOrHelmetUsed")).append(",");
        json.append(toJsonProperty(this.casualtyType, "casualtyType")).append(",");
        json.append(toJsonProperty(this.casualtyClass, "casualtyClass")).append(",");
        json.append(toJsonProperty(this.vehicle, "vehicle")).append("}");
        return json.toString();
    }

    @Override
	public boolean equals(Object o) {
		if (!(o instanceof Casualty)) {
			return false;
		}
        return id != null && this.id.equals(((Casualty)o).getId());
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}