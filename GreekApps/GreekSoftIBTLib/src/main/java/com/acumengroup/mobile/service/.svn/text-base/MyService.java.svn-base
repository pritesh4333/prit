package com.acumengroup.mobile.service;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.GreekBaseActivity;

/**
 * Created by user on 05-Aug-16.
 */
public class MyService extends Service {
    GreekBaseActivity greekBaseActivity = new GreekBaseActivity();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        return super.onStartCommand(intent, flags, startId);
        //   return START_NOT_STICKY;

   /*     START_STICKY: It will restart the service in case if it terminated and the Intent data which is passed to the onStartCommand() method is NULL. This is suitable for the service which are not executing commands but running independently and waiting for the job.
        START_NOT_STICKY: It will not restart the service and it is useful for the services which will run periodically. The service will restart only when there are a pending startService() calls. It’s the best option to avoid running a service in case if it is not necessary.
        START_REDELIVER_INTENT: It’s same as STAR_STICKY and it recreates the service, call onStartCommand() with last intent that was delivered to the service.
        */

        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Context ctx = getApplicationContext();
        AccountDetails.setLogin_user_type("openuser");
        AccountDetails.setIsApolloConnected(false);
        AccountDetails.setIsIrisConnected(false);
//        AccountDetails.clearCache(ctx); Whenever Android OS kil service class it clears the cache memory.


        Log.e("MyService", "onTaskRemoved=====>>>>>");
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        // greekBaseActivity.onLogout(0);
        super.onDestroy();
    }

}
