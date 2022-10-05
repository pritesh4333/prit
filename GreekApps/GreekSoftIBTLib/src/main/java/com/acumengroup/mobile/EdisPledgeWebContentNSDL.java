package com.acumengroup.mobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.acumengroup.greekmain.core.market.OrderStreamingController;

import de.greenrobot.event.EventBus;

/**
 * Created by user on 07-Jul-17.
 */

public class EdisPledgeWebContentNSDL extends AppCompatActivity {
    private static final String TAG = "WebContent";
    static Context mContext;
    String transactionType,requestor,requestorId,requestReference,channel,requestTime,orderReqDtls
            ,digitalSignature,url;

    boolean loadingFinished = true;
    boolean redirect = false;
    WebView webView;
    OrderStreamingController orderStreamingController = new OrderStreamingController();
    Bundle extras;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webviewrecharge);
        mContext = this;
        extras = getIntent().getExtras();

        if (extras != null) {
            transactionType = extras.getString("transactionType");
            requestor = extras.getString("requestor");
            requestorId = extras.getString("requestorId");
            requestReference = extras.getString("requestReference");
            channel = extras.getString("channel");
            requestTime = extras.getString("requestTime");
            orderReqDtls = extras.getString("orderReqDtls");
            digitalSignature = extras.getString("digitalSignature");
            url = extras.getString("MPIRqst");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE))
            { WebView.setWebContentsDebuggingEnabled(true); }
        }
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
//        DPId="92400";
//        ReqId="TxN566126044";
//        Version="1.0";
//        TransDtls="lo1skYhPZ4pq/ykuNgp1i0PChTVGalKcYuKuvggplGFNzXNXtURXbRoisgH9HPJToBm21J/8iCMCZSk5elGkrn+H+3urNRAYCQlCxTG1BNng5UQchtWyS+lTBADrtgDaUiOqeYGZro9UvEULakOxT+KE26nPN4/GExpVAQlRHUVbTPZ6ENriX83idZqPuFZGhNXT9jx0HB6Uhpr4MnnpKpNtgrj51oio5EQPtEDlEiN0mBXA0J7lcf6KWw7WMmzhBjYdauHcveRpUW/woQmKxUtXxsVfMJM1WvXnBO3XSRzzxKHhyQ4oX4BAn9wJIyh/jwQPLIT8q44qd43TOZTRei3baHdG67tgAz47Nb6S+OAwoLj5Gdg2R0Gr3s6n7KVthL1c+ogxwgpV1scSlHDLk/UH3+x1cnJEO9uPA39k/lTXY7UcGoauRsadpcK7SarjHYzkzORUqJ0kWchrD5pN5zDRO9xrsdY29eBQmS3gTi+4tmbyFZHoUWRVdtsYItb/nCslEDPzNfPkn6TiiA18ojirbgJD+HgpZuxVVi1IucS4/CGDt8jufc1SxYVIXFwTZRYZGecZC1G7Y+miRl103A==";

