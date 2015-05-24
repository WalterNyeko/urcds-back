package com.sweroad.webapp.util;

import com.sweroad.Constants;
import com.sweroad.model.LabelValue;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Frank on 2/26/15.
 */
public class CrashAnalysisHelper {

    public static List<LabelValue> getYearsForSearch() {
        final int minYear = 2012;
        final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<LabelValue> years = new ArrayList<LabelValue>();
        LabelValue year;
        for (Integer i = currentYear; i >= minYear; i--) {
            year = new LabelValue();
            year.setLabel(i.toString());
            year.setValue(i.toString());
            years.add(year);
        }
        return years;
    }

    public static List<LabelValue> getMonthsForSearch(HttpServletRequest request) {
        List<LabelValue> months = new ArrayList<LabelValue>();
        LabelValue month;
        for (Integer i = 1; i <= 12; i++) {
            month = new LabelValue();
            month.setLabel(getMessage(request, Constants.MONTHS_OF_YEAR_KEYS[i - 1]));
            month.setValue(i.toString());
            months.add(month);
        }
        return months;
    }

    private static String getMessage(HttpServletRequest request, String code) {
        return new RequestContext(request).getMessage(code);
    }
}
