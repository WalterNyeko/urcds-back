package com.sweroad.service.impl;

import com.sweroad.model.DateRangable;
import com.sweroad.model.DateRangable;
import com.sweroad.service.DateRangeManager;
import com.sweroad.util.DateUtil;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Frank on 4/18/15.
 */
@Service("dateRangeManager")
public class DateRangeManagerImpl implements DateRangeManager {

    public void setDatesBasedOnYearMonthCriteria(DateRangable dateRangable) {
        if (bothYearsMonthsProvided(dateRangable)) {
            setStartAndEndDatesForStartAndEndYearMonth(dateRangable);
        } else if (bothYearsProvidedButNoMonths(dateRangable)) {
            setStartAndEndDatesForStartAndEndYear(dateRangable);
        } else if (startYearAndMonthButOnlyEndYearProvided(dateRangable)) {
            setStartAndEndDatesForStartYearMonthAndEndYear(dateRangable);
        } else if (endYearAndMonthButOnlyStartYearProvided(dateRangable)) {
            setStartAndEndDatesForStartYearAndEndYearMonth(dateRangable);
        } else if (onlyStartYearProvided(dateRangable)) {
            setStartDateForStartYear(dateRangable);
        } else if (onlyStartYearAndMonthProvided(dateRangable)) {
            setStartDateForStartYearAndMonth(dateRangable);
        } else if (onlyEndYearProvided(dateRangable)) {
            setEndDateForEndYear(dateRangable);
        } else if (onlyEndYearAndMonthProvided(dateRangable)) {
            setEndDateForEndYearAndMonth(dateRangable);
        }
    }

    private void setStartAndEndDatesForStartAndEndYearMonth(DateRangable dateRangable) {
        Date startDate = DateUtil.createDateFromYearMonthDay(dateRangable.getStartYear(), dateRangable.getStartMonth(), 1);
        Date endDate = DateUtil.createDateFromYearMonthDay(dateRangable.getEndYear(), dateRangable.getEndMonth(),
                DateUtil.getMaximumDateInYearMonth(dateRangable.getEndYear(), dateRangable.getEndMonth()));
        dateRangable.setStartDate(startDate);
        dateRangable.setEndDate(endDate);
    }

    private void setStartAndEndDatesForStartAndEndYear(DateRangable dateRangable) {
        Date startDate = DateUtil.createDateFromYearMonthDay(dateRangable.getStartYear(), 1, 1);
        Date endDate = DateUtil.createDateFromYearMonthDay(dateRangable.getEndYear(), 12, 31);
        dateRangable.setStartDate(startDate);
        dateRangable.setEndDate(endDate);
    }

    private void setStartAndEndDatesForStartYearMonthAndEndYear(DateRangable dateRangable) {
        Date startDate = DateUtil.createDateFromYearMonthDay(dateRangable.getStartYear(), dateRangable.getStartMonth(), 1);
        Date endDate = DateUtil.createDateFromYearMonthDay(dateRangable.getEndYear(), 12, 31);
        dateRangable.setStartDate(startDate);
        dateRangable.setEndDate(endDate);
    }

    public void setStartAndEndDatesForStartYearAndEndYearMonth(DateRangable dateRangable) {
        Date startDate = DateUtil.createDateFromYearMonthDay(dateRangable.getStartYear(), 1, 1);
        Date endDate = DateUtil.createDateFromYearMonthDay(dateRangable.getEndYear(), dateRangable.getEndMonth(),
                DateUtil.getMaximumDateInYearMonth(dateRangable.getEndYear(), dateRangable.getEndMonth()));
        dateRangable.setStartDate(startDate);
        dateRangable.setEndDate(endDate);
    }

    private void setStartDateForStartYear(DateRangable dateRangable) {
        Date startDate = DateUtil.createDateFromYearMonthDay(dateRangable.getStartYear(), 1, 1);
        dateRangable.setStartDate(startDate);
    }

