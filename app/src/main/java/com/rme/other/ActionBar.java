package com.rme.other;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rme.pos.R;

import java.util.ArrayList;

public class ActionBar {
    public static Activity mActivity = null;
    public static ArrayList<Activity> activityArrayList = new ArrayList<>();
    public static void Config(Activity activity) {
        View view = activity.getWindow().getDecorView();
        mActivity = activity;
        activityArrayList.add(activity);
        ImageView imgBack = view.findViewById(R.id.imgBack);
//        ImageView imgNotification = view.findViewById(R.id.imgNotification);



        if (imgBack != null) {
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeActivity(activity);
                    activity.finish();
                }
            });
        }

    }

    public static void removeActivity(Activity activity) {
        ArrayList<Integer> deleteArray = new ArrayList<>();
        for (int i = 0; i < activityArrayList.size(); i++) {
            if (activity.getLocalClassName().equals(activityArrayList.get(i).getLocalClassName())) {
                deleteArray.add(i);
            }
        }
        for (int i = 0; i < deleteArray.size(); i++) {
            activityArrayList.remove(deleteArray.get(i));
        }
    }

    public static void finishAll(Activity activity) {
        try {
            for (int i = 0; i < activityArrayList.size(); i++) {
                if (!activity.getLocalClassName().equals(activityArrayList.get(i).getLocalClassName())) {
                    activityArrayList.get(i).finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        activityArrayList = new ArrayList<>();
    }
}
