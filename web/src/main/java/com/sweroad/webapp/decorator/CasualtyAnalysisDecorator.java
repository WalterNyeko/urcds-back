package com.sweroad.webapp.decorator;

import com.sweroad.model.Casualty;
import com.sweroad.model.Vehicle;
import com.sweroad.webapp.util.CrashHtmlHelper;
import org.displaytag.decorator.TableDecorator;

public class CasualtyAnalysisDecorator extends TableDecorator {

    public String getCrashCode() {
        Casualty casualty = getCasualty();
        if(casualty.getCrash() == null || casualty.getCrash().getUniqueCode() == null) {
            return "";
        }
        long crashId = casualty.getCrash().getId();
        return CrashHtmlHelper.createCrashCodeLink(crashId, casualty.getCrash().getUniqueCode());
    }

    public String getCasualtyVehicle() {
        Casualty casualty = getCasualty();
        if(casualty.getCasualtyClass().getId().equals(1L)) {
            return "N/A";
        }
        if(casualty.getVehicle() != null) {
            return casualty.getVehicle().getNumber() + " [" +
                    casualty.getVehicle().getVehicleType().getName() + "]";
        }
        return "";
    }

    public String getCasualtyBeltUsed() {
        Casualty casualty = getCasualty();
        if(casualty.getCasualtyClass().getId().equals(1L)) {
            return "N/A";
        }
        if(casualty.getBeltOrHelmetUsed() == Boolean.TRUE) {
            return "Yes";
        }
        if(casualty.getBeltOrHelmetUsed() == Boolean.FALSE) {
            return "No";
        }
        return "Unknown";
    }

    private Casualty getCasualty() {
        return (Casualty) getCurrentRowObject();
    }
}