package com.sweroad.webapp.taglib;

/**
 * Created by Frank on 12/2/14.
 */
public class Functions {

    public static String getCrashSeverityColor(int severityId) {
        String color = "";
        switch (severityId) {
            case 1:
                color = "#FF0000";
                break;
            case 2:
                color = "#FFA500";
                break;
            case 3:
                color = "#FFFF00";
                break;
            case 4:
                color = "#A52A2A";
                break;
        }
        return color;
    }
}
