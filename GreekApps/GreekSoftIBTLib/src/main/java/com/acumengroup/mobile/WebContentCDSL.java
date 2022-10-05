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

import com.acumengroup.greekmain.core.market.OrderStreamingController;

/**
 * Created by user on 07-Jul-17.
 */

public class WebContentCDSL extends AppCompatActivity {
    private static final String TAG = "WebContent";
    static Context mContext;
    String action, DPId, ReqId, Version, TransDtls, Url;
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
            DPId = extras.getString("DPId");
            ReqId = extras.getString("ReqId");
            Version = extras.getString("Version");
            TransDtls = extras.getString("TransDtls");
            Url = extras.getString("Url");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE))
            {
                WebView.setWebContentsDebuggingEnabled(true);
            }
        }
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);


//        DPId="92400";
//        ReqId="TxN566126044";
//        Version="1.0";
//        TransDtls="lo1skYhPZ4pq/ykuNgp1i0PChTVGalKcYuKuvggplGFNzXNXtURXbRoisgH9HPJToBm21J/8iCMCZSk5elGkrn+H+3urNRAYCQlCxTG1BNng5UQchtWyS+lTBADrtgDaUiOqeYGZro9UvEULakOxT+KE26nPN4/GExpVAQlRHUVbTPZ6ENriX83idZqPuFZGhNXT9jx0HB6Uhpr4MnnpKpNtgrj51oio5EQPtEDlEiN0mBXA0J7lcf6KWw7WMmzhBjYdauHcveRpUW/woQmKxUtXxsVfMJM1WvXnBO3XSRzzxKHhyQ4oX4BAn9wJIyh/jwQPLIT8q44qd43TOZTRei3baHdG67tgAz47Nb6S+OAwoLj5Gdg2R0Gr3s6n7KVthL1c+ogxwgpV1scSlHDLk/UH3+x1cnJEO9uPA39k/lTXY7UcGoauRsadpcK7SarjHYzkzORUqJ0kWchrD5pN5zDRO9xrsdY29eBQmS3gTi+4tmbyFZHoUWRVdtsYItb/nCslEDPzNfPkn6TiiA18ojirbgJD+HgpZuxVVi1IucS4/CGDt8jufc1SxYVIXFwTZRYZGecZC1G7Y+miRl103A==";

        String html = "<html>" +
                "<head></head>" +
                "<body onload='document.frmDIS.submit()'>" +
                "<form name='frmDIS' method = 'post' action= '"+Url+"'>" +
                "<input type='hidden' name= 'DPId' value= '" + DPId + "'/>" +
                "<input type='hidden' name= 'ReqId' value= '" + ReqId + "'/>" +
                "<input type='hidden' name= 'Version' value= '" + Version + "'/>" +
                "<input type='hidden' name= 'TransDtls' value= '" + TransDtls + "'/>" +
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
            if(url.contains("getEDISAuthorizationResponseMobile")){
                webView.setVisibility(View.GONE);
            }
            Log.w(TAG, "Loading");
            Log.e("WebpageUrlPageStarted", url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.d("return url", url);
            if (!redirect) {
                loadingFinished = true;
            }

            if (loadingFinished && !redirect) {
                //HIDE LOADING IT HAS FINISHED
                Log.w(TAG, "Finish Loading");
                Log.e("WebpageUrlPagefinished",url);
                if (url.contains("getEDISAuthorizationResponseMobile")) {
                   // view.loadUrl("javascript:HTMLOUT.processHTML(var a = document.getElementsByTagName('h1')[0].innerHTML); alert(a);");
//                    webView.loadUrl("javascript:window.HTMLOUT.showHTML(document.getElementsByTagName('h1')[0].innerHTML;");
                    webView.loadUrl("javascript:window.Android.onResponse('<body>'+document.getElementsByTagName('body')[0].innerHTML+'</body>');");
                }
            } else {
                redirect = false;

            }

        }
    }

    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void onResponse(String reponseText) {
            Log.e("WebpageUrlrreponseText", reponseText);
            try {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("Result", reponseText);
                setResult(RESULT_OK, returnIntent);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }

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
}
