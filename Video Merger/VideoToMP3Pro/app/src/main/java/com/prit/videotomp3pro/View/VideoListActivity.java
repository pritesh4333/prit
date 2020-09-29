package com.prit.videotomp3pro.View;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.prit.videotomp3pro.Model.Model_Video;
import com.prit.videotomp3pro.R;
import com.prit.videotomp3pro.Utils.FileUtil;
import com.prit.videotomp3pro.Utils.Helper;
import com.prit.videotomp3pro.Utils.InternetConnection;
import com.prit.videotomp3pro.ViewModel.Adapter_Video;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.shape.CircleShape;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;
import static com.prit.videotomp3pro.View.MainActivity.MY_PREFS_NAME;
import static com.prit.videotomp3pro.ViewModel.Adapter_Video.isAnySelectec;

//import static com.prit.videotomp3pro.ViewModel.Adapter_Video.ViewHolder.select_video;


public class VideoListActivity extends AppCompatActivity {

    Adapter_Video obj_adapter;
    private static final int PICK_VIDEO_REQUEST = 2;
    List<Model_Video> al_video = new ArrayList<>();
    GridView recyclerView;
    private AdView mAdView;
    InterstitialAd mInterstitialAd;
    private static Activity videoactivity;
    static String result;
    private static final int MY_REQUEST_CODE = 1;
    AppUpdateManager appUpdateManager;
    private static final int REQUEST_PERMISSIONS = 100;
    public static MenuItem item,gallery,menu_share;
    public static  int fulladdcount=0;
    //RequestQueue queue;
    private ActionMode mActionMode;
    String admob_app_id,banner_home_footer,interstitial_full_screen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videofolder);
        videoactivity=this;

        appUpdateManager = AppUpdateManagerFactory.create(VideoListActivity.this);
        init();



    }

    private void init(){

        recyclerView = (GridView) findViewById(R.id.recycler_view1);



        fn_checkpermission();

        // show add on banner
//        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
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
        Helper.LogPrint("admob_app_id",admob_app_id);
        Helper.LogPrint("interstitial_full_screen",interstitial_full_screen);

        LinearLayout adContainer = findViewById(R.id.videlist_adview);
        AdView mAdView = new AdView(VideoListActivity.this);
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
        if (InternetConnection.checkConnection(this)) {
            // Its Available...



            checkforUpdate();
        } else {
            // Not Available...
            Helper.LogPrint("No Internet","No internet");
        }
        //showShowcase();

    }
    public  void showShowcase(){
    SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
    String Showcase = prefs.getString("Showcase", "");

        if (Showcase.equalsIgnoreCase("")){

            View menuitem= findViewById(R.id.menu_gallery);
            new MaterialShowcaseView.Builder(this)
                .setTarget( menuitem)
                .setShape(new CircleShape())
                .setDismissText("GOT IT")
                .setContentText("Select Video You Want MP3")
                .setDelay(1000)
                .setFadeDuration(500)
                    .setMaskColour(getResources().getColor(R.color.primary_dark))
                    .setContentTextColor(getResources().getColor(R.color.accent))
                // optional but starting animations immediately in onCreate can make them choppy
                // .singleUse("Videocompress") // provide a unique ID used to ensure it is only shown once
                .show();

//            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//            editor.putString("Showcase", "Videocompress");
//            editor.apply();
    }else {
    }
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void checkforUpdate() {

        // Creates instance of the manager.


// Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // For a flexible update, use AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)) {
                // Request the update.
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                            appUpdateInfo,
                            // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                            IMMEDIATE,
                            // The current activity making the update request.
                            this,
                            // Include a request code to later monitor this update request.
                            MY_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }else{
                Helper.LogPrint("update Method","No update");
            }
        });


    }

    private void fn_checkpermission(){
        /*RUN TIME PERMISSIONS*/

        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(VideoListActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(VideoListActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(VideoListActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        }else {
            Helper.LogPrint("Else","Else");
            fn_video();
        }
    }



    private void showInterstitial() {
        if ( fulladdcount!=1) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }
    }
    public void fn_video() {
        long duration;
        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name,column_id,thum,size;

        String absolutePathOfImage = null;
        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME, MediaStore.Video.Media._ID, MediaStore.Video.Thumbnails.DATA};

        final String orderBy = MediaStore.Images.Media.DATE_MODIFIED;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
        column_id = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
        thum = cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA);
        //duration=cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);


