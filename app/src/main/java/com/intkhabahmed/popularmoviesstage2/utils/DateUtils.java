package com.intkhabahmed.popularmoviesstage2.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String getFormattedDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM YYYY");
        return formatter.format(date);
    }
}
