package com.sweroad.reporting.builder.impl;

import com.sweroad.model.*;
import com.sweroad.reporting.builder.MapBuilder;
import com.sweroad.service.GenericManager;
import com.sweroad.service.LookupManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Frank on 5/3/15.
 */
@Service("mapBuilder")
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
    private GenericManager<VehicleType, Long> vehicleTypeManager;
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
    private LookupManager lookupManager;

    public Map<CollisionType, Map<CrashSeverity, Integer>> buildCollisionTypeMap(List<Crash> crashes) {
        Map<CollisionType, Map<CrashSeverity, Integer>> collisionTypeMap = new HashMap<>();
        List<CollisionType> collisionTypes = collisionTypeManager.getAllDistinct();
        List<CrashSeverity> crashSeverities = crashSeverityManager.getAllDistinct();
        Collections.sort(collisionTypes);
        Collections.sort(crashSeverities);
        for (CollisionType collisionType : collisionTypes) {
            collisionTypeMap.put(collisionType, new HashMap<>());
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

    @Override
    public Map<PoliceStation, Map<CrashSeverity, Integer>> buildPoliceStationMap(List<Crash> crashes) {
        List<PoliceStation> policeStations = policeStationManager.getAllDistinct();
        List<CrashSeverity> crashSeverities = crashSeverityManager.getAllDistinct();
        Collections.sort(policeStations);
        Collections.sort(crashSeverities);
        Map<PoliceStation, Map<CrashSeverity, Integer>> policeStationMap = new HashMap<>();
        for (PoliceStation policeStation : policeStations) {
            policeStationMap.put(policeStation, new HashMap<>());
            for (CrashSeverity crashSeverity : crashSeverities) {
                policeStationMap.get(policeStation).put(crashSeverity, 0);
            }
        }
        for (Crash crash : crashes) {
            if (crash.getPoliceStation() != null && crash.getCrashSeverity() != null) {
                Map<CrashSeverity, Integer> crashSeverityMap = policeStationMap.get(crash.getPoliceStation());
                Integer count = crashSeverityMap.get(crash.getCrashSeverity()) + 1;
                crashSeverityMap.put(crash.getCrashSeverity(), count);
            }
        }
        return policeStationMap;
    }

    public Map<CrashCause, Map<CrashSeverity, Integer>> buildCrashCauseMap(List<Crash> crashes) {
        Map<CrashCause, Map<CrashSeverity, Integer>> crashCauseMap = new HashMap<>();
        List<CrashCause> crashCauses = crashCauseManager.getAllDistinct();
        List<CrashSeverity> crashSeverities = crashSeverityManager.getAllDistinct();
        Collections.sort(crashCauses);
        Collections.sort(crashSeverities);
        for (CrashCause crashCause : crashCauses) {
            crashCauseMap.put(crashCause, new HashMap<>());
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
        Map<VehicleFailureType, Map<CrashSeverity, Integer>> vehicleFailureTypeMap = new HashMap<>();
        List<CrashSeverity> crashSeverities = crashSeverityManager.getAllDistinct();
        List<VehicleFailureType> vehicleFailureTypes = vehicleFailureTypeManager.getAllDistinct();
        Collections.sort(crashSeverities);
        Collections.sort(vehicleFailureTypes);
        for (VehicleFailureType vehicleFailureType : vehicleFailureTypes) {
            vehicleFailureTypeMap.put(vehicleFailureType, new HashMap<>());
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

    @Override
    public Map<VehicleType, Map<CrashSeverity, Integer>> buildVehicleTypeMap(List<Crash> crashes) {
        Map<VehicleType, Map<CrashSeverity, Integer>> vehicleTypeMap = new HashMap<>();
        List<CrashSeverity> crashSeverities = crashSeverityManager.getAllDistinct();
        List<VehicleType> vehicleTypes = vehicleTypeManager.getAllDistinct();
        Collections.sort(crashSeverities);
        Collections.sort(vehicleTypes);
        for (VehicleType vehicleType : vehicleTypes) {
            vehicleTypeMap.put(vehicleType, new HashMap<>());
            for (CrashSeverity crashSeverity : crashSeverities) {
                vehicleTypeMap.get(vehicleType).put(crashSeverity, 0);
            }
        }
        for (Crash crash : crashes) {
            if (crash.getVehicles() != null && crash.getCrashSeverity() != null) {
                for (Vehicle vehicle : crash.getVehicles()) {
                    if (vehicle.getVehicleType() != null) {
                        Map<CrashSeverity, Integer> crashSeverityMap = vehicleTypeMap.get(vehicle.getVehicleType());
                        Integer count = crashSeverityMap.get(crash.getCrashSeverity()) + 1;
                        crashSeverityMap.put(crash.getCrashSeverity(), count);
                    }
                }
            }
        }
        return vehicleTypeMap;
    }

    @Override
    public Map<String, Map<CrashSeverity, Integer>> buildTimeRangeMap(List<Crash> crashes) {
        Map<String, Map<CrashSeverity, Integer>> timeRangeMap = new HashMap<>();
        List<LabelValue> timeRanges = lookupManager.getAllTimeRanges();
        List<CrashSeverity> crashSeverities = crashSeverityManager.getAllDistinct();
        Collections.sort(timeRanges);
        Collections.sort(crashSeverities);
        for (LabelValue timeRange : timeRanges) {
            timeRangeMap.put(timeRange.getLabel(), new HashMap<>());
            for (CrashSeverity crashSeverity : crashSeverities) {
                timeRangeMap.get(timeRange.getLabel()).put(crashSeverity, 0);
            }
        }
        for (Crash crash : crashes) {
            if (crash.getCrashDateTime() != null && crash.getCrashSeverity() != null) {
                TimeRange timeRange = lookupManager.getTimeRangeByTime(crash.getCrashDateTime());
                Map<CrashSeverity, Integer> crashSeverityMap = timeRangeMap.get(timeRange.getLabel());
                Integer count = crashSeverityMap.get(crash.getCrashSeverity()) + 1;
                crashSeverityMap.put(crash.getCrashSeverity(), count);
            }
        }
        return timeRangeMap;
    }

    @Override
    public Map<District, Map<CrashSeverity, Integer>> buildDistrictMap(List<Crash> crashes) {
        List<District> districts = districtManager.getAllDistinct();
        List<CrashSeverity> crashSeverities = crashSeverityManager.getAllDistinct();
        Collections.sort(districts);
        Collections.sort(crashSeverities);
        Map<District, Map<CrashSeverity, Integer>> districtMap = new HashMap<District, Map<CrashSeverity, Integer>>();
        for (District district : districts) {
            districtMap.put(district, new HashMap<>());
            for (CrashSeverity crashSeverity : crashSeverities) {
                districtMap.get(district).put(crashSeverity, 0);
            }
        }
        for (Crash crash : crashes) {
            if (crash.getPoliceStation() != null && crash.getPoliceStation().getDistrict() != null && crash.getCrashSeverity() != null) {
                Map<CrashSeverity, Integer> crashSeverityMap = districtMap.get(crash.getPoliceStation().getDistrict());
                Integer count = crashSeverityMap.get(crash.getCrashSeverity()) + 1;
                crashSeverityMap.put(crash.getCrashSeverity(), count);
            }
        }
        return districtMap;
    }

    @Override
    public Map<String, Map<CrashSeverity, int[]>> buildCasualtyAgeGenderMap(List<Crash> crashes) {
        Map<String, Map<CrashSeverity, int[]>> casualtyAgeGenderMap = new HashMap<>();
        List<LabelValue> ageRanges = lookupManager.getAllAgeRanges();
        List<CrashSeverity> crashSeverities = crashSeverityManager.getAllDistinct();
        Collections.sort(crashSeverities);
        for (LabelValue ageRange : ageRanges) {
            casualtyAgeGenderMap.put(ageRange.getLabel(), new HashMap<>());
            for (CrashSeverity crashSeverity : crashSeverities) {
                casualtyAgeGenderMap.get(ageRange.getLabel()).put(crashSeverity, new int[]{0, 0, 0});
            }
        }
        for (Crash crash : crashes) {
            if (crash.getCasualties() != null && crash.getCrashSeverity() != null) {
                for (Casualty casualty : crash.getCasualties()) {
                    if (casualty.getAge() != null) {
                        AgeRange ageRange = lookupManager.getAgeRangeByAge(casualty.getAge());
                        if (ageRange == null) {
                            continue;
                        }
                        Map<CrashSeverity, int[]> crashSeverityMap = casualtyAgeGenderMap.get(ageRange.getLabel());
                        incrementGenderCounts(casualty, crashSeverityMap.get(crash.getCrashSeverity()));
                    }
                }
            }
        }
        return casualtyAgeGenderMap;
    }

    private void incrementGenderCounts(Casualty casualty, int[] counts) {
        if (casualty.getGender() != null && casualty.getGender().trim().equals("M")) {
            counts[0]++;
        } else if (casualty.getGender() != null && casualty.getGender().trim().equals("F")) {
            counts[1]++;
        } else {
            counts[2]++;
        }
    }

    public Map<Weather, Map<CrashSeverity, Integer>> buildWeatherMap(List<Crash> crashes) {
        Map<Weather, Map<CrashSeverity, Integer>> weatherMap = new HashMap<>();
        List<Weather> weathers = weatherManager.getAllDistinct();
        List<CrashSeverity> crashSeverities = crashSeverityManager.getAllDistinct();
        Collections.sort(weathers);
        Collections.sort(crashSeverities);
        for (Weather weather : weathers) {
            weatherMap.put(weather, new HashMap<>());
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
            surfaceConditionMap.put(surfaceCondition, new HashMap<>());
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
        Map<RoadSurface, Map<CrashSeverity, Integer>> roadSurfaceMap = new HashMap<>();
        List<RoadSurface> roadSurfaces = roadSurfaceManager.getAllDistinct();
        List<CrashSeverity> crashSeverities = crashSeverityManager.getAllDistinct();
        Collections.sort(roadSurfaces);
        Collections.sort(crashSeverities);
        for (RoadSurface roadSurface : roadSurfaces) {
            roadSurfaceMap.put(roadSurface, new HashMap<>());
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
            surfaceTypeMap.put(surfaceType, new HashMap<>());
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
            roadwayCharacterMap.put(roadwayCharacter, new HashMap<>());
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
        Map<JunctionType, Map<CrashSeverity, Integer>> junctionTypeMap = new HashMap<>();
        List<JunctionType> junctionTypes = junctionTypeManager.getAllDistinct();
        List<CrashSeverity> crashSeverities = crashSeverityManager.getAllDistinct();
        Collections.sort(junctionTypes);
        Collections.sort(crashSeverities);
        junctionTypes.forEach(junctionType -> {
            junctionTypeMap.put(junctionType, new HashMap<>());
            crashSeverities.forEach(crashSeverity -> junctionTypeMap.get(junctionType).put(crashSeverity, 0));
        });
        crashes.stream()
            .filter(crash -> crash.getJunctionType() != null && crash.getCrashSeverity() != null)
            .forEach(crash -> {
                Map<CrashSeverity, Integer> crashSeverityMap = junctionTypeMap.get(crash.getJunctionType());
                Integer count = crashSeverityMap.get(crash.getCrashSeverity()) + 1;
                crashSeverityMap.put(crash.getCrashSeverity(), count);
            });
        return junctionTypeMap;
    }
}
