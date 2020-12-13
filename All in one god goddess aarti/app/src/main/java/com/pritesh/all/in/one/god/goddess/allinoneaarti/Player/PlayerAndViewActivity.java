/*
* Copyright (C) 2017 Gautam Chibde
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.pritesh.all.in.one.god.goddess.allinoneaarti.Player;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

//import com.chibde.visualizer.CircleBarVisualizer;
//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdLoader;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdSize;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.InterstitialAd;
//import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.formats.MediaView;
//import com.google.android.gms.ads.formats.NativeAdOptions;
//import com.google.android.gms.ads.formats.UnifiedNativeAd;
//import com.google.android.gms.ads.formats.UnifiedNativeAdView;
//import com.prit.videotomp3pro.R;
//import com.prit.videotomp3pro.View.MyFiles.Mp3FileAdapter;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.pritesh.all.in.one.god.goddess.allinoneaarti.Activity.LikeActivity;
import com.pritesh.all.in.one.god.goddess.allinoneaarti.Activity.MainActivity;
import com.pritesh.all.in.one.god.goddess.allinoneaarti.R;
import com.pritesh.all.in.one.god.goddess.allinoneaarti.Utils.Helper;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static com.pritesh.all.in.one.god.goddess.allinoneaarti.Player.BaseActivity.AUDIO_PERMISSION_REQUEST_CODE;
import static com.pritesh.all.in.one.god.goddess.allinoneaarti.Player.BaseActivity.WRITE_EXTERNAL_STORAGE_PERMS;


public class PlayerAndViewActivity extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "AllInOneArti";
    String admob_app_id, banner_home_footer, interstitial_full_screen;
    private ImageView forwardbtn, backwardbtn, pausebtn, playbtn;
    private MediaPlayer mPlayer;
    private TextView songName, startTime, songTime, lyricstext;
    private SeekBar songPrgs;
    private int oTime = 0, sTime = 0, eTime = 0, fTime = 5000, bTime = 5000;
    private Handler hdlr = new Handler();
    private AdView mAdView;
    File output;
    InterstitialAd mInterstitialAd;
    public static  int fulladdcount=0;
    private static final int REQUEST_PERMISSIONS = 100;
    private Boolean chekpermistion = false;
    String iconselect="";
    boolean feedbackstatus=true;
    //    CircleBarVisualizer circleBarVisualizer;
    String lyrics,like,postionList;
    int songs,postion;
    String Compress = "";
    Boolean one =true;
    Boolean two =true;
    Boolean three =true;
    Boolean four =true;
    Boolean five =true;
    Boolean six =true;
    Boolean seven =true;
    Boolean eight =true;
    public static MenuItem item, love, menu_share;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playerandview);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        backwardbtn = (ImageView) findViewById(R.id.btnBackward);
        forwardbtn = (ImageView) findViewById(R.id.btnForward);
        playbtn = (ImageView) findViewById(R.id.btnPlay);
        pausebtn = (ImageView) findViewById(R.id.btnPause);
        startTime = (TextView) findViewById(R.id.txtStartTime);
        songTime = (TextView) findViewById(R.id.txtSongTime);
        lyricstext = (TextView) findViewById(R.id.lyrics);


        try {
            songs = getIntent().getIntExtra("song", 0);
            lyrics = getIntent().getStringExtra("lyrics");
            postion = getIntent().getIntExtra("postion",0);
            like = getIntent().getStringExtra("like");

            lyricstext.setText(lyrics);

        } catch (Exception e) {

        }
        //  output= new File(OutputPath);
        //Uri outputpath= Uri.fromFile(output);
        initialize();


        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        admob_app_id = prefs.getString("admob_app_id", "");
        if (admob_app_id.equalsIgnoreCase("")) {
            admob_app_id = getString(R.string.admob_app_id);
            banner_home_footer = getString(R.string.banner_home_footer);
            interstitial_full_screen = getString(R.string.interstitial_full_screen);
        } else {
            admob_app_id = prefs.getString("admob_app_id", "");
            banner_home_footer = prefs.getString("banner_home_footer", "");
            interstitial_full_screen = prefs.getString("interstitial_full_screen", "");
        }
        // Loading banner add
        MobileAds.initialize(this, admob_app_id);
        LinearLayout adContainer = findViewById(R.id.circle_bar_visualizer_adview);
        mAdView = new AdView(PlayerAndViewActivity.this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(banner_home_footer);
        adContainer.addView(mAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        chekpermisstion();
//        // Show full page add
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
        //  playsong();

        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Toast.makeText(PlayerAndViewActivity.this, "Play", Toast.LENGTH_SHORT).show();
                    playsong();
                } catch (Exception e) {

                }
            }
        });
        pausebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mPlayer.pause();
                    pausebtn.setEnabled(false);
                    playbtn.setEnabled(true);
                    Toast.makeText(getApplicationContext(), "Pause", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {

                }
            }
        });
        forwardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if ((sTime + fTime) <= eTime) {
                        sTime = sTime + fTime;
                        mPlayer.seekTo(sTime);
                    } else {
                        Toast.makeText(getApplicationContext(), "Cannot jump forward 5 seconds", Toast.LENGTH_SHORT).show();
                    }
                    if (!playbtn.isEnabled()) {
                        playbtn.setEnabled(true);
                    }
                } catch (Exception e) {

                }
            }
        });
        backwardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if ((sTime - bTime) > 0) {
                        sTime = sTime - bTime;
                        mPlayer.seekTo(sTime);
                    } else {
                        Toast.makeText(getApplicationContext(), "Cannot jump backward 5 seconds", Toast.LENGTH_SHORT).show();
                    }
                    if (!playbtn.isEnabled()) {
                        playbtn.setEnabled(true);
                    }
                } catch (Exception e) {

                }
            }
        });


        if (Compress != null) {
            if (Compress.equalsIgnoreCase("Done")) {
                SharedPreferences prefss = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                String rateuse = prefss.getString("rateUs", "");
                if (rateuse.equalsIgnoreCase("")) {
                    //    feedbackPopup();
                } else {

                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);


        love = menu.findItem(R.id.love);


        if (like.equalsIgnoreCase("true")){
            love.setIcon(getResources().getDrawable(R.drawable.lovered));
            iconselect = "red";
        }else {
            love.setIcon(getResources().getDrawable(R.drawable.lovewhite));
            iconselect = "white";
        }

        love.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (iconselect.equalsIgnoreCase("red")){
                    love.setIcon(getResources().getDrawable(R.drawable.lovewhite));
                    iconselect = "white";
//                    SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
//                    admob_app_id = prefs.getString("admob_app_id", "");
//                    Map<String, ?> allEntries = prefs.getAll();
//
//                    for (int i = 0;i<prefs.getAll().size();i++){
//                        prefs.get
//                    }
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.remove("postion"+postion);
                    editor.commit();
                    Toast.makeText(PlayerAndViewActivity.this,R.string.removefavorate,Toast.LENGTH_LONG).show();
                }else {
                    love.setIcon(getResources().getDrawable(R.drawable.lovered));
                    iconselect = "red";
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

                    editor.putInt("postion"+postion, postion);
                    editor.commit();
                    Toast.makeText(PlayerAndViewActivity.this,R.string.favorate,Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });





        return super.onCreateOptionsMenu(menu);
    }

 
    public void chekpermisstion() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(PlayerAndViewActivity.this,
                    Manifest.permission.RECORD_AUDIO))) {

            } else {
                ActivityCompat.requestPermissions(PlayerAndViewActivity.this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_PERMISSIONS);
                chekpermistion = false;

            }
        } else {
            chekpermistion = true;

        }
    }

    private void playsong() {
        try {
            mPlayer.start();
            eTime = mPlayer.getDuration();
            sTime = mPlayer.getCurrentPosition();
            if (oTime == 0) {
                songPrgs.setMax(eTime);
                oTime = 1;
            }
            songTime.setText(String.format("%d:%d ", TimeUnit.MILLISECONDS.toMinutes(eTime),
                    TimeUnit.MILLISECONDS.toSeconds(eTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(eTime))));
            startTime.setText(String.format("%d:%d ", TimeUnit.MILLISECONDS.toMinutes(sTime),
                    TimeUnit.MILLISECONDS.toSeconds(sTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))));
            //   songPrgs.setProgress(sTime);
            hdlr.postDelayed(UpdateSongTime, 100);
            pausebtn.setEnabled(true);
            playbtn.setEnabled(false);
        } catch (Exception e) {

        }
    }

    private void initialize() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(WRITE_EXTERNAL_STORAGE_PERMS, AUDIO_PERMISSION_REQUEST_CODE);
        } else {
            setPlayer();
        }
    }

    private void showInterstitial() {
        if ( fulladdcount!=1) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }

    }

    private void setPlayer() {
        mPlayer = MediaPlayer.create(this, songs);
        songPrgs = (SeekBar) findViewById(R.id.sBar);
        songPrgs.setClickable(false);
        pausebtn.setEnabled(false);
        playsong();
    }

    private Runnable UpdateSongTime = new Runnable() {
        @SuppressLint("ResourceType")
        @Override
        public void run() {
            if (mPlayer.isPlaying()) {

                sTime = mPlayer.getCurrentPosition();
                startTime.setText(String.format("%d:%d ", TimeUnit.MILLISECONDS.toMinutes(sTime),
                        TimeUnit.MILLISECONDS.toSeconds(sTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))));
                songPrgs.setProgress(sTime);
                Helper.LogPrint("time : ",""+sTime);

                if (one) {
                    if (sTime > 50000) {
                        one = false;
                        AdRequest adRequest = new AdRequest.Builder().build();
                        mAdView.loadAd(adRequest);
                    }
                }
                if (two) {
                    if (sTime > 100000) {
                        two =false;
                        AdRequest adRequest = new AdRequest.Builder().build();
                        mAdView.loadAd(adRequest);
                    }
                }
                if (three) {
                    if (sTime > 150000) {
                        three=false;
                        AdRequest adRequest = new AdRequest.Builder().build();
                        mAdView.loadAd(adRequest);
                    }
                }
                if (four) {
                    if (sTime > 200000) {
                        four=false;
                        AdRequest adRequest = new AdRequest.Builder().build();
                        mAdView.loadAd(adRequest);
                    }
                }
                if (five) {
                    if (sTime > 250000) {
                        five=false;
                        AdRequest adRequest = new AdRequest.Builder().build();
                        mAdView.loadAd(adRequest);
                    }
                }
                if (six) {
                    if (sTime > 300000) {
                        six=false;
                        AdRequest adRequest = new AdRequest.Builder().build();
                        mAdView.loadAd(adRequest);
                    }
                }
                if (seven) {
                    if (sTime > 350000) {
                        seven=false;
                        AdRequest adRequest = new AdRequest.Builder().build();
                        mAdView.loadAd(adRequest);
                    }
                }
                if (eight) {
                    if (sTime > 400000) {
                        eight=false;
                        AdRequest adRequest = new AdRequest.Builder().build();
                        mAdView.loadAd(adRequest);
                    }
                }



                if (sTime>200000){
                    SharedPreferences prefss = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                    String rateuse = prefss.getString("rateUs", "");
                    if (rateuse.equalsIgnoreCase("")) {
                        if (feedbackstatus) {
                            feedbackstatus = false;
                            feedbackPopup();
                        }

                    } else {

                    }

                }
//                Random random= new Random();
//                int clor= Color.argb(255,random.nextInt(256),random.nextInt(256),random.nextInt(256));
//                circleBarVisualizer.setColor(clor);
                hdlr.postDelayed(this, 100);
            } else {
                pausebtn.setEnabled(false);
                playbtn.setEnabled(true);
            }
        }
    };


    @Override
    public void onBackPressed() {
        try {
            hdlr.removeCallbacks(UpdateSongTime);
            mPlayer.reset();
        } catch (Exception e) {

        }
        super.onBackPressed();

        if (like.equalsIgnoreCase("true")){
            Intent intent = new Intent(PlayerAndViewActivity.this, LikeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else{
            Intent intent = new Intent(PlayerAndViewActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
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
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void feedbackPopup()
    {

        LayoutInflater factory = LayoutInflater.from(PlayerAndViewActivity.this);
        final View deleteDialogView = factory.inflate(R.layout.feedbackpopup, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(PlayerAndViewActivity.this).create();
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
        AdLoader  adLoader = new AdLoader.Builder(PlayerAndViewActivity.this, natice_advanceadd).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

            private ColorDrawable background;@Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                NativeTemplateStyle styles = new
                        NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                template[0] = deleteDialogView.findViewById(R.id.nativeTemplateView);
                template[0].setStyles(styles);
                template[0].setNativeAd(unifiedNativeAd);
                deleteDialogView.findViewById(R.id.nativeTemplateView).setVisibility(View.VISIBLE);
                // Showing a simple Toast message to user when Native an ad is Loaded and ready to show
                //   Toast.makeText(PlayerAndViewActivity.this, "Native Ad is loaded ,now you can show the native ad  ", Toast.LENGTH_LONG).show();
            }

        }).build();


        // load Native Ad with the Request
        adLoader.loadAds(new AdRequest.Builder().build(),5);


        feedbacksubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("rateUs", "Done");
                editor.commit();

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




    }

}
