package soya.lang;

import java.lang.*;
import java.lang.Long;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: Jun Gong
 */
public class TimeDuration extends EvalObject {

    private static final int SECOND = 1000;
    private static final int MINUTE = SECOND * 60;
    private static final int HOUR   = MINUTE * 60;
    private static final int DAY    = HOUR * 24;

    private long milisecond;

    public TimeDuration(int hour, int minute, int second) {
        this(0, hour, minute, second);
    }

    public TimeDuration(int day, int hour, int minute, int second) {
        this(day * DAY + second * SECOND + minute * MINUTE + hour * HOUR);
    }

    public TimeDuration(long milisecond) {
        super(TimeDuration.class);
        setMilisecond(milisecond);
    }

    private int getCalendarValue(final int t) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.MILLISECOND, (int)Math.abs(milisecond));
        return calendar.get(t);
    }

    private void setCalendarValue(final int t, final int v) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.MILLISECOND, (int)Math.abs(milisecond));
        calendar.set(t, v);
        this.milisecond = (milisecond < 0 ? -1 : 1) * calendar.get(Calendar.MILLISECOND);
    }

    public int toDay() {
        return (int) Math.floor(milisecond / DAY);
    }

    public int toHour() {
        return (int) Math.floor(milisecond / HOUR);
    }

    public int toMinute() {
        return (int) Math.floor(milisecond / MINUTE);
    }

    public int toSecond() {
        return (int) Math.floor(milisecond / SECOND);
    }

    public int getHour() {
        return getCalendarValue(Calendar.HOUR);
    }

    public void setHour(int hour) {
        setCalendarValue(Calendar.HOUR_OF_DAY, hour);
    }

    public int getMinute() {
        return getCalendarValue(Calendar.MINUTE);
    }

    public void setMinute(int minute) {
        setCalendarValue(Calendar.MINUTE, minute);
    }

    public int getSecond() {
        return getCalendarValue(Calendar.SECOND);
    }

    public void setSecond(int second) {
        setCalendarValue(Calendar.SECOND, second);
    }

    public long getMilisecond() {
        return milisecond;
    }

    public void setMilisecond(long milisecond) {
        this.milisecond = milisecond;
    }

    public TimeDuration plus(TimeDuration timeDuration) {
        long newMilisecond = getMilisecond() + timeDuration.getMilisecond();
        return new TimeDuration(newMilisecond);
    }

    public TimeDuration minus(TimeDuration timeDuration) {
        long tmilisecond = timeDuration.getMilisecond();
        long newMilisecond = milisecond - tmilisecond;
        return new TimeDuration(newMilisecond);
    }

    public TimeDuration multi(int i) {
        long newMilisecond = milisecond * i;
        return new TimeDuration(newMilisecond);
    }

    public TimeDuration multi(float f) {
        long newMilisecond = (long) (milisecond * f);
        return new TimeDuration(newMilisecond);
    }

    public TimeDuration div(int i) {
        long newMilisecond = milisecond / i;
        return new TimeDuration(newMilisecond);
    }

    public TimeDuration div(float f) {
        long newMilisecond = (long) (milisecond / f);
        return new TimeDuration(newMilisecond);
    }

    public float div(TimeDuration duration) {
        return milisecond / duration.getMilisecond();
    }

    public Date toDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.MILLISECOND, (int)milisecond);
        return calendar.getTime();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TimeDuration) {
            return milisecond == ((TimeDuration) obj).getMilisecond();
        }
        if (obj instanceof Integer) {
            return milisecond == ((Integer) obj).intValue();
        }
        if (obj instanceof java.lang.Long) {
            return milisecond == ((Long) obj).longValue();
        }
        if (obj instanceof Int) {
            return milisecond == ((Int) obj).getValue();
        }
        return false;
    }

    public String toString() {
        int hour = toHour();
        int minute = getMinute();
        int second = getSecond();

        StringBuffer buffer = new StringBuffer();
        if (milisecond < 0) {
            buffer.append('-');
        }

        buffer.append(Math.abs(hour));
        buffer.append(':');
        if (minute < 10) {
            buffer.append(0);
        }
        buffer.append(minute);
        if (second > 0) {
            buffer.append(':');
            if (second < 10) {
                buffer.append(0);
            }
            buffer.append(second);
        }
        return buffer.toString();
    }

}
