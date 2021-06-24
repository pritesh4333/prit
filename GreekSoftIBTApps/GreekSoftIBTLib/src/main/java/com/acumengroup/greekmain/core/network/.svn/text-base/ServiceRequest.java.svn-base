package com.acumengroup.greekmain.core.network;

import com.loopj.android.http.Base64;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

public class ServiceRequest {

    public static int ID_COUNTER = 1;
    public static int READ_TIMEOUT = 15000;
    public static int CONNECTION_TIMEOUT = 15000;
    public int id;
    private String cacheKey;
    private String url;
    private String request;
    private HashMap<String, String> requestObj;
    private boolean postRequest = true;
    private Hashtable<String, String> requestArguments;
    private String content;
    private String contentType = "application/x-www-form-urlencoded; charset=UTF-8";

    public ServiceRequest() {
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }



    public boolean isPostRequest() {
        return postRequest;
    }

    /**
     * Returns true for a postRequest operation and false for a get operation
     *
     * @throws IllegalStateException if invoked after an addArgument call
     */
    public void setPostRequest(boolean postRequest) {
        if (this.postRequest != postRequest && requestArguments != null && requestArguments.size() > 0) {
            throw new IllegalStateException("Request method (postRequest/get) can't be modified one arguments have been assigned to the request");
        }
        this.postRequest = postRequest;
    }

    public void setReadTimeout(int readTimeout) {
        READ_TIMEOUT = readTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        CONNECTION_TIMEOUT = connectionTimeout;
    }

    protected String createRequestURL() {
        String url = this.url;

        // For Get Method, add the arguments
        if (!postRequest && requestArguments != null) {
            StringBuilder b = new StringBuilder(url);
            Enumeration<String> e = requestArguments.keys();
            if (e.hasMoreElements()) {
                b.append("?");
            }
            while (e.hasMoreElements()) {
                String key = e.nextElement();
                String value = requestArguments.get(key);
                b.append(key);
                b.append("=");
                b.append(value);
                if (e.hasMoreElements()) {
                    b.append("&");
                }
            }
            return b.toString();
        }
        return url;
    }

    /**
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    protected void buildRequestBody(HttpPost httppost) throws IOException {
        // if it is postRequest
        // by default write postRequest arguments as key value pair
        // subclass will override this method and give its respective
        // implementation e.g. json will write a json string
        // requestArguments iterate, outputstream write
        if (postRequest) {

            StringBuffer val = new StringBuffer();

            if (requestArguments != null) {

                Enumeration e = requestArguments.keys();
                while (e.hasMoreElements()) {
                    String key = (String) e.nextElement();
                    String value = requestArguments.get(key);
                    val.append(key);
                    val.append("=");
                    val.append(value);
                    if (e.hasMoreElements()) {
                        val.append("&");
                    }
                }

            } else if (request != null) {
                val = new StringBuffer(request);
            }

            StringEntity se = new StringEntity(val.toString(), "UTF-8");

            if (contentType != null)
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, contentType));

            httppost.setEntity(se);

        }
    }

    protected Object readResponse(HttpResponse httpResponse) throws Exception {

        if(httpResponse.getEntity()!=null) {
            String response = EntityUtils.toString(httpResponse.getEntity());
            //GreekLog.msg("Final Response ==>" + response);
            String decryptResponse = decodeBase64(response);
            //GreekLog.msg("Final Response ==>" + decryptResponse);
            return decryptResponse;
        }

        return null;

    }

    public String decodeBase64(String decodeData)
    {
        String decodedString=new String(android.util.Base64.decode(decodeData, android.util.Base64.DEFAULT));
        return decodedString;
    }
    public String encodeToBase64(String stringToEncode)
    {
        byte[] data= new byte[0];
        try {
            data = stringToEncode.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String encyrpt= Base64.encodeToString(data,Base64.NO_WRAP);
        return encyrpt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public HashMap<String, String> getRequestObj() {
        return requestObj;
    }

    public void setRequestObj(HashMap<String, String> requestObj) {
        this.requestObj = requestObj;
    }
}
