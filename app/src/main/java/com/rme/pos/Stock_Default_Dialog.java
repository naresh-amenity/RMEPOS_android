package com.rme.pos;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;
import com.rme.utils.ConvertDate;
import com.rme.utils.MySession;
import com.rme.utils.URLString;


public class Stock_Default_Dialog extends Dialog {

    public Activity mActivity;
    EditText edDefaultStock;
    CardView crdCancel, crdSaveNow;
ImageView imgMinus,imgPlus;
    public interface onSelectListener {
        public void onCancel();

        public void onDone();

    }

    private onSelectListener onSelectListener;

    public Stock_Default_Dialog(Activity a, onSelectListener onSelectListener) {
        super(a, R.style.MYDIALOG);
        // TODO Auto-generated constructor stub
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        this.mActivity = a;
        this.onSelectListener = onSelectListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.stock_default_dialog);

        edDefaultStock = findViewById(R.id.edDefaultStock);
        crdCancel = findViewById(R.id.crdCancel);
        crdSaveNow = findViewById(R.id.crdSaveNow);
        imgPlus = findViewById(R.id.imgPlus);
        imgMinus = findViewById(R.id.imgMinus);
        edDefaultStock.setText(MySession.getDefaultStock(mActivity));
        imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusMinus(false);
            }
        });
        imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusMinus(true);
            }
        });
        crdCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onSelectListener.onCancel();
            }
        });
        crdSaveNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sDefaultStock = edDefaultStock.getText().toString();


                MySession.set(mActivity, sDefaultStock, MySession.Share_DefaultStock);
                dismiss();
                onSelectListener.onDone();

            }
        });

    }


    public void plusMinus(boolean isPlus) {
        float fStockCount = URLString.getFloat(URLString.isNull(edDefaultStock.getText().toString().trim()));
        if (isPlus) {
//            if (fStockCount < 999) {
            fStockCount = fStockCount + 1;
//            }
            edDefaultStock.setText(URLString.decimalFormat.format(fStockCount));
        } else {
//            if (fStockCount > 1) {
            fStockCount = fStockCount - 1;
//            }
            edDefaultStock.setText(URLString.decimalFormat.format(fStockCount));
        }
    }
}