//        String html = "<html>" +
//                "<head></head>" +
//                "<body onload=\"document.DISRqst.submit()\">" +
//                "<form name=\"MPIRqst\" method = \"post\" action= https://dematgw.nsdl.com/mpi-service/v1/public/mpi/orders>" +
//                "<input type=\"hidden\" name= \"transactionType\" value= 'MPI'/>" +
//                "<input type=\"hidden\" name= \"requestor\" value= 'PHILLIPCAPITAL (INDIA) PVT LTD'/>" +
//                "<input type=\"hidden\" name= \"requestorId\" value= 'NS302164'/>" +
//                "<input type=\"hidden\" name= \"requestReference\" value= '344'/>" +
//                "<input type=\"hidden\" name= \"channel\" value= 'web'/>" +
//                "<input type=\"hidden\" name= \"orderReqDtls\" value= '{\"orderReqDtls\":{\"orderDtls\":{\"brokerOrderNo\":\"\",\"exchangeCd\":\"01\",\"segment\":\"01\",\"numOfSecurities\":\"1\",\"secDtls:\":[{\"seqNo\":\"1\",\"isin\":\"INE467B01029\",\"isinName\":\"TATA CONSULTANCY SERV LT\",\"quantity\":\"1\",\"lockInReasonCode\":\"\",\"lockInReleaseDate\":\"\"}]},\"pledgeDtls\":{\"pledgorDpId\":\"IN302164\",\"pledgorClientId\":\"10609996\",\"pledgorUCC\":\"TP203005\",\"pledgeeDpId\":\"IN302164\",\"pledgeeClientId\":\"10579995\",\"tmId\":\"14665\",\"cmId\":\"IN559538\",\"execDt\":\"08-11-2021\"}}}'/>" +
//                "<input type=\"hidden\" name= \"requestTime\" value= '2019-08-23T08:23:47+0530'/>" +
//                "<input type=\"hidden\" name= \"digitalSignature\" value= 'MIIGgAYJKoZIhvcNAQcCoIIGcTCCBm0CAQExDTALBglghkgBZQMEAgEwCwYJKoZIhvcNAQcB\n" +
//                "oIIEUzCCBE8wggM3oAMCAQICCQD0+7h84ScncDANBgkqhkiG9w0BAQUFADCBvTELMAkGA1UEBhMCSU4x\n" +
//                "FDASBgNVBAgMC01haGFyYXNodHJhMREwDwYDVQQHDAhCb3JpdmFsaTEnMCUGA1UECgweR3JlZWtzb2Z0\n" +
//                "IFRlY2hub2xvZ2llcyBQdnQgTHRkMRIwEAYDVQQLDAlHcmVla3NvZnQxHDAaBgNVBAMME3Rlc3Rlci5n\n" +
//                "cmVla3NvZnQuaW4xKjAoBgkqhkiG9w0BCQEWG3ByYXZpbi5wYXNpQGdyZWVrc29mdC5jby5pbjAeFw0y\n" +
//                "MTA2MTQxMDE0MTdaFw0yMTA3MTQxMDE0MTdaMIG9MQswCQYDVQQGEwJJTjEUMBIGA1UECAwLTWFoYXJh\n" +
//                "c2h0cmExETAPBgNVBAcMCEJvcml2YWxpMScwJQYDVQQKDB5HcmVla3NvZnQgVGVjaG5vbG9naWVzIFB2\n" +
//                "dCBMdGQxEjAQBgNVBAsMCUdyZWVrc29mdDEcMBoGA1UEAwwTdGVzdGVyLmdyZWVrc29mdC5pbjEqMCgG\n" +
//                "CSqGSIb3DQEJARYbcHJhdmluLnBhc2lAZ3JlZWtzb2Z0LmNvLmluMIIBIjANBgkqhkiG9w0BAQEFAAOC\n" +
//                "AQ8AMIIBCgKCAQEA7LAt8DuaHxUB9YLmDq8WJmqfHliP6MUttxGLqujvMzhkAACXlA/IPpO0m5bdXq5c\n" +
//                "qGlWoGfZCQkbwHmevUfhsuRqY/MYlQa4uzIpBMEq76E0e6qiA4ly2Al3So/s0C1qAppqMbNwvI+r4WM/\n" +
//                "plq/E2Acd7fdMRt3fKYFK6ny6VbdlgVW++TS32bhaM07XlWd3pVyfCRnhFG7ynC/czEO+Ke2d4rfkF8p\n" +
//                "Sxna+ZyV8G8rW6LS7rTaUXKTPJeFhCiidhA+AyiLNED0v7XrvFz0pYBjz187xBpmUqYK7CikOMCjfmMc\n" +
//                "PcgOszvNPmsuY30JAWkzuU0ylA/ucZtOQL5bDwIDAQABo1AwTjAdBgNVHQ4EFgQUCEiMp5TDT413O9jC\n" +
//                "e9Ht3fjkrPEwHwYDVR0jBBgwFoAUCEiMp5TDT413O9jCe9Ht3fjkrPEwDAYDVR0TBAUwAwEB/zANBgkq\n" +
//                "hkiG9w0BAQUFAAOCAQEACJBp+Id7xtiTedL0kNMAGIhJkowy0sFeZ56+wLIAV7qH0slfBTT3EYc1WXay\n" +
//                "GsAqBIDZDgWd0Rkh6Ca3dPzwdQOIx/tt69SXOE7xm9Zw5TRVj85LZCjm51RE8AoZV5fJESwYbNDJYtQu\n" +
//                "VJBx4QR46ahhrg/mHLj4h2HD4XpeOyYJZ0b/MdNPPuQVhy00Imt5ygZipRo/sv7j4DBxUP3n3Misdb62\n" +
//                "ddB+ywidafWdNnM5jeb7m7hyk+cCj6fpEcsUWPYeAZ5yHDSUNMHflmJGpfV26G0HxJxrOuBLwYffZM8v\n" +
//                "LxFFzMXDyPeBaINA8pRvT0/FWd8qxKYvcfbChahW5jGCAfMwggHvAgEBMIHLMIG9MQswCQYDVQQGEwJJ\n" +
//                "TjEUMBIGA1UECAwLTWFoYXJhc2h0cmExETAPBgNVBAcMCEJvcml2YWxpMScwJQYDVQQKDB5HcmVla3Nv\n" +
//                "ZnQgVGVjaG5vbG9naWVzIFB2dCBMdGQxEjAQBgNVBAsMCUdyZWVrc29mdDEcMBoGA1UEAwwTdGVzdGVy\n" +
//                "LmdyZWVrc29mdC5pbjEqMCgGCSqGSIb3DQEJARYbcHJhdmluLnBhc2lAZ3JlZWtzb2Z0LmNvLmluAgkA\n" +
//                "9Pu4fOEnJ3AwCwYJYIZIAWUDBAIBMAsGCSqGSIb3DQEBAQSCAQBYVKuTXi4Vqm7aLWKmjwAesd9CK5lI\n" +
//                "rT4Qg/L6iDcqGsBlYTlZMzTMV/Pm/JaaCSXcH9SA8VGDfMwnd9mXEDCKFNyKhPJRYtaf198Qv9NTP8P9\n" +
//                "QuJ+dlZkqWgNSkfffIqh7A5NARFGeRff6OAOW60w0+Hj06NYi9diW9TMLFrGr8TqqOVYqS9UrwgsD5jr\n" +
//                "Z0rBXo5YJhMGTjHXeK4D/EmLbfwTu1tJ+ckPjbHv2QcnsNeyErZb3eKF2NzDfUvhmeWV6J8bMOaMLEF6\n" +
//                "rktAMskMY0Zytx8EEfA7x6uNwm64qDBAuysBMAvvY1Crj/gqL6FPEqCLsDQQg8CB2qzxAWQp'/>" +
//                "<input type=\"submit\" value='Send'/>"+
//                "</form>" +
//                "</body>" +
//                "</html>";

        String html1 = "<html>" +
                "<head></head>" +
                "<body onload=\"document.MPIRqst.submit()\">" +
                "<form name=\"MPIRqst\" method = \"post\" action= "+url+">" +
                "<input type=\"hidden\" name= \"transactionType\" value= '" + transactionType + "'/>" +
                "<input type=\"hidden\" name= \"requestor\" value= '" + requestor + "'/>" +
                "<input type=\"hidden\" name= \"requestorId\" value= '" + requestorId + "'/>" +
                "<input type=\"hidden\" name= \"requestReference\" value= '" + requestReference + "'/>" +
                "<input type=\"hidden\" name= \"channel\" value= '" + channel + "'/>" +
                "<input type=\"hidden\" name= \"requestTime\" value= '" + requestTime + "'/>" +
                "<input type=\"hidden\" name= \"orderReqDtls\" value= '" + orderReqDtls + "'/>" +
                "<input type=\"hidden\" name= \"digitalSignature\" value= '" + digitalSignature + "'/>" +
                "</form>" +
                "</body>" +
                "</html>";


