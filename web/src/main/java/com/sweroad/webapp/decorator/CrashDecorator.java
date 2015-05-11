package com.sweroad.webapp.decorator;

import org.displaytag.decorator.TableDecorator;

import com.sweroad.model.Crash;
import com.sweroad.webapp.util.CrashHtmlHelper;

public class CrashDecorator extends TableDecorator {

    public String getActions() {
        Crash crash = getCrash();
        long id = crash.getId();
        String viewLink = crash.isRemoved() ? "" : CrashHtmlHelper.createViewLink(id, "data-crashes-id", getPageContext().getServletContext().getContextPath());
        String editLink = crash.isEditable() && !crash.isRemoved() ? CrashHtmlHelper.createEditLink(id, getPageContext().getServletContext().getContextPath()) : "";
        String removeOrRestoreLink;
        if (crash.isRemovable() && !crash.isRemoved()) {
            removeOrRestoreLink = CrashHtmlHelper.createRemoveLink(id, getPageContext().getServletContext().getContextPath());
        } else if (crash.isRemoved()) {
            removeOrRestoreLink = CrashHtmlHelper.createRestoreLink(id, getPageContext().getServletContext().getContextPath());
        } else {
            removeOrRestoreLink = "";
        }
        String mapViewLink = !crash.isRemoved() && crash.hasGpsCoordinates() ? CrashHtmlHelper.createMapViewLink(crash.getUniqueCode(),
                crash.getLatitudeNumeric(), crash.getLongitudeNumeric(), crash.getId(), getPageContext().getServletContext().getContextPath()) : "";
        String crashActions = String.format("%s%s%s%s", viewLink, editLink, removeOrRestoreLink, mapViewLink);
        return crashActions;
    }

    private Crash getCrash() {
        return (Crash) getCurrentRowObject();
    }
}