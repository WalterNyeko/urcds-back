package com.sweroad.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sweroad.util.GisHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sweroad.dao.CrashDao;
import com.sweroad.model.Casualty;
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
import com.sweroad.service.CrashExcelService;
import com.sweroad.service.CrashManager;
import com.sweroad.service.GenericManager;
import com.sweroad.util.ListUtil;

@Service("crashManager")
public class CrashManagerImpl extends GenericManagerImpl<Crash, Long> implements
        CrashManager {

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
    private GenericManager<Casualty, Long> casualtyManager;
    @Autowired
    private CrashExcelService crashExcelService;
    /**
     * Will be used instead of this so that we can intercept calls to save using
     * audit trail aop classes
     */
    @Autowired
    private GenericManager<Crash, Long> genericCrashManager;

    @Autowired
    public CrashManagerImpl(CrashDao crashDao) {
        super(crashDao);
        this.crashDao = crashDao;
    }

    @Override
    public Crash get(Long id) {
        Crash crash = super.get(id);
        if (crash.getCrashDateTime() != null) {
            crash.setCrashDateTimeString(crash.getCrashDisplayDate());
        }
        return crash;
    }

    @Override
    public List<Crash> getAll() {
        List<Crash> crashes = super.getAll();
        Collections.sort(crashes);
        return crashes;
    }

    public List<Crash> getCrashes() {
        return super.findByNamedQuery(Crash.FIND_CRASHES_ORDER_BY_DATE_DESC, null);
    }

    @Override
    public Crash getCrashForView(Long id) {
        return this.get(id);
    }

    @Override
    public Crash findByTarNo(String tarNo) {
        return crashDao.findByTarNo(tarNo);
    }

    @Override
    public Crash saveCrash(Crash crash) {
        saveCrashVehicles(crash);
        saveCrashCasualties(crash);
        if (crash.getDateCreated() == null) {
            crash.setDateCreated(new Date());
            crash.setEditable(false);
            crash.setRemovable(false);
            crash.setRemoved(false);
        } else {
            crash.setDateUpdated(new Date());
            Crash dbCrash = super.get(crash.getId());
            deleteRemovedVehicles(dbCrash, crash);
            deleteRemovedCasualties(dbCrash, crash);
        }
        setCrashParams(crash);
        return super.save(crash);
    }

    private void saveCrashVehicles(Crash crash) {
        if (crash.getVehicles() != null) {
            for (Vehicle vehicle : crash.getVehicles()) {
                saveVehicleDriver(vehicle);
                long vehicleId = vehicle.getId();
                if (vehicle.getDateCreated() == null) {
                    vehicle.setDateCreated(new Date());
                    vehicle.setId(null);
                } else {
                    vehicle.setDateUpdated(new Date());
                }
                setVehicleParams(vehicle);
                Vehicle savedVehicle = vehicleManager.save(vehicle);
                if (vehicle.getId() == null) {
                    vehicle.setId(vehicleId);
                }
                updateReferencedCasualty(crash, vehicleId, savedVehicle);
            }
        }
    }

    private void updateReferencedCasualty(Crash crash, long vehicleId,
                                          Vehicle vehicle) {
        for (Casualty casualty : crash.getCasualties()) {
            if (casualty.getVehicle() != null
                    && casualty.getVehicle().getId().equals(vehicleId)) {
                casualty.setVehicle(vehicle);
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
            driver = driverManager.save(driver);
            vehicle.setDriver(driver);
        }
    }

    private void saveCrashCasualties(Crash crash) {
        if (crash.getCasualties() != null) {
            for (Casualty casualty : crash.getCasualties()) {
                if (casualty.getDateCreated() == null) {
                    casualty.setDateCreated(new Date());
                    casualty.setId(null);
                } else {
                    casualty.setDateUpdated(new Date());
                }
                setCasualtyParams(casualty);
                casualty = casualtyManager.save(casualty);
            }
        }
    }

    private void setCrashParams(Crash crash) {
        if (crash.getCollisionType() != null) {
            crash.setCollisionType(collisionTypeManager.get(crash
                    .getCollisionType().getId()));
        }
        if (crash.getCrashSeverity() != null) {
            crash.setCrashSeverity(crashSeverityManager.get(crash
                    .getCrashSeverity().getId()));
        }
        if (crash.getJunctionType() != null) {
            crash.setJunctionType(junctionTypeManager.get(crash
                    .getJunctionType().getId()));
        }
        if (crash.getMainCrashCause() != null) {
            crash.setMainCrashCause(crashCauseManager.get(crash
                    .getMainCrashCause().getId()));
        }
        if (crash.getPoliceStation() != null) {
            crash.setPoliceStation(policeStationManager.get(crash
                    .getPoliceStation().getId()));
        }
        if (crash.getRoadSurface() != null) {
            crash.setRoadSurface(roadSurfaceManager.get(crash.getRoadSurface()
                    .getId()));
        }
        if (crash.getRoadwayCharacter() != null) {
            crash.setRoadwayCharacter(roadwayCharacterManager.get(crash
                    .getRoadwayCharacter().getId()));
        }
        if (crash.getSurfaceCondition() != null) {
            crash.setSurfaceCondition(surfaceConditionManager.get(crash
                    .getSurfaceCondition().getId()));
        }
        if (crash.getSurfaceType() != null) {
            crash.setSurfaceType(surfaceTypeManager.get(crash.getSurfaceType()
                    .getId()));
        }
        if (crash.getVehicleFailureType() != null) {
            crash.setVehicleFailureType(vehicleFailureTypeManager.get(crash
                    .getVehicleFailureType().getId()));
        }
        if (crash.getWeather() != null) {
            crash.setWeather(weatherManager.get(crash.getWeather().getId()));
        }
        setGpsCoordinates(crash);
    }

    private void setGpsCoordinates(Crash crash) {
        if(GisHelper.isValidLatitude(crash.getLatitude())
                && GisHelper.isValidLongitude(crash.getLongitude())) {
            crash.setLatitudeNumeric(GisHelper.convertLatDegToDec(crash.getLatitude()));
            crash.setLongitudeNumeric(GisHelper.convertLongDegToDec(crash.getLongitude()));
        }
    }

    private void setVehicleParams(Vehicle vehicle) {
        if (vehicle.getVehicleType() != null) {
            vehicle.setVehicleType(vehicleTypeManager.get(vehicle
                    .getVehicleType().getId()));
        }
    }

    private void setCasualtyParams(Casualty casualty) {
        if (casualty.getCasualtyClass() != null) {
            casualty.setCasualtyClass(casualtyClassManager.get(casualty
                    .getCasualtyClass().getId()));
        }
        if (casualty.getCasualtyType() != null) {
            casualty.setCasualtyType(casualtyTypeManager.get(casualty
                    .getCasualtyType().getId()));
        }

    }

    @Override
    @SuppressWarnings("rawtypes")
    public Map<String, List> getReferenceData() {
        Map<String, List> referenceData = new HashMap<String, List>();
        // Add reference data to map for use in the UI
        List<CrashSeverity> crashSeverities = crashSeverityManager
                .getAllDistinct();
        List<CollisionType> collisionTypes = collisionTypeManager
                .getAllDistinct();
        List<CrashCause> crashCauses = crashCauseManager.getAllDistinct();
        List<VehicleFailureType> vehicleFailureTypes = vehicleFailureTypeManager
                .getAllDistinct();
        List<Weather> weathers = weatherManager.getAllDistinct();
        List<SurfaceCondition> surfaceConditions = surfaceConditionManager
                .getAllDistinct();
        List<RoadSurface> roadSurfaces = roadSurfaceManager.getAllDistinct();
        List<SurfaceType> surfaceTypes = surfaceTypeManager.getAllDistinct();
        List<RoadwayCharacter> roadwayCharacters = roadwayCharacterManager
                .getAllDistinct();
        List<JunctionType> junctionTypes = junctionTypeManager.getAllDistinct();
        List<PoliceStation> policeStations = policeStationManager
                .findByNamedQuery(PoliceStation.FIND_POLICE_STATIONS_ORDER_BY_NAME, null);
        List<District> districts = districtManager
                .findByNamedQuery(District.FIND_DISTRICTS_ORDER_BY_NAME, null);
        List<VehicleType> vehicleTypes = vehicleTypeManager.getAllDistinct();
        List<CasualtyType> casualtyTypes = casualtyTypeManager.getAllDistinct();
        List<CasualtyClass> casualtyClasses = casualtyClassManager
                .getAllDistinct();
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

    @Override
    public void removeCasualtyFromCrash(Crash crash, Long casualtyId) {
        for (Casualty casualty : crash.getCasualties()) {
            if (casualty.getId().equals(casualtyId)) {
                crash.getCasualties().remove(casualty);
                break;
            }
        }
    }

    @Override
    public void removeCasualtiesFromCrash(Crash crash, List<Long> casualtyIds) {
        for (Long casualtyId : casualtyIds) {
            removeCasualtyFromCrash(crash, casualtyId);
        }
    }

    @Override
    public void removeVehicleFromCrash(Crash crash, Long vehicleId) {
        for (Vehicle vehicle : crash.getVehicles()) {
            if (vehicle.getId().equals(vehicleId)) {
                removeVehicleCasualtiesFromCrash(crash, vehicle);
                crash.getVehicles().remove(vehicle);
                break;
            }
        }
    }

    private void removeVehicleCasualtiesFromCrash(Crash crash, Vehicle vehicle) {
        List<Long> casualtyIds = new ArrayList<Long>();
        for (Casualty casualty : crash.getCasualties()) {
            if (casualty.getVehicle() != null
                    && casualty.getVehicle().equals(vehicle)) {
                casualtyIds.add(casualty.getId());
            }
        }
        removeCasualtiesFromCrash(crash, casualtyIds);
    }

    private void deleteRemovedVehicles(Crash dbCrash, Crash crash) {

        List<Vehicle> deletedVehicleIds = getVehiclesForDeletion(dbCrash, crash);
        for (Vehicle vehicle : deletedVehicleIds) {
            vehicleManager.remove(vehicle);
            driverManager.remove(vehicle.getDriver());
        }
    }

    private List<Vehicle> getVehiclesForDeletion(Crash dbCrash,
                                                 Crash crashInEdit) {

        List<Vehicle> vehiclesToDelete = new ArrayList<Vehicle>();
        ListUtil<Vehicle> vehicleUtil = new ListUtil<Vehicle>();
        for (Vehicle vehicle : dbCrash.getVehicles()) {
            if (!vehicleUtil.itemExistsInList(vehicle,
                    crashInEdit.getVehicles())) {
                vehiclesToDelete.add(vehicle);
            }
        }
        return vehiclesToDelete;
    }

    private void deleteRemovedCasualties(Crash dbCrash, Crash crash) {

        List<Casualty> deletedVehicleIds = getCasualtiesForDeletion(dbCrash,
                crash);
        for (Casualty casualty : deletedVehicleIds) {
            casualtyManager.remove(casualty);
        }
    }

    private List<Casualty> getCasualtiesForDeletion(Crash dbCrash,
                                                    Crash crashInEdit) {

        List<Casualty> casualtiesToDelete = new ArrayList<Casualty>();
        ListUtil<Casualty> casualtyUtil = new ListUtil<Casualty>();
        for (Casualty casualty : dbCrash.getCasualties()) {
            if (!casualtyUtil.itemExistsInList(casualty,
                    crashInEdit.getCasualties())) {
                casualtiesToDelete.add(casualty);
            }
        }
        return casualtiesToDelete;
    }

    @Override
    public void generateCrashDataExcel(String filename) throws IOException {
        List<Crash> crashes = crashDao.findByNamedQuery(
                Crash.FIND_CRASHES_ORDER_BY_DATE, null);
        crashExcelService.generateAndWriteCrashExcelToFile(crashes, filename);
    }

    @Override
    public void removeCrashById(Long id) {
        Crash crash = this.get(id);
        crash.setRemoved(true);
        genericCrashManager.save(crash);
    }

    @Override
    public void restoreCrashById(Long id) {
        Crash crash = this.get(id);
        crash.setRemoved(false);
        genericCrashManager.save(crash);
    }
}
