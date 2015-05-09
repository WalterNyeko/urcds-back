package com.sweroad.reporting.builder;

import com.sweroad.model.Crash;

import java.util.List;

/**
 * Created by Frank on 4/30/15.
 */
public interface ReportBuilder {

    void buildWeatherBySeverityReport(List<Crash> crashes, String fileName);
    void buildDistrictBySeverityReport(List<Crash> crashes, String fileName);
    void buildTimeRangeBySeverityReport(List<Crash> crashes, String fileName);
    void buildCrashCauseBySeverityReport(List<Crash> crashes, String fileName);
    void buildRoadSurfaceBySeverityReport(List<Crash> crashes, String fileName);
    void buildSurfaceTypeBySeverityReport(List<Crash> crashes, String fileName);
    void buildVehicleTypeBySeverityReport(List<Crash> crashes, String fileName);
    void buildJunctionTypeBySeverityReport(List<Crash> crashes, String fileName);
    void buildCollisionTypeBySeverityReport(List<Crash> crashes, String fileName);
    void buildPoliceStationBySeverityReport(List<Crash> crashes, String fileName);
    void buildRoadwayCharacterBySeverityReport(List<Crash> crashes, String fileName);
    void buildSurfaceConditionBySeverityReport(List<Crash> crashes, String fileName);
    void buildCasualtyAgeGenderBySeverityReport(List<Crash> crashes, String fileName);
    void buildVehicleFailureTypeBySeverityReport(List<Crash> crashes, String fileName);

}
