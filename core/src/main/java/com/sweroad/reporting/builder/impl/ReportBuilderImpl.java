package com.sweroad.reporting.builder.impl;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import com.sweroad.model.*;
import com.sweroad.reporting.ReportDefinition;
import com.sweroad.reporting.builder.DataSourceBuilder;
import com.sweroad.reporting.builder.ReportBuilder;
import com.sweroad.reporting.template.DRTemplate;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabRowGroupBuilder;
import net.sf.dynamicreports.report.constant.Calculation;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by Frank on 4/30/15.
 */
@Service("reportBuilder")
public class ReportBuilderImpl implements ReportBuilder {

    @Autowired
    private DataSourceBuilder dataSourceBuilder;

    @Override
    public void buildCasualtyAgeGenderBySeverityReport(List<Crash> crashes, String fileName) {
        buildCrashSeverityReport(new ReportDefinition
                .ReportDefinitionBuilder()
                .addRowGroupName("casualtyAge")
                .addRowGroupTitle("Casualty Age")
                .addMeasureName("casualties")
                .addMeasureTitle("Casualties")
                .addDataSource(dataSourceBuilder.buildCasualtyAgeGenderDataSource(crashes))
                .addMeasures(ctab.measure("M", "male", Integer.class, Calculation.SUM),
                        ctab.measure("F", "female", Integer.class, Calculation.SUM),
                        ctab.measure("U", "unknown", Integer.class, Calculation.SUM))
                .addFileName(fileName).build());
    }

    @Override
    public void buildCollisionTypeBySeverityReport(List<Crash> crashes, String fileName) {
        buildCrashSeverityReport(new ReportDefinition
                .ReportDefinitionBuilder()
                .addRowGroupName("collisionType")
                .addRowGroupTitle("Collision Type")
                .addDataSource(dataSourceBuilder.buildCollisionTypeDataSource(crashes))
                .addFileName(fileName).build());
    }

    @Override
    public void buildPoliceStationBySeverityReport(List<Crash> crashes, String fileName) {
        buildCrashSeverityReport(new ReportDefinition
                .ReportDefinitionBuilder()
                .addRowGroupName("policeStation")
                .addRowGroupTitle("Police Station")
                .addDataSource(dataSourceBuilder.buildPoliceStationDataSource(crashes))
                .addFileName(fileName).build());
    }

    @Override
    public void buildCrashCauseBySeverityReport(List<Crash> crashes, String fileName) {
        buildCrashSeverityReport(new ReportDefinition
                .ReportDefinitionBuilder()
                .addRowGroupName("crashCause")
                .addRowGroupTitle("Crash Cause")
                .addDataSource(dataSourceBuilder.buildCrashCauseDataSource(crashes))
                .addFileName(fileName).build());
    }

    @Override
    public void buildJunctionTypeBySeverityReport(List<Crash> crashes, String fileName) {
        buildCrashSeverityReport(new ReportDefinition
                .ReportDefinitionBuilder()
                .addRowGroupName("junctionType")
                .addRowGroupTitle("Junction Type")
                .addDataSource(dataSourceBuilder.buildJunctionTypeDataSource(crashes))
                .addFileName(fileName).build());
    }

    @Override
    public void buildRoadSurfaceBySeverityReport(List<Crash> crashes, String fileName) {
        buildCrashSeverityReport(new ReportDefinition
                .ReportDefinitionBuilder()
                .addRowGroupName("roadSurface")
                .addRowGroupTitle("Road Surface")
                .addDataSource(dataSourceBuilder.buildRoadSurfaceDataSource(crashes))
                .addFileName(fileName).build());
    }

    @Override
    public void buildRoadwayCharacterBySeverityReport(List<Crash> crashes, String fileName) {
        buildCrashSeverityReport(new ReportDefinition
                .ReportDefinitionBuilder()
                .addRowGroupName("roadwayCharacter")
                .addRowGroupTitle("Character of Roadway")
                .addDataSource(dataSourceBuilder.buildRoadwayCharacterDataSource(crashes))
                .addFileName(fileName).build());
    }

