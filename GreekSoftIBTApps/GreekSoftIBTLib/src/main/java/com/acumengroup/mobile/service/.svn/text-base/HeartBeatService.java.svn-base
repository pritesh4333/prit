package com.acumengroup.mobile.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.GreekUIServiceHandler;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.market.OrderStreamingController;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.heartbeat.HeartBeatRequest;
import com.acumengroup.greekmain.core.model.heartbeat.HeartBeatResponse;
import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.core.network.TCPConnectionHandler;
import com.acumengroup.greekmain.core.network.TCPOrderConnectionHandler;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.greekmain.util.Util;

import de.greenrobot.event.EventBus;



/**
 * Created by user on 1/18/2016.
 */
public class HeartBeatService extends Thread implements GreekUIServiceHandler {
    public static boolean instanceExists = false; // true - one instance is already running of this service
    String LOT_TAG = "HeartBeatService";
    Context context;
    static int counter = 0;

    //ERROR CODE
    private final String VALID_SESSION = "0";
    private final String EXPIRED_SESSION = "1";
    private final String INVALID_SESSION = "2";

    public static String gscid, sessionId, gcid;
    ServiceResponseHandler serviceResponseHandler;
    StreamingController streamingController;
    OrderStreamingController orderStreamingController;
    GreekBaseActivity greekBaseActivity;
    static boolean stop = false;

    public HeartBeatService(Context context, String gscid, String sessionId, String gcid) {
        //Log.d(LOT_TAG, "Heartbeat service started");
        instanceExists = true;

        this.context = context;
        HeartBeatService.gscid = gscid;
        HeartBeatService.sessionId = sessionId;
        HeartBeatService.gcid = gcid;
        serviceResponseHandler = new ServiceResponseHandler(context, this);
        greekBaseActivity = new GreekBaseActivity();
        stop = false;
        counter = 0;
    }

    public void stopHeartBeat() {
        stop = true;
        instanceExists = false;
        Thread.currentThread().interrupt();
    }

    public void run() {
        for (; ; ) {
            if (stop) {
                break;
            }
            try {
                if (counter <= Integer.parseInt(AccountDetails.getReconnection_attempts())) {
                    HeartBeatRequest.sendRequest(gscid, sessionId, context, serviceResponseHandler);
                    streamingController = new StreamingController(); //TODO PK
                    orderStreamingController = new OrderStreamingController();
                    if (AccountDetails.isIsIrisConnected()) {
                        Log.e("HeartBEAT", "Heart Beatttttttt before Iris sent"+AccountDetails.getHeartBeatIrisCount());
                        if (AccountDetails.getHeartBeatIrisCount() < 3) {
                            orderStreamingController.sendStreamingHeartBeatIrisRequest(context, gcid, sessionId);
                            int count = AccountDetails.getHeartBeatIrisCount();
                            Log.e("HeartBEAT", "Heart Beatttttttt Iris after req sent"+AccountDetails.getHeartBeatIrisCount());
                            AccountDetails.setHeartBeatIrisCount(count + 1);
                        } else {
                            //Now User will trying send apollo reconnect
                            Log.e("HeartBEAT", "Now User will trying send apollo reconnect"+AccountDetails.getHeartBeatIrisCount());
                            TCPOrderConnectionHandler.getInstance().reConnect();
                        }
                    }
                    if (AccountDetails.isIsApolloConnected()) {

                        if (AccountDetails.getHeartBeatApolloCount() < 3) {
                            streamingController.sendStreamingHeartBeatRequest(context, gcid, sessionId);
                            int count = AccountDetails.getHeartBeatApolloCount();
                            AccountDetails.setHeartBeatApolloCount(count + 1);
                        } else {
                            //Now User will trying send Iris reconnect
                            TCPConnectionHandler.getInstance().reConnect();
                        }

                    }
                    counter++;
                } else {



                    stop = true;

                    SharedPreferences.Editor editor = Util.getPrefs(context).edit();
                    editor.remove("GREEK_RETAINED_CUST_PASS");
                    editor.apply();

                   /* GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.OPENUSER;
                    AccountDetails.setLogin_user_type("openuser");*/
                    EventBus.getDefault().post("SessionTimeOut");

                    /*SharedPreferences.Editor editor = Util.getPrefs(context).edit();
                    editor.remove("GREEK_RETAINED_CUST_PASS");
                    editor.apply();

                    GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.OPENUSER;
                    AccountDetails.setLogin_user_type("openuser");
                    Intent intent = new Intent(context, GreekBaseActivity.class);
                    intent.putExtra("from", "heartBeat");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);*/
                }

                Thread.sleep(Integer.parseInt(AccountDetails.getHeartbeat_Intervals()) * 1000);
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.d(LOT_TAG, ex.toString());
            }
        }
    }

    @Override
    public void process(Object response) {

    }

    @Override
    public void handleResponse(Object response) {
        JSONResponse jsonResponse = (JSONResponse) response;
        try {
            HeartBeatResponse heartBeatResponse = (HeartBeatResponse) jsonResponse.getResponse();
            if (heartBeatResponse != null) {
                String errorCode = heartBeatResponse.getErrorCode();
                if (!errorCode.equals(VALID_SESSION)) {
                    stop = true;

                    if (errorCode.equals(EXPIRED_SESSION)) {
                        //Log.d(LOT_TAG, "Server Session Expired");
                    } else if (errorCode.equals(INVALID_SESSION)) {
                        EventBus.getDefault().post("UserLoginOtherDevice");
                    }

                    SharedPreferences.Editor editor = Util.getPrefs(context).edit();
                    editor.remove("GREEK_RETAINED_CUST_PASS");
                    editor.apply();

                    /*GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.OPENUSER;
                    AccountDetails.setLogin_user_type("openuser");*/
                    EventBus.getDefault().post(heartBeatResponse);
                } else {
                    counter = 0;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //Log.d(LOT_TAG, ex.toString());
        }
    }


    @Override
    public void handleError(int errorCode, String message, Object error, ServiceRequest serviceRequest) {

    }

    @Override
    public void infoDialog(int action, String msg, JSONResponse jsonResponse) {

    }

    @Override
    public void handleInvalidSession(String msg, int actionCode, JSONResponse jsonResponse) {

    }

    @Override
    public void showMsgOnScreen(int action, String msg, JSONResponse jsonResponse) {

    }

    @Override
    public void infoDialogOK(int action, String message, JSONResponse jsonResponse) {

    }
}
