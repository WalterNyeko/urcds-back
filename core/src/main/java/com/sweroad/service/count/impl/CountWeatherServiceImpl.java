package com.sweroad.service.count.impl;

import com.sweroad.model.CountResult;
import com.sweroad.model.Crash;
import com.sweroad.model.NameIdModel;
import com.sweroad.model.Weather;
import com.sweroad.service.GenericService;
import com.sweroad.service.count.CountAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 5/31/16.
 */
@Service("countWeatherService")
public class CountWeatherServiceImpl extends BaseCountService implements CountAttributeService {

    @Autowired
    private GenericService<Weather, Long> weatherService;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<NameIdModel> weatherList = this.prepareAttributes(weatherService.getAllDistinct());
        weatherList.forEach(weather -> countResults.add(countOccurrences(weather, crashes)));
        return countResults;
    }

    private CountResult countOccurrences(NameIdModel weather, List<Crash> crashes) {
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        crashes.stream().filter(crash -> this.matchAttributes(weather, crash.getWeather()))
                .forEach(crash -> this.incrementCounts(countResultBuilder, crash));
        return countResultBuilder.setAttribute(weather).build();
    }
}