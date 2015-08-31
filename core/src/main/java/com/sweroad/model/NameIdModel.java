package com.sweroad.model;

/**
 * Created by Frank on 8/31/15.
 */
public abstract class NameIdModel extends BaseModel {

    public abstract Long getId();

    public abstract String getName();

    @Override
    public String toJSON() {
        StringBuilder json = new StringBuilder("{");
        json.append(toJsonProperty(this.getId(), "id")).append(",");
        json.append(toJsonProperty(this.getName(), "name")).append("}");
        return json.toString();
    }
}
