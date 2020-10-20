package com.prit.videocompressorpro.View;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;



import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.prit.videocompressorpro.BuildConfig;
import com.prit.videocompressorpro.Model.Model_images;
import com.prit.videocompressorpro.R;
import com.prit.videocompressorpro.Utils.Helper;
import com.prit.videocompressorpro.Utils.InternetConnection;
import com.prit.videocompressorpro.ViewModel.Adapter_Photos;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;


import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.shape.CircleShape;
import uk.co.deanwild.materialshowcaseview.shape.NoShape;
import uk.co.deanwild.materialshowcaseview.shape.RectangleShape;

import static com.crashlytics.android.Crashlytics.log;
import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;


public class MainActivity extends AppCompatActivity {
    private static final int RC_APP_UPDATE = 0;
    private static final int MY_REQUEST_CODE = 1;
    private GridView gv_folder;
    private Button videlist;
    TextView more_apps;
    private AdView mAdView;
    private static Activity activity;
    public static final String MY_PREFS_NAME = "VideoCompressPrefsFile";

    public static ArrayList<Model_images> al_images = new ArrayList<>();
    boolean boolean_folder;
    private static final int REQUEST_PERMISSIONS = 100;
    private Boolean chekpermistion = false;
    static String result;
    private Adapter_Photos obj_adapter;
    // RequestQueue queue;
    static String URL = "https://priteshparmar.000webhostapp.com/appversion/version.txt";
    AppUpdateManager appUpdateManager;
    private static String LOG_TAG = "EXAMPLE";
    String admob_app_id,banner_home_footer;

    NativeExpressAdView mAdViews;
    VideoController mVideoController;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gv_folder = (GridView) findViewById(R.id.gv_folder);
        videlist = (Button) findViewById(R.id.videlist);
        more_apps=(TextView)findViewById(R.id.more_apps);

        activity=this;



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
        AdView mAdView = new AdView(MainActivity.this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(banner_home_footer);
        adContainer.addView(mAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);










        appUpdateManager = AppUpdateManagerFactory.create(MainActivity.this);

        more_apps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup viewGroup = findViewById(android.R.id.content);
                //then we will inflate the custom alert dialog xml that we created
                View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.alert_more_apps, viewGroup, false);
                //Now we need an AlertDialog.Builder object
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                //setting the view of the builder to our custom view that we already inflated
                builder.setView(dialogView);

                final AlertDialog alertDialog = builder.create();

                TextView ok=(TextView)dialogView.findViewById(R.id.ok);
                LinearLayout videocommproapp=(LinearLayout)dialogView.findViewById(R.id.videocompressorproapps);

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
                AdLoader adLoader = new AdLoader.Builder(MainActivity.this, natice_advanceadd)
                        .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

                            @Override
                            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                                // Show the ad.
                                // Assumes you have a placeholder FrameLayout in your View layout
                                // (with id fl_adplaceholder) where the ad is to be placed.
                                //Log.e("add loaded",""+unifiedNativeAd);
                                FrameLayout frameLayout =dialogView.
                                        findViewById(R.id.fl_adplaceholder);
                                // Assumes that your ad layout is in a file call ad_unified.xml
                                // in the res/layout folder
                                UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater()
                                        .inflate(R.layout.unified_ads, null);
                                // This method sets the text, images and the native ad, etc into the ad
                                // view.
                                populateUnifiedNativeAdView(unifiedNativeAd, adView);
                                frameLayout.removeAllViews();
                                frameLayout.addView(adView);
                            }
                        })
                        .withAdListener(new AdListener() {
                            @Override
                            public void onAdFailedToLoad(int errorCode) {
                                //   Log.e("add loaded",""+errorCode);
                                // Handle the failure by logging, altering the UI, and so on.
                              //   Toast.makeText(getApplicationContext(), "Ad failed to load! error code Native: " + errorCode, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .withNativeAdOptions(new NativeAdOptions.Builder()
                                // Methods in the NativeAdOptions.Builder class can be
                                // used here to specify individual options settings.
                                .build())
                        .build();
                adLoader.loadAds(new AdRequest.Builder().build(),5);

                videocommproapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.prit.videotomp3" )));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.prit.videotomp3")));
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

        // Onclik Listner
        videlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (chekpermistion) {

                    Intent ii = new Intent(MainActivity.this, VideoListActivity.class);
                   // Intent ii = new Intent(MainActivity.this, NativeAdd.class);
                    startActivity(ii);
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSIONS);
                }

            }
        });
        gv_folder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), PhotoListActivity.class);
                intent.putExtra("value", i);
                startActivity(intent);
            }
        });
        showMessagealert();
        // Checkpermition
        chekpermisstion();
        if (InternetConnection.checkConnection(this)) {
            // Its Available...



            checkforUpdate();
        } else {
            // Not Available...
            Helper.LogPrint("No Internet","No internet");
        }
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("Showcase", "Videocompress");
        editor.apply();
        showShowcase();


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
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        // [END subscribe_topics]

