/**
 *
 */
package com.sweroad.model;

import java.beans.Transient;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.*;

import com.sweroad.audit.IAuditable;
import com.sweroad.audit.IXMLConvertible;
import com.sweroad.util.CrashHelper;
import com.sweroad.util.DateUtil;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.sweroad.Constants;

/**
 * @author Frank
 */
@Entity
@Table(name = "crash")
@NamedQueries({
        @NamedQuery(name = Crash.FIND_CRASHES_ORDER_BY_DATE, query = "from Crash c order by c.crashDateTime"),
        @NamedQuery(name = Crash.FIND_CRASHES_ORDER_BY_DATE_DESC, query = "from Crash c order by c.crashDateTime desc"),
        @NamedQuery(name = Crash.FIND_REMOVED_CRASHES_ORDER_BY_DATE_DESC, query = "from Crash c where c.isRemoved = true order by c.crashDateTime desc"),
        @NamedQuery(name = Crash.FIND_AVAILABLE_CRASHES_ORDER_BY_DATE_DESC, query = "from Crash c where c.isRemoved = false order by c.crashDateTime desc"),
        @NamedQuery(name = Crash.FIND_DISTRICT_CRASHES_ORDER_BY_DATE_DESC, query = "from Crash c where c.policeStation.district = :district order by c.crashDateTime desc"),
        @NamedQuery(name = Crash.FIND_AVAILABLE_DISTRICT_CRASHES_ORDER_BY_DATE_DESC, query = "from Crash c where c.isRemoved = false and c.policeStation.district = :district order by c.crashDateTime desc")})
public class Crash extends BaseModel implements Comparable<Crash>, IXMLConvertible, IAuditable {

