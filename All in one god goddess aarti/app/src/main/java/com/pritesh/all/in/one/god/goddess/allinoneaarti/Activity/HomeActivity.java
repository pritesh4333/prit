package com.pritesh.all.in.one.god.goddess.allinoneaarti.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pritesh.all.in.one.god.goddess.allinoneaarti.BuildConfig;
import com.pritesh.all.in.one.god.goddess.allinoneaarti.Language.LocaleHelper;
import com.pritesh.all.in.one.god.goddess.allinoneaarti.R;
import com.pritesh.all.in.one.god.goddess.allinoneaarti.Utils.Helper;
import com.pritesh.all.in.one.god.goddess.allinoneaarti.Utils.InternetConnection;

import java.util.Locale;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;

public class HomeActivity extends AppCompatActivity {
    ImageView videlist,language,moreapps,likeactivity;
    TextView lang_text;
    public static final String MY_PREFS_NAME = "AllInOneArti";
    AppUpdateManager appUpdateManager;
    private static final int MY_REQUEST_CODE = 1;
    private static String LOG_TAG = "EXAMPLE";
    String admob_app_id,banner_home_footer;
    private static final int REQUEST_PERMISSIONS = 100;
    AdView mAdView;
    private Boolean chekpermistion = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        videlist=(ImageView)findViewById(R.id.videlist);
        likeactivity=(ImageView)findViewById(R.id.likeactivity);
        lang_text=(TextView) findViewById(R.id.lang_text);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        admob_app_id = prefs.getString("admob_app_id", "");
        if (admob_app_id.equalsIgnoreCase("")){
            admob_app_id=getString(R.string.admob_app_id);
            banner_home_footer=getString(R.string.banner_home_footer);
        }else {
            admob_app_id = prefs.getString("admob_app_id", "");
            banner_home_footer = prefs.getString("banner_home_footer", "");
        }
        // Loading banner add
        MobileAds.initialize(this, admob_app_id);
        LinearLayout adContainer = findViewById(R.id.banneradd);
          mAdView = new AdView(HomeActivity.this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(banner_home_footer);
        adContainer.addView(mAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        /// Initializing the Google Admob SDK  NATIVE
        MobileAds.initialize(HomeActivity.this, new OnInitializationCompleteListener() {@Override
        public void onInitializationComplete(InitializationStatus initializationStatus) {

            //Showing a simple Toast Message to the user when The Google AdMob Sdk Initialization is Completed
            //  Toast.makeText(HomeActivity.this, "AdMob Sdk Initialize " + initializationStatus.toString(), Toast.LENGTH_LONG).show();
        }
        });

        appUpdateManager = AppUpdateManagerFactory.create(HomeActivity.this);


        videlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
        likeactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,LikeActivity.class);
                startActivity(intent);
            }
        });
        language=(ImageView)findViewById(R.id.language);
        moreapps=(ImageView)findViewById(R.id.moreaaps);
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Locale locale ;
                if (lang_text.getText().toString().contains("हिंदी")) {
                    locale= new Locale("hi");
                }else{
                    locale= new Locale("en");
                }

