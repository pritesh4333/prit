package com.acumengroup.greekmain.core.app;

import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.core.parser.JSONResponse;

/**
 * Created by Arcadia
 */
public interface GreekUIServiceHandler {

    void process(Object response);

    void handleResponse(Object response);

    void handleError(int errorCode, String message, Object error, ServiceRequest serviceRequest);

    void infoDialog(int action, String msg, JSONResponse jsonResponse);

    void handleInvalidSession(String msg, int actionCode, JSONResponse jsonResponse);

    void showMsgOnScreen(int action, String msg, JSONResponse jsonResponse);

    void infoDialogOK(int action, String message, final JSONResponse jsonResponse);

    //void handleStreamResponse(final StreamingResponse response);
}
