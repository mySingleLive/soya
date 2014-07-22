package org.soya.antlr.util;

import org.soya.ast.expr.DateTimeExpression;
import org.soya.ast.expr.Expression;
import soya.lang.TimeDuration;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * User: dt_flys
 * Date: 13-11-3
 */
public class DateTimeUtil {

    public static void parseDateParts(String text, DateTimeExpression dateTimeExpression, Expression zone) {
        int year = 0, month = 0, day = 0;
        int len = text.length();
        int p = 0;
        boolean invalid = false;
        int parts[] = new int[] {0, 0, 0};

        for (int i = 0; i < len; i++) {
            char c = text.charAt(i);
            if (Character.isDigit(c)) {
                int n = c - '0';
                parts[p] = parts[p] * 10 + n;
            }
            else if (c == '-' || c == '/' || c == '\\' || c == '.') {
                p++;
            }
        }

        if (parts[0] >= 1000) { // Year Month Day
            year = parts[0];
            month = parts[1];
            day = parts[2];
        }
        else {                   // Month Day Year
            month = parts[0];
            day = parts[1];
            year = parts[2];
        }

        if (year < 100) {
            Date today = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.setTime(today);
            int thisYear = calendar.get(Calendar.YEAR);
            int thisCentry = thisYear / 100;
            int line = thisYear - 50;
            int lastCentry = line / 100;
            line -= lastCentry * 100;

            if (year < line) {      // This Centry
                year = thisCentry * 100 + year;
            }
            else {                  // Last Centry
                year = lastCentry * 100 + year;
            }
        }

        if (month > 12 || month <= 0 || year <= 0) {
            invalid = true;
        }
        if (year <= 0) {
            invalid = true;
        }
        if (day > DateTimeUtil.maxDayOfMonth(year, month)) {
            invalid = true;
        }

        dateTimeExpression.setYear(year);
        dateTimeExpression.setMonth(month);
        dateTimeExpression.setDay(day);
        dateTimeExpression.setInvalid(invalid);
        dateTimeExpression.setTimezone(zone);
    }

    public static void parseTimeParts(String text, DateTimeExpression dateTimeExpression) {
        int hour = 0, minute = 0, second = 0;
        int len = text.length();
        int p = 0;
        int parts[] = new int[] {0, 0, 0};

        for (int i = 0; i < len; i++) {
            char c = text.charAt(i);
            int n;
            if (Character.isDigit(c)) {
                n = c - '0';
                parts[p] = parts[p] * 10 + n;
            }
            else if (c == ':') {
                p++;
            }
        }
        dateTimeExpression.setHour(parts[0]);
        dateTimeExpression.setMinute(parts[1]);
        dateTimeExpression.setSecond(parts[2]);
    }


    public static boolean isLeapYear(int year)
    {
        return  (year % 400 == 0 || year % 4 == 0 && year % 100 != 0);
    }

    public static int maxDayOfMonth(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (isLeapYear(year))
                    return 28;
                return 29;
            default:
                return 0;
        }
    }

    public static TimeDuration createTime(DateTimeExpression dateTimeExpression) {
        return createTime(
                dateTimeExpression.getHour(),
                dateTimeExpression.getMinute(),
                dateTimeExpression.getSecond());
    }

    public static TimeDuration createTime(int hour, int minute, int second) {
        TimeDuration timeDuration = new TimeDuration(hour, minute, second);
        return timeDuration;
    }


/*
    public static Date createDateTime(DateTimeExpression dateTimeExpression) {
        return createDateTime(
                dateTimeExpression.getYear(),
                dateTimeExpression.getMonth(),
                dateTimeExpression.getDay(),
                dateTimeExpression.getHour(),
                dateTimeExpression.getMinute(),
                dateTimeExpression.getSecond(),
                null);
    }
*/



    public static Date createDate(int year, int month, int day) {
        return createDateTime(year, month, day, 0, 0, 0, null);
    }

    public static int monthNumber(int month) {
        switch (month) {
            case 1:
                return Calendar.JANUARY;
            case 2:
                return Calendar.FEBRUARY;
            case 3:
                return Calendar.MARCH;
            case 4:
                return Calendar.APRIL;
            case 5:
                return Calendar.MAY;
            case 6:
                return Calendar.JUNE;
            case 7:
                return Calendar.JULY;
            case 8:
                return Calendar.AUGUST;
            case 9:
                return Calendar.SEPTEMBER;
            case 10:
                return Calendar.OCTOBER;
            case 11:
                return Calendar.NOVEMBER;
            case 12:
                return Calendar.DECEMBER;
            default:
                return -1;
        }
    }


    public static Date createDateTime(int year, int month, int day, int hour, int minute, int second, String timezone) {
        TimeZone zone = null;
        if (timezone != null && !timezone.isEmpty()) {
            zone = TimeZone.getTimeZone(timezone);
        }
        if (zone == null) {
            zone = TimeZone.getDefault();
        }
        Calendar calendar = Calendar.getInstance(zone);
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthNumber(month));
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        Date date = calendar.getTime();
        return date;
    }

}
