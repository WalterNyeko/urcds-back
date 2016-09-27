package com.sweroad.service.impl;

import com.sweroad.Constants;
import com.sweroad.dao.CrashDao;
import com.sweroad.model.*;
import com.sweroad.service.*;
import com.sweroad.util.GisHelper;
import com.sweroad.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service("crashService")
public class CrashServiceImpl extends GenericManagerImpl<Crash, Long> implements
        CrashService {

    private CrashDao crashDao;
    @Autowired
    private UserManager userManager;
    @Autowired
    private LookupManager lookupManager;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private CasualtyService casualtyService;
    @Autowired
    private CrashExcelService crashExcelService;
    @Autowired
    private GenericManager<Driver, Long> driverManager;
    @Autowired
    private GenericManager<Weather, Long> weatherManager;
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
    public CrashServiceImpl(CrashDao crashDao) {
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
        final List<Crash> crashes = new ArrayList<>();
        this.getAllDistinct().forEach(crash -> {
            if (ids.contains(crash.getId())) {
                crashes.add(crash);
            }
        });
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
            crash.getVehicles().forEach(vehicle -> this.saveVehicle(vehicle, user));
        }
    }

    private void saveVehicle(Vehicle vehicle, User user) {
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
        Vehicle savedVehicle = vehicleService.save(vehicle);
        if (vehicle.getId() == null) {
            vehicle.setId(savedVehicle.getId());
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
            crash.getCasualties().forEach(casualty -> this.saveCasualty(casualty, user));
        }
    }

    private void saveCasualty(Casualty casualty, User user) {
        if (casualty.getDateCreated() == null) {
            casualty.setDateCreated(new Date());
            casualty.setCreatedBy(user);
            casualty.setId(null);
        } else {
            casualty.setDateUpdated(new Date());
            casualty.setUpdatedBy(user);
        }
        setCasualtyParams(casualty);
        casualtyService.save(casualty);
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
        List<District> districts = districtManager.getAllActive();
        Collections.sort(districts);
        List<PoliceStation> policeStations = policeStationManager.getAllActive();
        Collections.sort(policeStations);
        referenceData.put("districts", districts);
        referenceData.put("policeStations", policeStations);
        referenceData.put("weathers", weatherManager.getAllActive());
        referenceData.put("crashCauses", crashCauseManager.getAllActive());
        referenceData.put("roadSurfaces", roadSurfaceManager.getAllActive());
        referenceData.put("surfaceTypes", surfaceTypeManager.getAllActive());
        referenceData.put("vehicleTypes", vehicleTypeManager.getAllActive());
        referenceData.put("junctionTypes", junctionTypeManager.getAllActive());
        referenceData.put("casualtyTypes", casualtyTypeManager.getAllActive());
        referenceData.put("collisionTypes", collisionTypeManager.getAllActive());
        referenceData.put("crashSeverities", crashSeverityManager.getAllActive());
        referenceData.put("casualtyClasses", casualtyClassManager.getAllActive());
        referenceData.put("tristates", lookupManager.getAllQuadstateOptions(false));
        referenceData.put("quadstates", lookupManager.getAllQuadstateOptions(true));
        referenceData.put("surfaceConditions", surfaceConditionManager.getAllActive());
        referenceData.put("roadwayCharacters", roadwayCharacterManager.getAllActive());
        referenceData.put("vehicleFailureTypes", vehicleFailureTypeManager.getAllActive());
        return referenceData;
    }

    @Override
    public Map<String, List> getOrderedRefData() {
        Map<String, List> refData = this.getReferenceData();
        refData.keySet().forEach(key -> this.sortRefData(refData, key));
        refData.put("genders", lookupManager.getAllGenders());
        refData.put("ageRanges", lookupManager.getAllAgeRanges());
        refData.put("timeRanges", lookupManager.getAllTimeRanges());
        refData.put("licenseTypes", lookupManager.getAllLicenseTypes());
        refData.put("casualtyTypes", casualtyTypeManager.getAllActive());
        refData.put("casualtyClass", casualtyClassManager.getAllActive());
        refData.put("crashWeightRanges", lookupManager.getAllWeightRanges());
        refData.put("beltUsedOptions", lookupManager.getAllBeltUsedOptions());
        return refData;
    }

    private void sortRefData(Map<String, List> refData, String key) {
        if (!key.equalsIgnoreCase("crashSeverities") && !key.equalsIgnoreCase("casualtyTypes")) {
            Collections.sort(refData.get(key));
        }
    }

    @Override
    public void removeCasualtyFromCrash(Crash crash, Long casualtyId) {
        Optional<Casualty> casualty = crash.getCasualties()
                .stream()
                .filter(c -> casualtyId.equals(c.getId()))
                .findFirst();
        if (casualty.isPresent()) {
            crash.getCasualties().remove(casualty.get());
        }
    }

    @Override
    public void removeVehicleFromCrash(Crash crash, Long vehicleId) {
        Optional<Vehicle> vehicle = crash.getVehicles()
                .stream()
                .filter(v -> vehicleId.equals(v.getId()))
                .findFirst();
        if (vehicle.isPresent()) {
            this.removeVehicleCasualtiesFromCrash(crash, vehicle.get());
            crash.getVehicles().remove(vehicle.get());
        }
    }

    private void removeVehicleCasualtiesFromCrash(Crash crash, Vehicle vehicle) {
        List<Long> casualtyIds =  crash.getCasualties()
                .stream()
                .filter(casualty -> casualty.getVehicle() != null && casualty.getVehicle().equals(vehicle))
                .map(Casualty::getId)
                .collect(Collectors.toList());
        casualtyIds.forEach(id -> this.removeCasualtyFromCrash(crash, id));
    }

    private void deleteRemovedVehicles(Crash dbCrash, Crash crash) {
        ListUtil<Vehicle> vehicleUtil = new ListUtil<>();
        List<Vehicle> vehiclesToDelete = dbCrash.getVehicles()
                .stream()
                .filter(vehicle -> !vehicleUtil.itemExistsInList(vehicle, crash.getVehicles()))
                .collect(Collectors.toList());
        vehiclesToDelete.forEach(vehicle -> {
            dbCrash.getVehicles().remove(vehicle);
            vehicleService.remove(vehicle);
            driverManager.remove(vehicle.getDriver());
        });
    }

    private void deleteRemovedCasualties(Crash dbCrash, Crash crash) {
        ListUtil<Casualty> casualtyUtil = new ListUtil<>();
        List<Casualty> casualtiesToDelete = dbCrash.getCasualties()
                .stream()
                .filter(casualty -> !casualtyUtil.itemExistsInList(casualty, crash.getCasualties()))
                .collect(Collectors.toList());
        casualtiesToDelete.forEach(casualty -> {
            dbCrash.getCasualties().remove(casualty);
            casualtyService.remove(casualty);
        });
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
