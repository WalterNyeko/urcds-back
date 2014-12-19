package com.sweroad.util;

import com.sweroad.model.Crash;

/**
 * Created by Frank on 10/21/14.
 */
public class CrashHelper {

    /**
     * Detects if crash2 is an updated version of crash1.
     *
     * @param crash1 Crash object from db
     * @param crash2 Crash object from UI
     * @return true if yes, false otherwise
     */
    public static final boolean isUpdatedVersionOf(final Crash crash1, final Crash crash2) {
        if (!crash1.getId().equals(crash2.getId())) {
            return false;
        }
        if(crash1.getTarNo() == null && crash2.getTarNo() != null) {
            return true;
        }
        if (crash1.getTarNo() != null && !crash1.getTarNo().equals(crash2.getTarNo())) {
            return true;
        }
        if (crash1.getCollisionType() == null && crash2.getCollisionType() != null) {
            return true;
        }
        if (crash1.getCollisionType() != null && !crash1.getCollisionType().equals(crash2.getCollisionType())) {
            return true;
        }
        if(crash1.getCrashDateTime() == null && crash2.getCrashDateTime() != null) {
            return true;
        }
        if (crash1.getCrashDateTime() != null && !crash1.getCrashDateTime().equals(crash2.getCrashDateTime())) {
            return true;
        }
        if(crash1.getCrashPlace() == null && crash2.getCrashPlace() != null) {
            return true;
        }
        if (crash1.getCrashPlace() != null && !crash1.getCrashPlace().equals(crash2.getCrashPlace())) {
            return true;
        }
        if(crash1.getCrashSeverity() == null && crash2.getCrashSeverity() != null) {
            return true;
        }
        if (crash1.getCrashSeverity() != null && !crash1.getCrashSeverity().equals(crash2.getCrashSeverity())) {
            return true;
        }
        if(crash1.getLatitude() == null && crash2.getLatitude() != null) {
            return true;
        }
        if (crash1.getLatitude() != null && !crash1.getLatitude().equals(crash2.getLatitude())) {
            return true;
        }
        if(crash1.getLongitude() == null && crash2.getLongitude() != null) {
            return true;
        }
        if (crash1.getLongitude() != null && !crash1.getLongitude().equals(crash2.getLongitude())) {
            return true;
        }
        if(crash1.getJunctionType() == null && crash2.getJunctionType() != null) {
            return true;
        }
        if (crash1.getJunctionType() != null && !crash1.getJunctionType().equals(crash2.getJunctionType())) {
            return true;
        }
        if(crash1.getCrashCause() == null && crash2.getCrashCause() != null) {
            return true;
        }
        if (crash1.getCrashCause() != null && !crash1.getCrashCause().equals(crash2.getCrashCause())) {
            return true;
        }
        if(crash1.getPoliceStation() == null && crash2.getPoliceStation() != null) {
            return true;
        }
        if (crash1.getPoliceStation() != null && !crash1.getPoliceStation().equals(crash2.getPoliceStation())) {
            return true;
        }
        if(crash1.getReportingDate() == null && crash2.getReportingDate() != null) {
            return true;
        }
        if (crash1.getReportingDate() != null && !crash1.getReportingDate().equals(crash2.getReportingDate())) {
            return true;
        }
        if(crash1.getReportingOfficerName() == null && crash2.getReportingOfficerName() != null) {
            return true;
        }
        if (crash1.getReportingOfficerName() != null && !crash1.getReportingOfficerName().equals(crash2.getReportingOfficerName())) {
            return true;
        }
        if(crash1.getReportingOfficerRank() == null && crash2.getReportingOfficerRank() != null) {
            return true;
        }
        if (crash1.getReportingOfficerRank() != null && !crash1.getReportingOfficerRank().equals(crash2.getReportingOfficerRank())) {
            return true;
        }
        if(crash1.getRoad() == null && crash2.getRoad() != null) {
            return true;
        }
        if (crash1.getRoad() != null && !crash1.getRoad().equals(crash2.getRoad())) {
            return true;
        }
        if(crash1.getRoadNumber() == null && crash2.getRoadNumber() != null) {
            return true;
        }
        if (crash1.getRoadNumber() != null && !crash1.getRoadNumber().equals(crash2.getRoadNumber())) {
            return true;
        }
        if(crash1.getRoadSurface() == null && crash2.getRoadSurface() != null) {
            return true;
        }
        if (crash1.getRoadSurface() != null && !crash1.getRoadSurface().equals(crash2.getRoadSurface())) {
            return true;
        }
        if(crash1.getRoadwayCharacter() == null && crash2.getRoadwayCharacter() != null) {
            return true;
        }
        if (crash1.getRoadwayCharacter() != null && !crash1.getRoadwayCharacter().equals(crash2.getRoadwayCharacter())) {
            return true;
        }
        if(crash1.getSupervisingDate() == null && crash2.getSupervisingDate() != null) {
            return true;
        }
        if (crash1.getSupervisingDate() != null && !crash1.getSupervisingDate().equals(crash2.getSupervisingDate())) {
            return true;
        }
        if(crash1.getSupervisingOfficerName() == null && crash2.getSupervisingOfficerName() != null) {
            return true;
        }
        if (crash1.getSupervisingOfficerName() != null && !crash1.getSupervisingOfficerName().equals(crash2.getSupervisingOfficerName())) {
            return true;
        }
        if(crash1.getSupervisingOfficerRank() == null && crash2.getSupervisingOfficerRank() != null) {
            return true;
        }
        if (crash1.getSupervisingOfficerRank() != null && !crash1.getSupervisingOfficerRank().equals(crash2.getSupervisingOfficerRank())) {
            return true;
        }
        if(crash1.getSurfaceCondition() == null && crash2.getSurfaceCondition() != null) {
            return true;
        }
        if (crash1.getSurfaceCondition() != null && !crash1.getSurfaceCondition().equals(crash2.getSurfaceCondition())) {
            return true;
        }
        if(crash1.getSurfaceType() == null && crash2.getSurfaceType() != null) {
            return true;
        }
        if (crash1.getSurfaceType() != null && !crash1.getSurfaceType().equals(crash2.getSurfaceType())) {
            return true;
        }
        if(crash1.getTownOrVillage() == null && crash2.getTownOrVillage() != null) {
            return true;
        }
        if (crash1.getTownOrVillage() != null && !crash1.getTownOrVillage().equals(crash2.getTownOrVillage())) {
            return true;
        }
        if(crash1.getVehicleFailureType() == null && crash2.getVehicleFailureType() != null) {
            return true;
        }
        if (crash1.getVehicleFailureType() != null && !crash1.getVehicleFailureType().equals(crash2.getVehicleFailureType())) {
            return true;
        }
        if(crash1.getWeather() == null && crash2.getWeather() != null) {
            return true;
        }
        if (crash1.getWeather() != null && !crash1.getWeather().equals(crash2.getWeather())) {
            return true;
        }
        return false;
    }
}
