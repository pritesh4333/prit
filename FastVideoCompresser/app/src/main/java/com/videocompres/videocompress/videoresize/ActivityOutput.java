package com.videocompres.videocompress.videoresize;


import static com.videocompres.videocompress.videoresize.HomeActivity.MY_PREFS_NAME;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.tasks.OnCompleteListener;

import java.io.File;
import java.text.DecimalFormat;


public class ActivityOutput extends AppCompatActivity {
    VideoView vv_video;
    ImageView output_delete;
    TextView mOutputInfoView;
    TextView resolutiontext;
    ImageView output_share;
    TextView video_location;
    InterstitialAd mInterstitialAd;
    private MediaController mediaController;
     String Scrren;
    AdView mAdView;
    String Compress="";
    public   int cpunt =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.output);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        vv_video = (VideoView) findViewById(R.id.vv_video);
        mOutputInfoView = findViewById(R.id.output_info);
        output_delete=(ImageView)findViewById(R.id.output_delete);
        output_share=findViewById(R.id.output_share);
        video_location=(TextView)findViewById(R.id.video_location);
        resolutiontext=(TextView)findViewById(R.id.resolutiontext);
        final String str_video = getIntent().getStringExtra("OutoutPath");
        Scrren = getIntent().getStringExtra("Scrren");
        Compress = getIntent().getStringExtra("Compress");
        vv_video.setVideoPath(str_video);
        mediaController = new MediaController(this);
        vv_video.setMediaController(mediaController);
        vv_video.start();
        LinearLayout adContainer = findViewById(R.id.output_adview);
          mAdView = new AdView(ActivityOutput.this);
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
                cpunt =1;
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
            }
            @Override
            public void onAdLeftApplication() {
                cpunt =1;
            }
            @Override
            public void onAdOpened() {
                cpunt =1;
            }
        });
        output_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(ActivityOutput.this)
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
                                    final ContentResolver contentResolver = ActivityOutput.this.getContentResolver();
                                    final Uri filesUri = MediaStore.Files.getContentUri("external");

                                    contentResolver.delete(filesUri, where, selectionArgs);

                                    if (fdelete.exists()) {

                                        contentResolver.delete(filesUri, where, selectionArgs);
                                    }

                                         Toast.makeText(ActivityOutput.this, "File Deleted.", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(ActivityOutput.this,  ActivityVideoList.class);
                                        startActivity(i);
                                        finish();

                                    } else {
                                        Toast.makeText(ActivityOutput.this,"File Not Found.", Toast.LENGTH_LONG).show();
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

        vv_video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

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
    private void feedbackPopup()
    {

        LayoutInflater factory = LayoutInflater.from(ActivityOutput.this);
        final View deleteDialogView = factory.inflate(R.layout.feedback, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(ActivityOutput.this).create();
        deleteDialog.setView(deleteDialogView);


        TextView loveapp=(TextView)deleteDialogView.findViewById(R.id.header);
        loveapp.setText("Rate us if you like this app?");
        loveapp.setTextSize(15);
        Button feedbacksubmit = (Button) deleteDialogView.findViewById(R.id.feedbacksubmit);

        final TemplateView[] template = new TemplateView[1];
        AdLoader adLoader = new AdLoader.Builder(ActivityOutput.this, getString(R.string.natice_advanceadd)).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

            private ColorDrawable background;@Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                NativeTemplateStyle styles = new
                        NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                template[0] = deleteDialogView.findViewById(R.id.nativeTemplateViewFeedback);
                template[0].setStyles(styles);
                template[0].setNativeAd(unifiedNativeAd);
                deleteDialogView.findViewById(R.id.nativeTemplateViewFeedback).setVisibility(View.VISIBLE);

            }

        }).build();
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


    private void showInterstitial() {
        if ( cpunt !=1) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }
    }
    public void shareVideo(final String title, String path) {
        MediaScannerConnection.scanFile(ActivityOutput.this, new String[] { path },

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

        if (Scrren!=null) {
            if (Scrren.equalsIgnoreCase("MyVideo")) {
                Intent i = new Intent(ActivityOutput.this, AcitivityMyvideolist.class);
                startActivity(i);
                finish();
            } else {

            }
        }else{
            Intent i = new Intent(ActivityOutput.this, ActivityVideoList.class);
            startActivity(i);
            finish();
        }
    }
 }
