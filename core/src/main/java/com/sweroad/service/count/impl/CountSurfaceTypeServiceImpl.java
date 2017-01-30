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
@Service("countSurfaceTypeService")
public class CountSurfaceTypeServiceImpl extends BaseCountService implements CountAttributeService {

    @Autowired
    private GenericManager<SurfaceType, Long> surfaceTypeService;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<NameIdModel> surfaceTypes = this.prepareAttributes(surfaceTypeService.getAllDistinct());
        surfaceTypes.forEach(surfaceType -> countResults.add(countOccurrences(surfaceType, crashes)));
        return countResults;
    }

    private CountResult countOccurrences(NameIdModel surfaceType, List<Crash> crashes) {
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        crashes.stream().filter(crash -> this.matchAttributes(surfaceType, crash.getSurfaceType()))
                .forEach(crash -> this.incrementCounts(countResultBuilder, crash));
        return countResultBuilder.setAttribute(surfaceType).build();
    }
}