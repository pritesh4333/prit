package in.co.vyaparienterprise.ui.generic;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import java.util.Calendar;

public class MyTimePicker extends DialogFragment {

    private TimePickerDialog.OnTimeSetListener mListener;
    private Calendar calendar;

    public MyTimePicker() {
    }

    @SuppressLint("ValidFragment")
    public MyTimePicker(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (TimePickerDialog.OnTimeSetListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = calendar != null ? calendar : Calendar.getInstance();
        int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), mListener, hourOfDay, minute, true);
    }
}
