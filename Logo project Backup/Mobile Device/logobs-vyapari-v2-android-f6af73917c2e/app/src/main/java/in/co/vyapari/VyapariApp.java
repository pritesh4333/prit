package in.co.vyapari;

import android.app.Activity;
import android.app.Application;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import java.util.Observable;

import es.dmoral.toasty.Toasty;
import in.co.vyapari.analytics.AnalyticsTrackers;
import in.co.vyapari.constant.MobileConstants;
import in.co.vyapari.database.DbBridge;
import in.co.vyapari.middleware.ServiceCreator;
import in.co.vyapari.middleware.ServiceQueue;
import in.co.vyapari.middleware.ServiceRequest;
import in.co.vyapari.middleware.service.CommonService;
import in.co.vyapari.model.PushNotification;
import in.co.vyapari.ui.generic.firecrasher.CrashListener;
import in.co.vyapari.ui.generic.firecrasher.FireCrasher;
import in.co.vyapari.util.Utils;

/**
 * Created by Bekir.Dursun on 3.10.2017.
 */

public class VyapariApp extends Application {

    private static VyapariApp sInstance;
    private static ServiceRequest apiService;
    private static ServiceRequest portalapiService;
    private static ServiceRequest vyapariApiService;
    private static DbBridge dbBridge;
    private static ServiceQueue serviceQueue;

    public static PushNotification pushNotification = null;
    public static PushCheck pushCheck = null;
    private static boolean activityVisible;

    @Override
    public void onCreate() {
        FireCrasher.install(this, new CrashListener() {
            @Override
            public void onCrash(Throwable throwable, final Activity activity) {
                Toasty.error(activity, getString(R.string.crash_warn), Toast.LENGTH_LONG).show();
                String crashPage = activity.getLocalClassName();
                CommonService.addCrashLog(crashPage, throwable.toString());
                activity.finish();
            }
        });
        super.onCreate();
        sInstance = this;

        pushCheck = new PushCheck();
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .setNotificationReceivedHandler(new notificationReceivedHandler())
                .setNotificationOpenedHandler(new notificationOpenedHandler())
                .init();

        AnalyticsTrackers.initialize(this);
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);
    }

    public static synchronized VyapariApp getInstance() {
        return sInstance;
    }

    public static synchronized ServiceRequest getVyapariApiService() {
        if (vyapariApiService == null) {
            vyapariApiService = ServiceCreator.getClientNonToken().create(ServiceRequest.class);
        }
        return vyapariApiService;
    }
    public static synchronized ServiceRequest getPortalApiService() {

        portalapiService = ServiceCreator.getPortalTokenClient().create(ServiceRequest.class);

        return portalapiService;
    }
    public static synchronized ServiceRequest getApiService() {
        if (apiService == null || MobileConstants.accessToken.equals("")) {
            apiService = ServiceCreator.getClient().create(ServiceRequest.class);
        }
        return apiService;
    }

    public static synchronized ServiceQueue getServiceQueue() {
        if (serviceQueue == null) {
            serviceQueue = new ServiceQueue(getInstance().getBaseContext());
        }
        return serviceQueue;
    }

    public static synchronized DbBridge getDbBridge() {
        if (dbBridge == null) {
            dbBridge = new DbBridge(sInstance);
        }
        return dbBridge;
    }


    public class PushCheck extends Observable {

        @Override
        public void notifyObservers(Object data) {
            setChanged();
            super.notifyObservers(data);
        }
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    private class notificationReceivedHandler implements OneSignal.NotificationReceivedHandler {

        @Override
        public void notificationReceived(OSNotification notification) {
            JSONObject data = notification.payload.additionalData;

            int id = notification.androidNotificationId;
            String title = notification.payload.title;
            String message = notification.payload.body;

            if (Utils.isApplicationRunningFront(getInstance())) {
                pushNotification = new PushNotification(id, title, message, data);
                pushCheck.notifyObservers();
            }
        }
    }

    private class notificationOpenedHandler implements OneSignal.NotificationOpenedHandler {

        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotification notification = result.notification;
            JSONObject data = notification.payload.additionalData;

            int id = notification.androidNotificationId;
            String title = notification.payload.title;
            String message = notification.payload.body;

            pushNotification = new PushNotification(id, title, message, data);

            if (Utils.isApplicationRunningFront(getInstance())) {
                pushCheck.notifyObservers();
            }
        }
    }

    public synchronized Tracker getGoogleAnalyticsTracker() {
        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
    }

    public void trackEvent(String category, String action, String label) {
        Tracker t = getGoogleAnalyticsTracker();
        t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());

        if (action == null) {
            action = category;
        }
        //AppTracktion.getInstance().trackEvent(category, action, label);
    }

    public void trackException(Exception e) {
        if (e != null) {
            Tracker t = getGoogleAnalyticsTracker();
            t.send(new HitBuilders.ExceptionBuilder()
                    .setDescription(new StandardExceptionParser(this, null).getDescription(Thread.currentThread().getName(), e))
                    .setFatal(false)
                    .build()
            );
        }
    }
}