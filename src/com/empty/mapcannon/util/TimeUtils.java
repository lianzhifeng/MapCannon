
package com.empty.mapcannon.util;

import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {

    /**
     * 日期、时间格式
     */
    public static final String FORMAT_FULL = "yyyy/MM/dd HH:mm:ss";
    public static final String FORMAT_DATE = "yyyy/MM/dd";
    public static final String FORMAT_TIME = "HH:mm:ss";

    public static final String FORMAT_MONTH_DATA = "yyyyMMdd";

    /**
     * Format timeMillis
     * 
     * @param time timeMillis
     * @param pattern timePattern
     * @return
     */
    public static String format(long time, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(new Date(time));
    }

    /**
     * Calculate day interval
     * 
     * @param time
     * @param base
     * @param timeZone
     * @return
     */
    public static int getDayInterval(long time, long base, TimeZone timeZone) {
        int gmtoff = timeZone.getRawOffset() / 1000;
        return Time.getJulianDay(time, gmtoff) - Time.getJulianDay(base, gmtoff);
    }

    /**
     * Calculate day interval from now
     * 
     * @param time timeMillis
     * @return
     */
    public static int getFromNowDayInterval(long time) {
        return getDayInterval(System.currentTimeMillis(), time, TimeZone.getDefault());
    }
}
