package com.sweroad.reporting;

import com.sweroad.reporting.builder.ReportBuilder;
import com.sweroad.service.BaseManagerTestCase;
import com.sweroad.service.CrashManager;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;

/**
 * Created by Frank on 5/3/15.
 */
public class ReportBuilderTest extends BaseManagerTestCase {

    @Autowired
    private ReportBuilder reportBuilder;
    @Autowired
    private CrashManager crashManager;

    @Test
    public void testThatReportBuilderGeneratesReport() {
        reportBuilder.buildCrashCauseReport(crashManager.getAllDistinct());
        assertTrue(true);
    }
}
