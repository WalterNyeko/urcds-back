package com.sweroad.service.impl;

import com.mysql.jdbc.StringUtils;
import com.sweroad.dao.CrashDao;
import com.sweroad.model.*;
import com.sweroad.query.CrashQuery;
import com.sweroad.query.CrashSearch;
import com.sweroad.service.CrashManager;
import com.sweroad.service.CrashQueryManager;
import com.sweroad.service.GenericManager;
import com.sweroad.service.LookupManager;
import com.sweroad.util.DateUtil;
import com.sweroad.util.GenericManagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by Frank on 12/16/14.
 */
@Service("crashQueryManager")
public class CrashQueryManagerImpl implements CrashQueryManager {
    @Autowired
    private CrashDao crashDao;
    @Autowired
    private CrashManager crashManager;
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
    private GenericManager<PoliceStation, Long> policeStationManager;
    @Autowired
    private GenericManager<District, Long> districtManager;
    @Autowired
    private GenericManager<VehicleType, Long> vehicleTypeManager;
    @Autowired
    private GenericManager<CasualtyClass, Long> casualtyClassManager;
    @Autowired
    private GenericManager<CasualtyType, Long> casualtyTypeManager;
    @Autowired
    private LookupManager lookupManager;

    @Override
    public List<Crash> getCrashesByQuery(CrashQuery crashQuery) {
        return crashDao.findCrashesByQueryCrash(crashQuery);
    }

    @Override
    public void processCrashSearch(CrashSearch crashSearch) throws ParseException {
        crashSearch.setCrashSeverities(GenericManagerHelper.filterForCrashSearch(crashSearch.getCrashSeverities(), crashSeverityManager));
        crashSearch.setCollisionTypes(GenericManagerHelper.filterForCrashSearch(crashSearch.getCollisionTypes(), collisionTypeManager));
        crashSearch.setCrashCauses(GenericManagerHelper.filterForCrashSearch(crashSearch.getCrashCauses(), crashCauseManager));
        crashSearch.setVehicleFailureTypes(GenericManagerHelper.filterForCrashSearch(crashSearch.getVehicleFailureTypes(), vehicleFailureTypeManager));
        crashSearch.setWeathers(GenericManagerHelper.filterForCrashSearch(crashSearch.getWeathers(), weatherManager));
        crashSearch.setSurfaceConditions(GenericManagerHelper.filterForCrashSearch(crashSearch.getSurfaceConditions(), surfaceConditionManager));
        crashSearch.setRoadSurfaces(GenericManagerHelper.filterForCrashSearch(crashSearch.getRoadSurfaces(), roadSurfaceManager));
        crashSearch.setSurfaceTypes(GenericManagerHelper.filterForCrashSearch(crashSearch.getSurfaceTypes(), surfaceTypeManager));
        crashSearch.setRoadwayCharacters(GenericManagerHelper.filterForCrashSearch(crashSearch.getRoadwayCharacters(), roadwayCharacterManager));
        crashSearch.setJunctionTypes(GenericManagerHelper.filterForCrashSearch(crashSearch.getJunctionTypes(), junctionTypeManager));
        crashSearch.setPoliceStations(GenericManagerHelper.filterForCrashSearch(crashSearch.getPoliceStations(), policeStationManager));
        crashSearch.setDistricts(GenericManagerHelper.filterForCrashSearch(crashSearch.getDistricts(), districtManager));
        crashSearch.setVehicleTypes(GenericManagerHelper.filterForCrashSearch(crashSearch.getVehicleTypes(), vehicleTypeManager));
        crashSearch.setDriverLicenseTypes(lookupManager.getFilteredLicenseTypes(crashSearch.getDriverLicenseTypes()));
        crashSearch.setDriverGenders(lookupManager.getFilteredGenders(crashSearch.getDriverGenders()));
        crashSearch.setDriverAgeRanges(lookupManager.getFilteredAgeRanges(crashSearch.getDriverAgeRanges()));
        crashSearch.setDriverBeltUsedOptions(lookupManager.getFilteredBeltUsedOptions(crashSearch.getDriverBeltUsedOptions()));
        crashSearch.setDriverCasualtyTypes(GenericManagerHelper.filterForCrashSearch(crashSearch.getDriverCasualtyTypes(), casualtyTypeManager));
        crashSearch.setCasualtyClasses(GenericManagerHelper.filterForCrashSearch(crashSearch.getCasualtyClasses(), casualtyClassManager));
        crashSearch.setCasualtyTypes(GenericManagerHelper.filterForCrashSearch(crashSearch.getCasualtyTypes(), casualtyTypeManager));
        crashSearch.setCasualtyGenders(lookupManager.getFilteredGenders(crashSearch.getCasualtyGenders()));
        crashSearch.setCasualtyAgeRanges(lookupManager.getFilteredAgeRanges(crashSearch.getCasualtyAgeRanges()));
        crashSearch.setCasualtyBeltUsedOptions(lookupManager.getFilteredBeltUsedOptions(crashSearch.getCasualtyBeltUsedOptions()));
        processDates(crashSearch);
    }

    @Override
    public Map<String, List> getCrashQueryReferenceData() {
        Map<String, List> queryCrashReferenceData = crashManager.getReferenceData();
        queryCrashReferenceData.put("licenseTypes", lookupManager.getAllLicenseTypes());
        queryCrashReferenceData.put("genders", lookupManager.getAllGenders());
        queryCrashReferenceData.put("beltUseds", lookupManager.getAllBeltUsedOptions());
        queryCrashReferenceData.put("ageRanges", lookupManager.getAllAgeRanges());
        return queryCrashReferenceData;
    }

    private void processDates(CrashSearch crashSearch) throws ParseException {
        if (!StringUtils.isNullOrEmpty(crashSearch.getStartDateString())) {
            crashSearch.setStartDate(DateUtil.convertStringToDate(crashSearch.getStartDateString()));
        }
        if (!StringUtils.isNullOrEmpty(crashSearch.getEndDateString())) {
            crashSearch.setEndDate(DateUtil.convertStringToDate(crashSearch.getEndDateString()));
        }
    }
}