package com.videocompres.videocompress.videoresize;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
 import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateUtils;
import android.text.format.Formatter;
 import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.VideoView;

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
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
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


public class Activity_compress extends Activity {

    VideoView vv_video;

    TextView mOutputInfoView;
    TextView resolutiontext;
    private String isCompressDone ="";
    public String outputPath="";
    private MediaController mediaController;
    InterstitialAd mInterstitialAd;
    LinearLayout outoutinfo_showcase;
     ImageView input_options;
    ImageView output_share;
    ImageView mOutputOptionsButton;
    TextView mInputInfoView;
    private Boolean InputVideoPlayingStatus=false,OutputVideoPlayingStatus=false;
    private File outputs=null;
    private File outputsoption=null;
    private int mWidth;
    private int mHeight;
    Button video_location;
    Button convert;
    private static final String TAG = "Fast Video Compress";
    private String str_video;
    public static  int fulladdcountcompressactivity=0;
    private String Resolution="";
    AdRequest adRequest;
    AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.galleryview);
        initialisation();
     }

    private void initialisation() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        vv_video = (VideoView) findViewById(R.id.vv_video);
        mOutputOptionsButton=(ImageView)findViewById(R.id.output_options);

        video_location=(Button)findViewById(R.id.video_location);
        resolutiontext=(TextView)findViewById(R.id.resolutiontext);
        outoutinfo_showcase=(LinearLayout)findViewById(R.id.outoutinfo_showcase);
        mInputInfoView = findViewById(R.id.input_info);
        mOutputInfoView = findViewById(R.id.output_info);

        input_options=findViewById(R.id.input_options);
        output_share=findViewById(R.id.output_share);
        convert=(Button)findViewById(R.id.convert);

        convert.setVisibility(View.VISIBLE);

        if (FFmpeg.getInstance(this).isSupported()) {
            versionFFmpeg();
         } else {
            Helpers.LogPrint(TAG,"ffmpeg not supported!");
        }


        str_video = getIntent().getStringExtra("video");
        vv_video.setVideoPath(str_video);
        mediaController = new MediaController(this);
        vv_video.setMediaController(mediaController);
        vv_video.start();

        LinearLayout adContainer = findViewById(R.id.compress_adview);
          mAdView = new AdView(Activity_compress.this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(getString(R.string.banner_home_footer));
        adContainer.addView(mAdView);
          adRequest = new AdRequest.Builder().build();
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
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
            }

            @Override
            public void onAdLeftApplication() {
            }

            @Override
            public void onAdOpened() {
                fulladdcountcompressactivity++;
            }
        });

        input_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Activity_compress.this,ActivityVideoList.class);
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
                    mypath = new File(Environment.getExternalStorageDirectory(), "Fast Video Compressor");
                    if (!mypath.exists()) {
                        if (!mypath.mkdirs()) {
                            Helpers.LogPrint("App", "failed to create directory");
                        }
                    }
                }catch(Exception e){}
                if (Resolution.equalsIgnoreCase("")){
                    showAlertError("Select Resolution to Compress");
                }else{
                    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);
                    String output=mypath+"/FAST_COMPRESSOR" + dateFormat.format(new Date()) + ".mp4";
                    VideConvertWithFFMPEG(str_video,output);

                }

            }
        });
        video_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_compress.this, ActivityOutput.class);
                i.putExtra("OutoutPath",outputPath);
                i.putExtra("Compress","Done");
                startActivity(i);
                finish();
            }
        });
        ShowVideoDetial();
    }



    private void versionFFmpeg() {
        FFmpeg.getInstance(this).execute(new String[]{"-version"}, new ExecuteBinaryResponseHandler() {
            @Override
            public void onSuccess(String message) {
                Helpers.LogPrint(TAG,message);
            }

            @Override
            public void onProgress(String message) {
                Helpers.LogPrint(TAG,message);
            }
        });

    }
    private void VideConvertWithFFMPEG(String input, final String output) {
        final int msec = MediaPlayer.create(this, Uri.fromFile(new File(input))).getDuration();
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.progress, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        TextView total = (TextView) dialogView.findViewById(R.id.total);
        TextView progress = (TextView) dialogView.findViewById(R.id.progress);
        TextView percentage = (TextView) dialogView.findViewById(R.id.percentage);
        ProgressBar mProgressBar = (ProgressBar) dialogView.findViewById(R.id.progress_bar);
        total.setText("" + msec);
        final TemplateView[] template = new TemplateView[1];
         AdLoader adLoader = new AdLoader.Builder(Activity_compress.this, getString(R.string.natice_advanceadd)).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

            private ColorDrawable background;@Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                NativeTemplateStyle styles = new
                        NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                template[0] = dialogView.findViewById(R.id.nativeTemplateViewProgress);
                template[0].setStyles(styles);
                template[0].setNativeAd(unifiedNativeAd);
                dialogView.findViewById(R.id.nativeTemplateViewProgress).setVisibility(View.VISIBLE);
            }

        }).build();
         adLoader.loadAds(new AdRequest.Builder().build(),5);
        alertDialog.show();

        String[] command = {"-i", input,"-vcodec",  "libx264", "-crf", "27", "-filter:v", "scale="+Resolution+":-2", "-c:a", "copy", "-preset", "veryfast" ,output};
         for (int i =0;i<command.length;i++){
            Helpers.LogPrint(TAG,command[i]);
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
                Helpers.LogPrint(TAG,message);
                resolutiontext.setText("Resolution");
                Resolution="";
                isCompressDone="done";
                final Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                File outputs= new File(output);
                intent.setData(Uri.fromFile(outputs));
                Activity_compress.this.sendBroadcast(intent);
                ShowVideoOutputDetial(new File(output));
                alertDialog.dismiss();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onProgress(String message) {
                Helpers.LogPrint(TAG,message);
                float count = msec;
                isCompressDone="working";
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
                Helpers.LogPrint(TAG,message);
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
            showAlertError("Can't support this file choose another video and try again");
        }
    }

    public void showAlertError(String message){
        new AlertDialog.Builder(Activity_compress.this)
                .setTitle("Alert")
                .setMessage(message)


                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                                            }
                })

                 .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void shareVideo(final String title, String path) {
        MediaScannerConnection.scanFile(Activity_compress.this, new String[] { path },
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
        Helpers.LogPrint("addscount",""+fulladdcountcompressactivity);
        if (fulladdcountcompressactivity<1) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Activity_compress.this,ActivityVideoList.class);
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
                 outputsoption=outputs;
                video_location.setText("Click Here To See Output");
                try {
                    new MaterialShowcaseView.Builder(Activity_compress.this)
                            .setTarget(convert)
                            .setShape(new CircleShape())
                            .setDismissText("GOT IT")
                            .setContentText("Click Hear to See Final Result After Compresing Video")
                            .setDelay(1000)
                            .setFadeDuration(500)
                            .setDismissOnTouch(true)
                            .setMaskColour(Activity_compress.this.getResources().getColor(R.color.primary_dark))
                            .setContentTextColor(Activity_compress.this.getResources().getColor(R.color.yellow))
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
         }
    }

 }
