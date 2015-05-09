package com.sweroad.reporting;

import com.sweroad.model.Crash;
import com.sweroad.reporting.builder.ReportBuilder;
import com.sweroad.service.BaseManagerTestCase;
import com.sweroad.service.CrashManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Frank on 5/3/15.
 */
public class ReportBuilderTest extends BaseManagerTestCase {

    @Autowired
    private ReportBuilder reportBuilder;
    @Autowired
    private CrashManager crashManager;
    private File reportDir;
    private List<Crash> crashes;

    @Before
    public void setUp() throws Exception {
        crashes = crashManager.getAllDistinct();
        reportDir = new File("reports");
        if (!reportDir.exists()) {
            reportDir.mkdir();
        }
    }

    @Test
    public void testBuildCasualtyAgeGenderBySeverityReport() throws Exception {
        String fileName = reportDir.getName().concat(File.separator).concat("casualtyAge.pdf");
        reportBuilder.buildCasualtyAgeGenderBySeverityReport(crashes, fileName);
        assertTrue(new File(fileName).exists());
    }

    @Test
    public void testBuildCollisionTypeBySeverityReport() throws Exception {
        String fileName = reportDir.getName().concat(File.separator).concat("collisionType.pdf");
        reportBuilder.buildCollisionTypeBySeverityReport(crashes, fileName);
        assertTrue(new File(fileName).exists());
    }

    @Test
    public void testBuildCrashCauseBySeverityReport() throws Exception {
        String fileName = reportDir.getName().concat(File.separator).concat("crashCause.pdf");
        reportBuilder.buildCrashCauseBySeverityReport(crashes, fileName);
        assertTrue(new File(fileName).exists());
    }

    @Test
    public void testBuildDistrictBySeverityReport() throws Exception {
        String fileName = reportDir.getName().concat(File.separator).concat("district.pdf");
        reportBuilder.buildDistrictBySeverityReport(crashes, fileName);
        assertTrue(new File(fileName).exists());
    }

    @Test
    public void testBuildJunctionTypeBySeverityReport() throws Exception {
        String fileName = reportDir.getName().concat(File.separator).concat("junctionType.pdf");
        reportBuilder.buildJunctionTypeBySeverityReport(crashes, fileName);
        assertTrue(new File(fileName).exists());
    }

    @Test
    public void testBuildPoliceStationBySeverityReport() throws Exception {
        String fileName = reportDir.getName().concat(File.separator).concat("policeStation.pdf");
        reportBuilder.buildPoliceStationBySeverityReport(crashes, fileName);
        assertTrue(new File(fileName).exists());
    }

    @Test
    public void testBuildRoadSurfaceBySeverityReport() throws Exception {
        String fileName = reportDir.getName().concat(File.separator).concat("roadSurface.pdf");
        reportBuilder.buildRoadSurfaceBySeverityReport(crashes, fileName);
        assertTrue(new File(fileName).exists());
    }

    @Test
    public void testBuildRoadwayCharacterBySeverityReport() throws Exception {
        String fileName = reportDir.getName().concat(File.separator).concat("roadwayCharacter.pdf");
        reportBuilder.buildRoadwayCharacterBySeverityReport(crashes, fileName);
        assertTrue(new File(fileName).exists());
    }

    @Test
    public void testBuildSurfaceConditionBySeverityReport() throws Exception {
        String fileName = reportDir.getName().concat(File.separator).concat("surfaceCondition.pdf");
        reportBuilder.buildSurfaceConditionBySeverityReport(crashes, fileName);
        assertTrue(new File(fileName).exists());
    }

    @Test
    public void testBuildSurfaceTypeBySeverityReport() throws Exception {
        String fileName = reportDir.getName().concat(File.separator).concat("surfaceType.pdf");
        reportBuilder.buildSurfaceTypeBySeverityReport(crashes, fileName);
        assertTrue(new File(fileName).exists());
    }

    @Test
    public void testBuildTimeRangeBySeverityReport() throws Exception {
        String fileName = reportDir.getName().concat(File.separator).concat("timeRange.pdf");
        reportBuilder.buildTimeRangeBySeverityReport(crashes, fileName);
        assertTrue(new File(fileName).exists());
    }

    @Test
    public void testBuildVehicleFailureTypeBySeverityReport() throws Exception {
        String fileName = reportDir.getName().concat(File.separator).concat("vehicleFailureType.pdf");
        reportBuilder.buildVehicleFailureTypeBySeverityReport(crashes, fileName);
        assertTrue(new File(fileName).exists());
    }

    @Test
    public void testBuildVehicleTypeBySeverityReport() throws Exception {
        String fileName = reportDir.getName().concat(File.separator).concat("vehicleType.pdf");
        reportBuilder.buildVehicleTypeBySeverityReport(crashes, fileName);
        assertTrue(new File(fileName).exists());
    }

    @Test
    public void testBuildWeatherBySeverityReport() throws Exception {
        String fileName = reportDir.getName().concat(File.separator).concat("weather.pdf");
        reportBuilder.buildWeatherBySeverityReport(crashes, fileName);
        assertTrue(new File(fileName).exists());
    }

    @After
    public void tearDown() throws Exception {
        if (reportDir.exists()) {
            for (String file : reportDir.list()) {
                File fileToDelete = new File(reportDir, file);
                fileToDelete.delete();
            }
            reportDir.delete();
        }
    }
}
