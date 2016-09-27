package com.sweroad.model;

/**
 * Created by Frank on 9/11/15.
 */
public class Gender extends LabelValue {

    private Long id;

    public Gender(String label, String value, Long id) {
        this.setLabel(label);
        this.setValue(value);
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }
}
