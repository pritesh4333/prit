package com.reels.video.download.instagram.video.downloader.saver.story.video.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.reels.video.download.instagram.video.downloader.saver.story.video.BuildConfig;
import com.reels.video.download.instagram.video.downloader.saver.story.video.Download;
import com.reels.video.download.instagram.video.downloader.saver.story.video.DownloadService;
import com.reels.video.download.instagram.video.downloader.saver.story.video.R;
import com.reels.video.download.instagram.video.downloader.saver.story.video.RetrofitInterface;
import com.reels.video.download.instagram.video.downloader.saver.story.video.Utils.Helper;
import com.reels.video.download.instagram.video.downloader.saver.story.video.Utils.InternetConnection;
import com.reels.video.download.instagram.video.downloader.saver.story.video.instagramlogin.util.InstagramApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;

public class MainActivity extends AppCompatActivity {
    TextView progress;
    ProgressBar progressBar;
    AdLoader  adLoader;
    EditText URL;
    AlertDialog alertDialog;
    View pd;
    AppUpdateManager appUpdateManager;
    private static final int MY_REQUEST_CODE = 1;
    private static final int REQUEST_PERMISSIONS = 100;
    private Boolean chekpermistion = false;
    public static final String MY_PREFS_NAME = "InstagramReelsPrefsFile";
    String admob_app_id,banner_home_footer,interstitial_full_screen;
    AdView mAdView;
    InterstitialAd mInterstitialAd;
    TextView moreapps;
    ArrayList<String> imgUrls=new ArrayList<>();
    public static final String MESSAGE_PROGRESS = "message_progress";
    Boolean one=true ,two=true,three=true,four=true,five=true;

