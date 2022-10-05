package com.acumengroup.mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.market.OrderStreamingController;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;

/**
 * Created by user on 07-Jul-17.
 */

public class WebContent extends AppCompatActivity {
    private static final String TAG = "WebContent";
    SharedPreferences sp;
    static Context mContext;
    String Atom2Request;
    Intent intent;
    boolean loadingFinished = true;
    boolean redirect = false;
    WebView webView;
    OrderStreamingController orderStreamingController = new OrderStreamingController();
    Bundle extras;
    String result = "";
    String amt = "";
    String status = "";
    private StringBuilder sb= new StringBuilder();
    private StringBuilder sbstatus= new StringBuilder();
    private StringBuilder sbcurrent= new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webviewrecharge);
        mContext = this;
        extras = getIntent().getExtras();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE))
            { WebView.setWebContentsDebuggingEnabled(true); }
        }
        if (extras != null)
            Atom2Request = extras.getString("AtomRequest");
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.getSettings().setBuiltInZoomControls(true);


        webView.loadUrl(Atom2Request);
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
            Log.w(TAG, "Loading");
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

            } else {
                redirect = false;

            }
            String baseURl = AccountDetails.getIsSecure() + "://" + AccountDetails.getArachne_IP() + ":" + AccountDetails.getArachne_Port();
            //                          For Fund transfer                         FOR UPI
            if(url.contains(AccountDetails.getArachne_IP()) && ( url.contains("/getFundTransferResponse")||url.contains( "/getUpiTransferResponse_ios") ) ){
//                webView.loadUrl("javascript:window.Android.onResponse('<body>'+document.getElementsByTagName('body')[0].innerHTML+'</body>');");
                if(extras.getString("transactionMethod").equalsIgnoreCase("UPI")){
                    webView.evaluateJavascript("document.querySelector('.statusCode').textContent", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                            Log.d("LogName", s); // Log is written, but s is always null
                            JsonReader reader = new JsonReader(new StringReader(s));
                            if (s != null && s.length() != 0 && !s.equals("null")) {
                                sbstatus = new StringBuilder();
                                sbstatus.append(s);
                            }

                            status = sbstatus.toString();

                            webView.evaluateJavascript("document.querySelector('.transAmount').textContent", new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String s) {
                                    Log.d("LogName", s); // Log is written, but s is always null
                                    JsonReader reader = new JsonReader(new StringReader(s));
                                    if (s != null && s.length() != 0 && !s.equals("null")) {
                                        sb = new StringBuilder();
                                        sb.append(s);
                                    }

                                    amt= sb.toString();
                                    // Must set lenient to parse single values
                                    result = "Your request for addition of Rs." + amt + " has been " + status;
                                if(amt !="" && status !="") {
                                    Intent returnIntent = new Intent();
                                    returnIntent.putExtra("Result", result);
                                    setResult(RESULT_OK, returnIntent);
                                    finish();
                                }

                                }
                            });
                            // Must set lenient to parse single values


                        }
                    });
                }else{
                    webView.loadUrl("javascript:window.Android.onResponse('<body>'+document.getElementsByTagName('body')[0].innerHTML+'</body>');");
                }
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

            try {
                if(extras.getString("transactionMethod").equalsIgnoreCase("UPI")){
                /*webView.evaluateJavascript("document.querySelector('.statusCode').textContent", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Log.d("LogName", s); // Log is written, but s is always null
                        JsonReader reader = new JsonReader(new StringReader(s));
                        if (s != null && s.length() != 0 && !s.equals("null")) {
                            sbstatus.append(s);
                        }

                        status = sbstatus.toString();

                        webView.evaluateJavascript("document.querySelector('.transAmount').textContent", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {
                                Log.d("LogName", s); // Log is written, but s is always null
                                JsonReader reader = new JsonReader(new StringReader(s));
                                if (s != null && s.length() != 0 && !s.equals("null")) {
                                    sb.append(s);
                                }

                                 amt= sb.toString();
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
            }else {
                    if(!reponseText.equalsIgnoreCase("<body></body>")&&  reponseText.contains("pre")) {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("Result", reponseText);
                        setResult(RESULT_OK, returnIntent);
                        finish();
                    }
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
//            for phillips fundtrasfer issue
//            orderStreamingController.sendFundTransferDetailsRequest(getApplicationContext(), extras.getString("uniqueid"), AccountDetails.getUsername(getApplicationContext()), extras.getString("amt"), "2", AccountDetails.getClientCode(getApplicationContext()), extras.getString("segstr"));
        }
        super.onDestroy();
    }
}
