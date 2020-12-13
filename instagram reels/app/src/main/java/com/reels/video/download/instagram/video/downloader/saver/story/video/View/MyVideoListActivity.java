package com.reels.video.download.instagram.video.downloader.saver.story.video.View;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.reels.video.download.instagram.video.downloader.saver.story.video.Model.Model_Video;
import com.reels.video.download.instagram.video.downloader.saver.story.video.R;
import com.reels.video.download.instagram.video.downloader.saver.story.video.Utils.Helper;
import com.reels.video.download.instagram.video.downloader.saver.story.video.Utils.InternetConnection;
import com.reels.video.download.instagram.video.downloader.saver.story.video.ViewModel.Adapter_List_Video;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;
import static com.reels.video.download.instagram.video.downloader.saver.story.video.View.MainActivity.MY_PREFS_NAME;
import static com.reels.video.download.instagram.video.downloader.saver.story.video.ViewModel.Adapter_List_Video.MyAnySelectc;

public class MyVideoListActivity extends AppCompatActivity {
    GridView recyclerView;
    AdView mAdView;
    InterstitialAd mInterstitialAd;
    private static Activity videoactivity;
    static String result;
    private static final int MY_REQUEST_CODE = 1;
    AppUpdateManager appUpdateManager;
    private static final int REQUEST_PERMISSIONS = 100;
    public static MenuItem Myitem,Mygallery,Mymenu_share;
    public static  int fulladdcount=0;
//    RequestQueue queue;
    private ActionMode mActionMode;
    private Adapter_List_Video mAdapter;
    List<Model_Video> al_video = new ArrayList<>();
    String admob_app_id,banner_home_footer,interstitial_full_screen;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_video_list);
        appUpdateManager = AppUpdateManagerFactory.create(MyVideoListActivity.this);
        init();
        fn_video();
    }

    private void init(){
        //Start of dynamic title code---------------------
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {

            //Setting a dynamic title at runtime. Here, it displays the current time.
            actionBar.setTitle("My Reels");
        }

        recyclerView = (GridView) findViewById(R.id.my_List_recycler_view1);



     //   fn_checkpermission();

        // show add on banner

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
        Helper.LogPrint("admob_app_id",admob_app_id);
        Helper.LogPrint("interstitial_full_screen",interstitial_full_screen);

        LinearLayout adContainer = findViewById(R.id.videlist_adviews);
        mAdView = new AdView(MyVideoListActivity.this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(banner_home_footer);
        adContainer.addView(mAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        // Show full page add
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(interstitial_full_screen);
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
                //   Toast.makeText(getApplicationContext(), "Ad failed to load! error code:List " + errorCode, Toast.LENGTH_SHORT).show();
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
        if (InternetConnection.checkConnection(this)) {
            // Its Available...



            checkforUpdate();
        } else {
            // Not Available...
            Helper.LogPrint("No Internet","No internet");
        }
        // showShowcase();

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
                                MyVideoListActivity.this,
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fn_checkpermission(){
        /*RUN TIME PERMISSIONS*/

        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(MyVideoListActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(MyVideoListActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(MyVideoListActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        }else {
            Helper.LogPrint("Else","Else");
            fn_video();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void fn_video() {


        File mypath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        File[] files = mypath.listFiles();

        if (files != null) {
            Arrays.sort( files, new Comparator()
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
            for (int i = 0; i < files.length; i++) {
                Model_Video obj_model = new Model_Video();
                String file_name = files[i].getName();
                String file_path = files[i].getPath();

                if (file_name.contains("Reels") || file_name.contains("IGTV")) {
                    Helper.LogPrint("filesname", file_name);
                    File size = new File(file_path);
                    long sizes = size.length();
                    double b = sizes;
                    double k = sizes / 1024.0;
                    double m = ((sizes / 1024.0) / 1024.0);
                    double g = (((sizes / 1024.0) / 1024.0) / 1024.0);
                    double t = ((((sizes / 1024.0) / 1024.0) / 1024.0) / 1024.0);

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
                        obj_model.setStr_thumb(file_path);
                        obj_model.setSize(hrSize);
                        obj_model.setDuration(String.valueOf(duration / 100));
                        obj_model.setSelected(false);
                        al_video.add(obj_model);
                    } catch (Exception e) {

                    }
                }
                // you can store name to arraylist and use it later

            }



            if (al_video.size()>0) {


                mAdapter = new Adapter_List_Video(this, R.layout.adapter_videos, al_video);
                recyclerView.setAdapter(mAdapter);
                //mAdapter.notifyDataSetChanged();
            }else{
                Toast.makeText(this,"No video found", Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(MyVideoListActivity.this,"No video found", Toast.LENGTH_LONG).show();
        }
    }


    private void showInterstitial() {
        if ( fulladdcount!=1) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
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
                        fn_video();
                    } else {
                        Toast.makeText(MyVideoListActivity.this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
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
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
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
                                                MyVideoListActivity.this,
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);

        Myitem = menu.findItem(R.id.menu_delete);
        Mygallery = menu.findItem(R.id.menu_gallery);
        Mymenu_share=menu.findItem(R.id.menu_share);

        Mymenu_share.setVisible(false);
        Myitem.setVisible(false);
        Mygallery.setVisible(false);

        Mymenu_share.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String path="";




                for (int i=0;i<al_video.size();i++){
                    if (al_video.get(i).getSelected()) {
                        path=al_video.get(i).getStr_path();
                        break;
                    }
                }
                MediaScannerConnection.scanFile(MyVideoListActivity.this, new String[] { path },

                        null, new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
                                Intent shareIntent = new Intent(
                                        android.content.Intent.ACTION_SEND);
                                shareIntent.setType("video/*");
                                shareIntent.putExtra(
                                        android.content.Intent.EXTRA_SUBJECT, "Share");
                                shareIntent.putExtra(
                                        android.content.Intent.EXTRA_TITLE, "Share");
                                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                                shareIntent
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                                startActivity(Intent.createChooser(shareIntent,"Share"));

                            }
                        });
                return false;
            }
        });



        Myitem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(MyVideoListActivity.this)
                        // set message, title, and icon
                        .setTitle("Delete")
                        .setMessage("Do you want to Delete")
                        .setIcon(R.drawable.ic_delete)

                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                //your deleting code
                                for (int i = 0; i < al_video.size(); i++) {
                                    if (al_video.get(i).getSelected()) {

                                        MyAnySelectc = "";
                                        Myitem.setVisible(false);
                                        al_video.get(i).setSelected(false);

                                        String path = al_video.get(i).getStr_path();

                                        try {
                                            File fdelete = new File(path);
                                            if (fdelete.exists()) {
                                                final String where = MediaStore.MediaColumns.DATA + "=?";
                                                final String[] selectionArgs = new String[] {
                                                        fdelete.getAbsolutePath()
                                                };
                                                final ContentResolver contentResolver = MyVideoListActivity.this.getContentResolver();
                                                final Uri filesUri = MediaStore.Files.getContentUri("external");

                                                contentResolver.delete(filesUri, where, selectionArgs);

                                                if (fdelete.exists()) {

                                                    contentResolver.delete(filesUri, where, selectionArgs);
                                                }
                                                dialog.dismiss();
                                                System.out.println("file Deleted :" + path);

                                            } else {
                                                dialog.dismiss();
                                                Toast.makeText(MyVideoListActivity.this,"File Not Found.", Toast.LENGTH_LONG).show();
                                                System.out.println("file not Found :" + path);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            dialog.dismiss();
                                        }
                                    }

                                }

                                Toast.makeText(MyVideoListActivity.this,"Delete", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(MyVideoListActivity.this, MyVideoListActivity.class);
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
        if (MyAnySelectc.equalsIgnoreCase("true")) {
            for (int i = 0; i < al_video.size(); i++) {
                if (al_video.get(i).getSelected()) {
                    MyAnySelectc = "";
                    Myitem.setVisible(false);
                    al_video.get(i).setSelected(false);

                }
            }
        }
    }

}