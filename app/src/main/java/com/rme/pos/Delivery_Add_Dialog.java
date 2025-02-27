package com.rme.pos;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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


public class Delivery_Add_Dialog extends Dialog {

    public Activity mActivity;
    TextView txtTitle, txtCancelNow;
    TextInputEditText edDate, edOrderNo, edDeliveryNo, edBarcode, edDescription;
    CardView crdCancel, crdSave;
    LinearLayout layBarcodeScan;
    EditText edDeliveryQty;
    ImageView imgMinus, imgPlus;
    DeliveryTable selectedDeliveryTable;
    String sOldOrderNumber = "", sOldDeliveryNo = "";

    public interface onSelectListener {
        public void onCancel();

        public void onDone();

        public void onDone(String sOldOrderNumber, String sOldDeliveryNo);

    }

    private onSelectListener onSelectListener;

    public Delivery_Add_Dialog(Activity a, DeliveryTable selectedDeliveryTable, String sOldOrderNumber, String sOldDeliveryNo, onSelectListener onSelectListener) {
        super(a, R.style.MYDIALOG);
        // TODO Auto-generated constructor stub
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        this.mActivity = a;
        this.selectedDeliveryTable = selectedDeliveryTable;
        this.sOldOrderNumber = sOldOrderNumber;
        this.sOldDeliveryNo = sOldDeliveryNo;
        this.onSelectListener = onSelectListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.delivery_add_dialog);

        edOrderNo = findViewById(R.id.edOrderNo);
        edBarcode = findViewById(R.id.edBarcode);
        txtTitle = findViewById(R.id.txtTitle);
        edDescription = findViewById(R.id.edDescription);
        edDate = findViewById(R.id.edDate);
        edDeliveryQty = findViewById(R.id.edDeliveryQty);
        edDeliveryNo = findViewById(R.id.edDeliveryNo);
        imgMinus = findViewById(R.id.imgMinus);
        imgPlus = findViewById(R.id.imgPlus);
        crdCancel = findViewById(R.id.crdCancel);
        crdSave = findViewById(R.id.crdSave);
        layBarcodeScan = findViewById(R.id.layBarcodeScan);
        edDeliveryQty.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_SEARCH) {
                    crdSave.performClick();
                    return true;
                }
                return false;
            }
        });

        layBarcodeScan.setVisibility(View.GONE);
//        layBarcodeScan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                scanBarcode();
//            }
//        });
        edBarcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    searchProduct("");
            }
        });
        crdCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onSelectListener.onCancel();
            }
        });
        crdSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDate();
                String sOrderNo = edOrderNo.getText().toString().trim();
                String sDeliveryNo = edDeliveryNo.getText().toString().trim();
                String sBarcode = edBarcode.getText().toString().trim();
                float fDeliveryQty = URLString.getFloat(URLString.isNull(edDeliveryQty.getText().toString().trim()).replaceAll(" ", ""));
                String sDescription = edDescription.getText().toString().trim();
                String sDate = edDate.getText().toString().trim();

                if (sOrderNo.isEmpty()) {
                    edOrderNo.setError("Please enter order number");
                    edOrderNo.requestFocus();
                    return;
                }
                if (sDeliveryNo.isEmpty()) {
                    edDeliveryNo.setError("Please enter delivery number");
                    edDeliveryNo.requestFocus();
                    return;
                }
                if (sBarcode.isEmpty()) {
                    edBarcode.setError("Please enter/Scan barcode");
                    edBarcode.requestFocus();
                    return;
                }

                if (!isProductFind) {
                    edBarcode.setError("This product not available.");
                    edBarcode.requestFocus();
                    return;
                }
//                if (fDeliveryQty <= 0) {
//                    ToastMsg.mToastMsg(mActivity, "Please Enter Delivery Qty.", 1);
//                    return;
//                }
                addNow(sBarcode, sDescription, sDate, sOrderNo, sDeliveryNo, URLString.decimalFormat.format(fDeliveryQty));
            }
        });
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
        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePicker(mActivity, new DatePicker.onSelectListener() {
                    @Override
                    public void onDate(Date date) {
                        edDate.setText(ConvertDate.getDateToDMY(date));
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });
        edDeliveryQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String sDeliveryQty = URLString.isNull(edDeliveryQty.getText().toString().trim()).replaceAll(" ", "");
                if (!sDeliveryQty.isEmpty()) {
                    float fDeliveryQty = URLString.getFloat(sDeliveryQty);
                    if (fDeliveryQty == 0) {
                        edDeliveryQty.setText("");
                    }
                }
            }
        });
        if (!selectedDeliveryTable.getBarcode().isEmpty()) {
            txtTitle.setText("Edit Delivery");
            edOrderNo.setText(URLString.isNull(selectedDeliveryTable.getOrderNumber()));
            edDeliveryNo.setText(URLString.isNull(selectedDeliveryTable.getDeliveryNumber()));
            edBarcode.setText(URLString.isNull(selectedDeliveryTable.getBarcode()));
            edDescription.setText(URLString.isNull(selectedDeliveryTable.getDescription()));
            edDeliveryQty.setText(URLString.isNull(selectedDeliveryTable.getDeliveryQty()));
            edDate.setText(URLString.isNull(selectedDeliveryTable.getDeliveryDate()));
            edBarcode.setFocusable(false);
            edBarcode.setFocusableInTouchMode(false);
            layBarcodeScan.setVisibility(View.GONE);
            edOrderNo.setFocusable(false);
            edOrderNo.setFocusableInTouchMode(false);
            edDeliveryNo.setFocusable(false);
            edDeliveryNo.setFocusableInTouchMode(false);
        } else {
            txtTitle.setText("New Delivery");
            edDeliveryQty.setText("");
            edDate.setText(ConvertDate.getDateToDMY(new Date()));
            edOrderNo.setText(sOldOrderNumber);
            if (!sOldOrderNumber.isEmpty()) {
                edOrderNo.setFocusable(false);
                edOrderNo.setFocusableInTouchMode(false);
                edBarcode.requestFocus();
            }else{
                edOrderNo.requestFocus();
            }
            edDeliveryNo.setText(sOldDeliveryNo);
            if (!sOldDeliveryNo.isEmpty()) {
                edDeliveryNo.setFocusable(false);
                edDeliveryNo.setFocusableInTouchMode(false);
            }

//            final android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    scanBarcode();
//                }
//            }, 200);
        }


    }

