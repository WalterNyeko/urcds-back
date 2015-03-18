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
        if(minAge == 0 && maxAge != null) {
            return "Below " + (maxAge + 1);
        }
        if(minAge != null && maxAge == null) {
            return minAge + " and above";
        }
        if(minAge !=null && maxAge != null) {
            return minAge + "-" + maxAge;
        }
        return super.getLabel();
    }

    @Override
    public int compareTo(Object o) {
        return this.minAge.compareTo(((AgeRange)o).minAge);
    }
}
