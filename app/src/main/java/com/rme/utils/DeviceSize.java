package com.rme.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class DeviceSize {
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
    public static float convertDpToPixel(Context context,float dp){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }


    public static float convertPixelsToDp(Context context,float px){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
