package in.co.vyapari.ui.generic.firecrasher;

import android.app.Activity;

public interface CrashInterface {
    void onCrash(Throwable throwable, Activity activity);
}
