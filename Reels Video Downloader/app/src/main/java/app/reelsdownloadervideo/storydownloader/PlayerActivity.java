package app.reelsdownloadervideo.storydownloader;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.view.MenuItem;
import android.view.WindowManager;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.ImageView;

import android.widget.Toast;
import android.widget.VideoView;
import android.text.format.DateUtils;
import android.text.format.Formatter;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.File;
import java.text.DecimalFormat;

import static app.reelsdownloadervideo.storydownloader.HomeActivity.MY_PREFS;


public class PlayerActivity extends AppCompatActivity {

     private Boolean sOutputVideoPlayingStatuss=false;
    private MediaController smediaControllers;
     String sScrrens;
    String sCompresss="";
    VideoView svv_videos;
    ImageView soutput_deletes;
    TextView smOutputInfoViews;
    TextView sresolutiontexts;
    TextView soutputtxts;
     ImageView soutput_shares;
    TextView svideo_locations;
    AdView myAdView;
    InterstitialAd mInterstitialAd;
    public static  int fulladdcount=0;
    String app_id,footer_add,fullscreen_add,native_add;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
         smOutputInfoViews = findViewById(R.id.soutput_infos);
        svv_videos = (VideoView) findViewById(R.id.svv_videos);
        soutput_deletes=(ImageView)findViewById(R.id.soutput_deletes);

        soutput_shares=findViewById(R.id.soutput_shares);

        svideo_locations=(TextView)findViewById(R.id.svideo_locations);
        sresolutiontexts=(TextView)findViewById(R.id.sresolutiontexts);


        final String str_videos = getIntent().getStringExtra("OutoutPath");
        sScrrens = getIntent().getStringExtra("Scrren");
        svv_videos.setVideoPath(str_videos);
        smediaControllers = new MediaController(this);

        svv_videos.setMediaController(smediaControllers);
        svv_videos.start();
        sOutputVideoPlayingStatuss=true;
         loadAds();


        soutput_deletes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog myQuittingDialogBoxs = new AlertDialog.Builder(PlayerActivity.this)

                         .setTitle("Delete")

                        .setMessage("Do you want to Delete")

                        .setIcon(R.drawable.dele)


                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                                try {

                                File fdelete = new File(str_videos);

                                    if(fdelete.exists()) {
                                        fdelete.delete();


                                    MediaScannerConnection.scanFile(PlayerActivity.this,
                                            new String[]{fdelete.toString()},
                                            new String[]{fdelete.getName()},null);
                                    dialog.dismiss();

                                    final String wheres = MediaStore.MediaColumns.DATA + "=?";
                                    final String[] selectionArgs = new String[] {
                                            fdelete.getAbsolutePath()
                                    };

                                    final ContentResolver contentResolver = PlayerActivity.this.getContentResolver();

                                    final Uri filesUri = MediaStore.Files.getContentUri("external");

                                    contentResolver.delete(filesUri, wheres, selectionArgs);

                                    if (fdelete.exists()) {

                                        contentResolver.delete(filesUri, wheres, selectionArgs);
                                    }

                                        System.out.println("file Deleted :" + str_videos);
                                        Toast.makeText(PlayerActivity.this, "File Deleted.", Toast.LENGTH_LONG).show();

                                        Intent i = new Intent(PlayerActivity.this, MyvideoList.class);
                                        startActivity(i);
                                        finish();

                                    } else {
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

                myQuittingDialogBoxs.show();


            }
        });

        svv_videos.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                sOutputVideoPlayingStatuss=false;

            }
        });
        soutput_shares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str_videos!=null) {
                    share("Share", "" + str_videos);
                }else{


                }
            }
        });
        ShowVideoOutputDetial(new File(str_videos));





    }
    private void loadAds() {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        app_id = prefs.getString("admob_app_id", "");
        if (app_id.equalsIgnoreCase("")){
            app_id=getString(R.string.app_id);
            footer_add=getString(R.string.footer);
            native_add=getString(R.string.native_add);
            fullscreen_add=getString(R.string.full_screen);
        }else {
            app_id = prefs.getString("app_id", "");
            footer_add = prefs.getString("footer_add", "");
            native_add=prefs.getString("native_add", "");
            fullscreen_add=prefs.getString("fullscreen_add", "");

        }
        MobileAds.initialize(this, app_id);
        LinearLayout adContainer = findViewById(R.id.banner);
        myAdView = new AdView(PlayerActivity.this);
        myAdView.setAdSize(AdSize.BANNER);
        myAdView.setAdUnitId(footer_add);
        adContainer.addView(myAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        myAdView.loadAd(adRequest);

        /// Initializing the Google Admob SDK  NATIVE
        MobileAds.initialize(PlayerActivity.this, new OnInitializationCompleteListener() {@Override
        public void onInitializationComplete(InitializationStatus initializationStatus) {

            //Showing a simple Toast Message to the user when The Google AdMob Sdk Initialization is Completed
            //  Toast.makeText(HomeActivity.this, "AdMob Sdk Initialize " + initializationStatus.toString(), Toast.LENGTH_LONG).show();
        }
        });

        // Show full page add
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(fullscreen_add);
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
    }
    private void showInterstitial() {
        if ( fulladdcount!=1) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (sScrrens!=null) {
            if (sScrrens.equalsIgnoreCase("MyVideo")) {
                Intent i = new Intent(PlayerActivity.this, MyvideoList.class);
                startActivity(i);
                finish();
            } else {

            }

        }else{

            Intent i = new Intent(PlayerActivity.this, PlayerActivity.class);
            startActivity(i);
            finish();
        }
    }
    @Override
    public void onDestroy() {
        if (myAdView != null) {
            myAdView.destroy();
        }
        super.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (myAdView != null) {
            myAdView.resume();
        }
    }
    @Override
    public void onPause() {
        if (myAdView != null) {
            myAdView.pause();
        }
        super.onPause();
    }
    public void share(final String title, String path) {

        MediaScannerConnection.scanFile(PlayerActivity.this, new String[] { path },

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

                double g = (((size/1024.0)/1024.0)/1024.0);
                double t = ((((size/1024.0)/1024.0)/1024.0)/1024.0);
                double k = size/1024.0;
                double m = ((size/1024.0)/1024.0);

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
                    svideo_locations.setVisibility(View.GONE);


                    return;
                }
                mediaMetadataRetriever.release();
                smOutputInfoViews.setText(getString(R.string.details_video_info, width, height,
                        DateUtils.formatElapsedTime(duration / 1000),
                        Formatter.formatShortFileSize(this, outputs.length())));

                svideo_locations.setText("File Location:-\n" + outputs);
                svideo_locations.setVisibility(View.VISIBLE);
            } catch (Exception ex) {


                File fdelete = new File(outputs.getPath());
                if (fdelete.exists()) {
                    if (fdelete.delete()) {
                        System.out.println("file Deleted :" + outputs.getPath());
                    } else {
                        System.out.println("file not Deleted :" + outputs.getPath());
                    }
                }
                svideo_locations.setVisibility(View.GONE);
            }
        }catch (Exception e){
         }
    }



}
