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
@Service("countJunctionTypeService")
public class CountJunctionTypeServiceImpl extends BaseCountService implements CountAttributeService {

    @Autowired
    private GenericManager<JunctionType, Long> junctionTypeManager;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<JunctionType> junctionTypes = junctionTypeManager.getAllDistinct();
        junctionTypes.forEach(junctionType -> countResults.add(countOccurrences(junctionType, crashes)));
        countResults.add(countNotSpecified(crashes));
        return countResults;
    }

    private CountResult countOccurrences(JunctionType junctionType, List<Crash> crashes) {
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        crashes.stream().filter(crash -> junctionType.equals(crash.getJunctionType()))
                .forEach(crash -> this.incrementCounts(countResultBuilder, crash));
        return countResultBuilder.setAttribute(junctionType).build();
    }

    private CountResult countNotSpecified(List<Crash> crashes) {
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        crashes.stream().filter(crash -> crash.getJunctionType() == null)
                .forEach(crash -> this.incrementCounts(countResultBuilder, crash));
        return countResultBuilder.setAttribute(NameIdModel.createNotSpecifiedInstance()).build();
    }
}