package com.sweroad.util;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtilTest extends TestCase {
    //~ Instance fields ========================================================

    private final Log log = LogFactory.getLog(DateUtilTest.class);

    //~ Constructors ===========================================================

    public DateUtilTest(String name) {
        super(name);
    }

    public void testGetInternationalDatePattern() {
        LocaleContextHolder.setLocale(new Locale("nl"));
        assertEquals("dd-MMM-yyyy", DateUtil.getDatePattern());

        LocaleContextHolder.setLocale(Locale.FRANCE);
        assertEquals("dd/MM/yyyy", DateUtil.getDatePattern());

        LocaleContextHolder.setLocale(Locale.GERMANY);
        assertEquals("dd.MM.yyyy", DateUtil.getDatePattern());

        // non-existent bundle should default to default locale
        LocaleContextHolder.setLocale(new Locale("fi"));
        String fiPattern = DateUtil.getDatePattern();
        LocaleContextHolder.setLocale(Locale.getDefault());
        String defaultPattern = DateUtil.getDatePattern();

        assertEquals(defaultPattern, fiPattern);
    }

    public void testGetDate() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("db date to convert: " + new Date());
        }

        String date = DateUtil.getDate(new Date());

        if (log.isDebugEnabled()) {
            log.debug("converted ui date: " + date);
        }

        assertTrue(date != null);
    }

    public void testGetDateTime() {
        if (log.isDebugEnabled()) {
            log.debug("entered 'testGetDateTime' method");
        }
        String now = DateUtil.getTimeNow(new Date());
        assertTrue(now != null);
        log.debug(now);
    }

    public void testGetDateWithNull() {
        final String date = DateUtil.getDate(null);
        assertEquals("", date);
    }

    public void testGetDateTimeWithNull() {
        final String date = DateUtil.getDateTime(null, null);
        assertEquals("", date);
    }

    public void testGetToday() throws ParseException {
        assertNotNull(DateUtil.getToday());
    }

    public void testGetDateMonthOrYearWithYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2015);
        assertEquals(2015, DateUtil.getDateMonthOrYear(calendar.getTime(), Calendar.YEAR));
    }

    public void testGetDateMonthOrYearWithMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 2);
        assertEquals(2, DateUtil.getDateMonthOrYear(calendar.getTime(), Calendar.MONTH));
    }

    public void testCreateDateFromYearMonthDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, 1, 1);
        assertEquals(calendar.getTime(), DateUtil.createDateFromYearMonthDay(2015, 2, 1));
    }

    public void testGetMaximumDateInYearMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2012, 1, 1);
        assertEquals(29, DateUtil.getMaximumDateInYearMonth(2012, 2));
    }

    public void testGetOneBasedMonthFromDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2012, 1, 1);
        assertEquals(2, DateUtil.getOneBasedMonthFromDate(calendar.getTime()));
    }
}
