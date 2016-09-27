package com.sweroad.model;

import com.sweroad.util.DateUtil;

import javax.persistence.*;
import javax.persistence.Transient;

import java.beans.*;
import java.util.Date;

/**
 * Created by Frank on 11/15/14.
 */
public class SearchCriteria extends BaseModel implements DateRangable {

    private Long id;
    private Crash crash;
    private String name;
    private Date endDate;
    private Date startDate;
    private District district;
    private String description;
    private VehicleType vehicleType;
    private Integer endYear;
    private Integer endHour;
    private Integer endMonth;
    private Integer startHour;
    private Integer startYear;
    private Integer startMonth;
    private String endDateString;
    private String startDateString;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public String getEndDateString() {
        return endDateString;
    }

    public Crash getCrash() {
        return crash;
    }

    public void setCrash(Crash crash) {
        this.crash = crash;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public Integer getStartMonth() {
        return startMonth;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public Integer getEndMonth() {
        return endMonth;
    }

    @Override
    public Integer getStartHour() {
        return startHour;
    }

    @Override
    public Integer getEndHour() {
        return endHour;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public String toJSON() {
        StringBuilder json = new StringBuilder("{");
        json.append(toJsonProperty(this.id, "id")).append(",");
        json.append(toJsonProperty(this.name, "name")).append(",");
        json.append(toJsonProperty(this.crash, "crash")).append(",");
        json.append(toJsonProperty(this.district, "district")).append(",");
        json.append(toJsonProperty(this.description, "description")).append(",");
        json.append(toJsonProperty(DateUtil.convertDateToString(this.endDate), "endDate")).append(",");
        json.append(toJsonProperty(DateUtil.convertDateToString(this.startDate), "startDate")).append("}");
        return json.toString();
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
