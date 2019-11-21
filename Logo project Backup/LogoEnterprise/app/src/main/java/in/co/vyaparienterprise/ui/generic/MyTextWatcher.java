package in.co.vyaparienterprise.ui.generic;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Bekir.Dursun on 4.10.2017.
 */

public abstract class MyTextWatcher implements TextWatcher {
    public abstract void afterTextChanged();

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        afterTextChanged();
    }
}
