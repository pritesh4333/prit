package com.acumengroup.mobile;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MyTextWatcher implements TextWatcher {
    private EditText editText;

    public MyTextWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String text = editText.getText().toString();
        if (text.startsWith(" ")) {
            editText.setText(text.trim());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
