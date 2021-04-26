package app.reelsdownloadervideo.storydownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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
import java.net.URL;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    Boolean permistion = false;
    private static final int REQUEST_PERMISSIONS_CODE = 100;
    EditText Enter_URL;
    ProgressBar progressbar;
      TextView progresstext;
    AlertDialog alertpopup;
    View progress_dailog;
    ArrayList<String> imgUrlss=new ArrayList<>();
    File mypath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_main);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getUI();
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

                    Intent ii = new Intent(HomeActivity.this, MyReelsList.class);
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
                        Enter_URL.setError("Please Enter Enter_URL");
                    } else if (!Enter_URL.getText().toString().trim().contains("www.instagram.com/reel") &&
                            !Enter_URL.getText().toString().trim().contains("www.instagram.com/tv")
                    ) {
                        Enter_URL.setError("Provide Instagram Reel or IGTV URL");
                    } else if (!checkInternetConnection(HomeActivity.this)) {
                        Toast.makeText(HomeActivity.this, "Please check Internet connection", Toast.LENGTH_LONG).show();
                    } else {
                        LocalBroadcastManager bManagers = LocalBroadcastManager.getInstance(HomeActivity.this);
                        IntentFilter intentFilters = new IntentFilter();
                        intentFilters.addAction("message_progress");
                        bManagers.registerReceiver(broadcastReceiver, intentFilters);

                        try {
                              mypath = new File(Environment.getExternalStorageDirectory(), "Reels-Video-Downloader");

                            if (!mypath.exists()) {
                                if (!mypath.mkdirs()) {
                                }
                            }
                        } catch (Exception e) {
                        }

                        InputMethodManager immn = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                        immn.hideSoftInputFromWindow(download_btn.getWindowToken(), 0);

                        String fileUrl = Enter_URL.getText().toString().trim();

                        String[] parts = fileUrl.split("igshid");

                        new DownloadJson().execute(parts[0] + "__a=1");
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


            alertpopup.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;

            BufferedReader reader = null;
            try {
                java.net.URL urls = new URL(params[0]);

                connection = (HttpURLConnection) urls.openConnection();
                connection.connect();


                InputStream streams = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(streams));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                }

                return buffer.toString();


            } catch (MalformedURLException e) {

            } catch (IOException e) {

            } finally {
                if (connection != null) {
                    connection.disconnect();
                }

                try {

                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
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
                        String[] partss = fileUrl.split("igshid");
                         new DownloadJson().execute(partss[0] + "__a=1");
                    }
                } else {

                    JSONObject jsonObjects = new JSONObject(result);
                    JSONObject jsonObject11 = jsonObjects.getJSONObject("graphql");
                    JSONObject jsonObject22 = jsonObject11.getJSONObject("shortcode_media");
                    try {
                        loudScreaming = jsonObject22.getString("video_url");
                    }catch(Exception e){
                        imgUrlss= new ArrayList<>();
                        JSONArray jsonArray=jsonObject22.getJSONArray("display_resources");
                        for (int i =0;i<jsonArray.length();i++){
                            JSONObject jsonObject3= jsonArray.getJSONObject(i);
                            src = jsonObject3.getString("src");
                            imgUrlss.add(src);
                         }

                    }
                    Intent intents = new Intent(HomeActivity.this, DownloadBackgroundService.class);
                    intents.putExtra("url",loudScreaming.substring(39));
                    intents.putExtra("path",mypath.toString());
                    startService(intents);





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
}



