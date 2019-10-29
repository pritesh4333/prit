package in.co.vyapari.ui.generic;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.widget.CompoundButton;

/**
 * Created by bekirdursun on 23.03.2018.
 */

public class MySwitch extends SwitchCompat {
    private boolean mIgnoreCheckedChange = false;

    public MySwitch(Context context) {
        super(context);
    }

    public MySwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setOnCheckedChangeListener(final OnCheckedChangeListener listener) {
        super.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mIgnoreCheckedChange) {
                    return;
                }
                listener.onCheckedChanged(buttonView, isChecked);
            }
        });
    }

    public void setChecked(boolean checked, boolean notify) {
        mIgnoreCheckedChange = !notify;
        setChecked(checked);
        mIgnoreCheckedChange = false;
    }
}