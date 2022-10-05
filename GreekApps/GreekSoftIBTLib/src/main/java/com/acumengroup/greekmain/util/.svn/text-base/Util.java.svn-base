package com.acumengroup.greekmain.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    static ProgressDialog pdilog;


    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);


    /**
     * Returns the SharedPreferences using the packagename as sharedpreference
     * name
     *
     * @param ctx
     * @return
     */
    public static SharedPreferences getPrefs(Context ctx) {
        return ctx.getSharedPreferences(ctx.getPackageName(), Context.MODE_PRIVATE);
    }

    /*
     * Generate a value suitable for use in {@link #setId(int)}.
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }


    public static void showShortToast(Context ctx, int resId) {

    }

    public static boolean isNumeric(String str) {
        if (str == null || str.trim().length() == 0) return false;
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    public static boolean isEmailValid(String email) {
        String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@" + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\." + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|" + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void showProgress(Context ctx) {
        pdilog = new ProgressDialog(ctx);


        pdilog.setTitle("Please wait.....");
        pdilog.show();

    }

    public static void hideProgress() {
        if (pdilog != null)
            pdilog.dismiss();

    }
    /*public static boolean needsValidation(Context context, boolean sensitive) {
        boolean reValidate = false;
        if (sensitive) {
            long currentTime = System.currentTimeMillis();
            long lastValidatedTime = getPrefs(context).getLong("LastValidatedTime", 0);

            if (lastValidatedTime + 1200000l < currentTime) {
                reValidate = true;
            } else if (currentTime < lastValidatedTime - 1200000l) {
                reValidate = true;
            }
        }
        return reValidate;
    }*/

    public static void updateValidationTime(Context context) {
        getPrefs(context).edit().putLong("LastValidatedTime", System.currentTimeMillis()).apply();
    }

    public static CharSequence read(InputStream in) {
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuilder buffer = new StringBuilder();

            while ((line = br.readLine()) != null) buffer.append(line).append('\n');
            return buffer;
        } catch (IOException e) {
            return "";
        } finally {
            closeStream(in);
        }

    }

    private static void closeStream(InputStream in) {
        // TODO Auto-generated method stub

    }

    /**
     * @param context
     * @return application version <br>
     * Ex :- 1.0.3
     */
    public static String getAppVersion(Context context) {
        String appVersion = "";
        try {
            PackageInfo manager = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            appVersion = manager.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appVersion;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static Gson gson;

    public static Gson getGsonParser() {
        if(null == gson) {
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
        }
        return gson;
    }

   /* public static String getDivedValue(String input, int divideBy){
        input = input.replaceAll(",","");
        if(isNumeric(input)){
            return (new Double(input)/divideBy)+"";
        }
        return "";
    }*/

    public static void Toast(String input){


    }


    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {

            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String convertPassMd5(String pass) {
        String password = null;
        MessageDigest mdEnc;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(pass.getBytes(), 0, pass.length());
            pass = new BigInteger(1, mdEnc.digest()).toString(16);
            while (pass.length() < 32) {
                pass = "0" + pass;
            }
            password = pass;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
    }


}
