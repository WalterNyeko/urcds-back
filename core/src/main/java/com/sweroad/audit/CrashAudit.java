package com.sweroad.audit;

import com.sweroad.model.Crash;

/**
 * Created by Frank on 10/21/14.
 */
public class CrashAudit extends BaseAudit {

    /**
     * Detects if crash is an updated version of dbCrash.
     *
     * @param dbCrash Crash object from db
     * @param crash Crash object from UI
     * @return true if yes, false otherwise
     */
    public static final boolean hasChanges(final Crash dbCrash, final Crash crash) {
        if (dbCrash.equals(crash)) {
            if (isDifferent(dbCrash.getRoad(), crash.getRoad())) {
                return true;
            }
            if (isDifferent(dbCrash.getTarNo(), crash.getTarNo())) {
                return true;
            }
            if(isDifferent(dbCrash.getWeather(), crash.getWeather())) {
                return true;
            }
            if (dbCrash.getVehicleCount() != crash.getVehicleCount()) {
                return true;
            }
            if (dbCrash.getCasualtyCount() != crash.getCasualtyCount()) {
                return true;
            }
            if (isDifferent(dbCrash.getLatitude(), crash.getLatitude())) {
                return true;
            }
            if (isDifferent(dbCrash.getLongitude(), crash.getLongitude())) {
                return true;
            }
            if(isDifferent(dbCrash.getRoadNumber(), crash.getRoadNumber())) {
                return true;
            }
            if (isDifferent(dbCrash.getCrashPlace(), crash.getCrashPlace())) {
                return true;
            }
            if (isDifferent(dbCrash.getCrashCause(), crash.getCrashCause())) {
                return true;
            }
            if (isDifferent(dbCrash.getRoadSurface(), crash.getRoadSurface())) {
                return true;
            }
            if (isDifferent(dbCrash.getSurfaceType(), crash.getSurfaceType())) {
                return true;
            }
            if (isDifferent(dbCrash.getJunctionType(), crash.getJunctionType())) {
                return true;
            }
            if(isDifferent(dbCrash.getPoliceStation(), crash.getPoliceStation())) {
                return true;
            }
            if(isDifferent(dbCrash.getCrashSeverity(), crash.getCrashSeverity())) {
                return true;
            }
            if (isDifferent(dbCrash.getCollisionType(), crash.getCollisionType())) {
                return true;
            }
            if (isDifferent(dbCrash.getCrashDateTime(), crash.getCrashDateTime())) {
                return true;
            }
            if (isDifferent(dbCrash.getReportingDate(), crash.getReportingDate())) {
                return true;
            }
            if (isDifferent(dbCrash.getTownOrVillage(), crash.getTownOrVillage())) {
                return true;
            }
            if (isDifferent(dbCrash.getSupervisingDate(), crash.getSupervisingDate())) {
                return true;
            }
            if (isDifferent(dbCrash.getRoadwayCharacter(), crash.getRoadwayCharacter())) {
                return true;
            }
            if (isDifferent(dbCrash.getSurfaceCondition(), crash.getSurfaceCondition())) {
                return true;
            }
            if (isDifferent(dbCrash.getVehicleFailureType(), crash.getVehicleFailureType())) {
                return true;
            }
            if (isDifferent(dbCrash.getReportingOfficerName(), crash.getReportingOfficerName())) {
                return true;
            }
            if (isDifferent(dbCrash.getReportingOfficerRank(), crash.getReportingOfficerRank())) {
                return true;
            }
            if(isDifferent(dbCrash.getSupervisingOfficerRank(), crash.getSupervisingOfficerRank())) {
                return true;
            }
            if (isDifferent(dbCrash.getSupervisingOfficerName(), crash.getSupervisingOfficerName())) {
                return true;
            }
            for (int i = 0; i < dbCrash.getVehicleCount(); i++) {
                if (VehicleAudit.hasChanges(dbCrash.getVehicles().get(i), crash.getVehicles().get(i))) {
                    return true;
                }
            }
            for (int i = 0; i < dbCrash.getCasualties().size(); i++) {
                if (CasualtyAudit.hasChanges(dbCrash.getCasualties().get(i), crash.getCasualties().get(i))) {
                    return true;
                }
            }
        }
        return false;
    }
}
