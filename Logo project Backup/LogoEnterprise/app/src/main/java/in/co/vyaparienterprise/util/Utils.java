package in.co.vyaparienterprise.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ContentUris;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.CursorLoader;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Spanned;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.google.gson.Gson;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.VyapariApp;
import in.co.vyaparienterprise.constant.Constants;
import in.co.vyaparienterprise.model.Address;
import in.co.vyaparienterprise.model.City;
import in.co.vyaparienterprise.model.Contact;
import in.co.vyaparienterprise.model.Currency;
import in.co.vyaparienterprise.model.Employee;
import in.co.vyaparienterprise.model.Firm;
import in.co.vyaparienterprise.model.KeyValue;
import in.co.vyaparienterprise.model.response.dto.FirmDTO;
import in.co.vyaparienterprise.model.response.summary.FirmSum;
import in.co.vyaparienterprise.model.response.summary.ProductSum;
import in.co.vyaparienterprise.ui.adapter.PhoneCallAdapter;
import in.co.vyaparienterprise.ui.generic.dialog.LoadingDialog;

/**
 * Created by Bekir.Dursun on 3.10.2017.
 */

public class Utils {

    private static LoadingDialog loadingDialog = null;

    public static String json(Object myObj) {
        Gson gson = new Gson();
        return gson.toJson(myObj);
    }

    public static void showLoading(Context c) {
        loadingDialog = new LoadingDialog(c);
        loadingDialog.setCancelable(false);
        loadingDialog.show();
    }

