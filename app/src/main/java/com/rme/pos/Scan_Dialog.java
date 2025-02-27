package com.rme.pos;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;
import com.rme.roomdb.AppDatabase;
import com.rme.roomdb.DatabaseClient;
import com.rme.roomdb.ProductTable;
import com.rme.sunmi.SunmiScanner;
import com.rme.utils.AsyncTasks;
import com.rme.utils.ConvertDate;
import com.rme.utils.MySession;
import com.rme.utils.ToastMsg;
import com.rme.utils.URLString;

import java.util.ArrayList;
import java.util.List;


public class Scan_Dialog extends Dialog {

    public Activity mActivity;

    View scannerLayout;
    View scannerBar;
TextView txtCancel;
    ObjectAnimator animator;
    EditText edTemp;
    public interface onSelectListener {
        public void onCancel();
        public void onScan(ProductTable productTable);
    }

    private onSelectListener onSelectListener;

    public Scan_Dialog(Activity a, onSelectListener onSelectListener) {
        super(a, R.style.MYDIALOG);
        // TODO Auto-generated constructor stub
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        this.mActivity = a;
        this.onSelectListener = onSelectListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.scan_dialog);


        scannerLayout = findViewById(R.id.scannerLayout);
        scannerBar = findViewById(R.id.scannerBar);
        txtCancel = findViewById(R.id.txtCancel);
        edTemp = findViewById(R.id.edTemp);
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sunmiScanner!=null){
                    sunmiScanner.closeScannerListener();
                    sunmiScanner.destory();
                }
                try {
                    if (animator!=null){
                        animator.pause();
                        animator.cancel();
                        animator = null;
                    }
                } catch (Exception e) {
                }

                dismiss();
                onSelectListener.onCancel();
            }
        });
        edTemp.requestFocus();
        startAnimation();
        initScanner();
    }

    public void startAnimation() {

        animator = null;

        ViewTreeObserver vto = scannerLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                scannerLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    scannerLayout.getViewTreeObserver().
                            removeGlobalOnLayoutListener(this);

                } else {
                    scannerLayout.getViewTreeObserver().
                            removeOnGlobalLayoutListener(this);
                }

                float destination = (float) (scannerLayout.getY() +
                        scannerLayout.getHeight());

                animator = ObjectAnimator.ofFloat(scannerBar, "translationY",
                        scannerLayout.getY(),
                        destination);

                animator.setRepeatMode(ValueAnimator.REVERSE);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(3000);
                animator.start();

            }
        });
    }
    SunmiScanner sunmiScanner;

    private void initScanner() {
        Log.e("sunmiScanner", "=Call");
        sunmiScanner = new SunmiScanner(mActivity);
        sunmiScanner.analysisBroadcast();

        sunmiScanner.setScannerListener(new SunmiScanner.OnScannerListener() {
            @Override
            public void onScanData(String data, SunmiScanner.DATA_DISCRIBUTE_TYPE type) {
                edTemp.requestFocus();
                Log.e("Data", "=" + data);
                Log.e("type", "=" + type);
                if (!data.equalsIgnoreCase(edTemp.getText().toString())) {
                    searchProduct(data);
                }
            }

            @Override
            public void onResponseData(String data, SunmiScanner.DATA_DISCRIBUTE_TYPE type) {
                Log.e("Data2", "=" + data);
                Log.e("type2", "=" + type);

            }

            @Override
            public void onResponseTimeout() {
                Log.e("type2", "=onResponseTimeout");


            }
        });
    }
    boolean isSearching = false;
    boolean isSearchPending = false;
    public void searchProduct(String sBarcodeScan) {
        if (!isSearching) {
            isSearching = true;

            new AsyncTasks() {
                List<ProductTable> productArray = new ArrayList<>();
                String sBarcode = "";

                @Override
                public void onPreExecute() {
                        sBarcode = sBarcodeScan;

                }

                @Override
                public void doInBackground() {
                    if (!sBarcode.isEmpty()) {

                        AppDatabase appDatabase = DatabaseClient.getInstance(mActivity).getAppDatabase();
                        productArray = appDatabase.productDao().getProductByBarcode(sBarcode);
                    }

                }


                @Override
                public void onPostExecute() {
                    isSearching = false;
                    if (isSearchPending) {
                        isSearchPending = false;
                        searchProduct(sBarcodeScan);
                    } else {
                        if (productArray.isEmpty()) {
                            ToastMsg.mToastMsg(mActivity,"Product not found.",1);
                        } else {
                            if (sunmiScanner!=null){
                                sunmiScanner.closeScannerListener();
                                sunmiScanner.destory();
                            }
                            dismiss();
                            onSelectListener.onScan(productArray.get(0));
                        }
                    }

                }
            }.execute();
        } else {
            isSearchPending = true;
        }

    }
}