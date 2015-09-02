package com.sweroad.model;

/**
 * Created by Frank on 3/13/15.
 */
public class AgeRange extends LabelValue {
    private Integer minAge;
    private Integer maxAge;

    public AgeRange(Long id, Integer minAge, Integer maxAge) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        super.setValue(id.toString());
    }

    public Integer getMinAge() {
        return minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    @Override
    public String getLabel() {
        if (minAge == 0 && maxAge != null) {
            return "00 - " + padZeroes(maxAge + 1);
        }
        if (minAge != null && maxAge == null) {
            return "> " + (minAge - 1);
        }
        if (minAge != null && maxAge != null) {
            return padZeroes(minAge) + " - " + padZeroes(maxAge);
        }
        return super.getLabel();
    }

    private String padZeroes(int num) {
        return num > 9 ? "" + num : "0" + num;
    }

    @Override
    public int compareTo(Object o) {
        return this.minAge.compareTo(((AgeRange) o).minAge);
    }

    @Override
    public String toJSON() {
        StringBuilder json = new StringBuilder("{");
        json.append(toJsonProperty(this.getId(), "id")).append(",");
        json.append(toJsonProperty(this.minAge, "minAge")).append(",");
        json.append(toJsonProperty(this.maxAge, "maxAge")).append(",");
        json.append(toJsonProperty(this.getLabel(), "label")).append("}");
        return json.toString();
    }
}
