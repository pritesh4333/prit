package com.acumengroup.greekmain.util.inputfilter;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.widget.EditText;

/**
 * Created by Arcadia
 */

public class InputFilterBeforeAfterDecimalDigits implements InputFilter {

    int beforeDecimal, afterDecimal;
    String prevValue = "";

    public InputFilterBeforeAfterDecimalDigits(EditText editText, int beforeDecimal, int afterDecimal) {
        this.beforeDecimal = beforeDecimal;
        this.afterDecimal = afterDecimal;

        editText.setKeyListener(new DigitsKeyListener(Boolean.FALSE, Boolean.TRUE));
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        String temp = "";

        for (int i = 0; i < dest.toString().length(); i++) {

            if ((i) == (dstart)) {
                if (source.toString().equalsIgnoreCase("")) {
                    continue;
                }
            }

            if (dstart == 0 && i == 0) {
                if (!source.toString().equalsIgnoreCase("")) {
                    temp += source.toString();
                }
            }

            temp += dest.toString().charAt(i);

            if ((i + 1) == (dstart)) {
                temp += source.toString();
            }

        }

        if (!isValueCorrect(temp, beforeDecimal, afterDecimal)) {
            if (source.toString().equalsIgnoreCase("")) {
                if (prevValue.charAt(dstart) == '.') {
                    return ".";
                }
            }

            return "";
        }

        prevValue = temp;

        return null;
    }

    private boolean isValueCorrect(String value, int beforeDecimal, int afterDecimal) {

        if (value.contains(".")) {

            String sp[] = value.split("\\.");

            if (sp.length == 0) return true;

            if (sp[0].length() > beforeDecimal) {
                return false;
            } else {
                if (sp.length > 1) {
                    if (sp[1].length() > afterDecimal) {
                        return false;
                    }
                }
            }

        } else {
            return value.length() <= beforeDecimal;
        }

        return true;
    }

}
