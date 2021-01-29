package com.example.fcmnotificationdemo12.FCM;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import androidx.constraintlayout.solver.widgets.Helper;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.fcmnotificationdemo12.MainActivity;
import com.example.fcmnotificationdemo12.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * NOTE: There can only be one service in each app that receives FCM messages. If multiple
 * are declared in the Manifest then the first one will be chosen.
 *
 * In order to make this Java sample functional, you must remove the following from the Kotlin messaging
 * service in the AndroidManifest.xml:
 *
 * <intent-filter>
 *   <action android:name="com.google.firebase.MESSAGING_EVENT" />
 * </intent-filter>
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @SuppressLint("WrongThread")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages
        // are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data
        // messages are the type
        // traditionally used with GCM. Notification messages are only received here in
        // onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated
        // notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages
        // containing both notification
        // and data payloads are treated as notification messages. The Firebase console always
        // sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
//        Log.e(TAG, "From: " + remoteMessage.getFrom());
//
//        // Check if message contains a data payload.
//        if (remoteMessage.getData().size() > 0) {
//            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
//
//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use WorkManager.
//                scheduleJob();
//            } else {
//                // Handle message within 10 seconds
//                handleNow();
//            }
//
//        }
//
//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Message Notification Body: " + remoteMessage.getData().get("body"));

            String bodymssage=remoteMessage.getNotification().getBody();

            try {
                JSONObject jsonObject= new JSONObject(bodymssage.replaceAll("\\n",""));
                JSONObject jsonObject1= jsonObject.getJSONObject("data");
                String title=jsonObject1.getString("title");
                String body=jsonObject1.getString("body");
                String image=jsonObject1.getString("image");
                String update=jsonObject1.getString("update");
                new sendNotification(this,title,body,image,update).execute();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



       // sendNotification(remoteMessage.getNotification().getBody());


        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
  //  }
    // [END receive_message]


    // [START on_new_token]
    /**
     * There are two scenarios when onNewToken is called:
     * 1) When a new token is generated on initial app startup
     * 2) Whenever an existing token is changed
     * Under #2, there are three scenarios when the existing token is changed:
     * A) App is restored to a new device
     * B) User uninstalls/reinstalls the app
     * C) User clears app data
     */
    @Override
    public void onNewToken(String token) {
        Log.e(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token);
    }
    // [END on_new_token]

    /**
     * Schedule async work using WorkManager.
     */
    private void scheduleJob() {
        // [START dispatch_job]
        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class)
                .build();
        WorkManager.getInstance().beginWith(work).enqueue();
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.e(TAG, "Short lived task is done.");
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM registration token with any
     * server-side account maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
    private class sendNotification extends AsyncTask<String, Void, Bitmap> {

        Context ctx;
        String title;
        String body;
        String image;
        String update;



        public sendNotification(Context ctx, String title, String body, String image, String update) {
            this.ctx = ctx;
            this.title = title;
            this.body = body;
            this.image = image;
            this.update = update;

        }

        @Override
        protected Bitmap doInBackground(String... params) {

            InputStream in;

            try {

                URL url = new URL(image);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e("Url exception",e+"url exception");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("IO exception",e+"IO exception");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {

            super.onPostExecute(result);
            try {
                if (result==null || result.toString()=="")
                {
                    Log.e("bitmap","bitmap null or blank");
                    new sendNotification(ctx,title,body,image,update).execute();
//                    result = BitmapFactory.decodeResource(ctx.getResources(),
//                            R.drawable.bg);
                }else {
                    Log.e("bitmap", "got bitmap");

                    String channelId = getString(R.string.default_notification_channel_id);
                    String channername = getString(R.string.default_notification_channel_name);
                    NotificationCompat.BigPictureStyle bpStyle = new NotificationCompat.BigPictureStyle();
                    bpStyle.bigPicture(result).build();
                    // Set the intent to fire when the user taps on notification.
                    Intent intent = new Intent(ctx, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0 /* Request code */, intent,
                            PendingIntent.FLAG_ONE_SHOT);
                    NotificationCompat.Builder mBuilder;
                    Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    long[] v = {500, 1000};

                    Bitmap icon = BitmapFactory.decodeResource(ctx.getResources(),
                            R.drawable.ic_launcher_background);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        Log.e("Heighr orio", "Heighr Orio");
                        if (update.equalsIgnoreCase("Yes")) {

                            mBuilder = new NotificationCompat.Builder(ctx, channelId)
                                    .setSmallIcon(R.drawable.ic_launcher_background)
                                    .setContentTitle(title)
                                    .setContentText(body)
                                    .setSound(uri)
                                    .setVibrate(v)
                                    .setLargeIcon(icon)
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                    .addAction(0, "Update", pendingIntent)
                                    .setColor(ContextCompat.getColor(ctx, R.color.colorAccent))
                                    .setStyle(bpStyle);
                        } else {
                            mBuilder = new NotificationCompat.Builder(ctx, channelId)
                                    .setSmallIcon(R.drawable.ic_launcher_background)
                                    .setContentTitle(title)
                                    .setContentText(body)
                                    .setSound(uri)
                                    .setVibrate(v)
                                    .setLargeIcon(icon)
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                    .setColor(ContextCompat.getColor(ctx, R.color.colorAccent))
                                    .setStyle(bpStyle);
                        }

                        try {

                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();
                            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            // default pattern goes here
                            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));

                        } catch (Exception e) {
                            Log.e("Error Playing sound ", "error");
                            e.printStackTrace();
                        }

                    } else {
                        Log.e("Below orio", "Below Orio");
                        if (update.equalsIgnoreCase("Yes")) {
                            mBuilder = new NotificationCompat.Builder(ctx, channelId)
                                    .setSmallIcon(R.drawable.ic_launcher_background)
                                    .setContentTitle(title)
                                    //.setSubText(body)
                                    .setContentText(body)
                                    .setSound(uri)
                                    .setVibrate(v)
                                    .setLargeIcon(icon)
                                    .addAction(0, "Update", pendingIntent)
                                    .setColor(ContextCompat.getColor(ctx, R.color.colorAccent))
                                    .setStyle(bpStyle);
                        } else {
                            mBuilder = new NotificationCompat.Builder(ctx, channelId)
                                    .setSmallIcon(R.drawable.ic_launcher_background)
                                    .setContentTitle(title)
                                    //.setSubText(body)
                                    .setContentText(body)
                                    .setSound(uri)
                                    .setVibrate(v)
                                    .setLargeIcon(icon)
                                    .setColor(ContextCompat.getColor(ctx, R.color.colorAccent))
                                    .setStyle(bpStyle);
                        }
                    }
                    mBuilder.setContentIntent(pendingIntent);
                    // Sets an ID for the notification
                    int mNotificationId = 001;

                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    NotificationChannel mNotificationChannel = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        mNotificationChannel = new NotificationChannel(channelId, channername, NotificationManager.IMPORTANCE_HIGH);
                        mNotificationChannel.setVibrationPattern(new long[]{0});
                        mNotificationChannel.enableVibration(true);
                        notificationManager.createNotificationChannel(mNotificationChannel);
                    }


                    // It will display the notification in notification bar
                    notificationManager.notify(mNotificationId, mBuilder.build());


                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}