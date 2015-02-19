package com.sweroad.webapp.decorator;

import com.sweroad.model.Crash;
import com.sweroad.model.Vehicle;
import com.sweroad.service.CrashManager;
import com.sweroad.webapp.util.CrashHtmlHelper;
import org.displaytag.decorator.TableDecorator;
import org.springframework.beans.factory.annotation.Autowired;

public class VehicleAnalysisDecorator extends TableDecorator {

    public String getCrashCode() {
        Vehicle vehicle = getVehicle();
        long crashId = vehicle.getCrash().getId();
        return CrashHtmlHelper.createCrashCodeLink(crashId, vehicle.getCrash().getUniqueCode(), "data-crashanalysis-id", vehicle.getId());
    }

    public String getDriverLicense() {
        Vehicle vehicle = getVehicle();
        if(vehicle.getDriver().getLicenseValid() == Boolean.TRUE) {
            return "Valid";
        }
        if(vehicle.getDriver().getLicenseValid() == Boolean.FALSE) {
            return "Not Valid";
        }
        return "Unknown";
    }

    public String getDriverBeltUsed() {
        Vehicle vehicle = getVehicle();
        if(vehicle.getDriver().getBeltUsed() == Boolean.TRUE) {
            return "Yes";
        }
        if(vehicle.getDriver().getBeltUsed() == Boolean.FALSE) {
            return "No";
        }
        return "Unknown";
    }

    private Vehicle getVehicle() {
        return (Vehicle) getCurrentRowObject();
    }
}