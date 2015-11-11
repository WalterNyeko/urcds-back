package com.sweroad.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sweroad.Constants;
import com.sweroad.model.*;
import com.sweroad.query.CrashQuery;
import com.sweroad.service.*;
import com.sweroad.util.GisHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sweroad.dao.CrashDao;
import com.sweroad.util.ListUtil;

@Service("crashManager")
public class CrashManagerImpl extends GenericManagerImpl<Crash, Long> implements
        CrashManager {

    private CrashDao crashDao;
    @Autowired
    private UserManager userManager;
    @Autowired
    private LookupManager lookupManager;
    @Autowired
    private CrashExcelService crashExcelService;
    @Autowired
    private GenericManager<Driver, Long> driverManager;
    @Autowired
    private GenericManager<Weather, Long> weatherManager;
    @Autowired
    private GenericManager<Vehicle, Long> vehicleManager;
    @Autowired
    private GenericManager<Casualty, Long> casualtyManager;
    @Autowired
    private GenericManager<District, Long> districtManager;
    @Autowired
    private GenericManager<CrashCause, Long> crashCauseManager;
    @Autowired
    private GenericManager<VehicleType, Long> vehicleTypeManager;
    @Autowired
    private GenericManager<RoadSurface, Long> roadSurfaceManager;
    @Autowired
    private GenericManager<SurfaceType, Long> surfaceTypeManager;
    @Autowired
    private GenericManager<JunctionType, Long> junctionTypeManager;
    @Autowired
    private GenericManager<CasualtyType, Long> casualtyTypeManager;
    @Autowired
    private GenericManager<PoliceStation, Long> policeStationManager;
    @Autowired
    private GenericManager<CasualtyClass, Long> casualtyClassManager;
    @Autowired
    private GenericManager<CrashSeverity, Long> crashSeverityManager;
    @Autowired
    private GenericManager<CollisionType, Long> collisionTypeManager;
    @Autowired
    private GenericManager<SurfaceCondition, Long> surfaceConditionManager;
    @Autowired
    private GenericManager<RoadwayCharacter, Long> roadwayCharacterManager;
    @Autowired
    private GenericManager<VehicleFailureType, Long> vehicleFailureTypeManager;

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
            crash.setCrashDateTimeString(crash.getCrashDisplayDateTime());
        }
        return crash;
    }

    @Override
    public List<Crash> getAll() {
        List<Crash> crashes = super.getAll();
        Collections.sort(crashes);
        return crashes;
    }

    public List<Crash> getCrashes(boolean latestOnly) {
        User user = userManager.getCurrentUser();
        if ((user.hasRole(Constants.USER_ROLE) && user.getDistrict() != null)
                || user.hasRole(Constants.READONLY_ROLE)) {
            return this.getAvailableCrashes(latestOnly);
        } else {
            return latestOnly ? dao.findByNamedQuery(Crash.FIND_CRASHES_ORDER_BY_DATE_DESC, null, 1, 300)
                    : dao.findByNamedQuery(Crash.FIND_CRASHES_ORDER_BY_DATE_DESC, null);
        }
    }

    @Override
    public List<Crash> getCrashes(List<Long> ids) {
        List<Crash> crashes = new ArrayList<>();
        for(Crash crash : this.getAllDistinct()) {
            if (ids.contains(crash.getId())) {
                crashes.add(crash);
            }
        }
        return crashes;
    }

    public List<Crash> getAvailableCrashes(boolean latestOnly) {
        if (latestOnly) {
            User user = userManager.getCurrentUser();
            if (user.hasRole(Constants.USER_ROLE) && user.getDistrict() != null) {
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("district", user.getDistrict());
                return dao.findByNamedQuery(Crash.FIND_AVAILABLE_DISTRICT_CRASHES_ORDER_BY_DATE_DESC, parameters, 1, 300);
            }
            return dao.findByNamedQuery(Crash.FIND_AVAILABLE_CRASHES_ORDER_BY_DATE_DESC, null, 1, 300);
        }
        return dao.findByNamedQuery(Crash.FIND_AVAILABLE_CRASHES_ORDER_BY_DATE_DESC, null);
    }

    @Override
    public List<Crash> getRemovedCrashes() {
        return dao.findByNamedQuery(Crash.FIND_REMOVED_CRASHES_ORDER_BY_DATE_DESC, null);
    }

    @Override
    public Crash getCrashForView(Long id) {
        return this.get(id);
    }

    @Override
    public Crash saveCrash(Crash crash) {
        User user = userManager.getCurrentUser();
        saveCrashVehicles(crash, user);
        saveCrashCasualties(crash, user);
        if (crash.getDateCreated() == null) {
            crash.setDateCreated(new Date());
            crash.setCreatedBy(user);
            crash.setEditable(false);
            crash.setRemovable(false);
            crash.setRemoved(false);
        } else {
            crash.setDateUpdated(new Date());
            crash.setUpdatedBy(user);
            Crash dbCrash = super.get(crash.getId());
            deleteRemovedVehicles(dbCrash, crash);
            deleteRemovedCasualties(dbCrash, crash);
        }
        setCrashParams(crash);
        return super.save(crash);
    }

    private void saveCrashVehicles(Crash crash, User user) {
        if (crash.getVehicles() != null) {
            for (Vehicle vehicle : crash.getVehicles()) {
                saveVehicleDriver(vehicle, user);
                if (vehicle.getDateCreated() == null) {
                    vehicle.setDateCreated(new Date());
                    vehicle.setCreatedBy(user);
                    vehicle.setId(null);
                } else {
                    vehicle.setDateUpdated(new Date());
                    vehicle.setUpdatedBy(user);
                }
                setVehicleParams(vehicle);
                Vehicle savedVehicle = vehicleManager.save(vehicle);
                if (vehicle.getId() == null) {
                    vehicle.setId(savedVehicle.getId());
                }
            }
        }
    }

    private void saveVehicleDriver(Vehicle vehicle, User user) {
        if (vehicle.getDriver() != null) {
            Driver driver = vehicle.getDriver();
            if (driver.getDateCreated() == null) {
                driver.setDateCreated(new Date());
                driver.setCreatedBy(user);
                driver.setId(null);
            } else {
                driver.setDateUpdated(new Date());
                driver.setUpdatedBy(user);
            }
            driver = driverManager.save(driver);
            vehicle.setDriver(driver);
        }
    }

    private void saveCrashCasualties(Crash crash, User user) {
        if (crash.getCasualties() != null) {
            for (Casualty casualty : crash.getCasualties()) {
                if (casualty.getDateCreated() == null) {
                    casualty.setDateCreated(new Date());
                    casualty.setCreatedBy(user);
                    casualty.setId(null);
                } else {
                    casualty.setDateUpdated(new Date());
                    casualty.setUpdatedBy(user);
                }
                setCasualtyParams(casualty);
                casualtyManager.save(casualty);
            }
        }
    }

    private void setCrashParams(Crash crash) {
        if (crash.getCollisionType() != null && crash.getCollisionType().getId() != null) {
            crash.setCollisionType(collisionTypeManager.get(crash
                    .getCollisionType().getId()));
        }
        if (crash.getCrashSeverity() != null && crash.getCrashSeverity().getId() != null) {
            crash.setCrashSeverity(crashSeverityManager.get(crash
                    .getCrashSeverity().getId()));
        }
        if (crash.getJunctionType() != null && crash.getJunctionType().getId() != null) {
            crash.setJunctionType(junctionTypeManager.get(crash
                    .getJunctionType().getId()));
        }
        if (crash.getCrashCause() != null && crash.getCrashCause().getId() != null) {
            crash.setCrashCause(crashCauseManager.get(crash
                    .getCrashCause().getId()));
        }
        if (crash.getPoliceStation() != null && crash.getPoliceStation().getId() != null) {
            crash.setPoliceStation(policeStationManager.get(crash
                    .getPoliceStation().getId()));
        }
        if (crash.getRoadSurface() != null && crash.getRoadSurface().getId() != null) {
            crash.setRoadSurface(roadSurfaceManager.get(crash.getRoadSurface()
                    .getId()));
        }
        if (crash.getRoadwayCharacter() != null && crash.getRoadwayCharacter().getId() != null) {
            crash.setRoadwayCharacter(roadwayCharacterManager.get(crash
                    .getRoadwayCharacter().getId()));
        }
        if (crash.getSurfaceCondition() != null && crash.getSurfaceCondition().getId() != null) {
            crash.setSurfaceCondition(surfaceConditionManager.get(crash
                    .getSurfaceCondition().getId()));
        }
        if (crash.getSurfaceType() != null && crash.getSurfaceType().getId() != null) {
            crash.setSurfaceType(surfaceTypeManager.get(crash.getSurfaceType()
                    .getId()));
        }
        if (crash.getVehicleFailureType() != null && crash.getVehicleFailureType().getId() != null) {
            crash.setVehicleFailureType(vehicleFailureTypeManager.get(crash
                    .getVehicleFailureType().getId()));
        }
        if (crash.getWeather() != null && crash.getWeather().getId() != null) {
            crash.setWeather(weatherManager.get(crash.getWeather().getId()));
        }
        setCrashWeight(crash);
        setGpsCoordinates(crash);
    }

    private void setCrashWeight(Crash crash) {
        BigDecimal weight = new BigDecimal(0);
        if (crash.getCrashSeverity() != null) {
            weight  = weight.add(crash.getCrashSeverity().getWeight());
        }
        if (crash.getCasualties() != null) {
            for(Casualty casualty : crash.getCasualties()) {
                if (casualty.getCasualtyType() != null) {
                    weight = weight.add(casualty.getCasualtyType().getWeight());
                }
            }
        }
        crash.setWeight(weight);
    }

    private void setGpsCoordinates(Crash crash) {
        if (GisHelper.isValidLatitude(crash.getLatitude())
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
        Map<String, List> referenceData = new HashMap<>();
        List<District> districts = districtManager.getAllDistinct();
        Collections.sort(districts);
        List<PoliceStation> policeStations = policeStationManager.getAllDistinct();
        Collections.sort(policeStations);
        referenceData.put("districts", districts);
        referenceData.put("policeStations", policeStations);
        referenceData.put("weathers", weatherManager.getAllDistinct());
        referenceData.put("crashCauses", crashCauseManager.getAllDistinct());
        referenceData.put("roadSurfaces", roadSurfaceManager.getAllDistinct());
        referenceData.put("surfaceTypes", surfaceTypeManager.getAllDistinct());
        referenceData.put("vehicleTypes", vehicleTypeManager.getAllDistinct());
        referenceData.put("junctionTypes", junctionTypeManager.getAllDistinct());
        referenceData.put("casualtyTypes", casualtyTypeManager.getAllDistinct());
        referenceData.put("collisionTypes", collisionTypeManager.getAllDistinct());
        referenceData.put("crashSeverities", crashSeverityManager.getAllDistinct());
        referenceData.put("casualtyClasses", casualtyClassManager.getAllDistinct());
        referenceData.put("surfaceConditions", surfaceConditionManager.getAllDistinct());
        referenceData.put("roadwayCharacters", roadwayCharacterManager.getAllDistinct());
        referenceData.put("vehicleFailureTypes", vehicleFailureTypeManager.getAllDistinct());
        return referenceData;
    }

    @Override
    public Map<String, List> getOrderedRefData() {
        Map<String, List> refData = this.getReferenceData();
        for (String key : refData.keySet()) {
            if (!key.equalsIgnoreCase("crashSeverities") && !key.equalsIgnoreCase("casualtyTypes")) {
                Collections.sort(refData.get(key));
            }
        }
        refData.put("genders", lookupManager.getAllGenders());
        refData.put("ageRanges", lookupManager.getAllAgeRanges());
        refData.put("timeRanges", lookupManager.getAllTimeRanges());
        refData.put("licenseTypes", lookupManager.getAllLicenseTypes());
        refData.put("casualtyTypes", casualtyTypeManager.getAllDistinct());
        refData.put("casualtyClass", casualtyClassManager.getAllDistinct());
        refData.put("crashWeightRanges", lookupManager.getAllWeightRanges());
        refData.put("beltUsedOptions", lookupManager.getAllBeltUsedOptions());
        return refData;
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
        List<Long> casualtyIds = new ArrayList<>();
        for (Casualty casualty : crash.getCasualties()) {
            if (casualty.getVehicle() != null
                    && casualty.getVehicle().equals(vehicle)) {
                casualtyIds.add(casualty.getId());
            }
        }
        removeCasualtiesFromCrash(crash, casualtyIds);
    }

    private void removeCasualtiesFromCrash(Crash crash, List<Long> casualtyIds) {
        for (Long casualtyId : casualtyIds) {
            removeCasualtyFromCrash(crash, casualtyId);
        }
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

        List<Vehicle> vehiclesToDelete = new ArrayList<>();
        ListUtil<Vehicle> vehicleUtil = new ListUtil<>();
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

        List<Casualty> casualtiesToDelete = new ArrayList<>();
        ListUtil<Casualty> casualtyUtil = new ListUtil<>();
        for (Casualty casualty : dbCrash.getCasualties()) {
            if (!casualtyUtil.itemExistsInList(casualty,
                    crashInEdit.getCasualties())) {
                casualtiesToDelete.add(casualty);
            }
        }
        return casualtiesToDelete;
    }

    @Override
    public void generateCrashDataExcel(List<Crash> crashes, String filename) throws IOException {
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

    @Override
    public List<Crash> findByTarNo(String tarNo) {
        return crashDao.findByTarNo(tarNo);
    }
}
