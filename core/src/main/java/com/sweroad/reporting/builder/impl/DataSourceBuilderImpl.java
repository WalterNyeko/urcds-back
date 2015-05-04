package com.sweroad.reporting.builder.impl;

import com.sweroad.model.*;
import com.sweroad.reporting.builder.DataSourceBuilder;
import com.sweroad.reporting.builder.MapBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.jasperreports.engine.JRDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Frank on 5/3/15.
 */
@Service("dataSourceBuilder")
public class DataSourceBuilderImpl implements DataSourceBuilder {

    @Autowired
    private MapBuilder mapBuilder;

    public JRDataSource buildCollisionTypeDataSource(List<Crash> crashes) {
        DRDataSource dataSource = new DRDataSource("collisionType", "crashSeverity", "crashes");
        Map<CollisionType, Map<CrashSeverity, Integer>> collisionTypeMap = mapBuilder.buildCollisionTypeMap(crashes);
        for (CollisionType collisionType : collisionTypeMap.keySet()) {
            for (CrashSeverity crashSeverity : collisionTypeMap.get(collisionType).keySet()) {
                dataSource.add(collisionType.getName(), crashSeverity.getName(), collisionTypeMap.get(collisionType).get(crashSeverity));
            }
        }
        return dataSource;
    }

    public JRDataSource buildCrashCauseDataSource(List<Crash> crashes) {
        DRDataSource dataSource = new DRDataSource("crashCause", "crashSeverity", "crashes");
        Map<CrashCause, Map<CrashSeverity, Integer>> crashCauseMap = mapBuilder.buildCrashCauseMap(crashes);
        for (CrashCause crashCause : crashCauseMap.keySet()) {
            for (CrashSeverity crashSeverity : crashCauseMap.get(crashCause).keySet()) {
                dataSource.add(crashCause.getName(), crashSeverity.getName(), crashCauseMap.get(crashCause).get(crashSeverity));
            }
        }
        return dataSource;
    }

    public JRDataSource buildVehicleFailureTypeDataSource(List<Crash> crashes) {
        DRDataSource dataSource = new DRDataSource("vehicleFailureType", "crashSeverity", "crashes");
        Map<VehicleFailureType, Map<CrashSeverity, Integer>> vehicleFailureTypeMap = mapBuilder.buildVehicleFailureTypeMap(crashes);
        for (VehicleFailureType vehicleFailureType : vehicleFailureTypeMap.keySet()) {
            for (CrashSeverity crashSeverity : vehicleFailureTypeMap.get(vehicleFailureType).keySet()) {
                dataSource.add(vehicleFailureType.getName(), crashSeverity.getName(), vehicleFailureTypeMap.get(vehicleFailureType).get(crashSeverity));
            }
        }
        return dataSource;
    }

    public JRDataSource buildWeatherDataSource(List<Crash> crashes) {
        DRDataSource dataSource = new DRDataSource("weather", "crashSeverity", "crashes");
        Map<Weather, Map<CrashSeverity, Integer>> weatherMap = mapBuilder.buildWeatherMap(crashes);
        for (Weather weather : weatherMap.keySet()) {
            for (CrashSeverity crashSeverity : weatherMap.get(weather).keySet()) {
                dataSource.add(weather.getName(), crashSeverity.getName(), weatherMap.get(weather).get(crashSeverity));
            }
        }
        return dataSource;
    }

    public JRDataSource buildSurfaceConditionDataSource(List<Crash> crashes) {
        DRDataSource dataSource = new DRDataSource("surfaceCondition", "crashSeverity", "crashes");
        Map<SurfaceCondition, Map<CrashSeverity, Integer>> surfaceConditionMap = mapBuilder.buildSurfaceConditionMap(crashes);
        for (SurfaceCondition surfaceCondition : surfaceConditionMap.keySet()) {
            for (CrashSeverity crashSeverity : surfaceConditionMap.get(surfaceCondition).keySet()) {
                dataSource.add(surfaceCondition.getName(), crashSeverity.getName(), surfaceConditionMap.get(surfaceCondition).get(crashSeverity));
            }
        }
        return dataSource;
    }

    public JRDataSource buildRoadSurfaceDataSource(List<Crash> crashes) {
        DRDataSource dataSource = new DRDataSource("roadSurface", "crashSeverity", "crashes");
        Map<RoadSurface, Map<CrashSeverity, Integer>> weatherMap = mapBuilder.buildRoadSurfaceMap(crashes);
        for (RoadSurface roadSurface : weatherMap.keySet()) {
            for (CrashSeverity crashSeverity : weatherMap.get(roadSurface).keySet()) {
                dataSource.add(roadSurface.getName(), crashSeverity.getName(), weatherMap.get(roadSurface).get(crashSeverity));
            }
        }
        return dataSource;
    }

    public JRDataSource buildSurfaceTypeDataSource(List<Crash> crashes) {
        DRDataSource dataSource = new DRDataSource("surfaceType", "crashSeverity", "crashes");
        Map<SurfaceType, Map<CrashSeverity, Integer>> surfaceTypeMap = mapBuilder.buildSurfaceTypeMap(crashes);
        for (SurfaceType surfaceType : surfaceTypeMap.keySet()) {
            for (CrashSeverity crashSeverity : surfaceTypeMap.get(surfaceType).keySet()) {
                dataSource.add(surfaceType.getName(), crashSeverity.getName(), surfaceTypeMap.get(surfaceType).get(crashSeverity));
            }
        }
        return dataSource;
    }

    public JRDataSource buildRoadwayCharacterDataSource(List<Crash> crashes) {
        DRDataSource dataSource = new DRDataSource("roadwayCharacter", "crashSeverity", "crashes");
        Map<RoadwayCharacter, Map<CrashSeverity, Integer>> roadwayCharacterMap = mapBuilder.buildRoadwayCharacterMap(crashes);
        for (RoadwayCharacter roadwayCharacter : roadwayCharacterMap.keySet()) {
            for (CrashSeverity crashSeverity : roadwayCharacterMap.get(roadwayCharacter).keySet()) {
                dataSource.add(roadwayCharacter.getName(), crashSeverity.getName(), roadwayCharacterMap.get(roadwayCharacter).get(crashSeverity));
            }
        }
        return dataSource;
    }

    public JRDataSource buildJunctionTypeDataSource(List<Crash> crashes) {
        DRDataSource dataSource = new DRDataSource("junctionType", "crashSeverity", "crashes");
        Map<JunctionType, Map<CrashSeverity, Integer>> junctionTypeMap = mapBuilder.buildJunctionTypeMap(crashes);
        for (JunctionType junctionType : junctionTypeMap.keySet()) {
            for (CrashSeverity crashSeverity : junctionTypeMap.get(junctionType).keySet()) {
                dataSource.add(junctionType.getName(), crashSeverity.getName(), junctionTypeMap.get(junctionType).get(crashSeverity));
            }
        }
        return dataSource;
    }
}
