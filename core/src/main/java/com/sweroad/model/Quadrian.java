package com.sweroad.model;

/**
 * A boolean with four states: true, false, unknown, not applicable
 * Created by Frank on 11/5/15.
 */
public enum Quadrian {

    YES (1, "Yes"),
    NO (0, "No"),
    UNKNOWN (2, "Unknown"),
    NOT_APPLICABLE (3, "N/A");

    private int value;
    private String label;

    Quadrian(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return this.value;
    }

    public String getLabel() {
        return this.label;
    }
}