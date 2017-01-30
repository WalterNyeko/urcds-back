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
    private UserManager userManager;
    @Autowired
    private CrashService crashService;
    @Autowired
    private LookupService lookupService;
    @Autowired
    private GenericManager<Query, Long> queryService;
    @Autowired
    private GenericManager<Weather, Long> weatherService;
    @Autowired
    private GenericManager<District, Long> districtService;
    @Autowired
    private GenericManager<CrashCause, Long> crashCauseService;
    @Autowired
    private GenericManager<RoadSurface, Long> roadSurfaceService;
    @Autowired
    private GenericManager<SurfaceType, Long> surfaceTypeService;
    @Autowired
    private GenericManager<VehicleType, Long> vehicleTypeService;
    @Autowired
    private GenericManager<CasualtyType, Long> casualtyTypeService;
    @Autowired
    private GenericManager<JunctionType, Long> junctionTypeService;
    @Autowired
    private GenericManager<CasualtyClass, Long> casualtyClassService;
    @Autowired
    private GenericManager<PoliceStation, Long> policeStationService;
    @Autowired
    private GenericManager<CrashSeverity, Long> crashSeverityService;
    @Autowired
    private GenericManager<CollisionType, Long> collisionTypeService;
    @Autowired
    private GenericManager<SurfaceCondition, Long> surfaceConditionService;
    @Autowired
    private GenericManager<RoadwayCharacter, Long> roadwayCharacterService;
    @Autowired
    private GenericManager<VehicleFailureType, Long> vehicleFailureTypeService;

    @Override
    public List<Crash> getCrashesByQuery(CrashQuery crashQuery) {
        return crashDao.findCrashesByQueryCrash(crashQuery);
    }

    @Override
    public void processCrashSearch(CrashSearch crashSearch) throws ParseException {
        crashSearch.setCrashSeverities(GenericManagerHelper.filterForCrashSearch(crashSearch.getCrashSeverities(), crashSeverityService));
        crashSearch.setCollisionTypes(GenericManagerHelper.filterForCrashSearch(crashSearch.getCollisionTypes(), collisionTypeService));
        crashSearch.setCrashCauses(GenericManagerHelper.filterForCrashSearch(crashSearch.getCrashCauses(), crashCauseService));
        crashSearch.setVehicleFailureTypes(GenericManagerHelper.filterForCrashSearch(crashSearch.getVehicleFailureTypes(), vehicleFailureTypeService));
        crashSearch.setWeathers(GenericManagerHelper.filterForCrashSearch(crashSearch.getWeathers(), weatherService));
        crashSearch.setSurfaceConditions(GenericManagerHelper.filterForCrashSearch(crashSearch.getSurfaceConditions(), surfaceConditionService));
        crashSearch.setRoadSurfaces(GenericManagerHelper.filterForCrashSearch(crashSearch.getRoadSurfaces(), roadSurfaceService));
        crashSearch.setSurfaceTypes(GenericManagerHelper.filterForCrashSearch(crashSearch.getSurfaceTypes(), surfaceTypeService));
        crashSearch.setRoadwayCharacters(GenericManagerHelper.filterForCrashSearch(crashSearch.getRoadwayCharacters(), roadwayCharacterService));
        crashSearch.setJunctionTypes(GenericManagerHelper.filterForCrashSearch(crashSearch.getJunctionTypes(), junctionTypeService));
        crashSearch.setPoliceStations(GenericManagerHelper.filterForCrashSearch(crashSearch.getPoliceStations(), policeStationService));
        crashSearch.setDistricts(GenericManagerHelper.filterForCrashSearch(crashSearch.getDistricts(), districtService));
        crashSearch.setVehicleTypes(GenericManagerHelper.filterForCrashSearch(crashSearch.getVehicleTypes(), vehicleTypeService));
        crashSearch.setDriverLicenseTypes(lookupService.getFilteredLicenseTypes(crashSearch.getDriverLicenseTypeValues()));
        crashSearch.setDriverGenders(lookupService.getFilteredGenders(crashSearch.getDriverGenders()));
        crashSearch.setDriverAgeRanges(lookupService.getFilteredAgeRanges(crashSearch.getDriverAgeRanges()));
        crashSearch.setDriverBeltUsedOptions(lookupService.getFilteredBeltUsedOptions(crashSearch.getDriverBeltUsedOptionValues()));
        crashSearch.setDriverCasualtyTypes(GenericManagerHelper.filterForCrashSearch(crashSearch.getDriverCasualtyTypes(), casualtyTypeService));
        crashSearch.setCasualtyClasses(GenericManagerHelper.filterForCrashSearch(crashSearch.getCasualtyClasses(), casualtyClassService));
        crashSearch.setCasualtyTypes(GenericManagerHelper.filterForCrashSearch(crashSearch.getCasualtyTypes(), casualtyTypeService));
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
        return queryService.getAllDistinct().stream().filter(query -> query.getOwner().equals(userManager.getCurrentUser())).collect(Collectors.toList());
    }

    @Override
    public void saveQuery(Query query) {
        if (query.getId() == null) {
            query.setDateCreated(new Date());
        } else {
            query.setDateUpdated(new Date());
        }
        query.setOwner(userManager.getCurrentUser());
        queryService.save(query);
    }

    @Override
    public Query getQueryById(Long queryId) {
        return queryService.get(queryId);
    }

    @Override
    public void removeQueryById(Long queryId) {
        queryService.remove(queryId);
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