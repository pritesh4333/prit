package warehousedelivery.taaxgenie.in.androidrestart;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.provider.CallLog;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;

public class MainActivity extends AppCompatActivity  {
    WebView webView;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



try {
    webView = (WebView) findViewById(R.id.web);
    progressBar = (ProgressBar) findViewById(R.id.progressBar);
    webView.loadUrl("https://news.google.com");
    webView.getSettings().setJavaScriptEnabled(true);
    webView.setHorizontalScrollBarEnabled(false);
    initWebView();



    final String[] NECESSARY_PERMISSIONS = new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS
            , Manifest.permission.RECEIVE_BOOT_COMPLETED, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

    if (ContextCompat.checkSelfPermission(MainActivity.this,
            Manifest.permission.WRITE_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    try {
                        AlarmManager alarms = (AlarmManager) this
                                .getSystemService(Context.ALARM_SERVICE);

                        Intent intent = new Intent(getApplicationContext(),
                                DBAlarmReceiver.class);
                        intent.putExtra(DBAlarmReceiver.ACTION_ALARM,
                                DBAlarmReceiver.ACTION_ALARM);

                        final PendingIntent pIntent = PendingIntent.getBroadcast(this,
                                1234567, intent, 0);

                        alarms.setRepeating(AlarmManager.RTC_WAKEUP,
                                System.currentTimeMillis(), 1000 * 60 * 1, pIntent);


                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        Helper.sendCrashReportDetails(null,e.getMessage(),e.getStackTrace());
                    }


                    try {
                        AlarmManager alarmss = (AlarmManager) this
                                .getSystemService(Context.ALARM_SERVICE);

                        Intent intents = new Intent(getApplicationContext(),
                                ServerAlarmReceiver.class);
                        intents.putExtra(ServerAlarmReceiver.ACTION_ALARM,
                                ServerAlarmReceiver.ACTION_ALARM);

                        final PendingIntent pIntents = PendingIntent.getBroadcast(this,
                                1234568, intents, 0);

                        alarmss.setRepeating(AlarmManager.RTC_WAKEUP,
                                System.currentTimeMillis(), 1000000, pIntents);


                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        Helper.sendCrashReportDetails(null,e.getMessage(),e.getStackTrace());
                    }
                }
            }

        }

    } else {

        //ask for permission
        //Toast.makeText(getApplicationContext(), "permistion denied", Toast.LENGTH_SHORT).show();
        ActivityCompat.requestPermissions(
                MainActivity.this,
                NECESSARY_PERMISSIONS, 123);
    }
}catch (Exception e){
    Helper.sendCrashReportDetails(null,e.getMessage(),e.getStackTrace());
}
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
try{
        if (grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[2] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[3] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[4] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[5] == PackageManager.PERMISSION_GRANTED) {
                Log.e("log", "Permission: " + permissions[0] + "was " + grantResults[0]);


                try {
                    AlarmManager alarms = (AlarmManager) this
                            .getSystemService(Context.ALARM_SERVICE);

                    Intent intent = new Intent(getApplicationContext(),
                            DBAlarmReceiver.class);
                    intent.putExtra(DBAlarmReceiver.ACTION_ALARM,
                            DBAlarmReceiver.ACTION_ALARM);

                    final PendingIntent pIntent = PendingIntent.getBroadcast(this,
                            1234567, intent, 0);

                    alarms.setRepeating(AlarmManager.RTC_WAKEUP,
                            System.currentTimeMillis(), 1000 * 60 * 1, pIntent);


                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Helper.sendCrashReportDetails(null,e.getMessage(),e.getStackTrace());
                }


                try {
                    AlarmManager alarmss = (AlarmManager) this
                            .getSystemService(Context.ALARM_SERVICE);

                    Intent intents = new Intent(getApplicationContext(),
                            ServerAlarmReceiver.class);
                    intents.putExtra(ServerAlarmReceiver.ACTION_ALARM,
                            ServerAlarmReceiver.ACTION_ALARM);

                    final PendingIntent pIntents = PendingIntent.getBroadcast(this,
                            1234568, intents, 0);

                    alarmss.setRepeating(AlarmManager.RTC_WAKEUP,
                            System.currentTimeMillis(), 1000 * 60 * 5, pIntents);




                    //FIRST REQUIR PERMISSTION BY OPENING SETTING THEN THIS CODE WILL WORK and aad permisstion to mainfest file
//                        startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));


                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Helper.sendCrashReportDetails(null,e.getMessage(),e.getStackTrace());
                }


            }

        }
}catch (Exception e){
    Helper.sendCrashReportDetails(null,e.getMessage(),e.getStackTrace());
}
    }

    @Override
    public void onBackPressed() {

        exit();

    }

    private void initWebView() {
        try{
        webView.setWebChromeClient(new MyWebChromeClient(this));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
                invalidateOptionsMenu();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                invalidateOptionsMenu();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                progressBar.setVisibility(View.GONE);
                invalidateOptionsMenu();
            }
        });

        }catch (Exception e){
            Helper.sendCrashReportDetails(null,e.getMessage(),e.getStackTrace());
            webView.clearCache(true);
            webView.clearHistory();
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setHorizontalScrollBarEnabled(false);
        }
    }

    public void exit() {
        try{
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        }catch (Exception e){
            Helper.sendCrashReportDetails(null,e.getMessage(),e.getStackTrace());
        }
    }



    private class MyWebChromeClient extends WebChromeClient {
        Context context;

        public MyWebChromeClient(Context context) {
            super();
            this.context = context;
        }
    }

}

