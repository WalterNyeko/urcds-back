package com.sweroad.query.service;

import com.sweroad.model.Quadstate;
import com.sweroad.query.Comparison;
import com.sweroad.query.CrashQuery;
import com.sweroad.query.CustomQueryable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Frank on 3/9/15.
 */
public class QuadstateQueryableService implements CustomQueryableService {

    private List<Quadstate> selectedStates;
    private CrashQuery.CrashQueryBuilder.CrashJoinType joinType;
    private String propertyName;
    private String propertyPrefix;

    public QuadstateQueryableService(List<Quadstate> selectedStates, CrashQuery.CrashQueryBuilder.CrashJoinType joinType,
                                     String propertyName, String propertyPrefix) {
        this.selectedStates = selectedStates;
        this.joinType = joinType;
        this.propertyName = propertyName;
        this.propertyPrefix = propertyPrefix;
    }

    @Override
    public void addToCrashQueryBuilder(CrashQuery.CrashQueryBuilder crashQueryBuilder) {
        if (selectedStates != null && selectedStates.size() > 0) {
            List<Integer> values = selectedStates.stream().map(Quadstate::getValue).collect(Collectors.toList());
            crashQueryBuilder.addCustomQueryable(createCustomQueryableForQuadstate(values));
        }
    }

    private CustomQueryable createCustomQueryableForQuadstate(Object paramValue) {
        return new CustomQueryable.CustomQueryableBuilder()
                .addCrashJoinType(joinType)
                .addProperty(getPropertyName())
                .addComparison(Comparison.IN)
                .addParameterName(getParameterName())
                .addParameterValue(paramValue)
                .shouldEncloseInParenthesis(true)
                .build();
    }

    private String getPropertyName() {
        if (propertyPrefix.length() > 0) {
            return propertyPrefix.concat(".").concat(propertyName);
        }
        return propertyName;
    }

    private String getParameterName() {
        if (propertyPrefix.length() > 0) {
            return propertyPrefix.concat(Character.toTitleCase(propertyName.charAt(0)) + propertyName.substring(1));
        }
        return propertyName;
    }
}