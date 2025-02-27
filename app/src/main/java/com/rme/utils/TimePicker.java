package com.rme.utils;


import android.app.Activity;
import android.app.TimePickerDialog;

import java.util.Calendar;

public class TimePicker {
    public interface onSelectListener {
        public void onTime(String sTime);

        public void onCancel();
    }


    public TimePicker(Activity activity, onSelectListener onSelectListener) {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(android.widget.TimePicker timePicker, int i, int i1) {
                String sHour = ""+i;
                String sMinute = ""+i1;
                if (sHour.length()==1){
                    sHour = "0"+sHour;
                }
                if (sMinute.length()==1){
                    sMinute = "0"+sMinute;
                }
                onSelectListener.onTime(ConvertDate.HHTOhh(sHour + ":"+sMinute+":00"));
            }

        }, hour, minute, false);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
}
