package com.sweroad.model;

/**
 * Created by Frank on 5/31/16.
 */
public class CountResult implements Countable {

    private long crashCount;
    private long vehicleCount;
    private long casualtyCount;
    private NameIdModel attribute;

    private CountResult(CountResultBuilder builder) {
        this.attribute = builder.attribute;
        this.crashCount = builder.crashCount;
        this.vehicleCount = builder.vehicleCount;
        this.casualtyCount = builder.casualtyCount;
    }

    public long getVehicleCount() {
        return vehicleCount;
    }

    public NameIdModel getAttribute() {
        return attribute;
    }

    public long getCrashCount() {
        return crashCount;
    }

    public long getCasualtyCount() {
        return casualtyCount;
    }

    public static class CountResultBuilder {

        private long crashCount;
        private long vehicleCount;
        private long casualtyCount;
        private NameIdModel attribute;

        public CountResultBuilder setAttribute(NameIdModel attribute) {
            this.attribute = attribute;
            return this;
        }

        public CountResultBuilder setCrashCount(long count) {
            this.crashCount = count;
            return this;
        }

        public void incrementCrashCount(long value) {
            this.crashCount += value;
        }

        public void incrementVehicleCount(long value) {
            this.vehicleCount += value;
        }

        public void incrementCasualtyCount(long value) {
            this.casualtyCount += value;
        }

        public CountResult build() {
            return new CountResult(this);
        }
    }
}
