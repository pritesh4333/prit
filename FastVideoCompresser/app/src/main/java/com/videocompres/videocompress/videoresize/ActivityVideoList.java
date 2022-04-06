package com.videocompres.videocompress.videoresize;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;

import static com.videocompres.videocompress.videoresize.HomeActivity.MY_PREFS_NAME;
import static com.videocompres.videocompress.videoresize.Video_adapter.isAnySelectec;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.shape.CircleShape;




public class ActivityVideoList extends AppCompatActivity {

    Video_adapter obj_adapter;
    private static final int PICK_VIDEO_REQUEST = 2;
    List<Video_model> al_video = new ArrayList<>();
    GridView recyclerView;
    private AdView mAdView;
    InterstitialAd mInterstitialAd;
    private static Activity videoactivity;
     private static final int REQUEST_PERMISSIONS = 100;
    public static MenuItem item,gallery,menu_share;
    public static  int fulladdcount=0;
    private static final int MY_REQUEST_CODE = 1;
    AppUpdateManager appUpdateManager;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videodisplay);
        videoactivity=this;
        appUpdateManager = AppUpdateManagerFactory.create(ActivityVideoList.this);
        init();


    }

    private void init(){
        recyclerView = (GridView) findViewById(R.id.recycler_view1);
        fn_checkpermission();
         LinearLayout adContainer = findViewById(R.id.videlist_adview);
        AdView mAdView = new AdView(ActivityVideoList.this);
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
                 fulladdcount=1;
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
             }

            @Override
            public void onAdLeftApplication() {
                fulladdcount=1;
             }

            @Override
            public void onAdOpened() {
                fulladdcount=1;
             }
        });
        if (Internetconn.checkConnection(this)) {
           checkforUpdate();
        } else {
             Helpers.LogPrint("No Internet","No internet");
        }
     }
    public  void showShowcase(){
    SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
    String Showcase = prefs.getString("Showcase", "");
        if (Showcase.equalsIgnoreCase("")){
            View menuitem= findViewById(R.id.menu_gallery);
            new MaterialShowcaseView.Builder(this)
                .setTarget( menuitem)
                .setShape(new CircleShape())
                .setDismissText("GOT IT")
                .setContentText("Select Video You Want To Compress From The List")
                .setDelay(1000)
                .setFadeDuration(500)
                .setMaskColour(getResources().getColor(R.color.primary_dark))
                .setContentTextColor(getResources().getColor(R.color.yellow))
                .show();

    }else {
    }
}
    private void checkforUpdate() {
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)) {
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
                Helpers.LogPrint("update Method","No update");
            }
        });
    }

    private void fn_checkpermission(){
        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(ActivityVideoList.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(ActivityVideoList.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(ActivityVideoList.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        }else {
            Helpers.LogPrint("Else","Else");
            getvideos();
        }
    }

    private void showInterstitial() {
        if ( fulladdcount!=1) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }
    }
    public void getvideos() {

        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name,column_id,thum,size;

        String absolutePathOfImage = null;
        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME, MediaStore.Video.Media._ID, MediaStore.Video.Thumbnails.DATA};

        final String orderBy = MediaStore.Images.Media.DATE_MODIFIED;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
        column_id = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
        thum = cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA);


        while (cursor.moveToNext()) {
            long l=0;
            String hrSize = "";
            absolutePathOfImage = cursor.getString(column_index_data);
                Helpers.LogPrint("thum", cursor.getString(thum));
            File output = new File(cursor.getString(thum));
            long sizes = output.length();
            double b = sizes;
            double k = sizes / 1024.0;
            double m = ((sizes / 1024.0) / 1024.0);
            double g = (((sizes / 1024.0) / 1024.0) / 1024.0);
            double t = ((((sizes / 1024.0) / 1024.0) / 1024.0) / 1024.0);

            DecimalFormat dec = new DecimalFormat("0.00");

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

            Video_model obj_model = new Video_model();

            obj_model.setStr_path(absolutePathOfImage);
            obj_model.setStr_thumb(cursor.getString(thum));
            obj_model.setSize(hrSize);

                obj_model.setDuration("");

            obj_model.setSelected(false);
            al_video.add(obj_model);

        }
        obj_adapter = new Video_adapter(ActivityVideoList.this,R.layout.video_adapts,al_video);
        recyclerView.setAdapter( obj_adapter);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        getvideos();
                    } else {
                        Toast.makeText(ActivityVideoList.this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
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
        }else if (requestCode==PICK_VIDEO_REQUEST){

            if (data == null) {
                return;
            }else{

                try {
                    File actualvideo = UtilFile.from(ActivityVideoList.this,data.getData());
                    Intent intent_gallery = new Intent(ActivityVideoList.this, Activity_compress.class);
                    intent_gallery.putExtra("video", actualvideo.toString());
                    startActivity(intent_gallery);
                    finish();

                }catch (Exception e) {
                    Helpers.LogPrint("gallery file faildto open", "" + e);
                }
            }
        }else {
            Helpers.LogPrint("else", "else");
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

        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {

                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
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
                                Helpers.LogPrint("No update On Resume","No update");
                            }
                        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        item = menu.findItem(R.id.menu_delete);
          gallery = menu.findItem(R.id.menu_gallery);
        menu_share=menu.findItem(R.id.menu_share);
        menu_share.setVisible(false);
        item.setVisible(false);

        menu_share.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String path="";
                for (int i=0;i<al_video.size();i++){
                    if (al_video.get(i).getSelected()) {
                      path=al_video.get(i).getStr_path();
                    break;
                    }
                }
                MediaScannerConnection.scanFile(ActivityVideoList.this, new String[] { path },

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
        gallery.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
                startActivityForResult(intent, PICK_VIDEO_REQUEST);
                return false;
            }
        });

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(ActivityVideoList.this)
                        // set message, title, and icon
                        .setTitle("Delete")
                        .setMessage("Do you want to Delete")
                        .setIcon(R.drawable.ic_delete)

                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                //your deleting code
                                for (int i = 0; i < al_video.size(); i++) {
                                    if (al_video.get(i).getSelected()) {
                                        isAnySelectec = "";
                                        item.setVisible(false);
                                        al_video.get(i).setSelected(false);

                                        String path = al_video.get(i).getStr_path();

                                        try {
                                            File fdelete = new File(path);
                                            if (fdelete.exists()) {
                                                final String where = MediaStore.MediaColumns.DATA + "=?";
                                                final String[] selectionArgs = new String[] {
                                                        fdelete.getAbsolutePath()
                                                };
                                                final ContentResolver contentResolver = ActivityVideoList.this.getContentResolver();
                                                final Uri filesUri = MediaStore.Files.getContentUri("external");

                                                contentResolver.delete(filesUri, where, selectionArgs);

                                                if (fdelete.exists()) {

                                                    contentResolver.delete(filesUri, where, selectionArgs);
                                                }
                                                dialog.dismiss();
                                                System.out.println("file Deleted :" + path);

                                            } else {
                                                dialog.dismiss();
                                                Toast.makeText(ActivityVideoList.this,"File Not Found.", Toast.LENGTH_LONG).show();
                                                System.out.println("file not Found :" + path);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            dialog.dismiss();
                                        }
                                    }

                                }

                                Toast.makeText(ActivityVideoList.this,"Delete", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(ActivityVideoList.this, ActivityVideoList.class);
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
        if (isAnySelectec.equalsIgnoreCase("true")) {
            for (int i = 0; i < al_video.size(); i++) {
                if (al_video.get(i).getSelected()) {
                    isAnySelectec = "";
                    item.setVisible(false);
                    al_video.get(i).setSelected(false);

                }
            }
        }
    }
    }