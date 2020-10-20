package com.pritesh.videocompressorpro_fastvideocompressor.View;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.pritesh.videocompressorpro_fastvideocompressor.R;

import java.io.File;
import java.text.DecimalFormat;

import static com.pritesh.videocompressorpro_fastvideocompressor.View.MainActivity.MY_PREFS_NAME;


public class OutputActivity extends AppCompatActivity {
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
    String Scrren;
    String Compress="";
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
        Scrren = getIntent().getStringExtra("Scrren");
        Compress = getIntent().getStringExtra("Compress");
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

        LayoutInflater factory = LayoutInflater.from(OutputActivity.this);
        final View deleteDialogView = factory.inflate(R.layout.feedbackpopup, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(OutputActivity.this).create();
        deleteDialog.setView(deleteDialogView);


        TextView loveapp=(TextView)deleteDialogView.findViewById(R.id.header);
        loveapp.setText("Rate us if you like this app?");
        loveapp.setTextSize(15);
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
        AdLoader adLoader = new AdLoader.Builder(OutputActivity.this, natice_advanceadd)

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
                                android.content.Intent.ACTION_SEND);
                        shareIntent.setType("video/*");
                        shareIntent.putExtra(
                                android.content.Intent.EXTRA_SUBJECT, title);
                        shareIntent.putExtra(
                                android.content.Intent.EXTRA_TITLE, title);
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

        if (Scrren!=null) {
            if (Scrren.equalsIgnoreCase("MyVideo")) {
                Intent i = new Intent(OutputActivity.this, MyVideoListActivity.class);
                startActivity(i);
                finish();
            } else {

            }
        }else{
            Intent i = new Intent(OutputActivity.this, VideoListActivity.class);
            startActivity(i);
            finish();
        }
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