//                //Change Application level locale
//                if (lang_text.getText().toString().contains("हिंदी")) {
//                    LocaleHelper.setLocale(HomeActivity.this, "hi");
//                }else{
//                    LocaleHelper.setLocale(HomeActivity.this, "en");
//                }


                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                recreate();
            }
        });
        moreapps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGroup viewGroup = findViewById(android.R.id.content);
                //then we will inflate the custom alert dialog xml that we created
                View dialogView = LayoutInflater.from(HomeActivity.this).inflate(R.layout.alert_more_apps, viewGroup, false);
                //Now we need an AlertDialog.Builder object
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                //setting the view of the builder to our custom view that we already inflated
                builder.setView(dialogView);

                final AlertDialog alertDialog = builder.create();

                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                String natice_advanceadd;
                admob_app_id = prefs.getString("admob_app_id", "");
                if (admob_app_id.equalsIgnoreCase("")){
                    admob_app_id=getString(R.string.admob_app_id);
                    natice_advanceadd=getString(R.string.natice_advanceadd);
                }else {
                    admob_app_id = prefs.getString("admob_app_id", "");
                    natice_advanceadd = prefs.getString("natice_advanceadd","");
                }



                final TemplateView[] template = new TemplateView[1];

                //Initializing the AdLoader   objects
                AdLoader  adLoader = new AdLoader.Builder(HomeActivity.this, natice_advanceadd).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

                    private ColorDrawable background;@Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                        template[0] = dialogView.findViewById(R.id.nativeTemplateView);
                        template[0].setStyles(styles);
                        template[0].setNativeAd(unifiedNativeAd);
                        dialogView.findViewById(R.id.nativeTemplateView).setVisibility(View.VISIBLE);
                        // Showing a simple Toast message to user when Native an ad is Loaded and ready to show
                     //   Toast.makeText(HomeActivity.this, "Native Ad is loaded ,now you can show the native ad  ", Toast.LENGTH_LONG).show();
                    }

                }).build();


                // load Native Ad with the Request
                adLoader.loadAds(new AdRequest.Builder().build(),5);
                // Showing a simple Toast message to user when Native an ad is Loading
              //  Toast.makeText(HomeActivity.this, "Native Ad is loading ", Toast.LENGTH_LONG).show();
                // Showing a simple Toast message to user when Native an ad is Loading


                TextView ok=(TextView)dialogView.findViewById(R.id.ok);
                LinearLayout videocommproapp=(LinearLayout)dialogView.findViewById(R.id.videocompressorproapps);
                LinearLayout videtomp3=(LinearLayout)dialogView.findViewById(R.id.videtomp3);




                videocommproapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.pritesh.videocompressorpro_fastvideocompressor" )));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.pritesh.videocompressorpro_fastvideocompressor")));
                        }
                    }

                });
                videtomp3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.prit.videotomp3pro" )));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.prit.videotomp3pro")));
                        }
                    }

                });
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });




                alertDialog.show();

            }
        });
        chekpermisstion();
        if (InternetConnection.checkConnection(this)) {
            // Its Available...
            checkforUpdate();
        } else {
            // Not Available...
            Helper.LogPrint("No Internet","No internet");
        }

//        try {
//
//            Integer i = null;
//                    int c=i/0;
//        }catch(Exception e){
//            Crashlytics.log("Line no 384"+e);
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]

        // [END handle_data_extras]
        FirebaseMessaging.getInstance().subscribeToTopic(getString(R.string.default_notification_channel_name))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                        String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                        }
                        Log.d(LOG_TAG, msg);
                        // Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

    }
    public void chekpermisstion() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                    Manifest.permission.RECORD_AUDIO))) {

            } else {
                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{ Manifest.permission.RECORD_AUDIO},
                        REQUEST_PERMISSIONS);
                chekpermistion = false;

            }
        } else {
            chekpermistion = true;

        }
    }
    @Override
    public void onBackPressed() {



        ViewGroup viewGroup = findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.alert_layout, viewGroup, false);
        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        TextView rateus=(TextView)dialogView.findViewById(R.id.rateus_btn);
        TextView no=(TextView)dialogView.findViewById(R.id.no_btn);
        TextView yes=(TextView)dialogView.findViewById(R.id.yes_btn);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String natice_advanceadd;
        admob_app_id = prefs.getString("admob_app_id", "");
        if (admob_app_id.equalsIgnoreCase("")){
            admob_app_id=getString(R.string.admob_app_id);
            natice_advanceadd=getString(R.string.natice_advanceadd);
        }else {
            admob_app_id = prefs.getString("admob_app_id", "");
            natice_advanceadd = prefs.getString("natice_advanceadd","");
        }
        final TemplateView[] template = new TemplateView[1];

        //Initializing the AdLoader   objects
        AdLoader  adLoader = new AdLoader.Builder(HomeActivity.this, natice_advanceadd).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

            private ColorDrawable background;@Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                NativeTemplateStyle styles = new
                        NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                template[0] = dialogView.findViewById(R.id.nativeTemplateView);
                template[0].setStyles(styles);
                template[0].setNativeAd(unifiedNativeAd);
                dialogView.findViewById(R.id.nativeTemplateView).setVisibility(View.VISIBLE);
                // Showing a simple Toast message to user when Native an ad is Loaded and ready to show
                //   Toast.makeText(HomeActivity.this, "Native Ad is loaded ,now you can show the native ad  ", Toast.LENGTH_LONG).show();
            }

        }).build();


        // load Native Ad with the Request
        adLoader.loadAds(new AdRequest.Builder().build(),5);

        rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                finish();
            }
        });



        alertDialog.show();

    }
    private void checkforUpdate() {

        // Creates instance of the manager.


// Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // For a flexible update, use AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)) {
                // Request the update.
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                            appUpdateInfo,
                            // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                            IMMEDIATE,
                            // The current activity making the update request.
                            this,
                            // Include a request code to later monitor this update request.
                            MY_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }else{
                Helper.LogPrint("update Method","No update");
            }
        });


    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        // Checks that the update is not stalled during 'onResume()'.