//        String html1="<html><head></head><body onload=\"document.MPIRqst.submit()\"><form name=\"MPIRqst\" method = \"post\" action= \""+url+"\"><input type=\"hidden\" name= \"transactionType\" value= \'"+transactionType +"\'/><input type=\"hidden\" name= \"requestor\" value= \'"+requestor+"\'/><input type=\"hidden\" name= \"requestorId\" value= \'"+requestorId+"\'/><input type=\"hidden\" name= \"requestReference\" value= \'"+requestReference+"\'/><input type=\"hidden\" name= \"channel\" value= \'"+channel+"\'/><input type=\"hidden\" name= \"orderReqDtls\" value= \'"+orderReqDtls+"\'/><input type=\"hidden\" name= \"requestTime\" value= \' "+requestTime+" \'/><input type=\"hidden\" name= \"digitalSignature\" value= \'"+digitalSignature+"\' /></form></body></html>";

//        String html="<html><head></head><body onload=\"document.DISRqst.submit()\"><form name=\"MPIRqst\" method = \"post\" action= https://dematgw.nsdl.com/mpi-service/v1/public/mpi/orders><input type=\"hidden\" name= \"transactionType\" value= 'MPI'/><input type=\"hidden\" name= \"requestor\" value= 'PHILLIPCAPITAL (INDIA) PVT LTD'/><input type=\"hidden\" name= \"requestorId\" value= 'NS302164'/><input type=\"hidden\" name= \"requestReference\" value= '344'/><input type=\"hidden\" name= \"channel\" value= 'web'/><input type=\"hidden\" name= \"orderReqDtls\" value= '{\"orderReqDtls\":{\"orderDtls\":{\"brokerOrderNo\":\"\",\"exchangeCd\":\"01\",\"segment\":\"01\",\"numOfSecurities\":\"1\",\"secDtls:\":[{\"seqNo\":\"1\",\"isin\":\"INE467B01029\",\"isinName\":\"TATA CONSULTANCY SERV LT\",\"quantity\":\"1\",\"lockInReasonCode\":\"\",\"lockInReleaseDate\":\"\"}]},\"pledgeDtls\":{\"pledgorDpId\":\"IN302164\",\"pledgorClientId\":\"10609996\",\"pledgorUCC\":\"TP203005\",\"pledgeeDpId\":\"IN302164\",\"pledgeeClientId\":\"10579995\",\"tmId\":\"14665\",\"cmId\":\"IN559538\",\"execDt\":\"08-11-2021\"}}}'/><input type=\"hidden\" name= \"requestTime\" value= '2019-08-23T08:23:47+0530'/><input type=\"hidden\" name= \"digitalSignature\" value= 'MIIGgAYJKoZIhvcNAQcCoIIGcTCCBm0CAQExDTALBglghkgBZQMEAgEwCwYJKoZIhvcNAQcB oIIEUzCCBE8wggM3oAMCAQICCQD0+7h84ScncDANBgkqhkiG9w0BAQUFADCBvTELMAkGA1UEBhMCSU4x FDASBgNVBAgMC01haGFyYXNodHJhMREwDwYDVQQHDAhCb3JpdmFsaTEnMCUGA1UECgweR3JlZWtzb2Z0 IFRlY2hub2xvZ2llcyBQdnQgTHRkMRIwEAYDVQQLDAlHcmVla3NvZnQxHDAaBgNVBAMME3Rlc3Rlci5n cmVla3NvZnQuaW4xKjAoBgkqhkiG9w0BCQEWG3ByYXZpbi5wYXNpQGdyZWVrc29mdC5jby5pbjAeFw0y MTA2MTQxMDE0MTdaFw0yMTA3MTQxMDE0MTdaMIG9MQswCQYDVQQGEwJJTjEUMBIGA1UECAwLTWFoYXJh c2h0cmExETAPBgNVBAcMCEJvcml2YWxpMScwJQYDVQQKDB5HcmVla3NvZnQgVGVjaG5vbG9naWVzIFB2 dCBMdGQxEjAQBgNVBAsMCUdyZWVrc29mdDEcMBoGA1UEAwwTdGVzdGVyLmdyZWVrc29mdC5pbjEqMCgG CSqGSIb3DQEJARYbcHJhdmluLnBhc2lAZ3JlZWtzb2Z0LmNvLmluMIIBIjANBgkqhkiG9w0BAQEFAAOC AQ8AMIIBCgKCAQEA7LAt8DuaHxUB9YLmDq8WJmqfHliP6MUttxGLqujvMzhkAACXlA/IPpO0m5bdXq5c qGlWoGfZCQkbwHmevUfhsuRqY/MYlQa4uzIpBMEq76E0e6qiA4ly2Al3So/s0C1qAppqMbNwvI+r4WM/ plq/E2Acd7fdMRt3fKYFK6ny6VbdlgVW++TS32bhaM07XlWd3pVyfCRnhFG7ynC/czEO+Ke2d4rfkF8p Sxna+ZyV8G8rW6LS7rTaUXKTPJeFhCiidhA+AyiLNED0v7XrvFz0pYBjz187xBpmUqYK7CikOMCjfmMc PcgOszvNPmsuY30JAWkzuU0ylA/ucZtOQL5bDwIDAQABo1AwTjAdBgNVHQ4EFgQUCEiMp5TDT413O9jC e9Ht3fjkrPEwHwYDVR0jBBgwFoAUCEiMp5TDT413O9jCe9Ht3fjkrPEwDAYDVR0TBAUwAwEB/zANBgkq hkiG9w0BAQUFAAOCAQEACJBp+Id7xtiTedL0kNMAGIhJkowy0sFeZ56+wLIAV7qH0slfBTT3EYc1WXay GsAqBIDZDgWd0Rkh6Ca3dPzwdQOIx/tt69SXOE7xm9Zw5TRVj85LZCjm51RE8AoZV5fJESwYbNDJYtQu VJBx4QR46ahhrg/mHLj4h2HD4XpeOyYJZ0b/MdNPPuQVhy00Imt5ygZipRo/sv7j4DBxUP3n3Misdb62 ddB+ywidafWdNnM5jeb7m7hyk+cCj6fpEcsUWPYeAZ5yHDSUNMHflmJGpfV26G0HxJxrOuBLwYffZM8v LxFFzMXDyPeBaINA8pRvT0/FWd8qxKYvcfbChahW5jGCAfMwggHvAgEBMIHLMIG9MQswCQYDVQQGEwJJ TjEUMBIGA1UECAwLTWFoYXJhc2h0cmExETAPBgNVBAcMCEJvcml2YWxpMScwJQYDVQQKDB5HcmVla3Nv ZnQgVGVjaG5vbG9naWVzIFB2dCBMdGQxEjAQBgNVBAsMCUdyZWVrc29mdDEcMBoGA1UEAwwTdGVzdGVy LmdyZWVrc29mdC5pbjEqMCgGCSqGSIb3DQEJARYbcHJhdmluLnBhc2lAZ3JlZWtzb2Z0LmNvLmluAgkA 9Pu4fOEnJ3AwCwYJYIZIAWUDBAIBMAsGCSqGSIb3DQEBAQSCAQBYVKuTXi4Vqm7aLWKmjwAesd9CK5lI rT4Qg/L6iDcqGsBlYTlZMzTMV/Pm/JaaCSXcH9SA8VGDfMwnd9mXEDCKFNyKhPJRYtaf198Qv9NTP8P9 QuJ+dlZkqWgNSkfffIqh7A5NARFGeRff6OAOW60w0+Hj06NYi9diW9TMLFrGr8TqqOVYqS9UrwgsD5jr Z0rBXo5YJhMGTjHXeK4D/EmLbfwTu1tJ+ckPjbHv2QcnsNeyErZb3eKF2NzDfUvhmeWV6J8bMOaMLEF6 rktAMskMY0Zytx8EEfA7x6uNwm64qDBAuysBMAvvY1Crj/gqL6FPEqCLsDQQg8CB2qzxAWQp'/><input type=\"submit\" value='Send'/></form></body></html>";

