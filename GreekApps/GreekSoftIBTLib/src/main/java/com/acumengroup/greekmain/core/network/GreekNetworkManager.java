package com.acumengroup.greekmain.core.network;

import android.content.Context;
import android.util.Log;

import com.acumengroup.greekmain.util.Util;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Arcadia
 */
public class GreekNetworkManager {

    private final static String COOKIE = "Cookie";
    private final static String SESSION = "Session";
    private static String COOKIE_VALUE = null;

    public static String getCookieDataFromPreference(Context context) {
        COOKIE_VALUE = Util.getPrefs(context).getString(COOKIE, null);
        return COOKIE_VALUE;
    }

    public static void clearCookieFromPreference(Context context) {
        Util.getPrefs(context).edit().remove(COOKIE).commit();
        COOKIE_VALUE = null;
    }

    private void setCookieDataToPreference(Context context, String value) {
        Util.getPrefs(context).edit().putString(COOKIE, value).commit();
        COOKIE_VALUE = value;
    }

    private void setSessionDataToPreference(Context context, String value) {
        Util.getPrefs(context).edit().putString(SESSION, value).commit();
    }

    public Object performRequest(Context context, ServiceRequest request) {
        Object response;
        try {
            String actualUrl = request.createRequestURL();

            Log.d("GreekNetworkManager", "Arcane_HTTP_POST_Request_URL ===>>" + actualUrl);

            // URL must starts with HTTP
            if (!actualUrl.startsWith("http")) {
                return null;
            }

//            // old code
//            HttpClient httpclient = getNewHttpClient();


            // New Code - After Checking Certificate
            HttpClient httpclient = new DefaultHttpClient();

            HttpResponse httpResponse;

            if (request.isPostRequest()) {
                // Post
                HttpPost httppost = new HttpPost(actualUrl);

                // Get cookie from preference if exist
                if (COOKIE_VALUE == null) {
                    getCookieDataFromPreference(context);
                }

                // Set the cookie in http post header
                if (COOKIE_VALUE != null) {
                    httppost.setHeader(COOKIE, COOKIE_VALUE);
                }

                // Build the http post request with arguments
                request.buildRequestBody(httppost);

                // Execute HTTP Post Request
                httpResponse = httpclient.execute(httppost);
                //String resp= EntityUtils.toString(httpResponse.getEntity());

                String SET_COOKIE = "set-cookie";
                Header header = httpResponse.getFirstHeader(SET_COOKIE);

                if (header != null && header.getValue() != null) {
                    String session = header.getValue();
                    String HEADER_SESSION_KEY = "JSESSIONID";
                    int startIndex = session.indexOf(HEADER_SESSION_KEY) + HEADER_SESSION_KEY.length() + 1;
                    int endIndex = session.indexOf(";", startIndex);
                    session = session.substring(startIndex, endIndex);

                    setCookieDataToPreference(context, header.getValue());
                    setSessionDataToPreference(context, session);
                }

            } else {
                // Get
                HttpGet httpGet = new HttpGet(actualUrl);
                httpResponse = httpclient.execute(httpGet);
            }

            response = request.readResponse(httpResponse);
            httpclient.getConnectionManager().shutdown();

        } catch (CertificateException e) {
            e.printStackTrace();
            response = "POST response Exception";
        } catch (Exception e) {
            e.printStackTrace();
            response = "POST response Exception";
        }
        Log.d("GreekNetworkManager", "Arcane_HTTP_POST_Request_OnSuccess====>" + response.toString());

        return response;
    }
    // Old code
    public HttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            HttpConnectionParams.setConnectionTimeout(params, ServiceRequest.CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, ServiceRequest.READ_TIMEOUT);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }
// old code
    public class MySSLSocketFactory extends SSLSocketFactory {
        final SSLContext sslContext = SSLContext.getInstance("TLS");

        public MySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException,
                KeyManagementException, KeyStoreException, UnrecoverableKeyException {
            super(truststore);

            TrustManager tm = new X509TrustManager() {

                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    try {
                        chain[0].checkValidity();
                    } catch (Exception e) {
                        throw new CertificateException("Certificate not valid or trusted.");
                    }
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };

            sslContext.init(null, new TrustManager[]{tm}, null);
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }
    }

}
