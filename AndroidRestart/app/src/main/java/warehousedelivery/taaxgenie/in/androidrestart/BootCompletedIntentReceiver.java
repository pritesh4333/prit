package warehousedelivery.taaxgenie.in.androidrestart;

/**
 * Created by santosh on 9/10/2018.
 */

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.widget.Toast;


public class BootCompletedIntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try{
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            try {

               // Toast.makeText(context,"restarted serivce",Toast.LENGTH_SHORT).show();
                AlarmManager alarms = (AlarmManager) context
                        .getSystemService(Context.ALARM_SERVICE);

                Intent intentss = new Intent(context,
                        DBAlarmReceiver.class);
                intentss.putExtra(DBAlarmReceiver.ACTION_ALARM,
                        DBAlarmReceiver.ACTION_ALARM);

                final PendingIntent pIntent = PendingIntent.getBroadcast(context,
                        1234567, intentss, 0);

                alarms.setRepeating(AlarmManager.RTC_WAKEUP,
                        System.currentTimeMillis(), 1000*60*1, pIntent);


            } catch (Exception e) {
                // TODO Auto-generated catch block
                Helper.sendCrashReportDetails((Activity) context,e.getMessage(),e.getStackTrace());
            }


            try {
                AlarmManager alarmss = (AlarmManager) context
                        .getSystemService(Context.ALARM_SERVICE);

                Intent intents = new Intent(context,
                        ServerAlarmReceiver.class);
                intents.putExtra(ServerAlarmReceiver.ACTION_ALARM,
                        ServerAlarmReceiver.ACTION_ALARM);

                final PendingIntent pIntents = PendingIntent.getBroadcast(context,
                        1234568, intents, 0);

                alarmss.setRepeating(AlarmManager.RTC_WAKEUP,
                        System.currentTimeMillis(), 1000*60*5, pIntents);


            } catch (Exception e) {
                // TODO Auto-generated catch block
                Helper.sendCrashReportDetails((Activity) context,e.getMessage(),e.getStackTrace());
            }
        }
        }catch (Exception e){
            Helper.sendCrashReportDetails((Activity) context,e.getMessage(),e.getStackTrace());
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            context.startForegroundService(new Intent(context, HelloService.class));
//        } else {
//            context.startService(new Intent(context, HelloService.class));
//        }
    }
}

