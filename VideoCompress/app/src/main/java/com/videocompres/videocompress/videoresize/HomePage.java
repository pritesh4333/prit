package com.videocompres.videocompress.videoresize;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

import nl.bravobit.ffmpeg.ExecuteBinaryResponseHandler;
import nl.bravobit.ffmpeg.FFmpeg;

public class HomePage extends AppCompatActivity {

    LinearLayout MyGallery,MyVideo_List;
    private static Activity activity;
    private static final int MY_REQUEST_CODE = 1;
    private Boolean permistion = false;
    private static final int REQUEST_PERMISSIONS = 100;
    AppUpdateManager appUpdateManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_layout);

        MyGallery = (LinearLayout) findViewById(R.id.MyGallery);
        MyVideo_List = (LinearLayout) findViewById(R.id.MyVideo_List);
        activity=this;
        appUpdateManager = AppUpdateManagerFactory.create(HomePage.this);

        MyGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (permistion) {

//                    Intent ii = new Intent(HomePage.this, VideoListActivity.class);
//                    // Intent ii = new Intent(HomePage.this, NativeAdd.class);
//                    startActivity(ii);
                } else {
                    ActivityCompat.requestPermissions(HomePage.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSIONS);
                }

            }
        });
        MyVideo_List.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (permistion) {

//                    Intent ii = new Intent(HomePage.this, MyVideoListActivity.class);
//                    // Intent ii = new Intent(HomePage.this, NativeAdd.class);
//                    startActivity(ii);
                } else {
                    ActivityCompat.requestPermissions(HomePage.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSIONS);
                }

            }
        });
        grantpermisstion();
        if (InternetConnection.checkConnection(this)) {
            checkforUpdate();
        } else {
            Log.e("No Internet","No internet");
        }
        if (FFmpeg.getInstance(this).isSupported()) {
            versionFFmpeg();
        } else { ;
            Log.e("TAG","ffmpeg not supported!");
        }

    }
    private void checkforUpdate() {

        // Creates instance of the manager.


// Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // For a flexible update, use AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // Request the update.
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                            appUpdateInfo,
                            // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                            AppUpdateType.IMMEDIATE,
                            // The current activity making the update request.
                            this,
                            // Include a request code to later monitor this update request.
                            MY_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }else{
                Log.e("update Method","No update");
            }
        });


    }

    public void grantpermisstion() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(HomePage.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(HomePage.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(HomePage.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
                permistion = false;

            }
        } else {
            permistion = true;

        }
    }
    private void versionFFmpeg() {
        FFmpeg.getInstance(this).execute(new String[]{"-version"}, new ExecuteBinaryResponseHandler() {
            @Override
            public void onSuccess(String message) {
                Log.e("TAG",message);
            }

            @Override
            public void onProgress(String message) {
                Log.e("TAG",message);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        permistion = true;
                    } else {
                        permistion = false;
                        Toast.makeText(HomePage.this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

//    @Override
//    public void onBackPressed() {
//        ViewGroup viewGroup = findViewById(android.R.id.content);
//        View dialogView = LayoutInflater.from(this).inflate(R.layout.alert_layout, viewGroup, false);
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setView(dialogView);
//        final AlertDialog alertDialog = builder.create();
//        TextView rateus=(TextView)dialogView.findViewById(R.id.rateus_btn);
//        TextView no=(TextView)dialogView.findViewById(R.id.no_btn);
//        TextView yes=(TextView)dialogView.findViewById(R.id.yes_btn);
//        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
//        String natice_advanceadd;
//        admob_app_id = prefs.getString("admob_app_id", "");
//        if (admob_app_id.equalsIgnoreCase("")){
//            admob_app_id=getString(R.string.admob_app_id);
//            natice_advanceadd=getString(R.string.natice_advanceadd);
//        }else {
//            admob_app_id = prefs.getString("admob_app_id", "");
//            natice_advanceadd = prefs.getString("natice_advanceadd","");
//        }
//        final TemplateView[] template = new TemplateView[1];
//
//        //Initializing the AdLoader   objects
//        AdLoader adLoader = new AdLoader.Builder(HomePage.this, natice_advanceadd).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
//
//            private ColorDrawable background;@Override
//            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
//
//                NativeTemplateStyle styles = new
//                        NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();
//
//                template[0] = dialogView.findViewById(R.id.nativeTemplateView);
//                template[0].setStyles(styles);
//                template[0].setNativeAd(unifiedNativeAd);
//                dialogView.findViewById(R.id.nativeTemplateView).setVisibility(View.VISIBLE);
//                // Showing a simple Toast message to user when Native an ad is Loaded and ready to show
//                //   Toast.makeText(HomePage.this, "Native Ad is loaded ,now you can show the native ad  ", Toast.LENGTH_LONG).show();
//            }
//
//        }).build();
//
//
//        // load Native Ad with the Request
//        adLoader.loadAds(new AdRequest.Builder().build(),5);
//
//        rateus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
//                try {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
//                } catch (android.content.ActivityNotFoundException anfe) {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
//                }
//            }
//        });
//        no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.cancel();
//            }
//        });
//        yes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//                activity.finish();
//            }
//        });
//
//
//
//        alertDialog.show();
//
//    }

//    @Override
//    public void onPause() {
//        if (mAdView != null) {
//            mAdView.pause();
//        }
//        super.onPause();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (mAdView != null) {
//            mAdView.resume();
//        }
//        // Checks that the update is not stalled during 'onResume()'.
//// However, you should execute this check at all entry points into the app.
//        appUpdateManager
//                .getAppUpdateInfo()
//                .addOnSuccessListener(
//                        appUpdateInfo -> {
//
//                            if (appUpdateInfo.updateAvailability()
//                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
//                                // If an in-app update is already running, resume the update.
//                                try {
//                                    appUpdateManager.startUpdateFlowForResult(
//                                            appUpdateInfo,
//                                            IMMEDIATE,
//                                            this,
//                                            MY_REQUEST_CODE);
//                                } catch (IntentSender.SendIntentException e) {
//                                    e.printStackTrace();
//                                }
//                            }else{
//                                Log.e("No update On Resume","No update");
//                            }
//                        });
//
//    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == MY_REQUEST_CODE) {
//            if (resultCode != RESULT_OK) {
//                Log.e("Update flow failed! Result code: ", "" + resultCode);
//                // If the update is cancelled or fails,
//                // you can request to start the update again.
//            }else{
//                Log.e("Update Done: ", "" + resultCode);
//            }
//        }
//    }
//    @Override
//    public void onDestroy() {
//        if (mAdView != null) {
//            mAdView.destroy();
//        }
//        super.onDestroy();
//    }
}