    public static void hideLoading() {
        if (loadingDialog != null) {
            if (loadingDialog.isShowing()) {
                loadingDialog.dismiss();
                loadingDialog = null;
            }
        }
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void hideKeyboard(Context context) {
        Activity activity = (Activity) context;
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static synchronized void setObjectSharedPreferencesValue(Context c, Object model, String key) {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(c.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();

        Gson gson = new Gson();
        String jsonObject = gson.toJson(model);
        prefsEditor.putString(key, jsonObject);
        prefsEditor.apply();
    }

    public static synchronized Object getObjectSharedPreferencesValue(Context c, String key, Class className) {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(c.getApplicationContext());
        String json = appSharedPrefs.getString(key, "");

        Gson gson = new Gson();
        return gson.fromJson(json, className);
    }


    public static synchronized int getSPIntegerValue(Context context, String key) {
        try {
            SharedPreferences sPreferences = context.getSharedPreferences(context.getPackageName(), Application.MODE_PRIVATE);
            if (sPreferences == null)
                return -1;
            else {
                SharedPreferences.Editor editor = sPreferences.edit();
                if (editor != null) {
                    try {
                        int val = sPreferences.getInt(key, -1);
                        editor.apply();
                        return val;
                    } catch (Exception e) {
                        return -1;
                    }
                } else {
                    return -1;
                }
            }
        } catch (NullPointerException e) {
            return -1;
        }
    }

    public static synchronized boolean setSPIntegerValue(Context context, String key, int value) {
        SharedPreferences sPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        if (sPreferences == null) {
            return false;
        } else {
            SharedPreferences.Editor editor = sPreferences.edit();
            if (editor == null) {
                return false;
            } else {
                try {
                    int val = sPreferences.getInt(key, -1);
                    if (val != -1) {
                        editor.remove(key);
                    }
                    editor.putInt(key, value);
                    editor.apply();
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        }
    }

    public static synchronized String getSPStringValue(Context context, String key) {
        try {
            SharedPreferences sPreferences = context.getSharedPreferences(context.getPackageName(), Application.MODE_PRIVATE);
            if (sPreferences == null)
                return null;
            else {
                SharedPreferences.Editor editor = sPreferences.edit();
                if (editor != null) {
                    try {
                        String val = sPreferences.getString(key, null);
                        editor.apply();
                        return val;
                    } catch (Exception e) {
                        return null;
                    }
                } else {
                    return null;
                }
            }
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static synchronized boolean setSPStringValue(Context context, String key, String value) {
        SharedPreferences sPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        if (sPreferences == null) {
            return false;
        } else {
            SharedPreferences.Editor editor = sPreferences.edit();
            if (editor == null) {
                return false;
            } else {
                try {
                    String val = sPreferences.getString(key, null);
                    if (val != null) {
                        editor.remove(key);
                    }
                    editor.putString(key, value);
                    editor.apply();
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        }
    }

    public static synchronized boolean isApplicationRunningFront(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        for (int i = 0; i < procInfos.size(); i++) {
            if (VyapariApp.isActivityVisible() && procInfos.get(i).processName.equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static String convertURLinString(String s) {
        StringBuilder result = new StringBuilder();
        String[] parts = s.split("\\s+");
        for (String item : parts)
            try {
                URL url = new URL(item);
                result.append("<a href=\"").append(url).append("\">").append(url).append("</a> ");
            } catch (MalformedURLException e) {
                result.append(item).append(" ");
            }
        return result.toString();
    }

    public static boolean isFirstOpen(Context context, String key) {
        int c = getSPIntegerValue(context, key);
        if (c > 0) {
            return false;
        } else {
            setSPIntegerValue(context, key, 1);
            return true;
        }
    }

    public static boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) VyapariApp.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public static void generateInputDialog(Context context, String title, String content,
                                           int inputType, String inputHint, String inputPrefill,
                                           String buttonName, MaterialDialog.InputCallback inputCallback) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .inputRange(10,12)
                .inputType(inputType)
                .input(inputHint, inputPrefill, inputCallback)
                .positiveText(buttonName)
                .show();
    }

    public static void generateInputDialog(Context context, String title, String content,
                                           int inputType, String inputHint, String inputPrefill,
                                           String positiveName, MaterialDialog.InputCallback inputCallback,
                                           String negativeName, MaterialDialog.SingleButtonCallback negativeCallback,
                                           String neutralName, MaterialDialog.SingleButtonCallback neutralCallback) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .inputType(inputType)
                .input(inputHint, inputPrefill, inputCallback)
                .positiveText(positiveName)
                .negativeText(negativeName)
                .onNegative(negativeCallback)
                .neutralText(neutralName)
                .onNeutral(neutralCallback)
                .show();
    }

    public static void generateDialog(Context context, String title, String content,
                                      String buttonName, MaterialDialog.SingleButtonCallback callback) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText(buttonName)
                .onPositive(callback)
                .show();
    }

    public static void generateDialog(Context context, String title, String content,
                                      String pButtonName, MaterialDialog.SingleButtonCallback pCallback,
                                      String nButtonName, MaterialDialog.SingleButtonCallback nCallback) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText(pButtonName)
                .onPositive(pCallback)
                .negativeText(nButtonName)
                .onNegative(nCallback)
                .show();
    }

    public static void generateDialog(Context context, String title, String content,
                                      String positiveName, MaterialDialog.SingleButtonCallback positiveCallback,
                                      String negativeName, MaterialDialog.SingleButtonCallback negativeCallback,
                                      String neutralName, MaterialDialog.SingleButtonCallback neutralCallback) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText(positiveName)
                .onPositive(positiveCallback)
                .negativeText(negativeName)
                .onNegative(negativeCallback)
                .neutralText(neutralName)
                .onNeutral(neutralCallback)
                .show();
    }

    public static void generateDialog(Context context, String title, String content,
                                      String positiveName, MaterialDialog.SingleButtonCallback positiveCallback,
                                      String negativeName, MaterialDialog.SingleButtonCallback negativeCallback,
                                      String neutralName, MaterialDialog.SingleButtonCallback neutralCallback, boolean isCancelable) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText(positiveName)
                .onPositive(positiveCallback)
                .negativeText(negativeName)
                .cancelable(isCancelable)
                .onNegative(negativeCallback)
                .neutralText(neutralName)
                .onNeutral(neutralCallback)
                .show();
    }

    public static synchronized void generateMaterialDialog(final Activity activity, String title, Spanned message,
                                                           String pButton, MaterialDialog.SingleButtonCallback pClick,
                                                           String nButton, MaterialDialog.SingleButtonCallback nClick) {
        MaterialDialog.Builder md = new MaterialDialog.Builder(activity)
                .theme(Theme.LIGHT)
                .title(title)
                .cancelable(false)
                .content(message)
                .positiveText(pButton)
                .onPositive(pClick)
                .positiveColor(Color.parseColor("#000000"))
                .negativeText(nButton)
                .onNegative(nClick)
                .cancelable(false)
                .negativeColor(Color.parseColor("#000000"));

        md.show();
    }

    public static boolean equalsKeyValue(KeyValue k1, KeyValue k2) {
        if (k1 == null || k2 == null)
            return false;

        if (k1.getKey() != null && k1.getKey() != null) {
            if (k1.getKey().equals(k2.getKey())) {
                return true;
            }
        }

        return k1.getKey() == null && k2.getKey() == null;

    }

    public static boolean equalsKeyValue(KeyValue k1, String k2) {
        if (k1 == null || k2 == null)
            return false;

        if (k1.getKey() != null && k1.getKey() != null) {
            if (k1.getKey().equals(k2)) {
                return true;
            }
        }

        return false;
    }

    public static int getCurrencySelection(ArrayList<Currency> currencyValues, Currency currency) {
        try {
            String currencyKey = currency.getKey();
            for (int i = 0; i < currencyValues.size(); i++) {
                if (currencyKey.equals(currencyValues.get(i).getKey())) {
                    return i;
                }
            }
        } catch (Exception ignored) {
            return 0;
        }
        return 0;
    }

    public static int getSelection(ArrayList<KeyValue> list, String key) {
        try {
            for (int i = 0; i < list.size(); i++) {
                if (key.equals(list.get(i).getKey())) {
                    return i;
                }
            }
        } catch (Exception ignored) {
            return 0;
        }
        return 0;
    }

    public static String getValue(ArrayList<KeyValue> list, String key) {
        for (int i = 0; i < list.size(); i++) {
            if (key.equals(list.get(i).getKey())) {
                return list.get(i).getValue();
            }
        }
        return "";
    }

    public static ArrayList<String> getOnlyValues(ArrayList<KeyValue> keyValues) {
        ArrayList<String> results = new ArrayList<>();
        for (KeyValue keyValue : keyValues) {
            results.add(keyValue.getValue());
        }

        return results;
    }

    public static boolean isInteger(double number) {
        return Math.ceil(number) == Math.floor(number);
    }

    public static ArrayList<KeyValue> createContactAndEmployeePhoneList(FirmSum firmSum) {
        ArrayList<Contact> contacts = firmSum.getContacts();
        ArrayList<Employee> employees = firmSum.getEmployees();
        ArrayList<KeyValue> phoneList = new ArrayList<>();

        if (contacts != null) {
            for (Contact c : contacts) {
                if (c.getType() == Constants.TYPE_PHONE) {
                    KeyValue keyValue = new KeyValue();
                    keyValue.setKey(firmSum.getName());
                    keyValue.setValue(c.getValue());
                    phoneList.add(keyValue);
                }
            }
        }

        if (employees != null) {
            for (Employee e : employees) {
                KeyValue keyValue = new KeyValue();
                keyValue.setKey(e.getFullName());
                keyValue.setValue(e.getPhone());
                phoneList.add(keyValue);
            }
        }

        return phoneList;
    }

    public static ArrayList<KeyValue> cleanList(ArrayList<KeyValue> data) {
        ArrayList<KeyValue> newData = new ArrayList<>();
        if (data != null && data.size() > 0) {
            for (KeyValue keyValue : data) {
                if (keyValue.getKey() != null) {
                    newData.add(keyValue);
                }
            }
        }
        return newData;
    }

    public static int getPosition(String productId, ArrayList<ProductSum> productSumList) {
        for (int i = 0; i < productSumList.size(); i++) {
            if (productId.equals(productSumList.get(i).getId())) {
                return i;
            }
        }
        return -1;
    }

    public static boolean equalsObject(Object o1, Object o2) {
        Gson gson = new Gson();
        return gson.toJson(o1).equals(gson.toJson(o2));
    }

    public static String createFirmAddressDetail(Context context, FirmDTO firmDTO) {
        return (context.getString(R.string.address2) + " " + firmDTO.getAddress() + " ")
                + (firmDTO.getDistrict() != null ? (context.getString(R.string.district) + " " + firmDTO.getDistrict().getValue()) : "")
                + (firmDTO.getCity() != null ? (context.getString(R.string.city) + " " + firmDTO.getCity().getValue() + " ") : "")
                + (firmDTO.getState() != null ? firmDTO.getState().getValue() : "")
                + (firmDTO.getCountry() != null ? " / " + firmDTO.getCountry().getValue() : "");
    }

    public static String createFirmAddressDetail(Context context, Firm firm) {
        return (context.getString(R.string.address2) + " " + firm.getAddress() + " ")
                + (firm.getDistrict() != null ? (context.getString(R.string.district) + " " + firm.getDistrict().getValue()) : "")
                + (firm.getCity() != null ? (context.getString(R.string.city) + " " + firm.getCity().getValue() + " ") : "")
                + (firm.getState() != null ? firm.getState().getValue() : "")
                + (firm.getCountry() != null ? " / " + firm.getCountry().getValue() : "");
    }

    public static String createAddressDetail(Context context, Address address) {
        return (context.getString(R.string.address2) + " " + address.getAddressText() + " ")
                + (address.getDistrict() != null ? (context.getString(R.string.district) + " " + address.getDistrict().getValue()) : "")
                + (address.getCity() != null ? (context.getString(R.string.city) + " " + address.getCity().getValue() + " ") : "")
                + (address.getState() != null ? address.getState().getValue() : "")
                + (address.getCountry() != null ? " / " + address.getCountry().getValue() : "");
    }

    public static DialogPlus createPhoneCallDialog(Context context, ArrayList<KeyValue> phoneList, OnItemClickListener onItemClickListener) {
        PhoneCallAdapter adapter = new PhoneCallAdapter(context, phoneList);
        return DialogPlus.newDialog(context)
                .setAdapter(adapter)
                .setOnItemClickListener(onItemClickListener)
                .setExpanded(false)
                .create();
    }

    public static ArrayList<KeyValue> getGSTRegistrationTypes() {
        ArrayList<KeyValue> result = new ArrayList<>();
        KeyValue keyValue;
        keyValue = new KeyValue("0", "Regular");
        result.add(keyValue);
        keyValue = new KeyValue("1", "SEZ");
        result.add(keyValue);
        keyValue = new KeyValue("2", "Composition Dealer");
        result.add(keyValue);
        return result;
    }

    public static ArrayList<String> getCityList(ArrayList<City> cities) {
        ArrayList<String> cityList = new ArrayList<>();
        for (City c : cities) {
            cityList.add(c.getName());
        }
        return cityList;
    }

    /*public static UserAttributes createUserAttributes(LoginDTO loginDTO) {
        User user = loginDTO.getUser();
        CompanyDTO companyDTO = loginDTO.getCompany();

        io.intercom.android.sdk.Company iCompany = new io.intercom.android.sdk.Company.Builder()
                .withCompanyId(String.valueOf(companyDTO.getId()))
                .withName(companyDTO.getName())
                .build();

        return new UserAttributes.Builder()
                .withUserId(String.valueOf(user.getId()))
                .withEmail(user.getEmail())
                .withName(user.getFullName())
                .withPhone(user.getPhone())
                .withCompany(iCompany)
                .build();
    }*/

    public static int getMatColor(Context mContext, int typeColor) {
        int returnColor = Color.BLACK;
        int arrayId = mContext.getResources().getIdentifier("mdcolor_" + typeColor, "array", mContext.getPackageName());

        if (arrayId != 0) {
            TypedArray colors = mContext.getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.BLACK);
            colors.recycle();
        }
        return returnColor;
    }

    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }

    public static void saveCompanyLogo(Context context, String base64ImageData) {
        Bitmap bitmapImage = null;
        if (base64ImageData != null) {
            String data = base64ImageData.replace("\n", "");
            byte[] decodedString = Base64.decode(data, Base64.DEFAULT);
            bitmapImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }

        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, "company_logo.png");

        if (base64ImageData != null) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(mypath);
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            boolean delete = mypath.delete();
        }
        //return directory.getAbsolutePath();
    }

    public static Bitmap loadCompanyLogo(Context context) {
        try {
            ContextWrapper cw = new ContextWrapper(context);
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File f = new File(directory, "company_logo.png");
            return BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    @SuppressLint("HardwareIds")
    public static String getHashAndroidId() {
        return convertToMd5(Settings.Secure.getString(VyapariApp.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID));
    }

    public static String convertToMd5(final String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap decodeSampledBitmapFromResource(String resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;
        return BitmapFactory.decodeFile(resId, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    public static String getPath(Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }

                } else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);

                } else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };

                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }

            } else if ("content".equalsIgnoreCase(uri.getScheme())) {

                if (isGooglePhotosUri(uri))
                    return uri.getLastPathSegment();

                return getDataColumn(context, uri, null, null);

            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }

        } else if (Build.VERSION.SDK_INT < 19) {
            String[] proj = {MediaStore.Images.Media.DATA};
            String result = null;

            CursorLoader cursorLoader = new CursorLoader(context, uri, proj, null, null, null);
            Cursor cursor = cursorLoader.loadInBackground();

            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                result = cursor.getString(column_index);
            }

            return result;
        }

        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}