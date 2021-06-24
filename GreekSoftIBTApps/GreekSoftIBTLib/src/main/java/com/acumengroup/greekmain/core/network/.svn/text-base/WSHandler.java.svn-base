package com.acumengroup.greekmain.core.network;

import android.content.Context;
import android.util.Log;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.util.Util;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.Base64;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * GreekSoft 2016
 */
public class WSHandler {
    //streaming
    private static final int LOCAL_STREAMING_SERVER_PORT = 4447;
    private static final String LOCAL_STREAMING_SERVER_URL = "192.168.209.198";
    private static final int LOCAL_ORDER_STREAMING_SERVER_PORT = 4246;
    private static final String LOCAL_ORDER_STREAMING_SERVER_URL = "192.168.209.65";

    // private static final int TEST_STREAMING_SERVER_PORT = 4447;
    //private static final String TEST_STREAMING_SERVER_URL = "203.122.55.173";
    //private static final String TEST_STREAMING_SERVER_URL = "49.248.161.116";
//    private static final String TEST_STREAMING_SERVER_URL = "greeksoft.dynu.com";
//    private static final String TEST_STREAMING_SERVER_URL = "125.99.50.243";
//    private static final String TEST_STREAMING_SERVER_URL = "vtrade.vikson.in";
//    private static final String TEST_STREAMING_SERVER_URL = "192.168.209.198";
//    private static final String TEST_STREAMING_SERVER_URL = "192.168.206.6";
    // private static final String TEST_STREAMING_SERVER_URL = "192.168.209.237";

    // private static final int TEST_ORDER_STREAMING_SERVER_PORT = 4246;
//    private static final String TEST_ORDER_STREAMING_SERVER_URL = "192.168.205.16";
    //private static final String TEST_ORDER_STREAMING_SERVER_URL = "114.143.6.165";
    //  private static final String TEST_ORDER_STREAMING_SERVER_URL = "192.168.209.237";
//    private static final String TEST_ORDER_STREAMING_SERVER_URL = "49.248.161.116";
//     private static final String TEST_ORDER_STREAMING_SERVER_URL = "192.168.206.6";
//    private static final String TEST_ORDER_STREAMING_SERVER_URL = "125.99.50.243";
//    private static final String TEST_ORDER_STREAMING_SERVER_URL = "125.99.50.243";
//    private static final String TEST_ORDER_STREAMING_SERVER_URL = "greeksoft.dynu.com";

    private static final int PROD_STREAMING_SERVER_PORT = 4448;
    private static final String PROD_STREAMING0_SERVER_URL = "192.168.210.150";
    private static final int PROD_ORDER_STREAMING_SERVER_PORT = 4246;
    private static final String PROD_ORDER_STREAMING_SERVER_URL = "192.168.210.150";

    //private static final String TEST_URL = "http://203.122.55.173:3001";
//    private static final String LIVE_URL = "https://greeksoft.dynu.com";
//    private static final String LIVE_URL = "https://acumenleap.acumengroup.in";
//    private static final String LIVE_URL = "http://192.168.209.237:3001";
//    private static final String LIVE_URL = "http://192.168.209.237:3001";
//    private static final String LIVE_URL = "http://192.168.209.22:3000";
//    private static final String LIVE_URL = "http://192.168.209.147:3000";
//      private static final String LIVE_URL = "http://192.168.209.20:3000";
//      private static final String LIVE_URL = "http://192.168.209.5:3000";
//      private static final String LIVE_URL = "http://182.76.70.73:3000";
//      private static final String LIVE_URL = "http://182.76.70.74:3000";
    private static final String LIVE_URL = "http://tester.greeksoft.in";
//      private static final String LIVE_URL = "https://gtrade.marwadionline.com";// TODO: Marwadi Live server
//      private static final String LIVE_URL = "http://NS1.PHILLIPCAPITAL.IN";
//      private static final String LIVE_URL = "http://NS2.PHILLIPCAPITAL.IN";
//      private static final String LIVE_URL = "http://202.134.52.132:3000";
//      private static final String LIVE_URL = "https://10.204.7.157";
//    private static final String LIVE_URL = "http://192.168.209.110:3001";
//    private static final String LIVE_URL = "http://192.168.208.13:3000"
//      private static final String LIVE_URL = "http://192.168.206.6:3000";
    //  private static final String LIVE_URL = "http://49.248.161.116:3000";

    //fILE uPLOAD uRL
    public static final String SERVER_URL = LIVE_URL + "/upload";

    public static final String CURRENT_BASE_URL = LIVE_URL;

    public static final String CURRENT_URL = AccountDetails.getIsSecure() + "://" + AccountDetails.getArachne_IP() + ":" + AccountDetails.getArachne_Port();


