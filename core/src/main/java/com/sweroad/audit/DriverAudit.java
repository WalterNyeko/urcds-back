package com.sweroad.audit;

import com.sweroad.model.Driver;

/**
 * Created by Frank on 11/16/15.
 */
public class DriverAudit extends BaseAudit {

    public static final boolean hasChanges(Driver dbDriver, Driver driver) {
        if (dbDriver != null && dbDriver.equals(driver)) {
            if (isDifferent(dbDriver.getAge(), driver.getAge())) {
                return true;
            }
            if (isDifferent(dbDriver.getGender(), driver.getGender())) {
                return true;
            }
            if (isDifferent(dbDriver.getBeltUsed(), driver.getBeltUsed())) {
                return true;
            }
            if (isDifferent(dbDriver.getCasualtyType(), driver.getCasualtyType())) {
                return true;
            }
            if (isDifferent(dbDriver.getLicenseValid(), driver.getLicenseValid())) {
                return true;
            }
            if (isDifferent(dbDriver.getLicenseNumber(), driver.getLicenseNumber())) {
                return true;
            }
        }
        return false;
    }
}
