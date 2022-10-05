package com.acumengroup.greekmain.core.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.acumengroup.greekmain.core.app.AccountDetails;

import de.greenrobot.event.EventBus;

/**
 * Created by Arcadia
 */
public class NetworkChangeReceiver extends BroadcastReceiver {
    private boolean isConnected = false;
    NetworkStrengthConnectivity networkStrengthConnectivity;


    @Override
    public void onReceive(final Context context, final Intent intent) {
        networkStrengthConnectivity = new NetworkStrengthConnectivity();

        if (intent.getAction().contains("CONNECTIVITY_CHANGE")) {
            updateConnectionInfo(context);
        }
    }

    private boolean updateConnectionInfo(Context con) {
        ConnectivityManager sockMan = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (sockMan != null) {

            NetworkInfo[] info = sockMan.getAllNetworkInfo();

            if (info != null) {

                for (int i = 0; i < info.length; i++) {

                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {

                        if (!isConnected) {

                            AccountDetails.isNetworkConnected = true;
                            isConnected = true;

                            if (AccountDetails.isLoginAndDisconnected) {

                                TCPConnectionHandler.getInstance().reConnect();
                                TCPOrderConnectionHandler.getInstance().reConnect();
                                AccountDetails.isLoginAndDisconnected = false;
                            }

                            if (!networkStrengthConnectivity.isConnectedFast(con)) {

                                AccountDetails.isWeakNetworkConnected = true;
                                EventBus.getDefault().post("Weak");

                            } else {

                                AccountDetails.isWeakNetworkConnected = false;
                                EventBus.getDefault().post("Strong");
                            }

                            EventBus.getDefault().post("on");

                        }
                        return true;
                    }
                }
            }
        }
        EventBus.getDefault().post("off");
        AccountDetails.isNetworkConnected = false;
        AccountDetails.isLoginAndDisconnected = true;
        isConnected = false;
        return false;
    }


}