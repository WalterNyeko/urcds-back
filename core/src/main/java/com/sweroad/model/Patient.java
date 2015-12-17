package com.sweroad.model;

import com.sweroad.audit.IAuditable;
import com.sweroad.audit.IXMLConvertible;
import com.sweroad.audit.PatientAudit;
import com.sweroad.util.DateUtil;

import javax.persistence.*;
import javax.persistence.Transient;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Frank on 3/12/15.
 */
@Entity
@Table(name = "patient")
@NamedQueries({
        @NamedQuery(name = Patient.FIND_PATIENTS_ORDER_BY_DATE, query = "from Patient p order by p.injuryDateTime"),
        @NamedQuery(name = Patient.FIND_PATIENTS_ORDER_BY_DATE_DESC, query = "from Patient p order by p.injuryDateTime desc"),
        @NamedQuery(name = Patient.FIND_REMOVED_PATIENTS_ORDER_BY_DATE_DESC, query = "from Patient p where p.isRemoved = true order by p.injuryDateTime desc"),
        @NamedQuery(name = Patient.FIND_AVAILABLE_PATIENT_ORDER_BY_DATE_DESC, query = "from Patient p where p.isRemoved = false order by p.injuryDateTime desc"),
        @NamedQuery(name = Patient.FIND_HOSPITAL_PATIENTS_ORDER_BY_DATE_DESC, query = "from Patient p where p.hospital = :hospital order by p.injuryDateTime desc"),
        @NamedQuery(name = Patient.FIND_AVAILABLE_HOSPITAL_PATIENTS_ORDER_BY_DATE_DESC, query = "from Patient p where p.isRemoved = false and p.hospital = :hospital order by p.injuryDateTime desc")})
public class Patient extends BaseModel implements IXMLConvertible, IAuditable {

    public static final String FIND_PATIENTS_ORDER_BY_DATE = "findPatientsOrderByDate";
    public static final String FIND_PATIENTS_ORDER_BY_DATE_DESC = "findPatientsOrderByDateDesc";
    public static final String FIND_REMOVED_PATIENTS_ORDER_BY_DATE_DESC = "findRemovedPatientsOrderByDateDesc";
    public static final String FIND_HOSPITAL_PATIENTS_ORDER_BY_DATE_DESC = "findHospitalPatientsOrderByDateDesc";
    public static final String FIND_AVAILABLE_PATIENT_ORDER_BY_DATE_DESC = "findAvailablePatientsOrderByDateDesc";
    public static final String FIND_AVAILABLE_HOSPITAL_PATIENTS_ORDER_BY_DATE_DESC = "findAvailableHospitalPatientsOrderByDateDesc";

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
    private Integer beltUsed;
    @Transient
    private Quadrian beltUsedOption;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pk.patient", cascade = CascadeType.ALL)
    private List<PatientInjuryType> patientInjuryTypes = new ArrayList<>(0);
    @Transient
    private List<PatientInjuryType> tempPatientInjuries = new ArrayList<>(0);
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
    @Column(nullable = false)
    private boolean editable;
    @Column(nullable = false)
    private boolean removable;
    @Column(name = "is_removed", nullable = false)
    private boolean isRemoved;

    public Patient() { }

    public Patient(Long id) { this.setId(id); }

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

    public String getInjuryDateTimeString() {
        if (injuryDateTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.format(injuryDateTime);
        }
        return "";
    }

