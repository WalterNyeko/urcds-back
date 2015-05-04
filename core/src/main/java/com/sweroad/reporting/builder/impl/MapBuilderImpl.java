package com.sweroad.reporting.builder.impl;

import com.sweroad.model.*;
import com.sweroad.reporting.builder.MapBuilder;
import com.sweroad.service.GenericManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Frank on 5/3/15.
 */
@Service("mapBuilder¬")
public class MapBuilderImpl implements MapBuilder {

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

    public Map<CollisionType, Map<CrashSeverity, Integer>> buildCollisionTypeMap(List<Crash> crashes) {
        Map<CollisionType, Map<CrashSeverity, Integer>> collisionTypeMap = new HashMap<CollisionType, Map<CrashSeverity, Integer>>();
        List<CollisionType> collisionTypes = collisionTypeManager.getAllDistinct();
        List<CrashSeverity> crashSeverities = crashSeverityManager.getAllDistinct();
        Collections.sort(collisionTypes);
        Collections.sort(crashSeverities);
        for (CollisionType collisionType : collisionTypes) {
            collisionTypeMap.put(collisionType, new HashMap<CrashSeverity, Integer>());
            for (CrashSeverity crashSeverity : crashSeverities) {
                collisionTypeMap.get(collisionType).put(crashSeverity, 0);
            }
        }
        for (Crash crash : crashes) {
            if (crash.getCollisionType() != null && crash.getCrashSeverity() != null) {
                Map<CrashSeverity, Integer> crashSeverityMap = collisionTypeMap.get(crash.getCollisionType());
                Integer count = crashSeverityMap.get(crash.getCrashSeverity()) + 1;
                crashSeverityMap.put(crash.getCrashSeverity(), count);
            }
        }
        return collisionTypeMap;
    }

    public Map<CrashCause, Map<CrashSeverity, Integer>> buildCrashCauseMap(List<Crash> crashes) {
        Map<CrashCause, Map<CrashSeverity, Integer>> crashCauseMap = new HashMap<CrashCause, Map<CrashSeverity, Integer>>();
        List<CrashCause> crashCauses = crashCauseManager.getAllDistinct();
        List<CrashSeverity> crashSeverities = crashSeverityManager.getAllDistinct();
        Collections.sort(crashCauses);
        Collections.sort(crashSeverities);
        for (CrashCause crashCause : crashCauses) {
            crashCauseMap.put(crashCause, new HashMap<CrashSeverity, Integer>());
            for (CrashSeverity crashSeverity : crashSeverities) {
                crashCauseMap.get(crashCause).put(crashSeverity, 0);
            }
        }
        for (Crash crash : crashes) {
            if (crash.getCrashCause() != null && crash.getCrashSeverity() != null) {
                Map<CrashSeverity, Integer> crashSeverityMap = crashCauseMap.get(crash.getCrashCause());
                Integer count = crashSeverityMap.get(crash.getCrashSeverity()) + 1;
                crashSeverityMap.put(crash.getCrashSeverity(), count);
            }
        }
        return crashCauseMap;
    }

    public Map<VehicleFailureType, Map<CrashSeverity, Integer>> buildVehicleFailureTypeMap(List<Crash> crashes) {
        Map<VehicleFailureType, Map<CrashSeverity, Integer>> vehicleFailureTypeMap = new HashMap<VehicleFailureType, Map<CrashSeverity, Integer>>();
        List<CrashSeverity> crashSeverities = crashSeverityManager.getAllDistinct();
        List<VehicleFailureType> vehicleFailureTypes = vehicleFailureTypeManager.getAllDistinct();
        Collections.sort(crashSeverities);
        Collections.sort(vehicleFailureTypes);
        for (VehicleFailureType vehicleFailureType : vehicleFailureTypes) {
            vehicleFailureTypeMap.put(vehicleFailureType, new HashMap<CrashSeverity, Integer>());
            for (CrashSeverity crashSeverity : crashSeverities) {
                vehicleFailureTypeMap.get(vehicleFailureType).put(crashSeverity, 0);
            }
        }
        for (Crash crash : crashes) {
            if (crash.getVehicleFailureType() != null && crash.getCrashSeverity() != null) {
                Map<CrashSeverity, Integer> crashSeverityMap = vehicleFailureTypeMap.get(crash.getVehicleFailureType());
                Integer count = crashSeverityMap.get(crash.getCrashSeverity()) + 1;
                crashSeverityMap.put(crash.getCrashSeverity(), count);
            }
        }
        return vehicleFailureTypeMap;
    }

