package com.sweroad.webapp.util;

import com.google.gson.Gson;
import com.sweroad.model.Crash;
import com.sweroad.model.PoliceStation;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Frank on 12/19/14.
 */
public class JsonHelper {

    /**
     * Takes list of crashes and converts them to JSON and then sets crashes and JSON String in session
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
     * @param request
     * @param policeStations
     */
    public static void policeStationsToJsonAndSetInAttribute(HttpServletRequest request, List<PoliceStation> policeStations) {
        String policeStationJSON = new Gson().toJson(policeStations);
        request.setAttribute("police_stations_json", policeStationJSON);
    }
}