//        File outputs = new File(cursor.getString(thum));
//        Cursor cursors = MediaStore.Video.query( getApplicationContext().getContentResolver(),  Uri.fromFile(outputs), new
//                String[]{MediaStore.Video.VideoColumns.DURATION});
//
//
//        long durations = 0;
//        if (cursors != null && cursors.moveToFirst()) {
//            durations = cursors.getLong(cursors.getColumnIndex(MediaStore.Video
//                    .VideoColumns.DURATION));
//            Helper.LogPrint("duration", ""+durations);
//            cursors.close();
//        }

        while (cursor.moveToNext()) {
            long l=0;
            String hrSize = "";
            absolutePathOfImage = cursor.getString(column_index_data);
//            Helper.LogPrint("Column", absolutePathOfImage);
//            Helper.LogPrint("Folder", cursor.getString(column_index_folder_name));
//            Helper.LogPrint("column_id", cursor.getString(column_id));
                Helper.LogPrint("thum", cursor.getString(thum));



                //Helper.LogPrint("duration", ""+timeString);
//



//            try {
//                final MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
//                mediaMetadataRetriever.setDataSource(cursor.getString(thum));
//                l = Long.parseLong(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
//                //Helper.LogPrint("duration", ""+durations);
//                mediaMetadataRetriever.release();
//            }catch ( Exception e){
//
//            }


            File output = new File(cursor.getString(thum));
            long sizes = output.length();




            double b = sizes;
            double k = sizes / 1024.0;
            double m = ((sizes / 1024.0) / 1024.0);
            double g = (((sizes / 1024.0) / 1024.0) / 1024.0);
            double t = ((((sizes / 1024.0) / 1024.0) / 1024.0) / 1024.0);

            DecimalFormat dec = new DecimalFormat("0.00");

            if (t > 1) {
                hrSize = dec.format(t).concat("TB");
            } else if (g > 1) {
                hrSize = dec.format(g).concat("GB");
            } else if (m > 1) {
                hrSize = dec.format(m).concat("MB");
            } else if (k > 1) {
                hrSize = dec.format(k).concat("KB");
            } else {
                hrSize = dec.format(b).concat("Bytes");
            }
            //Helper.LogPrint("Size", ""+hrSize);

            Model_Video obj_model = new Model_Video();

            obj_model.setStr_path(absolutePathOfImage);
            obj_model.setStr_thumb(cursor.getString(thum));
            obj_model.setSize(hrSize);
          //  if (l!=0) {
            //    obj_model.setDuration(String.valueOf(l));
            //}else{
                obj_model.setDuration("");
            //}
            obj_model.setSelected(false);
            al_video.add(obj_model);

        }


        obj_adapter = new Adapter_Video(VideoListActivity.this,R.layout.adapter_videos,al_video);

        recyclerView.setAdapter( obj_adapter);
