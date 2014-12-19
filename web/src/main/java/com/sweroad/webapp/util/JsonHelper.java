package com.sweroad.webapp.util;

import com.google.gson.Gson;
import com.sweroad.model.Crash;

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
}
