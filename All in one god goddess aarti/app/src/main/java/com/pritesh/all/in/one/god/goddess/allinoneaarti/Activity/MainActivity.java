package com.pritesh.all.in.one.god.goddess.allinoneaarti.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.pritesh.all.in.one.god.goddess.allinoneaarti.Adapter.GodName_Adapter;
import com.pritesh.all.in.one.god.goddess.allinoneaarti.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    public static int[] imgs = { R.drawable.ganpati, R.drawable.ramchandra, R.drawable.vishnu, R.drawable.hanuman
            , R.drawable.shivji, R.drawable.laxmi, R.drawable.ambe, R.drawable.krishna, R.drawable.saibaba,
            R.drawable.swaminarayn, R.drawable.gayatri, R.drawable.kalima, R.drawable.sarasvati
            , R.drawable.santoshi, R.drawable.ganpati};
    public  static String[] godnameslist;
    public static final String MY_PREFS_NAME = "AllInOneArti";
    AppUpdateManager appUpdateManager;
    private static final int MY_REQUEST_CODE = 1;
    private static String LOG_TAG = "EXAMPLE";
    String admob_app_id,banner_home_footer,interstitial_full_screen;
    AdView mAdView;
    InterstitialAd mInterstitialAd;
    public static  int fulladdcount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        godnameslist = getResources().getStringArray(R.array.godNames);

        recyclerView = (RecyclerView) findViewById(R.id.God_ListView);
        GodName_Adapter obj_adapter = new GodName_Adapter(MainActivity.this, godnameslist,imgs,"false");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(obj_adapter);

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
        MobileAds.initialize(this, admob_app_id);
        LinearLayout adContainer = findViewById(R.id.videlist_adview);
        mAdView = new AdView(MainActivity.this);
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
//        Intent intent= new Intent(MainActivity.this,HomeActivity.class);
//        startActivity(intent);
//        finish();

    }
}