package com.prit.videocompressorpro.View;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.NativeExpressAdView;
import com.prit.videocompressorpro.BuildConfig;
import com.prit.videocompressorpro.Model.Model_images;
import com.prit.videocompressorpro.R;
import com.prit.videocompressorpro.Utils.Helper;
import com.prit.videocompressorpro.ViewModel.Adapter_Photos;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private GridView gv_folder;
    private Button videlist;
    private AdView mAdView;


    public static ArrayList<Model_images> al_images = new ArrayList<>();
    boolean boolean_folder;
    private static final int REQUEST_PERMISSIONS = 100;
    private Boolean chekpermistion = false;
    String result;
    private Adapter_Photos obj_adapter;
   // RequestQueue queue;
    static String URL = "https://priteshparmar.000webhostapp.com/appversion/version.txt";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gv_folder = (GridView) findViewById(R.id.gv_folder);
        videlist = (Button) findViewById(R.id.videlist);



        // Loading banner add
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);






        // Onclik Listner
        videlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (chekpermistion) {
                    Intent ii = new Intent(MainActivity.this, VideoListActivity.class);
                    //Intent ii = new Intent(MainActivity.this, ExampleActivity.class);
                    startActivity(ii);
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSIONS);
                }

            }
        });
        gv_folder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), PhotoListActivity.class);
                intent.putExtra("value", i);
                startActivity(intent);
            }
        });
        // Checkpermition
        chekpermisstion();

        new checkforupdate().execute();


    }
    public class checkforupdate extends AsyncTask<String, Void, String> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;
        @Override
        protected String doInBackground(String... params){


            String inputLine;
            try {
                //Create a URL object holding our url
                java.net.URL myUrl = new URL(URL);
                //Create a connection
                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();
                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
                Helper.LogPrint("app Version",result);


            }
            catch(IOException e){
                e.printStackTrace();
                result = null;
            }
            return result;
        }
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            showUpdatepopup();
        }
    }

    private void showUpdatepopup() {
        try {
            PackageInfo pInfo = MainActivity.this.getPackageManager().getPackageInfo(getPackageName(), 0);
            int   versionCode = pInfo.versionCode;
            if (Integer.parseInt(result)>versionCode){

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Please update your app");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("This app version is no longer supported. Please update your app from the Play Store.");
                alertDialog.setPositiveButton("UPDATE NOW", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final String appPackageName = getPackageName();
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));

                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        }
                    }
                });
                alertDialog.show();
            }else{

            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void chekpermisstion() {
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
                chekpermistion = false;

            }
        } else {
            chekpermistion = true;

        }
    }

    // Fetching image from gallery
    public ArrayList<Model_images> fn_imagespath() {
        al_images.clear();

        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;

        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            Helper.LogPrint("Column", absolutePathOfImage);
            Helper.LogPrint("Folder", cursor.getString(column_index_folder_name));

            for (int i = 0; i < al_images.size(); i++) {
                if (al_images.get(i).getStr_folder().equals(cursor.getString(column_index_folder_name))) {
                    boolean_folder = true;
                    int_position = i;
                    break;
                } else {
                    boolean_folder = false;
                }
            }


            if (boolean_folder) {

                ArrayList<String> al_path = new ArrayList<>();
                al_path.addAll(al_images.get(int_position).getAl_imagepath());
                al_path.add(absolutePathOfImage);
                al_images.get(int_position).setAl_imagepath(al_path);

            } else {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.add(absolutePathOfImage);
                Model_images obj_model = new Model_images();
                obj_model.setStr_folder(cursor.getString(column_index_folder_name));
                obj_model.setAl_imagepath(al_path);

                al_images.add(obj_model);


            }


        }


        for (int i = 0; i < al_images.size(); i++) {
            Helper.LogPrint("FOLDER", al_images.get(i).getStr_folder());
            for (int j = 0; j < al_images.get(i).getAl_imagepath().size(); j++) {
                Helper.LogPrint("FILE", al_images.get(i).getAl_imagepath().get(j));
            }
        }
        obj_adapter = new Adapter_Photos(getApplicationContext(), al_images);
        gv_folder.setAdapter(obj_adapter);
        return al_images;
    }

    // Check permission granted or not
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
                        Toast.makeText(MainActivity.this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {

        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.alert_layout, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
//        // For Live Native Express Adview
//        NativeExpressAdView mAdView = (NativeExpressAdView) findViewById(R.id.adViewNative);
//        AdRequest adRequests = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequests);

// For Testing Native Express Adview
        NativeExpressAdView mAdView = (NativeExpressAdView)dialogView. findViewById(R.id.adViewNative);
        AdRequest adRequests = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequests);
        final AlertDialog alertDialog = builder.create();
        Button rateus=(Button)dialogView.findViewById(R.id.rateus_btn);
        Button no=(Button)dialogView.findViewById(R.id.no_btn);
        Button yes=(Button)dialogView.findViewById(R.id.yes_btn);

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
                finish();
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
        inflater.inflate(R.menu.menushare, menu);

        MenuItem item = menu.findItem(R.id.mShare);
        MenuItem feedback = menu.findItem(R.id.feedback);

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Video Compressor Pro");
                    String shareMessage = "\nLet me recommend you this application\n\n";
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


                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                final View deleteDialogView = factory.inflate(R.layout.feedbackpopup, null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(MainActivity.this).create();
                deleteDialog.setTitle("Feedback");
                deleteDialog.setView(deleteDialogView);



                Button feedbacksubmit = (Button) deleteDialogView.findViewById(R.id.feedbacksubmit);

                feedbacksubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                        }
//                            String feedbackvalue =text.getText().toString().trim();
//                            if (feedbackvalue.equalsIgnoreCase("")) {
//                                Toast.makeText(MainActivity.this,"Please provide feedback hear or in play store",Toast.LENGTH_LONG).show();
//                            }else{
//                                String androidSDK = "null", androidVersion = "null", androidBrand = "null",
//                                        androidManufacturer = "null", androidModel = "null", androidDeviceId = "null";
//                                String versionCode = "null", versionName = "null";
//                                PackageInfo pInfo = null;
//                                String deviceDetail = "null";
//                                pInfo = MainActivity.this.getPackageManager().getPackageInfo(MainActivity.this.getPackageName(), 0);
//                                versionCode = String.valueOf(pInfo.versionCode).trim();
//                                versionName = String.valueOf(pInfo.versionName).trim();
//                                androidSDK = String.valueOf(android.os.Build.VERSION.SDK_INT);
//                                androidVersion = android.os.Build.VERSION.RELEASE;
//                                androidBrand = android.os.Build.BRAND;
//                                androidManufacturer = android.os.Build.MANUFACTURER;
//                                androidModel = android.os.Build.MODEL;
//                                androidDeviceId = Settings.Secure.getString(MainActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
//                                deviceDetail = "App Version Code: " + versionCode + "\nApp Version Number: " + versionName + "\nSDK: " + androidSDK + "\nVersion: " + androidVersion + "\nBrand: " + androidBrand +
//                                        "\nManufacturer: " + androidManufacturer + "\nModel: " + androidModel + "\nAndroid Device Id: " + androidDeviceId;
//
//                                JSONObject feed = new JSONObject();
//
//                                feed.put("Feedback", feedbackvalue);
//                                feed.put("deviceDetail", deviceDetail);
//
//
//                                sendMessageToserver(feed);
//                            }
                        deleteDialog.dismiss();
                    }
                });

                deleteDialog.show();
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }
//    private void sendMessageToserver(JSONObject json) {
//
//        try {
//            final ProgressDialog pDialog = new ProgressDialog(this);
//            pDialog.setMessage("Sending Please Wait...");
//            pDialog.show();
//            Log.e("JSON", "" + json);
//            String url = "https://priteshparmar.000webhostapp.com/appversion/feedback.php";
//            Log.e("url", "" + url);
//
//
//            JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST,
//                    url, json, new Response.Listener<JSONObject>() {
//
//                @Override
//                public void onResponse(JSONObject response) {
//                    pDialog.dismiss();
//                    Log.e("Respose", response.toString());
//                    Toast.makeText(MainActivity.this,"Thank you for your feedback",Toast.LENGTH_LONG).show();
//
//                }
//            }, new Response.ErrorListener() {
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    pDialog.dismiss();
//                    VolleyLog.e("TAG", "Error: " + error.getMessage());
//
//                }
//
//            }) {
//
//
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<String, String>();
//                    headers.put("Content-Type", "application/json");
//                    headers.put("apiKey", "xxxxxxxxxxxxxxx");
//                    return headers;
//                }
//            };
//
//
//// Adding request to request queue
//            queue.add(strReq);
//        } catch (Exception e) {
//
//        }
//    }
}





