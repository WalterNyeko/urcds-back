package com.sweroad.service.count.impl;

import com.sweroad.Constants;
import com.sweroad.model.CountResult;
import com.sweroad.model.Countable;
import com.sweroad.model.NameIdModel;

import java.util.List;

/**
 * Created by Frank on 6/17/16.
 */
public class BaseCountService {

    protected void incrementCounts(CountResult.CountResultBuilder countResultBuilder, Countable countable) {
        countResultBuilder.incrementCrashCount(countable.getCrashCount());
        countResultBuilder.incrementVehicleCount(countable.getVehicleCount());
        countResultBuilder.incrementCasualtyCount(countable.getCasualtyCount());
    }

    protected boolean matchAttributes(NameIdModel candidate, NameIdModel attribute) {
        if (candidate.getId().equals(Constants.NOT_SPECIFIED_ID)) {
            return attribute == null;
        }
        return candidate.equals(attribute);
    }

    protected List<NameIdModel> prepareAttributes(List<? extends NameIdModel> attributes) {
        List<NameIdModel> attr = (List<NameIdModel>) attributes;
        attr.add(NameIdModel.createNotSpecifiedInstance());
        return attr;
    }
}
