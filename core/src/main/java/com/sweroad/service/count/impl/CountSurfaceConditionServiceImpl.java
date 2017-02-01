package com.sweroad.service.count.impl;

import com.sweroad.model.*;
import com.sweroad.service.GenericService;
import com.sweroad.service.count.CountAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 5/31/16.
 */
@Service("countSurfaceConditionService")
public class CountSurfaceConditionServiceImpl extends BaseCountService implements CountAttributeService {

    @Autowired
    private GenericService<SurfaceCondition, Long> surfaceConditionService;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<NameIdModel> surfaceConditions = this.prepareAttributes(surfaceConditionService.getAllDistinct());
        surfaceConditions.forEach(surfaceType -> countResults.add(countOccurrences(surfaceType, crashes)));
        return countResults;
    }

    private CountResult countOccurrences(NameIdModel surfaceCondition, List<Crash> crashes) {
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        crashes.stream().filter(crash -> this.matchAttributes(surfaceCondition, crash.getSurfaceCondition()))
                .forEach(crash -> this.incrementCounts(countResultBuilder, crash));
        return countResultBuilder.setAttribute(surfaceCondition).build();
    }
}