package com.sweroad.webapp.decorator;

import com.sweroad.model.Crash;
import com.sweroad.service.CrashManager;
import com.sweroad.webapp.util.CrashHtmlHelper;
import org.displaytag.decorator.TableDecorator;
import org.springframework.beans.factory.annotation.Autowired;

public class CrashAnalysisDecorator extends TableDecorator {

    @Autowired
    private CrashManager crashManager;

    public String getActions() {
        Crash crash = getCrash();
        long id = crash.getId();
        return CrashHtmlHelper.createViewLink(id);
    }

    private Crash getCrash() {
        return (Crash) getCurrentRowObject();
    }
}