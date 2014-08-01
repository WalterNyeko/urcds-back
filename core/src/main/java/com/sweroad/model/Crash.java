/**
 * 
 */
package com.sweroad.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @author Frank
 *
 */
@Entity(name = "crash")
public class Crash extends BaseModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2144213374837809344L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "tar_no", nullable = false)
	private String tarNo;
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
	@JoinColumn(name = "police_station_id", nullable = false)
	private PoliceStation policeStation;
	@Column(name = "town_or_village", nullable = false)
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
	@JoinColumn(name = "gps_coordinate_id")
	private GpsCoordinate gpsCoordinate;
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "crash_severity_id")
	private CrashSeverity crashSeverity;
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "collision_type_id")
	private CollisionType collisionType;
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "main_crash_cause_id")
	private CrashCause mainCrashCause;
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
    @JoinTable(
            name = "crash_vehicle",
            joinColumns = { @JoinColumn(name = "crash_id", referencedColumnName = "id") },
            inverseJoinColumns = @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    )
	private List<Vehicle> vehicles = new ArrayList<Vehicle>();
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @Fetch(FetchMode.SELECT)    
    @JoinTable(
            name = "crash_casualty",
            joinColumns = { @JoinColumn(name = "crash_id", referencedColumnName = "id") },
            inverseJoinColumns = @JoinColumn(name = "casualty_id", referencedColumnName = "id")
    )
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
	 * @return the gpsCoordinate
	 */
	public GpsCoordinate getGpsCoordinate() {
		return gpsCoordinate;
	}

	/**
	 * @param gpsCoordinate the gpsCoordinate to set
	 */
	public void setGpsCoordinate(GpsCoordinate gpsCoordinate) {
		this.gpsCoordinate = gpsCoordinate;
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
	public CrashCause getMainCrashCause() {
		return mainCrashCause;
	}

	/**
	 * @param mainCrashCause the mainCrashCause to set
	 */
	public void setMainCrashCause(CrashCause mainCrashCause) {
		this.mainCrashCause = mainCrashCause;
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
	 * @param crashVehicles the crashVehicles to set
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

	/**
	 * @param reportingDate the reportingDate to set
	 */
	public void setReportingDate(Date reportingDate) {
		this.reportingDate = reportingDate;
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

	/**
	 * @param supervisingDate the supervisingDate to set
	 */
	public void setSupervisingDate(Date supervisingDate) {
		this.supervisingDate = supervisingDate;
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
		casualties.add(casualty);
	}
	
	public void addVehicle(Vehicle vehicle) {
		vehicles.add(vehicle);
	}
	
	public int getCasualtyCount() {
		return casualties.size();
	}
	
	public int getVehicleCount() {
		return vehicles.size();
	}
	
	public String getCrashDisplayDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return sdf.format(crashDateTime); 
	}

	@Override
	public String toString() {
		return String.format("Crash {%s}", tarNo);
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
		return 0;
	}	
}
