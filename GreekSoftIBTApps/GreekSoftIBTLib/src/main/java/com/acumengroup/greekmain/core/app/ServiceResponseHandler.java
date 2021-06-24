package com.acumengroup.greekmain.core.app;

import android.content.Context;

import com.acumengroup.greekmain.core.constants.ActionCode;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.network.HTTPGetResponse;
import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.parser.GreekConfig;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.greekmain.util.operation.StringStuff;

import java.util.Hashtable;

/**
 * Created by Arcadia
 */
public class ServiceResponseHandler implements ServiceResponseListener, GreekConstants {
    private Context context;
    private GreekUIServiceHandler greekUIServiceHandler;

    public ServiceResponseHandler(Context context, GreekUIServiceHandler abmResponseListener) {
        this.context = context;
        this.greekUIServiceHandler = abmResponseListener;
    }

    @Override
    public void processResponse(Object response) {

        greekUIServiceHandler.process(response);
        if (response instanceof JSONResponse) {
            JSONResponse jsonResponse = (JSONResponse) response;
            if (handleInfoConfig(jsonResponse)) {
                greekUIServiceHandler.handleResponse(jsonResponse);
            }
        } else if (response instanceof HTTPGetResponse) {
            greekUIServiceHandler.handleResponse(response);
        }
    }

    private boolean handleInfoConfig(JSONResponse jsonResponse) {
        try {
            if (!GreekConfig.getConfigHashtable().containsKey(GreekConfig.INFO)) {
                GreekConfig.getParam(context, GreekConfig.INFO);
                if (!GreekConfig.getConfigHashtable().containsKey(GreekConfig.INFO)) {
                    return true;
                }
            }

            Hashtable info = (Hashtable) GreekConfig.getParam(context, GreekConfig.INFO, GreekConfig.CONFIG);
            if (info.containsKey(jsonResponse.getInfoID())) {
                info = (Hashtable) info.get(jsonResponse.getInfoID());
                String msg = jsonResponse.getInfoMsg();
                if (msg == null || msg.equalsIgnoreCase("")) {
                    msg = (String) info.get(INFO_CONFIG_MSG);
                }

                if (msg != null) {
                    msg = StringStuff.formatStr(msg);
                }

                int actionCode = Integer.parseInt((String) info.get(INFO_CONFIG_ACTION));

                //GreekLog.msg("Info ID==>" + jsonResponse.getInfoID() + "  msg==>" + msg + " action code==>" + actionCode);

                if (actionCode == ActionCode.ACT_CODE_SESSION_TIMEOUT.value) {
                    greekUIServiceHandler.handleInvalidSession(msg, actionCode, jsonResponse);
                } else if (actionCode == ActionCode.ACT_CODE_MSG_IN_SCREEN.value) {
                    greekUIServiceHandler.showMsgOnScreen(actionCode, msg, jsonResponse);
                } else if (actionCode == ActionCode.ACT_CODE_OK_DIALOG.value) {
                    greekUIServiceHandler.infoDialogOK(actionCode, msg, jsonResponse);
                } else {
                    greekUIServiceHandler.infoDialog(actionCode, msg, jsonResponse);
                }
                return false;
            } else {
                //GreekLog.msg("Info ID " + jsonResponse.getInfoID() + " not exists");
                String msg = jsonResponse.getInfoMsg();
                if (msg != null && (!msg.equals(""))) {
                    greekUIServiceHandler.infoDialogOK(ActionCode.ACT_CODE_OK_DIALOG.value, msg, jsonResponse);
                    return false;
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void handleResponseError(int errorCode, String message, Object error, ServiceRequest serviceRequest) {
        greekUIServiceHandler.handleError(errorCode, message, error, serviceRequest);
    }
}