    public Map<Weather, Map<CrashSeverity, Integer>> buildWeatherMap(List<Crash> crashes) {
        Map<Weather, Map<CrashSeverity, Integer>> weatherMap = new HashMap<Weather, Map<CrashSeverity, Integer>>();
        List<Weather> weathers = weatherManager.getAllDistinct();
        List<CrashSeverity> crashSeverities = crashSeverityManager.getAllDistinct();
        Collections.sort(weathers);
        Collections.sort(crashSeverities);
        for (Weather weather : weathers) {
            weatherMap.put(weather, new HashMap<CrashSeverity, Integer>());
            for (CrashSeverity crashSeverity : crashSeverities) {
                weatherMap.get(weather).put(crashSeverity, 0);
            }
        }
        for (Crash crash : crashes) {
            if (crash.getWeather() != null && crash.getCrashSeverity() != null) {
                Map<CrashSeverity, Integer> crashSeverityMap = weatherMap.get(crash.getWeather());
                Integer count = crashSeverityMap.get(crash.getCrashSeverity()) + 1;
                crashSeverityMap.put(crash.getCrashSeverity(), count);
            }
        }
        return weatherMap;
    }

    public Map<SurfaceCondition, Map<CrashSeverity, Integer>> buildSurfaceConditionMap(List<Crash> crashes) {
        Map<SurfaceCondition, Map<CrashSeverity, Integer>> surfaceConditionMap = new HashMap<SurfaceCondition, Map<CrashSeverity, Integer>>();
        List<CrashSeverity> crashSeverities = crashSeverityManager.getAllDistinct();
        List<SurfaceCondition> surfaceConditions = surfaceConditionManager.getAllDistinct();
        Collections.sort(crashSeverities);
        Collections.sort(surfaceConditions);
        for (SurfaceCondition surfaceCondition : surfaceConditions) {
            surfaceConditionMap.put(surfaceCondition, new HashMap<CrashSeverity, Integer>());
            for (CrashSeverity crashSeverity : crashSeverities) {
                surfaceConditionMap.get(surfaceCondition).put(crashSeverity, 0);
            }
        }
        for (Crash crash : crashes) {
            if (crash.getSurfaceCondition() != null && crash.getCrashSeverity() != null) {
                Map<CrashSeverity, Integer> crashSeverityMap = surfaceConditionMap.get(crash.getSurfaceCondition());
                Integer count = crashSeverityMap.get(crash.getCrashSeverity()) + 1;
                crashSeverityMap.put(crash.getCrashSeverity(), count);
            }
        }
        return surfaceConditionMap;
    }

    public Map<RoadSurface, Map<CrashSeverity, Integer>> buildRoadSurfaceMap(List<Crash> crashes) {
        Map<RoadSurface, Map<CrashSeverity, Integer>> roadSurfaceMap = new HashMap<RoadSurface, Map<CrashSeverity, Integer>>();
        List<RoadSurface> roadSurfaces = roadSurfaceManager.getAllDistinct();
        List<CrashSeverity> crashSeverities = crashSeverityManager.getAllDistinct();
        Collections.sort(roadSurfaces);
        Collections.sort(crashSeverities);
        for (RoadSurface roadSurface : roadSurfaces) {
            roadSurfaceMap.put(roadSurface, new HashMap<CrashSeverity, Integer>());
            for (CrashSeverity crashSeverity : crashSeverities) {
                roadSurfaceMap.get(roadSurface).put(crashSeverity, 0);
            }
        }
        for (Crash crash : crashes) {
            if (crash.getRoadSurface() != null && crash.getCrashSeverity() != null) {
                Map<CrashSeverity, Integer> crashSeverityMap = roadSurfaceMap.get(crash.getRoadSurface());
                Integer count = crashSeverityMap.get(crash.getCrashSeverity()) + 1;
                crashSeverityMap.put(crash.getCrashSeverity(), count);
            }
        }
        return roadSurfaceMap;
    }

