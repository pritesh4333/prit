package com.prit.videocompressorpro.View;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.prit.videocompressorpro.Model.Model_Video;
import com.prit.videocompressorpro.R;
import com.prit.videocompressorpro.Utils.Helper;
import com.prit.videocompressorpro.ViewModel.Adapter_Video;

import java.util.ArrayList;


public class VideoListActivity extends AppCompatActivity {

    Adapter_Video obj_adapter;
    ArrayList al_video = new ArrayList<>();
    GridView recyclerView;
    private AdView mAdView;
    InterstitialAd mInterstitialAd;

    private static final int REQUEST_PERMISSIONS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videofolder);

        init();
    }

    private void init(){

        recyclerView = (GridView) findViewById(R.id.recycler_view1);



        fn_checkpermission();

        // show add on banner
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // Show full page add
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));
        //mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }

            @Override
            public void onAdClosed() {
                // Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                // Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                // Toast.makeText(getApplicationContext(), "Ad is opened!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void fn_checkpermission(){
        /*RUN TIME PERMISSIONS*/

        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(VideoListActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(VideoListActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(VideoListActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        }else {
            Helper.LogPrint("Else","Else");
            fn_video();
        }
    }



    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
    public void fn_video() {

        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name,column_id,thum;

        String absolutePathOfImage = null;
        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME,MediaStore.Video.Media._ID,MediaStore.Video.Thumbnails.DATA};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
        column_id = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
        thum = cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            Helper.LogPrint("Column", absolutePathOfImage);
            Helper.LogPrint("Folder", cursor.getString(column_index_folder_name));
            Helper.LogPrint("column_id", cursor.getString(column_id));
            Helper.LogPrint("thum", cursor.getString(thum));

            Model_Video obj_model = new Model_Video();
            obj_model.setBoolean_selected(false);
            obj_model.setStr_path(absolutePathOfImage);
            obj_model.setStr_thumb(cursor.getString(thum));

            al_video.add(obj_model);

        }


        obj_adapter = new Adapter_Video(VideoListActivity.this,al_video);
        recyclerView.setAdapter(obj_adapter);



    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {                     if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    fn_video();
                } else {
                    Toast.makeText(VideoListActivity.this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
                }
            }
        }
    }
}