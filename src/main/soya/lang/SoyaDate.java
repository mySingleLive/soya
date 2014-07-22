package soya.lang;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author: Jun Gong
 */
public class SoyaDate extends AbstractObject implements Comparable {

    private Date date;
    private TimeZone timeZone;

    public SoyaDate(Date date, String timeZone) {
        this(date, timeZone == null || timeZone.isEmpty() ? null : TimeZone.getTimeZone(timeZone));
    }

    public SoyaDate(Date date, TimeZone timeZone) {
        if (timeZone != null) {
            this.timeZone = timeZone;
        }
        else {
            this.timeZone = TimeZone.getDefault();
        }
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(date);
        this.date = date;
    }


    public SoyaDate(Date date) {
        this.date = date;
        this.timeZone = TimeZone.getDefault();
    }

    public SoyaDate(long time) {
        this.date = new Date(time);
        this.timeZone = TimeZone.getDefault();
    }

    public SoyaDate() {
        this(new Date());
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
//        if (timeZone != null) {
//            calendar.setTimeZone(timeZone);
//        }
        calendar.setTime(date);
        return calendar;
    }

    public Calendar getCalendar() {
        return getCalendar(date);
    }

    private int getCalendarValue(final int t) {
        Calendar calendar = getCalendar();
        return calendar.get(t);
    }

    private void setCalendarValue(final int t, final int v) {
        Calendar calendar = getCalendar();
        calendar.set(t, v);
        this.date = calendar.getTime();
    }

    private static int toCalendarMonthConstant(int month) {
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
                throw new SoyaRuntimeException(month + " is a invalid month");
        }
    }

    private static int fromCalendarMonthConstant(int calendarMonth) {
        switch (calendarMonth) {
            case Calendar.JANUARY:
                return 1;
            case Calendar.FEBRUARY:
                return 2;
            case Calendar.MARCH:
                return 3;
            case Calendar.APRIL:
                return 4;
            case Calendar.MAY:
                return 5;
            case Calendar.JUNE:
                return 6;
            case Calendar.JULY:
                return 7;
            case Calendar.AUGUST:
                return 8;
            case Calendar.SEPTEMBER:
                return 9;
            case Calendar.OCTOBER:
                return 10;
            case Calendar.NOVEMBER:
                return 11;
            case Calendar.DECEMBER:
                return 12;
            default:
                throw new SoyaRuntimeException(calendarMonth + " is not a month constant of class Calendar");
        }
    }

    public TimeZone getTimeZone() {
        if (timeZone == null) {
            return TimeZone.getDefault();
        }
        return timeZone;
    }

    public int getYear() {
        return getCalendarValue(Calendar.YEAR);
    }

/*
    public void setYear(final int year) {
        setCalendarValue(Calendar.YEAR, year);
    }
*/

    public int getMonth() {
        return fromCalendarMonthConstant(getCalendarValue(Calendar.MONTH));
    }

/*
    public void setMonth(int month) {
        setCalendarValue(Calendar.MONTH, toCalendarMonthConstant(month));
    }
*/

    public int getDay() {
        return getCalendarValue(Calendar.DAY_OF_MONTH);
    }

/*
    public void setDay(int day) {
        setCalendarValue(Calendar.DAY_OF_MONTH, day);
    }
*/

    public int getHour() {
        return getCalendarValue(Calendar.HOUR_OF_DAY);
    }

/*
    public void setHour(int hour) {
        setCalendarValue(Calendar.HOUR_OF_DAY, hour);
    }
*/

    public int getMinute() {
        return getCalendarValue(Calendar.MINUTE);
    }

/*
    public void setMinute(int minute) {
        setCalendarValue(Calendar.MINUTE, minute);
    }
*/

    public int getSecond() {
        return getCalendarValue(Calendar.SECOND);
    }

/*
    public void setSecond(int second) {
        setCalendarValue(Calendar.SECOND, second);
    }
*/

    public int getMilisecond() {
        return getCalendarValue(Calendar.MILLISECOND);
    }

/*
    public void setMilisecond(int milisecond) {
        setCalendarValue(Calendar.MILLISECOND, milisecond);
    }
*/

    public SoyaDate plus(TimeDuration timeDuration) {
        long newTime = this.date.getTime() + timeDuration.getMilisecond();
        return new SoyaDate(newTime);
    }

    public SoyaDate minus(TimeDuration timeDuration) {
        long newTime = this.date.getTime() - timeDuration.getMilisecond();
        return new SoyaDate(newTime);
    }

    public TimeDuration minus(Date date) {
        long newTime = this.date.getTime() - date.getTime();
        return new TimeDuration(newTime);
    }

    public boolean after(Date date) {
        return this.date.after(date);
    }

    public boolean before(Date date) {
        return this.date.before(date);
    }

    public boolean greaterThan(Date date) {
        return after(date);
    }

    public boolean lessThan(Date date) {
        return before(date);
    }

    public boolean greaterEquals(Date date) {
        return this.date.equals(date) || after(date);
    }

    public boolean lessEquals(Date date) {
        return this.date.equals(date) || before(date);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Date) {
            return date.equals(obj);
        }
        if (obj instanceof SoyaDate) {
            return date.equals(((SoyaDate) obj).getDate());
        }
        return false;
    }

    public String format(String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        if (timeZone != null) {
            format.setTimeZone(timeZone);
        }
        else {
            format.setTimeZone(TimeZone.getDefault());
        }
        return format.format(date);
    }

    public String format(String pattern, Locale locale) {
        DateFormat format = new SimpleDateFormat(pattern, locale);
        return format.format(date);
    }


    public String format(String pattern, TimeZone zone) {
        DateFormat format = new SimpleDateFormat(pattern);
        if (zone != null) {
            format.setTimeZone(zone);
        }
        return format.format(date);
    }

    public String format(String pattern, String zone) {
        DateFormat format = new SimpleDateFormat(pattern);
        if (zone != null) {
            format.setTimeZone(TimeZone.getTimeZone(zone));
        }
        return format.format(date);
    }

    public SoyaDate clone() {
        Date cloneDate = (Date) date.clone();
        SoyaDate newDate = new SoyaDate(cloneDate);
        return newDate;
    }

    public SoyaDate clone(TimeZone zone) {
        Calendar cal = Calendar.getInstance(zone);
        cal.clear();
        cal.set(Calendar.YEAR, getYear());
        cal.set(Calendar.MONTH, getMonth());
        cal.set(Calendar.DAY_OF_MONTH, getDay());
        cal.set(Calendar.HOUR_OF_DAY, getHour());
        cal.set(Calendar.MINUTE, getMinute());
        cal.set(Calendar.SECOND, getSecond());
        SoyaDate newDate = new SoyaDate(cal.getTime(), zone);
        return newDate;
    }

    public SoyaDate clone(String zone) {
        TimeZone tz = TimeZone.getTimeZone(zone);
        return clone(tz);
    }


    public String toString() {
        int hour = getHour();
        int minute = getMinute();
        int second = getSecond();
        String format = "yyyy-MM-dd";
        if (hour > 0 || minute > 0 || second > 0) {
            format += " hh:mm:ss";
        }
        if (timeZone != null) {
            format += " Z(z)";
        }
        return format(format, timeZone);
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Date) {
            Date d = (Date) o;
            if (after(d)) {
                return 1;
            }
            if (before(d)) {
                return -1;
            }
            return 0;
        }
        else if (o instanceof SoyaDate) {
            SoyaDate d = (SoyaDate) o;
            if (after(d.getDate())) {
                return 1;
            }
            if (before(d.getDate())) {
                return -1;
            }
            return 0;
        }
        return -1;
    }
}
