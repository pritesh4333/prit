package com.acumengroup.greekmain.core.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;


import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.network.ErrorCodes;
import com.acumengroup.greekmain.core.network.GreekNetworkManager;
import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.network.StreamingRequest;
import com.acumengroup.greekmain.core.network.TCPConnectionHandler;
import com.acumengroup.greekmain.core.network.TCPOrderConnectionHandler;
import com.acumengroup.greekmain.util.Util;
import com.acumengroup.greekmain.util.statistics.GreekStatistics;
import com.acumengroup.mobile.R;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import javax.security.cert.CertificateException;

import de.greenrobot.event.EventBus;

/**
 * Created by Arcadia
 */
@SuppressLint("NewApi")
public class ServiceManager {

    public static final String HTTP_URL = "";
    private static ServiceManager current = null;
    private static Context context;

    public static ServiceManager getInstance(Context c) {
        context = c;
        if (current == null) current = new ServiceManager();
        return current;
    }

    public int sendRequest(ServiceRequest serviceRequest, ServiceResponseListener serviceResponseListener) {

        if (context != null) {

            if (GreekStatistics.isNetworkEnabled(context)) {
                // id generator
                serviceRequest.id = ServiceRequest.ID_COUNTER++;

                // Handling Cache
                if (serviceRequest.getCacheKey() != null) {
                    if (DataBuffer.getInstance(context).contains(serviceRequest.getCacheKey())) {
                        serviceResponseListener.processResponse(DataBuffer.getInstance(context).get(serviceRequest.getCacheKey()));

                        return serviceRequest.id;
                    }
                }

                if (serviceRequest.getUrl() == null) {

                    if (AccountDetails.getArachne_Port() == 0 || AccountDetails.getArachne_IP().isEmpty()) {
                        String setArachne_Port = Util.getPrefs(context).getString("setArachne_Port", " ");
                        String setArachne_IP = Util.getPrefs(context).getString("setArachne_IP", " ");
                        String isSecure = Util.getPrefs(context).getString("isSecure", " ");

                        AccountDetails.setArachne_IP(setArachne_IP);
                        if (isSecure.equalsIgnoreCase("true")) {
                            AccountDetails.setIsSecure("https");
                        } else {
                            AccountDetails.setIsSecure("http");
                        }
                        AccountDetails.setArachne_Port(Integer.parseInt(setArachne_Port));
                    }
                    String CURRENT_BASE_URL = AccountDetails.getIsSecure() + "://" + AccountDetails.getArachne_IP() + ":" + AccountDetails.getArachne_Port();
                    serviceRequest.setUrl(CURRENT_BASE_URL);
                }

               /* AsyncServiceTask asyncServiceTask = new AsyncServiceTask(context, serviceResponseListener);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    asyncServiceTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, serviceRequest);
                } else {
                    asyncServiceTask.execute(serviceRequest);
                }*/


                TaskHelper.execute(new AsyncServiceTask(context, serviceResponseListener), serviceRequest);

            } else {
                int errorCode = ErrorCodes.CONNECT_ERROR;
                String message = context.getResources().getString(R.string.GREEK_ERROR_CONNECT);
                EventBus.getDefault().post("Network congestion");

                /*if (serviceResponseListener != null)
                    serviceResponseListener.handleResponseError(errorCode, message, ConnectException.class, serviceRequest);*/
                return 0;
            }
        }

        return serviceRequest.id;
    }

    public void sendOrderStreamingRequest(StreamingRequest streamingRequest, boolean isLogin) {
        if (streamingRequest.getRequestType() == StreamingRequest.RequestType.SUBSCRIBE) {

            TCPOrderConnectionHandler.getInstance().placeRequest(streamingRequest.getHost(), streamingRequest.getPort(), streamingRequest.getRequest(), isLogin);
        } else {
            TCPOrderConnectionHandler.getInstance().pauseStreamingRequest(streamingRequest.getRequest());
        }

    }

    public void sendStreamingRequest(StreamingRequest streamingRequest, boolean isLogin) {


        if (streamingRequest.getRequestType() == StreamingRequest.RequestType.SUBSCRIBE) {
            TCPConnectionHandler.getInstance().placeRequest(streamingRequest.getHost(), streamingRequest.getPort(), streamingRequest.getRequest(), isLogin);
        } else {
            TCPConnectionHandler.getInstance().pauseStreamingRequest(streamingRequest.getRequest());
        }

    }

    public class AsyncServiceTask extends AsyncTask<ServiceRequest, Void, Object[]> {
        private ServiceResponseListener serviceResponseListener;
        private Context context;

        public AsyncServiceTask(Context context, ServiceResponseListener listener) {
            this.serviceResponseListener = listener;
            this.context = context;
        }

