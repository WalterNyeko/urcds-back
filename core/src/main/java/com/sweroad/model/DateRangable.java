package com.sweroad.model;

import java.util.Date;

/**
 * Created by Frank on 4/18/15.
 */
public interface DateRangable {
    Integer getStartYear();
    Integer getStartMonth();
    Integer getEndYear();
    Integer getEndMonth();
    Integer getStartHour();
    Integer getEndHour();
    void setStartDate(Date date);
    void setEndDate(Date date);
}