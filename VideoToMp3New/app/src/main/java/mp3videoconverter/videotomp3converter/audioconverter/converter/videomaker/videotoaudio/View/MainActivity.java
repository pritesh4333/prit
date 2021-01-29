package mp3videoconverter.videotomp3converter.audioconverter.converter.videomaker.videotoaudio.View;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
import androidx.core.content.ContextCompat;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.VideoController;
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
import mp3videoconverter.videotomp3converter.audioconverter.converter.videomaker.videotoaudio.BuildConfig;
import mp3videoconverter.videotomp3converter.audioconverter.converter.videomaker.videotoaudio.Model.Model_images;
import mp3videoconverter.videotomp3converter.audioconverter.converter.videomaker.videotoaudio.R;
import mp3videoconverter.videotomp3converter.audioconverter.converter.videomaker.videotoaudio.Utils.Helper;
import mp3videoconverter.videotomp3converter.audioconverter.converter.videomaker.videotoaudio.Utils.InternetConnection;
import mp3videoconverter.videotomp3converter.audioconverter.converter.videomaker.videotoaudio.View.MyFiles.Mp3Files;
import mp3videoconverter.videotomp3converter.audioconverter.converter.videomaker.videotoaudio.ViewModel.Adapter_Photos;

import java.util.ArrayList;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.shape.CircleShape;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;


public class MainActivity extends AppCompatActivity {
    private static final int RC_APP_UPDATE = 0;
    private static final int MY_REQUEST_CODE = 1;
    private GridView gv_folder;
    private Button videlist,myfiles;
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
    ImageView imageview;
    NativeExpressAdView mAdViews;
    TextView more_apps;
    VideoController mVideoController;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gv_folder = (GridView) findViewById(R.id.gv_folder);
        videlist = (Button) findViewById(R.id.videlist);
        imageview=(ImageView)findViewById(R.id.image);
        myfiles=(Button)findViewById(R.id.myfiles);
        more_apps=(TextView)findViewById(R.id.more_apps);

        activity=this;
        //        //test creashlatecs
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
          mAdView = new AdView(MainActivity.this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(banner_home_footer);
        adContainer.addView(mAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        /// Initializing the Google Admob SDK  NATIVE
        MobileAds.initialize(MainActivity.this, new OnInitializationCompleteListener() {@Override
        public void onInitializationComplete(InitializationStatus initializationStatus) {

            //Showing a simple Toast Message to the user when The Google AdMob Sdk Initialization is Completed
            //  Toast.makeText(MainActivity.this, "AdMob Sdk Initialize " + initializationStatus.toString(), Toast.LENGTH_LONG).show();
        }
        });







        appUpdateManager = AppUpdateManagerFactory.create(MainActivity.this);


        // Onclik Listner
        videlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //new RuntimeException("Test Crash"); crash app line for new crash anylatics version
                if (chekpermistion) {

                    Intent ii = new Intent(MainActivity.this, VideoListActivity.class);
                   //Intent ii = new Intent(MainActivity.this, Mp3Files.class);
                    startActivity(ii);
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO},
                            REQUEST_PERMISSIONS);
                }

            }
        });
        myfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (chekpermistion) {


                    Intent ii = new Intent(MainActivity.this, Mp3Files.class);
                    startActivity(ii);
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO},
                            REQUEST_PERMISSIONS);
                }

            }
        });
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chekpermistion) {

                    Intent ii = new Intent(MainActivity.this, VideoListActivity.class);
                    startActivity(ii);
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO},
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
                LinearLayout reelsapps=(LinearLayout)dialogView.findViewById(R.id.reelsapps);
                LinearLayout aartiapp=(LinearLayout)dialogView.findViewById(R.id.aartiapp);
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
                reelsapps.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.reels.video.download.instagram.video.downloader.saver.story.video" )));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.reels.video.download.instagram.video.downloader.saver.story.video")));
                        }
                    }

                });
                aartiapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.pritesh.all.in.one.god.goddess.allinoneaarti" )));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.pritesh.all.in.one.god.goddess.allinoneaarti")));
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
//        try {
//
//            Integer i = null;
//                    int c=i/0;
//        }catch(Exception e){
//            Crashlytics.log("Line no 384"+e);
//        }
        ////crash check button click button to start crash in web fcm
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
                        Log.d(LOG_TAG, msg);
                       // Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
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

        if (Showcase.equalsIgnoreCase("")){
            new MaterialShowcaseView.Builder(this)
                    .setTarget(videlist)
                    .setShape(new CircleShape())
                    .setDismissText("GOT IT")
                    .setContentText("Select Video From Gallery To Extract Audio")
                    .setDelay(1000)
                    .setFadeDuration(500)
                    .setDismissOnTouch(true)
                    .setMaskColour(getResources().getColor(R.color.black))
                    .setContentTextColor(getResources().getColor(R.color.white))
                    .setDismissTextColor(getResources().getColor(R.color.primary))
                    // optional but starting animations immediately in onCreate can make them choppy
                    //.singleUse("Videocompress") // provide a unique ID used to ensure it is only shown once
                    .show();

//            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//            editor.putString("Showcase", "Videocompress");
//            editor.apply();
        }else {

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
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))
                    && (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.RECORD_AUDIO))) {

            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO},
                        REQUEST_PERMISSIONS);
                chekpermistion = false;

            }
        } else {
            chekpermistion = true;

        }
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

                deleteDialog.setView(deleteDialogView);




                Button feedbacksubmit = (Button) deleteDialogView.findViewById(R.id.feedbacksubmit);
                  TextView aboutinfo=(TextView) deleteDialogView.findViewById(R.id.aboutinfo);
                  TextView loveapp=(TextView) deleteDialogView.findViewById(R.id.loveapp);
                loveapp.setText("About");
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
                AdLoader adLoader = new AdLoader.Builder(MainActivity.this, natice_advanceadd).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

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
                // Showing a simple Toast message to user when Native an ad is Loading
                //  Toast.makeText(MainActivity.this, "Native Ad is loading ", Toast.LENGTH_LONG).show();
                // Showing a simple Toast message to user when Native an ad is Loading

                feedbacksubmit.setVisibility(View.GONE);
                aboutinfo.setVisibility(View.VISIBLE);
                loveapp.setVisibility(View.VISIBLE);
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
                    String shareMessage = "Let me recommend you this application\n\n You can convert mp4 video file to mp3 file with dynamic resolution and faster compression.\n\n";
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
                AdLoader adLoader = new AdLoader.Builder(MainActivity.this, natice_advanceadd).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

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
                // Showing a simple Toast message to user when Native an ad is Loading
                //  Toast.makeText(MainActivity.this, "Native Ad is loading ", Toast.LENGTH_LONG).show();
                // Showing a simple Toast message to user when Native an ad is Loading

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





