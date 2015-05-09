package com.sweroad.reporting;

import net.sf.dynamicreports.report.builder.crosstab.CrosstabMeasureBuilder;
import net.sf.dynamicreports.report.constant.Calculation;
import net.sf.jasperreports.engine.JRDataSource;

import static net.sf.dynamicreports.report.builder.DynamicReports.ctab;

/**
 * Created by Frank on 5/9/15.
 */
public class ReportDefinition {

    private String rowGroupName;
    private String rowGroupTitle;
    private String measureName;
    private String measureTitle;
    private JRDataSource dataSource;
    private String fileName;
    private CrosstabMeasureBuilder[] measures;

    private ReportDefinition(ReportDefinitionBuilder builder) {
        this.fileName = builder.fileName;
        this.measures = builder.measures;
        this.dataSource = builder.dataSource;
        this.measureName = builder.measureName;
        this.rowGroupName = builder.rowGroupName;
        this.measureTitle = builder.measureTitle;
        this.rowGroupTitle = builder.rowGroupTitle;
        builder.setDefaults(this);
    }

    public String getRowGroupName() {
        return rowGroupName;
    }

    public void setRowGroupName(String rowGroupName) {
        this.rowGroupName = rowGroupName;
    }

    public String getRowGroupTitle() {
        return rowGroupTitle;
    }

    public void setRowGroupTitle(String rowGroupTitle) {
        this.rowGroupTitle = rowGroupTitle;
    }

    public String getMeasureName() {
        return measureName;
    }

    public void setMeasureName(String measureName) {
        this.measureName = measureName;
    }

    public String getMeasureTitle() {
        return measureTitle;
    }

    public void setMeasureTitle(String measureTitle) {
        this.measureTitle = measureTitle;
    }

    public JRDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(JRDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public CrosstabMeasureBuilder[] getMeasures() {
        return measures;
    }

    public void setMeasures(CrosstabMeasureBuilder... measures) {
        this.measures = measures;
    }

    public static class ReportDefinitionBuilder {

        private String rowGroupName;
        private String rowGroupTitle;
        private String measureName;
        private String measureTitle;
        private JRDataSource dataSource;
        private String fileName;
        private CrosstabMeasureBuilder[] measures;

        public ReportDefinitionBuilder addRowGroupName(String rowGroupName) {
            this.rowGroupName = rowGroupName;
            return this;
        }

        public ReportDefinitionBuilder addRowGroupTitle(String rowGroupTitle) {
            this.rowGroupTitle = rowGroupTitle;
            return this;
        }

        public ReportDefinitionBuilder addMeasureName(String measureName) {
            this.measureName = measureName;
            return this;
        }

        public ReportDefinitionBuilder addMeasureTitle(String measureTitle) {
            this.measureTitle = measureTitle;
            return this;
        }

        public ReportDefinitionBuilder addDataSource(JRDataSource dataSource) {
            this.dataSource = dataSource;
            return this;
        }

        public ReportDefinitionBuilder addFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public ReportDefinitionBuilder addMeasures(CrosstabMeasureBuilder... measures) {
            this.measures = measures;
            return this;
        }

        public ReportDefinition build() {
            return new ReportDefinition(this);
        }

        private void setDefaults(ReportDefinition reportDefinition) {
            if (reportDefinition.getMeasureName() == null) {
                reportDefinition.setMeasureName("crashes");
            }
            if (reportDefinition.getMeasureTitle() == null) {
                reportDefinition.setMeasureTitle("Crashes");
            }
            if (reportDefinition.getMeasures() == null) {
                reportDefinition.setMeasures(ctab.measure(reportDefinition.getMeasureTitle(),
                        reportDefinition.getMeasureName(), Integer.class, Calculation.SUM));
            }
        }
    }
}
