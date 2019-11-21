package in.co.vyapari.ui.activity.ChatWithUs;


import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.co.vyapari.R;
import in.co.vyapari.util.Utils;

public class ChatwithusActivity extends AppCompatActivity {
//    @BindView(R.id.toolbar_name)
//    TextView toolbar_name;
    @BindView(R.id.webview)
    WebView webView;
    private String ChatUrl="http://live.unfyd.com/logoInfosoft/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatwithus);
        ButterKnife.bind(this, ChatwithusActivity.this);
        Utils.showLoading(ChatwithusActivity.this);
        //toolbar_name.setText(getResources().getString(R.string.chat));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());



        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.equalsIgnoreCase(ChatUrl)) {
                    Utils.showLoading(ChatwithusActivity.this);
                }
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(
                    WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                //SHOW LOADING IF IT ISNT ALREADY VISIBLE
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.equalsIgnoreCase(ChatUrl)) {
                    Utils.hideLoading();
                }

            }
        });
        webView.loadUrl(ChatUrl);
    }

}
