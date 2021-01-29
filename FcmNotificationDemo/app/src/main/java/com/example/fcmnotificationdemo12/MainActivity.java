package com.example.fcmnotificationdemo12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.fcmnotificationdemo12.ApiClients.APIClient;
import com.example.fcmnotificationdemo12.ApiClients.APIInterface;
import com.example.fcmnotificationdemo12.FileUpload.AndroidMultiPartEntity;
import com.example.fcmnotificationdemo12.Fingerprint.FingerprintActivity;
import com.example.fcmnotificationdemo12.admob.InterstitialAdActivity;
import com.example.fcmnotificationdemo12.admob.RewardedVideoAdActivity;
import com.example.fcmnotificationdemo12.model.UserList;
import com.example.fcmnotificationdemo12.model.Warehouse;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
    private static final int REQUEST_PERMISSIONS = 100;
    APIInterface apiInterface;
    APIInterface apiInterfaceswagger;
    private AdView mAdView;
    private Button btnFullscreenAd, btnShowRewardedVideoAd,fingerprint;
    private ProgressBar progressBar;
    private TextView txtPercentage;
    long totalSize = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        txtPercentage = (TextView) findViewById(R.id.txtPercentage);
        try {
            int i = 0 / 100;
            int k = 100 / 0;


        } catch (Exception e) {
          //  Crashlytics.logException(e);
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

        //mAdView.loadAd(adRequest);


        spinner = findViewById(R.id.spinnerTopics);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        apiInterfaceswagger = APIClient.getClientswagger().create(APIInterface.class);

//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationManager mNotificationManager =
//                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
//            mChannel.setDescription(CHANNEL_DESCRIPTION);
//            mChannel.enableLights(true);
//            mChannel.setLightColor(Color.RED);
//            mChannel.enableVibration(true);
//            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//            mNotificationManager.createNotificationChannel(mChannel);
//        }
//
//        findViewById(R.id.buttonSubscribe).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String topic = spinner.getSelectedItem().toString();
//                FirebaseMessaging.getInstance().subscribeToTopic(topic);
//                Toast.makeText(getApplicationContext(), "Topic Subscribed", Toast.LENGTH_LONG).show();
//            }
//        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }
        FirebaseMessaging.getInstance().subscribeToTopic(getString(R.string.default_notification_channel_name))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                        String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                        }
                        Log.d("Notification", msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }


                });
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("TAG", msg);
                        Toast.makeText(MainActivity.this, "token"+msg, Toast.LENGTH_SHORT).show();
                    }


                });
        // LoadJson();
        chekpermisstion();
        //LoadRetorofitJson();
     //   new UploadFileToServer().execute();
    }

    public void chekpermisstion(){
        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {




            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);


            }
        }else {


            //fn_imagespath();
        }
    }
    /**
     * This function upload the large file to server with other POST values.
     * @param filename
     * @param targetUrl
     * @return
     */
    public static String uploadFileToServer(String filename, String targetUrl) {
        String response = "error";
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;

        String pathToOurFile = filename;
        String urlServer = targetUrl;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024;
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(
                    pathToOurFile));

            URL url = new URL(urlServer);
            connection = (HttpURLConnection) url.openConnection();

            // Allow Inputs & Outputs
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setChunkedStreamingMode(1024);
            // Enable POST method
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);

            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);

            String token = "anyvalye";
            outputStream.writeBytes("Content-Disposition: form-data; name=\"Token\"" + lineEnd);
            outputStream.writeBytes("Content-Type: text/plain;charset=UTF-8" + lineEnd);
            outputStream.writeBytes("Content-Length: " + token.length() + lineEnd);
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(token + lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);

            String taskId = "anyvalue";
            outputStream.writeBytes("Content-Disposition: form-data; name=\"TaskID\"" + lineEnd);
            outputStream.writeBytes("Content-Type: text/plain;charset=UTF-8" + lineEnd);
            outputStream.writeBytes("Content-Length: " + taskId.length() + lineEnd);
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(taskId + lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);

            String connstr = null;
            connstr = "Content-Disposition: form-data; name=\"UploadFile\";filename=\""
                    + pathToOurFile + "\"" + lineEnd;

            outputStream.writeBytes(connstr);
            outputStream.writeBytes(lineEnd);

            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // Read file
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            System.out.println("Image length " + bytesAvailable + "");
            try {
                while (bytesRead > 0) {
                    try {
                        outputStream.write(buffer, 0, bufferSize);
                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
                        response = "outofmemoryerror";
                        return response;
                    }
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
            } catch (Exception e) {
                e.printStackTrace();
                response = "error";
                return response;
            }
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens
                    + lineEnd);

            // Responses from the server (code and message)
            int serverResponseCode = connection.getResponseCode();
            String serverResponseMessage = connection.getResponseMessage();
            System.out.println("Server Response Code " + " " + serverResponseCode);
            System.out.println("Server Response Message "+ serverResponseMessage);

            if (serverResponseCode == 200) {
                response = "true";
            }else
            {
                response = "false";
            }

            fileInputStream.close();
            outputStream.flush();

            connection.getInputStream();
            //for android InputStream is = connection.getInputStream();
            java.io.InputStream is = connection.getInputStream();

            int ch;
            StringBuffer b = new StringBuffer();
            while( ( ch = is.read() ) != -1 ){
                b.append( (char)ch );
            }

            String responseString = b.toString();
            System.out.println("response string is" + responseString); //Here is the actual output

            outputStream.close();
            outputStream = null;

        } catch (Exception ex) {
            // Exception handling
            response = "error";
            System.out.println("Send file Exception" + ex.getMessage() + "");
            ex.printStackTrace();
        }
        return response;
    }


    public static void downloadFileFromServer(String filename, String urlString) throws MalformedURLException, IOException
    {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try
        {
            URL url = new URL(urlString);

            in = new BufferedInputStream(url.openStream());
            fout = new FileOutputStream(filename);

            byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1)
            {
                fout.write(data, 0, count);
                System.out.println(count);
            }
        }
        finally
        {
            if (in != null)
                in.close();
            if (fout != null)
                fout.close();
        }
        System.out.println("Done");
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
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        //Toast.makeText(MainActivity.this, "granted", Toast.LENGTH_LONG).show();
                        //fn_imagespath();
                       // new UploadFileToServer().execute();
                        new UploadFileToServer().execute();
                    } else {

                        Toast.makeText(MainActivity.this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
//    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
//        @Override
//        protected void onPreExecute() {
//            // setting progress bar to zero
//            progressBar.setProgress(0);
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... progress) {
//            // Making progress bar visible
//            progressBar.setVisibility(View.VISIBLE);
//
//            // updating progress bar value
//            progressBar.setProgress(progress[0]);
//
//            // updating percentage value
//            txtPercentage.setText(String.valueOf(progress[0]) + "%");
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//
//            return uploadFileToServer("/storage/emulated/0/DCIM/Camera/VID_20191216_165052.mp4","https://priteshparmar.000webhostapp.com/BigFileUpload/fileUpload.php");
//        }
//
//
//
//        @Override
//        protected void onPostExecute(String result) {
//            Log.e(TAG, "Response from server: " + result);
//
//            // showing the server response in an alert dialog
//            showAlert(result);
//
//            super.onPostExecute(result);
//        }
//
//    }
private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
    @Override
    protected void onPreExecute() {
        // setting progress bar to zero
        progressBar.setProgress(0);
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        // Making progress bar visible
        progressBar.setVisibility(View.VISIBLE);

        // updating progress bar value
        progressBar.setProgress(progress[0]);

        // updating percentage value
        txtPercentage.setText(String.valueOf(progress[0]) + "%");
    }

    @Override
    protected String doInBackground(Void... params) {
        return uploadFile();
    }

    @SuppressWarnings("deprecation")
    private String uploadFile() {
        String responseString = null;

        HttpClient httpclient = new DefaultHttpClient();
        //HttpPost httppost = new HttpPost("https://priteshparmar.000webhostapp.com/BigFileUpload/fileUpload.php" );
        HttpPost httppost = new HttpPost("http://192.168.100.60/fileUpload.php" );
/// upload big file we have to change in xampp config button (PHP)php.ini file with below change
//        max_execution_time = 5000
//        max_input_time = 5000
//        memory_limit = 1000M
//                post_max_size = 750M
//                upload_max_filesize = 750M
        try {
            AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                    new AndroidMultiPartEntity.ProgressListener() {

                        @Override
                        public void transferred(long num) {
                            publishProgress((int) ((num / (float) totalSize) * 100));
                        }
                    });

           // File sourceFile = new File("/storage/emulated/0/DCIM/Camera/VID_20191216_165052.mp4");
            File sourceFile = new File("/storage/emulated/0/DCIM/Camera/VID_20191216_161925.mp4");
            // Adding file data to http body
            entity.addPart("image", new FileBody(sourceFile));

            // Extra parameters if you want to pass to server
            entity.addPart("website",
                    new StringBody("www.androidhive.info"));
            entity.addPart("email", new StringBody("abc@gmail.com"));

            totalSize = entity.getContentLength();
            httppost.setEntity(entity);

            // Making server call
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity r_entity = response.getEntity();

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                // Server response
                responseString = EntityUtils.toString(r_entity);
            } else {
                responseString = "Error occurred! Http Status Code: "
                        + statusCode;
            }

        } catch (ClientProtocolException e) {
            responseString = e.toString();
        } catch (IOException e) {
            responseString = e.toString();
        }

        return responseString;

    }

    @Override
    protected void onPostExecute(String result) {
        Log.e(TAG, "Response from server: " + result);

        // showing the server response in an alert dialog
        showAlert(result);

        super.onPostExecute(result);
    }

}
    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle("Response from Servers")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // do nothing
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
