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
package com.prit.videotomp3pro.View.Player;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.chibde.visualizer.BarVisualizer;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.prit.videotomp3pro.R;
import com.prit.videotomp3pro.View.MainActivity;
import com.prit.videotomp3pro.View.MyFiles.Mp3FileAdapter;

import java.io.File;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.prit.videotomp3pro.View.Player.BaseActivity.AUDIO_PERMISSION_REQUEST_CODE;
import static com.prit.videotomp3pro.View.Player.BaseActivity.WRITE_EXTERNAL_STORAGE_PERMS;

public class BarVisualizerActivity extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "VideoCompressPrefsFile";
    String admob_app_id,banner_home_footer,interstitial_full_screen;
    private ImageView forwardbtn, backwardbtn, pausebtn, playbtn;
    private MediaPlayer mPlayer;
    private TextView songName, startTime, songTime;
    private SeekBar songPrgs;
    private  int oTime = 0, sTime = 0, eTime = 0, fTime = 5000, bTime = 5000;
    private Handler hdlr = new Handler();
    private AdView mAdView;
    File output;
    InterstitialAd mInterstitialAd;
    ImageView editfilename;
    TextView filename;
    public static MenuItem item,gallery,menu_share;
    BarVisualizer barVisualizer;
     int Position;
    String OutputPath;
    String Compress="";
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_visualizer);




        backwardbtn = (ImageView) findViewById(R.id.btnBackward);
        forwardbtn = (ImageView) findViewById(R.id.btnForward);
        playbtn = (ImageView) findViewById(R.id.btnPlay);
        pausebtn = (ImageView) findViewById(R.id.btnPause);
        startTime = (TextView) findViewById(R.id.txtStartTime);
        songTime = (TextView) findViewById(R.id.txtSongTime);
        editfilename=(ImageView)findViewById(R.id.editfilename);
        filename=(TextView)findViewById(R.id.filename);



        try {
            OutputPath = getIntent().getStringExtra("OutputPath");
            Position = getIntent().getIntExtra("position",00);
            Compress = getIntent().getStringExtra("Compress");
        }
        catch(Exception e){

        }
        output= new File(OutputPath);
        Uri outputpath= Uri.fromFile(output);
        initialize();
        filename.setText(OutputPath);
        mPlayer = MediaPlayer.create(this,outputpath);
        songPrgs = (SeekBar) findViewById(R.id.sBar);
        songPrgs.setClickable(false);
        pausebtn.setEnabled(false);


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
        LinearLayout adContainer = findViewById(R.id.circle_bar_visualizer_adview);
        mAdView = new AdView(BarVisualizerActivity.this);
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
        playsong();

        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BarVisualizerActivity.this, "Play", Toast.LENGTH_SHORT).show();
                playsong();
            }
        });
        pausebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.pause();
                pausebtn.setEnabled(false);
                playbtn.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Pause", Toast.LENGTH_SHORT).show();
            }
        });
        forwardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((sTime + fTime) <= eTime) {
                    sTime = sTime + fTime;
                    mPlayer.seekTo(sTime);
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot jump forward 5 seconds", Toast.LENGTH_SHORT).show();
                }
                if (!playbtn.isEnabled()) {
                    playbtn.setEnabled(true);
                }
            }
        });
        backwardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((sTime - bTime) > 0) {
                    sTime = sTime - bTime;
                    mPlayer.seekTo(sTime);
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot jump backward 5 seconds", Toast.LENGTH_SHORT).show();
                }
                if (!playbtn.isEnabled()) {
                    playbtn.setEnabled(true);
                }
            }
        });
        editfilename.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ViewGroup viewGroup = findViewById(android.R.id.content);

                //then we will inflate the custom alert dialog xml that we created
                View dialogView = LayoutInflater.from(BarVisualizerActivity.this).inflate(R.layout.rename_alert, viewGroup, false);


                //Now we need an AlertDialog.Builder object
                AlertDialog.Builder builder = new AlertDialog.Builder(BarVisualizerActivity.this);

                //setting the view of the builder to our custom view that we already inflated
                builder.setView(dialogView);




                final AlertDialog alertDialog = builder.create();
                EditText renameedit= (EditText)dialogView.findViewById(R.id.renameedittext);
                TextView save=(TextView)dialogView.findViewById(R.id.save);
                TextView cancel=(TextView)dialogView.findViewById(R.id.cancel);

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
                AdLoader adLoader = new AdLoader.Builder(BarVisualizerActivity.this, natice_advanceadd)
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
                            }
                        })
                        .withNativeAdOptions(new NativeAdOptions.Builder()
                                // Methods in the NativeAdOptions.Builder class can be
                                // used here to specify individual options settings.
                                .build())
                        .build();
                adLoader.loadAds(new AdRequest.Builder().build(),5);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String renametext=renameedit.getText().toString().trim();

                        if(!renametext.equals("")) {
                            try {
                                File mypath=null;

                                mypath = new File(Environment.getExternalStorageDirectory(), "Video To MP3");
                                File to = new File(mypath+"/", renametext+".mp3");
                                if(output.exists()) {
                                    output.renameTo(to);
                                    output = to;
                                    output = to;
                                    filename.setText(to.toString());
                                    Toast.makeText(BarVisualizerActivity.this, "Save", Toast.LENGTH_LONG).show();
                                }
                                alertDialog.cancel();
                            } catch (Exception anfe) {
                                Toast.makeText(BarVisualizerActivity.this,"Fail",Toast.LENGTH_LONG).show();
                            }
                        }else{
                            renameedit.setError("Enter Name");
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });




                alertDialog.show();
            }
        });

          barVisualizer = findViewById(R.id.visualizer);
        // set custom color to the line.
        barVisualizer.setColor(ContextCompat.getColor(this, R.color.custom));
        // define custom number of bars you want in the visualizer between (10 - 256).
        barVisualizer.setDensity(70);
        // Set your media player to the visualizer.
        barVisualizer.setPlayer(mPlayer.getAudioSessionId());

        if (Compress!=null) {
            if (Compress.equalsIgnoreCase("Done")) {
                SharedPreferences prefss = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                String rateuse = prefss.getString("rateUs", "");
                if (rateuse.equalsIgnoreCase("")) {
                    feedbackPopup();
                } else {

                }
            }
        }
    }

    private void feedbackPopup() {

        LayoutInflater factory = LayoutInflater.from(BarVisualizerActivity.this);
        final View deleteDialogView = factory.inflate(R.layout.feedbackpopup, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(BarVisualizerActivity.this).create();
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
        AdLoader adLoader = new AdLoader.Builder(BarVisualizerActivity.this, natice_advanceadd)

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

    private void playsong() {
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

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }

    }
    private void setPlayer() {
        Uri outputpath= Uri.fromFile(output);
        mPlayer = MediaPlayer.create(this, outputpath);

    }

    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            if (mPlayer.isPlaying()) {
                sTime = mPlayer.getCurrentPosition();
                startTime.setText(String.format("%d:%d ", TimeUnit.MILLISECONDS.toMinutes(sTime),
                        TimeUnit.MILLISECONDS.toSeconds(sTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))));
                songPrgs.setProgress(sTime);
                Random random= new Random();
                int clor= Color.argb(255,random.nextInt(256),random.nextInt(256),random.nextInt(256));
                barVisualizer.setColor(clor);
                hdlr.postDelayed(this, 100);
            } else {
                pausebtn.setEnabled(false);
                playbtn.setEnabled(true);
            }
        }
    };


    @Override
    public void onBackPressed() {
        hdlr.removeCallbacks(UpdateSongTime);
        mPlayer.reset();
        super.onBackPressed();
        finish();
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
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);

        item = menu.findItem(R.id.menu_delete);
        gallery = menu.findItem(R.id.menu_gallery);
        menu_share=menu.findItem(R.id.menu_share);

        menu_share.setVisible(true);
        item.setVisible(true);
        gallery.setVisible(false);

        menu_share.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                MediaScannerConnection.scanFile(BarVisualizerActivity.this, new String[] {String.valueOf(output)},

                        null, new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {

                                mPlayer.pause();
                                Intent shareIntent = new Intent(
                                        Intent.ACTION_SEND);
                                shareIntent.setType("audio/*");
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



        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(BarVisualizerActivity.this)
                        // set message, title, and icon
                        .setTitle("Delete")
                        .setMessage("Do you want to Delete")
                        .setIcon(R.drawable.ic_delete)

                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                //your deleting code


                                try {

                                    if (output.exists()) {
                                        final String where = MediaStore.MediaColumns.DATA + "=?";
                                        final String[] selectionArgs = new String[] {
                                                output.getAbsolutePath()
                                        };
                                        final ContentResolver contentResolver = BarVisualizerActivity.this.getContentResolver();
                                        final Uri filesUri = MediaStore.Files.getContentUri("external");

                                        contentResolver.delete(filesUri, where, selectionArgs);

                                        if (output.exists()) {

                                            contentResolver.delete(filesUri, where, selectionArgs);
                                        }
                                        dialog.dismiss();
                                        if(Position!=00) {
                                            Mp3FileAdapter.name.remove(Position);
                                            Mp3FileAdapter.size.remove(Position);
                                            Mp3FileAdapter.path.remove(Position);
                                        }
                                        System.out.println("file Deleted :" + output);

                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(BarVisualizerActivity
                                                .this,"File Not Found.", Toast.LENGTH_LONG).show();
                                        System.out.println("file not Found :" + output);

                                        mPlayer.pause();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    dialog.dismiss();
                                }




                                Toast.makeText(BarVisualizerActivity.this,"Delete", Toast.LENGTH_LONG).show();
                                onBackPressed();



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
}




