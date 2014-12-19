package com.sweroad.service.impl;

import com.sweroad.dao.CrashDao;
import com.sweroad.model.*;
import com.sweroad.query.CrashQuery;
import com.sweroad.query.CrashSearch;
import com.sweroad.query.Queryable;
import com.sweroad.service.CrashQueryManager;
import com.sweroad.service.GenericManager;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 12/16/14.
 */
@Service("crashQueryManager")
public class CrashQueryManagerImpl implements CrashQueryManager {
    @Autowired
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

    @Override
    public List<Crash> getCrashesByQuery(CrashQuery crashQuery) {
        return crashDao.findCrashesByQueryCrash(crashQuery);
    }

    @Override
    public void processCrashSearch(CrashSearch crashSearch) {
        List<CrashSeverity> crashSeverities = stripNulls(crashSearch.getCrashSeverities());
        crashSearch.setCrashSeverities(
                crashSeverities.size() > 0 ? getFilteredList(crashSeverities, crashSeverityManager)
                        : crashSeverities
        );

        List<CollisionType> collisionTypes = stripNulls(crashSearch.getCollisionTypes());
        crashSearch.setCollisionTypes(
                collisionTypes.size() > 0 ? getFilteredList(collisionTypes, collisionTypeManager) :
                        collisionTypes
        );

        List<CrashCause> crashCauses = stripNulls(crashSearch.getCrashCauses());
        crashSearch.setCrashCauses(
                crashCauses.size() > 0 ? getFilteredList(crashCauses, crashCauseManager)
                        : crashCauses
        );

        List<VehicleFailureType> vehicleFailureTypes = stripNulls(crashSearch.getVehicleFailureTypes());
        crashSearch.setVehicleFailureTypes(
                vehicleFailureTypes.size() > 0 ? getFilteredList(vehicleFailureTypes, vehicleFailureTypeManager)
                        : vehicleFailureTypes
        );

        List<Weather> weathers = stripNulls(crashSearch.getWeathers());
        crashSearch.setWeathers(
                weathers.size() > 0 ? getFilteredList(weathers, weatherManager)
                        : weathers
        );

        List<SurfaceCondition> surfaceConditions = stripNulls(crashSearch.getSurfaceConditions());
        crashSearch.setSurfaceConditions(
                surfaceConditions.size() > 0 ? getFilteredList(surfaceConditions, surfaceConditionManager)
                        : surfaceConditions
        );

        List<RoadSurface> roadSurfaces = stripNulls(crashSearch.getRoadSurfaces());
        crashSearch.setRoadSurfaces(
                roadSurfaces.size() > 0 ? getFilteredList(roadSurfaces, roadSurfaceManager)
                        : roadSurfaces
        );

        List<SurfaceType> surfaceTypes = stripNulls(crashSearch.getSurfaceTypes());
        crashSearch.setSurfaceTypes(
                surfaceTypes.size() > 0 ? getFilteredList(surfaceTypes, surfaceTypeManager)
                        : surfaceTypes
        );

        List<RoadwayCharacter> roadwayCharacters = stripNulls(crashSearch.getRoadwayCharacters());
        crashSearch.setRoadwayCharacters(
                roadwayCharacters.size() > 0 ? getFilteredList(roadwayCharacters, roadwayCharacterManager)
                        : roadwayCharacters
        );

        List<JunctionType> junctionTypes = stripNulls(crashSearch.getJunctionTypes());
        crashSearch.setJunctionTypes(
                junctionTypes.size() > 0 ? getFilteredList(junctionTypes, junctionTypeManager)
                        : junctionTypes
        );

        List<VehicleType> vehicleTypes = stripNulls(crashSearch.getVehicleTypes());
        crashSearch.setVehicleTypes(
                vehicleTypes.size() > 0 ? getFilteredList(vehicleTypes, vehicleTypeManager)
                        : vehicleTypes
        );

        List<CasualtyClass> casualtyClasses = stripNulls(crashSearch.getCasualtyClasses());
        crashSearch.setCasualtyClasses(
                casualtyClasses.size() > 0 ? getFilteredList(casualtyClasses, casualtyTypeManager)
                        : casualtyClasses
        );

        List<CasualtyType> casualtyTypes = stripNulls(crashSearch.getCasualtyTypes());
        crashSearch.setCasualtyTypes(
                casualtyTypes.size() > 0 ? getFilteredList(casualtyTypes, casualtyTypeManager)
                        : casualtyTypes
        );
    }

    private <T extends Queryable> List<T> getFilteredList(List<T> list, GenericManager listManager) {
        List<T> dbList = listManager.getAll();
        List<T> filteredList = new ArrayList<T>();
        for (T t : dbList) {
            for (T y : list) {
                if (t.getId().equals(y.getId())) {
                    filteredList.add(t);
                }
            }
        }
        return filteredList;
    }

    private <T extends Queryable> List<T> stripNulls(List<T> list) {
        List<T> strippedList = new ArrayList<T>();
        if (list != null) {
            for (T t : list) {
                if (t.getId() != null) {
                    strippedList.add(t);
                }
            }
        }
        return strippedList;
    }
}
