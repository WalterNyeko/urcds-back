package com.sweroad.service.count.impl;

import com.sweroad.model.*;
import com.sweroad.service.GenericManager;
import com.sweroad.service.count.CountAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 5/31/16.
 */
@Service("countCrashSeverityService")
public class CountCrashSeverityServiceImpl extends BaseCountService implements CountAttributeService {

    @Autowired
    private GenericManager<CrashSeverity, Long> crashSeverityManager;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<CrashSeverity> crashSeverities = crashSeverityManager.getAllDistinct();
        crashSeverities.forEach(crashSeverity -> countResults.add(countOccurrences(crashSeverity, crashes)));
        countResults.add(countNotSpecified(crashes));
        return countResults;
    }

    private CountResult countOccurrences(CrashSeverity crashSeverity, List<Crash> crashes) {
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        crashes.stream().filter(crash -> crashSeverity.equals(crash.getCrashSeverity()))
                .forEach(crash -> this.incrementCounts(countResultBuilder, crash));
        return countResultBuilder.setAttribute(crashSeverity).build();
    }

    private CountResult countNotSpecified(List<Crash> crashes) {
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        crashes.stream().filter(crash -> crash.getCrashSeverity() == null)
                .forEach(crash -> this.incrementCounts(countResultBuilder, crash));
        return countResultBuilder.setAttribute(NameIdModel.createNotSpecifiedInstance()).build();
    }
}