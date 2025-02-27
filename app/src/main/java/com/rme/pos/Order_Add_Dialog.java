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
import com.rme.roomdb.OrderTable;
import com.rme.roomdb.ProductTable;
import com.rme.roomdb.StockCountTable;
import com.rme.sunmi.SunmiScanner;
import com.rme.utils.AsyncTasks;
import com.rme.utils.ConvertDate;
import com.rme.utils.DatePicker;
import com.rme.utils.MySession;
import com.rme.utils.URLString;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class Order_Add_Dialog extends Dialog {

    public Activity mActivity;
    TextView txtTitle, txtCancelNow;
    TextInputEditText edDate, edOrderNo, edBarcode, edDescription;
    CardView crdCancel, crdSave;
    LinearLayout layBarcodeScan;
    EditText edOrderQty;
    ImageView imgMinus, imgPlus;
    OrderTable selectOrderTable;
    String sOldOrderNumber = "";

    public interface onSelectListener {
        public void onCancel();

        public void onDone();

        public void onDone(String sOldOrderNumber);

    }

    private onSelectListener onSelectListener;

    public Order_Add_Dialog(Activity a, OrderTable selectOrderTable, String sOldOrderNumber, onSelectListener onSelectListener) {
        super(a, R.style.MYDIALOG);
        // TODO Auto-generated constructor stub
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        this.mActivity = a;
        this.sOldOrderNumber = sOldOrderNumber;
        this.selectOrderTable = selectOrderTable;
        this.onSelectListener = onSelectListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.order_add_dialog);

        edOrderNo = findViewById(R.id.edOrderNo);
        edBarcode = findViewById(R.id.edBarcode);
        txtTitle = findViewById(R.id.txtTitle);
        edDescription = findViewById(R.id.edDescription);
        edDate = findViewById(R.id.edDate);
        edOrderQty = findViewById(R.id.edOrderQty);
        imgMinus = findViewById(R.id.imgMinus);
        imgPlus = findViewById(R.id.imgPlus);
        crdCancel = findViewById(R.id.crdCancel);
        crdSave = findViewById(R.id.crdSave);
        layBarcodeScan = findViewById(R.id.layBarcodeScan);
        edOrderQty.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
//               scanBarcode();
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
                String sBarcode = edBarcode.getText().toString().trim();
                float fOrderQty = URLString.getFloat(URLString.isNull(edOrderQty.getText().toString().trim()).replaceAll(" ", ""));
                String sDescription = edDescription.getText().toString().trim();
                String sDate = edDate.getText().toString().trim();
                if (sOrderNo.isEmpty()) {
                    edOrderNo.setError("Please enter order number");
                    edOrderNo.requestFocus();
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

//                if (fOrderQty <= 0) {
//                    ToastMsg.mToastMsg(mActivity, "Please Enter Order Qty.", 1);
//                    return;
//                }
                addNow(sBarcode, sDescription, sOrderNo, URLString.decimalFormat.format(fOrderQty), sDate);
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
        edOrderQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String sOrderQty = URLString.isNull(edOrderQty.getText().toString().trim()).replaceAll(" ", "");
                if (!sOrderQty.isEmpty()) {
                    float fOrderQty = URLString.getFloat(sOrderQty);
                    if (fOrderQty == 0) {
                        edOrderQty.setText("");
                    }
                }
            }
        });

        if (!selectOrderTable.getBarcode().isEmpty()) {
            txtTitle.setText("Edit Order");
            edOrderNo.setText(URLString.isNull(selectOrderTable.getOrderNumber()));
            edBarcode.setText(URLString.isNull(selectOrderTable.getBarcode()));
            edDescription.setText(URLString.isNull(selectOrderTable.getDescription()));
            edOrderQty.setText(URLString.isNull(selectOrderTable.getOrderQty()));
            edDate.setText(URLString.isNull(selectOrderTable.getOrderDate()));
            edBarcode.setFocusable(false);
            edBarcode.setFocusableInTouchMode(false);
            edOrderNo.setFocusable(false);
            edOrderNo.setFocusableInTouchMode(false);
        } else {
            txtTitle.setText("New Order");
            edOrderQty.setText("");
            edDate.setText(ConvertDate.getDateToDMY(new Date()));
            edOrderNo.setText(sOldOrderNumber);
            if (!sOldOrderNumber.isEmpty()) {
                edOrderNo.setFocusable(false);
                edOrderNo.setFocusableInTouchMode(false);
                edBarcode.requestFocus();
            } else {
                edOrderNo.requestFocus();
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

    public void checkDate() {
            String sPrevDate = ConvertDate.getDateToDMY(ConvertDate.getDateByInt(-1));
            if (sPrevDate.equals(edDate.getText().toString())){
                edDate.setText(ConvertDate.getDateToDMY(new Date()));
            }
    }

    public void addNow(String sBarcode, String sDescription, String sOrderNo, String sOrderQty, String sDate) {

        new AsyncTasks() {

            @Override
            public void onPreExecute() {


            }

            @Override
            public void doInBackground() {
                AppDatabase appDatabase = DatabaseClient.getInstance(mActivity).getAppDatabase();
                OrderTable orderTable = new OrderTable(sBarcode, sDescription, sOrderNo, sOrderQty, sDate, MySession.get(mActivity, MySession.Share_BranchID), "" + new Date().getTime());
                if (!selectOrderTable.getBarcode().isEmpty()) {
                    orderTable.setID(selectOrderTable.getID());
                    appDatabase.orderDao().update(orderTable);
                } else {
                    appDatabase.orderDao().insert(orderTable);
                }


            }


            @Override
            public void onPostExecute() {
                dismiss();

                if (!selectOrderTable.getBarcode().isEmpty()) {
                    onSelectListener.onDone();
                } else {
                    onSelectListener.onDone(sOrderNo);
                }

            }
        }.execute();
    }

    public void plusMinus(boolean isPlus) {
        float fQty = URLString.getFloat(URLString.isNull(edOrderQty.getText().toString().trim()));
        if (isPlus) {
            if (fQty < 999) {
                fQty = fQty + 1;
            }
            edOrderQty.setText(URLString.decimalFormat.format(fQty));
        } else {
            if (fQty > 0) {
                fQty = fQty - 1;
            }
            edOrderQty.setText(URLString.decimalFormat.format(fQty));
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

}