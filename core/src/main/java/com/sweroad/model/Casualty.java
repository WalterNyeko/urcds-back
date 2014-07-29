package com.sweroad.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="casualty")
public class Casualty extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2725420586814515518L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "gender")
	private String Gender;
	@Column(name = "age")
	private Integer Age;
	@Column(name = "belt_or_helmet_used")
	private Boolean beltOrHelmetUsed;	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "casualty_type_id", nullable = false)
	private CasualtyType casualtyType;
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "vehicle_id")
	private Vehicle vehicle;
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "casualty_class_id", nullable = false)
	private CasualtyClass casualtyClass;

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
	 * @return the gender
	 */
	public String getGender() {
		return Gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		Gender = gender;
	}

	/**
	 * @return the age
	 */
	public Integer getAge() {
		return Age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(Integer age) {
		Age = age;
	}

	/**
	 * @return the beltOrHelmetUsed
	 */
	public Boolean getBeltOrHelmetUsed() {
		return beltOrHelmetUsed;
	}

	/**
	 * @param beltOrHelmetUsed the beltOrHelmetUsed to set
	 */
	public void setBeltOrHelmetUsed(Boolean beltOrHelmetUsed) {
		this.beltOrHelmetUsed = beltOrHelmetUsed;
	}

	/**
	 * @return the casualtyType
	 */
	public CasualtyType getCasualtyType() {
		return casualtyType;
	}

	/**
	 * @param casualtyType the casualtyType to set
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
	 * @param casualtyClass the casualtyClass to set
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

	/**
	 * @param vehicle the vehicle to set
	 */
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	@Override
	public String toString() {
		return String.format("Casualty {%s}", casualtyType.getName());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
            return true;
        }
        if (!(o instanceof Casualty)) {
            return false;
        }

        final Casualty casualty = (Casualty) o;

        return casualty != null ? id.equals(casualty.getId()) : false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}