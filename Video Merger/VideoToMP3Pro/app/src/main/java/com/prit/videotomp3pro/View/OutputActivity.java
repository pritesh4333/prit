package com.prit.videotomp3pro.View;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateUtils;
import android.text.format.Formatter;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.prit.videotomp3pro.R;

import java.io.File;
import java.text.DecimalFormat;

import static com.prit.videotomp3pro.View.MainActivity.MY_PREFS_NAME;

public class OutputActivity extends AppCompatActivity {
    private FirebaseCrashlytics Crashlytics;
    VideoView vv_video;
    ImageView output_delete;
    TextView mOutputInfoView;
    TextView resolutiontext;
    TextView outputtxt;
   // ImageView output_play;
    ImageView output_share;
    TextView video_location;
    InterstitialAd mInterstitialAd;
    private AdView mAdView;
    private Boolean OutputVideoPlayingStatus=false;
    private MediaController mediaController;
    String admob_app_id,banner_home_footer,interstitial_full_screen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        vv_video = (VideoView) findViewById(R.id.vv_video);
        output_delete=(ImageView)findViewById(R.id.output_delete);

        mOutputInfoView = findViewById(R.id.output_info);
        //output_play=findViewById(R.id.output_play);


        output_share=findViewById(R.id.output_share);

        video_location=(TextView)findViewById(R.id.video_location);
        resolutiontext=(TextView)findViewById(R.id.resolutiontext);

       // outputtxt=(TextView)findViewById(R.id.outputtxt);

        final String str_video = getIntent().getStringExtra("OutoutPath");
        vv_video.setVideoPath(str_video);
        mediaController = new MediaController(this);
        vv_video.setMediaController(mediaController);
        vv_video.start();
        OutputVideoPlayingStatus=true;

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

        LinearLayout adContainer = findViewById(R.id.output_adview);
        AdView mAdView = new AdView(OutputActivity.this);
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
        output_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(OutputActivity.this)
                        // set message, title, and icon
                        .setTitle("Delete")
                        .setMessage("Do you want to Delete")
                        .setIcon(R.drawable.ic_delete)

                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                //your deleting code
                                dialog.dismiss();
                                try {
                                File fdelete = new File(str_video);
                                if (fdelete.exists()) {
                                    final String where = MediaStore.MediaColumns.DATA + "=?";
                                    final String[] selectionArgs = new String[] {
                                            fdelete.getAbsolutePath()
                                    };
                                    final ContentResolver contentResolver = OutputActivity.this.getContentResolver();
                                    final Uri filesUri = MediaStore.Files.getContentUri("external");

                                    contentResolver.delete(filesUri, where, selectionArgs);

                                    if (fdelete.exists()) {

                                        contentResolver.delete(filesUri, where, selectionArgs);
                                    }

                                        System.out.println("file Deleted :" + str_video);
                                        Toast.makeText(OutputActivity.this, "File Deleted.", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(OutputActivity.this, VideoListActivity.class);
                                        startActivity(i);
                                        finish();

                                    } else {
                                        Toast.makeText(OutputActivity.this,"File Not Found.", Toast.LENGTH_LONG).show();
                                        System.out.println("file not Found :" + str_video);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }

                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        })
                        .create();
                myQuittingDialogBox.show();


            }
        });
//        output_play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (OutputVideoPlayingStatus==false) {
//                    vv_video.start();
//                    OutputVideoPlayingStatus=true;
//                    outputtxt.setText("Playing");
//                    output_play.setBackground(getResources().getDrawable(R.drawable.ic_pause));
//                }else{
//                    OutputVideoPlayingStatus=false;
//                    vv_video.pause();
//                    outputtxt.setText("Stop");
//                    output_play.setBackground(getResources().getDrawable(R.drawable.ic_play));
//                }
//            }
//        });
        vv_video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                OutputVideoPlayingStatus=false;
//                outputtxt.setText("Play");
//                output_play.setBackground(getResources().getDrawable(R.drawable.ic_play));
            }
        });
        output_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str_video!=null) {
                    shareVideo("Share", "" + str_video);
                }else{


                }
            }
        });
        ShowVideoOutputDetial(new File(str_video));
    }
    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
    public void shareVideo(final String title, String path) {

        MediaScannerConnection.scanFile(OutputActivity.this, new String[] { path },

                null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Intent shareIntent = new Intent(
                                Intent.ACTION_SEND);
                        shareIntent.setType("video/*");
                        shareIntent.putExtra(
                                Intent.EXTRA_SUBJECT, title);
                        shareIntent.putExtra(
                                Intent.EXTRA_TITLE, title);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        shareIntent
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        startActivity(Intent.createChooser(shareIntent,"Share"));

                    }
                });
    }
    private void ShowVideoOutputDetial(File outputs) {
        try {
            final MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();

            try {

                mediaMetadataRetriever.setDataSource(String.valueOf(outputs));


                final String width = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
                final String height = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);

                long size = outputs.length();


                String hrSize = null;

                double b = size;
                double k = size/1024.0;
                double m = ((size/1024.0)/1024.0);
                double g = (((size/1024.0)/1024.0)/1024.0);
                double t = ((((size/1024.0)/1024.0)/1024.0)/1024.0);

                DecimalFormat dec = new DecimalFormat("0.00");

                if ( t>1 ) {
                    hrSize = dec.format(t).concat(" TB");
                } else if ( g>1 ) {
                    hrSize = dec.format(g).concat(" GB");
                } else if ( m>1 ) {
                    hrSize = dec.format(m).concat(" MB");
                } else if ( k>1 ) {
                    hrSize = dec.format(k).concat(" KB");
                } else {
                    hrSize = dec.format(b).concat(" Bytes");
                }
                long duration;
                try {
                    duration = Long.parseLong(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));


                } catch (NumberFormatException e) {
                    Crashlytics.log("Line no 510"+e);
                    //Toast.makeText(getBaseContext(), R.string.bad_video, Toast.LENGTH_SHORT).show();
                    File fdelete = new File(outputs.getPath());
                    if (fdelete.exists()) {
                        if (fdelete.delete()) {
                            System.out.println("file Deleted :" + outputs.getPath());
                        } else {
                            System.out.println("file not Deleted :" + outputs.getPath());
                        }
                    }
                    video_location.setVisibility(View.GONE);


                    return;
                }
                mediaMetadataRetriever.release();
                mOutputInfoView.setText(getString(R.string.video_info, width, height,
                        DateUtils.formatElapsedTime(duration / 1000),
                        Formatter.formatShortFileSize(this, outputs.length())));
               // output_play.setEnabled(true);


                video_location.setText("File Location:-\n" + outputs);

                video_location.setVisibility(View.VISIBLE);
            } catch (Exception ex) {


                File fdelete = new File(outputs.getPath());
                if (fdelete.exists()) {
                    if (fdelete.delete()) {
                        System.out.println("file Deleted :" + outputs.getPath());
                    } else {
                        System.out.println("file not Deleted :" + outputs.getPath());
                    }
                }
                video_location.setVisibility(View.GONE);
            }
        }catch (Exception e){
            Crashlytics.log("Show Detial"+e);
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
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(OutputActivity.this,VideoListActivity.class);
        startActivity(i);
        finish();

    }


}
