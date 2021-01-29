package com.pritesh.videocompressorpro_fastvideocompressor.View;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
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
import android.os.Environment;
import android.text.format.DateUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;


import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pritesh.videocompressorpro_fastvideocompressor.R;
import com.pritesh.videocompressorpro_fastvideocompressor.Utils.Helper;


import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;


import nl.bravobit.ffmpeg.ExecuteBinaryResponseHandler;
import nl.bravobit.ffmpeg.FFmpeg;
import nl.bravobit.ffmpeg.FFtask;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;
import uk.co.deanwild.materialshowcaseview.shape.CircleShape;

import static com.pritesh.videocompressorpro_fastvideocompressor.View.MainActivity.MY_PREFS_NAME;

//import com.vincent.videocompressor.VideoCompress;

public class CompressorActivity extends Activity {

    VideoView vv_video;
    ImageView mOutputOptionsButton;
    TextView mInputInfoView;
    TextView mOutputInfoView;
    TextView resolutiontext;
    //TextView play_txt;
    // TextView outputtxt;
    // ImageView output_play;
    // ImageView play_img;
    ImageView input_options;
    ImageView output_share;
    Button video_location;
    Button convert;
    private static final String TAG = "video-Compress & Edit";
    private String str_video;
    public static  int fulladdcountcompressactivity=0;
    public File mInputFile; // = new File("/storage/emulated/0/Android/data/com.dstukalov.videoconverter/files/Temp/tmp.mp4");
    private File outputs=null;
    private File outputsoption=null;
    public long mTimeFrom;
    public long mTimeTo;
    private int mWidth;
    private int mHeight;
    private int msize;
    private String Resolution="";
    private String isCompressDone ="";
    public String outputPath="";
    ProgressDialog progressDialog;
    private MediaController mediaController;
    InterstitialAd mInterstitialAd;


