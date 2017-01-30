package com.sweroad.service.count.impl;

import com.sweroad.model.CountResult;
import com.sweroad.model.Crash;
import com.sweroad.model.NameIdModel;
import com.sweroad.model.RoadwayCharacter;
import com.sweroad.service.GenericManager;
import com.sweroad.service.count.CountAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 5/31/16.
 */
@Service("countRoadwayCharacterService")
public class CountRoadwayCharacterServiceImpl extends BaseCountService implements CountAttributeService {

    @Autowired
    private GenericManager<RoadwayCharacter, Long> roadwayCharacterService;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<NameIdModel> roadwayCharacters = this.prepareAttributes(roadwayCharacterService.getAllDistinct());
        roadwayCharacters.forEach(roadwayCharacter -> countResults.add(countOccurrences(roadwayCharacter, crashes)));
        return countResults;
    }

    private CountResult countOccurrences(NameIdModel roadwayCharacter, List<Crash> crashes) {
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        crashes.stream().filter(crash -> this.matchAttributes(roadwayCharacter, crash.getRoadwayCharacter()))
                .forEach(crash -> this.incrementCounts(countResultBuilder, crash));
        return countResultBuilder.setAttribute(roadwayCharacter).build();
    }
}