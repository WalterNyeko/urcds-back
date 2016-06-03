package com.sweroad.model;

/**
 * Created by Frank on 5/31/16.
 */
public class CountResult {

    private NameIdModel attribute;

    private Long crashCount;

    private Long casualtyCount;

    private Long vehicleCount;

    public Long getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(Long vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public NameIdModel getAttribute() {
        return attribute;
    }

    public void setAttribute(NameIdModel attribute) {
        this.attribute = attribute;
    }

    public Long getCrashCount() {
        return crashCount;
    }

    public void setCrashCount(Long crashCount) {
        this.crashCount = crashCount;
    }

    public Long getCasualtyCount() {
        return casualtyCount;
    }

    public void setCasualtyCount(Long casualtyCount) {
        this.casualtyCount = casualtyCount;
    }
}