//        webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
        webView.loadData(html1, "text/html", "UTF-8");
        Log.e("Final html", html1);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {
            if (!loadingFinished) {
                redirect = true;
            }

            loadingFinished = false;
            view.loadUrl(urlNewString);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap facIcon) {
            loadingFinished = false;
            //SHOW LOADING IF IT ISNT ALREADY VISIBLE
//            if(url.contains("getEDISAuthorizationResponseMobile")){
//                webView.setVisibility(View.GONE);
//            }
            Log.w(TAG, "Loading");
            Log.e("WebpageUrlPageStarted", url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.d("return url", url);
            if (!redirect) {
                loadingFinished = true;
            }
//
//            if (loadingFinished && !redirect) {
//                //HIDE LOADING IT HAS FINISHED
//                Log.w(TAG, "Finish Loading");
                Log.e("WebpageUrlPagefinished",url);
//                if (url.contains("getEDISAuthorizationResponseMobile")) {
//                   // view.loadUrl("javascript:HTMLOUT.processHTML(var a = document.getElementsByTagName('h1')[0].innerHTML); alert(a);");
////                    webView.loadUrl("javascript:window.HTMLOUT.showHTML(document.getElementsByTagName('h1')[0].innerHTML;");
//                    webView.loadUrl("javascript:window.Android.onResponse('<body>'+document.getElementsByTagName('body')[0].innerHTML+'</body>');");
//                }
//            } else {
//                redirect = false;
//
//            }

        }
    }

    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void onResponse(String reponseText) {

        }
    }




    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (extras != null) {
            // orderStreamingController.sendFundTransferDetailsRequest(getApplicationContext(), extras.getString("uniqueid"), AccountDetails.getUsername(getApplicationContext()), extras.getString("amt"), "2", AccountDetails.getClientCode(getApplicationContext()), extras.getString("segstr"));
        }
        super.onDestroy();
    }
    public void onEventMainThread(String streamingResponse) {
        try {
            if (streamingResponse.contains("UpdateNSDLPledgeAuthorizationResponse")) {
                try {
//                 Gson gson = new Gson();
//                 UpdateNSDLAuthorizationResponse cdslReturnResponse = gson.fromJson(String.valueOf(streamingResponse), UpdateNSDLAuthorizationResponse.class);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("NSDLResult", streamingResponse);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

}
