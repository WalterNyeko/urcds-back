package com.sweroad.audit;

import com.sweroad.model.Casualty;

/**
 * Created by Frank on 11/16/15.
 */
public class CasualtyAudit extends BaseAudit {

    /**
     * Detects if casualty is an updated version of dbCasualty
     * @param dbCasualty Casualty from the db
     * @param casualty Casualty from the UI
     * @return true if changes are detected, false otherwise
     */
    public static final boolean hasChanges(final Casualty dbCasualty, final Casualty casualty) {
        if (dbCasualty != null && dbCasualty.equals(casualty)) {
            if (isDifferent(dbCasualty.getAge(), casualty.getAge())) {
                return true;
            }
            if (isDifferent(dbCasualty.getGender(), casualty.getGender())) {
                return true;
            }
            if (isDifferent(dbCasualty.getVehicle(), casualty.getVehicle())) {
                return true;
            }
            if (isDifferent(dbCasualty.getCasualtyType(), casualty.getCasualtyType())) {
                return true;
            }
            if (isDifferent(dbCasualty.getCasualtyClass(), casualty.getCasualtyClass())) {
                return true;
            }
            if (isDifferent(dbCasualty.getBeltOrHelmetUsed(), casualty.getBeltOrHelmetUsed())) {
                return true;
            }
        }
        return false;
    }

}
