package warehousedelivery.taaxgenie.in.resagitpvtltd;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.developers.imagezipper.ImageZipper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;


public class MainActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar;
    private File actualImage;
    private static final int PICK_IMAGE_REQUEST = 1;
    public static final int PERMISSION_READ = 123;
    JSONObject image;
    public static String [] imgaraydata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            webView = (WebView) findViewById(R.id.web);
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            webView.loadUrl("https://news.google.com");
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setHorizontalScrollBarEnabled(false);
            initWebView();


            final String[] NECESSARY_PERMISSIONS = new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};


            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Log.e("log", "Permission: grandted");

                }

            } else {

                //ask for permission
                // Toast.makeText(getApplicationContext(), "permission denied storage", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        NECESSARY_PERMISSIONS, 123);
            }


        } catch (Exception e) {

        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                    Log.e("log", "Permission: granted" + permissions[0] + "was " + grantResults[0]);


                }

            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onBackPressed() {

        exit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 1, Menu.NONE, "New URL");
        menu.add(Menu.NONE, 2, Menu.NONE, "New Tab");
        menu.add(Menu.NONE, 3, Menu.NONE, "Send URL");
        menu.add(Menu.NONE, 4, Menu.NONE, "Get URL");
        menu.add(Menu.NONE, 5, Menu.NONE, "Refresh");
        menu.add(Menu.NONE, 6, Menu.NONE, "Exit");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:

                EnterPassword("GetMessage");
                return true;
            case 2:

                EnterPassword("SendMessage");

                return true;
            case 3:

                EnterPassword("SendImage");

                return true;
            case 4:

                EnterPassword("GetImage");

                return true;
            case 5:
                Toast.makeText(getApplicationContext(), "Refresh", Toast.LENGTH_SHORT).show();
                return true;

            case 6:
                exit();
                return true;


            default:
                return false;
        }
    }

    private void EnterPassword(final String sendMessage) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter URL");

        final EditText input = new EditText(MainActivity.this);
        input.setHint("Password");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (input.getText().toString().equalsIgnoreCase("4333")) {

                    if (sendMessage.equalsIgnoreCase("GetMessage")) {
                        getMessageFromServer();
                    } else if (sendMessage.equalsIgnoreCase("SendMessage")) {
                        GetMesage();
                    } else if (sendMessage.equalsIgnoreCase("SendImage")) {
                        SendImage();
                    } else if (sendMessage.equalsIgnoreCase("GetImage")) {
                        GetImage();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Password " + input.getText().toString(), Toast.LENGTH_SHORT).show();
                }

            }


        });
        builder.show();
    }

    private void GetImage() {

        try {
            final ProgressDialog pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading...");
            pDialog.show();
            String url = "https://priteshparmarnew.000webhostapp.com/sendimageandrecive/Image.txt";
            Log.e("url", "" + url);


            StringRequest strReq = new StringRequest(Request.Method.GET,
                    url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {



                        pDialog.hide();




                        String[] vowels = response.split("\\{\"icon\":\"");
                        imgaraydata = new String[vowels.length];
                        for (int i=1;i<vowels.length;i++){
                            String imgaray=vowels[i].substring(0, vowels[i].length() - 2);
                            imgaraydata[i]=imgaray;
                        }

                        List<String> list = new ArrayList<String>(Arrays.asList(imgaraydata));
                        list.remove(0);
                        imgaraydata = list.toArray(new String[0]);

                        Log.e("response array list",""+imgaraydata);

                        Showimagesinlist();

                    } catch (Exception e) {
                        Log.e("api error", e.getMessage());
                        GetImage();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    GetImage();

                }
            });

// Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq);
        } catch (Exception e) {
            Log.e("eror", e.getMessage());
        }

    }

    private void Showimagesinlist() {
        Intent i = new Intent(MainActivity.this,Imagelist.class);
        startActivity(i);
    }

    private void SendImage() {

        getImageFromGallery();


    }

    private void getImageFromGallery() {



        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Faild to open", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                actualImage = FileUtil.from(this, data.getData());
                File actualFile = new File(String.valueOf(actualImage));
                setCompressedImage(actualFile);
            } catch (IOException e) {
                Toast.makeText(this, "Faild to read", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    private void setCompressedImage(File actualFile) {
        try {
            File imageZipperFile = new ImageZipper(MainActivity.this).setQuality(90).setMaxWidth(1000).setMaxHeight(1000).compressToFile(actualFile);
            //           int result_file_size = Integer.parseInt(String.valueOf(imageZipperFile.length() / 1024));
            //           Toast.makeText(mContext, "" + result_file_size + " Kb", Toast.LENGTH_LONG).show();
            Bitmap b = BitmapFactory.decodeFile(imageZipperFile.getAbsolutePath());


            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String images = Base64.encodeToString(byteArray, Base64.DEFAULT);
            image = new JSONObject();
            images = images.replaceAll("\n", "");
            image.put("icon", images.replaceAll("\\\\",""));
            sendImagetoServer();


        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
    }

    private void sendImagetoServer() {
        try {
            final ProgressDialog pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading Please Wait...");
            pDialog.show();

            String url = "https://priteshparmarnew.000webhostapp.com/sendimageandrecive/sendimage.php";
            Log.e("url", "" + image);


            JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST,
                    url, image, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    pDialog.dismiss();
                    Log.e("Respose", response.toString());
                    try {
                        String json = response.getString("status");
                        if (json.equalsIgnoreCase("Y")) {
                            Toast.makeText(MainActivity.this, "Succsess", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Log.e("server error", "" + e.getMessage());
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.dismiss();
                    VolleyLog.e("TAG", "Error: " + error.getMessage());
                    sendImagetoServer();
                }

            }) {


                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("apiKey", "xxxxxxxxxxxxxxx");
                    return headers;
                }
            };


// Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq);
        } catch (Exception e) {

        }
    }

    private void GetMesage() {
        final View dialogView;
        final AlertDialog alertDialog;
        final String[] team = new String[1];

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        dialogView = inflater.inflate(R.layout.alert_label_editor, null);
        dialogBuilder.setView(dialogView);
        final String[] Rmsgs = {""};
        final EditText editText = (EditText) dialogView.findViewById(R.id.msg);
        RadioGroup rg = (RadioGroup) dialogView.findViewById(R.id.grps);


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rb = (RadioButton) dialogView.findViewById(checkedId);
                team[0] = rb.getText().toString();
                Toast.makeText(getBaseContext(), team[0], Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        Button send = (Button) dialogView.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject json = new JSONObject();
                    json.put("Message", editText.getText().toString());
                    json.put("Reason", team[0]);
                    json.put("Time", getdateandTime());
                    sendMessageToserver(json);
                    alertDialog.dismiss();
                } catch (Exception e) {

                }
            }
        });


    }

    private String getdateandTime() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.S aa");
        String formattedDate = dateFormat.format(new Date()).toString();
        System.out.println(formattedDate);
        return formattedDate;
    }

    private void sendMessageToserver(JSONObject json) {

        try {
            final ProgressDialog pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading Please Wait...");
            pDialog.show();

            String url = "https://priteshparmarnew.000webhostapp.com/TextFiles/writeMsg.php";
            Log.e("url", "" + url);


            JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST,
                    url, json, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.e("Respose", response.toString());


                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.dismiss();
                    VolleyLog.e("TAG", "Error: " + error.getMessage());

                }

            }) {


                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("apiKey", "xxxxxxxxxxxxxxx");
                    return headers;
                }
            };


// Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq);
        } catch (Exception e) {

        }
    }

    private void getMessageFromServer() {

        try {
            final ProgressDialog pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading...");
            pDialog.show();
            String url = "https://priteshparmarnew.000webhostapp.com/ReadMsg.php";
            Log.e("url", "" + url);


            StringRequest strReq = new StringRequest(Request.Method.GET,
                    url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        Log.e("CallLog res", response);
                        pDialog.hide();
                        JSONObject jsonObject = new JSONObject(response);

                        String msg = jsonObject.getString("Message");
                        String Reason = "";
                        try {
                            Reason = jsonObject.getString("Reason");
                        } catch (Exception e) {

                        }
                        String time = jsonObject.getString("Time");

                        Log.e("Get Message From Server", msg);


                        ShowMessageinBox(msg, time, Reason);


                    } catch (JSONException e) {
                        Log.e("api error", e.getMessage());
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {


                }
            });

// Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq);
        } catch (Exception e) {
            Log.e("eror", e.getMessage());
        }


    }

    private void ShowMessageinBox(String msg, String time, String reson) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mesage");
        builder.setMessage("Message:-" + msg + " \nReason:-" + reson + " \nTime:-" + time);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setCancelable(false);
        builder.show();
    }


    private void initWebView() {
        try {
            webView.setWebChromeClient(new MyWebChromeClient(this));
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    progressBar.setVisibility(View.VISIBLE);
                    invalidateOptionsMenu();
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    webView.loadUrl(url);
                    return true;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    progressBar.setVisibility(View.GONE);
                    invalidateOptionsMenu();
                }

                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                    progressBar.setVisibility(View.GONE);
                    invalidateOptionsMenu();
                }
            });

        } catch (Exception e) {
            webView.clearCache(true);
            webView.clearHistory();
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setHorizontalScrollBarEnabled(false);
        }
    }

    public void exit() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.app_name);
            builder.setIcon(R.mipmap.download);
            builder.setMessage("Do you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception e) {

        }
    }


    private class MyWebChromeClient extends WebChromeClient {
        Context context;

        public MyWebChromeClient(Context context) {
            super();
            this.context = context;
        }
    }

    private boolean isAppInForeground(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
            ActivityManager.RunningTaskInfo foregroundTaskInfo = am.getRunningTasks(1).get(0);
            String foregroundTaskPackageName = foregroundTaskInfo.topActivity.getPackageName();

            return foregroundTaskPackageName.toLowerCase().equals(context.getPackageName().toLowerCase());
        } else {
            ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
            ActivityManager.getMyMemoryState(appProcessInfo);
            if (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE) {
                return true;
            }

            KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            // App is foreground, but screen is locked, so show notification
            return km.inKeyguardRestrictedInputMode();
        }
    }

}
