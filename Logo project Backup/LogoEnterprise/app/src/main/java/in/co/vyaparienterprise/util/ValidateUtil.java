package in.co.vyaparienterprise.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import es.dmoral.toasty.Toasty;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.constant.Constants;

/**
 * Created by Bekir.Dursun on 4.10.2017.
 */

public class ValidateUtil {

    public static boolean validateIBAN(String iban) {
        try {
            // TODO: iban check
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean validateEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean validateStrings(Context context, String s, int minSize, String errorMessage) {
        s = s.trim();
        if (s.isEmpty() || s.length() < minSize) {
            Toasty.error(context, errorMessage).show();
            return false;
        }
        return true;
    }

    public static boolean validateEmail(Context context, String email) {
        email = email.trim();
        if (email.isEmpty() || !validateEmail(email)) {
            Toasty.error(context, context.getString(R.string.err_msg_email)).show();
            return false;
        }
        return true;
    }

    public static boolean validatePassword(Context context, String password) {
        if (password.isEmpty() || password.length() < Constants.PASSWORD_MIN_LIMIT) {
            Toasty.error(context, context.getString(R.string.err_msg_password)).show();
            return false;
        }
        return true;
    }

    public static boolean validateInteger(String value) {
        String regexStr = "^[1-9]\\d*(\\.\\d+)?$";
        return value.matches(regexStr);
    }

    public static boolean validateEditTexts(EditText... editTexts) {
        for (EditText editText : editTexts) {
            int count = editText.getText().toString().length();
            if (count == 0) {
                return false;
            }
        }
        return true;
    }
}
