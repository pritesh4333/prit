/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.acumengroup.greekmain.util.logger;

import android.util.Log;

/**
 * Created by Arcadia
 */
public class GreekLog {

    public static boolean loggerSwitch = false;

    private static String LOGGER_TAG = "GREEK_LOGGER";

    public GreekLog() {
    }

    /**
     * @param status
     */
    public static void setSwitch(boolean status) {
        loggerSwitch = status;
    }

    /**
     * @param msg Log as a Debug
     */
    public static void msg(String msg) {
        if (loggerSwitch) {
            Log.d(LOGGER_TAG, msg);
        }
    }

    /**
     * @param error Log as a Error
     */
    public static void error(String error) {
        if (loggerSwitch) {
            Log.e(LOGGER_TAG, error);
        }
    }

}