    @Override
    public void buildSurfaceConditionBySeverityReport(List<Crash> crashes, String fileName) {
        buildCrashSeverityReport(new ReportDefinition
                .ReportDefinitionBuilder()
                .addRowGroupName("surfaceCondition")
                .addRowGroupTitle("Surface Condtion")
                .addDataSource(dataSourceBuilder.buildSurfaceConditionDataSource(crashes))
                .addFileName(fileName).build());
    }

    @Override
    public void buildSurfaceTypeBySeverityReport(List<Crash> crashes, String fileName) {
        buildCrashSeverityReport(new ReportDefinition
                .ReportDefinitionBuilder()
                .addRowGroupName("surfaceType")
                .addRowGroupTitle("Surface Type")
                .addDataSource(dataSourceBuilder.buildSurfaceTypeDataSource(crashes))
                .addFileName(fileName).build());
    }

    @Override
    public void buildTimeRangeBySeverityReport(List<Crash> crashes, String fileName) {
        buildCrashSeverityReport(new ReportDefinition
                .ReportDefinitionBuilder()
                .addRowGroupName("timeRange")
                .addRowGroupTitle("Time of Crash")
                .addDataSource(dataSourceBuilder.buildTimeRangeDataSource(crashes))
                .addFileName(fileName).build());
    }

    @Override
    public void buildVehicleFailureTypeBySeverityReport(List<Crash> crashes, String fileName) {
        buildCrashSeverityReport(new ReportDefinition
                .ReportDefinitionBuilder()
                .addRowGroupName("vehicleFailureType")
                .addRowGroupTitle("Vehicle Failure Type")
                .addDataSource(dataSourceBuilder.buildVehicleFailureTypeDataSource(crashes))
                .addFileName(fileName).build());
    }

    @Override
    public void buildVehicleTypeBySeverityReport(List<Crash> crashes, String fileName) {
        buildCrashSeverityReport(new ReportDefinition
                .ReportDefinitionBuilder()
                .addRowGroupName("vehicleType")
                .addRowGroupTitle("Vehicle Type")
                .addMeasureName("vehicles")
                .addMeasureTitle("Vehicles")
                .addDataSource(dataSourceBuilder.buildVehicleTypeDataSource(crashes))
                .addFileName(fileName).build());
    }

    @Override
    public void buildWeatherBySeverityReport(List<Crash> crashes, String fileName) {
        buildCrashSeverityReport(new ReportDefinition
                .ReportDefinitionBuilder()
                .addRowGroupName("weather")
                .addRowGroupTitle("Weather")
                .addDataSource(dataSourceBuilder.buildWeatherDataSource(crashes))
                .addFileName(fileName).build());
    }

    @Override
    public void buildDistrictBySeverityReport(List<Crash> crashes, String fileName) {
        buildCrashSeverityReport(new ReportDefinition
                .ReportDefinitionBuilder()
                .addRowGroupName("district")
                .addRowGroupTitle("District")
                .addDataSource(dataSourceBuilder.buildDistrictDataSource(crashes))
                .addFileName(fileName).build());
    }

    private void buildCrashSeverityReport(ReportDefinition reportDefinition) {
        CrosstabRowGroupBuilder<String> rowGroup = ctab.rowGroup(reportDefinition.getRowGroupName(), String.class)
                .setTotalHeader("Total " + reportDefinition.getMeasureTitle());
        CrosstabColumnGroupBuilder<String> columnGroup = ctab.columnGroup("crashSeverity", String.class);
        CrosstabBuilder crosstab = ctab.crosstab().headerCell(cmp.text(reportDefinition.getRowGroupTitle())
                .setStyle(DRTemplate.boldCenteredStyle))
                .rowGroups(rowGroup)
                .columnGroups(columnGroup)
                .measures(reportDefinition.getMeasures());
        try {
            OutputStream outputStream = new FileOutputStream(reportDefinition.getFileName());
            report()
                    .setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
                    .setPageMargin(DRTemplate.marginTemplate)
                    .setTemplate(DRTemplate.reportTemplate)
                    .title(DRTemplate.createTitleComponent(reportDefinition.getRowGroupTitle() + " by Crash Severity"))
                    .summary(crosstab)
                    .setDataSource(reportDefinition.getDataSource())
                    .toPdf(outputStream);
        } catch (DRException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}