        @Override
        protected Object[] doInBackground(ServiceRequest... arg0) {

            ServiceRequest serviceRequest = arg0[0];


            Object response[] = new Object[2];

            GreekNetworkManager greekNetworkManager = new GreekNetworkManager();

            response[0] = greekNetworkManager.performRequest(context, serviceRequest);

            if (response[0] instanceof Exception) {
                // Exception
                response[1] = serviceRequest;
            } else {
                if (serviceRequest.getCacheKey() != null)
                    DataBuffer.getInstance(context).put(serviceRequest.getCacheKey(), response[0]);
            }
            return response;
        }

        @Override
        protected void onPostExecute(Object[] result) {
            super.onPostExecute(result);
            if (context == null) {
                return;
            }
            if (result[0] instanceof Exception) {

                Exception ex = (Exception) result[0];
                int errorCode = 0;
                String message = "";
                if (ex instanceof SocketTimeoutException) {
                    errorCode = ErrorCodes.SOCKET_TIMEOUT_ERROR;
                    message = context.getResources().getString(R.string.GREEK_ERROR_SOCKET_TIMEOUT);
                } else if (ex instanceof SecurityException) {
                    errorCode = ErrorCodes.SECURITY_ERROR;
//                    message = context.getResources().getString(R.string.GREEK_ERROR_SECURITY);
                } else if (ex instanceof org.apache.http.conn.HttpHostConnectException) {
                    errorCode = ErrorCodes.CONNECT_ERROR;
                    message = context.getResources().getString(R.string.GREEK_ERROR_UNABLE_CONNECT);
                } else if (ex instanceof ConnectException) {
                    errorCode = ErrorCodes.CONNECT_ERROR;
                    message = context.getResources().getString(R.string.GREEK_ERROR_CONNECT);
                } else if (ex instanceof IllegalArgumentException) {
                    errorCode = ErrorCodes.ILLEGAL_ARGUMENT_ERROR;
//                    message = context.getResources().getString(R.string.GREEK_ERROR_ILLEGAL_ARGUMENT);
                    message = context.getResources().getString(R.string.GREEK_ERROR_SOCKET_TIMEOUT);

                } else if (ex instanceof NullPointerException) {
                    errorCode = ErrorCodes.NULL_ERROR;
//                    message = context.getResources().getString(R.string.GREEK_ERROR_NULL);
                    message = context.getResources().getString(R.string.GREEK_ERROR_UNABLE_CONNECT);

                } else if (ex instanceof CertificateException) {
                    errorCode = ErrorCodes.NULL_ERROR;
//                    message = context.getResources().getString(R.string.GREEK_ERROR_NULL);
                    message = context.getResources().getString(R.string.GREEK_ERROR_UNABLE_CONNECT);

                } else if (ex instanceof IllegalArgumentException) {
                    errorCode = ErrorCodes.NULL_ERROR;
//                    message = context.getResources().getString(R.string.GREEK_ERROR_NULL);
                    message = context.getResources().getString(R.string.GREEK_ERROR_UNABLE_CONNECT);

                } else if (ex instanceof JSONException) {
                    //errorCode = ErrorCodes.NULL_ERROR;
//                    message = context.getResources().getString(R.string.CHART_MGR_ERROR);
                    message = "No Chart Data Available";
//                    message = context.getResources().getString(R.string.GREEK_ERROR_UNABLE_CONNECT);

                } else if (ex instanceof org.apache.http.conn.ConnectTimeoutException) {
                    //errorCode = ErrorCodes.NULL_ERROR;
                    message = context.getResources().getString(R.string.CONNECTION_TIMEOUT_ERROR);
                } else {
                    errorCode = ErrorCodes.UNKNOWN_ERROR;
//                    message = context.getResources().getString(R.string.GREEK_ERROR_UNKNOWN);
                    message = context.getResources().getString(R.string.GREEK_ERROR_UNABLE_CONNECT);

                    ex.printStackTrace();
                }

                if (serviceResponseListener != null)
                    serviceResponseListener.handleResponseError(errorCode, message, result[0], (ServiceRequest) result[1]);
            } else if (result[0] != null && result[0].toString().equalsIgnoreCase("POST response Exception")) {
                int errorCode = ErrorCodes.UNKNOWN_ERROR;
                String message = context.getResources().getString(R.string.GREEK_ERROR_UNABLE_CONNECT);
                //  Ask to ROhit sir unable to connect popup in error code 3
                if (serviceResponseListener != null)
                    serviceResponseListener.handleResponseError(errorCode, message, result[0], (ServiceRequest) result[1]);
            } else {
                if (serviceResponseListener != null)
                    serviceResponseListener.processResponse(result[0]);
            }
        }

    }
}
