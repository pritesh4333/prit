package com.acumengroup.greekmain.chart.util;

import android.util.Log;

/**
 * * Created by Arcadia
 */
public class GreekChartLogger {

    private static final String LOGGER_TAG = "GREEK_CHART";
    public static boolean showLogMessage = false;

    /**
     * @param message <br>
     *                Log the Message
     */
    public static void logMsg(String message) {
        if (showLogMessage) {
            Log.d(LOGGER_TAG, message);
        }
    }

    /**
     * @param message <br>
     *                Log the Error
     */
    public static void logError(String message) {
        if (showLogMessage) {
            Log.e(LOGGER_TAG, message);
        }
    }

}
