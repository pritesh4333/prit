package com.acumengroup.ui;


import android.content.Context;
import android.widget.EditText;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;


/**
 * Created by Arcadia
 */
public class AppConstants {

    /**
     * <b>Type </b> Optional <br>
     * <br>
     * Set idleTimeOut in Milliseconds. Default is 5 mins.
     */
    public static long idleTimeout = 5 * 60 * 1000;

    public static Boolean isEmptyEditText(Context context, EditText editText, String emptyMessage) {
        if (editText.getText().toString().isEmpty() || editText.getText().toString().trim().length() <= 0) {
            GreekDialog.alertDialog(context, 0, GREEK, emptyMessage, "Ok", false, null);

            return true;
        } else


            return false;
    }

}
