package in.co.vyapari.ui.base;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesignal.OneSignal;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Observable;
import java.util.Observer;

import in.co.vyapari.R;
import in.co.vyapari.VyapariApp;
import in.co.vyapari.ui.generic.MySpinner;
import in.co.vyapari.util.Utils;

/**
 * Created by Bekir.Dursun on 3.10.2017.
 */

public abstract class SohoActivity extends AppCompatActivity implements Observer {

    public Toolbar toolbar;
    public TextView _pageName;

    @Override
    protected synchronized void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (Build.VERSION.SDK_INT > 18) {
            if (makeTranslucent) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            } else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }

    protected void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void setToolbarConfig(String pageName, boolean isLogo) {
        toolbar = findViewById(R.id.toolbar);
        _pageName = findViewById(R.id.toolbar_name);
        if (pageName.contains("<font")) {
            _pageName.setText(Html.fromHtml(pageName));
        } else {
            _pageName.setText(Html.fromHtml("<font color='#171e22'>" + pageName + "</font>"));
        }

        try {
            ImageView home = findViewById(R.id.home);
            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        } catch (Exception ignored) {
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setElevation(0);
    }

    public void setToolbarConfig(int rString) {
        setToolbarConfig(getString(rString));
    }

    public void setToolbarConfig(String pageName) {
        _pageName = findViewById(R.id.toolbar_name);
        if (pageName.contains("<font")) {
            _pageName.setText(Html.fromHtml(pageName));
        } else {
            _pageName.setText(Html.fromHtml("<font color='#171e22'>" + pageName + "</font>"));
        }
    }

    public void setDetailToolbarConfig(int rTitle, int rIcon, int rEtHint) {
        setDetailToolbarConfig(getString(rTitle), rIcon, getString(rEtHint));
    }

    public void setDetailToolbarConfig(String title, int icon, String etHint) {
        TextView _title = findViewById(R.id.title);
        ImageView _icon = findViewById(R.id.icon);
        EditText name = findViewById(R.id.toolbar_name_et);

        _title.setText(title);
        _icon.setImageResource(icon);
        name.setHint(etHint);
    }

    public void setDetailToolbarConfig(int rTitle, int rEtHint) {
        setDetailToolbarConfig(getString(rTitle), getString(rEtHint));
    }

    public void setDetailToolbarConfig(String title, String etHint) {
        TextView _title = findViewById(R.id.title);
        EditText name = findViewById(R.id.toolbar_name_et);

        _title.setText(title);
        name.setHint(etHint);
    }

    protected void lockSpinner(MySpinner spinner) {
        spinner.setEnabled(false);
        spinner.setClickable(false);
    }

    protected void unlockSpinner(MySpinner spinner) {
        spinner.setEnabled(true);
        spinner.setClickable(true);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(String s) {
    }


    @Override
    public void onResume() {
        super.onResume();
        VyapariApp.activityResumed();
        VyapariApp.pushCheck.deleteObservers();
        VyapariApp.pushCheck.addObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        VyapariApp.activityPaused();
    }

    @Override
    public void update(Observable o, Object data) {
        if (Utils.isApplicationRunningFront(this)) {
            Handler mHandler = new Handler(getMainLooper());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    showNotification();
                }
            });
        }
    }

    public void showNotification() {
        if (VyapariApp.pushNotification != null) {
            Spanned message = Html.fromHtml(Utils.convertURLinString(VyapariApp.pushNotification.getMessage()));
            String title = VyapariApp.pushNotification.getTitle();
            Utils.generateMaterialDialog(SohoActivity.this,
                    title,
                    message,
                    getResources().getString(R.string.close),
                    null,
                    null,
                    null);

            OneSignal.cancelNotification(VyapariApp.pushNotification.getId());
            VyapariApp.pushNotification = null;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
