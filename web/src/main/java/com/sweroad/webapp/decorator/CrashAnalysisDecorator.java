package com.sweroad.webapp.decorator;

import com.sweroad.model.Crash;
import com.sweroad.webapp.util.CrashHtmlHelper;
import org.displaytag.decorator.TableDecorator;

public class CrashAnalysisDecorator extends TableDecorator {

    public String getActions() {
        Crash crash = getCrash();
        long id = crash.getId();
        return CrashHtmlHelper.createViewLink(id, "data-crashanalysis-id", getPageContext().getServletContext().getContextPath());
    }

    private Crash getCrash() {
        return (Crash) getCurrentRowObject();
    }
}