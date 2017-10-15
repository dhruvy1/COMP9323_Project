package com.comp9323.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by timta on 14/10/2017.
 */

public class DateTimeConverter {

    private static SimpleDateFormat appDateFormat = new SimpleDateFormat("EEE, MMM d yyyy");
    private static SimpleDateFormat appTimeFormat = new SimpleDateFormat("hh:mma");
    private static SimpleDateFormat serverDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat serverTimeFormat = new SimpleDateFormat("HH:mm:ss");

    /**
     * Converts date from the app to the date in the server
     * e.g. Wed, Oct 21 2017 to 2017-10-21
     * @param appDate
     * @return
     */
    public static String convertA2SDate(String appDate) {
        try {
            Date convertedToDate = appDateFormat.parse(appDate);
            String convertedToString = serverDateFormat.format(convertedToDate);
            return convertedToString;
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * Converts time from the app to the time in the server
     * e.g. 12:31AM to 00:31:00
     * @param appTime
     * @return
     */
    public static String convertA2STime(String appTime) {
        try {
            Date convertedToDate = appTimeFormat.parse(appTime);
            String convertedToString = serverTimeFormat.format(convertedToDate);
            return convertedToString;
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * Converts date from the server to the date in the app
     * e.g. 2017-10-21 to Wed, Oct 21 2017
     * @param serverDate
     * @return
     */
    public String convertS2ADate(String serverDate) {
        try {
            Date convertedToDate = serverDateFormat.parse(serverDate);
            String convertedToString = appDateFormat.format(convertedToDate);
            return convertedToString;
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * Converts time from the server to the time in the app
     * e.g. 00:31:00 to 12:31AM
     * @param serverTime
     * @return
     */
    public String convertS2ATime(String serverTime) {
        try {
            Date convertedToDate = serverTimeFormat.parse(serverTime);
            String convertedToString = appTimeFormat.format(convertedToDate);
            return convertedToString;
        } catch (ParseException e) {
            return "";
        }
    }
}
