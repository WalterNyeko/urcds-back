package com.sweroad.service.count.impl;

import com.sweroad.model.CountResult;
import com.sweroad.model.Crash;
import com.sweroad.model.CrashCause;
import com.sweroad.model.NameIdModel;
import com.sweroad.service.GenericManager;
import com.sweroad.service.count.CountAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 5/31/16.
 */
@Service("countCrashCauseService")
public class CountCrashCauseServiceImpl extends BaseCountService implements CountAttributeService {

    @Autowired
    private GenericManager<CrashCause, Long> crashCauseService;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<NameIdModel> crashCauses = this.prepareAttributes(crashCauseService.getAllDistinct());
        crashCauses.forEach(crashCause -> countResults.add(countOccurrences(crashCause, crashes)));
        return countResults;
    }

    private CountResult countOccurrences(NameIdModel crashCause, List<Crash> crashes) {
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        crashes.stream().filter(crash -> this.matchAttributes(crashCause, crash.getCrashCause()))
                .forEach(crash -> this.incrementCounts(countResultBuilder, crash));
        return countResultBuilder.setAttribute(crashCause).build();
    }
}