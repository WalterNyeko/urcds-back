package com.sweroad.service;

import com.sweroad.model.DateRangable;

/**
 * Created by Frank on 4/18/15.
 */
public interface DateRangeService {
    void setDatesBasedOnYearMonthCriteria(DateRangable dateRangable);
    boolean bothMonthProvidedButNoYears(DateRangable dateRangable);
    boolean onlyStartMonthProvided(DateRangable dateRangable);
    boolean onlyEndMonthProvided(DateRangable dateRangable);
    boolean atLeastOneYearMonthProvided(DateRangable dateRangable);
}
