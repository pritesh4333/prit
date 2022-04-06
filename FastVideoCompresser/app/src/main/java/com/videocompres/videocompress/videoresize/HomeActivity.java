package com.videocompres.videocompress.videoresize;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
 import java.util.ArrayList;
import nl.bravobit.ffmpeg.ExecuteBinaryResponseHandler;
import nl.bravobit.ffmpeg.FFmpeg;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.shape.CircleShape;


public class HomeActivity extends AppCompatActivity {
     private static final int MY_REQUEST_CODE = 1;
     private LinearLayout videlist,video_List;
    TextView more_apps;
    private AdView mAdView;
    private static Activity activity;
    public static final String MY_PREFS_NAME = "NewVideoCompressPrefsFile";
     boolean boolean_folder;
    private static final int REQUEST_PERMISSIONS = 100;
    private Boolean chekpermistion = false;
      AppUpdateManager appUpdateManager;
    private static String LOG_TAG = "EXAMPLE";
     @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
         videlist = (LinearLayout) findViewById(R.id.videlist);
        video_List = (LinearLayout) findViewById(R.id.video_List);
         activity=this;

        MobileAds.initialize(this, getString(R.string.admob_app_id));
        LinearLayout adContainer = findViewById(R.id.banneradd);
          mAdView = new AdView(HomeActivity.this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId( getString(R.string.banner_home_footer));
        adContainer.addView(mAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        appUpdateManager = AppUpdateManagerFactory.create(HomeActivity.this);
        videlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (chekpermistion) {

                    Intent ii = new Intent(HomeActivity.this, ActivityVideoList.class);
                     startActivity(ii);
                } else {
                    ActivityCompat.requestPermissions(HomeActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSIONS);
                }

            }
        });
        video_List.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (chekpermistion) {
                    Intent ii = new Intent(HomeActivity.this,  AcitivityMyvideolist.class);
                     startActivity(ii);
                } else {
                    ActivityCompat.requestPermissions(HomeActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSIONS);
                }

            }
        });

         chekpermisstion();
        if (Internetconn.checkConnection(this)) {
     checkforUpdate();
        } else {

            Helpers.LogPrint("No Internet","No internet");
        }
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("Showcase", "Videocompress");
        editor.apply();
             }


    private void checkforUpdate() {

        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                     && appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)) {
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
                Helpers.LogPrint("update Method","No update");
            }
        });
    }
    public void chekpermisstion() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
                chekpermistion = false;

            }
        } else {
            chekpermistion = true;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        chekpermistion = true;
                    } else {
                        chekpermistion = false;
                        Toast.makeText(HomeActivity.this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.alerts, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        TextView rateus=(TextView)dialogView.findViewById(R.id.rateus_btn);
        TextView no=(TextView)dialogView.findViewById(R.id.no_btn);
        TextView yes=(TextView)dialogView.findViewById(R.id.yes_btn);
        final TemplateView[] template = new TemplateView[1];

         AdLoader adLoader = new AdLoader.Builder(HomeActivity.this, getString(R.string.natice_advanceadd)).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

            private ColorDrawable background;@Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                NativeTemplateStyle styles = new
                        NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                template[0] = dialogView.findViewById(R.id.nativeTemplateView);
                template[0].setStyles(styles);
                template[0].setNativeAd(unifiedNativeAd);
                dialogView.findViewById(R.id.nativeTemplateView).setVisibility(View.VISIBLE);

            }
        }).build();
         adLoader.loadAds(new AdRequest.Builder().build(),5);
        rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                activity.finish();
            }
        });
        alertDialog.show();

    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {

                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
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
                                Helpers.LogPrint("No update On Resume","No update");
                            }
                        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Helpers.LogPrint("Update flow failed! Result code: ", "" + resultCode);

            }else{
                Helpers.LogPrint("Update Done: ", "" + resultCode);
            }
        }
    }
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dropdownmenu, menu);

        MenuItem item = menu.findItem(R.id.mShare);
        MenuItem feedback = menu.findItem(R.id.feedback);
//        MenuItem showcase=menu.findItem(R.id.showcase);
        MenuItem about=menu.findItem(R.id.about);
//        showcase.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
////            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
////            editor.putString("Showcase", "");
////            editor.apply();
//                 return false;
//            }
//        });
        about.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                LayoutInflater factory = LayoutInflater.from(HomeActivity.this);
                final View deleteDialogView = factory.inflate(R.layout.feedback, null);

                final AlertDialog deleteDialog = new AlertDialog.Builder(HomeActivity.this).create();
                deleteDialog.setView(deleteDialogView);



                Button feedbacksubmit = (Button) deleteDialogView.findViewById(R.id.feedbacksubmit);
                  TextView aboutinfo=(TextView) deleteDialogView.findViewById(R.id.aboutinfo);
                  TextView header=(TextView) deleteDialogView.findViewById(R.id.header);
                header.setText("About");

                final TemplateView[] template = new TemplateView[1];

                 AdLoader adLoader = new AdLoader.Builder(HomeActivity.this, getString(R.string.natice_advanceadd)).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

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
                feedbacksubmit.setVisibility(View.GONE);
                aboutinfo.setVisibility(View.VISIBLE);
                aboutinfo.setText("Version:-  "+ BuildConfig.VERSION_NAME);
                deleteDialog.show();
                return false;
            }
        });
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Fast Video Compressor");
                    String shareMessage = "\nLet me recommend you this application\n\n You can easily compress video without lossing quality and share in any other app you want.\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }

                return false;
            }
        });
        feedback.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                LayoutInflater factory = LayoutInflater.from(HomeActivity.this);
                final View deleteDialogView = factory.inflate(R.layout.feedback, null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(HomeActivity.this).create();

                deleteDialog.setView(deleteDialogView);



                Button feedbacksubmit = (Button) deleteDialogView.findViewById(R.id.feedbacksubmit);
                TextView header=(TextView) deleteDialogView.findViewById(R.id.header);
                header.setText("Feedback");

                final TemplateView[] template = new TemplateView[1];

                //Initializing the AdLoader   objects
                AdLoader adLoader = new AdLoader.Builder(HomeActivity.this, getString(R.string.natice_advanceadd)).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

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
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

}





