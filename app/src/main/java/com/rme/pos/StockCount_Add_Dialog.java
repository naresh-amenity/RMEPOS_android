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
import com.rme.roomdb.ProductTable;
import com.rme.roomdb.StockCountTable;
import com.rme.sunmi.SunmiScanner;
import com.rme.utils.AsyncTasks;
import com.rme.utils.MySession;
import com.rme.utils.URLString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class StockCount_Add_Dialog extends Dialog {

    public Activity mActivity;
    TextView txtTitle, txtCancelNow;
    TextInputEditText edCurrentStock, edBarcode, edDescription;
    CardView crdCancel, crdSave;
    LinearLayout layBarcodeScan;
    EditText edStockCount;
    TextView txtTotalCounted;
    ImageView imgMinus, imgPlus;
    StockCountTable selectedStockTable;
    boolean isFromEdit = false;
    float fPreviewTotalCount = 0;

    public interface onSelectListener {
        public void onCancel();

        public void onDone();

        public void onDoneAgain();

    }

    private onSelectListener onSelectListener;

    public StockCount_Add_Dialog(Activity a, StockCountTable selectedStockTable, onSelectListener onSelectListener) {
        super(a, R.style.MYDIALOG);
        // TODO Auto-generated constructor stub
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        this.mActivity = a;
        this.selectedStockTable = selectedStockTable;
        this.onSelectListener = onSelectListener;
        if (!selectedStockTable.getBarcode().isEmpty()) {
            isFromEdit = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.stock_count_add_dialog);

        edCurrentStock = findViewById(R.id.edCurrentStock);
        edBarcode = findViewById(R.id.edBarcode);
        txtTitle = findViewById(R.id.txtTitle);
        edDescription = findViewById(R.id.edDescription);
        edStockCount = findViewById(R.id.edStockCount);
        txtTotalCounted = findViewById(R.id.txtTotalCounted);
        imgMinus = findViewById(R.id.imgMinus);
        imgPlus = findViewById(R.id.imgPlus);
        crdCancel = findViewById(R.id.crdCancel);
        crdSave = findViewById(R.id.crdSave);
        layBarcodeScan = findViewById(R.id.layBarcodeScan);
        layBarcodeScan.setVisibility(View.GONE);

//            layBarcodeScan.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                  scanBarcode();
//                }
//            });
        edStockCount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_SEARCH) {
                    crdSave.performClick();
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
                    searchStockCount("");
            }
        });
        edStockCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String sStockCount = URLString.isNull(edStockCount.getText().toString().trim()).replaceAll(" ", "");
                if (!sStockCount.isEmpty()) {
                    float fStockCount = URLString.getFloat(sStockCount);
                    if (fStockCount == 0) {
                        edStockCount.setText("");
                    }
                }
                calculateTotal();

            }
        });

        edCurrentStock.setFocusable(false);
        edCurrentStock.setFocusableInTouchMode(false);
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
                float fStockCount = URLString.getFloat(URLString.isNull(edStockCount.getText().toString().trim()).replaceAll(" ", ""));
                String sDescription = edDescription.getText().toString().trim();


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
//                if (fStockCount <= 0) {
//                    ToastMsg.mToastMsg(mActivity, "Please Enter Stock Count.", 1);
//                    return;
//                }
                addNow(sBarcode, sDescription, URLString.decimalFormat.format(fStockCount));
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
        if (!selectedStockTable.getBarcode().isEmpty()) {
            edBarcode.setText(URLString.isNull(selectedStockTable.getBarcode()));
            edDescription.setText(URLString.isNull(selectedStockTable.getDescription()));
            edStockCount.setText(URLString.isNull(selectedStockTable.getStockCount()));
            edBarcode.setFocusable(false);
            edBarcode.setFocusableInTouchMode(false);
//            layBarcodeScan.setVisibility(View.GONE);
        } else {
            edStockCount.setText(MySession.getDefaultStock(mActivity));
            edBarcode.requestFocus();
//            final android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    scanBarcode();
//                }
//            }, 200);
        }
    }

    public void addNow(String sBarcode, String sDescription, String sStockCount) {

        new AsyncTasks() {

            @Override
            public void onPreExecute() {


            }

            @Override
            public void doInBackground() {
                StockCountTable stockCountTable = new StockCountTable(sBarcode, sDescription, sStockCount, "" + new Date().getTime());

                AppDatabase appDatabase = DatabaseClient.getInstance(mActivity).getAppDatabase();

                if (isFromEdit) {
                    stockCountTable.setID(selectedStockTable.getID());
                    appDatabase.stockCountDao().update(stockCountTable);
                } else {
                    appDatabase.stockCountDao().insert(stockCountTable);
                }

            }


            @Override
            public void onPostExecute() {

                dismiss();
                if (isFromEdit) {
                    onSelectListener.onDone();
                } else {
                    onSelectListener.onDoneAgain();
                }
            }
        }.execute();
    }

    public void calculateTotal() {
        float fStockCount = URLString.getFloat(URLString.isNull(edStockCount.getText().toString().trim()).replaceAll(" ", ""));
        float fTotalStock = fPreviewTotalCount + fStockCount;
        txtTotalCounted.setText(URLString.decimalFormat.format(fTotalStock));
    }

    public void plusMinus(boolean isPlus) {
        float fStockCount = URLString.getFloat(URLString.isNull(edStockCount.getText().toString().trim()));
        if (isPlus) {
//            if (fStockCount < 999) {
            fStockCount = fStockCount + 1;
//            }
            edStockCount.setText(URLString.decimalFormat.format(fStockCount));
        } else {
//            if (fStockCount > 1) {
            fStockCount = fStockCount - 1;
//            }
            edStockCount.setText(URLString.decimalFormat.format(fStockCount));
        }
        calculateTotal();
    }

    boolean isSearching = false;
    boolean isSearchPending = false;
    boolean isProductFind = false;
    boolean isFirstTime = true;

    public void searchStockCount(String sBarcodeScan) {
        if (!isSearching) {
            isSearching = true;
            new AsyncTasks() {
                List<StockCountTable> editStockCountArray = new ArrayList<>();
                List<ProductTable> productArray = new ArrayList<>();
                String sBarcode = "";
                String sEditID = "";

                @Override
                public void onPreExecute() {
                    fPreviewTotalCount = 0;
                    isProductFind = false;

                    if (!sBarcodeScan.isEmpty()) {
                        sBarcode = sBarcodeScan;
                    } else {
                        sBarcode = edBarcode.getText().toString().trim();
                    }
                    if (!selectedStockTable.getBarcode().isEmpty()) {
                        sEditID = URLString.isNull(selectedStockTable.getID());
                    }
                }

                @Override
                public void doInBackground() {
                    if (!sBarcode.isEmpty()) {
                        AppDatabase appDatabase = DatabaseClient.getInstance(mActivity).getAppDatabase();
                        editStockCountArray = appDatabase.stockCountDao().getByBarcode(sBarcode);
                        productArray = appDatabase.productDao().getProductByBarcode(sBarcode);

                        if (!productArray.isEmpty()) {
                            isProductFind = true;
                        }

                        for (int i = 0; i < editStockCountArray.size(); i++) {
                            if (sEditID.isEmpty() && !sEditID.equalsIgnoreCase(URLString.isNull(editStockCountArray.get(i).getID()))) {
                                fPreviewTotalCount = fPreviewTotalCount + URLString.getFloat(editStockCountArray.get(i).getStockCount());
                            }
                        }
                    }

                }


                @Override
                public void onPostExecute() {
                    isSearching = false;
                    if (isSearchPending) {
                        isSearchPending = false;
                        searchStockCount(sBarcodeScan);
                    } else {
                        if (!productArray.isEmpty()) {
                            edDescription.setText(URLString.isNull(productArray.get(0).getDescription()));
                            edCurrentStock.setText(URLString.isNull(productArray.get(0).getStock()));
                            if (!isFromEdit) {
                                float fStockCount = URLString.getFloat(URLString.isNull(edStockCount.getText().toString().trim()).replaceAll(" ", ""));
                                if (fStockCount != 0) {
                                    crdSave.performClick();
                                }
                            }
                        } else {
                            edDescription.setText("");
                            edCurrentStock.setText("");
                        }
                        if (isFirstTime && !selectedStockTable.getBarcode().isEmpty()) {
                            edStockCount.setText(URLString.decimalFormat.format(URLString.getFloat(selectedStockTable.getStockCount())));
                        }
                        calculateTotal();
                        isFirstTime = false;
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
//                isSearchBarcode = true;
//                edBarcode.setText(URLString.isNull(productTable.getBarcode()));
//
//            }
//        }).show();
//    }

}