    public Map<SurfaceType, Map<CrashSeverity, Integer>> buildSurfaceTypeMap(List<Crash> crashes) {
        Map<SurfaceType, Map<CrashSeverity, Integer>> surfaceTypeMap = new HashMap<SurfaceType, Map<CrashSeverity, Integer>>();
        List<SurfaceType> surfaceTypes = surfaceTypeManager.getAllDistinct();
        List<CrashSeverity> crashSeverities = crashSeverityManager.getAllDistinct();
        Collections.sort(surfaceTypes);
        Collections.sort(crashSeverities);
        for (SurfaceType surfaceType : surfaceTypes) {
            surfaceTypeMap.put(surfaceType, new HashMap<CrashSeverity, Integer>());
            for (CrashSeverity crashSeverity : crashSeverities) {
                surfaceTypeMap.get(surfaceType).put(crashSeverity, 0);
            }
        }
        for (Crash crash : crashes) {
            if (crash.getSurfaceType() != null && crash.getCrashSeverity() != null) {
                Map<CrashSeverity, Integer> crashSeverityMap = surfaceTypeMap.get(crash.getSurfaceType());
                Integer count = crashSeverityMap.get(crash.getCrashSeverity()) + 1;
                crashSeverityMap.put(crash.getCrashSeverity(), count);
            }
        }
        return surfaceTypeMap;
    }

    public Map<RoadwayCharacter, Map<CrashSeverity, Integer>> buildRoadwayCharacterMap(List<Crash> crashes) {
        Map<RoadwayCharacter, Map<CrashSeverity, Integer>> roadwayCharacterMap = new HashMap<RoadwayCharacter, Map<CrashSeverity, Integer>>();
        List<CrashSeverity> crashSeverities = crashSeverityManager.getAllDistinct();
        List<RoadwayCharacter> roadwayCharacters = roadwayCharacterManager.getAllDistinct();
        Collections.sort(crashSeverities);
        Collections.sort(roadwayCharacters);
        for (RoadwayCharacter roadwayCharacter : roadwayCharacters) {
            roadwayCharacterMap.put(roadwayCharacter, new HashMap<CrashSeverity, Integer>());
            for (CrashSeverity crashSeverity : crashSeverities) {
                roadwayCharacterMap.get(roadwayCharacter).put(crashSeverity, 0);
            }
        }
        for (Crash crash : crashes) {
            if (crash.getRoadwayCharacter() != null && crash.getCrashSeverity() != null) {
                Map<CrashSeverity, Integer> crashSeverityMap = roadwayCharacterMap.get(crash.getRoadwayCharacter());
                Integer count = crashSeverityMap.get(crash.getCrashSeverity()) + 1;
                crashSeverityMap.put(crash.getCrashSeverity(), count);
            }
        }
        return roadwayCharacterMap;
    }

    public Map<JunctionType, Map<CrashSeverity, Integer>> buildJunctionTypeMap(List<Crash> crashes) {
        Map<JunctionType, Map<CrashSeverity, Integer>> junctionTypeMap = new HashMap<JunctionType, Map<CrashSeverity, Integer>>();
        List<JunctionType> junctionTypes = junctionTypeManager.getAllDistinct();
        List<CrashSeverity> crashSeverities = crashSeverityManager.getAllDistinct();
        Collections.sort(junctionTypes);
        Collections.sort(crashSeverities);
        for (JunctionType junctionType : junctionTypes) {
            junctionTypeMap.put(junctionType, new HashMap<CrashSeverity, Integer>());
            for (CrashSeverity crashSeverity : crashSeverities) {
                junctionTypeMap.get(junctionType).put(crashSeverity, 0);
            }
        }
        for (Crash crash : crashes) {
            if (crash.getJunctionType() != null && crash.getCrashSeverity() != null) {
                Map<CrashSeverity, Integer> crashSeverityMap = junctionTypeMap.get(crash.getJunctionType());
                Integer count = crashSeverityMap.get(crash.getCrashSeverity()) + 1;
                crashSeverityMap.put(crash.getCrashSeverity(), count);
            }
        }
        return junctionTypeMap;
    }
}
