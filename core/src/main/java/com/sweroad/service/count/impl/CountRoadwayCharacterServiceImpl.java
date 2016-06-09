package com.sweroad.service.count.impl;

import com.sweroad.model.CountResult;
import com.sweroad.model.Crash;
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
public class CountRoadwayCharacterServiceImpl implements CountAttributeService {

    @Autowired
    private GenericManager<RoadwayCharacter, Long> roadwayCharacterManager;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<RoadwayCharacter> roadwayCharacters = roadwayCharacterManager.getAllDistinct();
        roadwayCharacters.forEach(roadwayCharacter -> countResults.add(countOccurrences(roadwayCharacter, crashes)));
        return countResults;
    }

    private CountResult countOccurrences(RoadwayCharacter roadwayCharacter, List<Crash> crashes) {
        long crashCount = 0, vehicleCount = 0, casualtyCount = 0;
        for (Crash crash : crashes) {
            if (crash.getRoadwayCharacter().equals(roadwayCharacter)) {
                crashCount++;
                vehicleCount += crash.getVehicleCount();
                casualtyCount += crash.getCasualtyCount();
            }
        }
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        return countResultBuilder.setAttribute(roadwayCharacter).setCrashCount(crashCount)
                .setVehicleCount(vehicleCount).setCasualtyCount(casualtyCount).build();
    }
}