package com.sweroad.model;

/**
 * Created by Frank on 5/31/16.
 */
public class CountResult {

    private Long crashCount;
    private Long vehicleCount;
    private Long casualtyCount;
    private NameIdModel attribute;

    private CountResult(CountResultBuilder builder) {
        this.attribute = builder.attribute;
        this.crashCount = builder.crashCount;
        this.vehicleCount = builder.vehicleCount;
        this.casualtyCount = builder.casualtyCount;
    }

    public Long getVehicleCount() {
        return vehicleCount;
    }

    public NameIdModel getAttribute() {
        return attribute;
    }

    public Long getCrashCount() {
        return crashCount;
    }

    public Long getCasualtyCount() {
        return casualtyCount;
    }

    public static class CountResultBuilder {

        private Long crashCount;
        private Long vehicleCount;
        private Long casualtyCount;
        private NameIdModel attribute;

        public CountResultBuilder setAttribute(NameIdModel attribute) {
            this.attribute = attribute;
            return this;
        }

        public CountResultBuilder setCrashCount(Long crashCount) {
            this.crashCount = crashCount;
            return this;
        }

        public CountResultBuilder setVehicleCount(Long vehicleCount) {
            this.vehicleCount = vehicleCount;
            return this;
        }

        public CountResultBuilder setCasualtyCount(Long casualtyCount) {
            this.casualtyCount = casualtyCount;
            return this;
        }

        public CountResult build() {
            return new CountResult(this);
        }
    }
}
