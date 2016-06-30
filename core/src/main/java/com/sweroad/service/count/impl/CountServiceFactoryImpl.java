package com.sweroad.service.count.impl;

import com.sweroad.model.AttributeType;
import com.sweroad.service.count.CountAttributeService;
import com.sweroad.service.count.CountServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Frank on 7/1/16.
 */

@Service("countServiceFactory")
public class CountServiceFactoryImpl implements CountServiceFactory {

    @Autowired
    private CountAttributeService countWeatherService;

    @Autowired
    private CountAttributeService countDistrictService;

    @Autowired
    private CountAttributeService countCrashCauseService;

    @Autowired
    private CountAttributeService countRoadSurfaceService;

    @Autowired
    private CountAttributeService countSurfaceTypeService;

    @Autowired
    private CountAttributeService countJunctionTypeService;

    @Autowired
    private CountAttributeService countCollisionTypeService;

    @Autowired
    private CountAttributeService countCrashSeverityService;

    @Autowired
    private CountAttributeService countPoliceStationService;

    @Autowired
    private CountAttributeService countRoadwayCharacterService;

    @Autowired
    private CountAttributeService countSurfaceConditionService;

    @Autowired
    private CountAttributeService countVehicleFailureTypeService;

    public CountAttributeService getCountService(AttributeType attributeType) {
        switch(attributeType) {
            case WEATHER:
                return countWeatherService;
            case DISTRICT:
                return countDistrictService;
            case CRASH_CAUSE:
                return countCrashCauseService;
            case ROAD_SURFACE:
                return countRoadSurfaceService;
            case SURFACE_TYPE:
                return countSurfaceTypeService;
            case JUNCTION_TYPE:
                return countJunctionTypeService;
            case COLLISION_TYPE:
                return countCollisionTypeService;
            case CRASH_SEVERITY:
                return countCrashSeverityService;
            case POLICE_STATION:
                return countPoliceStationService;
            case ROADWAY_CHARACTER:
                return countRoadwayCharacterService;
            case SURFACE_CONDITION:
                return countSurfaceConditionService;
            case VEHICLE_FAILURE_TYPE:
                return countVehicleFailureTypeService;
            default:
                throw new UnsupportedOperationException("This attribute type is not supported");
        }
    }
}
