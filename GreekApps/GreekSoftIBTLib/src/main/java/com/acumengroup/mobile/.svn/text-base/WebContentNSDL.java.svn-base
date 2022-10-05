package com.acumengroup.mobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.acumengroup.greekmain.core.market.OrderStreamingController;
import com.acumengroup.greekmain.core.network.EDISHoldingInfoResponse;
import com.acumengroup.greekmain.core.network.EDISHoldingInfoResponse1;
import com.acumengroup.mobile.model.UpdateNSDLAuthorizationResponse;
import com.acumengroup.mobile.reports.EdisDashboardReport;
import com.google.gson.Gson;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by user on 07-Jul-17.
 */

public class WebContentNSDL extends AppCompatActivity {
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
            url = extras.getString("url");
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

        String html = "<html>" +
                "<head></head>" +
                "<body onload=\"document.DISRqst.submit()\">" +
                "<form name=\"DISRqst\" method = \"post\" action= "+url+">" +
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


        webView.loadData(html, "text/html", "UTF-8");
        Log.e("Final html", html);
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
            if (streamingResponse.contains("UpdateNSDLAuthorizationResponse")) {
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
