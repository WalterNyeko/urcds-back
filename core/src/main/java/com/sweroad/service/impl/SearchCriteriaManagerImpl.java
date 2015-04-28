package com.sweroad.service.impl;

import com.sweroad.model.*;
import com.sweroad.service.CrashManager;
import com.sweroad.service.DateRangeManager;
import com.sweroad.service.SearchCriteriaManager;
import com.sweroad.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Frank on 11/17/14.
 */
@Service("searchCriteriaManager")
public class SearchCriteriaManagerImpl implements SearchCriteriaManager {

    @Autowired
    private CrashManager crashManager;
    @Autowired
    private DateRangeManager dateRangeManager;

    @Override
    public List<Crash> getCrashesByCriteria(SearchCriteria searchCriteria) {
        List<Crash> allCrashes = crashManager.getAllDistinct();
        List<Crash> filteredCrashes = new ArrayList<Crash>();
        for (Crash crash : allCrashes) {
            if (!crash.isRemoved() && meetsCriteria(searchCriteria, crash)) {
                filteredCrashes.add(crash);
            }
        }
        return filteredCrashes;
    }

    private boolean meetsCriteria(SearchCriteria searchCriteria, Crash crash) {
        if (!meetsPoliceStationCriteria(searchCriteria, crash)) {
            return false;
        }
        if (!meetsDistrictCriteria(searchCriteria, crash)) {
            return false;
        }
        if (!meetsCrashSeverityCriteria(searchCriteria, crash)) {
            return false;
        }
        if (!meetsCollisionTypeCriteria(searchCriteria, crash)) {
            return false;
        }
        if (!meetsCrashCauseCriteria(searchCriteria, crash)) {
            return false;
        }
        if (!meetsVehicleTypeCriteria(searchCriteria, crash)) {
            return false;
        }
        if (!meetsDateCriteria(searchCriteria, crash)) {
            return false;
        }
        return true;
    }

    private boolean meetsDateCriteria(SearchCriteria searchCriteria, Crash crash) {
        boolean meets;
        if (dateRangeManager.atLeastOneYearMonthProvided(searchCriteria)) {
            meets = meetsMonthOnlyCriteria(searchCriteria, crash);
            if (!meets) {
                return false;
            }
            dateRangeManager.setDatesBasedOnYearMonthCriteria(searchCriteria);
        }
        if ((searchCriteria.getStartDate() != null || searchCriteria.getEndDate() != null)
                && crash.getCrashDateTime() == null) {
            return false;
        }
        if (searchCriteria.getStartDate() != null && crash.getCrashDateTime() != null) {
            meets = crash.getCrashDateTime().compareTo(searchCriteria.getStartDate()) >= 0;
            if (!meets) {
                return false;
            }
        }
        if (searchCriteria.getEndDate() != null && crash.getCrashDateTime() != null) {
            meets = crash.getCrashDateTime().compareTo(searchCriteria.getEndDate()) <= 0;
            if (!meets) {
                return false;
            }
        }
        return true;
    }

    private boolean meetsMonthOnlyCriteria(SearchCriteria searchCriteria, Crash crash) {
        if (dateRangeManager.bothMonthProvidedButNoYears(searchCriteria)) {
            if (crash.getCrashDateTime() == null) {
                return false;
            }
            int crashMonth = DateUtil.getOneBasedMonthFromDate(crash.getCrashDateTime());
            return crashMonth >= searchCriteria.getStartMonth() && crashMonth <= searchCriteria.getEndMonth();
        }
        return true;
    }

    private boolean meetsDistrictCriteria(SearchCriteria searchCriteria, Crash crash) {
        if (searchCriteria.getDistrict().getId() != null) {
            if (crash.getPoliceStation() != null && crash.getPoliceStation().getDistrict()
                    .equals(searchCriteria.getDistrict())) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    private boolean meetsPoliceStationCriteria(SearchCriteria searchCriteria, Crash crash) {
        if (searchCriteria.getCrash().getPoliceStation().getId() != null) {
            if (crash.getPoliceStation() != null && crash.getPoliceStation()
                    .equals(searchCriteria.getCrash().getPoliceStation())) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    private boolean meetsVehicleTypeCriteria(SearchCriteria searchCriteria, Crash crash) {
        if (searchCriteria.getVehicleType().getId() != null) {
            if(crash.getVehicles() != null) {
                for(Vehicle vehicle : crash.getVehicles()) {
                    if (vehicle.getVehicleType() != null && vehicle.getVehicleType().getId().equals(searchCriteria.getVehicleType().getId())) {
                        return true;
                    }
                }
            }
        } else {
            return true;
        }
        return false;
    }
    private boolean meetsCrashSeverityCriteria(SearchCriteria searchCriteria, Crash crash) {
        if (searchCriteria.getCrash().getCrashSeverity().getId() != null) {
            if (crash.getCrashSeverity() != null && crash.getCrashSeverity()
                    .equals(searchCriteria.getCrash().getCrashSeverity())) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    private boolean meetsCollisionTypeCriteria(SearchCriteria searchCriteria, Crash crash) {
        if (searchCriteria.getCrash().getCollisionType().getId() != null) {
            if (crash.getCollisionType() != null && crash.getCollisionType()
                    .equals(searchCriteria.getCrash().getCollisionType())) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    private boolean meetsCrashCauseCriteria(SearchCriteria searchCriteria, Crash crash) {
        if (searchCriteria.getCrash().getCrashCause().getId() != null) {
            if (crash.getCrashCause() != null && crash.getCrashCause()
                    .equals(searchCriteria.getCrash().getCrashCause())) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }
}