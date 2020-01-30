package com.prit.videocompressorpro.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;

import com.crashlytics.android.Crashlytics;

import com.prit.videocompressorpro.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.prit.videocompressorpro.Utils.Helper;
//import com.vincent.videocompressor.VideoCompress;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import nl.bravobit.ffmpeg.ExecuteBinaryResponseHandler;
import nl.bravobit.ffmpeg.FFmpeg;
import nl.bravobit.ffmpeg.FFtask;

public class CompressorActivity extends Activity {

    VideoView vv_video;
    ImageView  mOutputOptionsButton;
    TextView mInputInfoView;
    TextView mOutputInfoView;
    TextView resolutiontext;
    TextView play_txt;
    TextView outputtxt;
    ImageView output_play;
    ImageView play_img;
    ImageView input_options;
    ImageView output_share;
    Button video_location;
    Button convert;
    private static final String TAG = "video-Compress & Edit";
    private String str_video;

    public File mInputFile; // = new File("/storage/emulated/0/Android/data/com.dstukalov.videoconverter/files/Temp/tmp.mp4");
    private File outputs=null;
    private File outputsoption=null;
    public long mTimeFrom;
    public long mTimeTo;
    private int mWidth;
    private int mHeight;
    private int msize;
    private String Resolution="";
    public String outputPath="";
    ProgressDialog progressDialog;

