package com.acumengroup.mobile.trade;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;

import android.os.Bundle;



public class DatePickerFragment extends DialogFragment {
    DatePickerDialog.OnDateSetListener ondateset;
    public DatePickerFragment(){

    }
    public void setCallBack(DatePickerDialog.OnDateSetListener ondate){
        ondateset=ondate;
    }
    private int year,month,day;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year=args.getInt("year");
        month=args.getInt("month");
        day=args.getInt("day");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerDialog dialog = new DatePickerDialog(getActivity(),ondateset,year,month,day);
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        return dialog;
    }
}