    public static AsyncHttpClient getWSClient() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        //client.setEnableRedirects(false, false);
        client.setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.63 Safari/537.36");
        client.setMaxRetriesAndTimeout(1, 5000);
        client.addHeader("Connection", "keep-alive");
        client.addHeader("Cache-Control", "max-age=0");
        client.addHeader("Accept-Language", "en-US,en;q=0.8");
        client.addHeader("Host", "49.248.161.116:10551");
        return client;
    }


    public static void getRequest(Context context, String url, final GreekResponseCallback handler) {
        //String encryptedUrl= encodeToBase64(url);
        Log.d("WSHandler", "GET Requesting===>" + getUrl(context, url));
        getWSClient().get(context, getUrl(context, url), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String resp1 = new String(responseBody);
                String response = decodeBase64(resp1);
                Log.d("WSHandler", "GET Requesting OnSuccess==>" + response);

                if (response.length() > 0 && ((response.startsWith("{") && response.endsWith("}")) || (response.startsWith("[") && response.endsWith("]")))) {
                    try {
                        JSONObject data = new JSONObject(response);
                        if (("true".equals(data.optString("success")) && data.has("data") && data.getJSONArray("data").length() > 0) || (data.has("Total") && data.optJSONArray("Total").length() > 0)) {
                            if (handler != null)
                                handler.onSuccess(data);
                        } else {
                            if (handler != null)
                                handler.onFailure(data.getString("message"));
                        }
                    } catch (JSONException e) {
                        if (handler != null)
                            handler.onFailure("No data available.");
                    }
                } else {
                    if (handler != null)
                        handler.onFailure("No data available.");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                if (handler != null)
                    handler.onFailure("No data available.");
            }
        });
    }

    public static void postRequest(Context context, String url, RequestParams params, final GreekResponseCallback callback) {
        //Log.d("POST Requesting: ", getUrl(url));

        getWSClient().post(context, getUrl(context, url), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);

                if (response.length() > 0 && ((response.startsWith("{") && response.endsWith("}")) || (response.startsWith("[") && response.endsWith("]")))) {
                    try {
                        JSONObject data = new JSONObject(response);
                        if (("true".equals(data.optString("success")) && data.has("data") && data.getJSONArray("data").length() > 0) || (data.has("Total") && data.optJSONArray("Total").length() > 0)) {
                            if (callback != null)
                                callback.onSuccess(data);
                        } else {
                            if (callback != null)
                                callback.onFailure(data.getString("message"));
                        }
                    } catch (JSONException e) {
                        if (callback != null)
                            callback.onFailure("No data available.");
                    }
                } else {
                    if (callback != null)
                        callback.onFailure("No data available.");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (callback != null)
                    callback.onFailure("No data available.");
            }
        });
    }

    public static void postAuthorization(Context context, String url, RequestParams params, AsyncHttpResponseHandler handler) {
        //Log.d("POST Requesting: ", CURRENT_URL + "/"+ url);
        getWSClient().post(context, CURRENT_URL + "/" + url, params, handler);
    }

    public static void postJson(Context context, String url, StringEntity entity, AsyncHttpResponseHandler handler) {
        getWSClient().post(context, url, entity, "application/json", handler);
    }

    private static String getUrl(Context context, String relativeUrl) {
        String CURRENT_URL;
        if (AccountDetails.getArachne_IP().isEmpty() || AccountDetails.getArachne_Port() == 0) {

            String setArachne_Port = Util.getPrefs(context).getString("setArachne_Port", " ");
            String setArachne_IP = Util.getPrefs(context).getString("setArachne_IP", " ");
            String isSecure = Util.getPrefs(context).getString("isSecure", " ");
            if (isSecure.equalsIgnoreCase("true")) {
                AccountDetails.setIsSecure("https");
            } else {
                AccountDetails.setIsSecure("http");
            }
            CURRENT_URL = AccountDetails.getIsSecure() + "://" + setArachne_IP + ":" + setArachne_Port;
        } else {
            CURRENT_URL = AccountDetails.getIsSecure() + "://" + AccountDetails.getArachne_IP() + ":" + AccountDetails.getArachne_Port();
        }

        if (relativeUrl.contains("?")) {
            String svcName = relativeUrl.substring(0, relativeUrl.indexOf("?"));
            String svcParameters = relativeUrl.substring(relativeUrl.indexOf("?") + 1);
            String encryptSvcParameters = encodeToBase64(svcParameters);
            return CURRENT_URL + "/" + svcName + "?" + encryptSvcParameters;
        } else {
            return CURRENT_URL + "/" + relativeUrl;
        }
    }

    public static String decodeBase64(String decodeData) {
        String decodedString = new String(android.util.Base64.decode(decodeData, android.util.Base64.DEFAULT));
        return decodedString;
    }

    public static String encodeToBase64(String stringToEncode) {
        byte[] data = new byte[0];
        try {
            data = stringToEncode.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String encyrpt = Base64.encodeToString(data, Base64.NO_WRAP);
        return encyrpt;
    }

    public interface GreekResponseCallback {
        void onSuccess(JSONObject response);

        void onFailure(String message);
    }
}
