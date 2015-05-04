package com.sweroad.reporting.builder.impl;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import com.sweroad.model.*;
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
    public void buildCrashCauseReport(List<Crash> crashes) {

        CrosstabRowGroupBuilder<String> rowGroup = ctab.rowGroup("collisionType", String.class).setTotalHeader("Total Crashes");
        CrosstabColumnGroupBuilder<String> columnGroup = ctab.columnGroup("crashSeverity", String.class);
        CrosstabBuilder crosstab = ctab.crosstab().headerCell(cmp.text("Collision Type").setStyle(DRTemplate.BOLD_CENTERED_STYLE))
                .rowGroups(rowGroup)
                .columnGroups(columnGroup)
                .measures(ctab.measure("Crashes", "crashes", Integer.class, Calculation.SUM));
        try {
            OutputStream outputStream = new FileOutputStream("/Users/Frank/Projects/sweroad/rcds/report.pdf");
            report()
                    .setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
                    .setTemplate(template())
                    .title(DRTemplate.createTitleComponent("Crash Cause Report"))
                    .summary(crosstab)
                    .setDataSource(dataSourceBuilder.buildCollisionTypeDataSource(crashes))
                    .toPdf(outputStream);
        } catch(DRException e) {
            e.printStackTrace();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}