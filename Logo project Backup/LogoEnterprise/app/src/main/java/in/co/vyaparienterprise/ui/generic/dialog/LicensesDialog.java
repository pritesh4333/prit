package in.co.vyaparienterprise.ui.generic.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by bekirdursun on 11.04.2018.
 */

public class LicensesDialog extends AlertDialog.Builder {

    private Context mContext;
    private String text;

    public LicensesDialog(Context context, String text) {
        super(context);
        this.mContext = context;
        this.text = text;
        init();
    }

    private void init() {
        WebView view = new WebView(mContext);
        view.loadData(text, "text/html; charset=utf-8", "UTF-8");
        view.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null && url.startsWith("http://") || url != null && url.startsWith("https://")) {
                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                } else {
                    return false;
                }
            }
        });
        setView(view);
    }
}