    AdRequest adRequest;
    File mypath=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);



        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        admob_app_id = prefs.getString("admob_app_id", "");
        if (admob_app_id.equalsIgnoreCase("")){
            admob_app_id=getString(R.string.admob_app_id);
            banner_home_footer=getString(R.string.banner_home_footer);
            interstitial_full_screen=getString(R.string.interstitial_full_screen);
        }else {
            admob_app_id = prefs.getString("admob_app_id", "");
            banner_home_footer = prefs.getString("banner_home_footer", "");
            interstitial_full_screen=prefs.getString("interstitial_full_screen","");
        }
        // Loading banner add
        MobileAds.initialize(this, admob_app_id);
        LinearLayout adContainer = findViewById(R.id.banneradd);
        mAdView = new AdView(MainActivity.this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(banner_home_footer);
        adContainer.addView(mAdView);
          adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //test creashlatecs
//        Button crashButton = new Button(this);
//        crashButton.setText("Crash!");
//        crashButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                throw new RuntimeException("Test Crash"); // Force a crash
//            }
//        });
//
//        addContentView(crashButton, new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT));

        /// Initializing the Google Admob SDK  NATIVE
        MobileAds.initialize(MainActivity.this, new OnInitializationCompleteListener() {@Override
        public void onInitializationComplete(InitializationStatus initializationStatus) {

            //Showing a simple Toast Message to the user when The Google AdMob Sdk Initialization is Completed
            //  Toast.makeText(MainActivity.this, "AdMob Sdk Initialize " + initializationStatus.toString(), Toast.LENGTH_LONG).show();
        }
        });

        //app update
        appUpdateManager = AppUpdateManagerFactory.create(MainActivity.this);

        final LinearLayout download=(LinearLayout)findViewById(R.id.download);
        final LinearLayout getall=(LinearLayout)findViewById(R.id.download_list);

        moreapps=(TextView) findViewById(R.id.more_apps);
        URL=(EditText)findViewById(R.id.URL);



        getall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chekpermistion) {


                    try {
                        mypath = new File(Environment.getExternalStorageDirectory(), "Reels Video Downloader");

                        if (!mypath.exists()) {
                            if (!mypath.mkdirs()) {
                                Helper.LogPrint("App", "failed to create directory");
                            }
                        }
                    }catch(Exception e){}
                    Intent i = new Intent(MainActivity.this,MyVideoListActivity.class);
                    startActivity(i);
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSIONS);
                }


            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ///// Downliad images url codition && !URL.getText().toString().trim().contains("www.instagram.com/p")
                if (chekpermistion) {

                    if (URL.getText().toString().trim().isEmpty()){
                        URL.setError("Please Enter URL");
                    }else if(!URL.getText().toString().trim().contains("www.instagram.com/reel") &&
                            !URL.getText().toString().trim().contains("www.instagram.com/tv")
                            ){
                        URL.setError("Provide Instagram Reel or IGTV URL");
                    }else if(!InternetConnection.checkConnection(MainActivity.this)) {
                        Toast.makeText(MainActivity.this,"Please check Internet connection",Toast.LENGTH_LONG).show();
                    }else{
                        registerReceiver();

                        try {
                            mypath = new File(Environment.getExternalStorageDirectory(), "Reels Video Downloader");

                            if (!mypath.exists()) {
                                if (!mypath.mkdirs()) {
                                    Helper.LogPrint("App", "failed to create directory");
                                }
                            }
                        }catch(Exception e){}
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(download.getWindowToken(), 0);
                        String fileUrl = URL.getText().toString().trim();
                        String[] parts = fileUrl.split("igshid");
                        Helper.LogPrint("Log", parts[0] + "__a=1");
                        new JsonTask().execute(parts[0] + "__a=1");
                    }
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSIONS);
                }


            }
        });

        moreapps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGroup viewGroup = findViewById(android.R.id.content);
                //then we will inflate the custom alert dialog xml that we created
                final View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.alert_more_apps, viewGroup, false);
                //Now we need an AlertDialog.Builder object
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                AdLoader adLoader = new AdLoader.Builder(MainActivity.this, natice_advanceadd).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

                    private ColorDrawable background;@Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                        template[0] = dialogView.findViewById(R.id.nativeTemplateView);
                        template[0].setStyles(styles);
                        template[0].setNativeAd(unifiedNativeAd);
                        dialogView.findViewById(R.id.nativeTemplateView).setVisibility(View.VISIBLE);
                        // Showing a simple Toast message to user when Native an ad is Loaded and ready to show
                        //   Toast.makeText(MainActivity.this, "Native Ad is loaded ,now you can show the native ad  ", Toast.LENGTH_LONG).show();
                    }

                }).build();


                // load Native Ad with the Request
                adLoader.loadAds(new AdRequest.Builder().build(),5);
                // Showing a simple Toast message to user when Native an ad is Loading
                //  Toast.makeText(MainActivity.this, "Native Ad is loading ", Toast.LENGTH_LONG).show();
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
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=mp3videoconverter.videotomp3converter.audioconverter.converter.videomaker.videotoaudio" )));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=mp3videoconverter.videotomp3converter.audioconverter.converter.videomaker.videotoaudio")));
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

        if (InternetConnection.checkConnection(this)) {
            // Its Available...
            checkforUpdate();
        } else {
            // Not Available...
            Helper.LogPrint("No Internet","No internet");
        }



//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            // Create channel to show notifications.
//            String channelId  = getString(R.string.default_notification_channel_id);
//            String channelName = getString(R.string.default_notification_channel_name);
//            NotificationManager notificationManager =
//                    getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
//                    channelName, NotificationManager.IMPORTANCE_LOW));
//        }
//
//
//
//        FirebaseMessaging.getInstance().subscribeToTopic(getString(R.string.default_notification_channel_name))
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
//                        String msg = getString(R.string.msg_subscribed);
//                        if (!task.isSuccessful()) {
//                            msg = getString(R.string.msg_subscribe_failed);
//                        }
//                        Helper.LogPrint("LOG_TAG", msg);
//                        // Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }
        FirebaseMessaging.getInstance().subscribeToTopic(getString(R.string.default_notification_channel_name))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                        String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                        }
                        Log.d("Notification", msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }


                });
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("TAG", msg);
                        Toast.makeText(MainActivity.this, "token"+msg, Toast.LENGTH_SHORT).show();
                    }


                });
        chekpermisstion();
    }
    @Override
    public void onBackPressed() {



        ViewGroup viewGroup = findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.alert_layout, viewGroup, false);
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
        AdLoader  adLoader = new AdLoader.Builder(MainActivity.this, natice_advanceadd).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

            private ColorDrawable background;@Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                NativeTemplateStyle styles = new
                        NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                template[0] = dialogView.findViewById(R.id.nativeTemplateView);
                template[0].setStyles(styles);
                template[0].setNativeAd(unifiedNativeAd);
                dialogView.findViewById(R.id.nativeTemplateView).setVisibility(View.VISIBLE);
                // Showing a simple Toast message to user when Native an ad is Loaded and ready to show
                //   Toast.makeText(MainActivity.this, "Native Ad is loaded ,now you can show the native ad  ", Toast.LENGTH_LONG).show();
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
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
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
                                MainActivity.this,
                                // Include a request code to later monitor this update request.
                                MY_REQUEST_CODE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                } else {
                    Helper.LogPrint("update Method", "No update");
                }
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
        URL.setText("");

        if (mAdView != null) {
            mAdView.resume();
        }
        // Checks that the update is not stalled during 'onResume()'.
