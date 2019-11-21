package in.co.vyapari.ui.generic.firecrasher;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

public final class CrashHandler implements Thread.UncaughtExceptionHandler {
    private Activity activity;
    private Application.ActivityLifecycleCallbacks lifecycleCallbacks;
    private CrashListener crashListener;
    private CrashInterface crashInterface;

    CrashHandler() {
        lifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                CrashHandler.this.activity = activity;
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        };
    }

    void setCrashListener(CrashListener crashListener) {
        this.crashListener = crashListener;
    }

    void setCrashInterface(CrashInterface crashListener) {
        this.crashInterface = crashListener;
    }

    @Override
    public void uncaughtException(Thread thread, final Throwable throwable) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                if (crashListener != null) {
                    crashListener.onCrash(throwable, activity);
                } else if (crashInterface != null) {
                    crashInterface.onCrash(throwable, activity);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                    alertDialog.setTitle("Crash");
                    alertDialog.setMessage(throwable.getMessage());
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Recover",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    FireCrasher.recover(activity);
                                }
                            });
                    alertDialog.show();
                }
            }
        });
        Log.e("FireCrasher.err", thread.getName(), throwable);
    }

    Application.ActivityLifecycleCallbacks getLifecycleCallbacks() {
        return lifecycleCallbacks;
    }
}
