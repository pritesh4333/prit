package com.videocompres.videocompress.videoresize;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;
import static com.videocompres.videocompress.videoresize.List_video_adapter.MyAnySelectc;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;


import androidx.annotation.NonNull;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;


import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Comparator;
import java.util.List;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.android.gms.ads.AdView;

import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

public class AcitivityMyvideolist extends AppCompatActivity {
    public static MenuItem Myitem,Mygallery,Mymenu_share;
    public static  int count =0;
    //RequestQueue queue;
    private ActionMode mActionMode;
    private List_video_adapter mAdapter;
    GridView recyclerView;
    private AdView mAdView;
    InterstitialAd mInterstitialAd;
    private static Activity videoactivity;
    List<Video_model> al_video = new ArrayList<>();
     static String result;
    private static final int MY_REQUEST_CODE = 1;
    AppUpdateManager updateManager;
    private static final int REQUEST_PERMISSIONS = 100;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myvideolist);
        updateManager = AppUpdateManagerFactory.create(AcitivityMyvideolist.this);
        initmethod();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initmethod(){
         ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setTitle("My Video");
        }
        recyclerView = (GridView) findViewById(R.id.my_List_recycler_view1);
        checkpermission();

        LinearLayout adContainer = findViewById(R.id.videlist_adview);
        AdView mAdView = new AdView(AcitivityMyvideolist.this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(getString(R.string.banner_home_footer));
        adContainer.addView(mAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        // Show full page add
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }

            @Override
            public void onAdClosed() {
                count =1;
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
            }

            @Override
            public void onAdLeftApplication() {
                count =1;
            }

            @Override
            public void onAdOpened() {
                count =1;
            }
        });
        if (Internetconn.checkConnection(this)) {
            checkforUpdate();
        } else {
            Helpers.LogPrint("No Internet","No internet");
        }
     }
    private void checkforUpdate() {
         Task<AppUpdateInfo> appUpdateInfoTask = updateManager.getAppUpdateInfo();
           appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                     && appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)) {
                 try {
                    updateManager.startUpdateFlowForResult(
                             appUpdateInfo,
                             IMMEDIATE,
                             this,
                             MY_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }else{
                Helpers.LogPrint("update Method","No update");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void checkpermission(){
        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(AcitivityMyvideolist.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(AcitivityMyvideolist.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(AcitivityMyvideolist.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        }else {
            Helpers.LogPrint("Else","Else");
            getallvideos();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getallvideos() {
        File mypath = new File(Environment.getExternalStorageDirectory(), "Fast Video Compressor");
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
            for (int i = 0; i < files.length; i++) {
                Video_model obj_model = new Video_model();
                String file_name = files[i].getName();
                String file_path = files[i].getPath();
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
                    obj_model.setDuration(String.valueOf(duration/100));
                    obj_model.setSelected(false);
                    al_video.add(obj_model);
                }catch(Exception e){
                }
            }
            if (al_video.size()>0) {
                mAdapter = new List_video_adapter(this, R.layout.video_adapts, al_video);
                recyclerView.setAdapter(mAdapter);
            }else{
                Toast.makeText(this,"No video found",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(AcitivityMyvideolist.this,"No video found",Toast.LENGTH_LONG).show();
        }
    }


    private void showInterstitial() {
        if ( count !=1) {
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
                        getallvideos();
                    } else {
                        Toast.makeText(AcitivityMyvideolist.this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
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
                Helpers.LogPrint("Update flow failed! Result code: ", "" + resultCode);
            }else{
                Helpers.LogPrint("Update Done: ", "" + resultCode);
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
        updateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {
                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.
                                try {
                                    updateManager.startUpdateFlowForResult(
                                            appUpdateInfo,
                                            IMMEDIATE,
                                            this,
                                            MY_REQUEST_CODE);
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                Helpers.LogPrint("No update On Resume","No update");
                            }
                        });

    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
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
                MediaScannerConnection.scanFile(AcitivityMyvideolist.this, new String[] { path },
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
        Myitem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(AcitivityMyvideolist.this)
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
                                                final ContentResolver contentResolver = AcitivityMyvideolist.this.getContentResolver();
                                                final Uri filesUri = MediaStore.Files.getContentUri("external");

                                                contentResolver.delete(filesUri, where, selectionArgs);

                                                if (fdelete.exists()) {

                                                    contentResolver.delete(filesUri, where, selectionArgs);
                                                }
                                                dialog.dismiss();
                                                System.out.println("file Deleted :" + path);

                                            } else {
                                                dialog.dismiss();
                                                Toast.makeText(AcitivityMyvideolist.this,"File Not Found.", Toast.LENGTH_LONG).show();
                                                System.out.println("file not Found :" + path);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            dialog.dismiss();
                                        }
                                    }

                                }

                                Toast.makeText(AcitivityMyvideolist.this,"Delete", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(AcitivityMyvideolist.this, AcitivityMyvideolist.class);
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