package com.sweroad.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.sweroad.Constants;

@Entity(name = "driver")
public class Driver extends BaseModel {

    /**
     *
     */
    private static final long serialVersionUID = 2725420586814515518L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "license_valid")
    private Boolean licenseValid;
    @Column(name = "license_number")
    private String licenseNumber;
    @Column(name = "gender")
    private String gender;
    @Column(name = "age")
    private Integer age;
    @Column(name = "belt_used")
    private Boolean beltUsed;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "casualty_type_id")
    private CasualtyType casualtyType;
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

    public Driver() { }

    public Driver(Long id) { this.setId(id); }

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
     * @return the licenseNumber
     */
    public String getLicenseNumber() {
        return licenseNumber;
    }

    /**
     * @param licenseNumber the licenseNumber to set
     */
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
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
     * @param age the age to set
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * @return the beltUsed
     */
    public Boolean getBeltUsed() {
        return beltUsed;
    }

    /**
     * @param beltUsed the beltUsed to set
     */
    public void setBeltUsed(Boolean beltUsed) {
        this.beltUsed = beltUsed;
    }

    /**
     * @return the licenseValid
     */
    public Boolean getLicenseValid() {
        return licenseValid;
    }

    /**
     * @param licenseValid the licenseValid to set
     */
    public void setLicenseValid(Boolean licenseValid) {
        this.licenseValid = licenseValid;
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
     * @return the dateCreated
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated the dateCreated to set
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
     * @param dateUpdated the dateUpdated to set
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
     * @param createdBy the createdBy to set
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
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Casualty toCasualty(Vehicle vehicle) {
        if (this.casualtyType == null) {
            return null;
        }
        if (this.casualtyType.getId().equals(Constants.NOT_INJURED_ID)) {
            return null;
        }
        CasualtyClass cc = new CasualtyClass();
        cc.setName("Driver");
        cc.setId(0L);
        Casualty casualty = new Casualty();
        casualty.setAge(age);
        casualty.setBeltOrHelmetUsed(beltUsed);
        casualty.setCasualtyClass(cc);
        casualty.setCasualtyType(casualtyType);
        casualty.setGender(gender);
        casualty.setVehicle(vehicle);
        casualty.setId(0L);
        return casualty;
    }

    public boolean isCasualty() {
        if (this.casualtyType == null) {
            return false;
        }
        if (this.casualtyType.getId().equals(Constants.NOT_INJURED_ID)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("Driver: License No.: {%s}; Age: {%d}; Gender: {%s}", licenseNumber, age, gender);
    }

    @Override
    public String toJSON() {
        StringBuilder json = new StringBuilder("{");
        json.append(toJsonProperty(this.id, "id")).append(",");
        json.append(toJsonProperty(this.age, "age")).append(",");
        json.append(toJsonProperty(this.gender, "gender")).append(",");
        json.append(toJsonProperty(this.licenseValid, "licenseValid")).append(",");
        json.append(toJsonProperty(this.beltUsed, "beltOrHelmetUsed")).append(",");
        json.append(toJsonProperty(this.licenseNumber, "licenseNumber")).append(",");
        json.append(toJsonProperty(this.casualtyType, "casualtyType")).append("}");
        return json.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Driver)) {
            return false;
        }
        final Driver driver = (Driver) o;
        if (driver != null) {
            if (id != null && id.equals(driver.getId())) {
                return true;
            }
            if (licenseNumber != null
                    && licenseNumber.equals(driver.licenseNumber)) {
                if (age != null && age.equals(driver.getAge())) {
                    if (gender != null && gender.equals(driver.getGender())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}