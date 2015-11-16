package com.sweroad.audit;

import com.sweroad.model.*;
import com.sweroad.util.DateUtil;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Frank on 11/2/14.
 */
public class CrashAuditTest {
    @Test
    public void testThatTarNoChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.setTarNo("TAR-001");
        crash2.setTarNo("TAR-001-Updated");
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatCollisionTypeChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.setCollisionType(new CollisionType(1L));
        crash2.setCollisionType(new CollisionType(2L));
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatCrashDateChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.setCrashDateTime(DateUtil.parseDate("yyyy-MM-dd", "2015-11-16"));
        crash2.setCrashDateTime(DateUtil.parseDate("yyyy-MM-dd", "2015-11-17"));
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatCrashPlaceChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.setCrashPlace("Place1");
        crash2.setCrashPlace("Place2");
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatCrashSeverityChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.setCrashSeverity(new CrashSeverity(1L));
        crash2.setCrashSeverity(new CrashSeverity(2L));
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatLatitudeChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.setLatitude("N00 14.533");
        crash2.setLatitude("N00 15.533");
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatLongitudeChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.setLongitude("E032 21.639");
        crash2.setLongitude("E032 22.639");
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatJunctionTypeChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.setJunctionType(new JunctionType(1L));
        crash2.setJunctionType(new JunctionType(2L));
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatCrashCauseChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.setCrashCause(new CrashCause(1L));
        crash2.setCrashCause(new CrashCause(2L));
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatPoliceStationChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.setPoliceStation(new PoliceStation(1L));
        crash2.setPoliceStation(new PoliceStation(2L));
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatReportingDateChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.setReportingDateString("2015-11-16");
        crash2.setReportingDateString("2015-11-17");
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatReportingOfficerNameChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.setReportingOfficerName("Officer1");
        crash2.setReportingOfficerName("Officer2");
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatReportingOfficerRankChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.setReportingOfficerRank("Rank1");
        crash2.setReportingOfficerRank("Rank2");
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatRoadChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.setRoad("RoadA");
        crash2.setRoad("RoadB");
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatRoadNumberChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.setRoadNumber("001");
        crash2.setRoadNumber("002");
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatRoadSurfaceChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.setRoadSurface(new RoadSurface(1L));
        crash2.setRoadSurface(new RoadSurface(2L));
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatRoadwayCharacterChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.setRoadwayCharacter(new RoadwayCharacter(1L));
        crash2.setRoadwayCharacter(new RoadwayCharacter(2L));
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatSupervisingDateChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.setSupervisingDateString("2015-11-16");
        crash2.setSupervisingDateString("2015-11-17");
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatSupervisingOfficerNameChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.setSupervisingOfficerName("Officer1");
        crash2.setSupervisingOfficerName("Officer2");
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatSupervisingOfficerRankChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.setSupervisingOfficerRank("Rank1");
        crash2.setSupervisingOfficerRank("Rank2");
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatSurfaceConditionChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.setSurfaceCondition(new SurfaceCondition(1L));
        crash2.setSurfaceCondition(new SurfaceCondition(2L));
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatSurfaceTypeChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.setSurfaceType(new SurfaceType(1L));
        crash2.setSurfaceType(new SurfaceType(2L));
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatTownOrVillageChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.setTownOrVillage("TownA");
        crash2.setTownOrVillage("VillageA");
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatVehicleFailureTypeChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.setVehicleFailureType(new VehicleFailureType(1L));
        crash2.setVehicleFailureType(new VehicleFailureType(2L));
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatWeatherChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.setWeather(new Weather(1L));
        crash2.setWeather(new Weather(2L));
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatVehicleNumberChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.addVehicle(new Vehicle(1L));
        crash1.addVehicle(new Vehicle(2L));
        crash2.addVehicle(new Vehicle(1L));
        crash1.getVehicles().get(0).setVehicleType(new VehicleType(1L));
        crash1.getVehicles().get(1).setVehicleType(new VehicleType(2L));
        crash2.getVehicles().get(0).setVehicleType(new VehicleType(1L));
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatChangeInVehicleDataIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.addVehicle(new Vehicle(1L));
        crash2.addVehicle(new Vehicle(1L));
        crash1.getVehicles().get(0).setVehicleType(new VehicleType(1L));
        crash2.getVehicles().get(0).setVehicleType(new VehicleType(2L));
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatCasualtyNumberChangeIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.addCasualty(new Casualty(1L));
        crash1.addCasualty(new Casualty(2L));
        crash2.addCasualty(new Casualty(1L));
        crash1.getCasualties().get(0).setCasualtyType(new CasualtyType(1L));
        crash1.getCasualties().get(1).setCasualtyType(new CasualtyType(2L));
        crash2.getCasualties().get(0).setCasualtyType(new CasualtyType(1L));
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatChangeInCasualtyDataIsDetected() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        crash1.addCasualty(new Casualty(1L));
        crash2.addCasualty(new Casualty(1L));
        crash1.getCasualties().get(0).setCasualtyType(new CasualtyType(1L));
        crash2.getCasualties().get(0).setCasualtyType(new CasualtyType(2L));
        assertTrue(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatDifferentCrashIsNotDetectedAsChange() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(2L);
        assertFalse(CrashAudit.hasChanges(crash1, crash2));
    }

    @Test
    public void testThatUnchangedCrashHasNoChanges() {
        Crash crash1 = new Crash(1L);
        Crash crash2 = new Crash(1L);
        assertFalse(CrashAudit.hasChanges(crash1, crash2));
    }
}