//    boolean isSearchBarcode = true;
//
//    public void scanBarcode() {
//        isSearchBarcode = false;
//        new Scan_Dialog(mActivity, new Scan_Dialog.onSelectListener() {
//            @Override
//            public void onCancel() {
//                isSearchBarcode = true;
//            }
//
//            @Override
//            public void onScan(ProductTable productTable) {
//                isProductFind = true;
//
//                edBarcode.setText(URLString.isNull(productTable.getBarcode()));
//                edDescription.setText(URLString.isNull(productTable.getDescription()));
//
//                final android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        isSearchBarcode = true;
//                    }
//                }, 400);
//
//            }
//        }).show();
//    }

    public void checkDate() {
        String sPrevDate = ConvertDate.getDateToDMY(ConvertDate.getDateByInt(-1));
        if (sPrevDate.equals(edDate.getText().toString())){
            edDate.setText(ConvertDate.getDateToDMY(new Date()));
        }
    }
    public void addNow(String sBarcode, String sDescription, String sDeliveryDate, String sOrderNumber, String sDeliveryNumber, String sDeliveryQty) {

        new AsyncTasks() {

            @Override
            public void onPreExecute() {


            }

            @Override
            public void doInBackground() {
                AppDatabase appDatabase = DatabaseClient.getInstance(mActivity).getAppDatabase();

                DeliveryTable deliveryTable = new DeliveryTable(sBarcode, sDescription, sDeliveryDate, sOrderNumber, sDeliveryNumber, sDeliveryQty, MySession.get(mActivity, MySession.Share_BranchID), "" + new Date().getTime());
                if (!selectedDeliveryTable.getBarcode().isEmpty()) {
                    deliveryTable.setID(selectedDeliveryTable.getID());
                    appDatabase.deliveryDao().update(deliveryTable);
                } else {
                    appDatabase.deliveryDao().insert(deliveryTable);
                }
            }


            @Override
            public void onPostExecute() {
                dismiss();
                if (!selectedDeliveryTable.getBarcode().isEmpty()) {
                    onSelectListener.onDone();
                } else {
                    onSelectListener.onDone(sOrderNumber, sDeliveryNumber);
                }

            }
        }.execute();
    }

    public void plusMinus(boolean isPlus) {
        float fQty = URLString.getFloat(URLString.isNull(edDeliveryQty.getText().toString().trim()));
        if (isPlus) {
            if (fQty < 999) {
                fQty = fQty + 1;
            }
            edDeliveryQty.setText(URLString.decimalFormat.format(fQty));
        } else {
            if (fQty > 0) {
                fQty = fQty - 1;
            }
            edDeliveryQty.setText(URLString.decimalFormat.format(fQty));
        }
    }

    boolean isSearching = false;
    boolean isSearchPending = false;
    boolean isProductFind = false;

    public void searchProduct(String sBarcodeScan) {
        if (!isSearching) {
            isSearching = true;

            new AsyncTasks() {
                List<ProductTable> productArray = new ArrayList<>();
                String sBarcode = "";

                @Override
                public void onPreExecute() {
                    isProductFind = false;
                    if (!sBarcodeScan.isEmpty()) {
                        sBarcode = sBarcodeScan;
                    } else {
                        sBarcode = edBarcode.getText().toString().trim();
                    }
                }

                @Override
                public void doInBackground() {
                    if (!sBarcode.isEmpty()) {

                        AppDatabase appDatabase = DatabaseClient.getInstance(mActivity).getAppDatabase();
                        productArray = appDatabase.productDao().getProductByBarcode(sBarcode);
                        if (!productArray.isEmpty()) {
                            isProductFind = true;
                        }
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
                            edDescription.setText("");
                            edDeliveryQty.setText("");
                        } else {
                            edDescription.setText(URLString.isNull(productArray.get(0).getDescription()));
                        }
                    }

                }
            }.execute();
        } else {
            isSearchPending = true;
        }

    }

}