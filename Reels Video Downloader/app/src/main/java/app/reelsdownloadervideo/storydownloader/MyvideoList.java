package app.reelsdownloadervideo.storydownloader;

import android.Manifest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaScannerConnection;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static app.reelsdownloadervideo.storydownloader.HomeActivity.MY_PREFS;
import static app.reelsdownloadervideo.storydownloader.ReelsAdapter.sMyAnySelectcs;
import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;


import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class MyvideoList extends AppCompatActivity {
    private static final int MY_REQUEST_CODES = 1;


    private ReelsAdapter mAdapters;
    List<ReelsModel> al_videos = new ArrayList<>();
     GridView my_reel_recycler;
    private static final int REQUEST_PERMISSIONS = 100;
    AppUpdateManager appUpdateManager;
    AdView myAdView;
    InterstitialAd mInterstitialAd;
    public static  int fulladdcount=0;
    public static MenuItem Myitems,Mygallerys,Mymenu_shares;
    String app_id,footer_add,fullscreen_add,native_add;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_reels_list);
        appUpdateManager = AppUpdateManagerFactory.create(MyvideoList.this);
        binding();
        loadAds();
         fn_checkpermission();
        getReels();

    }

    private void loadAds() {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        app_id = prefs.getString("admob_app_id", "");
        if (app_id.equalsIgnoreCase("")){
            app_id=getString(R.string.app_id);
            footer_add=getString(R.string.footer);
            native_add=getString(R.string.native_add);
            fullscreen_add=getString(R.string.full_screen);
        }else {
            app_id = prefs.getString("app_id", "");
            footer_add = prefs.getString("footer_add", "");
            native_add=prefs.getString("native_add", "");
            fullscreen_add=prefs.getString("fullscreen_add", "");

        }
        MobileAds.initialize(this, app_id);
        LinearLayout adContainer = findViewById(R.id.banner);
        myAdView = new AdView(MyvideoList.this);
        myAdView.setAdSize(AdSize.BANNER);
        myAdView.setAdUnitId(footer_add);
        adContainer.addView(myAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        myAdView.loadAd(adRequest);

        /// Initializing the Google Admob SDK  NATIVE
        MobileAds.initialize(MyvideoList.this, new OnInitializationCompleteListener() {@Override
        public void onInitializationComplete(InitializationStatus initializationStatus) {

            //Showing a simple Toast Message to the user when The Google AdMob Sdk Initialization is Completed
            //  Toast.makeText(HomeActivity.this, "AdMob Sdk Initialize " + initializationStatus.toString(), Toast.LENGTH_LONG).show();
        }
        });

        // Show full page add
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(fullscreen_add);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }

            @Override
            public void onAdClosed() {
                // Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();

                fulladdcount=1;
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                fulladdcount=1;
                // Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                fulladdcount=1;
                // Toast.makeText(getApplicationContext(), "Ad is opened!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showInterstitial() {
        if ( fulladdcount!=1) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }
    }
    @Override
    public void onDestroy() {
        if (myAdView != null) {
            myAdView.destroy();
        }
        super.onDestroy();
    }
    public static boolean checkInternetConnection(Context context) {

        final ConnectivityManager connMgrer = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgrer != null) {
            NetworkInfo oneactiveNetworkInfo = connMgrer.getActiveNetworkInfo();

            if (oneactiveNetworkInfo != null) { // connected to the internet
                if (oneactiveNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
                } else return oneactiveNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            }

        }
        return false;
    }

    private void binding(){
        //Start of dynamic title code---------------------
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {

            //Setting a dynamic title at runtime. Here, it displays the current time.
            actionBar.setTitle("My Video");
        }

        my_reel_recycler = (GridView) findViewById(R.id.my_reel_recycler);




        if (checkInternetConnection(this)) {




            checkUpdate();
        } else {

        }


    }
    private void checkUpdate() {




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
                                MyvideoList.this,
                                // Include a request code to later monitor this update request.
                                MY_REQUEST_CODES);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                } else {
                 }
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fn_checkpermission(){
        /*RUN TIME PERMISSIONS*/

        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(MyvideoList.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(MyvideoList.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(MyvideoList.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        }else {

            getReels();
        }
    }

    public void getReels() {

        al_videos = new ArrayList<>();
        File mypaths = new File(Environment.getExternalStorageDirectory(), "Reels-Video-Downloader");
//        File mypath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File[] filess = mypaths.listFiles();

        if (filess != null) {
            Arrays.sort( filess, new Comparator()
            {
                public int compare(Object o1, Object o2) {

                    if (((File)o1).lastModified() > ((File)o2).lastModified()) {
                        return -1;
                    } else if (((File)o1).lastModified() < ((File)o2).lastModified()) {
                        return +1;
                    } else {
                        return 0;
                    }
                }

            });
           // Arrays.sort(files, Comparator.comparingLong(File::lastModified));
            for (int i = 0; i < filess.length; i++) {
                ReelsModel obj_model = new ReelsModel();
                String file_name = filess[i].getName();
                String file_path = filess[i].getPath();

                if (file_name.contains("Reels") || file_name.contains("IGTV")) {
                     File size = new File(file_path);
                    long sizes = size.length();
                    double b = sizes;

                    double t = ((((sizes / 1024.0) / 1024.0) / 1024.0) / 1024.0);
                    double k = sizes / 1024.0;
                    double m = ((sizes / 1024.0) / 1024.0);
                    double g = (((sizes / 1024.0) / 1024.0) / 1024.0);
                    DecimalFormat dec = new DecimalFormat("0.00");

                    String hrSize;
                    if (t > 1) {
                        hrSize = dec.format(t).concat("TB");
                    } else if (g > 1) {
                        hrSize = dec.format(g).concat("GB");
                    } else if (m > 1) {
                        hrSize = dec.format(m).concat("MB");
                    } else if (k > 1) {
                        hrSize = dec.format(k).concat("KB");
                    } else {
                        hrSize = dec.format(b).concat("Bytes");
                    }
                    long duration = 0;
                    try {

                        final MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();

                        mediaMetadataRetriever.setDataSource(String.valueOf(file_path));
                        duration = (int) Long.parseLong(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
                        obj_model.setStr_path(file_path);

                        obj_model.setDuration(String.valueOf(duration / 100));
                        obj_model.setSelected(false);
                        obj_model.setStr_thumb(file_path);
                        obj_model.setSize(hrSize);
                        al_videos.add(obj_model);
                    } catch (Exception e) {

                    }
                }
                // you can store name to arraylist and use it later

            }



            if (al_videos.size()>0) {


                mAdapters = new ReelsAdapter(this, R.layout.reels_list_data, al_videos);
                my_reel_recycler.setAdapter(mAdapters);
             }else{
             }

        }else{
         }
    }





    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        getReels();
                    } else {
                        Toast.makeText(MyvideoList.this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODES) {
            if (resultCode != RESULT_OK) {
                 // If the update is cancelled or fails,
                // you can request to start the update again.
            }else{
             }
        }
    }
    @Override
    public void onPause() {
        if (myAdView != null) {
            myAdView.pause();
        }
        super.onPause();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (myAdView != null) {
            myAdView.resume();
        }
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        new OnSuccessListener<AppUpdateInfo>() {
                            @Override
                            public void onSuccess(AppUpdateInfo appUpdateInfo) {

                                if (appUpdateInfo.updateAvailability()
                                        == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                     try {
                                        appUpdateManager.startUpdateFlowForResult(
                                                appUpdateInfo,
                                                IMMEDIATE,
                                                MyvideoList.this,
                                                MY_REQUEST_CODES);
                                    } catch (IntentSender.SendIntentException e) {
                                     }
                                } else {
                                 }
                            }
                        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        Myitems = menu.findItem(R.id.menu_deletes);
        Mygallerys = menu.findItem(R.id.menu_gallerys);
        Mymenu_shares=menu.findItem(R.id.menu_shares);

        Mymenu_shares.setVisible(false);
        Myitems.setVisible(false);
        Mygallerys.setVisible(false);

        Mymenu_shares.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String path="";




                for (int i=0;i<al_videos.size();i++){
                    if (al_videos.get(i).getSelected()) {
                        path=al_videos.get(i).getStr_path();
                        break;
                    }
                }
                MediaScannerConnection.scanFile(MyvideoList.this, new String[] { path },

                        null, new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
                                Intent shareIntent = new Intent(
                                        Intent.ACTION_SEND);

                                shareIntent.setType("video/*");

                                shareIntent.putExtra(
                                        Intent.EXTRA_SUBJECT, "Share");

                                shareIntent.putExtra(
                                        Intent.EXTRA_TITLE, "Share");


                                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                                shareIntent
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                                startActivity(Intent.createChooser(shareIntent,"Share"));

                            }
                        });
                return false;
            }
        });



        Myitems.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(MyvideoList.this)
                        // set message, title, and icon
                        .setTitle("Delete")
                        .setMessage("Do you want to Delete")
                        .setIcon(R.drawable.dele)

                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                //your deleting code
                                for (int i = 0; i < al_videos.size(); i++) {
                                    if (al_videos.get(i).getSelected()) {

                                        sMyAnySelectcs = "";
                                        Myitems.setVisible(false);
                                        al_videos.get(i).setSelected(false);

                                        String path = al_videos.get(i).getStr_path();
                                        File file = new File(path);
                                        if (file.exists()) {
                                            file.delete();

                                            MediaScannerConnection.scanFile(MyvideoList.this,
                                                    new String[]{file.toString()},
                                                    new String[]{file.getName()}, null);

                                            dialog.dismiss();
                                            try {
                                                File fdelete = new File(path);
                                                if (fdelete.exists()) {
                                                    final String where = MediaStore.MediaColumns.DATA + "=?";
                                                    final String[] selectionArgs = new String[]{
                                                            fdelete.getAbsolutePath()
                                                    };
                                                    final ContentResolver contentResolver = MyvideoList.this.getContentResolver();

                                                    final Uri filesUri = MediaStore.Files.getContentUri("external");

                                                    contentResolver.delete(filesUri, where, selectionArgs);

                                                    if (fdelete.exists()) {

                                                        contentResolver.delete(filesUri, where, selectionArgs);
                                                    }
                                                    dialog.dismiss();
                                                    System.out.println("file Deleted :" + path);

                                                } else {
                                                    dialog.dismiss();
                                                   // Toast.makeText(MyVideoListActivity.this, "File Not Found.", Toast.LENGTH_LONG).show();
                                                    System.out.println("file not Found :" + path);
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                dialog.dismiss();
                                            }
                                        }
                                    }

                                }

                                Toast.makeText(MyvideoList.this,"Delete", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(MyvideoList.this, MyvideoList.class);
                                startActivity(i);
                                finish();



                            }

                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        })
                        .create();
                myQuittingDialogBox.show();




                return false;
            }
        });



        return super.onCreateOptionsMenu(menu);
    }


    public void onBackPressed() {
        super.onBackPressed();
        if (sMyAnySelectcs.equalsIgnoreCase("true")) {
            for (int i = 0; i < al_videos.size(); i++) {
                if (al_videos.get(i).getSelected()) {
                    sMyAnySelectcs = "";
                    Myitems.setVisible(false);
                    al_videos.get(i).setSelected(false);

                }
            }
        }
    }

}