// However, you should execute this check at all entry points into the app.
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {

                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.
                                try {
                                    appUpdateManager.startUpdateFlowForResult(
                                            appUpdateInfo,
                                            IMMEDIATE,
                                            this,
                                            MY_REQUEST_CODE);
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                Helper.LogPrint("No update On Resume","No update");
                            }
                        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Helper.LogPrint("Update flow failed! Result code: ", "" + resultCode);
                // If the update is cancelled or fails,
                // you can request to start the update again.
            }else{
                Helper.LogPrint("Update Done: ", "" + resultCode);
            }
        }
    }
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menushare, menu);

        MenuItem item = menu.findItem(R.id.mShare);
        MenuItem feedback = menu.findItem(R.id.feedback);
        MenuItem about=menu.findItem(R.id.about);

        about.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                LayoutInflater factory = LayoutInflater.from(HomeActivity.this);
                final View deleteDialogView = factory.inflate(R.layout.feedbackpopup, null);

                final AlertDialog deleteDialog = new AlertDialog.Builder(HomeActivity.this).create();
                deleteDialog.setView(deleteDialogView);



                Button feedbacksubmit = (Button) deleteDialogView.findViewById(R.id.feedbacksubmit);
                TextView aboutinfo=(TextView) deleteDialogView.findViewById(R.id.aboutinfo);
                TextView loveapp=(TextView) deleteDialogView.findViewById(R.id.loveapp);
                loveapp.setText(getString(R.string.about));
                loveapp.setVisibility(View.VISIBLE);
                // For Live Native Express Adview
                String natice_advanceadd;
                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                admob_app_id = prefs.getString("admob_app_id", "");
                if (admob_app_id.equalsIgnoreCase("")){
                    admob_app_id=getString(R.string.admob_app_id);
                    natice_advanceadd=getString(R.string.natice_advanceadd);
                }else {
                    admob_app_id = prefs.getString("admob_app_id", "");
                    natice_advanceadd = prefs.getString("natice_advanceadd","");
                }
                final TemplateView[] template = new TemplateView[1];

                //Initializing the AdLoader   objects
                AdLoader  adLoader = new AdLoader.Builder(HomeActivity.this, natice_advanceadd).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

                    private ColorDrawable background;@Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                        template[0] = deleteDialogView.findViewById(R.id.nativeTemplateView);
                        template[0].setStyles(styles);
                        template[0].setNativeAd(unifiedNativeAd);
                        deleteDialogView.findViewById(R.id.nativeTemplateView).setVisibility(View.VISIBLE);
                        // Showing a simple Toast message to user when Native an ad is Loaded and ready to show
                        //   Toast.makeText(HomeActivity.this, "Native Ad is loaded ,now you can show the native ad  ", Toast.LENGTH_LONG).show();
                    }

                }).build();


                // load Native Ad with the Request
                adLoader.loadAds(new AdRequest.Builder().build(),5);

                feedbacksubmit.setVisibility(View.GONE);
                aboutinfo.setVisibility(View.VISIBLE);
                aboutinfo.setText("Version:-  "+ BuildConfig.VERSION_NAME);

                deleteDialog.show();
                return false;
            }
        });
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Video To MP3");
                    String shareMessage = "Let me recommend you this application\n\n You can convert mp4 video mp3 file with dynamic resolution selection with faster compression.\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }

                return false;
            }
        });
        feedback.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {


                LayoutInflater factory = LayoutInflater.from(HomeActivity.this);
                final View deleteDialogView = factory.inflate(R.layout.feedbackpopup, null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(HomeActivity.this).create();
                deleteDialog.setView(deleteDialogView);


                TextView loveapp=(TextView)deleteDialogView.findViewById(R.id.loveapp);
                loveapp.setVisibility(View.VISIBLE);
                Button feedbacksubmit = (Button) deleteDialogView.findViewById(R.id.feedbacksubmit);
                // For Live Native Express Adview
                String natice_advanceadd;
                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                admob_app_id = prefs.getString("admob_app_id", "");
                if (admob_app_id.equalsIgnoreCase("")){
                    admob_app_id=getString(R.string.admob_app_id);
                    natice_advanceadd=getString(R.string.natice_advanceadd);
                }else {
                    admob_app_id = prefs.getString("admob_app_id", "");
                    natice_advanceadd = prefs.getString("natice_advanceadd","");
                }
                final TemplateView[] template = new TemplateView[1];

                //Initializing the AdLoader   objects
                AdLoader  adLoader = new AdLoader.Builder(HomeActivity.this, natice_advanceadd).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

                    private ColorDrawable background;@Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                        template[0] = deleteDialogView.findViewById(R.id.nativeTemplateView);
                        template[0].setStyles(styles);
                        template[0].setNativeAd(unifiedNativeAd);
                        deleteDialogView.findViewById(R.id.nativeTemplateView).setVisibility(View.VISIBLE);
                        // Showing a simple Toast message to user when Native an ad is Loaded and ready to show
                        //   Toast.makeText(HomeActivity.this, "Native Ad is loaded ,now you can show the native ad  ", Toast.LENGTH_LONG).show();
                    }

                }).build();


                // load Native Ad with the Request
                adLoader.loadAds(new AdRequest.Builder().build(),5);


                feedbacksubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                        }
