package com.sweroad.model;

/**
 * Created by Frank on 5/7/15.
 */
public class TimeRange extends LabelValue {
    private Integer hour;

    public TimeRange(Integer hour) {
        this.hour = hour;
        this.setLabel(this.getLabel());
        this.setValue(hour.toString());
    }

    public Integer getHour() {
        return this.hour;
    }

    @Override
    public String getLabel() {
        String hourPart = hour < 10 ? "0" : "";
        hourPart += hour;
        return hourPart.concat(":00 - ").concat(hourPart).concat(":59");
    }

    @Override
    public int compareTo(Object o) {
        return this.hour.compareTo(((TimeRange)o).getHour());
    }
}
