package com.rme.utils;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

/**
 * Created by Rajnish on 10/4/2015.
 * this class use Displaying message
 */
public class ToastMsg {
    //this class call for Toast Message.
    public static void mToastMsg(Context context, String msg, int val) {
        if (msg.isEmpty()) {
            msg = "!Oops something went wrong, Please try again.";
        }
        if (val == 1) {
            Toast toast = Toast.makeText(context,msg, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Toast toast = Toast.makeText(context,
                    msg, Toast.LENGTH_LONG);
            toast.show();
        }


    }

    //    //this class call for Snak Bar Message.
    public static void mSnakMsg(View view, String msg, int val) {
        if (val == 1) {
            Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).setAction("Ok", null).show();
        } else {
            Snackbar.make(view, msg, Snackbar.LENGTH_LONG).setAction("Ok", null).show();
        }


    }
}
