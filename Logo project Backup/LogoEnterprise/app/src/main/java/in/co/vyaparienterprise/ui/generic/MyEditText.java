package in.co.vyaparienterprise.ui.generic;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Bekir.Dursun on 26.10.2017.
 */

public class MyEditText extends AppCompatEditText {

    private Context context;

    public MyEditText(Context context) {
        super(context);
        this.context = context;
        config();
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        config();
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        config();
    }

    private void config() {
        setSingleLine();
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    int count = getText().toString().length();
                    if (count == 0) {
                        //setError(context.getString(R.string.empy_warn));
                    }
                }
            }
        });
    }
}
