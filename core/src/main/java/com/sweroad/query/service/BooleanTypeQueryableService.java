package com.sweroad.query.service;

import com.sweroad.model.LabelValue;
import com.sweroad.query.Comparison;
import com.sweroad.query.CrashQuery;
import com.sweroad.query.CustomQueryable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 3/9/15.
 */
public class BooleanTypeQueryableService implements CustomQueryableService {

    private List<LabelValue> fullOptionsList;
    private List<LabelValue> selectedOptions;
    private CrashQuery.CrashQueryBuilder.CrashJoinType joinType;
    private String propertyName;
    private String propertyPrefix;

    public BooleanTypeQueryableService(List<LabelValue> selectedOptions, List<LabelValue> fullOptionsList,
                                       CrashQuery.CrashQueryBuilder.CrashJoinType joinType, String propertyName,
                                       String propertyPrefix) {
        this.selectedOptions = selectedOptions;
        this.fullOptionsList = fullOptionsList;
        this.joinType = joinType;
        this.propertyName = propertyName;
        this.propertyPrefix = propertyPrefix;
    }
    @Override
    public void addToCrashQueryBuilder(CrashQuery.CrashQueryBuilder crashQueryBuilder) {
        if(selectedOptions != null && selectedOptions.size() > 0 &&
                selectedOptions.size() < fullOptionsList.size()) {
            List<Boolean> booleanOptions = new ArrayList<Boolean>();
            boolean includeNull = false;
            for(LabelValue option : selectedOptions) {
                if(option.getId() != null && option.getId().equals(1L)) {
                    booleanOptions.add(Boolean.TRUE);
                } else if (option.getId() != null && option.getId().equals(0L)) {
                    booleanOptions.add(Boolean.FALSE);
                } else if (option.getId() != null && option.getId().equals(-1L)) {
                    includeNull = true;
                }
            }
            if(booleanOptions.size() > 0) {
                crashQueryBuilder.addCustomQueryable(createCustomQueryableForLicenseType(booleanOptions, includeNull));
            } else if(includeNull) {
                crashQueryBuilder.addCustomQueryable(createCustomQueryableForNullLicenseType());
            }
        }
    }

    private CustomQueryable createCustomQueryableForLicenseType(Object paramValue, boolean includeNull) {
        return new CustomQueryable.CustomQueryableBuilder()
                .addCrashJoinType(joinType)
                .addProperty(getPropertyName())
                .addComparison(Comparison.IN)
                .addParameterName(getParameterName())
                .addParameterValue(paramValue)
                .shouldEncloseInParenthesis(true)
                .shouldIncludeNulls(includeNull)
                .build();
    }

    private CustomQueryable createCustomQueryableForNullLicenseType() {
        return new CustomQueryable.CustomQueryableBuilder()
                .addCrashJoinType(joinType)
                .addProperty(getPropertyName())
                .addComparison(Comparison.IS)
                .addParameterName(getParameterName())
                .addParameterValue("NULL")
                .shouldUseLiterals(true)
                .build();
    }

    private String getPropertyName() {
        if(propertyPrefix.length() > 0) {
            return propertyPrefix.concat(".").concat(propertyName);
        }
        return propertyName;
    }

    private String getParameterName() {
        if(propertyPrefix.length() > 0) {
            return propertyPrefix.concat(Character.toTitleCase(propertyName.charAt(0)) + propertyName.substring(1));
        }
        return propertyName;
    }
}