    InterstitialAd mInterstitialAd;
    AdRequest adRequest;
    private AdView mAdView;
    private  Boolean InputVideoPlayingStatus=false,OutputVideoPlayingStatus=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_galleryview);

        init();
    }

    private void init() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        vv_video = (VideoView) findViewById(R.id.vv_video);
        mOutputOptionsButton=(ImageView)findViewById(R.id.output_options);
        mInputInfoView = findViewById(R.id.input_info);
        mOutputInfoView = findViewById(R.id.output_info);
        output_play=findViewById(R.id.output_play);
        play_img=findViewById(R.id.play_img);
        input_options=findViewById(R.id.input_options);
        output_share=findViewById(R.id.output_share);
        convert=(Button)findViewById(R.id.convert);
        video_location=(Button)findViewById(R.id.video_location);
        resolutiontext=(TextView)findViewById(R.id.resolutiontext);
        play_txt=(TextView)findViewById(R.id.play_txt);
        outputtxt=(TextView)findViewById(R.id.outputtxt);

        convert.setVisibility(View.VISIBLE);


        //FFmpeg Instance
        if (FFmpeg.getInstance(this).isSupported()) {
            // ffmpeg is supported
            versionFFmpeg();
            //ffmpegTestTaskQuit();
        } else {
            // ffmpeg is not supported
            Helper.LogPrint(TAG,"ffmpeg not supported!");
        }

        str_video = getIntent().getStringExtra("video");
        vv_video.setVideoPath(str_video);
        vv_video.start();

        InputVideoPlayingStatus=true;




        // show add on banner
        mAdView = findViewById(R.id.adView);
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

        play_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if (InputVideoPlayingStatus==false){
                    InputVideoPlayingStatus=true;
                    vv_video.start();
                    play_txt.setText("Playing");
                    play_img.setBackground(getResources().getDrawable(R.drawable.ic_pause));
                }else{
                    InputVideoPlayingStatus=false;
                    vv_video.pause();
                    play_txt.setText("Stop");
                    play_img.setBackground(getResources().getDrawable(R.drawable.ic_play));
                }

            }
        });
        vv_video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                InputVideoPlayingStatus=false;
                OutputVideoPlayingStatus=false;
                play_txt.setText("Play");
                play_img.setBackground(getResources().getDrawable(R.drawable.ic_play));
                outputtxt.setText("Play");
                output_play.setBackground(getResources().getDrawable(R.drawable.ic_play));
            }
        });

        output_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (OutputVideoPlayingStatus==false) {
                    if (outputsoption != null) {
                        vv_video.setVideoPath("" + outputsoption);
                        vv_video.start();
                        OutputVideoPlayingStatus=true;
                        outputtxt.setText("Playing");
                        output_play.setBackground(getResources().getDrawable(R.drawable.ic_pause));

                    } else {
                        showAlertError("First Compress Video To Play Output File");
                    }
                }else{
                    OutputVideoPlayingStatus=false;
                    vv_video.pause();
                    outputtxt.setText("Stop");
                    output_play.setBackground(getResources().getDrawable(R.drawable.ic_play));
                }
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
                // VideoConver(str_video);
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
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMax(msec);
            dialog.setMessage("Progress");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setProgress(0);
            dialog.setCancelable(false);


            //String[] command = {"-i",selectedVideoPath, "-preset", "fast", selectedVideoPath+timeStamp};
            //String[] command = {"-i", "/storage/emulated/0/DCIM/Camera/VID_20191216_161925.mp4", "-vcodec", "libx264", "-crf", "20", "/storage/emulated/0/DCIM/Camera/output.mp4"};
            //String[] command = {"-i", selectedVideoPath, "-vf", "scale="+scalel.getText().toString(), "-preset", "fast", selectedVideoPath+timeStamp+".mp4"};
            //String[] command = {"-i", "/storage/emulated/0/DCIM/Camera/VID_20191216_161925.mp4", "-vf", "scale=480:320,setdar=4:3", "/storage/emulated/0/DCIM/Camera/output.mp4"};
            //String[] command = {"-i", selectedVideoPath, "-filter:v", "scale="+scalel.getText().toString()+":-1", "-preset", "fast", selectedVideoPath+timeStamp+".mp4"};
            String[] command = {"-i", input, "-filter:v", "scale="+Resolution+":-2", "-c:a", "copy", "-preset", "fast" ,output};
            for (int i =0;i<command.length;i++){
                Helper.LogPrint(TAG,command[i]);
            }
            final FFtask task = FFmpeg.getInstance(this).execute(command, new ExecuteBinaryResponseHandler() {
                @Override
                public void onStart() {


                    dialog.show();

                }

                @Override
                public void onFinish() {

                    dialog.dismiss();



                }

                @Override
                public void onSuccess(String message) {
                    Helper.LogPrint(TAG,message);
                    resolutiontext.setText("Resolution");
                    Resolution="";
                    final Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    File  outputs= new File(output);
                    intent.setData(Uri.fromFile(outputs));
                    CompressorActivity.this.sendBroadcast(intent);
                    ShowVideoOutputDetial(new File(output));
                    dialog.dismiss();

                }

                @Override
                public void onProgress(String message) {

                    Helper.LogPrint(TAG,message);

                    int start = message.indexOf("time=");
                    int end = message.indexOf(" bitrate");
                    if (start != -1 && end != -1) {
                        String duration = message.substring(start + 5, end);
                        if (duration != "") {
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                                dialog.setProgress((int)sdf.parse("1970-01-01 " + duration).getTime());
                            }catch (ParseException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }

                }

                @Override
                public void onFailure(String message) {
                    Helper.LogPrint(TAG,message);
                    dialog.dismiss();
                    showAlertError("Compress Fail Try Again");

                }
            });

//        FFmpeg ffmpeg = FFmpeg.getInstance(ExampleActivity.this);
//        // to execute "ffmpeg -version" command you just need to pass "-version"
//
//        ffmpeg.execute(command, new ExecuteBinaryResponseHandler() {
//
//            @Override
//            public void onStart() {    status.setText("Start");}
//
//            @Override
//            public void onProgress(String message) {    status.setText("progress"+message);}
//
//            @Override
//            public void onFailure(String message) {    status.setText("fail"+message);}
//
//            @Override
//            public void onSuccess(String message) {    status.setText("Sucess"+message);}
//
//            @Override
//            public void onFinish() {
//                status.setText("finish");
//            }
//
//        });





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
        } catch (Exception  e) {
            //Toast.makeText(getBaseContext(), R.string.bad_video, Toast.LENGTH_SHORT).show();
            Crashlytics.log("Line no 384"+e);
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

    private void onOutputOptions() {
        final PopupMenu popup = new PopupMenu(this, mOutputOptionsButton);
        popup.getMenuInflater().inflate(R.menu.output_options, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.quality_186p:
                        Resolution="186";
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
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(CompressorActivity.this, VideoListActivity.class);
        startActivity(i);
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
                    Formatter.formatShortFileSize(this, str_video.length()))+" "+hrSize);
                output_play.setEnabled(true);
                outputsoption=outputs;
                video_location.setText("Click Here To See Output");

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
            Crashlytics.log("Show Detial"+e);
        }
    }
}
