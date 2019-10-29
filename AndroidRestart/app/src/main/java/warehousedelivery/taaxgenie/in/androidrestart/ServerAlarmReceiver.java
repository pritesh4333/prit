package warehousedelivery.taaxgenie.in.androidrestart;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

import static android.support.v4.app.NotificationCompat.PRIORITY_MIN;

public class ServerAlarmReceiver extends BroadcastReceiver {
	
	public static String ACTION_ALARM = "com.alarammanager.alaram";
	 
	 @Override
	 public void onReceive(Context context, Intent intent) {

	  Log.e("Alarm Server Receiver", "Entered");

		 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			 context.startForegroundService(new Intent(context, BackgroundService.class));
		 } else {
			 context.startService(new Intent(context, BackgroundService.class));
		 }
	 }




}
