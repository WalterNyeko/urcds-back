package com.sweroad.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sweroad.dao.CrashDao;
import com.sweroad.model.CasualtyClass;
import com.sweroad.model.CasualtyType;
import com.sweroad.model.CollisionType;
import com.sweroad.model.Crash;
import com.sweroad.model.CrashCause;
import com.sweroad.model.CrashSeverity;
import com.sweroad.model.District;
import com.sweroad.model.Driver;
import com.sweroad.model.JunctionType;
import com.sweroad.model.PoliceStation;
import com.sweroad.model.RoadSurface;
import com.sweroad.model.RoadwayCharacter;
import com.sweroad.model.SurfaceCondition;
import com.sweroad.model.SurfaceType;
import com.sweroad.model.Vehicle;
import com.sweroad.model.VehicleFailureType;
import com.sweroad.model.VehicleType;
import com.sweroad.model.Weather;
import com.sweroad.service.CrashManager;
import com.sweroad.service.GenericManager;

@Service("crashManager")
public class CrashManagerImpl extends GenericManagerImpl<Crash, Long> implements CrashManager {

	private CrashDao crashDao;
	@Autowired
	private GenericManager<CrashSeverity, Long> crashSeverityManager;
	@Autowired
	private GenericManager<CollisionType, Long> collisionTypeManager;
	@Autowired
	private GenericManager<CrashCause, Long> crashCauseManager;
	@Autowired
	private GenericManager<VehicleFailureType, Long> vehicleFailureTypeManager;
	@Autowired
	private GenericManager<Weather, Long> weatherManager;
	@Autowired
	private GenericManager<SurfaceCondition, Long> surfaceConditionManager;
	@Autowired
	private GenericManager<RoadSurface, Long> roadSurfaceManager;
	@Autowired
	private GenericManager<SurfaceType, Long> surfaceTypeManager;
	@Autowired
	private GenericManager<RoadwayCharacter, Long> roadwayCharacterManager;
	@Autowired
	private GenericManager<JunctionType, Long> junctionTypeManager;
	@Autowired
	private GenericManager<VehicleType, Long> vehicleTypeManager;
	@Autowired
	private GenericManager<CasualtyClass, Long> casualtyClassManager;
	@Autowired
	private GenericManager<CasualtyType, Long> casualtyTypeManager;
	@Autowired
	private GenericManager<PoliceStation, Long> policeStationManager;
	@Autowired
	private GenericManager<District, Long> districtManager;
	@Autowired
	private GenericManager<Driver, Long> driverManager;
	@Autowired
	private GenericManager<Vehicle, Long> vehicleManager;
	
	@Autowired
	public CrashManagerImpl(CrashDao crashDao) {
		super(crashDao);
		this.crashDao = crashDao;
	}
	@Override
	public Crash findByTarNo(String tarNo) {
		return crashDao.findByTarNo(tarNo);
	}
	@Override
	public Crash saveCrash(Crash crash) {		
		saveCrashVehicles(crash);
		if (crash.getDateCreated() == null) {
			crash.setDateCreated(new Date());
		} else {
			crash.setDateUpdated(new Date());
		}
		setCrashParams(crash);
		return super.save(crash);
	}
	
	private void saveCrashVehicles(Crash crash) {
		if (crash.getVehicles() != null) {
			for (Vehicle vehicle : crash.getVehicles()) {
				saveVehicleDriver(vehicle);
				if (vehicle.getDateCreated() == null) {
					vehicle.setDateCreated(new Date());
					vehicle.setId(null);
				} else {
					vehicle.setDateUpdated(new Date());
				}
				setVehicleParams(vehicle);
				vehicle = vehicleManager.save(vehicle);
			}
		}
	}

	private void saveVehicleDriver(Vehicle vehicle) {
		if (vehicle.getDriver() != null) {
			Driver driver = vehicle.getDriver();
			if (driver.getDateCreated() == null) {
				driver.setDateCreated(new Date());
				driver.setId(null);
			} else {
				driver.setDateUpdated(new Date());
			}
			setDriverParams(driver);
			driver = driverManager.save(driver);
			vehicle.setDriver(driver);
		}
	}
	