    private void setStartDateForStartYearAndMonth(DateRangable dateRangable) {
        Date startDate = DateUtil.createDateFromYearMonthDay(dateRangable.getStartYear(), dateRangable.getStartMonth(), 1);
        dateRangable.setStartDate(startDate);
    }

    private void setEndDateForEndYear(DateRangable dateRangable) {
        Date endDate = DateUtil.createDateFromYearMonthDay(dateRangable.getEndYear(), 12, 31);
        dateRangable.setEndDate(endDate);
    }

    private void setEndDateForEndYearAndMonth(DateRangable dateRangable) {
        Date endDate = DateUtil.createDateFromYearMonthDay(dateRangable.getEndYear(), dateRangable.getEndMonth(),
                DateUtil.getMaximumDateInYearMonth(dateRangable.getEndYear(), dateRangable.getEndMonth()));
        dateRangable.setEndDate(endDate);
    }

    public boolean bothMonthProvidedButNoYears(DateRangable dateRangable) {
        return (dateRangable.getStartYear() == null && dateRangable.getEndYear() == null
                && dateRangable.getStartMonth() != null && dateRangable.getEndMonth() != null);
    }

    public boolean onlyStartMonthProvided(DateRangable dateRangable) {
        return (dateRangable.getStartMonth() != null && dateRangable.getStartYear() == null
                && dateRangable.getEndMonth() == null && dateRangable.getEndYear() == null);
    }

    public boolean onlyEndMonthProvided(DateRangable dateRangable) {
        return (dateRangable.getStartMonth() == null && dateRangable.getStartYear() == null
                && dateRangable.getEndMonth() != null && dateRangable.getEndYear() == null);
    }

    public boolean atLeastOneYearMonthProvided(DateRangable dateRangable) {
        return (dateRangable.getStartYear() != null || dateRangable.getStartMonth() != null
                || dateRangable.getEndYear() != null || dateRangable.getEndMonth() != null);
    }

    private boolean bothYearsMonthsProvided(DateRangable dateRangable) {
        return (dateRangable.getStartYear() != null && dateRangable.getStartMonth() != null
                && dateRangable.getEndYear() != null && dateRangable.getEndMonth() != null);
    }

    private boolean bothYearsProvidedButNoMonths(DateRangable dateRangable) {
        return (dateRangable.getStartYear() != null && dateRangable.getEndYear() != null
                && dateRangable.getStartMonth() == null && dateRangable.getEndMonth() == null);
    }

    private boolean startYearAndMonthButOnlyEndYearProvided(DateRangable dateRangable) {
        return (dateRangable.getStartYear() != null && dateRangable.getStartMonth() != null
                && dateRangable.getEndYear() != null && dateRangable.getEndMonth() == null);
    }

    private boolean endYearAndMonthButOnlyStartYearProvided(DateRangable dateRangable) {
        return (dateRangable.getStartYear() != null && dateRangable.getStartMonth() == null
                && dateRangable.getEndYear() != null && dateRangable.getEndMonth() != null);
    }

    private boolean onlyStartYearProvided(DateRangable dateRangable) {
        return (dateRangable.getStartYear() != null && dateRangable.getStartMonth() == null
                && dateRangable.getEndYear() == null && dateRangable.getEndMonth() == null);
    }

    private boolean onlyStartYearAndMonthProvided(DateRangable dateRangable) {
        return (dateRangable.getStartYear() != null && dateRangable.getStartMonth() != null
                && dateRangable.getEndYear() == null && dateRangable.getEndMonth() == null);
    }

    private boolean onlyEndYearProvided(DateRangable dateRangable) {
        return (dateRangable.getStartYear() == null && dateRangable.getStartMonth() == null
                && dateRangable.getEndYear() != null && dateRangable.getEndMonth() == null);
    }

    private boolean onlyEndYearAndMonthProvided(DateRangable dateRangable) {
        return (dateRangable.getStartYear() == null && dateRangable.getStartMonth() == null
                && dateRangable.getEndYear() != null && dateRangable.getEndMonth() != null);
    }
}
