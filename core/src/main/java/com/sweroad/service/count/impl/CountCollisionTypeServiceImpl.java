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
@Service("countCollisionTypeService")
public class CountCollisionTypeServiceImpl extends BaseCountService implements CountAttributeService {

    @Autowired
    private GenericManager<CollisionType, Long> collisionTypeService;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<NameIdModel> collisionTypes = this.prepareAttributes(collisionTypeService.getAllDistinct());
        collisionTypes.forEach(collisionType -> countResults.add(countOccurrences(collisionType, crashes)));
        return countResults;
    }

    private CountResult countOccurrences(NameIdModel collisionType, List<Crash> crashes) {
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        crashes.stream().filter(crash -> this.matchAttributes(collisionType, crash.getCollisionType()))
                .forEach(crash -> this.incrementCounts(countResultBuilder, crash));
        return countResultBuilder.setAttribute(collisionType).build();
    }
}