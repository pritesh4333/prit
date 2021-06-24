package com.acumengroup.mobile.messaging;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.acumengroup.mobile.R;
import com.acumengroup.mobile.init.SplashActivity;
import com.acumengroup.mobile.messaging.NotifyRegistrationIntentService;
import com.acumengroup.greekmain.util.Util;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by User on 8/10/2016.
 */
public class NotifyPushReceiverService extends FirebaseMessagingService {
    private final static String GROUP_KEY_MESSAGES = "group_key_messages";
    public static int notificationId = 0;
    private NotificationManager notificationManager;
    public static final String REGISTRATION_SUCCESS = "RegistrationSuccess";
    public static int count = 0;

    @Override
    public void onNewToken(String token) {
        Log.e("NotifyPush", "Refreshed token:==========>>> " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.


        Intent registrationComplete = null;
        FirebaseInstanceId instanceID = FirebaseInstanceId.getInstance();
        SharedPreferences.Editor editor = Util.getPrefs(getApplicationContext()).edit();
        editor.putString("GCMToken", token);
        editor.commit();
        //notify to UI that registration complete success
        registrationComplete = new Intent(REGISTRATION_SUCCESS);
        registrationComplete.putExtra("token", token);
        Intent intent = new Intent(this, NotifyRegistrationIntentService.class);
        startService(intent);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e("", "Message data payload: " + remoteMessage.getData());

        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e("", "FCM Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());

        }
        //String message = remoteMessage.getNotification().getBody();
        String message = remoteMessage.getData().get("body");

        if (message != null && message.length() > 0) {
            sendNotification(message);
        }

    }

    private void sendNotification(String message) {
        notificationId++;
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("isProceedFrom", NAV_TO_NOTIFICATION_SCREEN);
        intent.putExtra("isProceedFrom", Intent.FLAG_ACTIVITY_NEW_TASK);
        String id = "01";
        String title = "my_channel_title_01";
        int requestID = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, notificationId, intent, 0);
        Log.e("Message", "Message Notification: " + id + "----------" + title);
        count = Util.getPrefs(this).getInt("fcmcount", 0);
        count++;
        SharedPreferences.Editor editor = Util.getPrefs(getApplicationContext()).edit();
        editor.putInt("fcmcount", count);
        //editor.putInt("NotificationVal", 1012);
        editor.commit();
        editor.apply();
        Log.e("count", "count Notification: " + count);
        //Setup notification
        //Sound
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Build notification
        NotificationCompat.Builder builder;
        //if (notificationManager == null) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //}
        //notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder;
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] v = {500, 1000};

        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();

        bigTextStyle.bigText(message);
        String appname = Util.getPrefs(this).getString("AppName", "");
        if (appname.equalsIgnoreCase("Vishwas")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;

                NotificationChannel mChannel = null;//= notificationManager.getNotificationChannel(id);

                if (mChannel == null) {
                    mChannel = new NotificationChannel(id, title, importance);
                    mChannel.enableVibration(true);
                    mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    mChannel.setShowBadge(true);
                    notificationManager.createNotificationChannel(mChannel);
                }

                builder = new NotificationCompat.Builder(getApplicationContext(), id);
                builder.setAutoCancel(true)
                        .setSmallIcon(R.mipmap.vishwas_launcher)
                        .setLargeIcon(drawableToBitmap(getResources().getDrawable(R.mipmap.vishwas_launcher)))
                        .setStyle(bigTextStyle)
                        .setAutoCancel(true)
                        .setGroup(GROUP_KEY_MESSAGES)
                        .setGroupSummary(true)
                        .setContentTitle("Vishwas")
                        .setChannelId(id)
                        .setVibrate(v)
                        .setSound(uri)
                        .setNumber(count)
                        .setDefaults(0)
                        .setOngoing(true)
                        .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                        .setContentIntent(pendingIntent);
            } else {
                builder = new NotificationCompat.Builder(this, id)
                        .setAutoCancel(true)
                        .setSmallIcon(R.mipmap.vishwas_launcher)
                        .setLargeIcon(drawableToBitmap(getResources().getDrawable(R.mipmap.vishwas_launcher)))
                        .setStyle(bigTextStyle)
                        .setAutoCancel(true)
                        .setGroup(GROUP_KEY_MESSAGES)
                        .setGroupSummary(true)
                        .setContentTitle("Vishwas")
                        .setChannelId(id)
                        .setSound(uri)
                        .setVibrate(v)
                        .setDefaults(0)
                        .setOngoing(true)
                        .setNumber(count)
                        .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                        .setContentIntent(pendingIntent);
            }

