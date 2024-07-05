package com.example.myapplication.presentation.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    public static String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault());
        return sdf.format(new Date());
    }

    public static Date parseDateTime(String dateTimeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        try {
            return sdf.parse(dateTimeStr);
        } catch (ParseException e) {
            return new Date(0);
        }
    }
}
