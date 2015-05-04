package com.sweroad.reporting.builder;

import com.sweroad.model.Crash;

import java.util.List;

/**
 * Created by Frank on 4/30/15.
 */
public interface ReportBuilder {
    void buildCrashCauseReport(List<Crash> crashes);
}
