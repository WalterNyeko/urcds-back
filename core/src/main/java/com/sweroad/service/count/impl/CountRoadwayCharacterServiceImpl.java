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
@Service("countRoadwayCharacterService")
public class CountRoadwayCharacterServiceImpl extends BaseCountService implements CountAttributeService {

    @Autowired
    private GenericManager<RoadwayCharacter, Long> roadwayCharacterManager;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<RoadwayCharacter> roadwayCharacters = roadwayCharacterManager.getAllDistinct();
        roadwayCharacters.forEach(roadwayCharacter -> countResults.add(countOccurrences(roadwayCharacter, crashes)));
        countResults.add(countNotSpecified(crashes));
        return countResults;
    }

    private CountResult countOccurrences(RoadwayCharacter roadwayCharacter, List<Crash> crashes) {
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        crashes.stream().filter(crash -> roadwayCharacter.equals(crash.getRoadwayCharacter()))
                .forEach(crash -> this.incrementCounts(countResultBuilder, crash));
        return countResultBuilder.setAttribute(roadwayCharacter).build();
    }

    private CountResult countNotSpecified(List<Crash> crashes) {
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        crashes.stream().filter(crash -> crash.getRoadwayCharacter() == null)
                .forEach(crash -> this.incrementCounts(countResultBuilder, crash));
        return countResultBuilder.setAttribute(NameIdModel.createNotSpecifiedInstance()).build();
    }
}