package com.acumengroup.mobile;

import static com.acumengroup.greekmain.core.constants.ServiceConstants.CURRENT_BASE_URL;

import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.view.KeyEvent;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.LongSparseArray;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.market.OrderStreamingController;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import cz.msebera.android.httpclient.Header;

/**
 * Created by user on 08-oct-2021.
 */

public class WebViewUPI extends AppCompatActivity {
    private static final String TAG = "WebContent";
    static Context mContext;
    String action, DPId, ReqId, Version, TransDtls, Url;
    boolean loadingFinished = true;
    boolean redirect = false;
    WebView webView;
    OrderStreamingController orderStreamingController = new OrderStreamingController();
    Bundle extras;
    boolean isPaymentURLloaded=false;
    boolean isredirectionURLloaded=false;
    boolean isacesLoader=false;
    String returnurl;
    int counter=0;
    String result = "";
    String amt = "";
    String status = "";
    private StringBuilder sb= new StringBuilder();
    private StringBuilder sbstatus= new StringBuilder();
    private StringBuilder sbcurrent= new StringBuilder();


    @Override
    public void onBackPressed () {
        /*webView.evaluateJavascript("document.querySelector('greek-upi-payment').getAttribute('currentScreen');",new ValueCallback<String>(){

            @Override
            public void onReceiveValue(String s) {
                Log.d("LogName", s); // Log is written, but s is always null
                JsonReader reader = new JsonReader(new StringReader(s));
                if(s != null && s.length() != 0 && !s.equals("null")){
                    sbcurrent.append(s);
                }
            }
        } );
        if(sbcurrent.toString().equalsIgnoreCase("welcomescreen")) {
            webView.evaluateJavascript("document.querySelector('greek-upi-payment').setAttribute('gotoamountscreen','true');", null);
        }*/
    }
       // return;


    @Override
    public boolean onKeyDown(int key_code, KeyEvent key_event) {
        if (key_code== KeyEvent.KEYCODE_BACK) {
            super.onKeyDown(key_code, key_event);
            return true;
        }
        return false;
    }

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webviewrecharge);
        mContext = this;
//        extras = getIntent().getExtras();
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
//        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.loadUrl(CURRENT_BASE_URL+"/upi_app/upiTransfer.html");
        WebView.setWebContentsDebuggingEnabled(true);
//        webView.setInitialScale(1);


        Log.e("Final url", CURRENT_BASE_URL+"/upi_app/upiTransfer.html");
        return;
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
            /*loadingFinished = false;
            //SHOW LOADING IF IT ISNT ALREADY VISIBLE
            if(url.contains("getEDISAuthorizationResponseMobile")){
                webView.setVisibility(View.GONE);
            }
            Log.w(TAG, "Loading");
            Log.e("WebpageUrlPageStarted", url);*/
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.d("return url", url);
          /*  String[] arrOfStr = url.split("\\?", 2);
            String newurl= url.replace("https://null:null",CURRENT_BASE_URL);*/
            if (url.contains("upiTransfer")) {
                String gscid= "document.querySelector('greek-upi-payment').setAttribute('gscid','"+ AccountDetails.getUsername(getApplicationContext()) +"');";
                String clientCode= "document.querySelector('greek-upi-payment').setAttribute('clientcode','"+ AccountDetails.getClientCode(getApplicationContext()) +"');";
                String sessionid= "document.querySelector('greek-upi-payment').setAttribute('sessionid','"+ AccountDetails.getSessionId(getApplicationContext()) +"');";


                String htmltag ="document.querySelector('greek-upi-payment').setAttribute('gscid','"+AccountDetails.getUsername(getApplicationContext())+"');document.querySelector('greek-upi-payment').setAttribute('clientcode','"+AccountDetails.getClientCode(getApplicationContext())+"');document.querySelector('greek-upi-payment').setAttribute('sessionid','"+AccountDetails.getSessionId(getApplicationContext())+"')";
                view.evaluateJavascript(htmltag,null);
                Log.e("HTML tag ", htmltag);

            }else if(isPaymentURLloaded && returnurl.equalsIgnoreCase(url)){
                counter++;

/*

                webView.evaluateJavascript("document.querySelector('.transAmount').textContent",new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Log.d("LogName", s); // Log is written, but s is always null
                        JsonReader reader = new JsonReader(new StringReader(s));
                        if(s != null && s.length() != 0 && !s.equals("null")){
                            sb.append(s);
                        }

                        amt= sb.toString();

                        webView.evaluateJavascript("document.querySelector('.statusCode').textContent",new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {
                                Log.d("LogName", s); // Log is written, but s is always null
                                JsonReader reader = new JsonReader(new StringReader(s));
                                if(s != null && s.length() != 0 && !s.equals("null")){
                                    sbstatus.append(s);
                                }

                                status= sbstatus.toString();
                                // Must set lenient to parse single values
                                result = "Your request for addition of Rs." + amt + " has been " + status;

                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("Result", result);
                                setResult(RESULT_OK, returnIntent);
                                finish();

                            }
                        });
                        // Must set lenient to parse single values


                    }
                });*/


                webView.loadUrl("javascript:window.Android.onResponse('<body>'+document.getElementsByTagName('body')[0].innerHTML+'</body>');");

            } /*else if(url.contains("/getFundTransferResponse")||url.contains("/getUpiTransferResponseForWeb")){
                if(!isPaymentURLloaded) {
                    Uri uri = Uri.parse(url);
                    returnurl=uri.getQueryParameter("ru");
                    Log.d("return atom url", returnurl);
                    isPaymentURLloaded=true;
                    webView.loadUrl("javascript:window.Android.onResponse('<body>'+document.getElementsByTagName('body')[0].innerHTML+'</body>');");
                }
            }*/ else if(url.contains("/getUpiTransferResponse")){
                if(!isPaymentURLloaded) {
                    Uri uri = Uri.parse(url);
                    returnurl=uri.getQueryParameter("ru");
                    Log.d("return atom url", returnurl);
                    isPaymentURLloaded=true;
                    webView.loadUrl("javascript:window.Android.onResponse('<body>'+document.getElementsByTagName('body')[0].innerHTML+'</body>');");
                }
            }
            /*else if(url.contains("https://caller.atomtech.in/ots/page/aborted")){
                webView.loadUrl("javascript:window.Android.onResponse('<body>'+document.getElementsByTagName('body')[0].innerHTML+'</body>');");
                //webView.loadUrl(url);
            }*/
            /*else if(url.contains("https://caller.atomtech.in/ots/payment/txn?merchId")){
                if(!isredirectionURLloaded) {
                    isredirectionURLloaded=true;
                    webView.loadUrl(url);
                }
            }else if(url.contains("https://caller.atomtech.in/ots/page/acse")){
                if(!isacesLoader) {
                    isacesLoader=true;
                    webView.loadUrl(url);
                }
            }*/
            else if(url.contains("https://caller.atomtech.in/ots/bank/ru/")){
               // webView.loadUrl("javascript:window.Android.onResponse('<body>'+document.getElementsByTagName('body')[0].innerHTML+'</body>');");
                counter++;
            }



            /*
            else if(url.contains("https://caller.atomtech.in/ots/page/aborted")){
                Log.e("Aborted url ",url);
                //webView.loadUrl(url);
            }*/

        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
           // Toast.makeText(WebViewUPI.this, "Oh no! " + error, Toast.LENGTH_SHORT).show();
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
                if (counter >0 && reponseText.contains("f_code")) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("Result", reponseText);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
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
