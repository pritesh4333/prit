package warehousedelivery.taaxgenie.in.resagitpvtltd;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class LoginPage extends AppCompatActivity {

    private static final String TAG =LoginPage.class.getSimpleName() ;
    private Context context;
    private TextView server,version,signin;
    private String user = "", pass = "";
    private EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        signin = findViewById(R.id.signin);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
//        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
//        scale.setDuration(90000);
//        scale.setInterpolator(new OvershootInterpolator());
//        signin.setAnimation(scale);3



        final String[] NECESSARY_PERMISSIONS = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(LoginPage.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(LoginPage.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                Log.e("permistion ", "accepted");
            }else{
                ActivityCompat.requestPermissions(
                        LoginPage.this,
                        NECESSARY_PERMISSIONS, 123);
            }

            } else {

                //ask for permission
                // Toast.makeText(getApplicationContext(), "permission denied storage", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(
                        LoginPage.this,
                        NECESSARY_PERMISSIONS, 123);
            }
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             String   usernames = username.getText().toString();
               String  passwords = password.getText().toString();
                if(usernames.equalsIgnoreCase("4333")&&passwords.equalsIgnoreCase("4333")) {
                    Intent i = new Intent(LoginPage.this,CalllogOrMap.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"Server Not Connected",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED  &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
                    Log.e("log", "Permission: " + permissions[0] + "was " + grantResults[0]);


                }

            }
        }


    @Override
    public void onBackPressed() {

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginPage.this);
        alertDialogBuilder.setTitle("Exit");
        alertDialogBuilder.setMessage("Are you sure you want to exit?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        finish();
                        System.exit(0);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }



}
