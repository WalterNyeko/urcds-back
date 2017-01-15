package com.sweroad.service.impl;

import com.mysql.jdbc.StringUtils;
import com.sweroad.dao.CrashDao;
import com.sweroad.model.*;
import com.sweroad.query.CrashQuery;
import com.sweroad.query.CrashSearch;
import com.sweroad.service.*;
import com.sweroad.util.DateUtil;
import com.sweroad.util.GenericManagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Frank on 12/16/14.
 */
@Service("crashQueryService")
public class CrashQueryServiceImpl implements CrashQueryService {
    @Autowired
    private CrashDao crashDao;
    @Autowired
    private CrashService crashService;
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
    private LookupService lookupService;
    @Autowired
    private GenericManager<Query, Long> queryManager;
    @Autowired
    private UserManager userManager;

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
        crashSearch.setDriverLicenseTypes(lookupService.getFilteredLicenseTypes(crashSearch.getDriverLicenseTypeValues()));
        crashSearch.setDriverGenders(lookupService.getFilteredGenders(crashSearch.getDriverGenders()));
        crashSearch.setDriverAgeRanges(lookupService.getFilteredAgeRanges(crashSearch.getDriverAgeRanges()));
        crashSearch.setDriverBeltUsedOptions(lookupService.getFilteredBeltUsedOptions(crashSearch.getDriverBeltUsedOptionValues()));
        crashSearch.setDriverCasualtyTypes(GenericManagerHelper.filterForCrashSearch(crashSearch.getDriverCasualtyTypes(), casualtyTypeManager));
        crashSearch.setCasualtyClasses(GenericManagerHelper.filterForCrashSearch(crashSearch.getCasualtyClasses(), casualtyClassManager));
        crashSearch.setCasualtyTypes(GenericManagerHelper.filterForCrashSearch(crashSearch.getCasualtyTypes(), casualtyTypeManager));
        crashSearch.setCasualtyGenders(lookupService.getFilteredGenders(crashSearch.getCasualtyGenders()));
        crashSearch.setCasualtyAgeRanges(lookupService.getFilteredAgeRanges(crashSearch.getCasualtyAgeRanges()));
        crashSearch.setCasualtyBeltUsedOptions(lookupService.getFilteredBeltUsedOptions(crashSearch.getCasualtyBeltUsedOptionValues()));
        processDates(crashSearch);
    }

    @Override
    public Map<String, List> getCrashQueryReferenceData() {
        Map<String, List> queryCrashReferenceData = crashService.getReferenceData();
        queryCrashReferenceData.put("genders", lookupService.getAllGenders());
        queryCrashReferenceData.put("ageRanges", lookupService.getAllAgeRanges());
        queryCrashReferenceData.put("quadstates", lookupService.getAllQuadstateOptions(true));
        queryCrashReferenceData.put("tristates", lookupService.getAllQuadstateOptions(false));
        return queryCrashReferenceData;
    }

    @Override
    public List<Query> getCurrentUserQueries() {
        return queryManager.getAllDistinct().stream().filter(query -> query.getOwner().equals(userManager.getCurrentUser())).collect(Collectors.toList());
    }

    @Override
    public void saveQuery(Query query) {
        if (query.getId() == null) {
            query.setDateCreated(new Date());
        } else {
            query.setDateUpdated(new Date());
        }
        query.setOwner(userManager.getCurrentUser());
        queryManager.save(query);
    }

    @Override
    public Query getQueryById(Long queryId) {
        return queryManager.get(queryId);
    }

    @Override
    public void removeQueryById(Long queryId) {
        queryManager.remove(queryId);
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