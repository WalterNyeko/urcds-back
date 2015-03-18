package com.sweroad.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.annotation.Generated;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Frank on 3/12/15.
 */
@Entity(name = "patient")
public class Patient extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "hospital_outpatient_no")
    private String hospitalOutpatientNo;
    @Column(name = "hospital_inpatient_no")
    private String hospitalInpatientNo;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;
    @Column
    private String gender;
    @Column
    private Integer age;
    @Column
    private String village;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "district_id")
    private District district;
    @Column(name = "injury_description")
    private String injuryDescription;
    @Column(name = "injury_datetime")
    private Date injuryDateTime;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "transport_mode_id")
    private TransportMode transportMode;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "rode_user_type_id")
    private RoadUserType roadUserType;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "counterpart_transport_mode_id")
    private TransportMode counterpartTransportMode;
    @Column(name = "belt_used")
    private Boolean beltUsed;
    @Column(name = "helmet_used")
    private Boolean helmetUsed;
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = "patient_injury", joinColumns = {@JoinColumn(name = "patient_id", referencedColumnName = "id")}, inverseJoinColumns = @JoinColumn(name = "injury_type_id", referencedColumnName = "id"))
    private List<InjuryType> injuryTypes = new ArrayList<InjuryType>();
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "patient_disposition_id")
    private PatientDisposition patientDisposition;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "patient_status_id")
    private PatientStatus patientStatus;
    @Column(name = "form_filled_by")
    private String formFilledBy;
    @Column(name = "form_filled_on")
    private Date formFilledOn;
    @Column(name = "form_checked_by")
    private String formCheckedBy;
    @Column(name = "form_checked_on")
    private Date formCheckedOn;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHospitalOutpatientNo() {
        return hospitalOutpatientNo;
    }

    public void setHospitalOutpatientNo(String hospitalOutpatientNo) {
        this.hospitalOutpatientNo = hospitalOutpatientNo;
    }

    public String getHospitalInpatientNo() {
        return hospitalInpatientNo;
    }

    public void setHospitalInpatientNo(String hospitalInpatientNo) {
        this.hospitalInpatientNo = hospitalInpatientNo;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public String getInjuryDescription() {
        return injuryDescription;
    }

    public void setInjuryDescription(String injuryDescription) {
        this.injuryDescription = injuryDescription;
    }

    public Date getInjuryDateTime() {
        return injuryDateTime;
    }

    public void setInjuryDateTime(Date injuryDateTime) {
        this.injuryDateTime = injuryDateTime;
    }

    public TransportMode getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(TransportMode transportMode) {
        this.transportMode = transportMode;
    }

    public RoadUserType getRoadUserType() {
        return roadUserType;
    }

    public void setRoadUserType(RoadUserType roadUserType) {
        this.roadUserType = roadUserType;
    }

    public TransportMode getCounterpartTransportMode() {
        return counterpartTransportMode;
    }

    public void setCounterpartTransportMode(TransportMode counterpartTransportMode) {
        this.counterpartTransportMode = counterpartTransportMode;
    }

    public Boolean getBeltUsed() {
        return beltUsed;
    }

    public void setBeltUsed(Boolean beltUsed) {
        this.beltUsed = beltUsed;
    }

    public Boolean getHelmetUsed() {
        return helmetUsed;
    }

    public void setHelmetUsed(Boolean helmetUsed) {
        this.helmetUsed = helmetUsed;
    }

    public List<InjuryType> getInjuryTypes() {
        return injuryTypes;
    }

    public void setInjuryTypes(List<InjuryType> injuryTypes) {
        this.injuryTypes = injuryTypes;
    }

    public void addInjuryType(InjuryType injuryType) {
        if(injuryTypes == null) {
            injuryTypes = new ArrayList<InjuryType>();
        }
        injuryTypes.add(injuryType);
    }

    public PatientDisposition getPatientDisposition() {
        return patientDisposition;
    }

    public void setPatientDisposition(PatientDisposition patientDisposition) {
        this.patientDisposition = patientDisposition;
    }

    public PatientStatus getPatientStatus() {
        return patientStatus;
    }

    public void setPatientStatus(PatientStatus patientStatus) {
        this.patientStatus = patientStatus;
    }

    public String getFormFilledBy() {
        return formFilledBy;
    }

    public void setFormFilledBy(String formFilledBy) {
        this.formFilledBy = formFilledBy;
    }

    public Date getFormFilledOn() {
        return formFilledOn;
    }

    public void setFormFilledOn(Date formFilledOn) {
        this.formFilledOn = formFilledOn;
    }

    public String getFormCheckedBy() {
        return formCheckedBy;
    }

    public void setFormCheckedBy(String formCheckedBy) {
        this.formCheckedBy = formCheckedBy;
    }

    public Date getFormCheckedOn() {
        return formCheckedOn;
    }

    public void setFormCheckedOn(Date formCheckedOn) {
        this.formCheckedOn = formCheckedOn;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
