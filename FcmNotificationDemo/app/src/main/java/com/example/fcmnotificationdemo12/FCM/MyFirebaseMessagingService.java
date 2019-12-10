package com.example.fcmnotificationdemo12.FCM;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.fcmnotificationdemo12.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;


/**
     * Created by Belal on 12/8/2017.
     */

//class extending FirebaseMessagingService
    public class MyFirebaseMessagingService extends FirebaseMessagingService {
        String CHANNEL_ID = "my_channel_01";
        String CHANNEL_NAME = "Simplified Coding Notification";
        String CHANNEL_DESCRIPTION = "www.simplifiedcoding.net";
        @Override
        public void onMessageReceived(RemoteMessage remoteMessage) {
            super.onMessageReceived(remoteMessage);

            //if the message contains data payload
            //It is a map of custom keyvalues
            //we can read it easily
            if(remoteMessage.getData().size() > 0){
                //handle the data message here
            }

            //getting the title and the body
            Collection<String> title = remoteMessage.getData().values();


            String titles= (String) title.toArray()[1];
            String body = (String) title.toArray()[2];
            String img=(String)title.toArray()[0];
            Log.e("title",titles);
            Log.e("body",body);
            sendNotification(titles,getBitmapfromUrl(img),body,remoteMessage.getMessageId());
       //     createNotification(titles,body,img);
            //then here we can use the title and body to build a notification
        }
//    //Simple method for image downloading
//    public Bitmap getBitmapfromUrl(String imageUrl) {
//        try {
//            URL url = new URL(imageUrl);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            return BitmapFactory.decodeStream(input);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//    private void createNotification(String title, String messageBody, String img) {
//        Bitmap bitmap = getBitmapfromUrl(img); //obtain the image
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//
//            NotificationManager mNotificationManager =
//                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
//
//            mChannel.setDescription(CHANNEL_DESCRIPTION);
//            mChannel.enableLights(true);
//            mChannel.setLightColor(Color.RED);
//            mChannel.enableVibration(true);
//            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//            mNotificationManager.createNotificationChannel(mChannel);
//
//
//        }
//
//        MyNotificationManager.getInstance(this).displayNotification(title, messageBody);
//    }

    private void sendNotification(String message, Bitmap image, String title,String msg_id) {
        int notifyID = 0;
        try {
            notifyID = Integer.parseInt(msg_id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

              // The id of the channel.
        Intent intent = new Intent(this, MyFirebaseMessagingService.class);
        intent.putExtra("title", title);
        intent.putExtra("message", message);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "01")
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setChannelId(CHANNEL_ID)
                .setContentIntent(pendingIntent);

        if (image != null) {
            notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle()   //Set the Image in Big picture Style with text.
                    .bigPicture(image)
                    .setSummaryText(message)
                    .bigLargeIcon(null));
        }


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {       // For Oreo and greater than it, we required Notification Channel.
                           // The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, importance); //Create Notification Channel
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(notifyID /* ID of notification */, notificationBuilder.build());
    }

    public Bitmap getBitmapfromUrl(String imageUrl) {     //This method returns the Bitmap from Url;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }

    }
}
