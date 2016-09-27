package com.sweroad.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Frank on 3/13/15.
 */
public class WeightRange extends LabelValue {
    private BigDecimal minWeight;
    private BigDecimal maxWeight;

    public WeightRange(Long id, BigDecimal minWeight, BigDecimal maxWeight) {
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        super.setValue(id.toString());
    }

    @Override
    public String getLabel() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        if (minWeight.equals(0) && maxWeight != null) {
            return "0 - " + decimalFormat.format(maxWeight.add(new BigDecimal(1)));
        }
        if (minWeight != null && maxWeight == null) {
            return "> " + decimalFormat.format(minWeight.subtract(new BigDecimal(1)));
        }
        if (minWeight != null && maxWeight != null) {
            return decimalFormat.format(minWeight) + " - " + decimalFormat.format(maxWeight);
        }
        return super.getLabel();
    }

    @Override
    public int compareTo(Object o) {
        return this.minWeight.compareTo(((WeightRange) o).minWeight);
    }

    @Override
    public String toJSON() {
        StringBuilder json = new StringBuilder("{");
        json.append(toJsonProperty(this.getId(), "id")).append(",");
        json.append(toJsonProperty(this.minWeight, "minWeight")).append(",");
        json.append(toJsonProperty(this.maxWeight, "maxWeight")).append(",");
        json.append(toJsonProperty(this.getLabel(), "label")).append("}");
        return json.toString();
    }
}