// However, you should execute this check at all entry points into the app.
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        new OnSuccessListener<AppUpdateInfo>() {
                            @Override
                            public void onSuccess(AppUpdateInfo appUpdateInfo) {

                                if (appUpdateInfo.updateAvailability()
                                        == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                    // If an in-app update is already running, resume the update.
                                    try {
                                        appUpdateManager.startUpdateFlowForResult(
                                                appUpdateInfo,
                                                IMMEDIATE,
                                                MainActivity.this,
                                                MY_REQUEST_CODE);
                                    } catch (IntentSender.SendIntentException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Helper.LogPrint("No update On Resume", "No update");
                                }
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
                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                final View deleteDialogView = factory.inflate(R.layout.feedbackpopup, null);

                final AlertDialog deleteDialog = new AlertDialog.Builder(MainActivity.this).create();
                deleteDialog.setView(deleteDialogView);



                Button feedbacksubmit = (Button) deleteDialogView.findViewById(R.id.feedbacksubmit);
                TextView aboutinfo=(TextView) deleteDialogView.findViewById(R.id.aboutinfo);
                TextView loveapp=(TextView) deleteDialogView.findViewById(R.id.loveapp);
                loveapp.setText("About");
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
                AdLoader  adLoader = new AdLoader.Builder(MainActivity.this, natice_advanceadd).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

                    private ColorDrawable background;@Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                        template[0] = deleteDialogView.findViewById(R.id.nativeTemplateView);
                        template[0].setStyles(styles);
                        template[0].setNativeAd(unifiedNativeAd);
                        deleteDialogView.findViewById(R.id.nativeTemplateView).setVisibility(View.VISIBLE);
                        // Showing a simple Toast message to user when Native an ad is Loaded and ready to show
                        //   Toast.makeText(MainActivity.this, "Native Ad is loaded ,now you can show the native ad  ", Toast.LENGTH_LONG).show();
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
                    String shareMessage = "Let me recommend you this application\n\n You can easily download instagram reels and share in any other app you want and you can extract mp3 also.\n\n";
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


                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                final View deleteDialogView = factory.inflate(R.layout.feedbackpopup, null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(MainActivity.this).create();
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
                AdLoader  adLoader = new AdLoader.Builder(MainActivity.this, natice_advanceadd).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

                    private ColorDrawable background;@Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                        template[0] = deleteDialogView.findViewById(R.id.nativeTemplateView);
                        template[0].setStyles(styles);
                        template[0].setNativeAd(unifiedNativeAd);
                        deleteDialogView.findViewById(R.id.nativeTemplateView).setVisibility(View.VISIBLE);
                        // Showing a simple Toast message to user when Native an ad is Loaded and ready to show
                        //   Toast.makeText(MainActivity.this, "Native Ad is loaded ,now you can show the native ad  ", Toast.LENGTH_LONG).show();
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

                        deleteDialog.dismiss();
                    }
                });

                deleteDialog.show();
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    private void registerReceiver(){

        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);

    }

    public void chekpermisstion() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
                chekpermistion = false;

            }
        } else {
            chekpermistion = true;

        }
    }
    // Check permission granted or not
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        chekpermistion = true;
                    } else {
                        chekpermistion = false;
                        Toast.makeText(MainActivity.this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }



    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            ViewGroup viewGroup = findViewById(android.R.id.content);

            //then we will inflate the custom alert dialog xml that we created
            pd = LayoutInflater.from(MainActivity.this).inflate(R.layout.progress_dailog, viewGroup, false);


            //Now we need an AlertDialog.Builder object
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            //setting the view of the builder to our custom view that we already inflated
            builder.setView(pd);




            alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            progress = (TextView) pd.findViewById(R.id.progress);
            progressBar = (ProgressBar) pd.findViewById(R.id.progress_bar);

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
               adLoader = new AdLoader.Builder(MainActivity.this, natice_advanceadd).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

                private ColorDrawable background;@Override
                public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                    NativeTemplateStyle styles = new
                            NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                    template[0] = pd.findViewById(R.id.nativeTemplateView);
                    template[0].setStyles(styles);
                    template[0].setNativeAd(unifiedNativeAd);
                    pd.findViewById(R.id.nativeTemplateView).setVisibility(View.VISIBLE);
                    // Showing a simple Toast message to user when Native an ad is Loaded and ready to show
                    //   Toast.makeText(MainActivity.this, "Native Ad is loaded ,now you can show the native ad  ", Toast.LENGTH_LONG).show();
                }

            }).build();


            // load Native Ad with the Request
            adLoader.loadAds(new AdRequest.Builder().build(),5);
            alertDialog.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                java.net.URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Helper.LogPrint("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String loudScreaming="";
            String src="";
            try {
                if (result == null ) {
                    alertDialog.dismiss();
                    String fileUrl = URL.getText().toString().trim();
                    if (fileUrl.contains("-")||fileUrl.contains("_")){

                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Private Account")


                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Continue with delete operation
                                        URL.setText("");

                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.

                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }else {
                        String[] parts = fileUrl.split("igshid");
                        Helper.LogPrint("Log", parts[0] + "__a=1");
                        new JsonTask().execute(parts[0] + "__a=1");
                    }
                } else {


                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("graphql");
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("shortcode_media");
                    try {
                          loudScreaming = jsonObject2.getString("video_url");
                    }catch(Exception e){
                        imgUrls= new ArrayList<>();
                        JSONArray jsonArray=jsonObject2.getJSONArray("display_resources");
                        for (int i =0;i<jsonArray.length();i++){
                            JSONObject jsonObject3= jsonArray.getJSONObject(i);
                              src = jsonObject3.getString("src");
                            imgUrls.add(src);
                            Helper.LogPrint("SRC",src);
                        }

                    }


                        Intent intent = new Intent(MainActivity.this, DownloadService.class);
                        intent.putExtra("url",loudScreaming.substring(39));
                        intent.putExtra("path",mypath.toString());
                        startService(intent);





                }
            } catch(JSONException e){
                e.printStackTrace();
                alertDialog.dismiss();
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        URL.setText("");
                        Toast.makeText(MainActivity.this,"Bad URL",Toast.LENGTH_LONG).show();
                    }
                });
            }

        }
    }

