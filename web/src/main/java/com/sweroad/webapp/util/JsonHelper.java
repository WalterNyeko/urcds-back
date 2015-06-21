package com.sweroad.webapp.util;

import com.google.common.base.CaseFormat;
import com.google.gson.Gson;
import com.sweroad.Constants;
import com.sweroad.model.Crash;
import com.sweroad.model.PoliceStation;
import com.sweroad.util.DateUtil;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by Frank on 12/19/14.
 */
public class JsonHelper {

    /**
     * Takes list of crashes and converts them to JSON and then sets crashes and JSON String in session
     *
     * @param request
     * @param crashes
     */
    public static void crashesToJsonAndSetInSession(HttpServletRequest request, List<Crash> crashes) {
        String crashesJSON = new Gson().toJson(crashes);
        request.getSession().setAttribute("crashes", crashes);
        request.getSession().setAttribute("crashesJSON", crashesJSON);
    }

    /**
     * Takes list of police stations, converts them to JSON and adds the JSON string as a request attribute
     *
     * @param request
     * @param policeStations
     */
    public static void policeStationsToJsonAndSetInAttribute(HttpServletRequest request, List<PoliceStation> policeStations) {
        String policeStationJSON = new Gson().toJson(policeStations);
        request.setAttribute("police_stations_json", policeStationJSON);
    }

    public static void crashAttributesToJsonAndSetInSession(HttpServletRequest request, Map<String,
            List> crashAttributes) throws ParseException {
        StringBuilder crashAttributeJSON = new StringBuilder("{");
        Gson gson = new Gson();
        for (String key : crashAttributes.keySet()) {
            crashAttributeJSON.append("\"").append(generateAttributeName(crashAttributes.get(key).get(0)));
            crashAttributeJSON.append("\": ").append(gson.toJson(crashAttributes.get(key)));
            crashAttributeJSON.append(", ");
        }
        crashAttributeJSON.append("\"month\": ").append(gson.toJson(Constants.MONTHS_OF_YEAR)).append(", ");
        crashAttributeJSON.append("\"year\": ").append(gson.toJson(yearsFrom2014())).append("}");
        request.getSession().setAttribute("crashAttributesJSON", crashAttributeJSON.toString());
    }

    private static Integer[] yearsFrom2014() throws ParseException {
        int startYear = 2014;
        int currentYear = DateUtil.getDateMonthOrYear(DateUtil.getToday().getTime(), Calendar.YEAR);
        List<Integer> years = new ArrayList<>();
        for(int i = startYear; i <= currentYear; i++) {
            years.add(i);
        }
        Integer[] yearsArr = new Integer[years.size()];
        years.toArray(yearsArr);
        return yearsArr;
    }

    public static String generateAttributeName(Object attribute) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, attribute.getClass().getSimpleName());
    }
}
