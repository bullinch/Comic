package com.example.onlinecomic.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd  HH:mm");

    public static String millis2String(long millis) {
        return FORMAT.format(new Date(millis));
    }

    public static String millis2String2(Long millis) {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(millis));
    }

    public static String millis2StringHHMM(Long millis) {
        return new SimpleDateFormat("HH:mm").format(new Date(millis));
    }

    public static boolean isSameData(Long currentTime, Long lastTime) {
        try {
            Calendar nowCal = Calendar.getInstance();
            Calendar dataCal = Calendar.getInstance();
            Date now = new Date(currentTime);
            Date last = new Date(lastTime);
            nowCal.setTime(now);
            dataCal.setTime(last);
            return isSameDay(nowCal, dataCal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if(cal1 != null && cal2 != null) {
            return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
                    && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                    && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        } else {
            return false;
        }
    }

}