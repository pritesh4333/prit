package in.co.vyaparienterprise.ui.generic;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Date;

@SuppressLint("ValidFragment")
public class MyDatePicker extends DialogFragment {

    private DatePickerDialog.OnDateSetListener mListener;
    private Calendar calendar;
    private long minDate;

    public MyDatePicker(Date date) {
        this.calendar = Calendar.getInstance();
        if (date != null) {
            this.calendar.setTime(date);
        }
    }

    public MyDatePicker(Date date, Date minDate) {
        this.calendar = Calendar.getInstance();
        if (date != null) {
            this.calendar.setTime(date);
        }

        if (minDate != null) {
            this.minDate = minDate.getTime();
        }
    }

    public MyDatePicker(Calendar calendar) {
        this.calendar = calendar;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (DatePickerDialog.OnDateSetListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = calendar != null ? calendar : Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), mListener, year, month, day);
        if (minDate != 0) {
            datePickerDialog.getDatePicker().setMinDate(minDate);
        }
        return datePickerDialog;
    }
}
