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
@Service("countJunctionTypeService")
public class CountJunctionTypeServiceImpl extends BaseCountService implements CountAttributeService {

    @Autowired
    private GenericService<JunctionType, Long> junctionTypeService;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<NameIdModel> junctionTypes = this.prepareAttributes(junctionTypeService.getAllDistinct());
        junctionTypes.forEach(junctionType -> countResults.add(countOccurrences(junctionType, crashes)));
        return countResults;
    }

    private CountResult countOccurrences(NameIdModel junctionType, List<Crash> crashes) {
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        crashes.stream().filter(crash -> this.matchAttributes(junctionType, crash.getJunctionType()))
                .forEach(crash -> this.incrementCounts(countResultBuilder, crash));
        return countResultBuilder.setAttribute(junctionType).build();
    }
}