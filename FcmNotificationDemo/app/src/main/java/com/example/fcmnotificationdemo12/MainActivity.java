package com.example.fcmnotificationdemo12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;

import com.example.fcmnotificationdemo12.ApiClients.APIClient;
import com.example.fcmnotificationdemo12.ApiClients.APIInterface;
import com.example.fcmnotificationdemo12.Fingerprint.FingerprintActivity;
import com.example.fcmnotificationdemo12.admob.InterstitialAdActivity;
import com.example.fcmnotificationdemo12.admob.RewardedVideoAdActivity;
import com.example.fcmnotificationdemo12.model.UserList;
import com.example.fcmnotificationdemo12.model.Warehouse;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;


public class MainActivity extends AppCompatActivity {
    String CHANNEL_ID = "my_channel_01";
    String CHANNEL_NAME = "Simplified Coding Notification";
    String CHANNEL_DESCRIPTION = "www.simplifiedcoding.net";
    Spinner spinner;
    private String TAG;
    private String DATA_URL = "https://query1.finance.yahoo.com/v8/finance/chart/AAPL?";
    APIInterface apiInterface;
    APIInterface apiInterfaceswagger;
    private AdView mAdView;
    private Button btnFullscreenAd, btnShowRewardedVideoAd,fingerprint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            int i = 0 / 100;
            int k = 100 / 0;


        } catch (Exception e) {
            Crashlytics.logException(e);
        }
        btnFullscreenAd = (Button) findViewById(R.id.btn_fullscreen_ad);
        btnShowRewardedVideoAd = (Button) findViewById(R.id.btn_show_rewarded_video);
        fingerprint=(Button)findViewById(R.id.fingerprint);
        fingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, FingerprintActivity.class);
                startActivity(i);
            }
        });
        btnFullscreenAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InterstitialAdActivity.class));
            }
        });

        btnShowRewardedVideoAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RewardedVideoAdActivity.class));
            }
        });

        // TODO - remove this if condition
        // it's for demo purpose
        if (TextUtils.isEmpty(getString(R.string.banner_home_footer))) {
            Toast.makeText(getApplicationContext(), "Please mention your Banner Ad ID in strings.xml", Toast.LENGTH_LONG).show();
            return;
        }

        mAdView = (AdView) findViewById(R.id.adView);


        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                .addTestDevice("F264BDBEADFEA6032F2FBC37CB138C33")
                .build();

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });

        mAdView.loadAd(adRequest);


        spinner = findViewById(R.id.spinnerTopics);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        apiInterfaceswagger = APIClient.getClientswagger().create(APIInterface.class);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            mChannel.setDescription(CHANNEL_DESCRIPTION);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
        }

        findViewById(R.id.buttonSubscribe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String topic = spinner.getSelectedItem().toString();
                FirebaseMessaging.getInstance().subscribeToTopic(topic);
                Toast.makeText(getApplicationContext(), "Topic Subscribed", Toast.LENGTH_LONG).show();
            }
        });
        // MyNotificationManager.getInstance(this).displayNotification("Greetings", "Hello how are you?");
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.e("token ", token);

                        // Log and toast

                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

        // LoadJson();

        //LoadRetorofitJson();
    }

    private void LoadRetorofitJson() {
        Call<UserList> call2 = apiInterface.doGetUserList("2");
        call2.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, retrofit2.Response<UserList> response) {

                UserList userList = response.body();
                Integer text = userList.page;
                Integer total = userList.total;
                Integer totalPages = userList.totalPages;
                List<UserList.Datum> datumList = userList.data;
                Toast.makeText(getApplicationContext(), text + " page\n" + total + " total\n" + totalPages + " totalPages\n", Toast.LENGTH_SHORT).show();

                for (UserList.Datum datum : datumList) {
                    Toast.makeText(getApplicationContext(), "id : " + datum.id + " name: " + datum.first_name + " " + datum.last_name + " avatar: " + datum.avatar, Toast.LENGTH_SHORT).show();
                }

                Loadwarehouse();
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void Loadwarehouse() {
        Call<Warehouse> call2 = apiInterfaceswagger.getWarehouseDetail("7102","6jDuJPlg9IIe9VY0TCbaCY4srbv2PPKfNHjKW4rcaLjmpolsPuRWgHFLsbwkXrOZZTYidNbKzpRF/RcYcb/8oc9F9EVLEGQpFXK/+TcZRXkYTTHhJHga94iPXZZRNcGWED1fJabizjtrwdzTBnhefskig9TD+0q2Bj237CzV0w/O4tT6PoRV/uKs/tPXVaU7OfXvreMbdl52eNsz80T0aqOxXkpQW33evTHx5uPRRSLONtLqH8CwoCdlVx8NdwQRbLOjYCeakboY9ng5fLtZ9CMqks9F1QQ2YoU3X43BDfeLR/fp+25QURY2Fljp6WMf","1","1");
        call2.enqueue(new Callback<Warehouse>() {
            @Override
            public void onResponse(Call<Warehouse> call, retrofit2.Response<Warehouse> response) {

                Warehouse userList = response.body();

//                Log.e("warehouse",""+userList.getData().getId());
                Toast.makeText(MainActivity.this,""+userList.getData().getDescription(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Warehouse> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void LoadJson() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                DATA_URL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e("response", response.toString());
                            JSONObject obj = response.getJSONObject("chart");
                            JSONArray result = obj.getJSONArray("result");
                            Log.e("result", result.toString());
                            for (int i = 0; i < result.length(); i++) {
                                JSONObject metaobj = result.getJSONObject(i);

                                JSONObject meta = metaobj.getJSONObject("meta");
                                String currency = meta.getString("currency");
                                Log.e("currency", currency.toString());

                                JSONObject currentTradingPeriod = meta.getJSONObject("currentTradingPeriod");

                                JSONObject pre = currentTradingPeriod.getJSONObject("pre");
                                String timezone = pre.getString("timezone");
                                Log.e("pretimezone", timezone.toString());

                                JSONObject regular = currentTradingPeriod.getJSONObject("regular");
                                String regulartimezone = regular.getString("timezone");
                                Log.e("regulartimezone", regulartimezone.toString());

                                JSONObject post = currentTradingPeriod.getJSONObject("post");
                                String posttimezone = post.getString("timezone");
                                Log.e("posttimezone", posttimezone.toString());


                                JSONArray timestamp = metaobj.getJSONArray("timestamp");
                                for (int timestamps = 0; timestamps < timestamp.length(); timestamps++) {

                                    Log.e("timestamp0", timestamp.get(timestamps).toString());
                                }
                                JSONObject indicators = metaobj.getJSONObject("indicators");
                                JSONArray quote = indicators.getJSONArray("quote");
                                for (int k = 0; k < quote.length(); k++) {
                                    JSONObject close = quote.getJSONObject(k);
                                    JSONArray closearray = close.getJSONArray("close");
                                    Log.e("Close array", closearray.toString());
                                    for (int ks = 0; ks < closearray.length(); ks++) {
                                        String doubles = (String) closearray.get(ks);
                                        Log.e("closearrayprint", "" + doubles);
                                    }
                                }


                            }

                        } catch (Exception e) {
                            Log.e("Error", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("symbol", "INTC");
                params.put("period1", "0");
                params.put("period2", "9999999999");
                params.put("interval", "1d");

                return params;
            }

        };

// Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Adding our request to the queue

        requestQueue.add(jsonObjReq);
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
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

}