            notificationManager.notify(requestID, builder.build()); //0 = ID of notification
        }
        else if (appname.equalsIgnoreCase("Trade On Go")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;

                NotificationChannel mChannel = null;//= notificationManager.getNotificationChannel(id);

                if (mChannel == null) {
                    mChannel = new NotificationChannel(id, title, importance);
                    mChannel.enableVibration(true);
                    mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    mChannel.setShowBadge(true);
                    notificationManager.createNotificationChannel(mChannel);
                }

                builder = new NotificationCompat.Builder(getApplicationContext(), id);
                builder.setAutoCancel(true)
                        .setSmallIcon(R.drawable.philips_login_icon)
                        .setLargeIcon(drawableToBitmap(getResources().getDrawable(R.mipmap.phillips_launcher)))
                        .setStyle(bigTextStyle)
                        .setAutoCancel(true)
                        .setGroup(GROUP_KEY_MESSAGES)
                        .setGroupSummary(true)
                        .setContentTitle("Trade On Go")
                        .setChannelId(id)
                        .setVibrate(v)
                        .setSound(uri)
                        .setNumber(count)
                        .setDefaults(0)
                        .setOngoing(true)
                        .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                        .setContentIntent(pendingIntent);
            } else {
                builder = new NotificationCompat.Builder(this, id)
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.philips_login_icon)
                        .setLargeIcon(drawableToBitmap(getResources().getDrawable(R.mipmap.phillips_launcher)))
                        .setStyle(bigTextStyle)
                        .setAutoCancel(true)
                        .setGroup(GROUP_KEY_MESSAGES)
                        .setGroupSummary(true)
                        .setContentTitle("Trade On Go")
                        .setChannelId(id)
                        .setSound(uri)
                        .setVibrate(v)
                        .setDefaults(0)
                        .setOngoing(true)
                        .setNumber(count)
                        .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                        .setContentIntent(pendingIntent);
            }
            notificationManager.notify(requestID, builder.build()); //0 = ID of notification
        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;

                NotificationChannel mChannel = null;//= notificationManager.getNotificationChannel(id);

                if (mChannel == null) {
                    mChannel = new NotificationChannel(id, title, importance);
                    mChannel.enableVibration(true);
                    mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    mChannel.setShowBadge(true);
                    notificationManager.createNotificationChannel(mChannel);
                }

                builder = new NotificationCompat.Builder(getApplicationContext(), id);
                builder.setAutoCancel(true)
                        .setSmallIcon(R.drawable.marwadi_login_icon)
                        .setLargeIcon(drawableToBitmap(getResources().getDrawable(R.mipmap.marwadi_launcher)))
                        .setStyle(bigTextStyle)
                        .setAutoCancel(true)
                        .setGroup(GROUP_KEY_MESSAGES)
                        .setGroupSummary(true)
                        .setContentTitle("MSFL")
                        .setChannelId(id)
                        .setVibrate(v)
                        .setSound(uri)
                        .setNumber(count)
                        .setDefaults(0)
                        .setOngoing(true)
                        .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                        .setContentIntent(pendingIntent);
            } else {
                builder = new NotificationCompat.Builder(this, id)
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.marwadi_login_icon)
                        .setLargeIcon(drawableToBitmap(getResources().getDrawable(R.mipmap.marwadi_launcher)))
                        .setStyle(bigTextStyle)
                        .setAutoCancel(true)
                        .setGroup(GROUP_KEY_MESSAGES)
                        .setGroupSummary(true)
                        .setContentTitle("MSFL")
                        .setChannelId(id)
                        .setSound(uri)
                        .setVibrate(v)
                        .setDefaults(0)
                        .setOngoing(true)
                        .setNumber(count)
                        .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                        .setContentIntent(pendingIntent);
            }
        notificationManager.notify(requestID, builder.build()); //0 = ID of notification

        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
