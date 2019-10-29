package com.example.fcmnotificationdemo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity1 extends AppCompatActivity {
    String CHANNEL_ID = "my_channel_01";
    String CHANNEL_NAME = "Simplified Coding Notification";
    String CHANNEL_DESCRIPTION = "www.simplifiedcoding.net";
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
         spinner = findViewById(R.id.spinnerTopics);

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
        MyNotificationManager.getInstance(this).displayNotification("Greetings", "Hello how are you?");
    }
}
