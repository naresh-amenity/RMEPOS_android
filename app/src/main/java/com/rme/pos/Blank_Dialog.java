package com.rme.pos;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;
import com.rme.roomdb.AppDatabase;
import com.rme.roomdb.DatabaseClient;
import com.rme.roomdb.DeliveryTable;
import com.rme.roomdb.ProductTable;
import com.rme.sunmi.SunmiScanner;
import com.rme.utils.AsyncTasks;
import com.rme.utils.ConvertDate;
import com.rme.utils.DatePicker;
import com.rme.utils.MySession;
import com.rme.utils.URLString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Blank_Dialog extends Dialog {

    public Activity mActivity;
View v;
    public Blank_Dialog(Activity a) {
        super(a, R.style.MYDIALOG);
        // TODO Auto-generated constructor stub
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        this.mActivity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.blank_dialog);
        v = findViewById(R.id.v);
        v.requestFocus();
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 1000);
    }


}