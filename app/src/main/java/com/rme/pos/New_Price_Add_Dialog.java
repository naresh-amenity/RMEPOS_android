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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;
import com.rme.roomdb.AppDatabase;
import com.rme.roomdb.DatabaseClient;
import com.rme.roomdb.ProductTable;
import com.rme.sunmi.SunmiScanner;
import com.rme.utils.AsyncTasks;
import com.rme.utils.URLString;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class New_Price_Add_Dialog extends Dialog {

    public Activity mActivity;
    TextView txtTitle;
    TextInputEditText edBarcode, edDescription, edStock, edPrice1, edPrice2, edNewPrice1, edNewPrice2;
    CardView crdCancel, crdSave;
    LinearLayout layBarcodeScan;
    ProductTable productTable;
    boolean isFromEdit = false;
    int iID = 0;
    public interface onSelectListener {
        public void onCancel();

        public void onDone();

        public void onDoneAndNext();

    }

    private onSelectListener onSelectListener;

    public New_Price_Add_Dialog(Activity a, ProductTable productTable, onSelectListener onSelectListener) {
        super(a, R.style.MYDIALOG);
        // TODO Auto-generated constructor stub
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        this.mActivity = a;
        this.productTable = productTable;
        this.onSelectListener = onSelectListener;
        if (!productTable.getBarcode().isEmpty()) {
            iID = productTable.getID();
            isFromEdit = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.new_price_add_dialog);

        edBarcode = findViewById(R.id.edBarcode);
        txtTitle = findViewById(R.id.txtTitle);
        edDescription = findViewById(R.id.edDescription);
        edStock = findViewById(R.id.edStock);
        edPrice1 = findViewById(R.id.edPrice1);
        edPrice2 = findViewById(R.id.edPrice2);
        edNewPrice1 = findViewById(R.id.edNewPrice1);
        edNewPrice2 = findViewById(R.id.edNewPrice2);
        crdCancel = findViewById(R.id.crdCancel);
        crdSave = findViewById(R.id.crdSave);
        layBarcodeScan = findViewById(R.id.layBarcodeScan);
        if (isFromEdit) {
            txtTitle.setText("Edit Price");
        } else {
            txtTitle.setText("New Price");
        }
        layBarcodeScan.setVisibility(View.GONE);
//        layBarcodeScan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//             scanBarcode();
//            }
//        });
        edNewPrice2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.e("actionId", "=" + actionId);
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_SEARCH) {
                    crdSave.performClick();
                    return true;
                }
                return false;
            }
        });
        edNewPrice1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edNewPrice1.setText("");
                }
            }
        });
        edNewPrice2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edNewPrice2.setText("");
                }
            }
        });
        edNewPrice1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    edNewPrice1.setText("");
                    return true;
                }
                return false;
            }
        });
        edNewPrice2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    edNewPrice2.setText("");
                    return true;
                }
                return false;
            }
        });

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
                String sBarcode = edBarcode.getText().toString().trim();
                String sStock = edStock.getText().toString().trim();
                String sDescription = edDescription.getText().toString().trim();
                String sPrice1 = edPrice1.getText().toString().trim();
                String sPrice2 = edPrice2.getText().toString().trim();
                String sNewPrice1 = edNewPrice1.getText().toString().trim();
                String sNewPrice2 = edNewPrice2.getText().toString().trim();
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

                if (!sNewPrice1.isEmpty() && !sNewPrice1.contains(".")) {
                    if (sNewPrice1.length() == 1) {
                        sNewPrice1 = "0.0" + sNewPrice1;
                    } else if (sNewPrice1.length() == 2) {
//                        String s1 = sNewPrice1.substring(0, 1);
//                        String s2 = sNewPrice1.substring(1, sNewPrice1.length());
//                        sNewPrice1 = s1 + "." + s2;
                        sNewPrice1 = "0." + sNewPrice1;
                    } else {
                        String s1 = sNewPrice1.substring(0, sNewPrice1.length() - 2);
                        String s2 = sNewPrice1.substring(sNewPrice1.length() - 2, sNewPrice1.length());
                        sNewPrice1 = s1 + "." + s2;
                    }
                }
                if (!sNewPrice2.isEmpty() && !sNewPrice2.contains(".")) {
                    if (sNewPrice2.length() == 1) {
                        sNewPrice2 = "0.0" + sNewPrice2;
                    } else if (sNewPrice2.length() == 2) {
//                        String s1 = sNewPrice2.substring(0, 1);
//                        String s2 = sNewPrice2.substring(1, sNewPrice2.length());
//                        sNewPrice2 = s1 + "." + s2;
                        sNewPrice2 = "0." + sNewPrice2;
                    } else {
                        String s1 = sNewPrice2.substring(0, sNewPrice2.length() - 2);
                        String s2 = sNewPrice2.substring(sNewPrice2.length() - 2, sNewPrice2.length());
                        sNewPrice2 = s1 + "." + s2;
                    }
                }
                addNow(sBarcode, sDescription, sStock, sPrice1, sPrice2, sNewPrice1, sNewPrice2);


            }
        });
        if (!productTable.getBarcode().isEmpty()) {
            edBarcode.setText(URLString.isNull(productTable.getBarcode()));
        }else{
//            final android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    scanBarcode();
//                }
//            }, 200);
        }
        edBarcode.requestFocus();


    }

    public void addNow(String sBarcode, String sDescription, String sStock, String sPrice1, String sPrice2, String sNewPrice1, String sNewPrice2) {

        new AsyncTasks() {

            @Override
            public void onPreExecute() {


            }

            @Override
            public void doInBackground() {
                ProductTable productTable1 = new ProductTable(sBarcode, sDescription, sStock, sPrice1, sPrice2, sNewPrice1, sNewPrice2, "" + new Date().getTime(), "1");

                AppDatabase appDatabase = DatabaseClient.getInstance(mActivity).getAppDatabase();
                if (iID != 0) {
                    productTable1.setID(iID);
                    appDatabase.productDao().update(productTable1);
                } else {
                    appDatabase.productDao().insert(productTable1);
                }

            }


            @Override
            public void onPostExecute() {


                dismiss();
                if (isFromEdit) {
                    onSelectListener.onDone();
                } else {
                    onSelectListener.onDoneAndNext();
                }
            }
        }.execute();
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
                            edStock.setText("");
                            edPrice1.setText("");
                            edPrice2.setText("");
                            edNewPrice1.setText("");
                            edNewPrice2.setText("");
                        } else {

                            edDescription.setText(URLString.isNull(productArray.get(0).getDescription()));
                            edStock.setText(URLString.isNull(productArray.get(0).getStock()));
                            edPrice1.setText(URLString.isNull(productArray.get(0).getPrice1()));
                            edPrice2.setText(URLString.isNull(productArray.get(0).getPrice2()));
                            edNewPrice1.setText(URLString.isNull(productArray.get(0).getNewPrice1()));
                            edNewPrice2.setText(URLString.isNull(productArray.get(0).getNewPrice2()));
                            iID = productArray.get(0).getID();
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
//                edStock.setText(URLString.isNull(productTable.getStock()));
//                edPrice1.setText(URLString.isNull(productTable.getPrice1()));
//                edPrice2.setText(URLString.isNull(productTable.getPrice2()));
//                edNewPrice1.setText(URLString.isNull(productTable.getNewPrice1()));
//                edNewPrice2.setText(URLString.isNull(productTable.getNewPrice2()));
//                iID = productTable.getID();
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