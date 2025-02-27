package com.rme.utils;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;

import java.util.Calendar;
import java.util.Date;

public class DatePicker {
    public interface onSelectListener {
        public void onDate(Date date);

        public void onCancel();
    }


    public DatePicker(Activity activity, onSelectListener onSelectListener) {
        int mYear = 0, mMonth = 0, mDay = 0;

        // Get Current Date
         Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String sDateddd = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        Date date = ConvertDate.getDateFromDMY(sDateddd);
                        onSelectListener.onDate(date);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                onSelectListener.onCancel();
            }
        });
        datePickerDialog.show();
    }
}
