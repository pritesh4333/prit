package com.acumengroup.mobile.messaging;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.acumengroup.greekmain.util.Util;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by User on 8/10/2016.
 */
public class NotifyRegistrationIntentService extends IntentService {
    public static final String REGISTRATION_SUCCESS = "RegistrationSuccess";
    public static final String REGISTRATION_ERROR = "RegistrationError";

    public NotifyRegistrationIntentService() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        registerGCM();
    }

    private void registerGCM() {
        Intent registrationComplete = null;
        String token = null;
        try {
            FirebaseInstanceId instanceID = FirebaseInstanceId.getInstance();
            token = instanceID.getToken();
            SharedPreferences.Editor editor = Util.getPrefs(getApplicationContext()).edit();
            editor.putString("GCMToken", token);
            editor.commit();
            //notify to UI that registration complete success
            registrationComplete = new Intent(REGISTRATION_SUCCESS);
            registrationComplete.putExtra("token", token);


        } catch (Exception e) {
            registrationComplete = new Intent(REGISTRATION_ERROR);
        }
        //Send broadcast
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }
}
