package com.sweroad.service.count.impl;

import com.sweroad.model.CountResult;
import com.sweroad.model.Crash;
import com.sweroad.model.NameIdModel;
import com.sweroad.model.RoadSurface;
import com.sweroad.service.GenericManager;
import com.sweroad.service.count.CountAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 5/31/16.
 */
@Service("countRoadSurfaceService")
public class CountRoadSurfaceServiceImpl extends BaseCountService implements CountAttributeService {

    @Autowired
    private GenericManager<RoadSurface, Long> roadSurfaceService;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<NameIdModel> roadSurfaces = this.prepareAttributes(roadSurfaceService.getAllDistinct());
        roadSurfaces.forEach(roadSurface -> countResults.add(countOccurrences(roadSurface, crashes)));
        return countResults;
    }

    private CountResult countOccurrences(NameIdModel roadSurface, List<Crash> crashes) {
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        crashes.stream().filter(crash -> this.matchAttributes(roadSurface, crash.getRoadSurface()))
                .forEach(crash -> this.incrementCounts(countResultBuilder, crash));
        return countResultBuilder.setAttribute(roadSurface).build();
    }
}