    LinearLayout outoutinfo_showcase;
    private Boolean InputVideoPlayingStatus=false,OutputVideoPlayingStatus=false;
    String admob_app_id,banner_home_footer,interstitial_full_screen;
    private Math Crashlytics;
    AdRequest adRequest;
    AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_galleryview);

        init();
        FCMregistration();
    }

    private void init() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        vv_video = (VideoView) findViewById(R.id.vv_video);
        mOutputOptionsButton=(ImageView)findViewById(R.id.output_options);
        mInputInfoView = findViewById(R.id.input_info);
        mOutputInfoView = findViewById(R.id.output_info);

        input_options=findViewById(R.id.input_options);
        output_share=findViewById(R.id.output_share);
        convert=(Button)findViewById(R.id.convert);
        video_location=(Button)findViewById(R.id.video_location);
        resolutiontext=(TextView)findViewById(R.id.resolutiontext);
        outoutinfo_showcase=(LinearLayout)findViewById(R.id.outoutinfo_showcase);

        convert.setVisibility(View.VISIBLE);


        //FFmpeg Instance





        if (FFmpeg.getInstance(this).isSupported()) {

            // ffmpeg is supported

            versionFFmpeg();
            //ffmpegTestTaskQuit();
        } else {
          //  Toast.makeText(this,"not support",Toast.LENGTH_LONG).show();
            // ffmpeg is not supported
            Helper.LogPrint(TAG,"ffmpeg not supported!");
        }


        str_video = getIntent().getStringExtra("video");
        vv_video.setVideoPath(str_video);
        mediaController = new MediaController(this);
        vv_video.setMediaController(mediaController);
        vv_video.start();

        InputVideoPlayingStatus=true;




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

        LinearLayout adContainer = findViewById(R.id.compress_adview);
          mAdView = new AdView(CompressorActivity.this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(banner_home_footer);
        adContainer.addView(mAdView);
          adRequest = new AdRequest.Builder().build();
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
               //  Toast.makeText(getApplicationContext(), "Ad failed to load! error code Compress: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {

                // Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                fulladdcountcompressactivity++;
                // Toast.makeText(getApplicationContext(), "Ad is opened!", Toast.LENGTH_SHORT).show();
            }
        });

        input_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(CompressorActivity.this, VideoListActivity.class);
                startActivity(i);
                finish();
            }
        });

        output_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (outputsoption!=null) {
                    shareVideo("Share", "" + outputsoption);
                }else{
                    showAlertError("First Compress video to Share");

                }
            }
        });


        vv_video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                InputVideoPlayingStatus=false;
                OutputVideoPlayingStatus=false;
                // play_txt.setText("Play");
                // play_img.setBackground(getResources().getDrawable(R.drawable.ic_play));
                //  outputtxt.setText("Play");
                //  output_play.setBackground(getResources().getDrawable(R.drawable.ic_play));
            }
        });



        mOutputOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOutputOptions();
            }
        });

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                File mypath=null;

                try {
                    mypath = new File(Environment.getExternalStorageDirectory(), "Video Compressor Pro");

                    if (!mypath.exists()) {
                        if (!mypath.mkdirs()) {
                            Helper.LogPrint("App", "failed to create directory");
                        }
                    }
                }catch(Exception e){}

                if (Resolution.equalsIgnoreCase("")){
                    showAlertError("Select Resolution to Compress");
                }else{
                    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);

                    String output=mypath+"/VID_COMPRESSOR_PRO_" + dateFormat.format(new Date()) + ".mp4";
                    VideConvertWithFFMPEG(str_video,output);

                }

            }
        });
        video_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CompressorActivity.this,OutputActivity.class);
                i.putExtra("OutoutPath",outputPath);
                i.putExtra("Compress","Done");
                startActivity(i);
                finish();
            }
        });



        ShowVideoDetial();


        showShowcase();


    }



    private void versionFFmpeg() {
        FFmpeg.getInstance(this).execute(new String[]{"-version"}, new ExecuteBinaryResponseHandler() {
            @Override
            public void onSuccess(String message) {
                Helper.LogPrint(TAG,message);
            }

            @Override
            public void onProgress(String message) {
                Helper.LogPrint(TAG,message);
            }
        });

    }
    private void VideConvertWithFFMPEG(String input, final String output) {



        final int msec = MediaPlayer.create(this, Uri.fromFile(new File(input))).getDuration();
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.progress_dailog, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);




        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        TextView total = (TextView) dialogView.findViewById(R.id.total);
        TextView progress = (TextView) dialogView.findViewById(R.id.progress);
        TextView percentage = (TextView) dialogView.findViewById(R.id.percentage);
        ProgressBar mProgressBar = (ProgressBar) dialogView.findViewById(R.id.progress_bar);
        total.setText("" + msec);
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
        AdLoader adLoader = new AdLoader.Builder(CompressorActivity.this, natice_advanceadd).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

            private ColorDrawable background;@Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                NativeTemplateStyle styles = new
                        NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                template[0] = dialogView.findViewById(R.id.nativeTemplateViewProgress);
                template[0].setStyles(styles);
                template[0].setNativeAd(unifiedNativeAd);
                dialogView.findViewById(R.id.nativeTemplateViewProgress).setVisibility(View.VISIBLE);
                // Showing a simple Toast message to user when Native an ad is Loaded and ready to show
                //   Toast.makeText(MainActivity.this, "Native Ad is loaded ,now you can show the native ad  ", Toast.LENGTH_LONG).show();
            }

        }).build();


        // load Native Ad with the Request
        adLoader.loadAds(new AdRequest.Builder().build(),5);
        alertDialog.show();




        //String[] command = {"-i",selectedVideoPath, "-preset", "fast", selectedVideoPath+timeStamp};
        //String[] command = {"-i", "/storage/emulated/0/DCIM/Camera/VID_20191216_161925.mp4", "-vcodec", "libx264", "-crf", "20", "/storage/emulated/0/DCIM/Camera/output.mp4"};
        //String[] command = {"-i", selectedVideoPath, "-vf", "scale="+scalel.getText().toString(), "-preset", "fast", selectedVideoPath+timeStamp+".mp4"};
        //String[] command = {"-i", "/storage/emulated/0/DCIM/Camera/VID_20191216_161925.mp4", "-vf", "scale=480:320,setdar=4:3", "/storage/emulated/0/DCIM/Camera/output.mp4"};
        //String[] command = {"-i", selectedVideoPath, "-filter:v", "scale="+scalel.getText().toString()+":-1", "-preset", "fast", selectedVideoPath+timeStamp+".mp4"};
        //"-i" input "-c:v libx265 -preset veryfast -tag:v hvc1 -b:v 800k -bufsize 1200k -vf scale=1080:1920,format=yuv420p -b:a 128k output.mp4
        //ffmpeg -i input.mp4 -vcodec libx264 -crf 20 output.mp4
        //String[] command = {"-i", input, "-vcodec",  "libx264", "-crf", "20" ,output};
        //String[] command = {"-i", input, "-vcodec", "libx265", "-crf", "27",  "scale="+Resolution,  "ultrafast",output};
        //String[] command = {"-i "+input+" -c:v libvpx-vp9 -b:v 0.33M -c:a libopus -b:a 96k -filter:v scale="+Resolution+" "+output+""};

        //add ffmpeg final and previous working with only 150p like this only
        String[] command = {"-i", input,"-vcodec",  "libx264", "-crf", "27", "-filter:v", "scale="+Resolution+":-2", "-c:a", "copy", "-preset", "veryfast" ,output};
        //String[] command = {"-i", input, "-vf", "scale=320:200" , "-c:a", "copy", "-crf", "10", "-preset", "ultrafast" ,output};
        //-i input.avi -vf scale=7680:4320 -crf 10 output.avi
        //String[] command = {"-i", input, "-vf", "-s "+Resolution,  "-preset", "fast" ,output};
        // -i <inputfilename> -s 640x480 -b:v 512k -vcodec mpeg1video -acodec copy <outputfilename>



        for (int i =0;i<command.length;i++){
            Helper.LogPrint(TAG,command[i]);
        }
        final Boolean[] one = {true};
        final Boolean[] two = {true};
        final Boolean[] three = {true};
        final Boolean[] four = {true};
        final FFtask task = FFmpeg.getInstance(this).execute(command, new ExecuteBinaryResponseHandler() {
            @Override
            public void onStart() {


                alertDialog.show();


            }

            @Override
            public void onFinish() {

                alertDialog.dismiss();



            }

            @Override
            public void onSuccess(String message) {
                Helper.LogPrint(TAG,message);
                resolutiontext.setText("Resolution");
                Resolution="";
                isCompressDone="done";
                final Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                File outputs= new File(output);
                intent.setData(Uri.fromFile(outputs));
                CompressorActivity.this.sendBroadcast(intent);
                ShowVideoOutputDetial(new File(output));
                alertDialog.dismiss();

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onProgress(String message) {

                Helper.LogPrint(TAG,message);
                float count = msec;

                isCompressDone="working";


                // Escape early if cancel() is called


                int start = message.indexOf("time=");
                int end = message.indexOf(" bitrate");

                if (start != -1 && end != -1) {
                    String duration = message.substring(start + 5, end);
                    if (duration != "") {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

                            progress.setText(""+(int)sdf.parse("1970-01-01 " + duration).getTime()+" / ");
                            float i =sdf.parse("1970-01-01 " + duration).getTime();
                            percentage.setText(new DecimalFormat("##.##").format(i/count*100)+"%");
                            if (i/count*100>25){
                                if (one[0]) {
                                    adLoader.loadAds(new AdRequest.Builder().build(),3);
                                    mAdView.loadAd(adRequest);
                                    one[0] =false;
                                }
                            }
                            if (i/count*100>50){
                                if (two[0]) {
                                    adLoader.loadAds(new AdRequest.Builder().build(),3);
                                    mAdView.loadAd(adRequest);
                                    two[0] =false;
                                }
                            }
                            if (i/count*100>75){
                                if (three[0]) {
                                    adLoader.loadAds(new AdRequest.Builder().build(),3);
                                    mAdView.loadAd(adRequest);
                                    three[0] =false;
                                }

                            }
                            if (i/count*100>90){
                                if (four[0]) {
                                    adLoader.loadAds(new AdRequest.Builder().build(),3);
                                    mAdView.loadAd(adRequest);
                                    four[0] =false;
                                }
                            }
                            mProgressBar.setProgress(Integer.parseInt(new DecimalFormat("##").format(i/count*100)));
                        }catch (NumberFormatException | ParseException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }

            }

            @Override
            public void onFailure(String message) {
                Helper.LogPrint(TAG,message);
                alertDialog.dismiss();
                showAlertError("Compress Fail Try Again");

            }
        });

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
        if (isCompressDone.equalsIgnoreCase("working")){
            if (outputs!=null) {
                File fdelete = new File(outputs.getPath());
                if (fdelete.exists()) {
                    if (fdelete.delete()) {
                        System.out.println("file Deleted :" + outputs.getPath());
                    } else {
                        System.out.println("file not Deleted :" + outputs.getPath());
                    }
                }
            }
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

    private void ShowVideoDetial() {
        final MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            try {
                mediaMetadataRetriever.setDataSource(str_video);
            } catch (Exception ex) {
                showAlertError("Can't support this file choose another video and try again");

                str_video = null;

                return;
            }

            final String width = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            final String height = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            long size = new File(str_video).length();


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

            duration = Long.parseLong(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));

            mWidth = Integer.parseInt(width);
            mHeight = Integer.parseInt(height);

            mediaMetadataRetriever.release();
            mInputInfoView.setText(getString(R.string.video_info, width, height,
                    DateUtils.formatElapsedTime(duration / 1000),
                    Formatter.formatShortFileSize(this, str_video.length())+" "+hrSize));
        } catch (Exception e) {
            //Toast.makeText(getBaseContext(), R.string.bad_video, Toast.LENGTH_SHORT).show();
            FirebaseCrashlytics.getInstance().log("Line no 384"+e);

            // str_video = null;
            showAlertError("Can't support this file choose another video and try again");

        }
    }

    public void showAlertError(String message){
        new AlertDialog.Builder(CompressorActivity.this)
                .setTitle("Alert")
                .setMessage(message)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        dialog.dismiss();
                        if(message.equalsIgnoreCase("Select Resolution to Compress")) {
                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                            editor.putString("Showcase", "");
                            editor.apply();
                            showShowcase();
                        }

                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void shareVideo(final String title, String path) {

        MediaScannerConnection.scanFile(CompressorActivity.this, new String[] { path },

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

    private void onOutputOptions() {
        final PopupMenu popup = new PopupMenu(this, mOutputOptionsButton);
        popup.getMenuInflater().inflate(R.menu.output_options, popup.getMenu());

            if (mWidth <= 186) {
                popup.getMenu().findItem(R.id.quality_186p).setVisible(false);
            }
            if (mWidth <= 426) {
                popup.getMenu().findItem(R.id.quality_426p).setVisible(false);
            }
            if (mWidth <= 512) {
                popup.getMenu().findItem(R.id.quality_512p).setVisible(false);
            }
            if (mWidth <= 640) {
                popup.getMenu().findItem(R.id.quality_640p).setVisible(false);
            }
            if (mWidth <= 720) {
                popup.getMenu().findItem(R.id.quality_720p).setVisible(false);
            }
            if (mWidth <= 960) {
                popup.getMenu().findItem(R.id.quality_960p).setVisible(false);
            }
            if (mWidth <= 1080) {
                popup.getMenu().findItem(R.id.quality_1080p).setVisible(false);
            }
            if (mWidth <= 1920) {
                popup.getMenu().findItem(R.id.quality_1920p).setVisible(false);
            }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {


                    case R.id.quality_186p:

                        Resolution = "186";
                        resolutiontext.setText(Resolution);

                        break;
                    case R.id.quality_426p:

                        Resolution="426";
                        resolutiontext.setText(Resolution);
                        break;

                    case R.id.quality_512p:

                        Resolution="512";
                        resolutiontext.setText(Resolution);
                        break;

                    case R.id.quality_640p:
                        Resolution="640";
                        resolutiontext.setText(Resolution);
                        break;
                    case R.id.quality_720p:
                        Resolution="720";
                        resolutiontext.setText(Resolution);
                        break;
                    case R.id.quality_960p:
                        Resolution="960";
                        resolutiontext.setText(Resolution);
                        break;
                    case R.id.quality_1080p:
                        Resolution="1080";
                        resolutiontext.setText(Resolution);
                        break;
                    case R.id.quality_1920p:
                        Resolution="1920";
                        resolutiontext.setText(Resolution);
                        break;

                }

                return true;
            }
        });

        popup.show();
    }

    private void showInterstitial() {
        Helper.LogPrint("addscount",""+fulladdcountcompressactivity);
        if (fulladdcountcompressactivity<1) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(CompressorActivity.this, VideoListActivity.class);
        startActivity(i);
        finish();

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

                    mWidth = Integer.parseInt(width);
                    mHeight = Integer.parseInt(height);
                } catch (NumberFormatException e) {
                    FirebaseCrashlytics.getInstance().log("Line no 510"+e);
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
                        Formatter.formatShortFileSize(this, str_video.length()))+" "+hrSize);
                //output_play.setEnabled(true);
                outputsoption=outputs;
                video_location.setText("Click Here To See Output");
                try {
                    new MaterialShowcaseView.Builder(CompressorActivity.this)
                            .setTarget(convert)
                            .setShape(new CircleShape())
                            .setDismissText("GOT IT")
                            .setContentText("Click Hear to See Final Result After Compresing Video")
                            .setDelay(1000)
                            .setFadeDuration(500)
                            .setDismissOnTouch(true)
                            .setMaskColour(CompressorActivity.this.getResources().getColor(R.color.primary_dark))
                            .setContentTextColor(CompressorActivity.this.getResources().getColor(R.color.yellow))
                            .show();
                }catch(Exception e){

                }
                showAlertError("Video Compress Successfully Click To See Output");
                convert.setVisibility(View.GONE);
                outputPath=outputs.getPath();
                video_location.setVisibility(View.VISIBLE);
            } catch (Exception ex) {
                showAlertError("Can't support this file choose another video and try again");

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
            FirebaseCrashlytics.getInstance().log("Show Detial"+e);
        }
    }
    public  void showShowcase(){
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String Showcase = prefs.getString("Showcase", "");

        if (Showcase.equalsIgnoreCase("")){

            ShowcaseConfig config = new ShowcaseConfig();
            config.setDelay(500); // half second between each showcase view
            String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?/.,";
            Random rnd = new Random();
            char charAt = alphabet.charAt(rnd.nextInt(alphabet.length()));


            MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, String.valueOf(charAt) );



            sequence.setConfig(config);



            try{
                sequence.addSequenceItem(
                        new MaterialShowcaseView.Builder(this)
                                .setTarget(mOutputOptionsButton)
                                .setShape(new CircleShape())
                                .setDismissText("GOT IT")
                                .setContentText("Select Resolution You Want To Compress. Video Size and Time For Compress Depends On Which Resolution You Select")
                                .setDelay(1000)
                                .setFadeDuration(500)
                                .setDismissOnTouch(true)
                                .setMaskColour(getResources().getColor(R.color.primary_dark))
                                .setContentTextColor(getResources().getColor(R.color.yellow))
                                // optional but starting animations immediately in onCreate can make them choppy
                                // .singleUse("Videocompress") // provide a unique ID used to ensure it is only shown once
                                .build()
                );


                sequence.addSequenceItem(

                        new MaterialShowcaseView.Builder(this)
                                .setTarget(convert)
                                .setShape(new CircleShape())
                                .setDismissText("GOT IT")
                                .setContentText("After Select Resolution Click Compress Video Button To Start Video Compresing")
                                .setDelay(1000)
                                .setDismissOnTouch(true)
                                .setFadeDuration(500)
                                .setMaskColour(getResources().getColor(R.color.primary_dark))
                                .setContentTextColor(getResources().getColor(R.color.yellow))
                                // optional but starting animations immediately in onCreate can make them choppy
                                // .singleUse("Videocompress") // provide a unique ID used to ensure it is only shown once
                                .build()
                );



                sequence.start();
            }catch(Exception e){

            }

            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString("Showcase", "Videocompress");
            editor.apply();
        }else {
        }
    }
    public void FCMregistration(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]

        // [END handle_data_extras]
        FirebaseMessaging.getInstance().subscribeToTopic(getString(R.string.default_notification_channel_name))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                        String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                        }
                        Log.d("Output Activity", msg);
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