	private void setCrashParams(Crash crash) {
		if (crash.getCollisionType() != null) {
			crash.setCollisionType(collisionTypeManager.get(crash.getCollisionType().getId()));
		}
		if (crash.getCrashSeverity() != null) {
			crash.setCrashSeverity(crashSeverityManager.get(crash.getCrashSeverity().getId()));
		}
		if (crash.getJunctionType() != null) {
			crash.setJunctionType(junctionTypeManager.get(crash.getJunctionType().getId()));
		}
		if (crash.getMainCrashCause() != null) {
			crash.setMainCrashCause(crashCauseManager.get(crash.getMainCrashCause().getId()));
		}
		if (crash.getPoliceStation() != null) {
			crash.setPoliceStation(policeStationManager.get(crash.getPoliceStation().getId()));
		}
		if (crash.getRoadSurface() != null) {
			crash.setRoadSurface(roadSurfaceManager.get(crash.getRoadSurface().getId()));
		}
		if (crash.getRoadwayCharacter() != null) {
			crash.setRoadwayCharacter(roadwayCharacterManager.get(crash.getRoadwayCharacter().getId()));
		}
		if (crash.getSurfaceCondition() != null) {
			crash.setSurfaceCondition(surfaceConditionManager.get(crash.getSurfaceCondition().getId()));
		}
		if (crash.getSurfaceType() != null) {
			crash.setSurfaceType(surfaceTypeManager.get(crash.getSurfaceType().getId()));
		}
		if (crash.getVehicleFailureType() != null) {
			crash.setVehicleFailureType(vehicleFailureTypeManager.get(crash.getVehicleFailureType().getId()));
		}
		if (crash.getWeather() != null) {
			crash.setWeather(weatherManager.get(crash.getWeather().getId()));
		}
	}
	
	private void setDriverParams(Driver driver) {
		if (driver.getCasualtyType() != null) {
			driver.setCasualtyType(casualtyTypeManager.get(driver.getCasualtyType().getId()));
		}
	}
	
	private void setVehicleParams(Vehicle vehicle) {
		if (vehicle.getVehicleType() != null) {
			vehicle.setVehicleType(vehicleTypeManager.get(vehicle.getVehicleType().getId()));
		}
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public Map<String, List> getReferenceData() {
		Map<String, List> referenceData = new HashMap<String, List>();
		//Add reference data to map for use in the UI
		List<CrashSeverity> crashSeverities = crashSeverityManager.getAllDistinct();
		List<CollisionType> collisionTypes = collisionTypeManager.getAllDistinct();
		List<CrashCause> crashCauses = crashCauseManager.getAllDistinct();
		List<VehicleFailureType> vehicleFailureTypes = vehicleFailureTypeManager.getAllDistinct();
		List<Weather> weathers = weatherManager.getAllDistinct();
		List<SurfaceCondition> surfaceConditions = surfaceConditionManager.getAllDistinct();
		List<RoadSurface> roadSurfaces = roadSurfaceManager.getAllDistinct();
		List<SurfaceType> surfaceTypes = surfaceTypeManager.getAllDistinct();
		List<RoadwayCharacter> roadwayCharacters = roadwayCharacterManager.getAllDistinct();
		List<JunctionType> junctionTypes = junctionTypeManager.getAllDistinct();
		List<PoliceStation> policeStations = policeStationManager.getAllDistinct();
		List<District> districts = districtManager.getAllDistinct();
		List<VehicleType> vehicleTypes = vehicleTypeManager.getAllDistinct();
		List<CasualtyType> casualtyTypes = casualtyTypeManager.getAllDistinct();
		List<CasualtyClass> casualtyClasses = casualtyClassManager.getAllDistinct();

		referenceData.put("crashSeverities", crashSeverities);
		referenceData.put("collisionTypes", collisionTypes);
		referenceData.put("crashCauses", crashCauses);
		referenceData.put("vehicleFailureTypes", vehicleFailureTypes);
		referenceData.put("weathers", weathers);
		referenceData.put("surfaceConditions", surfaceConditions);
		referenceData.put("roadSurfaces", roadSurfaces);
		referenceData.put("surfaceTypes", surfaceTypes);
		referenceData.put("roadwayCharacters", roadwayCharacters);
		referenceData.put("junctionTypes", junctionTypes);
		referenceData.put("districts", districts);
		referenceData.put("policeStations", policeStations);
		referenceData.put("vehicleTypes", vehicleTypes);
		referenceData.put("casualtyTypes", casualtyTypes);
		referenceData.put("casualtyClasses", casualtyClasses);
		
		return referenceData;
	}
}
