package warehousedelivery.taaxgenie.in.androidrestart;

/**
 * Created by santosh on 9/27/2018.
 */


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.*;
import android.content.*;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.*;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import static android.support.v4.app.NotificationCompat.PRIORITY_MIN;

public class BackgroundService extends Service implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener{
    private static final String TAG = BackgroundService.class.getSimpleName();
    private boolean isRunning;
    private Context context;
    public static final String ACTION_LOCATION_BROADCAST = BackgroundService.class.getName() + "LocationBroadcast";
    public static final String EXTRA_LATITUDE = "extra_latitude";
    public static final String EXTRA_LONGITUDE = "extra_longitude";
    GoogleApiClient mLocationClient;
    LocationRequest mLocationRequest = new LocationRequest();
    static String locations="";
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    String GPS="GPS OFF";
    BroadcastReceiver mBroadcastReceiver;
    public static int btry;
    SharedPreferences.Editor editor;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        if (Build.VERSION.SDK_INT >= 26) {

           NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            String channelId = getString(R.string.app_name);
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(channelId);
            notificationChannel.setSound(null, null);

            notificationManager.createNotificationChannel(notificationChannel);
            Notification notification = new Notification.Builder(this, channelId)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText("Connected through SDL")
                    .build();
            startForeground(1111, notification);
        }
    }



    @Override
    public void onDestroy() {
        this.isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        // get location

        mLocationClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);


        int priority = LocationRequest.PRIORITY_HIGH_ACCURACY; //by default
        //PRIORITY_BALANCED_POWER_ACCURACY, PRIORITY_LOW_POWER, PRIORITY_NO_POWER are the other priority modes


        mLocationRequest.setPriority(priority);
        mLocationClient.connect();

        //get the battry percentage


          mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
            /*
                BatteryManager
                    The BatteryManager class contains strings and constants used for values in the
                    ACTION_BATTERY_CHANGED Intent, and provides a method for querying battery
                    and charging properties.
            */
            /*
                public static final String EXTRA_SCALE
                    Extra for ACTION_BATTERY_CHANGED: integer containing the maximum battery level.
                    Constant Value: "scale"
            */
                // Get the battery scale
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
                // Display the battery scale in TextView
                Log.e("Battery Scale : " ,String.valueOf( scale));


            /*
                public static final String EXTRA_LEVEL
                    Extra for ACTION_BATTERY_CHANGED: integer field containing the current battery
                    level, from 0 to EXTRA_SCALE.

                    Constant Value: "level"
            */
                // get the battery level
                int   level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
                // Display the battery level in TextView
                Log.e("Battery Level : " ,String.valueOf( level));
                //Toast.makeText(getApplicationContext(),"Battery % : " +String.valueOf(  level ), Toast.LENGTH_LONG).show();


                // Calculate the battery charged percentage
                float percentage = level/ (float) scale;
                // Update the progress bar to display current battery charged percentage
               int  mProgressStatus = (int)((percentage)*100);

                // Show the battery charged percentage text inside progress bar
                Log.e("Battery % : " ,String.valueOf(  mProgressStatus ));
                btry=mProgressStatus;
                context.unregisterReceiver(mBroadcastReceiver);

                Database db = new Database(context.getApplicationContext());

                Log.e("btry % : " ,String.valueOf(  btry ));
                sendtoserver(db.getAlllogJSON(context,String.valueOf( btry)));
                //Toast.makeText(getApplicationContext(),"Battery % : " +String.valueOf(  mProgressStatus ), Toast.LENGTH_LONG).show();
                // Show the battery charged percentage in TextView

                // Display the battery charged percentage in progress bar

            }
        };
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        context.registerReceiver(mBroadcastReceiver,iFilter);




        // gps check



            final LocationManager manager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                GPS = "GPS OFF";
                Log.e("GPS", "OFF");
                editor.putString("GPS", GPS);
                editor.apply();
            } else {
                GPS = "GPS ON";
                Log.e("GPS", "ONN");
                editor.putString("GPS", GPS);
                editor.apply();
            }



        return START_STICKY;
    }
    public void sendtoserver(JSONObject data){
            //Log.e("JSON DATA to send",data.toString());
        //Toast.makeText(context,data.toString(),Toast.LENGTH_LONG).show();

        try{
            //Toast.makeText(context,"sending start",Toast.LENGTH_LONG).show();
            String url = "https://priteshparmarnew1.000webhostapp.com/TextFiles/append.php";
            Log.e("url",""+url);



            JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST,
                    url,data, new Response.Listener<JSONObject>() {

                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("Sucsess", response.toString());

                    //Toast.makeText(context,"Sucsess",Toast.LENGTH_LONG).show();
                    stopForeground(true);
                    stopSelf();
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                  Log.e("TAG", "Error: " + error.getMessage());
                    //Toast.makeText(context,"SEND",Toast.LENGTH_LONG).show();
                    stopForeground(true);
                    stopSelf();

                }
            });

// Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq);
        }catch (Exception e){
            Helper.sendCrashReportDetails((Activity) context,e.getMessage(),e.getStackTrace());
        }
    }
    @Override
    public void onConnected(Bundle dataBundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Log.d(TAG, "== Error On onConnected() Permission not granted");
            //Permission not granted by user so cancel the further execution.

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient, mLocationRequest, this);

        Log.d(TAG, "Connected to Google API");
    }

    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Connection suspended");
    }

    //to get the location change


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Location changed");


            //Send result to activities
            String lat= String.valueOf(location.getLatitude());
            String log= String.valueOf(location.getLongitude());
              locations ="http://maps.google.com/?q="+lat+","+log;
            //Log.e("location","http://maps.google.com/?q="+lat+","+log);

            editor.putString("Location", locations);


            editor.apply();
            //http://maps.google.com/?q=19.2185469,72.8621102
            //http://maps.google.com/?q=19.184466,72.860189

        }


}

