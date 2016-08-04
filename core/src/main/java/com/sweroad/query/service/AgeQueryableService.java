package com.sweroad.query.service;

import com.sweroad.model.AgeRange;
import com.sweroad.model.LabelValue;
import com.sweroad.query.CrashQuery;
import com.sweroad.query.CustomQueryable;
import com.sweroad.query.LiteralQueryable;
import com.sweroad.service.LookupManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Frank on 3/12/15.
 */
public class AgeQueryableService implements CustomQueryableService {

    private List<AgeRange> ageRanges;
    private CrashQuery.CrashQueryBuilder.CrashJoinType joinType;
    private String agePropertyName;

    public AgeQueryableService(List<LabelValue> ageRanges, CrashQuery.CrashQueryBuilder.CrashJoinType joinType) {
        this.joinType = joinType;
        initAndSortAgeRanges(ageRanges);
        setAgePropertyName(joinType);
    }

    private void initAndSortAgeRanges(List<LabelValue> ageRanges) {
        this.ageRanges = new ArrayList<>();
        ageRanges.forEach(ageRange ->  {
            if (ageRange instanceof AgeRange) {
                this.ageRanges.add((AgeRange) ageRange);
            }
        });
        Collections.sort(this.ageRanges);
    }

    private void setAgePropertyName(CrashQuery.CrashQueryBuilder.CrashJoinType joinType) {
        if (joinType.equals(CrashQuery.CrashQueryBuilder.CrashJoinType.VEHICLE)) {
            agePropertyName = "v.driver.age";
        } else {
            agePropertyName = "i.age";
        }
    }

    @Override
    public void addToCrashQueryBuilder(CrashQuery.CrashQueryBuilder crashQueryBuilder) {
        if (ageRanges != null && ageRanges.size() > 0) {
            crashQueryBuilder.addLiteralQueryable(new LiteralQueryable(joinType, buildAgeRangeQueryFilter()));
        }
    }

    private String buildAgeRangeQueryFilter() {
        StringBuilder filter = new StringBuilder("");
        if (ageRanges.size() == 1) {
            filter.append(getAgeRangeQueryPart(ageRanges.get(0)));
        } else {
            for(AgeRange ageRange : getCombinedAgeRages()) {
                filter.append(getAgeRangeQueryPart(ageRange)).append(" or ");
            }
            filter.delete(filter.lastIndexOf(" or "), filter.length());
        }
        return filter.toString();
    }

    private String getAgeRangeQueryPart(AgeRange ageRange) {
        if (ageRange.getMinAge() != null && ageRange.getMaxAge() != null) {
            if (ageRange.getMinAge() == 0) {
                return agePropertyName + " <= " + ageRange.getMaxAge();
            }
            return agePropertyName + " between " + ageRange.getMinAge() + " and " + ageRange.getMaxAge();
        }
        return agePropertyName + " >= " + ageRange.getMinAge();
    }

    private List<AgeRange> getCombinedAgeRages() {
        List<AgeRange> combinedAgeRanges = new ArrayList<>();
        AgeRange combinedAgeRange = null;
        for (int i = 0; i < ageRanges.size() - 1; i++) {
            if (combinedAgeRange == null) {
                if (ageRangesConsecutive(ageRanges.get(i), ageRanges.get(i + 1))) {
                    combinedAgeRange = combineAgeRanges(ageRanges.get(i), ageRanges.get(i + 1));
                } else {
                    combinedAgeRanges.add(ageRanges.get(i));
                }
            } else {
                if (ageRangesConsecutive(combinedAgeRange, ageRanges.get(i + 1))) {
                    combinedAgeRange = combineAgeRanges(combinedAgeRange, ageRanges.get(i + 1));
                } else {
                    combinedAgeRanges.add(combinedAgeRange);
                    combinedAgeRange = null;
                }
            }
            if (i == ageRanges.size() - 2) {
                combinedAgeRanges.add(combinedAgeRange != null ? combinedAgeRange : ageRanges.get(i + 1));
            }
        }
        return combinedAgeRanges;
    }

    private boolean ageRangesConsecutive(AgeRange ageRange1, AgeRange ageRange2) {
        return ageRange2.getId() - ageRange1.getId() == 1L;
    }

    private AgeRange combineAgeRanges(AgeRange ageRange1, AgeRange ageRange2) {
        return new AgeRange(ageRange2.getId(), ageRange1.getMinAge(), ageRange2.getMaxAge());
    }
}