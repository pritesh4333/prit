package in.co.vyapari.ui.activity.app;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.onesignal.OneSignal;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.co.vyapari.R;
import in.co.vyapari.VyapariApp;
import in.co.vyapari.constant.Constants;
import in.co.vyapari.middleware.listener.ServiceCall;
import in.co.vyapari.middleware.service.CommonService;
import in.co.vyapari.model.VersionControl;
import in.co.vyapari.model.response.BaseModel;
import in.co.vyapari.ui.activity.user.LoginActivity;
import in.co.vyapari.ui.base.SohoActivity;
import in.co.vyapari.util.Utils;

public class SplashActivity extends SohoActivity {

    @BindView(R.id.splash_progressbar)
    ProgressBar pb;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTranslucent(true);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mContext = this;

//        AppCenter.start(getApplication(), "61bc7d74-a842-4d50-af20-3ecc7cc30db2", Analytics.class, Crashes.class);
//          SelectServerDialog();
        if (VyapariApp.pushNotification != null) {
            showNotification();
        } else {
            versionControl(1500);
        }
    }

    public void showNotification() {
        pb.setVisibility(View.GONE);
        if (VyapariApp.pushNotification != null) {
            Spanned message = Html.fromHtml(Utils.convertURLinString(VyapariApp.pushNotification.getMessage()));
            String title = VyapariApp.pushNotification.getTitle();
            Utils.generateMaterialDialog(SplashActivity.this,
                    title,
                    message,
                    getResources().getString(R.string.close),
                    new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            versionControl(100);
                        }
                    },
                    null,
                    null);

            OneSignal.cancelNotification(VyapariApp.pushNotification.getId());
            VyapariApp.pushNotification = null;
        }
    }

    private void versionControl(final int delayMillis) {
        CommonService.getVersionControl(new ServiceCall<BaseModel<VersionControl>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<VersionControl> response) {
                if (response.getData() == null) {
                    openApp(delayMillis);
                } else {
                    final VersionControl vc = response.getData();
                    if (vc.isForceUpdate()) {
                        Utils.generateDialog(mContext, "New version", (vc.getDescription()),
                                "Update", new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        openStore();
                                    }
                                }, null, null);
                    } else {
                        Utils.generateDialog(mContext, "New version", (vc.getDescription()),
                                "Update", new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        openStore();
                                    }
                                },
                                "Close", new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        openApp(delayMillis);
                                    }
                                });
                    }
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable error) {
                openApp(delayMillis);
            }
        });
    }

    private void openApp(int delayMillis) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent i = new Intent(mContext, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        }, delayMillis);
    }

    private void openStore() {
        final String appPackageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
        finish();
    }
    public void SelectServerDialog(){
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.popupdailog);




        Button stagging = (Button) dialog.findViewById(R.id.staggin);
        Button production = (Button) dialog.findViewById(R.id.production);


        stagging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.BASE_URL = "http://isbasi-stg.logo-paas.com:8082/swagger/#/";
                Constants.VYAPARI_URL = "http://dev-vyapari.logo-paas.com/api-docs/";
                if (VyapariApp.pushNotification != null) {
                    showNotification();
                } else {
                    versionControl(1500);
                }
                dialog.dismiss();
            }
        });

        production.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.BASE_URL = "https://mobile.vyapari.co.in";
                Constants.VYAPARI_URL = "https://vyapari.co.in";
                if (VyapariApp.pushNotification != null) {
                    showNotification();
                } else {
                    versionControl(1500);
                }
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}