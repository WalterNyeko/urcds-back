package com.sweroad.util;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Frank on 11/2/14.
 */
public class GisHelper {
    public static Double convertLatDegToDec(String latitude) {
        double latDeg = 0;
        int sign = 1;
        if (latitude.startsWith("N")) {
            latitude = latitude.replaceFirst("N", "").trim();
        } else if (latitude.startsWith("n")) {
            latitude = latitude.replaceFirst("n", "").trim();
        } else if (latitude.startsWith("S")) {
            latitude = latitude.replaceFirst("S", "").trim();
            sign = -1;
        } else if (latitude.startsWith("s")) {
            latitude = latitude.replaceFirst("s", "").trim();
            sign = -1;
        } else if (latitude.startsWith("-")) {
            latitude = latitude.replaceFirst("-", "").trim();
            sign = -1;
        }
        String[] latSplit = latitude.split(" ");
        latDeg = Double.parseDouble(latSplit[0]);
        if (latSplit.length > 1) {
            latDeg += (Double.parseDouble(latSplit[1]) / 60);
        }
        if (latSplit.length > 2) {
            latSplit[2] = "0." + latSplit[2];
            latDeg += ((Double.parseDouble(latSplit[2]) * 60) / 3600);
        }
        latDeg *= sign;
        latDeg = Double.parseDouble(new DecimalFormat("0.######").format(latDeg));
        return latDeg;
    }

    public static Double convertLongDegToDec(String longitude) {
        double longDeg = 0;
        int sign = 1;
        if (longitude.startsWith("E")) {
            longitude = longitude.replaceFirst("E", "").trim();
        } else if (longitude.startsWith("e")) {
            longitude = longitude.replaceFirst("e", "").trim();
        } else if (longitude.startsWith("W")) {
            longitude = longitude.replaceFirst("W", "").trim();
            sign = -1;
        } else if (longitude.startsWith("w")) {
            longitude = longitude.replaceFirst("w", "").trim();
            sign = -1;
        } else if (longitude.startsWith("-")) {
            longitude = longitude.replaceFirst("-", "").trim();
            sign = -1;
        }
        String[] longSplit = longitude.split(" ");
        longDeg = Double.parseDouble(longSplit[0]);
        if (longSplit.length > 1) {
            longDeg += (Double.parseDouble(longSplit[1]) / 60);
        }
        if (longSplit.length > 2) {
            longSplit[2] = "0." + longSplit[2];
            longDeg += ((Double.parseDouble(longSplit[2]) * 60) / 3600);
        }
        longDeg *= sign;
        longDeg = Double.parseDouble(new DecimalFormat("0.######").format(longDeg));
        return longDeg;
    }

    public static boolean isValidLatitude(String latitude) {
        if(latitude.trim().isEmpty()) {
            return false;
        }
        Character[] validNonNumericFirstChars = { 'N', 'n', 'S', 's', '-' };
        List<Character> validFirstChars = Arrays.asList(validNonNumericFirstChars);
        char firstChar = latitude.charAt(0);
        if(firstChar < '0' || firstChar > '9') {
            if(!validFirstChars.contains(firstChar)) {
                return false;
            } else {
                latitude = latitude.substring(1).trim();
            }
        }
        String[] splitLat = latitude.split(" ");
        if(splitLat.length > 0) {
            if(Integer.parseInt(splitLat[0]) > 90) {
                return false;
            }
            if(Integer.parseInt(splitLat[0]) < -90) {
                return false;
            }
        }
        if (splitLat.length > 1) {
            if(((long)Double.parseDouble(splitLat[1])) > 60) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidLongitude(String longitude) {
        if(longitude.trim().isEmpty()) {
            return false;
        }
        Character[] validNonNumericFirstChars = { 'E', 'e', 'W', 'w', '-' };
        List<Character> validFirstChars = Arrays.asList(validNonNumericFirstChars);
        char firstChar = longitude.charAt(0);
        if(firstChar < '0' || firstChar > '9') {
            if(!validFirstChars.contains(firstChar)) {
                return false;
            } else {
                longitude = longitude.substring(1).trim();
            }
        }
        String[] splitLat = longitude.split(" ");
        if(splitLat.length > 0) {
            if(Integer.parseInt(splitLat[0]) > 180) {
                return false;
            }
            if(Integer.parseInt(splitLat[0]) < -180) {
                return false;
            }
        }
        if (splitLat.length > 1) {
            if(((long)Double.parseDouble(splitLat[1])) > 60) {
                return false;
            }
        }
        return true;
    }
}
