package com.sweroad.query.service;

import com.sweroad.model.LabelValue;
import com.sweroad.query.Comparison;
import com.sweroad.query.CrashQuery;
import com.sweroad.query.CustomQueryable;
import com.sweroad.service.LookupManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 3/10/15.
 */
public class GenderQueryableService implements CustomQueryableService {
    private LookupManager lookupManager;
    private List<LabelValue> genderTypes;
    private CrashQuery.CrashQueryBuilder.CrashJoinType joinType;

    public GenderQueryableService(List<LabelValue> genderTypes, LookupManager lookupManager,
                                  CrashQuery.CrashQueryBuilder.CrashJoinType joinType) {
        this.genderTypes = genderTypes;
        this.lookupManager = lookupManager;
        this.joinType = joinType;
    }

    @Override
    public void addToCrashQueryBuilder(CrashQuery.CrashQueryBuilder crashQueryBuilder) {
        if (genderTypes != null && genderTypes.size() > 0 &&
                genderTypes.size() < lookupManager.getAllGenders().size()) {
            List<String> genderOptions = new ArrayList<String>();
            boolean includeNull = false;
            for (LabelValue gender : genderTypes) {
                if (gender.getValue() != null && !gender.getValue().equals("-1")) {
                    genderOptions.add(gender.getValue());
                } else if (gender.getValue() != null && gender.getValue().equals("-1")) {
                    includeNull = true;
                }
            }
            if (genderOptions.size() > 0) {
                crashQueryBuilder.addCustomQueryable(createCustomQueryableGender(genderOptions, includeNull));
            } else if (includeNull) {
                crashQueryBuilder.addCustomQueryable(createCustomQueryableForNullGender());
            }
        }
    }

    private CustomQueryable createCustomQueryableGender(Object paramValue, boolean includeNull) {
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

    private CustomQueryable createCustomQueryableForNullGender() {
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
        if (joinType.equals(CrashQuery.CrashQueryBuilder.CrashJoinType.VEHICLE)) {
            return "driver.gender";
        }
        return "gender";
    }

    private String getParameterName() {
        if (joinType.equals(CrashQuery.CrashQueryBuilder.CrashJoinType.VEHICLE)) {
            return "driverGender";
        }
        return "casualtyGender";
    }
}
