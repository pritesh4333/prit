package com.prit.videocompressorpro.Fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import com.prit.videocompressorpro.R;
import com.prit.videocompressorpro.View.MainActivity;

import androidx.core.content.ContextCompat;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static com.prit.videocompressorpro.View.MainActivity.MY_PREFS_NAME;

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
    public static final String MY_PREFS_NAME = "VideoCompressPrefsFile";
    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
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


        Map<String, String> data = remoteMessage.getData();
        String title = data.get("title");
        String body = data.get("body");
        String image = data.get("image");
        String update=data.get("update");
        String admob_app_id=data.get("admob_app_id");
        String banner_home_footer=data.get("banner_home_footer");
        String interstitial_full_screen=data.get("interstitial_full_screen");
        String natice_advanceadd=data.get("natice_advanceadd");


        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String Default_admob_app_id = prefs.getString("admob_app_id", "");



        if (!Default_admob_app_id.equalsIgnoreCase(admob_app_id)) {

            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString("admob_app_id", admob_app_id);
            editor.putString("banner_home_footer", banner_home_footer);
            editor.putString("interstitial_full_screen", interstitial_full_screen);
            editor.putString("natice_advanceadd", natice_advanceadd);
            editor.commit();
            Log.e("Update Adsend IDs","Done");
        }

        sendNotification( title,body,image,update);
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]


    // [START on_new_token]

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */

    // [END on_new_token]

    /**
     * Schedule async work using WorkManager.
     */


    /**
     * Handle time allotted to BroadcastReceivers.
     */


    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */

    /**
     * Create and show a simple notification containing the received FCM message.

     */
    private void sendNotification( String title, String body, String image,String update) {



        String channelId = getString(R.string.default_notification_channel_id);
        String channername=getString(R.string.default_notification_channel_name);
        NotificationCompat.BigPictureStyle bpStyle = new NotificationCompat.BigPictureStyle();
        bpStyle.bigPicture(getBitmapFromURL(image)).build();
        // Set the intent to fire when the user taps on notification.
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder mBuilder ;
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] v = {500,1000};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            if (update.equalsIgnoreCase("Yes")) {

                mBuilder = new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.notification_icon2)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setSound(uri)
                        .setVibrate(v)
                        .setLargeIcon(getBitmapFromURL(image))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .addAction(0, "Update", pendingIntent)
                        .setColor(ContextCompat.getColor(this, R.color.green))
                        .setStyle(bpStyle);
            }else{
                mBuilder = new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.notification_icon2)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setSound(uri)
                        .setVibrate(v)
                        .setLargeIcon(getBitmapFromURL(image))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setColor(ContextCompat.getColor(this, R.color.green))
                        .setStyle(bpStyle);
            }

            try {

                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
                Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                // default pattern goes here
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            if (update.equalsIgnoreCase("Yes")) {
                mBuilder = new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.notification_icon2)
                        .setContentTitle(title)
                        .setSubText(body)
                        .setSound(uri)
                        .setVibrate(v)
                        .setLargeIcon(getBitmapFromURL(image))
                        .addAction(0, "Update", pendingIntent)
                        .setColor(ContextCompat.getColor(this, R.color.green))
                        .setStyle(bpStyle);
            }else{
                mBuilder = new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.notification_icon2)
                        .setContentTitle(title)
                        .setSubText(body)
                        .setSound(uri)
                        .setVibrate(v)
                        .setLargeIcon(getBitmapFromURL(image))
                        .setColor(ContextCompat.getColor(this, R.color.green))
                        .setStyle(bpStyle);
            }
        }
        mBuilder.setContentIntent(pendingIntent);
        // Sets an ID for the notification
        int mNotificationId = 001;
        AudioAttributes audioAttributes = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build();
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel mNotificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mNotificationChannel = new NotificationChannel(channelId,channername, NotificationManager.IMPORTANCE_HIGH);
            mNotificationChannel.setVibrationPattern(new long[]{ 0 });
            mNotificationChannel.enableVibration(true);
            mNotificationChannel.setSound(uri, audioAttributes);
            notificationManager.createNotificationChannel(mNotificationChannel);
        }



        // It will display the notification in notification bar
        notificationManager.notify(mNotificationId, mBuilder.build());

    }
    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
