package com.sweroad.service.count.impl;

import com.sweroad.Constants;
import com.sweroad.model.*;

import java.util.List;
import java.util.stream.Collectors;

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

    protected boolean matchAgeRange(NameIdModel candidate, Integer age) {
        if (candidate.isSpecified()) {
            return ((AgeRange) candidate).contains(age);
        }
        return age == null;
    }

    protected boolean matchGender(NameIdModel candidate, String gender) {
        if (candidate.isSpecified()) {
            return ((Gender)candidate).getValue().equalsIgnoreCase(gender);
        }
        return gender == null;
    }

    protected boolean matchQuadstateOptions(NameIdModel candidate, Integer value) {
        if (candidate.isSpecified()) {
            return value != null && candidate.getId().intValue() == value.intValue();
        }
        return value == null;
    }

    protected List<NameIdModel> prepareAttributes(List<? extends NameIdModel> attributes) {
        List<NameIdModel> attr = (List<NameIdModel>) attributes;
        attr.add(NameIdModel.createNotSpecifiedInstance());
        return attr;
    }

    protected long countCasualties(Crash crash, List<Vehicle> vehicles) {
        List<Long> vehicleIds = vehicles.stream().map(Vehicle::getId).collect(Collectors.toList());
        long casualtyCount = crash.getCasualties().stream().filter(casualty ->
                casualty.isPassenger() && vehicleIds.contains(casualty.getVehicle().getId())).count();
        casualtyCount += vehicles.stream().map(Vehicle::getDriver).filter(driver -> driver.isCasualty()).count();
        return casualtyCount;
    }

    protected Countable getCounts(Crash crash, List<Vehicle> vehicles) {
        final int vehicleCount = vehicles.size();
        final long crashCount = vehicleCount > 0 ? 1 : 0;
        final long casualtyCount = this.countCasualties(crash, vehicles);
        return new Countable() {
            @Override
            public long getCrashCount() {
                return crashCount;
            }

            @Override
            public long getVehicleCount() {
                return vehicleCount;
            }

            @Override
            public long getCasualtyCount() {
                return casualtyCount;
            }
        };
    }
}
