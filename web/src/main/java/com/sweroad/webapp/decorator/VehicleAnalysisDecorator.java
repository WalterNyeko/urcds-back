package com.sweroad.webapp.decorator;

import com.sweroad.model.Vehicle;
import com.sweroad.webapp.util.CrashHtmlHelper;
import org.displaytag.decorator.TableDecorator;

public class VehicleAnalysisDecorator extends TableDecorator {
    public String getCrashCode() {
        Vehicle vehicle = getVehicle();
        long crashId = vehicle.getCrash().getId();
        return CrashHtmlHelper.createCrashCodeLink(crashId, vehicle.getCrash().getUniqueCode(), getPageContext().getServletContext().getContextPath());
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