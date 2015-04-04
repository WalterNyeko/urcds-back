package com.sweroad.service.impl;

import com.sweroad.model.*;
import com.sweroad.service.CrashManager;
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
        if (atLeastOneYearMonthProvided(searchCriteria)) {
            meets = meetsMonthOnlyCriteria(searchCriteria, crash);
            if (!meets) {
                return false;
            }
            setDatesBasedOnYearMonthCriteria(searchCriteria);
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
        if (bothMonthProvidedButNoYears(searchCriteria)) {
            if (crash.getCrashDateTime() == null) {
                return false;
            }
            int crashMonth = DateUtil.getOneBasedMonthFromDate(crash.getCrashDateTime());
            return crashMonth >= searchCriteria.getStartMonth() && crashMonth <= searchCriteria.getEndMonth();
        }
        return true;
    }

    private void setDatesBasedOnYearMonthCriteria(SearchCriteria searchCriteria) {
        if (bothYearsMonthsProvided(searchCriteria)) {
            setStartAndEndDatesForStartAndEndYearMonth(searchCriteria);
        } else if (bothYearsProvidedButNoMonths(searchCriteria)) {
            setStartAndEndDatesForStartAndEndYear(searchCriteria);
        } else if (startYearAndMonthButOnlyEndYearProvided(searchCriteria)) {
            setStartAndEndDatesForStartYearMonthAndEndYear(searchCriteria);
        } else if (endYearAndMonthButOnlyStartYearProvided(searchCriteria)) {
            setStartAndEndDatesForStartYearAndEndYearMonth(searchCriteria);
        } else if (onlyStartYearProvided(searchCriteria)) {
            setStartDateForStartYear(searchCriteria);
        } else if (onlyStartYearAndMonthProvided(searchCriteria)) {
            setStartDateForStartYearAndMonth(searchCriteria);
        } else if (onlyEndYearProvided(searchCriteria)) {
            setEndDateForEndYear(searchCriteria);
        } else if (onlyEndYearAndMonthProvided(searchCriteria)) {
            setEndDateForEndYearAndMonth(searchCriteria);
        }
    }

    private void setStartAndEndDatesForStartAndEndYearMonth(SearchCriteria searchCriteria) {
        Date startDate = DateUtil.createDateFromYearMonthDay(searchCriteria.getStartYear(), searchCriteria.getStartMonth(), 1);
        Date endDate = DateUtil.createDateFromYearMonthDay(searchCriteria.getEndYear(), searchCriteria.getEndMonth(),
                DateUtil.getMaximumDateInYearMonth(searchCriteria.getEndYear(), searchCriteria.getEndMonth()));
        searchCriteria.setStartDate(startDate);
        searchCriteria.setEndDate(endDate);
    }

    private void setStartAndEndDatesForStartAndEndYear(SearchCriteria searchCriteria) {
        Date startDate = DateUtil.createDateFromYearMonthDay(searchCriteria.getStartYear(), 1, 1);
        Date endDate = DateUtil.createDateFromYearMonthDay(searchCriteria.getEndYear(), 12, 31);
        searchCriteria.setStartDate(startDate);
        searchCriteria.setEndDate(endDate);
    }

    private void setStartAndEndDatesForStartYearMonthAndEndYear(SearchCriteria searchCriteria) {
        Date startDate = DateUtil.createDateFromYearMonthDay(searchCriteria.getStartYear(), searchCriteria.getStartMonth(), 1);
        Date endDate = DateUtil.createDateFromYearMonthDay(searchCriteria.getEndYear(), 12, 31);
        searchCriteria.setStartDate(startDate);
        searchCriteria.setEndDate(endDate);
    }

    private void setStartAndEndDatesForStartYearAndEndYearMonth(SearchCriteria searchCriteria) {
        Date startDate = DateUtil.createDateFromYearMonthDay(searchCriteria.getStartYear(), 1, 1);
        Date endDate = DateUtil.createDateFromYearMonthDay(searchCriteria.getEndYear(), searchCriteria.getEndMonth(),
                DateUtil.getMaximumDateInYearMonth(searchCriteria.getEndYear(), searchCriteria.getEndMonth()));
        searchCriteria.setStartDate(startDate);
        searchCriteria.setEndDate(endDate);
    }

    private void setStartDateForStartYear(SearchCriteria searchCriteria) {
        Date startDate = DateUtil.createDateFromYearMonthDay(searchCriteria.getStartYear(), 1, 1);
        searchCriteria.setStartDate(startDate);
    }

    private void setStartDateForStartYearAndMonth(SearchCriteria searchCriteria) {
        Date startDate = DateUtil.createDateFromYearMonthDay(searchCriteria.getStartYear(), searchCriteria.getStartMonth(), 1);
        searchCriteria.setStartDate(startDate);
    }

    private void setEndDateForEndYear(SearchCriteria searchCriteria) {
        Date endDate = DateUtil.createDateFromYearMonthDay(searchCriteria.getEndYear(), 12, 31);
        searchCriteria.setEndDate(endDate);
    }

    private void setEndDateForEndYearAndMonth(SearchCriteria searchCriteria) {
        Date endDate = DateUtil.createDateFromYearMonthDay(searchCriteria.getEndYear(), searchCriteria.getEndMonth(),
                DateUtil.getMaximumDateInYearMonth(searchCriteria.getEndYear(), searchCriteria.getEndMonth()));
        searchCriteria.setEndDate(endDate);
    }

    private boolean bothYearsMonthsProvided(SearchCriteria searchCriteria) {
        return (searchCriteria.getStartYear() != null && searchCriteria.getStartMonth() != null
                && searchCriteria.getEndYear() != null && searchCriteria.getEndMonth() != null);
    }

    private boolean bothYearsProvidedButNoMonths(SearchCriteria searchCriteria) {
        return (searchCriteria.getStartYear() != null && searchCriteria.getEndYear() != null
                && searchCriteria.getStartMonth() == null && searchCriteria.getEndMonth() == null);
    }

    private boolean bothMonthProvidedButNoYears(SearchCriteria searchCriteria) {
        return (searchCriteria.getStartYear() == null && searchCriteria.getEndYear() == null
                && searchCriteria.getStartMonth() != null && searchCriteria.getEndMonth() != null);
    }

    private boolean startYearAndMonthButOnlyEndYearProvided(SearchCriteria searchCriteria) {
        return (searchCriteria.getStartYear() != null && searchCriteria.getStartMonth() != null
                && searchCriteria.getEndYear() != null && searchCriteria.getEndMonth() == null);
    }

    private boolean endYearAndMonthButOnlyStartYearProvided(SearchCriteria searchCriteria) {
        return (searchCriteria.getStartYear() != null && searchCriteria.getStartMonth() == null
                && searchCriteria.getEndYear() != null && searchCriteria.getEndMonth() != null);
    }

    private boolean onlyStartYearProvided(SearchCriteria searchCriteria) {
        return (searchCriteria.getStartYear() != null && searchCriteria.getStartMonth() == null
                && searchCriteria.getEndYear() == null && searchCriteria.getEndMonth() == null);
    }

    private boolean onlyStartYearAndMonthProvided(SearchCriteria searchCriteria) {
        return (searchCriteria.getStartYear() != null && searchCriteria.getStartMonth() != null
                && searchCriteria.getEndYear() == null && searchCriteria.getEndMonth() == null);
    }

    private boolean onlyEndYearProvided(SearchCriteria searchCriteria) {
        return (searchCriteria.getStartYear() == null && searchCriteria.getStartMonth() == null
                && searchCriteria.getEndYear() != null && searchCriteria.getEndMonth() == null);
    }

    private boolean onlyEndYearAndMonthProvided(SearchCriteria searchCriteria) {
        return (searchCriteria.getStartYear() == null && searchCriteria.getStartMonth() == null
                && searchCriteria.getEndYear() != null && searchCriteria.getEndMonth() != null);
    }

    private boolean atLeastOneYearMonthProvided(SearchCriteria searchCriteria) {
        return (searchCriteria.getStartYear() != null || searchCriteria.getStartMonth() != null
                || searchCriteria.getEndYear() != null || searchCriteria.getEndMonth() != null);
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