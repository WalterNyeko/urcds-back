package com.sweroad.service.count.impl;

import com.sweroad.model.CountResult;
import com.sweroad.model.Crash;
import com.sweroad.model.NameIdModel;
import com.sweroad.model.Weather;
import com.sweroad.service.GenericManager;
import com.sweroad.service.count.CountAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Frank on 5/31/16.
 */
@Service("countWeatherService")
public class CountWeatherServiceImpl extends BaseCountService implements CountAttributeService {

    @Autowired
    private GenericManager<Weather, Long> weatherManager;

    @Override
    public List<CountResult> countCrashes(List<Crash> crashes) {
        List<CountResult> countResults = new ArrayList<>();
        List<Weather> weatherList = weatherManager.getAllDistinct();
        weatherList.forEach(weather -> countResults.add(countOccurrences(weather, crashes)));
        countResults.add(countNotSpecified(crashes));
        return countResults;
    }

    private CountResult countOccurrences(Weather weather, List<Crash> crashes) {
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        crashes.stream().filter(crash -> weather.equals(crash.getWeather()))
                .forEach(crash -> this.incrementCounts(countResultBuilder, crash));
        return countResultBuilder.setAttribute(weather).build();
    }

    private CountResult countNotSpecified(List<Crash> crashes) {
        CountResult.CountResultBuilder countResultBuilder = new CountResult.CountResultBuilder();
        crashes.stream().filter(crash -> crash.getWeather() == null)
                .forEach(crash -> this.incrementCounts(countResultBuilder, crash));
        return countResultBuilder.setAttribute(NameIdModel.createNotSpecifiedInstance()).build();
    }
}