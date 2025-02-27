package com.rme.pos;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;


public class Stock_Option_Dialog extends Dialog {

    public Activity mActivity;

    CardView crdStockTake,crdOrders,crdDeliveries,crdTransferDespatch,crdTransferReceipt;

    public interface onSelectListener {
        public void onStockTake();
        public void onOrders();
        public void onDeliveries();
        public void onTransferDespatch();
        public void onTransferReceipt();

    }

    private onSelectListener onSelectListener;

    public Stock_Option_Dialog(Activity a, onSelectListener onSelectListener) {
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
        setContentView(R.layout.stock_option_dialog);

        crdStockTake = findViewById(R.id.crdStockTake);
        crdOrders = findViewById(R.id.crdOrders);
        crdDeliveries = findViewById(R.id.crdDeliveries);
        crdTransferDespatch = findViewById(R.id.crdTransferDespatch);
        crdTransferReceipt = findViewById(R.id.crdTransferReceipt);


        crdStockTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onSelectListener.onStockTake();
            }
        });

        crdOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onSelectListener.onOrders();
            }
        });

        crdDeliveries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onSelectListener.onDeliveries();
            }
        });

        crdTransferDespatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onSelectListener.onTransferDespatch();
            }
        });

        crdTransferReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onSelectListener.onTransferReceipt();
            }
        });

    }


}