//        recyclerView.setOnItemClickListener(VideoListActivity.this);
//        recyclerView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//
//            public boolean onItemLongClick(AdapterView<?> parent,
//                                           View view, int position, long id) {
//                Model_Video model_video= (Model_Video) al_video.get(position);
//                model_video.setSelected(true);
//
//
//                if (al_video.get(position).getSelected()){
//                    view.findViewById(R.id.select_video).setVisibility(View.VISIBLE);
//
//                    item.setVisible(true);
//
//
//
//                }else{
//                    view.findViewById(R.id.select_video).setVisibility(View.VISIBLE);
//
//                    item.setVisible(false);
//                }
//                return true;
//            }
//        });
        // obj_adapter.setClickLisner(this);





    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        fn_video();
                    } else {
                        Toast.makeText(VideoListActivity.this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Helper.LogPrint("Update flow failed! Result code: ", "" + resultCode);
                // If the update is cancelled or fails,
                // you can request to start the update again.
            }else{
                Helper.LogPrint("Update Done: ", "" + resultCode);
            }
        }else if (requestCode==PICK_VIDEO_REQUEST){

            if (data == null) {
                //Toast.makeText(this, "Faild to open", Toast.LENGTH_SHORT).show();
                return;
            }else{

                try {
                    File actualvideo = FileUtil.from(VideoListActivity.this,data.getData());
                    Intent intent_gallery = new Intent(VideoListActivity.this, CompressorActivity.class);
                    intent_gallery.putExtra("video", actualvideo.toString());
                    startActivity(intent_gallery);
                    finish();

                }catch (Exception e) {
                    Helper.LogPrint("gallery file faildto open", "" + e);
                }
            }


        }else {
            Helper.LogPrint("else", "else");
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
// Checks that the update is not stalled during 'onResume()'.
// However, you should execute this check at all entry points into the app.
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {

                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.
                                try {
                                    appUpdateManager.startUpdateFlowForResult(
                                            appUpdateInfo,
                                            IMMEDIATE,
                                            this,
                                            MY_REQUEST_CODE);
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                Helper.LogPrint("No update On Resume","No update");
                            }
                        });

    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);

        item = menu.findItem(R.id.menu_delete);
          gallery = menu.findItem(R.id.menu_gallery);
        menu_share=menu.findItem(R.id.menu_share);

        menu_share.setVisible(false);
        item.setVisible(false);

        menu_share.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String path="";




                for (int i=0;i<al_video.size();i++){
                    if (al_video.get(i).getSelected()) {
                      path=al_video.get(i).getStr_path();
                    break;
                    }
                }
                MediaScannerConnection.scanFile(VideoListActivity.this, new String[] { path },

                        null, new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
                                Intent shareIntent = new Intent(
                                        Intent.ACTION_SEND);
                                shareIntent.setType("video/*");
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
        gallery.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
                startActivityForResult(intent, PICK_VIDEO_REQUEST);
                return false;
            }
        });


        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(VideoListActivity.this)
                        // set message, title, and icon
                        .setTitle("Delete")
                        .setMessage("Do you want to Delete")
                        .setIcon(R.drawable.ic_delete)

                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                //your deleting code
                                for (int i = 0; i < al_video.size(); i++) {
                                    if (al_video.get(i).getSelected()) {

                                        isAnySelectec = "";
                                        item.setVisible(false);
                                        al_video.get(i).setSelected(false);

                                        String path = al_video.get(i).getStr_path();

                                        try {
                                            File fdelete = new File(path);
                                            if (fdelete.exists()) {
                                                final String where = MediaStore.MediaColumns.DATA + "=?";
                                                final String[] selectionArgs = new String[] {
                                                        fdelete.getAbsolutePath()
                                                };
                                                final ContentResolver contentResolver = VideoListActivity.this.getContentResolver();
                                                final Uri filesUri = MediaStore.Files.getContentUri("external");

                                                contentResolver.delete(filesUri, where, selectionArgs);

                                                if (fdelete.exists()) {

                                                    contentResolver.delete(filesUri, where, selectionArgs);
                                                }
                                                dialog.dismiss();
                                                System.out.println("file Deleted :" + path);

                                            } else {
                                                dialog.dismiss();
                                                Toast.makeText(VideoListActivity.this,"File Not Found.", Toast.LENGTH_LONG).show();
                                                System.out.println("file not Found :" + path);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            dialog.dismiss();
                                        }
                                    }

                                }

                                Toast.makeText(VideoListActivity.this,"Delete", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(VideoListActivity.this, VideoListActivity.class);
                                startActivity(i);
                                finish();



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


    public void onBackPressed() {
        super.onBackPressed();
        if (isAnySelectec.equalsIgnoreCase("true")) {
            for (int i = 0; i < al_video.size(); i++) {
                if (al_video.get(i).getSelected()) {
                    isAnySelectec = "";
                    item.setVisible(false);
                    al_video.get(i).setSelected(false);

                }
            }
        }
    }




}