package com.intkhabahmed.popularmoviesstage2.utils;

import android.arch.persistence.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String getFormattedDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        return formatter.format(date);
    }

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