//    private String statusMessage(Cursor c) {
//       String msg = "";
//
//        switch (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
//            case DownloadManager.STATUS_FAILED:
//                msg = "Download failed!";
//
//                alertDialog.dismiss();
//                MainActivity.this.runOnUiThread(new Runnable() {
//                    public void run() {
//
//                        showmessage("Download failed!");
//                    }
//                });
//                break;
//
//            case DownloadManager.STATUS_PAUSED:
//                msg = "Download paused!";
//
//                alertDialog.dismiss();
//
//                break;
//
//            case DownloadManager.STATUS_PENDING:
//                msg = "Download pending!";
//
//
//
//                break;
//
//            case DownloadManager.STATUS_RUNNING:
//                msg = "Download Running";
//                break;
//
//            case DownloadManager.STATUS_SUCCESSFUL:
//                msg = "Download complete! store in My Reels";
//                if (alertDialog.isShowing()) {
//                    alertDialog.dismiss();
//                    MainActivity.this.runOnUiThread(new Runnable() {
//                        public void run() {
//
//                            showmessage("Download complete save in My Reels.");
//                        }
//                    });
//                }
//
//
//                break;
//
//            default:
//                msg = "Download is nowhere in sight";
//
//                alertDialog.dismiss();
//
//                break;
//        }
//
//        return (msg);
//    }


    public void showmessage(String message){
        URL.setText("");
        LayoutInflater factory = LayoutInflater.from(MainActivity.this);
        final View deleteDialogView = factory.inflate(R.layout.feedbackpopup, null);

        final AlertDialog deleteDialog = new AlertDialog.Builder(MainActivity.this).create();
        deleteDialog.setView(deleteDialogView);



        Button feedbacksubmit = (Button) deleteDialogView.findViewById(R.id.feedbacksubmit);
        TextView aboutinfo=(TextView) deleteDialogView.findViewById(R.id.aboutinfo);
        TextView loveapp=(TextView) deleteDialogView.findViewById(R.id.loveapp);
        loveapp.setText("Download");

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
        AdLoader  adLoader = new AdLoader.Builder(MainActivity.this, natice_advanceadd).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

            private ColorDrawable background;@Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                NativeTemplateStyle styles = new
                        NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                template[0] = deleteDialogView.findViewById(R.id.nativeTemplateView);
                template[0].setStyles(styles);
                template[0].setNativeAd(unifiedNativeAd);
                deleteDialogView.findViewById(R.id.nativeTemplateView).setVisibility(View.VISIBLE);
                // Showing a simple Toast message to user when Native an ad is Loaded and ready to show
                //   Toast.makeText(MainActivity.this, "Native Ad is loaded ,now you can show the native ad  ", Toast.LENGTH_LONG).show();
            }

        }).build();


        // load Native Ad with the Request
        adLoader.loadAds(new AdRequest.Builder().build(),5);
        loveapp.setVisibility(View.VISIBLE);
        feedbacksubmit.setVisibility(View.GONE);
        aboutinfo.setVisibility(View.VISIBLE);
        aboutinfo.setText(message);
        aboutinfo.setTextSize(17);
        aboutinfo.setTextColor(MainActivity.this.getResources().getColor(R.color.white));
        aboutinfo.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.green));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)aboutinfo.getLayoutParams();
        params.setMargins(5, 10, 5, 5);
        aboutinfo.setLayoutParams(params);
        deleteDialog.show();
    }



    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Download download = intent.getParcelableExtra("download");
            if(intent.getAction().equals(MESSAGE_PROGRESS)) {

                if (one) {
                    if (download.getProgress() > 20) {
                        adLoader.loadAds(new AdRequest.Builder().build(), 5);
                        mAdView.loadAd(adRequest);
                        one = false;
                    }
                }
                if (two) {
                    if (download.getProgress() > 40) {
                        adLoader.loadAds(new AdRequest.Builder().build(), 5);
                        mAdView.loadAd(adRequest);
                        two = false;
                    }
                }
                if (three) {
                    if (download.getProgress() > 60) {
                        adLoader.loadAds(new AdRequest.Builder().build(), 5);
                        mAdView.loadAd(adRequest);
                        three = false;
                    }
                }
                if (four) {
                    if (download.getProgress() > 80) {
                        adLoader.loadAds(new AdRequest.Builder().build(), 5);
                        mAdView.loadAd(adRequest);
                        four = false;
                    }
                }
                if (five) {
                    if (download.getProgress() > 90) {
                        adLoader.loadAds(new AdRequest.Builder().build(), 5);
                        mAdView.loadAd(adRequest);
                        five = false;
                    }
                }
            }

            progressBar.setProgress(download.getProgress());
            if(download.getProgress() == 100){

                progress.setText("File Download Complete");
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {

                            showmessage("Download complete save in My Reels.");
                        }
                    });
                }
            } else {

                progress.setText("Downloading "+" ( "+download.getCurrentFileSize()+" / "+download.getTotalFileSize()+" )");

            }
        }

    };
}
