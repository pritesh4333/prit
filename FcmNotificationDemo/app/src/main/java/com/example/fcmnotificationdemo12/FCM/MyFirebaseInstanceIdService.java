package com.example.fcmnotificationdemo12.FCM;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;


public class MyFirebaseInstanceIdService extends FirebaseMessagingService {


    @Override
    public void onNewToken(String token) {


        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        String tokens= FirebaseInstanceId.getInstance().getToken();

        //for now we are displaying the token in the log
        //copy it as this method is called only when the new token is generated
        //and usually new token is only generated when the app is reinstalled or the data is cleared
        Log.e("MyRefreshedToken", tokens);
    }
    //this method will be called
    //when the token is generated

}
