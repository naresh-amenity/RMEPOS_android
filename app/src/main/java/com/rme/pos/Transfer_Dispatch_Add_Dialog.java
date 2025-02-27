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
import com.rme.roomdb.TransferDispatchTable;
import com.rme.sunmi.SunmiScanner;
import com.rme.utils.AsyncTasks;
import com.rme.utils.ConvertDate;
import com.rme.utils.DatePicker;
import com.rme.utils.MySession;
import com.rme.utils.URLString;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Transfer_Dispatch_Add_Dialog extends Dialog {

    public Activity mActivity;
    TextView txtTitle, txtCancelNow;
    TextInputEditText edDate,edTransferNo, edBarcode, edDescription;
    CardView crdCancel, crdSave;
    LinearLayout layBarcodeScan;
    EditText edTransferQty;
    ImageView imgMinus, imgPlus;
    TransferDispatchTable selectedTransferDispatchTable;
    String sOldTransferNumber = "";

    public interface onSelectListener {
        public void onCancel();

        public void onDone();
        public void onDone(String sOldTransferNumber);

    }

    private onSelectListener onSelectListener;

    public Transfer_Dispatch_Add_Dialog(Activity a, TransferDispatchTable selectedTransferDispatchTable,String sOldTransferNumber, onSelectListener onSelectListener) {
        super(a, R.style.MYDIALOG);
        // TODO Auto-generated constructor stub
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        this.mActivity = a;
        this.selectedTransferDispatchTable = selectedTransferDispatchTable;
        this.sOldTransferNumber = sOldTransferNumber;
        this.onSelectListener = onSelectListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.transfer_dispatch_add_dialog);

        edTransferQty = findViewById(R.id.edTransferQty);
        edTransferNo = findViewById(R.id.edTransferNo);
        edBarcode = findViewById(R.id.edBarcode);
        txtTitle = findViewById(R.id.txtTitle);
        edDescription = findViewById(R.id.edDescription);
        edDate = findViewById(R.id.edDate);

        imgMinus = findViewById(R.id.imgMinus);
        imgPlus = findViewById(R.id.imgPlus);
        crdCancel = findViewById(R.id.crdCancel);
        crdSave = findViewById(R.id.crdSave);
        layBarcodeScan = findViewById(R.id.layBarcodeScan);
        layBarcodeScan.setVisibility(View.GONE);
        edTransferQty.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_SEARCH) {
                    crdSave.performClick();
                    return true;
                }
                return false;
            }
        });


//        layBarcodeScan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//             scanBarcode();
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
        edTransferQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String sTransferQty = URLString.isNull(edTransferQty.getText().toString().trim()).replaceAll(" ", "");
                if (!sTransferQty.isEmpty()){
                    float fTransferQty = URLString.getFloat(sTransferQty);
                    if (fTransferQty == 0) {
                        edTransferQty.setText("");
                    }
                }
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
                String sTransferNo = edTransferNo.getText().toString().trim();
                String sBarcode = edBarcode.getText().toString().trim();
                float fTransferQty = URLString.getFloat(URLString.isNull(edTransferQty.getText().toString().trim()).replaceAll(" ", ""));
                String sDescription = edDescription.getText().toString().trim();
                String sDate = edDate.getText().toString().trim();

                if (sTransferNo.isEmpty()) {
                    edTransferNo.setError("Please enter Transfer number");
                    edTransferNo.requestFocus();
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
//                if (fTransferQty <= 0) {
//                    ToastMsg.mToastMsg(mActivity, "Please Enter Transfer Qty.", 1);
//                    return;
//                }
                addNow(sBarcode,sDescription,sDate,sTransferNo,URLString.decimalFormat.format(fTransferQty));
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
        if (!selectedTransferDispatchTable.getBarcode().isEmpty()) {
            txtTitle.setText("Edit Transfer Dispatch");
            edTransferNo.setText(URLString.isNull(selectedTransferDispatchTable.getTransferNumber()));
            edBarcode.setText(URLString.isNull(selectedTransferDispatchTable.getBarcode()));
            edDescription.setText(URLString.isNull(selectedTransferDispatchTable.getDescription()));
            edTransferQty.setText(URLString.isNull(selectedTransferDispatchTable.getTransferQty()));
            edDate.setText(URLString.isNull(selectedTransferDispatchTable.getDispatchDate()));
            edBarcode.setFocusable(false);
            edBarcode.setFocusableInTouchMode(false);
            edTransferNo.setFocusable(false);
            edTransferNo.setFocusableInTouchMode(false);
        } else {
            txtTitle.setText("Add Transfer Dispatch");
            edTransferQty.setText("0");
            edDate.setText(ConvertDate.getDateToDMY(new Date()));
            edTransferNo.setText(sOldTransferNumber);
            if (!sOldTransferNumber.isEmpty()){
                edTransferNo.setFocusable(false);
                edTransferNo.setFocusableInTouchMode(false);
                edBarcode.requestFocus();
            }else{
                edTransferNo.requestFocus();
            }
        }
    }

    public void checkDate() {
        String sPrevDate = ConvertDate.getDateToDMY(ConvertDate.getDateByInt(-1));
        if (sPrevDate.equals(edDate.getText().toString())){
            edDate.setText(ConvertDate.getDateToDMY(new Date()));
        }
    }
    public void addNow(String sBarcode, String sDescription, String sDispatchDate, String sTransferNumber, String sTransferQty) {

        new AsyncTasks() {

            @Override
            public void onPreExecute() {


            }

            @Override
            public void doInBackground() {
                TransferDispatchTable transferDispatchTable = new TransferDispatchTable(sBarcode,sDescription,sDispatchDate,sTransferNumber,sTransferQty, MySession.get(mActivity, MySession.Share_BranchID), "" + new Date().getTime());
                AppDatabase appDatabase = DatabaseClient.getInstance(mActivity).getAppDatabase();

                if (!selectedTransferDispatchTable.getBarcode().isEmpty()) {
                    transferDispatchTable.setID(selectedTransferDispatchTable.getID());
                    appDatabase.transferDispatchDao().update(transferDispatchTable);
                } else {
                    appDatabase.transferDispatchDao().insert(transferDispatchTable);
                }

            }


            @Override
            public void onPostExecute() {
                dismiss();
                if (!selectedTransferDispatchTable.getBarcode().isEmpty()) {
                    onSelectListener.onDone();
                } else {
                    onSelectListener.onDone(sTransferNumber);
                }
            }
        }.execute();
    }
    public void plusMinus(boolean isPlus) {
        float fQty = URLString.getFloat(URLString.isNull(edTransferQty.getText().toString().trim()));
        if (isPlus) {
            if (fQty < 999) {
                fQty = fQty + 1;
            }
            edTransferQty.setText(URLString.decimalFormat.format(fQty));
        } else {
            if (fQty > 0) {
                fQty = fQty - 1;
            }
            edTransferQty.setText(URLString.decimalFormat.format(fQty));
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
                        productArray =appDatabase.productDao().getProductByBarcode(sBarcode);

                        if (!productArray.isEmpty()){
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