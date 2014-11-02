package com.sweroad.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by Frank on 11/2/14.
 */
public class GisHelperTest {

    @Test
    public void testConvertNorthLatitudeDegreesToDecimal() {
        String latitude = "N00 15 342";
        Double latDec = GisHelper.convertLatDegToDec(latitude);
        assertEquals("0.2557", latDec.toString());
    }

    @Test
    public void testConvertSouthLatitudeDegreesToDecimal() {
        String latitude = "S00 15 342";
        Double latDec = GisHelper.convertLatDegToDec(latitude);
        assertEquals("-0.2557", latDec.toString());
    }

    @Test
    public void testConvertEastLongitudeDegreesToDecimal() {
        String longitude = "E32 24 032";
        Double longDec = GisHelper.convertLongDegToDec(longitude);
        assertEquals("32.400533", longDec.toString());
    }

    @Test
    public void testConvertWestLongitudeDegreesToDecimal() {
        String longitude = "W32 24 032";
        Double longDec = GisHelper.convertLongDegToDec(longitude);
        assertEquals("-32.400533", longDec.toString());
    }

    @Test
    public void testConvertLatitudeDegreesToDecimal() {
        String latitude = "00 15 342";
        Double latDec = GisHelper.convertLatDegToDec(latitude);
        assertEquals("0.2557", latDec.toString());
    }

    @Test
    public void testConvertNegativeLatitudeDegreesToDecimal() {
        String latitude = "-00 15 342";
        Double latDec = GisHelper.convertLatDegToDec(latitude);
        assertEquals("-0.2557", latDec.toString());
    }

    @Test
    public void testConvertLongitudeDegreesToDecimal() {
        String longitude = "32 24 032";
        Double longDec = GisHelper.convertLongDegToDec(longitude);
        assertEquals("32.400533", longDec.toString());
    }

    @Test
    public void testConvertNegativeLongitudeDegreesToDecimal() {
        String longitude = "-32 24 032";
        Double longDec = GisHelper.convertLongDegToDec(longitude);
        assertEquals("-32.400533", longDec.toString());
    }

    @Test
    public void testThatLatitudeWithNIsInValidFormat() {
        String latitude = "N00 15 342";
        assertTrue(GisHelper.isValidLatitude(latitude));
    }

    @Test
    public void testThatLatitudeWithSIsInValidFormat() {
        String latitude = "S00 15 342";
        assertTrue(GisHelper.isValidLatitude(latitude));
    }

    @Test
    public void testThatLatitudeIsInValidFormat() {
        String latitude = "00 15 342";
        assertTrue(GisHelper.isValidLatitude(latitude));
    }

    @Test
    public void testThatNegativeLatitudeIsInValidFormat() {
        String latitude = "-00 15 342";
        assertTrue(GisHelper.isValidLatitude(latitude));
    }

    @Test
    public void testThatLatitudeWithWrongLetterIsInvalid() {
        String latitude = "L00 15 342";
        assertFalse(GisHelper.isValidLatitude(latitude));
    }

    @Test
    public void testThatLatitudeWithMoreThan90DegreesIsInvalid() {
        String latitude = "N95 15 342";
        assertFalse(GisHelper.isValidLatitude(latitude));
    }

    @Test
    public void testThatLatitudeWithMoreThan60MinutesIsInvalid() {
        String latitude = "N00 65 342";
        assertFalse(GisHelper.isValidLatitude(latitude));
    }

    @Test
    public void testThatEmptyLatitudeIsInvalid() {
        String latitude = "";
        assertFalse(GisHelper.isValidLatitude(latitude));
    }

    @Test
    public void testThatLongitudeWithEIsInValidFormat() {
        String latitude = "E32 15 342";
        assertTrue(GisHelper.isValidLongitude(latitude));
    }

    @Test
    public void testThatLongitudeWithWIsInValidFormat() {
        String latitude = "W32 15 342";
        assertTrue(GisHelper.isValidLongitude(latitude));
    }

    @Test
    public void testThatLongitudeIsInValidFormat() {
        String latitude = "32 15 342";
        assertTrue(GisHelper.isValidLongitude(latitude));
    }

    @Test
    public void testThatNegativeLongitudeIsInValidFormat() {
        String latitude = "-00 15 342";
        assertTrue(GisHelper.isValidLongitude(latitude));
    }

    @Test
    public void testThatLongitudeWithWrongLetterIsInvalid() {
        String latitude = "L32 15 342";
        assertFalse(GisHelper.isValidLongitude(latitude));
    }

    @Test
    public void testThatLongitudeWithMoreThan180DegreesIsInvalid() {
        String latitude = "E195 15 342";
        assertFalse(GisHelper.isValidLongitude(latitude));
    }

    @Test
    public void testThatLongitudeWithMoreThan60MinutesIsInvalid() {
        String latitude = "E95 65 342";
        assertFalse(GisHelper.isValidLongitude(latitude));
    }

    @Test
    public void testThatEmptyLongitudeIsInvalid() {
        String latitude = "";
        assertFalse(GisHelper.isValidLongitude(latitude));
    }
}
