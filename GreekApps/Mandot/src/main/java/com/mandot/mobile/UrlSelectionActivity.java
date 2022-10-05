package com.mandot.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.acumengroup.greekmain.core.network.WSHandler;

public class UrlSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_selection);
        Button tester_secure = (Button) findViewById(R.id.tester_secure);
        Button tester_non_secure = (Button) findViewById(R.id.tester_non_secure);
        Button developergreeksoftin = (Button) findViewById(R.id.developergreeksoftin);
        Button developer_non_secure = (Button) findViewById(R.id.developer_non_secure);
        Button dynu_secure = (Button) findViewById(R.id.dynu_secure);
        Button mandot_non_secure = (Button) findViewById(R.id.mandot_non_secure);
        Button satendra_non_secure = (Button) findViewById(R.id.satendra_non_secure);
        Button fiftin_server = (Button) findViewById(R.id.fiftin_server);
        Button seventeenfour_server = (Button) findViewById(R.id.seventeenfour_server);
        Button satendra_secure = (Button) findViewById(R.id.satendra_secure);
        EditText urlinput = (EditText) findViewById(R.id.urlinput);
        Button flutter_secure = (Button) findViewById(R.id.flutter_secure);

        Button submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(view -> {
            String url =urlinput.getText().toString().trim();
            if(url.equalsIgnoreCase("")){
                Toast.makeText(this,"Please provide valid url",Toast.LENGTH_LONG).show();
            }else{
                if(url.contains("http")|| url.contains("https")){
                    setUrl(urlinput.getText().toString().trim());
                }else{
                    Toast.makeText(this,"Please provide valid url",Toast.LENGTH_LONG).show();
                }
            }

        });
        tester_secure.setOnClickListener(view -> {
            setUrl("https://tester.greeksoft.in");
         });

        tester_non_secure.setOnClickListener(view -> {
            setUrl("http://tester.greeksoft.in");
         });
        developergreeksoftin.setOnClickListener(view -> {
            setUrl("https://developer.greeksoft.in");
         });
        developer_non_secure.setOnClickListener(view -> {
            setUrl("http://developer.greeksoft.in");
         });
        satendra_non_secure.setOnClickListener(view -> {
            setUrl("http://192.168.209.116:3000");
        });
        satendra_secure.setOnClickListener(view -> {
            setUrl("https://192.168.209.116:3000");
        });
        fiftin_server.setOnClickListener(view -> {
            setUrl("http://192.168.209.15:3000");
        });
        seventeenfour_server.setOnClickListener(view -> {
            setUrl("http://182.76.70.74:3000");
        });
        dynu_secure.setOnClickListener(view -> {
            setUrl("https://greeksoft.dynu.com");
        });
        mandot_non_secure.setOnClickListener(view -> {
            setUrl("https://trade.mandotsecurities.com");
        });
        flutter_secure.setOnClickListener(view -> {
            setUrl("https://flutter.greeksoft.in:3000");
        });


    }

    private void setUrl(String url) {
        WSHandler.LIVE_URL=url;
        if(url.equalsIgnoreCase("https://trade.mandotsecurities.com")){
            WSHandler.VERSION_NO="1.0.1.11";
        }else{
            WSHandler.VERSION_NO="1.0.1.10";
        }
        Intent intent= new Intent(this,SplashActivity.class);
        startActivity(intent);
        finish();
    }
}