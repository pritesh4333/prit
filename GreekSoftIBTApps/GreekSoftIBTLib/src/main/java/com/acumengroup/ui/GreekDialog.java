package com.acumengroup.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import androidx.appcompat.app.AlertDialog;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.acumengroup.mobile.R;

/**
 * Created by Arcadia
 */
public class GreekDialog {
    static AlertDialog dialog = null;

    /**
     * It prompts a dialog with positive button. On clicking positive-button
     * {@link alertDialogWithOneButton } will be triggered
     *
     * @param title
     * @param message
     */
    public static AlertDialog alertDialog(Context context, final int id, String title, String message, String buttonText, boolean cancelable, final DialogListener listener) {
        //AlertDialog dialog = null;
        try {

            if (dialog != null) {
                dialog.dismiss();
                dialog.cancel();

            }
            TextView textView1 = new TextView(context);
            textView1.setText(title);
            textView1.setPadding(70,50,0,0);
            textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
            textView1.setTextColor(context.getResources().getColor(R.color.bajaj_black));

            Typeface font = Typeface.createFromAsset(context.getResources().getAssets(), "DaxOT.ttf");
            textView1.setTypeface(font,Typeface.BOLD);



            dialog = new AlertDialog.Builder(context,R.style.AlertDialogTheme).setCustomTitle(textView1).setMessage(message).setCancelable(cancelable).setNeutralButton(buttonText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if (listener != null) listener.alertDialogAction(Action.OK, id);

                }
            }).show();
            TextView textView = dialog.findViewById(android.R.id.message);
          /*  textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
            textView.setTextColor(context.getResources().getColor(R.color.bajaj_black));*/
           /* TextView textView1 = (TextView)dialog.findViewById(android.R.id.title)*/
//            Typeface font = Typeface.createFromAsset(context.getResources().getAssets(), "DaxOT.ttf");
            textView.setTypeface(font,Typeface.NORMAL);
//            textView1.setTypeface(font,Typeface.NORMAL);
           /* textView.setTypeface(font);
            textView1.setTypeface(font);*/

            Button bt1 = dialog.findViewById(android.R.id.button1);
//            Button bt2 = dialog.findViewById(android.R.id.button2);

            bt1.setTypeface(font,Typeface.NORMAL);
//            bt2.setTypeface(font,Typeface.NORMAL);

            dialog.setCanceledOnTouchOutside(cancelable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }


    public static AlertDialog EmptyAlertDialog(Context context, EditText editText,
                                                                      final int id, String title, String message, String buttonText, boolean cancelable,
                                                                      final DialogListener listener) {
        //AlertDialog dialog = null;

        if (editText.getText().toString().isEmpty() || editText.getText().toString().length() <= 0)
            try {
                dialog = new AlertDialog.Builder(context,R.style.AlertDialogTheme).setTitle(title).setMessage(message).setCancelable(cancelable).setNeutralButton(buttonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (listener != null) listener.alertDialogAction(Action.OK, id);

                    }
                }).show();
                TextView textView = dialog.findViewById(android.R.id.message);
                TextView textView1 = dialog.findViewById(android.R.id.title);
                Typeface font = Typeface.createFromAsset(context.getResources().getAssets(), "DaxOT.ttf");
                textView.setTypeface(font,Typeface.NORMAL);
                textView1.setTypeface(font,Typeface.NORMAL);
               /* textView.setTypeface(font);
                textView1.setTypeface(font);*/
                dialog.setCanceledOnTouchOutside(cancelable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        return dialog;
    }

    /**
     * It prompts a dialog with default "OK" button. There should not be any
     * action on clicking "OK" button.
     *
     * @param title
     * @param message
     */

    public static AlertDialog alertDialogOnly(Context context, String title, String
            message, String buttonText) {
        return alertDialog(context, -1, title, message, buttonText, false, null);
    }

    /**
     * @param context
     * @param title
     * @param message GREEK_DIALOG_OK -
     */
    public static AlertDialog alertDialogOnly(Context context, String title, String message) {
        return alertDialogOnly(context, title, message, context.getResources().getString(R.string.GREEK_DIALOG_OK));
    }

    public static AlertDialog alertDialogWithoutTitle(Context context, String message, String
            neutralButton) {
        return alertDialogOnly(context, null, message, neutralButton);
    }

    public static AlertDialog alertDialogSingleChoiceItem(Context context, final int id, String
            title, CharSequence[] items, final DialogListener listener) {

        try {

            TextView textView = dialog.findViewById(android.R.id.message);
            TextView textView1 = dialog.findViewById(android.R.id.title);
            textView1.setText(title);

            Typeface font = Typeface.createFromAsset(context.getResources().getAssets(), "DaxOT.ttf");

            textView.setTypeface(font,Typeface.NORMAL);
            textView1.setTypeface(font,Typeface.NORMAL);
            dialog = new AlertDialog.Builder(context,R.style.AlertDialogTheme).setCustomTitle(textView1).setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    if (listener != null) listener.alertDialogAction(Action.OK, id, item);
                }
            }).show();


            /* textView.setTypeface(font);
            textView1.setTypeface(font);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }

    public static void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * On clicking positive-button {@link alertDialogPositive } and on
     * negative-button {@link alertDialogPositive} will be triggered
     *
     * @param title
     * @param message
     * @param positiveBtnText Value of the positive button
     * @param negativeBtnText Value of negative button
     */

    public static AlertDialog alertDialog(Context context, final int id, String title, String
            message, String positiveBtnText, String negativeBtnText, boolean cancelable,
                                          final DialogListener listener) {
        AlertDialog dialog = null;
        try {

            TextView textView = new TextView(context);
            textView.setText(title);
            textView.setPadding(70,50,0,0);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
            textView.setTextColor(context.getResources().getColor(R.color.bajaj_black));

            /*TextView textView1 = new TextView(context);
            textView1.setText(message);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);*/

            Typeface font = Typeface.createFromAsset(context.getResources().getAssets(), "DaxOT.ttf");
            textView.setTypeface(font,Typeface.BOLD);

            dialog = new AlertDialog.Builder(context,R.style.AlertDialogTheme)

                    .setCustomTitle(textView).setMessage(message).setCancelable(cancelable).setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if (listener != null) listener.alertDialogAction(Action.OK, id);

                }
            }).setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if (listener != null) listener.alertDialogAction(Action.CANCEL, id);

                }
            }).show();
            /*TextView textView = (TextView)dialog.findViewById(android.R.id.message);
            TextView textView1 = (TextView)dialog.findViewById(android.R.id.title);
            Typeface font = Typeface.createFromAsset(context.getResources().getAssets(), "DaxOT.ttf");
            textView.setTypeface(font,Typeface.NORMAL);*/
            TextView textView1 = dialog.findViewById(android.R.id.message);

            textView1.setTypeface(font,Typeface.NORMAL);


//            textView1.setTextColor(context.getResources().getColor(R.color.bajaj_black));
            Button bt1 = dialog.findViewById(android.R.id.button1);
            Button bt2 = dialog.findViewById(android.R.id.button2);

            bt1.setTypeface(font,Typeface.NORMAL);
            bt2.setTypeface(font,Typeface.NORMAL);


            /* textView.setTypeface(font);
            textView1.setTypeface(font);*/
            dialog.setCanceledOnTouchOutside(cancelable);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }

    public enum Action {
        OK, CANCEL
    }

    public interface DialogListener {
        void alertDialogAction(Action action, Object... data);

    }

}
