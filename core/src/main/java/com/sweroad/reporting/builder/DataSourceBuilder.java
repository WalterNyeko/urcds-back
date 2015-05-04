package com.sweroad.reporting.builder;

import com.sweroad.model.Crash;
import net.sf.jasperreports.engine.JRDataSource;

import java.util.List;

/**
 * Created by Frank on 5/3/15.
 */
public interface DataSourceBuilder {

    JRDataSource buildWeatherDataSource(List<Crash> crashes);
    JRDataSource buildCrashCauseDataSource(List<Crash> crashes);
    JRDataSource buildRoadSurfaceDataSource(List<Crash> crashes);
    JRDataSource buildSurfaceTypeDataSource(List<Crash> crashes);
    JRDataSource buildJunctionTypeDataSource(List<Crash> crashes);
    JRDataSource buildCollisionTypeDataSource(List<Crash> crashes);
    JRDataSource buildRoadwayCharacterDataSource(List<Crash> crashes);
    JRDataSource buildSurfaceConditionDataSource(List<Crash> crashes);
    JRDataSource buildVehicleFailureTypeDataSource(List<Crash> crashes);

}
