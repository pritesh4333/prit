package com.prit.videotomp3pro.View.MyFiles;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.prit.videotomp3pro.R;
import com.prit.videotomp3pro.Utils.Helper;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import static com.prit.videotomp3pro.View.MainActivity.MY_PREFS_NAME;

public class Mp3Files extends AppCompatActivity {
    static RecyclerView mp3file_recyclerview;
    static Mp3FileAdapter mAdapter;
    String admob_app_id,banner_home_footer,interstitial_full_screen;
    private AdView mAdView;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3_files);
        mp3file_recyclerview = (RecyclerView) findViewById(R.id.mp3file_recyclerview);
        ArrayList<String> filenames = new ArrayList<String>();
        ArrayList<String> filesize = new ArrayList<String>();
        ArrayList<String> filepath = new ArrayList<String>();

        File mypath = new File(Environment.getExternalStorageDirectory(), "Video To MP3");

        File[] files = mypath.listFiles();

        if (files != null) {
            Arrays.sort(files, new Comparator() {
                public int compare(Object o1, Object o2) {

                    if (((File) o1).lastModified() > ((File) o2).lastModified()) {
                        return -1;
                    } else if (((File) o1).lastModified() < ((File) o2).lastModified()) {
                        return +1;
                    } else {
                        return 0;
                    }
                }

            });
        }
        if (files != null) {
            for (int i = 0; i < files.length; i++) {

                String file_name = files[i].getName();
                String file_path = files[i].getPath();

                Helper.LogPrint("filesname", file_name);
                File size = new File(file_path);
                long sizes = size.length();
                double b = sizes;
                double k = sizes / 1024.0;
                double m = ((sizes / 1024.0) / 1024.0);
                double g = (((sizes / 1024.0) / 1024.0) / 1024.0);
                double t = ((((sizes / 1024.0) / 1024.0) / 1024.0) / 1024.0);

                DecimalFormat dec = new DecimalFormat("0.00");

                String hrSize;
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
                // you can store name to arraylist and use it later
                filenames.add(file_name);
                filesize.add(hrSize);
                filepath.add(file_path);
            }
            mAdapter = new Mp3FileAdapter(this, filenames, filesize, filepath);
            mp3file_recyclerview.setHasFixedSize(true);
            mp3file_recyclerview.setLayoutManager(new LinearLayoutManager(this));
            mp3file_recyclerview.setAdapter(mAdapter);
        }else{
            Toast.makeText(this,"No File.",Toast.LENGTH_SHORT).show();
        }
        mAdapter = new Mp3FileAdapter(this, filenames, filesize, filepath);
        mp3file_recyclerview.setHasFixedSize(true);
        mp3file_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mp3file_recyclerview.setAdapter(mAdapter);

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
        AdView mAdView = new AdView(this);
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
         }







    private void showInterstitial() {

            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
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
        mAdapter.notifyDataSetChanged();
    }
}