//                            String feedbackvalue =text.getText().toString().trim();
//                            if (feedbackvalue.equalsIgnoreCase("")) {
//                                Toast.makeText(HomeActivity.this,"Please provide feedback hear or in play store",Toast.LENGTH_LONG).show();
//                            }else{
//                                String androidSDK = "null", androidVersion = "null", androidBrand = "null",
//                                        androidManufacturer = "null", androidModel = "null", androidDeviceId = "null";
//                                String versionCode = "null", versionName = "null";
//                                PackageInfo pInfo = null;
//                                String deviceDetail = "null";
//                                pInfo = HomeActivity.this.getPackageManager().getPackageInfo(HomeActivity.this.getPackageName(), 0);
//                                versionCode = String.valueOf(pInfo.versionCode).trim();
//                                versionName = String.valueOf(pInfo.versionName).trim();
//                                androidSDK = String.valueOf(android.os.Build.VERSION.SDK_INT);
//                                androidVersion = android.os.Build.VERSION.RELEASE;
//                                androidBrand = android.os.Build.BRAND;
//                                androidManufacturer = android.os.Build.MANUFACTURER;
//                                androidModel = android.os.Build.MODEL;
//                                androidDeviceId = Settings.Secure.getString(HomeActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
//                                deviceDetail = "App Version Code: " + versionCode + "\nApp Version Number: " + versionName + "\nSDK: " + androidSDK + "\nVersion: " + androidVersion + "\nBrand: " + androidBrand +
//                                        "\nManufacturer: " + androidManufacturer + "\nModel: " + androidModel + "\nAndroid Device Id: " + androidDeviceId;
//
//                                JSONObject feed = new JSONObject();
//
//                                feed.put("Feedback", feedbackvalue);
//                                feed.put("deviceDetail", deviceDetail);
//
//
//                                sendMessageToserver(feed);
//                            }
                        deleteDialog.dismiss();
                    }
                });

                deleteDialog.show();
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

}