package com.acumengroup.greekmain.core.network;

/**
 * Created by Arcadia
 */
public interface ServiceResponseListener {
    void processResponse(Object response);

    void handleResponseError(int errorCode, String message, Object error, ServiceRequest serviceRequest);
}