package com.sweroad.service.count.impl;

import com.sweroad.model.CountResult;
import com.sweroad.model.Countable;

/**
 * Created by Frank on 6/17/16.
 */
public class BaseCountService {

    protected void incrementCounts(CountResult.CountResultBuilder countResultBuilder, Countable countable) {
        countResultBuilder.incrementCrashCount(countable.getCrashCount());
        countResultBuilder.incrementVehicleCount(countable.getVehicleCount());
        countResultBuilder.incrementCasualtyCount(countable.getCasualtyCount());
    }
}
