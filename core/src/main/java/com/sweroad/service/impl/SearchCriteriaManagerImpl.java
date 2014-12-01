package com.sweroad.service.impl;

import com.sweroad.model.Crash;
import com.sweroad.model.SearchCriteria;
import com.sweroad.service.CrashManager;
import com.sweroad.service.SearchCriteriaManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 11/17/14.
 */
public class SearchCriteriaManagerImpl implements SearchCriteriaManager {

    @Autowired
    private CrashManager crashManager;
    private SearchCriteria searchCriteria;

    @Override
    public List<Crash> getCrashesByCriteria(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
        List<Crash> allCrashes = crashManager.getAllDistinct();
        List<Crash> filteredCrashes = new ArrayList<Crash>();
        for (Crash crash : allCrashes) {
            if (meetsCriteria(crash)) {
                filteredCrashes.add(crash);
            }
        }
        return filteredCrashes;
    }

    private boolean meetsCriteria(Crash crash) {
        if (!meetsDistrictCriteria(crash)) {
            return false;
        }
        if (!meetsCrashSeverityCriteria(crash)) {
            return false;
        }
        if (!meetsCollisionTypeCriteria(crash)) {
            return false;
        }
        if (!meetsCrashCauseCriteria(crash)) {
            return false;
        }
        if (!meetsDateCriteria(crash)) {
            return false;
        }
        return true;
    }

    private boolean meetsDateCriteria(Crash crash) {
        boolean meets;
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

    private boolean meetsDistrictCriteria(Crash crash) {
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

    private boolean meetsCrashSeverityCriteria(Crash crash) {
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

    private boolean meetsCollisionTypeCriteria(Crash crash) {
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

    private boolean meetsCrashCauseCriteria(Crash crash) {
        if (searchCriteria.getCrash().getMainCrashCause().getId() != null) {
            if (crash.getMainCrashCause() != null && crash.getMainCrashCause()
                    .equals(searchCriteria.getCrash().getMainCrashCause())) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }
}