package com.sweroad.reporting.builder;

import com.sweroad.model.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Frank on 5/3/15.
 */
public interface MapBuilder {

    Map<Weather, Map<CrashSeverity, Integer>> buildWeatherMap(List<Crash> crashes);
    Map<CrashCause, Map<CrashSeverity, Integer>> buildCrashCauseMap(List<Crash> crashes);
    Map<RoadSurface, Map<CrashSeverity, Integer>> buildRoadSurfaceMap(List<Crash> crashes);
    Map<SurfaceType, Map<CrashSeverity, Integer>> buildSurfaceTypeMap(List<Crash> crashes);
    Map<JunctionType, Map<CrashSeverity, Integer>> buildJunctionTypeMap(List<Crash> crashes);
    Map<CollisionType, Map<CrashSeverity, Integer>> buildCollisionTypeMap(List<Crash> crashes);
    Map<SurfaceCondition, Map<CrashSeverity, Integer>> buildSurfaceConditionMap(List<Crash> crashes);
    Map<RoadwayCharacter, Map<CrashSeverity, Integer>> buildRoadwayCharacterMap(List<Crash> crashes);
    Map<VehicleFailureType, Map<CrashSeverity, Integer>> buildVehicleFailureTypeMap(List<Crash> crashes);
}
