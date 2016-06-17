package com.sweroad.model;

import com.sweroad.Constants;

/**
 * Created by Frank on 8/31/15.
 */
public abstract class NameIdModel extends BaseModel {

    public abstract Long getId();

    public abstract String getName();

    public abstract void setName(String name);

    public abstract boolean isActive();

    public abstract void setActive(boolean active);

    @Override
    public String toJSON() {
        StringBuilder json = new StringBuilder("{");
        json.append(toJsonProperty(this.getId(), "id")).append(",");
        json.append(toJsonProperty(this.getName(), "name")).append("}");
        return json.toString();
    }

    public static NameIdModel createNotSpecifiedInstance() {
        return new NameIdModel() {
            private String unsupportedOperation = "This operation is not supported in this anonymous instance.";
            @Override
            public Long getId() {
                return Constants.NOT_SPECIFIED_ID;
            }

            @Override
            public String getName() {
                return Constants.NOT_SPECIFIED;
            }

            @Override
            public void setName(String name) {
                throw new UnsupportedOperationException(unsupportedOperation);
            }

            @Override
            public boolean isActive() {
                return true;
            }

            @Override
            public void setActive(boolean active) {
                throw new UnsupportedOperationException(unsupportedOperation);
            }

            @Override
            public String toString() {
                return this.getName();
            }

            @Override
            public boolean equals(Object o) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }
        };
    }
}