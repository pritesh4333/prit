package app.reelsdownloadervideo.storydownloader;

import static android.os.Environment.DIRECTORY_DOCUMENTS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {
    Boolean permistion = false;
    private static final int REQUEST_PERMISSIONS_CODE = 100;
    EditText Enter_URL;
    public static final String MY_PREFS = "reelsdown";
    ProgressBar progressbar;
    TextView progresstext;
    AlertDialog alertpopup;
    View progress_dailog;
    private static final int MY_REQUEST_CODE = 1;
    ArrayList<String> imgUrlss=new ArrayList<>();
    File mypath;
    AppUpdateManager appUpdateManager1;
    AdView myAdView;
    InterstitialAd mInterstitialAd;
    public static  int fulladdcount=0;
    String app_id,footer_add,fullscreen_add,native_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_main);
        //app update
        appUpdateManager1 = AppUpdateManagerFactory.create(HomeActivity.this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getUI();
        loadAdds();
        checkforNewUpdate();

    }

    public void loadAdds() {
        // Loading banner adds
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
        myAdView = new AdView(HomeActivity.this);
        myAdView.setAdSize(AdSize.BANNER);
        myAdView.setAdUnitId(footer_add);
        adContainer.addView(myAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        myAdView.loadAd(adRequest);

        /// Initializing the Google Admob SDK  NATIVE
        MobileAds.initialize(HomeActivity.this, new OnInitializationCompleteListener() {@Override
        public void onInitializationComplete(InitializationStatus initializationStatus) {

            //Showing a simple Toast Message to the user when The Google AdMob Sdk Initialization is Completed
            //  Toast.makeText(HomeActivity.this, "AdMob Sdk Initialize " + initializationStatus.toString(), Toast.LENGTH_LONG).show();
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
    private void checkforNewUpdate() {

        // Creates instance of the manager.


        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager1.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)) {
                    try {
                        appUpdateManager1.startUpdateFlowForResult(
                                // Pass the intent that is returned by 'getAppUpdateInfo()'.
                                appUpdateInfo,
                                // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                                IMMEDIATE,
                                // The current activity making the update request.
                                HomeActivity.this,
                                // Include a request code to later monitor this update request.
                                MY_REQUEST_CODE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                } else {
                }
            }
        });


    }
    public void chekpermisstion() {

        if ((ContextCompat.checkSelfPermission(getApplicationContext(),

                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,

                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.
                    shouldShowRequestPermissionRationale(HomeActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS_CODE);
                permistion = false;

            }

        } else {

            permistion = true;

        }
    }
    @Override
    public void onPause() {
        if (myAdView != null) {
            myAdView.pause();
        }
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (myAdView != null) {
            myAdView.resume();
        }
        appUpdateManager1
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        new OnSuccessListener<AppUpdateInfo>() {
                            @Override
                            public void onSuccess(AppUpdateInfo appUpdateInfo) {

                                if (appUpdateInfo.updateAvailability()
                                        == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                    // If an in-app update is already running, resume the update.
                                    try {
                                        appUpdateManager1.startUpdateFlowForResult(
                                                appUpdateInfo,
                                                IMMEDIATE,
                                                HomeActivity.this,
                                                MY_REQUEST_CODE);
                                    } catch (IntentSender.SendIntentException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                }
                            }
                        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                checkforNewUpdate();
            }else{
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS_CODE: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        permistion = true;
                    } else {
                        permistion = false;
                        Toast.makeText(HomeActivity.this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
    private void getUI() {



        final LinearLayout download_btn = (LinearLayout) findViewById(R.id.download_btn);
        LinearLayout My_files = (LinearLayout) findViewById(R.id.My_files);

        Enter_URL = (EditText) findViewById(R.id.Enter_URL);


        My_files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (permistion) {
                    try {
                        File path = new File(Environment.getExternalStorageDirectory(), "Reels-Video-Downloader");

                        if (!path.exists()) {
                            if (!path.mkdirs()) {

                            }
                        }
                    } catch (Exception e) {
                    }

                    Intent ii = new Intent(HomeActivity.this, MyvideoList.class);
                    startActivity(ii);

                } else {

                    ActivityCompat.requestPermissions(HomeActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSIONS_CODE);
                }


            }
        });
        download_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //  commentted text
                //  Downliad images url
                //  codition && !URL.getText().toString().trim().contains("www.instagram.com/p")
                if (permistion) {

                    if (Enter_URL.getText().toString().trim().isEmpty()) {
                        Enter_URL.setError("Please Enter OR Past URL");
                    } else if (!Enter_URL.getText().toString().trim().contains("www.instagram.com/reel") &&
                            !Enter_URL.getText().toString().trim().contains("www.instagram.com/tv")&&
                            !Enter_URL.getText().toString().trim().contains("www.instagram.com/p/")) {
                        Enter_URL.setError("Provide Instagram Reel or IGTV URL");
                    } else if (!checkInternetConnection(HomeActivity.this)) {
                        Toast.makeText(HomeActivity.this, "Please check Internet connection", Toast.LENGTH_LONG).show();
                    } else {
                        LocalBroadcastManager bManagers = LocalBroadcastManager.getInstance(HomeActivity.this);
                        IntentFilter intentFilters = new IntentFilter();
                        intentFilters.addAction("message_progress");
                        bManagers.registerReceiver(broadcastReceiver, intentFilters);

                        try {
                            mypath = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS).getPath()
                                    + "/PRIT");

                            if (!mypath.exists()) {
                                mypath.mkdir();
                                Toast.makeText(getApplicationContext(), "not exist", Toast.LENGTH_SHORT).show();
                            }
//                              mypath = new File(Environment.getExternalStorageDirectory(), "Reels-Video-Downloader");
//                            boolean isCreada = mypath.exists();
//                            if(!isCreada) {
//                                mypath.mkdirs();
//                                Log.e("path ","folder created");
//                            }else{
//                                Log.e("path ","folder allready  create ");
//                            }
//                            if (!mypath.exists()) {
//                                if (!mypath.mkdirs()) {
//                                    Log.e("path ","folder create failed");
//                                }
//                                Log.e("path ","folder created");
//                            }
                        } catch (Exception e) {
                        }

                        InputMethodManager immn = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                        immn.hideSoftInputFromWindow(download_btn.getWindowToken(), 0);

                        String fileUrl = Enter_URL.getText().toString().trim().replace("reel","p");

                        String[] parts = fileUrl.split("utm_medium=copy_link");

                         new DownloadJson().execute(parts[0] + "__a=1");
//                        new BackgroundTask(HomeActivity.this) {
//                            @Override
//                            public void onPreExecute() {
//                                ViewGroup viewGroups = findViewById(android.R.id.content);
//                                progress_dailog = LayoutInflater.from(HomeActivity.this).inflate(R.layout.progress, viewGroups, false);
//
//
//                                //Now we need an AlertDialog.Builder object
//                                AlertDialog.Builder builders = new AlertDialog.Builder(HomeActivity.this);
//
//                                //setting the view of the builder to our custom view that we already inflated
//                                builders.setView(progress_dailog);
//
//
//
//
//                                alertpopup = builders.create();
//                                alertpopup.setCancelable(false);
//                                alertpopup.setCanceledOnTouchOutside(false);
//                                progresstext = (TextView) progress_dailog.findViewById(R.id.progresspercentage);
//                                progressbar = (ProgressBar) progress_dailog.findViewById(R.id.progress_bar);
//
//                                final TemplateView[] template = new TemplateView[1];
//
//                                //Initializing the AdLoader   objects
//                                AdLoader adLoader = new AdLoader.Builder(HomeActivity.this, native_add).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
//
//                                    private ColorDrawable background;@Override
//                                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
//
//                                        NativeTemplateStyle styles = new
//                                                NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();
//
//                                        template[0] = progress_dailog.findViewById(R.id.nativeTemplateView);
//                                        template[0].setStyles(styles);
//                                        template[0].setNativeAd(unifiedNativeAd);
//                                        progress_dailog.findViewById(R.id.nativeTemplateView).setVisibility(View.VISIBLE);
//                                        // Showing a simple Toast message to user when Native an ad is Loaded and ready to show
//                                        //   Toast.makeText(HomeActivity.this, "Native Ad is loaded ,now you can show the native ad  ", Toast.LENGTH_LONG).show();
//                                    }
//
//                                }).build();
//
//
//                                // load Native Ad with the Request
//                                adLoader.loadAds(new AdRequest.Builder().build(),5);
//                                alertpopup.show();
//                            }
//
//                            @Override
//                            public String doInBackground(String url) {
//                                try {
//                                    OkHttpClient client = new OkHttpClient();
//
//                                    Request request =
//                                            new Request.Builder()
//                                                    .url(url)
//                                                    .build();
//
//                                    Response response = client.newCall(request).execute();
//                                    if (response.isSuccessful()) {
//                                        return response.body().string();
//                                    }
//                                } catch (IOException ioException) {
//                                    ioException.printStackTrace();
//                                }
//                                return "Download failed";
//                            }
//
//                            @Override
//                            public void onPostExecute(String result) {
//                                String loudScreaming="";
//                                String src="";
//                                try {
//                                    if (result == null ) {
//                                        alertpopup.dismiss();
//                                        String fileUrl = Enter_URL.getText().toString().trim();
//                                        if (fileUrl.contains("-")||fileUrl.contains("_")){
//                                            new AlertDialog.Builder(HomeActivity.this)
//                                                    .setTitle("Private Account")
//                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                                        public void onClick(DialogInterface dialog, int which) {
//                                                            Enter_URL.setText("");
//                                                        }
//                                                    })
//                                                    .setIcon(android.R.drawable.ic_dialog_alert)
//                                                    .show();
//                                        }else {
//                                            String[] partss = fileUrl.split("utm_medium=copy_link");
//                                            new DownloadJson().execute(partss[0] + "__a=1");
//                                        }
//                                    } else {
//
//                                        JSONObject jsonObjects = new JSONObject(result);
//                                        JSONObject jsonObject11 = jsonObjects.getJSONObject("graphql");
//                                        JSONObject jsonObject22 = jsonObject11.getJSONObject("shortcode_media");
//                                        try {
//                                            loudScreaming = jsonObject22.getString("video_url");
//
//                                        }catch(Exception e){
//                                            imgUrlss= new ArrayList<>();
//                                            JSONArray jsonArray=jsonObject22.getJSONArray("display_resources");
//                                            for (int i =0;i<jsonArray.length();i++){
//                                                JSONObject jsonObject3= jsonArray.getJSONObject(i);
//                                                src = jsonObject3.getString("src");
//                                                imgUrlss.add(src);
//                                            }
//
//                                        }
//
//                                        if(loudScreaming.equalsIgnoreCase("")){
//                                            try {
//                                                JSONObject jsonObject3 = jsonObject22.getJSONObject("edge_sidecar_to_children");
//                                                JSONArray jsonArray = jsonObject3.getJSONArray("edges");
//                                                for (int i = 0; i < jsonArray.length(); i++) {
//                                                    JSONObject jsonObject4 = jsonArray.getJSONObject(i).getJSONObject("node");
//                                                    loudScreaming = jsonObject4.getString("video_url");
//                                                    Log.e("video and post url ", loudScreaming);
//                                                }
//
//                                            }catch(Exception e) {
//                                                Intent intents = new Intent(HomeActivity.this, DownloadBackgroundService.class);
//                                                intents.putExtra("url", loudScreaming.substring(39));
//                                                intents.putExtra("path", mypath.toString());
//                                                startService(intents);
//
//                                            }
//
//                                        }else {
//
//                                            Intent intents = new Intent(HomeActivity.this, DownloadBackgroundService.class);
//                                            intents.putExtra("url", loudScreaming.substring(39));
//                                            intents.putExtra("path", mypath.toString());
//                                            startService(intents);
//                                        }
//
//
//
//
//                                    }
//                                } catch(JSONException e){
//                                    e.printStackTrace();
//                                    alertpopup.dismiss();
//                                    HomeActivity.this.runOnUiThread(new Runnable() {
//                                        public void run() {
//                                            Enter_URL.setText("");
//                                            Toast.makeText(HomeActivity.this,"Bad URL",Toast.LENGTH_LONG).show();
//                                        }
//                                    });
//                                }
//
//                            }
//                        }.execute(parts[0] + "__a=1");
                    }
                } else {

                    ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS_CODE);
                }


            }
        });
        chekpermisstion();
    }

    public static boolean checkInternetConnection(Context context) {

        final ConnectivityManager connMgrer = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgrer != null) {
            NetworkInfo oneactiveNetworkInfo = connMgrer.getActiveNetworkInfo();

            if (oneactiveNetworkInfo != null) { // connected to the internet
                if (oneactiveNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
                } else return oneactiveNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            }

        }
        return false;
    }
    @Override
    public void onDestroy() {
        if (myAdView != null) {
            myAdView.destroy();
        }
        super.onDestroy();
    }
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            ModelDownload downloads = intent.getParcelableExtra("download");
            if (intent.getAction().equals("message_progress")) {


                progressbar.setProgress(downloads.getProgress());
                if (downloads.getProgress() == 100) {

                    progresstext.setText("File Download Complete");
                    if (alertpopup.isShowing()) {
                        alertpopup.dismiss();
                        HomeActivity.this.runOnUiThread(new Runnable() {
                            public void run() {

                                showmessage("Download complete save in My Reels.");
                            }
                        });
                    }
                } else {

                    progresstext.setText("Downloading " + " ( " + downloads.getCurrentFileSize() + " / " + downloads.getTotalFileSize() + " )");

                }
            }

        }
    };


    public void showmessage(String message) {
        Enter_URL.setText("");
        LayoutInflater factorys = LayoutInflater.from(HomeActivity.this);
        final View deleteDialogView = factorys.inflate(R.layout.popup, null);

        final AlertDialog Dialog = new AlertDialog.Builder(HomeActivity.this).create();
        Dialog.setView(deleteDialogView);


        Button submitfeedback = (Button) deleteDialogView.findViewById(R.id.submitfeedback);
        TextView about = (TextView) deleteDialogView.findViewById(R.id.about);
        TextView likeapp = (TextView) deleteDialogView.findViewById(R.id.likeapp);
        likeapp.setText("Download");


        likeapp.setVisibility(View.VISIBLE);
        submitfeedback.setVisibility(View.GONE);
        about.setVisibility(View.VISIBLE);
        about.setText(message);
        about.setTextSize(17);
        about.setTextColor(HomeActivity.this.getResources().getColor(R.color.white));
        about.setBackgroundColor(HomeActivity.this.getResources().getColor(R.color.greencolor));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) about.getLayoutParams();
        params.setMargins(5, 10, 5, 5);
        about.setLayoutParams(params);
        final TemplateView[] template = new TemplateView[1];

        //Initializing the AdLoader   objects
        AdLoader adLoader = new AdLoader.Builder(HomeActivity.this, native_add).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

            private ColorDrawable background;@Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                NativeTemplateStyle styles = new
                        NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                template[0] = deleteDialogView.findViewById(R.id.nativeTemplateView);
                template[0].setStyles(styles);
                template[0].setNativeAd(unifiedNativeAd);
                deleteDialogView.findViewById(R.id.nativeTemplateView).setVisibility(View.VISIBLE);
                // Showing a simple Toast message to user when Native an ad is Loaded and ready to show
                //   Toast.makeText(HomeActivity.this, "Native Ad is loaded ,now you can show the native ad  ", Toast.LENGTH_LONG).show();
            }

        }).build();


        // load Native Ad with the Request
        adLoader.loadAds(new AdRequest.Builder().build(),5);
        Dialog.show();
        submitfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {

                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + getPackageName())));

                } catch (android.content.ActivityNotFoundException anfe) {

                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }

                Dialog.dismiss();
            }
        });

        Dialog.show();

    }


    private class DownloadJson extends AsyncTask<String, String, String> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;

        protected void onPreExecute() {
            super.onPreExecute();

            ViewGroup viewGroups = findViewById(android.R.id.content);
            progress_dailog = LayoutInflater.from(HomeActivity.this).inflate(R.layout.progress, viewGroups, false);


            //Now we need an AlertDialog.Builder object
            AlertDialog.Builder builders = new AlertDialog.Builder(HomeActivity.this);

            //setting the view of the builder to our custom view that we already inflated
            builders.setView(progress_dailog);




            alertpopup = builders.create();
            alertpopup.setCancelable(false);
            alertpopup.setCanceledOnTouchOutside(false);
            progresstext = (TextView) progress_dailog.findViewById(R.id.progresspercentage);
            progressbar = (ProgressBar) progress_dailog.findViewById(R.id.progress_bar);

            final TemplateView[] template = new TemplateView[1];

            //Initializing the AdLoader   objects
            AdLoader adLoader = new AdLoader.Builder(HomeActivity.this, native_add).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

                private ColorDrawable background;@Override
                public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                    NativeTemplateStyle styles = new
                            NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                    template[0] = progress_dailog.findViewById(R.id.nativeTemplateView);
                    template[0].setStyles(styles);
                    template[0].setNativeAd(unifiedNativeAd);
                    progress_dailog.findViewById(R.id.nativeTemplateView).setVisibility(View.VISIBLE);
                    // Showing a simple Toast message to user when Native an ad is Loaded and ready to show
                    //   Toast.makeText(HomeActivity.this, "Native Ad is loaded ,now you can show the native ad  ", Toast.LENGTH_LONG).show();
                }

            }).build();


            // load Native Ad with the Request
            adLoader.loadAds(new AdRequest.Builder().build(),5);
            alertpopup.show();
        }

        protected String doInBackground(String... params) {
            StringBuffer chaine = new StringBuffer("");
            try{
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestProperty("User-Agent", "");
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();

                InputStream inputStream = connection.getInputStream();

                BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while ((line = rd.readLine()) != null) {
                    chaine.append(line);
                }
                return String.valueOf(chaine);
            }

            catch (IOException e) {
                // Writing exception to log
                e.printStackTrace();
            }
            return String.valueOf(chaine);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("Return Response ", result);
            String loudScreaming="";
            String src="";
            try {
                if (result == null ) {
                    alertpopup.dismiss();
                    String fileUrl = Enter_URL.getText().toString().trim();
                    if (fileUrl.contains("-")||fileUrl.contains("_")){
                        new AlertDialog.Builder(HomeActivity.this)
                                .setTitle("Private Account")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Enter_URL.setText("");
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }else {
                        String[] partss = fileUrl.split("utm_medium=copy_link");
                        new DownloadJson().execute(partss[0] + "__a=1");
                    }
                } else {

                    JSONObject jsonObjects = new JSONObject(result);
                    JSONObject jsonObject11 = jsonObjects.getJSONObject("graphql");
                    JSONObject jsonObject22 = jsonObject11.getJSONObject("shortcode_media");
                    try {
                        loudScreaming = jsonObject22.getString("video_url");
                        Log.e("Download URl  ", loudScreaming);
                    }catch(Exception e){
                        imgUrlss= new ArrayList<>();
                        JSONArray jsonArray=jsonObject22.getJSONArray("display_resources");
                        for (int i =0;i<jsonArray.length();i++){
                            JSONObject jsonObject3= jsonArray.getJSONObject(i);
                            src = jsonObject3.getString("src");
                            imgUrlss.add(src);
                        }

                    }

                    if(loudScreaming.equalsIgnoreCase("")){
                        try {
                            JSONObject jsonObject3 = jsonObject22.getJSONObject("edge_sidecar_to_children");
                            JSONArray jsonArray = jsonObject3.getJSONArray("edges");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject4 = jsonArray.getJSONObject(i).getJSONObject("node");
                                loudScreaming = jsonObject4.getString("video_url");
                                Log.e("video and post url ", loudScreaming);
                            }

                        }catch(Exception e) {
                            Intent intents = new Intent(HomeActivity.this, DownloadBackgroundService.class);
                            intents.putExtra("url", loudScreaming.substring(16));
                            intents.putExtra("path", mypath.toString());
                            startService(intents);

                        }

                    }else {

                        Intent intents = new Intent(HomeActivity.this, DownloadBackgroundService.class);
                        intents.putExtra("url", loudScreaming.substring(16));
                        intents.putExtra("path", mypath.toString());
                        startService(intents);
                    }




                }
            } catch(JSONException e){
                e.printStackTrace();
                alertpopup.dismiss();
                HomeActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Enter_URL.setText("");
                        Toast.makeText(HomeActivity.this,"Bad URL",Toast.LENGTH_LONG).show();
                    }
                });
            }

        }
    }

    private abstract class BackgroundTask {

        private Activity activity;
        public BackgroundTask(Activity activity) {
            this.activity = activity;
        }

        private void startBackground(final String url) {
            new Thread(new Runnable() {
                public void run() {

                    final String result=  doInBackground(url);
                    activity.runOnUiThread(new Runnable() {
                        public void run() {

                            onPostExecute(result);
                        }
                    });
                }
            }).start();
        }
        public void execute(String url){
            onPreExecute();
            startBackground(url);
        }
        public abstract void onPreExecute();
        public abstract String doInBackground(String url);
        public abstract void onPostExecute(String result);


    }
    @Override
    public void onBackPressed() {



        ViewGroup viewGroup = findViewById(android.R.id.content);
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.back_press_alert_layout, viewGroup, false);
        AlertDialog.Builder Alrtbuilder = new AlertDialog.Builder(this);
        Alrtbuilder.setView(dialogView);

        final AlertDialog alertDialog = Alrtbuilder.create();
        TextView rateus=(TextView)dialogView.findViewById(R.id.rateus_btn);
        TextView no=(TextView)dialogView.findViewById(R.id.no_btn);
        TextView yes=(TextView)dialogView.findViewById(R.id.yes_btn);


        final TemplateView[] template = new TemplateView[1];

        //Initializing the AdLoader   objects
        AdLoader adLoader = new AdLoader.Builder(HomeActivity.this, native_add).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {

            private ColorDrawable background;@Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                NativeTemplateStyle styles = new
                        NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                template[0] = dialogView.findViewById(R.id.nativeTemplateView);
                template[0].setStyles(styles);
                template[0].setNativeAd(unifiedNativeAd);
                dialogView.findViewById(R.id.nativeTemplateView).setVisibility(View.VISIBLE);
                // Showing a simple Toast message to user when Native an ad is Loaded and ready to show
                //   Toast.makeText(HomeActivity.this, "Native Ad is loaded ,now you can show the native ad  ", Toast.LENGTH_LONG).show();
            }

        }).build();


        // load Native Ad with the Request
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
                finish();
            }
        });



        alertDialog.show();

    }
}