    public void setInjuryDateTimeString(String injuryDateTimeString) {
        this.injuryDateTime = DateUtil.parseDate("yyyy-MM-dd HH:mm", injuryDateTimeString);
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

    public Integer getBeltUsed() {
        return beltUsed;
    }

    public void setBeltUsed(Integer beltUsed) {
        this.beltUsed = beltUsed;
    }

    public Quadrian getBeltUsedOption() {
        if (this.beltUsedOption == null && this.beltUsed != null) {
            for(Quadrian option : Quadrian.values()) {
                if (option.getValue() == this.beltUsed.intValue()) {
                    this.beltUsedOption = option;
                }
            }
        }
        return beltUsedOption;
    }

    public void setBeltUsedOption(Quadrian beltUsedOption) {
        this.beltUsedOption = beltUsedOption;
        this.beltUsed = beltUsedOption.getValue();
    }

    public List<PatientInjuryType> getPatientInjuryTypes() {
        return this.patientInjuryTypes;
    }

    public void setPatientInjuryTypes(List<PatientInjuryType> patientInjuryTypes) {
        this.patientInjuryTypes = patientInjuryTypes;
    }

    public List<PatientInjuryType> getTempPatientInjuries() {
        return tempPatientInjuries;
    }

    public void setTempPatientInjuries(List<PatientInjuryType> tempPatientInjuries) {
        this.tempPatientInjuries = tempPatientInjuries;
        for(PatientInjuryType patientInjuryType : this.tempPatientInjuries) {
            patientInjuryType.setPatient(this);
        }
    }

    public void addPatientInjuryType(PatientInjuryType patientInjuryType) {
        if(patientInjuryTypes == null) {
            patientInjuryTypes = new ArrayList<>(0);
        }
        patientInjuryTypes.add(patientInjuryType);
    }

    public void clearPatientInjuryTypes() {
        if (this.patientInjuryTypes != null) {
            this.patientInjuryTypes.clear();
        }
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

    public String getFormFillDate() {
        if (formFilledOn != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(formFilledOn);
        }
        return "";
    }

    public void setFormFillDate(String date) {
        this.formFilledOn = DateUtil.parseDate("yyyy-MM-dd", date);
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

    public String getFormCheckDate() {
        if (formCheckedOn != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(formCheckedOn);
        }
        return "";
    }

    public void setFormCheckDate(String date) {
        this.formCheckedOn = DateUtil.parseDate("yyyy-MM-dd", date);
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

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isRemovable() {
        return removable;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    @Override
    public boolean isUpdated(IAuditable obj) {
        return PatientAudit.hasChanges(this, (Patient) obj);
    }

    @Override
    public Patient clone() throws CloneNotSupportedException {
        Patient clone = (Patient) super.clone();
        clone.setPatientInjuryTypes(new ArrayList<PatientInjuryType>());
        for (PatientInjuryType patientInjuryType : this.patientInjuryTypes) {
            clone.addPatientInjuryType(patientInjuryType.clone());
        }
        return clone;
    }

    public void setRemoved(boolean isRemoved) {
        this.isRemoved = isRemoved;
    }

    @Override
    public String toString() {
        return String.format("Patient {%s}", id != null ? id : "New");
    }

    @Override
    public String toJSON() {
        StringBuilder json = new StringBuilder("{");
        json.append(toJsonProperty(this.id, "id")).append(",");
        json.append(toJsonProperty(this.age, "age")).append(",");
        json.append(toJsonProperty(this.gender, "gender")).append(",");
        json.append(toJsonProperty(this.hospitalOutpatientNo, "hospitalOutpatientNo")).append(",");
        json.append(toJsonProperty(this.hospitalInpatientNo, "hospitalInpatientNo")).append(",");
        json.append(toJsonProperty(this.hospital, "hospital")).append(",");
        json.append(toJsonProperty(this.village, "village")).append(",");
        json.append(toJsonProperty(this.district, "district")).append(",");
        json.append(toJsonProperty(this.injuryDescription, "injuryDescription")).append(",");
        json.append(toJsonProperty(this.getInjuryDateTimeString(), "injuryDateTime")).append(",");
        json.append(toJsonProperty(this.transportMode, "transportMode")).append(",");
        json.append(toJsonProperty(this.roadUserType, "roadUserType")).append(",");
        json.append(toJsonProperty(this.counterpartTransportMode, "counterpartTransportMode")).append(",");
        json.append(toJsonProperty(this.transportMode, "transportMode")).append(",");
        json.append(toJsonProperty(this.patientInjuryTypes, "patientInjuryTypes")).append(",");
        json.append(toJsonProperty(this.getFormFillDate(), "formFillDate")).append(",");
        json.append(toJsonProperty(this.getFormCheckDate(), "formCheckDate")).append("}");
        return json.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Patient)) {
            return false;
        }
        return this.id != null && this.id.equals(((Patient)o).getId());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    @Transient
    public String getClassAlias() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Long getInstanceId() {
        return this.id;
    }

    @Override
    @Transient
    public List<String> getFieldsToBeOmitted() {
        return new ArrayList<>();
    }

    @Override
    @Transient
    public Map<String, String> getFieldsAliases() { return new HashMap<>(); }
}
