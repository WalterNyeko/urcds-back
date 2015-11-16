package com.sweroad.audit;

import com.sweroad.model.Vehicle;

/**
 * Created by Frank on 11/16/15.
 */
public class VehicleAudit extends BaseAudit {

    public static final boolean hasChanges(Vehicle dbVehicle, Vehicle vehicle) {
        if (dbVehicle != null && dbVehicle.equals(vehicle)) {
            if (isDifferent(dbVehicle.getNumber(), vehicle.getNumber())) {
                return true;
            }
            if (isDifferent(dbVehicle.getDriver(), vehicle.getDriver())) {
                return true;
            }
            if (isDifferent(dbVehicle.getVehicleType(), vehicle.getVehicleType())) {
                return true;
            }
            if (isDifferent(dbVehicle.getCompanyName(), vehicle.getCompanyName())) {
                return true;
            }
            if (DriverAudit.hasChanges(dbVehicle.getDriver(), vehicle.getDriver())) {
                return true;
            }
        }
        return false;
    }
}
