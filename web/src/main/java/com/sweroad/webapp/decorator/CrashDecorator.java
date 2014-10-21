package com.sweroad.webapp.decorator;

import org.displaytag.decorator.TableDecorator;
import org.springframework.beans.factory.annotation.Autowired;

import com.sweroad.model.Crash;
import com.sweroad.service.CrashManager;
import com.sweroad.webapp.util.CrashHtmlHelper;

public class CrashDecorator extends TableDecorator {

    @Autowired
    private CrashManager crashManager;

    public String getActions() {
        Crash crash = getCrash();
        long id = crash.getId();
        String viewLink = crash.isRemoved() ? "" : CrashHtmlHelper.createViewLink(id);
        String editLink = crash.isEditable() && !crash.isRemoved() ? CrashHtmlHelper.createEditLink(id) : "";
        String removeOrRestoreLink;
        if (crash.isRemovable() && !crash.isRemoved()) {
            removeOrRestoreLink = CrashHtmlHelper.createRemoveLink(id);
        } else if (crash.isRemoved()) {
            removeOrRestoreLink = CrashHtmlHelper.createRestoreLink(id);
        } else {
            removeOrRestoreLink = "";
        }
        String crashActions = String.format("%s%s%s", viewLink, editLink, removeOrRestoreLink);
        return crashActions;
    }

    private Crash getCrash() {
        return (Crash) getCurrentRowObject();
    }
}