package warehousedelivery.taaxgenie.in.androidrestart;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Helper {
    private static final String TAG = Helper.class.getSimpleName();
    private boolean flag = true;
    private static Context context;
    private static Activity activity;
    public static void sendCrashReportDetails(final Activity activity, String errorMessage, StackTraceElement[] stackTrace) {

        /************ Handle crash and show exit and send report ***********/
        final JSONObject crashObject = new JSONObject();

        String errorReport = "null";
        String stackTraceReport = "";///*********** Stack Trace ************/
        String deviceDetail = "null";
        String telephonyDeviceId = "null";
        String imeiSIM1 = "null";
        PackageInfo pInfo = null;
        String versionCode = "null", versionName = "null";

        String androidSDK="null",androidVersion="null",androidBrand="null",
                androidManufacturer="null",androidModel="null",androidDeviceId="null";


        if(activity==null)
        {
            try {
                androidSDK = String.valueOf(android.os.Build.VERSION.SDK_INT);
                androidVersion = android.os.Build.VERSION.RELEASE;
                androidBrand = android.os.Build.BRAND;
                androidManufacturer = android.os.Build.MANUFACTURER;
                androidModel = android.os.Build.MODEL;
                deviceDetail = "App Version Code: " + versionCode + "\nApp Version Number: " + versionName + "\nSDK: " + androidSDK + "\nVersion: " + androidVersion + "\nBrand: " + androidBrand +
                        "\nManufacturer: " + androidManufacturer + "\nModel: " + androidModel;

                for (int i = 0; i < stackTrace.length; i++) {
                    if (i == 6)
                        break;
                    //int StackNo = i+1;
                    //stackTraceReport = stackTraceReport +"\n\\**********Stack Trace "+StackNo+" *********/\n";
                    stackTraceReport = String.format("%s\n%s", stackTraceReport, stackTrace[i].toString());/*"Class Name: "+stackTrace[i].getClassName()+"\nFile Name: "+stackTrace[i].getFileName()+
                        "\nMethod Name: "+stackTrace[i].getMethodName()+"\nLine Number: "+stackTrace[i].getLineNumber();*/
                }


                errorReport = deviceDetail + "\nError Message: " + errorMessage + "\nError Stack Trace: \n" + stackTraceReport;
            }catch (Exception e) {
                e.printStackTrace();
                log(TAG, String.valueOf(e.getMessage()));
                errorReport = errorReport + "Error Message: " + e.getMessage() + "\n";
            }


        }else {


            // App Version Details
            try {
                pInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
                versionCode = String.valueOf(pInfo.versionCode).trim();
                versionName = String.valueOf(pInfo.versionName).trim();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                log(TAG, String.valueOf(e.getMessage()));
                errorReport = "Error Message: " + e.getMessage() + "\n";
            }


            //Device ID details
        /*TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE); //ANDROID DEVCIE ID
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            if (TelephonyMgr != null) {
                telephonyDeviceId = TelephonyMgr.getDeviceId();
                imeiSIM1 = TelephonyMgr.getSimSerialNumber();
            }

        }*/

            try {
                androidSDK = String.valueOf(android.os.Build.VERSION.SDK_INT);
                androidVersion = android.os.Build.VERSION.RELEASE;
                androidBrand = android.os.Build.BRAND;
                androidManufacturer = android.os.Build.MANUFACTURER;
                androidModel = android.os.Build.MODEL;
                androidDeviceId = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
                deviceDetail = "App Version Code: " + versionCode + "\nApp Version Number: " + versionName + "\nSDK: " + androidSDK + "\nVersion: " + androidVersion + "\nBrand: " + androidBrand +
                        "\nManufacturer: " + androidManufacturer + "\nModel: " + androidModel + "\nAndroid Device Id: " + androidDeviceId;

                for (int i = 0; i < stackTrace.length; i++) {
                    if (i == 6)
                        break;
                    //int StackNo = i+1;
                    //stackTraceReport = stackTraceReport +"\n\\**********Stack Trace "+StackNo+" *********/\n";
                    stackTraceReport = String.format("%s\n%s", stackTraceReport, stackTrace[i].toString());/*"Class Name: "+stackTrace[i].getClassName()+"\nFile Name: "+stackTrace[i].getFileName()+
                        "\nMethod Name: "+stackTrace[i].getMethodName()+"\nLine Number: "+stackTrace[i].getLineNumber();*/
                }

                errorReport = deviceDetail + "\nTelephony Device ID " + telephonyDeviceId + "\nIMEI No: " + imeiSIM1 + "\nError Message: " + errorMessage + "\nError Stack Trace: \n" + stackTraceReport;
            } catch (Exception e) {
                e.printStackTrace();
                log(TAG, String.valueOf(e.getMessage()));
                errorReport = errorReport + "Error Message: " + e.getMessage() + "\n";
            }
        }

        try {
            crashObject.put("AppVersionCode",versionCode);
            crashObject.put("AppVersionNumber",versionName);
            crashObject.put("SDK",androidSDK);
            crashObject.put("Version",androidVersion);
            crashObject.put("Brand",androidBrand);
            crashObject.put("Manufacturer",androidManufacturer);
            crashObject.put("Model",androidModel);
            crashObject.put("AndroidDeviceId",androidDeviceId);
            crashObject.put("TelephonyDeviceID",telephonyDeviceId);
            crashObject.put("IMEINo",imeiSIM1);
            crashObject.put("ErrorMessage",errorMessage);
            crashObject.put("ErrorStackTrace",stackTraceReport);
        } catch (JSONException e) {
            e.printStackTrace();
        }



            sendCrashReport(crashObject);





    }

    private static void sendCrashReport(JSONObject crashReport) {
        JSONObject finalObj = new JSONObject();
        try {

        finalObj.put("Crash",crashReport);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try{
            String url = "https://priteshparmar.000webhostapp.com/sendimageandrecive/carshreport.php";
            Log.e("url",""+url);



            JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST,
                    url,finalObj, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.e("TAG", response.toString());


                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("TAG", "Error: " + error.getMessage());

                }
            });

// Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq);
        }catch (Exception e){
            Helper.sendCrashReportDetails((Activity) context,e.getMessage(),e.getStackTrace());
        }
    }
    public static void log(String tag, String msg) {
        Log.e(tag, msg);
    }
}