//testing in main activity
//        Bitmap mIcon = BitmapFactory.decodeResource(this.getResources(),R.drawable.bg);
//        String channelId = getString(R.string.default_notification_channel_id);
//        String channername=getString(R.string.default_notification_channel_name);
//        NotificationCompat.BigPictureStyle bpStyle = new NotificationCompat.BigPictureStyle();
//        bpStyle.bigPicture(mIcon).build();
//        // Set the intent to fire when the user taps on notification.
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//        NotificationCompat.Builder mBuilder ;
//        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        long[] v = {500,1000};
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//            mBuilder =  new NotificationCompat.Builder(this,channelId)
//                    .setSmallIcon(R.drawable.notification_icon2)
//                    .setContentTitle("title")
//                    .setContentText("body")
//                    .setSound(uri)
//                    .setVibrate(v)
//                    .setLargeIcon(mIcon)
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                    .addAction(0, "Update", pendingIntent)
//                    .setColor(ContextCompat.getColor(this, R.color.green))
//                    .setStyle(bpStyle);
//
//        }else{
//
//            mBuilder  =  new NotificationCompat.Builder(this,channelId)
//                    .setSmallIcon(R.drawable.notification_icon2)
//                    .setContentTitle("title")
//                    .setSubText("Body")
//                    .setSound(uri)
//                    .setVibrate(v)
//                    .setLargeIcon(mIcon)
//                    .addAction(0, "Update", pendingIntent)
//                    .setColor(ContextCompat.getColor(this, R.color.green))
//                    .setStyle(bpStyle);
//        }
//        mBuilder.setContentIntent(pendingIntent);
//        // Sets an ID for the notification
//        int mNotificationId = 001;
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//        // It will display the notification in notification bar
//        notificationManager.notify(mNotificationId, mBuilder.build());


    }



    public  void showShowcase(){
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String Showcase = prefs.getString("Showcase", "");
        try{
        if (Showcase.equalsIgnoreCase("")){
            new MaterialShowcaseView.Builder(this)
                    .setTarget(videlist)
                    .setShape(new CircleShape())
                    .setDismissText("GOT IT")
                    .setContentText("Select Video From Gallery To Compress Your Video")
                    .setDelay(1000)
                    .setFadeDuration(500)
                    .setDismissOnTouch(true)
                    .setMaskColour(getResources().getColor(R.color.primary_dark))
                    .setContentTextColor(getResources().getColor(R.color.accent))
                    // optional but starting animations immediately in onCreate can make them choppy
                    //.singleUse("Videocompress") // provide a unique ID used to ensure it is only shown once
                    .show();

//            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//            editor.putString("Showcase", "Videocompress");
//            editor.apply();
        }else {

        }
        }catch(Exception e){

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

   public void  showMessagealert(){
       ViewGroup viewGroup = findViewById(android.R.id.content);
       //then we will inflate the custom alert dialog xml that we created
       View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.alert_more_apps, viewGroup, false);
       //Now we need an AlertDialog.Builder object
       AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
       //setting the view of the builder to our custom view that we already inflated
       builder.setView(dialogView);

       final AlertDialog alertDialog = builder.create();
       alertDialog.setCancelable(false);

       TextView ok=(TextView)dialogView.findViewById(R.id.ok);
       TextView more_apps_header=(TextView)dialogView.findViewById(R.id.more_apps_header);
       TextView message=(TextView)dialogView.findViewById(R.id.message);
       TextView appname=(TextView)dialogView.findViewById(R.id.appname);
       ImageView app_logo=(ImageView)dialogView.findViewById(R.id.app_logo);
       LinearLayout videocommproapp=(LinearLayout)dialogView.findViewById(R.id.videocompressorproapps);

       more_apps_header.setText("ACTION REQUIRED");
       app_logo.setBackground(getResources().getDrawable(R.drawable.dp));

       videocommproapp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               try {
                   startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.pritesh.videocompressorpro_fastvideocompressor" )));
               } catch (android.content.ActivityNotFoundException anfe) {
                   startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.pritesh.videocompressorpro_fastvideocompressor")));
               }
           }

       });
       ok.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               try {
                   startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.pritesh.videocompressorpro_fastvideocompressor" )));
               } catch (android.content.ActivityNotFoundException anfe) {
                   startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.pritesh.videocompressorpro_fastvideocompressor")));
               }
           }
       });





       message.setText("Kindly download video compressor pro Lite version from below link to enjoy same features because this app is remove from play store.");
       appname.setText("Video Compressor Pro - INDIAN App");
       message.setVisibility(View.VISIBLE);
       alertDialog.show();

    }

    // Fetching image from gallery
    public ArrayList<Model_images> fn_imagespath() {
        al_images.clear();

        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;

        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            Helper.LogPrint("Column", absolutePathOfImage);
            Helper.LogPrint("Folder", cursor.getString(column_index_folder_name));

            for (int i = 0; i < al_images.size(); i++) {
                if (al_images.get(i).getStr_folder().equals(cursor.getString(column_index_folder_name))) {
                    boolean_folder = true;
                    int_position = i;
                    break;
                } else {
                    boolean_folder = false;
                }
            }


            if (boolean_folder) {

                ArrayList<String> al_path = new ArrayList<>();
                al_path.addAll(al_images.get(int_position).getAl_imagepath());
                al_path.add(absolutePathOfImage);
                al_images.get(int_position).setAl_imagepath(al_path);

            } else {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.add(absolutePathOfImage);
                Model_images obj_model = new Model_images();
                obj_model.setStr_folder(cursor.getString(column_index_folder_name));
                obj_model.setAl_imagepath(al_path);

                al_images.add(obj_model);


            }


        }


        for (int i = 0; i < al_images.size(); i++) {
            Helper.LogPrint("FOLDER", al_images.get(i).getStr_folder());
            for (int j = 0; j < al_images.get(i).getAl_imagepath().size(); j++) {
                Helper.LogPrint("FILE", al_images.get(i).getAl_imagepath().get(j));
            }
        }
        obj_adapter = new Adapter_Photos(getApplicationContext(), al_images);
        gv_folder.setAdapter(obj_adapter);
        return al_images;
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
        AdLoader adLoader = new AdLoader.Builder(this, natice_advanceadd)
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        // Show the ad.
                        // Assumes you have a placeholder FrameLayout in your View layout
                        // (with id fl_adplaceholder) where the ad is to be placed.
                        //Log.e("add loaded",""+unifiedNativeAd);
                        FrameLayout frameLayout =dialogView.
                                findViewById(R.id.fl_adplaceholder);
                        // Assumes that your ad layout is in a file call ad_unified.xml
                        // in the res/layout folder
                        UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater()
                                .inflate(R.layout.unified_ads, null);
                        // This method sets the text, images and the native ad, etc into the ad
                        // view.
                        populateUnifiedNativeAdView(unifiedNativeAd, adView);
                        frameLayout.removeAllViews();
                        frameLayout.addView(adView);
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                     //   Log.e("add loaded",""+errorCode);
                        // Handle the failure by logging, altering the UI, and so on.
                       // Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();
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
                activity.finish();
            }
        });



        alertDialog.show();

    }
    private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {
        // Set the media view. Media content will be automatically populated in the media view once
        // adView.setNativeAd() is called.
        MediaView mediaView = adView.findViewById(R.id.ad_media);
        adView.setMediaView(mediaView);

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline is guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad. The SDK will populate the adView's MediaView
        // with the media content from this native ad.
        adView.setNativeAd(nativeAd);


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
        MenuItem showcase=menu.findItem(R.id.showcase);
        MenuItem about=menu.findItem(R.id.about);
        showcase.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString("Showcase", "");
            editor.apply();
            showShowcase();
                return false;
            }
        });
        about.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                final View deleteDialogView = factory.inflate(R.layout.feedbackpopup, null);

                final AlertDialog deleteDialog = new AlertDialog.Builder(MainActivity.this).create();
                deleteDialog.setTitle("About");
                deleteDialog.setView(deleteDialogView);



                Button feedbacksubmit = (Button) deleteDialogView.findViewById(R.id.feedbacksubmit);
                  TextView aboutinfo=(TextView) deleteDialogView.findViewById(R.id.aboutinfo);
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
                AdLoader adLoader = new AdLoader.Builder(MainActivity.this, natice_advanceadd)
                        .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

                            @Override
                            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                                // Show the ad.
                                // Assumes you have a placeholder FrameLayout in your View layout
                                // (with id fl_adplaceholder) where the ad is to be placed.
                              //  Log.e("add loaded",""+unifiedNativeAd);
                                FrameLayout frameLayout =deleteDialogView.
                                        findViewById(R.id.fl_adplaceholder);
                                // Assumes that your ad layout is in a file call ad_unified.xml
                                // in the res/layout folder
                                UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater()
                                        .inflate(R.layout.unified_ads, null);
                                // This method sets the text, images and the native ad, etc into the ad
                                // view.
                                populateUnifiedNativeAdView(unifiedNativeAd, adView);
                                frameLayout.removeAllViews();
                                frameLayout.addView(adView);
                            }
                        })
                        .withAdListener(new AdListener() {
                            @Override
                            public void onAdFailedToLoad(int errorCode) {
                               // Log.e("add loaded",""+errorCode);
                                // Handle the failure by logging, altering the UI, and so on.
                            //    Toast.makeText(getApplicationContext(), "Ad failed to load! error code Native: " + errorCode, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .withNativeAdOptions(new NativeAdOptions.Builder()
                                // Methods in the NativeAdOptions.Builder class can be
                                // used here to specify individual options settings.
                                .build())
                        .build();
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
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Video Compressor Pro");
                    String shareMessage = "\nLet me recommend you this application\n\n";
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
                deleteDialog.setTitle("Feedback");
                deleteDialog.setView(deleteDialogView);



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
                AdLoader adLoader = new AdLoader.Builder(MainActivity.this, natice_advanceadd)

                        .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

                            @Override
                            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                                // Show the ad.
                                // Assumes you have a placeholder FrameLayout in your View layout
                                // (with id fl_adplaceholder) where the ad is to be placed.
                             //   Log.e("add loaded",""+unifiedNativeAd);
                                FrameLayout frameLayout =deleteDialogView.
                                        findViewById(R.id.fl_adplaceholder);
                                // Assumes that your ad layout is in a file call ad_unified.xml
                                // in the res/layout folder
                                UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater()
                                        .inflate(R.layout.unified_ads, null);
                                // This method sets the text, images and the native ad, etc into the ad
                                // view.
                                populateUnifiedNativeAdView(unifiedNativeAd, adView);
                                frameLayout.removeAllViews();
                                frameLayout.addView(adView);
                            }
                        })
                        .withAdListener(new AdListener() {
                            @Override
                            public void onAdFailedToLoad(int errorCode) {
                          //      Log.e("add loaded",""+errorCode);
                                // Handle the failure by logging, altering the UI, and so on.
                           //     Toast.makeText(getApplicationContext(), "Ad failed to load! error code Native: " + errorCode, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .withNativeAdOptions(new NativeAdOptions.Builder()
                                // Methods in the NativeAdOptions.Builder class can be
                                // used here to specify individual options settings.
                                .build())
                        .build();
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
//                                Toast.makeText(MainActivity.this,"Please provide feedback hear or in play store",Toast.LENGTH_LONG).show();
//                            }else{
//                                String androidSDK = "null", androidVersion = "null", androidBrand = "null",
//                                        androidManufacturer = "null", androidModel = "null", androidDeviceId = "null";
//                                String versionCode = "null", versionName = "null";
//                                PackageInfo pInfo = null;
//                                String deviceDetail = "null";
//                                pInfo = MainActivity.this.getPackageManager().getPackageInfo(MainActivity.this.getPackageName(), 0);
//                                versionCode = String.valueOf(pInfo.versionCode).trim();
//                                versionName = String.valueOf(pInfo.versionName).trim();
//                                androidSDK = String.valueOf(android.os.Build.VERSION.SDK_INT);
//                                androidVersion = android.os.Build.VERSION.RELEASE;
//                                androidBrand = android.os.Build.BRAND;
//                                androidManufacturer = android.os.Build.MANUFACTURER;
//                                androidModel = android.os.Build.MODEL;
//                                androidDeviceId = Settings.Secure.getString(MainActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
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
//    private void sendMessageToserver(JSONObject json) {
//
//        try {
//            final ProgressDialog pDialog = new ProgressDialog(this);
//            pDialog.setMessage("Sending Please Wait...");
//            pDialog.show();
//            Log.e("JSON", "" + json);
//            String url = "https://priteshparmar.000webhostapp.com/appversion/feedback.php";
//            Log.e("url", "" + url);
//
//
//            JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST,
//                    url, json, new Response.Listener<JSONObject>() {
//
//                @Override
//                public void onResponse(JSONObject response) {
//                    pDialog.dismiss();
//                    Log.e("Respose", response.toString());
//                    Toast.makeText(MainActivity.this,"Thank you for your feedback",Toast.LENGTH_LONG).show();
//
//                }
//            }, new Response.ErrorListener() {
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    pDialog.dismiss();
//                    VolleyLog.e("TAG", "Error: " + error.getMessage());
//
//                }
//
//            }) {
//
//
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<String, String>();
//                    headers.put("Content-Type", "application/json");
//                    headers.put("apiKey", "xxxxxxxxxxxxxxx");
//                    return headers;
//                }
//            };
//
//
//// Adding request to request queue
//            queue.add(strReq);
//        } catch (Exception e) {
//
//        }
//    }
}





