package app.reelsdownloadervideo.storydownloader;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.view.WindowManager;
import android.widget.Button;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.Toast;
import android.widget.VideoView;
import android.text.format.DateUtils;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;



import java.io.File;
import java.text.DecimalFormat;




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

                                        Intent i = new Intent(PlayerActivity.this, MyReelsList.class);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (sScrrens!=null) {
            if (sScrrens.equalsIgnoreCase("MyVideo")) {
                Intent i = new Intent(PlayerActivity.this, MyReelsList.class);
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