    /**
     *
     */
    private static final long serialVersionUID = 2144213374837809344L;
    public static final String FIND_CRASHES_ORDER_BY_DATE = "findCrashesOrderByDate";
    public static final String FIND_CRASHES_ORDER_BY_DATE_DESC = "findCrashesOrderByDateDesc";
    public static final String FIND_REMOVED_CRASHES_ORDER_BY_DATE_DESC = "findRemovedCrashesOrderByDateDesc";
    public static final String FIND_DISTRICT_CRASHES_ORDER_BY_DATE_DESC = "findDistrictCrashesOrderByDateDesc";
    public static final String FIND_AVAILABLE_CRASHES_ORDER_BY_DATE_DESC = "findAvailableCrashesOrderByDateDesc";
    public static final String FIND_AVAILABLE_DISTRICT_CRASHES_ORDER_BY_DATE_DESC = "findAvailableDistrictCrashesOrderByDateDesc";
    public static final String CRASH_ALIAS_DOT = "c.";
    public static final String CASUALTY_ALIAS_DOT  = "i.";
    public static final String VEHICLE_ALIAS_DOT = "v.";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "tar_no", nullable = false)
    private String tarNo;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "police_station_id", nullable = false)
    private PoliceStation policeStation;
    @Column(name = "town_or_village")
    private String townOrVillage;
    @Column
    private String road;
    @Column(name = "road_number")
    private String roadNumber;
    @Column(name = "crash_place")
    private String crashPlace;
    @Column(name = "crash_date_time")
    private Date crashDateTime;
    private String crashDateTimeString;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "crash_severity_id")
    private CrashSeverity crashSeverity;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "collision_type_id")
    private CollisionType collisionType;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "main_crash_cause_id")
    private CrashCause crashCause;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "vehicle_failure_type_id")
    private VehicleFailureType vehicleFailureType;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "weather_id")
    private Weather weather;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "surface_condition_id")
    private SurfaceCondition surfaceCondition;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "road_surface_id")
    private RoadSurface roadSurface;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "surface_type_id")
    private SurfaceType surfaceType;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "roadway_character_id")
    private RoadwayCharacter roadwayCharacter;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "junction_type_id")
    private JunctionType junctionType;
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = "crash_vehicle", joinColumns = {@JoinColumn(name = "crash_id", referencedColumnName = "id")}, inverseJoinColumns = @JoinColumn(name = "vehicle_id", referencedColumnName = "id"))
    private List<Vehicle> vehicles = new ArrayList<Vehicle>();
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = "crash_casualty", joinColumns = {@JoinColumn(name = "crash_id", referencedColumnName = "id")}, inverseJoinColumns = @JoinColumn(name = "casualty_id", referencedColumnName = "id"))
    private List<Casualty> casualties = new ArrayList<Casualty>();
    @Column(name = "reporting_officer_name")
    private String reportingOfficerName;
    @Column(name = "reporting_officer_rank")
    private String reportingOfficerRank;
    @Column(name = "reporting_date")
    private Date reportingDate;
    @Column(name = "supervising_officer_name")
    private String supervisingOfficerName;
    @Column(name = "supervising_officer_rank")
    private String supervisingOfficerRank;
    @Column(name = "supervising_date")
    private Date supervisingDate;
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
    @Column
    private String latitude;
    @Column
    private String longitude;
    @Column(name = "latitude_numeric")
    private Double latitudeNumeric;
    @Column(name = "longitude_numeric")
    private Double longitudeNumeric;
    @Column
    private BigDecimal weight;
    private transient boolean dirty;

    /**
     * @return the casualties
     */
    public List<Casualty> getCasualties() {
        return casualties;
    }

    /**
     * @param casualties the casualties to set
     */
    public void setCasualties(List<Casualty> casualties) {
        this.casualties = casualties;
    }

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
     * @return the tarNo
     */
    public String getTarNo() {
        return tarNo;
    }

    /**
     * @param tarNo the tarNo to set
     */
    public void setTarNo(String tarNo) {
        this.tarNo = tarNo;
    }

    /**
     * @return the policeStation
     */
    public PoliceStation getPoliceStation() {
        return policeStation;
    }

    /**
     * @param policeStation the policeStation to set
     */
    public void setPoliceStation(PoliceStation policeStation) {
        this.policeStation = policeStation;
    }

    /**
     * @return the townOrVillage
     */
    public String getTownOrVillage() {
        return townOrVillage;
    }

    /**
     * @param townOrVillage the townOrVillage to set
     */
    public void setTownOrVillage(String townOrVillage) {
        this.townOrVillage = townOrVillage;
    }

    /**
     * @return the road
     */
    public String getRoad() {
        return road;
    }

    /**
     * @param road the road to set
     */
    public void setRoad(String road) {
        this.road = road;
    }

    /**
     * @return the roadNumber
     */
    public String getRoadNumber() {
        return roadNumber;
    }

    /**
     * @param roadNumber the roadNumber to set
     */
    public void setRoadNumber(String roadNumber) {
        this.roadNumber = roadNumber;
    }

    /**
     * @return the crashPlace
     */
    public String getCrashPlace() {
        return crashPlace;
    }

    /**
     * @param crashPlace the crashPlace to set
     */
    public void setCrashPlace(String crashPlace) {
        this.crashPlace = crashPlace;
    }

    /**
     * @return the crashDateTime
     */
    public Date getCrashDateTime() {
        return crashDateTime;
    }

    /**
     * @param crashDateTime the crashDateTime to set
     */
    public void setCrashDateTime(Date crashDateTime) {
        this.crashDateTime = crashDateTime;
    }

    /**
     * @return the crashDateTimeString
     */
    public String getCrashDateTimeString() {
        return crashDateTimeString;
    }

    /**
     * @param crashDateTimeString the crashDateTimeString to set
     */
    public void setCrashDateTimeString(String crashDateTimeString) {
        this.crashDateTimeString = crashDateTimeString;
    }

    /**
     * @return the crashSeverity
     */
    public CrashSeverity getCrashSeverity() {
        return crashSeverity;
    }

    /**
     * @param crashSeverity the crashSeverity to set
     */
    public void setCrashSeverity(CrashSeverity crashSeverity) {
        this.crashSeverity = crashSeverity;
    }

    /**
     * @return the collisionType
     */
    public CollisionType getCollisionType() {
        return collisionType;
    }

    /**
     * @param collisionType the collisionType to set
     */
    public void setCollisionType(CollisionType collisionType) {
        this.collisionType = collisionType;
    }

    /**
     * @return the mainCrashCause
     */
    public CrashCause getCrashCause() {
        return crashCause;
    }

    /**
     * @param mainCrashCause the mainCrashCause to set
     */
    public void setCrashCause(CrashCause mainCrashCause) {
        this.crashCause = mainCrashCause;
    }

    /**
     * @return the vehicleFailureType
     */
    public VehicleFailureType getVehicleFailureType() {
        return vehicleFailureType;
    }

    /**
     * @param vehicleFailureType the vehicleFailureType to set
     */
    public void setVehicleFailureType(VehicleFailureType vehicleFailureType) {
        this.vehicleFailureType = vehicleFailureType;
    }

    /**
     * @return the weather
     */
    public Weather getWeather() {
        return weather;
    }

    /**
     * @param weather the weather to set
     */
    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    /**
     * @return the surfaceCondition
     */
    public SurfaceCondition getSurfaceCondition() {
        return surfaceCondition;
    }

    /**
     * @param surfaceCondition the surfaceCondition to set
     */
    public void setSurfaceCondition(SurfaceCondition surfaceCondition) {
        this.surfaceCondition = surfaceCondition;
    }

    /**
     * @return the roadSurface
     */
    public RoadSurface getRoadSurface() {
        return roadSurface;
    }

    /**
     * @param roadSurface the roadSurface to set
     */
    public void setRoadSurface(RoadSurface roadSurface) {
        this.roadSurface = roadSurface;
    }

    /**
     * @return the surfaceType
     */
    public SurfaceType getSurfaceType() {
        return surfaceType;
    }

    /**
     * @param surfaceType the surfaceType to set
     */
    public void setSurfaceType(SurfaceType surfaceType) {
        this.surfaceType = surfaceType;
    }

    /**
     * @return the roadwayCharacter
     */
    public RoadwayCharacter getRoadwayCharacter() {
        return roadwayCharacter;
    }

    /**
     * @param roadwayCharacter the roadwayCharacter to set
     */
    public void setRoadwayCharacter(RoadwayCharacter roadwayCharacter) {
        this.roadwayCharacter = roadwayCharacter;
    }

    /**
     * @return the junctionType
     */
    public JunctionType getJunctionType() {
        return junctionType;
    }

    /**
     * @param junctionType the junctionType to set
     */
    public void setJunctionType(JunctionType junctionType) {
        this.junctionType = junctionType;
    }

    /**
     * @return the crashVehicles
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * @param vehicles the vehicles to set
     */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    /**
     * @return the reportingOfficerName
     */
    public String getReportingOfficerName() {
        return reportingOfficerName;
    }

    /**
     * @param reportingOfficerName the reportingOfficerName to set
     */
    public void setReportingOfficerName(String reportingOfficerName) {
        this.reportingOfficerName = reportingOfficerName;
    }

    /**
     * @return the reportingOfficerRank
     */
    public String getReportingOfficerRank() {
        return reportingOfficerRank;
    }

    /**
     * @param reportingOfficerRank the reportingOfficerRank to set
     */
    public void setReportingOfficerRank(String reportingOfficerRank) {
        this.reportingOfficerRank = reportingOfficerRank;
    }

    /**
     * @return the reportingDate
     */
    public Date getReportingDate() {
        return reportingDate;
    }

    public String getReportingDateString() {
        if (reportingDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(reportingDate);
        }
        return "";
    }

    /**
     * @param reportingDate the reportingDate to set
     */
    public void setReportingDate(Date reportingDate) {
        this.reportingDate = reportingDate;
    }

    public void setReportingDateString(String reportingDateString) {
        this.reportingDate = DateUtil.parseDate("yyyy-MM-dd", reportingDateString);
    }

    /**
     * @return the supervisingOfficerName
     */
    public String getSupervisingOfficerName() {
        return supervisingOfficerName;
    }

    /**
     * @param supervisingOfficerName the supervisingOfficerName to set
     */
    public void setSupervisingOfficerName(String supervisingOfficerName) {
        this.supervisingOfficerName = supervisingOfficerName;
    }

    /**
     * @return the supervisingOfficerRank
     */
    public String getSupervisingOfficerRank() {
        return supervisingOfficerRank;
    }

    /**
     * @param supervisingOfficerRank the supervisingOfficerRank to set
     */
    public void setSupervisingOfficerRank(String supervisingOfficerRank) {
        this.supervisingOfficerRank = supervisingOfficerRank;
    }

    /**
     * @return the supervisingDate
     */
    public Date getSupervisingDate() {
        return supervisingDate;
    }

    public String getSupervisingDateString() {
        if (supervisingDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(supervisingDate);
        }
        return "";
    }

    /**
     * @param supervisingDate the supervisingDate to set
     */
    public void setSupervisingDate(Date supervisingDate) {
        this.supervisingDate = supervisingDate;
    }

    public void setSupervisingDateString(String supervisingDateString) {
        this.supervisingDate = DateUtil.parseDate("yyyy-MM-dd", supervisingDateString);
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

    public void addCasualty(Casualty casualty) {
        if(casualties == null) {
            casualties = new ArrayList<Casualty>();
        }
        casualties.add(casualty);
    }

    public void addVehicle(Vehicle vehicle) {
        if(vehicles == null) {
            vehicles = new ArrayList<Vehicle>();
        }
        vehicles.add(vehicle);
    }

    public int getCasualtyCount() {
        int numInjuredDrivers = 0;
        for(Vehicle vehicle : vehicles) {
            if(vehicle.getDriver() != null && vehicle.getDriver().isCasualty()) {
                numInjuredDrivers++;
            }
        }
        return casualties.size() + numInjuredDrivers;
    }

    public int getVehicleCount() {
        return vehicles.size();
    }

    public String getCrashDisplayDate() {
        if (crashDateTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(crashDateTime);
        }
        return "";
    }

    public String getCrashDisplayDateTime() {
        if (crashDateTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.format(crashDateTime);
        }
        return "";
    }

    public Integer getCrashYear() {
        if (crashDateTime != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(crashDateTime);
            return cal.get(Calendar.YEAR);
        }
        return null;
    }

    public Integer getCrashMonth() {
        if (crashDateTime != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(crashDateTime);
            return cal.get(Calendar.MONTH) + 1;
        }
        return null;
    }

    public Integer getCrashDayOfMonth() {
        if (crashDateTime != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(crashDateTime);
            return cal.get(Calendar.DAY_OF_MONTH);
        }
        return null;
    }

    public String getCrashDayOfWeek() {
        if (crashDateTime != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(crashDateTime);
            return Constants.DAYS_OF_WEEK[cal.get(Calendar.DAY_OF_WEEK)];
        }
        return "";
    }

    public String getCrashTime() {
        if (crashDateTime != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(crashDateTime);
            String hours = cal.get(Calendar.HOUR_OF_DAY) < 10 ? "0"
                    + cal.get(Calendar.HOUR_OF_DAY) : ""
                    + cal.get(Calendar.HOUR_OF_DAY);
            String minutes = cal.get(Calendar.MINUTE) < 10 ? "0"
                    + cal.get(Calendar.MINUTE) : "" + cal.get(Calendar.MINUTE);
            return String.format("%s:%s", hours, minutes);
        }
        return null;
    }

    public String getUniqueCode() {
        StringBuilder uniqueCode = new StringBuilder("Crash-");
        uniqueCode.append(id).append(" [").append(tarNo).append("]");
        return uniqueCode.toString();
    }

    /**
     * @return the editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * @param editable the editable to set
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * @return the removable
     */
    public boolean isRemovable() {
        return removable;
    }

    /**
     * @param removable the removable to set
     */
    public void setRemovable(boolean removable) {
        this.removable = removable;
    }

    /**
     * @return the isRemoved
     */
    public boolean isRemoved() {
        return isRemoved;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Double getLatitudeNumeric() {
        return latitudeNumeric;
    }

    public void setLatitudeNumeric(Double latitudeNumeric) {
        this.latitudeNumeric = latitudeNumeric;
    }

    public Double getLongitudeNumeric() {
        return longitudeNumeric;
    }

    public void setLongitudeNumeric(Double longitudeNumeric) {
        this.longitudeNumeric = longitudeNumeric;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    /**
     * Detects if passed object is an updated version of this crash
     *
     * @param obj
     * @return
     */
    @Override
    public boolean isUpdated(IAuditable obj) {
        if (!(obj instanceof Crash)) {
            return false;
        }
        return CrashHelper.isUpdatedVersionOf(this, (Crash) obj);
    }

    /**
     * @param isRemoved the isRemoved to set
     */
    public void setRemoved(boolean isRemoved) {
        this.isRemoved = isRemoved;
    }

    /**
     * Checks if crash has valid gps coordinates
     *
     * @return
     */
    public boolean hasGpsCoordinates() {
        return this.latitudeNumeric != null && this.longitudeNumeric != null;
    }

    @Override
    public String toString() {
        return String.format("Crash {%s}", tarNo);
    }

    public String toJSON() {
        StringBuilder json = new StringBuilder("{");
        json.append(toJsonProperty(this.id, "id")).append(",");
        json.append(toJsonProperty(this.road, "road")).append(",");
        json.append(toJsonProperty(this.tarNo, "tarNo")).append(",");
        json.append(toJsonProperty(this.weight, "weight")).append(",");
        json.append(toJsonProperty(this.weather, "weather")).append(",");
        json.append(toJsonProperty(this.vehicles, "vehicles")).append(",");
        json.append(toJsonProperty(this.latitude, "latitude")).append(",");
        json.append(toJsonProperty(this.longitude, "longitude")).append(",");
        json.append(toJsonProperty(this.casualties, "casualties")).append(",");
        json.append(toJsonProperty(this.crashCause, "crashCause")).append(",");
        json.append(toJsonProperty(this.roadNumber, "roadNumber")).append(",");
        json.append(toJsonProperty(this.crashPlace, "crashPlace")).append(",");
        json.append(toJsonProperty(this.roadSurface, "roadSurface")).append(",");
        json.append(toJsonProperty(this.surfaceType, "surfaceType")).append(",");
        json.append(toJsonProperty(this.junctionType, "junctionType")).append(",");
        json.append(toJsonProperty(this.policeStation, "policeStation")).append(",");
        json.append(toJsonProperty(this.townOrVillage, "townOrVillage")).append(",");
        json.append(toJsonProperty(this.crashSeverity, "crashSeverity")).append(",");
        json.append(toJsonProperty(this.collisionType, "collisionType")).append(",");
        json.append(toJsonProperty(this.latitudeNumeric, "latitudeNumeric")).append(",");
        json.append(toJsonProperty(this.longitudeNumeric, "longitudeNumeric")).append(",");
        json.append(toJsonProperty(this.surfaceCondition, "surfaceCondition")).append(",");
        json.append(toJsonProperty(this.roadwayCharacter, "roadwayCharacter")).append(",");
        json.append(toJsonProperty(this.getCrashDisplayDate(), "crashDateTime")).append(",");
        json.append(toJsonProperty(this.vehicleFailureType, "vehicleFailureType")).append(",");
        json.append(toJsonProperty(this.getCrashDisplayDateTime(), "crashDateTimeString")).append("}");
        return json.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Crash)) {
            return false;
        }
        final Crash crash = (Crash) o;
        return crash != null && id != null ? id.equals(crash.getId()) : false;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public int compareTo(Crash crash) {
        if (crash.getId() == null || id == null) {
            return -1;
        }
        return id.compareTo(crash.getId());
    }

    @Override
    @Transient
    public String getClassAlias() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Long getInstanceId() {
        return id;
    }

    @Override
    @Transient
    public List<String> getFieldsToBeOmitted() {
        return null;
    }

    @Override
    @Transient
    public Map<String, String> getFieldsAliases() {
        return null;
    }

    @Override
    public Crash clone() throws CloneNotSupportedException {
        return (Crash) super.clone();
    }
}
