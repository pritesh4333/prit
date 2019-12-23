package com.prit.videocompressorpro.View;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.text.format.DateUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;

import com.dstukalov.videoconverter.BadVideoException;
import com.dstukalov.videoconverter.MediaConverter;
import com.prit.videocompressorpro.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.prit.videocompressorpro.Utils.Helper;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CompressorActivity extends Activity {

    VideoView vv_video;
    ImageView  mOutputOptionsButton;
    TextView mInputInfoView;
    TextView mOutputInfoView;
    ImageView output_play;
    ImageView input_play;
    ImageView input_options;
    ImageView output_share;
    TextView video_location;
    Button convert;
    private static final String TAG = "video-Compress & Edit";
    private String str_video;
    private ConversionTask mConversionTask;
    private ConversionParameters mConversionParameters = CONV_PARAMS_360P;
    private static final ConversionParameters CONV_PARAMS_240P = new ConversionParameters(240, MediaConverter.VIDEO_CODEC_H264, 1333000, 64000);
    private static final ConversionParameters CONV_PARAMS_360P = new ConversionParameters(360, MediaConverter.VIDEO_CODEC_H264, 2000000, 96000);
    private static final ConversionParameters CONV_PARAMS_480P = new ConversionParameters(480, MediaConverter.VIDEO_CODEC_H264, 2666000, 128000);
    private static final ConversionParameters CONV_PARAMS_720P = new ConversionParameters(720, MediaConverter.VIDEO_CODEC_H264,  4000000, 192000);
    private static final ConversionParameters CONV_PARAMS_720P_H265 = new ConversionParameters(720, MediaConverter.VIDEO_CODEC_H265,  2000000, 192000);
    private static final ConversionParameters CONV_PARAMS_1080P = new ConversionParameters(1080, MediaConverter.VIDEO_CODEC_H264, 6000000, 192000);
    private static final ConversionParameters CONV_PARAMS_1080P_H265 = new ConversionParameters(720, MediaConverter.VIDEO_CODEC_H265,  3000000, 192000);
    public File mInputFile; // = new File("/storage/emulated/0/Android/data/com.dstukalov.videoconverter/files/Temp/tmp.mp4");
    private File outputs=null;
    public long mTimeFrom;
    public long mTimeTo;
    private int mWidth;
    private int mHeight;

    ProgressDialog progressDialog;
    MediaConverter mConverter;
    InterstitialAd mInterstitialAd;
    AdRequest adRequest;
    private AdView mAdView;
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
        input_play=findViewById(R.id.input_play);
        input_options=findViewById(R.id.input_options);
        output_share=findViewById(R.id.output_share);
        convert=(Button)findViewById(R.id.convert);
        video_location=(TextView)findViewById(R.id.video_location);




        str_video = getIntent().getStringExtra("video");
        vv_video.setVideoPath(str_video);
        vv_video.start();




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
                if (outputs!=null) {
                    shareVideo("Share", "" + outputs);
                }else{
                    Toast.makeText(getBaseContext(), "Compress video to Share", Toast.LENGTH_SHORT).show();
                }
            }
        });

        input_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vv_video.setVideoPath(str_video);
                vv_video.start();
            }
        });

        output_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (outputs!=null) {
                    vv_video.setVideoPath("" + outputs);
                    vv_video.start();
                }else{
                    Toast.makeText(getBaseContext(), "Compress video to Play", Toast.LENGTH_SHORT).show();
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
                VideoConver(str_video);
            }
        });



        ShowVideoDetial();





    }

    private void ShowVideoDetial() {
        final MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();

        try {
            mediaMetadataRetriever.setDataSource(str_video);
        } catch (Exception ex) {
            Toast.makeText(getBaseContext(), R.string.bad_video, Toast.LENGTH_SHORT).show();
            mediaMetadataRetriever.release();
            str_video = null;

            return;
        }

        final String width = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
        final String height = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);

        long duration;
        try {
            duration = Long.parseLong(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));

            mWidth = Integer.parseInt(width);
            mHeight = Integer.parseInt(height);
        } catch (NumberFormatException e) {
            Toast.makeText(getBaseContext(), R.string.bad_video, Toast.LENGTH_SHORT).show();
            mediaMetadataRetriever.release();
            str_video = null;

            return;
        }
        mediaMetadataRetriever.release();
        mInputInfoView.setText(getString(R.string.video_info, width, height,
                DateUtils.formatElapsedTime(duration / 1000),
                Formatter.formatShortFileSize(this, str_video.length())));

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
    private void VideoConver(String str_path) {
        File mypath=null;
        try {


            try {
                mypath = new File(Environment.getExternalStorageDirectory(), "Video Compressor Pro");

                if (!mypath.exists()) {
                    if (!mypath.mkdirs()) {
                        Helper.LogPrint("App", "failed to create directory");
                    }
                }
            }catch(Exception e){}
            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);

            String output=mypath+"/VID_COMPRESSOR_PRO_" + dateFormat.format(new Date()) + ".mp4";
            outputs=new File(output);
        }catch(Exception e ){

        }
        Toast.makeText(this, "Compressing Progress Please Wait", Toast.LENGTH_SHORT).show();





        try {
            File strpath= new File(str_path);

            mConversionTask = new ConversionTask(strpath, outputs, 0, 0, mConversionParameters);
            mConversionTask.execute();
        } catch (FileNotFoundException e) {
            mConversionTask = null;
            Toast.makeText(this, "Compressor Failed Try Again", Toast.LENGTH_LONG).show();
        }

    }
    private void onOutputOptions() {
        final PopupMenu popup = new PopupMenu(this, mOutputOptionsButton);
        popup.getMenuInflater().inflate(R.menu.output_options, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.quality_240p:
                    mConversionParameters = CONV_PARAMS_240P;
                    break;
                case R.id.quality_360p:
                    mConversionParameters = CONV_PARAMS_360P;
                    break;
                case R.id.quality_480p:
                    mConversionParameters = CONV_PARAMS_480P;
                    break;
                case R.id.quality_720p:
                    mConversionParameters = CONV_PARAMS_720P;
                    break;
                case R.id.quality_720p_h265:
                    mConversionParameters = CONV_PARAMS_720P_H265;
                    break;
                case R.id.quality_1080p:
                    mConversionParameters = CONV_PARAMS_1080P;
                    break;
                case R.id.quality_1080p_h265:
                    mConversionParameters = CONV_PARAMS_1080P_H265;
                    break;
            }
            estimateOutput();
            return true;
        });

        popup.show();
    }
    private void estimateOutput() {
        int dstWidth;
        int dstHeight;
        if (mWidth <= mHeight) {
            dstWidth = mConversionParameters.mVideoResolution;
            dstHeight = mHeight * dstWidth / mWidth;
            dstHeight = dstHeight & ~3;
        } else {
            dstHeight = mConversionParameters.mVideoResolution;
            dstWidth = mWidth * dstHeight / mHeight;
            dstWidth = dstWidth & ~3;
        }
        final long duration = (mTimeTo - mTimeFrom) / 1000;
        final long estimatedSize = (mConversionParameters.mVideoBitrate + mConversionParameters.mAudioBitrate) * duration / 8;

        mOutputInfoView.setText(getString(R.string.video_info_output, dstWidth, dstHeight, DateUtils.formatElapsedTime(duration), Formatter.formatShortFileSize(this, estimatedSize)));
    }
    private static class ConversionParameters {
        final int mVideoResolution;
        final @MediaConverter.VideoCodec String mVideoCodec;
        final int mVideoBitrate;
        final int mAudioBitrate;

        ConversionParameters(final int videoResolution, final @MediaConverter.VideoCodec String videoCodec, final int videoBitrate, final int audioBitrate) {
            mVideoResolution = videoResolution;
            mVideoCodec = videoCodec;
            mVideoBitrate = videoBitrate;
            mAudioBitrate = audioBitrate;
        }
    }
    private class ConversionTask extends AsyncTask<Void, Integer, Boolean> {





        ConversionTask(final @NonNull File input, final @NonNull File output, final long timeFrom, final long timeTo, final @NonNull ConversionParameters conversionParameters) throws FileNotFoundException {

            mConverter = new MediaConverter();
            mConverter.setInput(input);
            mConverter.setOutput(output);
            mConverter.setTimeRange(timeFrom, timeTo);
            mConverter.setVideoResolution(conversionParameters.mVideoResolution);
            mConverter.setVideoCodec(conversionParameters.mVideoCodec);
            mConverter.setVideoBitrate(conversionParameters.mVideoBitrate);
            mConverter.setAudioBitrate(conversionParameters.mAudioBitrate);

            mConverter.setListener(percent -> {
                publishProgress(percent);
                return isCancelled();
            });

        }

        @Override
        protected Boolean doInBackground(Void... params) {


            try {

                mConverter.convert();
            }  catch (BadVideoException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return Boolean.TRUE;
        }

        protected void onProgressUpdate(Integer... values) {


            progressDialog.setProgress(values[0]);

        }

        protected void onPreExecute() {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());

            progressDialog = new ProgressDialog(CompressorActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setTitle("Video Compressor Pro");
            progressDialog.setMessage("Compressing Please Wait");
            progressDialog.setMax(100);
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        protected void onCancelled() {
            mConversionTask = null;

            Helper.LogPrint("cancelAsync","cancel");

        }

        protected void onPostExecute(final Boolean result) {
            mConversionTask = null;
            progressDialog.dismiss();
            if (Boolean.TRUE.equals(result)) {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                final Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(outputs));
                CompressorActivity.this.sendBroadcast(intent);

                final MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();

                mediaMetadataRetriever.setDataSource(outputs.getAbsolutePath());
                mediaMetadataRetriever.release();
                output_play.setEnabled(true);
                video_location.setText("Output:-\n"+outputs);
                Toast.makeText(CompressorActivity.this, "Compressing Done", Toast.LENGTH_LONG).show();


            } else {
                Toast.makeText(CompressorActivity.this, "Compressing failed", Toast.LENGTH_LONG).show();
            }

        }
    }
    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    @Override
    public void onBackPressed() {
        Helper.LogPrint("Backpress","cancel");
        if (mConversionTask!=null) {
            mConverter=null;
            mConversionTask=null;
        }
        super.onBackPressed();
    }


}
