package in.co.vyapari.middleware;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import in.co.vyapari.constant.Constants;
import in.co.vyapari.constant.MobileConstants;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bekir.Dursun on 3.10.2017.
 */

public class ServiceCreator {
    private static Retrofit retrofitportalNonToken = null;
    private static Retrofit retrofitNonToken = null;
    private static Retrofit retrofit = null;

    public static void cleanRetrofit() {
        retrofit = null;
        MobileConstants.accessToken = "";
    }

    public static Retrofit getClient() {
        if (retrofit == null || MobileConstants.accessToken.equals("")) {
            OkHttpClient okClient = getOkClient();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okClient)
                    .build();
        }
        return retrofit;
    }

    public static OkHttpClient getOkClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Interceptor.Chain chain) throws IOException {
                                Request original = chain.request();

                                Request.Builder requestBuilder = original.newBuilder()
                                        .addHeader("Authorization", MobileConstants.accessToken)
                                        .addHeader("UserName", MobileConstants.UserName)
                                        .addHeader("ClientToken", Constants.DEVICE_ID)
                                        .addHeader("AppVersion", Constants.APP_VERSION)
                                        .addHeader("AppVersionCode", Constants.APP_VERSION_CODE)
                                        .addHeader("DeviceType", Constants.DEVICE_TYPE)
                                        .addHeader("OSVersion", Constants.OS_VERSION)
                                        .addHeader("Language", Constants.LANG_S)
                                        .method(original.method(), original.body());

                                Request request = requestBuilder.build();
                                return chain.proceed(request);
                            }
                        })
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .build();
    }

    public static Retrofit getClientNonToken() {
        if (retrofitNonToken == null) {
            OkHttpClient okClient = new OkHttpClient.Builder()
                    .addInterceptor(
                            new Interceptor() {
                                @Override
                                public Response intercept(Interceptor.Chain chain) throws IOException {
                                    Request original = chain.request();

                                    Request.Builder requestBuilder = original.newBuilder()
                                            .addHeader("ClientToken", Constants.DEVICE_ID)
                                            .addHeader("AppVersion", Constants.APP_VERSION)
                                            .addHeader("AppVersionCode", Constants.APP_VERSION_CODE)
                                            .addHeader("DeviceType", Constants.DEVICE_TYPE)
                                            .addHeader("OSVersion", Constants.OS_VERSION)
                                            .addHeader("Language", Constants.LANG_S)
                                            .method(original.method(), original.body());

                                    Request request = requestBuilder.build();
                                    return chain.proceed(request);
                                }
                            })
                    .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                    .connectTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(5, TimeUnit.MINUTES)
                    .build();

            retrofitNonToken = new Retrofit.Builder()
                    .baseUrl(Constants.VYAPARI_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okClient)
                    .build();
        }
        return retrofitNonToken;
    }
    public static Retrofit getPortalTokenClient() {
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }


        }};
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");

            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Create an ssl socket factory with our all-trusting manager
        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        OkHttpClient okClient = new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Interceptor.Chain chain) throws IOException {
                                Request original = chain.request();

                                    Request.Builder requestBuilder = original.newBuilder()
                                            .addHeader("Authorization", MobileConstants.portalToken)
                                            .addHeader("UserName", MobileConstants.UserName)
                                            .addHeader("ClientToken", Constants.DEVICE_ID)
                                            .addHeader("AppVersion", Constants.APP_VERSION)
                                            .addHeader("AppVersionCode", Constants.APP_VERSION_CODE)
                                            .addHeader("DeviceType", Constants.DEVICE_TYPE)
                                            .addHeader("OSVersion", Constants.OS_VERSION)
                                            .addHeader("Language", Constants.LANG_S)
                                            .method(original.method(), original.body());

                                    Request request = requestBuilder.build();
                                    return chain.proceed(request);
                                }
                            })
                    .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                    .connectTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(5, TimeUnit.MINUTES)
                    .build();

            retrofitportalNonToken = new Retrofit.Builder()
                    .baseUrl(Constants.VYAPARI_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okClient)
                    .build();

        return retrofitportalNonToken;
    }


}