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
    private GenericManager<SurfaceType, Long> surfaceTypeManager;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<SurfaceType> surfaceTypes = surfaceTypeManager.getAllDistinct();
        surfaceTypes.forEach(surfaceType -> countResults.add(countOccurrences(surfaceType, crashes)));
        countResults.add(countNotSpecified(crashes));
        return countResults;
    }

    private CountResult countOccurrences(SurfaceType surfaceType, List<Crash> crashes) {
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        crashes.stream().filter(crash -> surfaceType.equals(crash.getSurfaceType()))
                .forEach(crash -> this.incrementCounts(countResultBuilder, crash));
        return countResultBuilder.setAttribute(surfaceType).build();
    }

    private CountResult countNotSpecified(List<Crash> crashes) {
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        crashes.stream().filter(crash -> crash.getSurfaceType() == null)
                .forEach(crash -> this.incrementCounts(countResultBuilder, crash));
        return countResultBuilder.setAttribute(NameIdModel.createNotSpecifiedInstance()).build();
    }
}