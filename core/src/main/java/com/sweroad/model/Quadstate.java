package com.sweroad.model;

/**
 * A boolean with four states: true, false, unknown, not applicable
 * Created by Frank on 11/5/15.
 */
public enum Quadstate {

    YES (1, "Yes"),
    NO (0, "No"),
    UNKNOWN (2, "Unknown"),
    NOT_APPLICABLE (3, "N/A");

    private int value;
    private String label;

    Quadstate(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return this.value;
    }

    public String getLabel() {
        return this.label;
    }

    public static Quadstate getByValue(int value) {
        for(Quadstate option : Quadstate.values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        throw new IllegalArgumentException("No enum Quadstate with value '" + value + "'");
    }

	@Override
	public String toString() {
		return "Quadstate{" +
				"value=" + value +
				", label='" + label + '\'' +
				'}';
	}
}