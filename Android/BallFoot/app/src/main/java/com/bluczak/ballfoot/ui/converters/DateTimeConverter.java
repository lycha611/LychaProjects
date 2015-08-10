package com.bluczak.ballfoot.ui.converters;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by BLuczak on 2015-07-19.
 */
public class DateTimeConverter {

    private static DateTimeFormatter dateOnly = DateTimeFormat.forPattern("yyyy.MM.dd");
    private static DateTimeFormatter timeOnly = DateTimeFormat.forPattern("HH:mm");
    private static DateTimeFormatter timeOnlyWithSeconds = DateTimeFormat.forPattern("HH:mm:ss");
    private static DateTimeFormatter dateAndTime = DateTimeFormat.forPattern("yyyy.MM.dd HH:mm");

    public static String toDateOnly(final DateTime time) {
        return dateOnly.print(time);
    }

    public static String toTimeOnly(final DateTime time) {
        return timeOnly.print(time);
    }

    public static String toTimeOnlyWithSeconds(final DateTime time) {
        return timeOnlyWithSeconds.print(time);
    }

    public static String toDateAndTime(final DateTime time) {
        return dateAndTime.print(time);
    }

}
