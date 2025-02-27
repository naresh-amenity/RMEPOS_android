package com.rme.pos;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.rme.DB.DBClass;
import com.rme.roomdb.AppDatabase;
import com.rme.roomdb.DatabaseClient;
import com.rme.roomdb.DeliveryTable;
import com.rme.roomdb.OrderTable;
import com.rme.roomdb.ProductTable;
import com.rme.roomdb.StockCountTable;
import com.rme.roomdb.TransferDispatchTable;
import com.rme.roomdb.TransferReceiptTable;
import com.rme.sunmi.SunmiScanner;
import com.rme.utils.AsyncTasks;
import com.rme.utils.ConvertDate;
import com.rme.utils.MyFunction;
import com.rme.utils.MySession;
import com.rme.utils.ProgressDialog;
import com.rme.utils.ToastMsg;
import com.rme.utils.URLString;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    TextView txtDate, txtBranch, txtSetupDate;
    TextView txtRemoveAllData, txtSetup, txtDownloadAllFiles, txtSecurity;
    CardView crdConnectDevice, crdPrice, crdStockTake, crdOrders, crdDeliveries, crdTransferDispatch, crdTransferReceipt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        txtDate = findViewById(R.id.txtDate);
        txtBranch = findViewById(R.id.txtBranch);
        txtSetupDate = findViewById(R.id.txtSetupDate);
        txtRemoveAllData = findViewById(R.id.txtRemoveAllData);
        txtSetup = findViewById(R.id.txtSetup);
        txtDownloadAllFiles = findViewById(R.id.txtDownloadAllFiles);
        txtSecurity = findViewById(R.id.txtSecurity);
        crdConnectDevice = findViewById(R.id.crdConnectDevice);
        crdPrice = findViewById(R.id.crdPrice);
        crdStockTake = findViewById(R.id.crdStockTake);
        crdOrders = findViewById(R.id.crdOrders);
        crdDeliveries = findViewById(R.id.crdDeliveries);
        crdTransferDispatch = findViewById(R.id.crdTransferDispatch);
        crdTransferReceipt = findViewById(R.id.crdTransferReceipt);
        txtSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinDialog();
            }
        });
        crdPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MySession.get(getApplicationContext(), MySession.Share_BranchID).isEmpty()) {
                    Intent intent = new Intent(HomeActivity.this, PriceListActivity.class);
                    startActivity(intent);
                } else {
                    ToastMsg.mToastMsg(getApplicationContext(), "No Branch or Setup Yet, Please First Setup Branch", 1);
                }
            }
        });
        crdStockTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MySession.get(getApplicationContext(), MySession.Share_BranchID).isEmpty()) {
                    Intent intent = new Intent(HomeActivity.this, StockListActivity.class);
                    startActivity(intent);
                } else {
                    ToastMsg.mToastMsg(getApplicationContext(), "No Branch or Setup Yet, Please First Setup Branch", 1);
                }
            }
        });
        crdOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MySession.get(getApplicationContext(), MySession.Share_BranchID).isEmpty()) {
                    Intent intent = new Intent(HomeActivity.this, OrderActivity.class);
                    startActivity(intent);
                } else {
                    ToastMsg.mToastMsg(getApplicationContext(), "No Branch or Setup Yet, Please First Setup Branch", 1);
                }
            }
        });
        crdDeliveries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MySession.get(getApplicationContext(), MySession.Share_BranchID).isEmpty()) {
                    Intent intent = new Intent(HomeActivity.this, DeliveryActivity.class);
                    startActivity(intent);
                } else {
                    ToastMsg.mToastMsg(getApplicationContext(), "No Branch or Setup Yet, Please First Setup Branch", 1);
                }
            }
        });
        crdTransferDispatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MySession.get(getApplicationContext(), MySession.Share_BranchID).isEmpty()) {
                    Intent intent = new Intent(HomeActivity.this, TransferDispatchActivity.class);
                    startActivity(intent);
                } else {
                    ToastMsg.mToastMsg(getApplicationContext(), "No Branch or Setup Yet, Please First Setup Branch", 1);
                }
            }
        });
        crdTransferReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MySession.get(getApplicationContext(), MySession.Share_BranchID).isEmpty()) {
                    Intent intent = new Intent(HomeActivity.this, TransferReceiptActivity.class);
                    startActivity(intent);
                } else {
                    ToastMsg.mToastMsg(getApplicationContext(), "No Branch or Setup Yet, Please First Setup Branch", 1);
                }
            }
        });
        crdConnectDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!MySession.get(getApplicationContext(), MySession.Share_BranchID).isEmpty()) {

                    if (!MySession.getPin(getApplicationContext()).isEmpty() && MySession.get(getApplicationContext(), MySession.Share_ServerLock).equals("1")) {
                        new PinSet_Dialog(HomeActivity.this, new PinSet_Dialog.onSelectListener() {
                            @Override
                            public void onCancel() {

                            }

                            @Override
                            public void onDone() {

                                startActivity(new Intent(HomeActivity.this, ServerActivity.class));
                            }
                        }).show();
                    } else {
                        startActivity(new Intent(HomeActivity.this, ServerActivity.class));
                    }
//                } else {
//                    ToastMsg.mToastMsg(getApplicationContext(), "No Branch or Setup Yet, Please First Setup Branch", 1);
//                }
            }
        });

        txtSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProductActivity.class);
                startActivity(intent);

            }
        });
        txtRemoveAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Confirm_Dialog(HomeActivity.this, "Remove All", "Are you sure do you want to remove all data?", "Cancel", "Remove all", new Confirm_Dialog.onSelectListener() {
                    @Override
                    public void onYes() {

                        new AsyncTasks() {

                            @Override
                            public void onPreExecute() {

                            }

                            @Override
                            public void doInBackground() {

                                AppDatabase appDatabase = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
                                appDatabase.productDao().deleteAll();
                                appDatabase.orderDao().deleteAll();
                                appDatabase.stockCountDao().deleteAll();
                                appDatabase.deliveryDao().deleteAll();
                                appDatabase.transferDispatchDao().deleteAll();
                                appDatabase.transferReceiptDao().deleteAll();

                            }


                            @Override
                            public void onPostExecute() {
                                MySession.set(getApplicationContext(), "", MySession.Share_BranchID);
                                MySession.set(getApplicationContext(), "", MySession.Share_SetupDate);
                                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(0, 0);
                            }
                        }.execute();


                    }

                    @Override
                    public void onNo() {

                    }
                }).show();

            }
        });
        txtDownloadAllFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MySession.getPin(getApplicationContext()).isEmpty() && MySession.get(getApplicationContext(), MySession.Share_DownloadLock).equals("1")) {
                    new PinSet_Dialog(HomeActivity.this, new PinSet_Dialog.onSelectListener() {
                        @Override
                        public void onCancel() {

                        }

                        @Override
                        public void onDone() {
                            downloadAll();
                        }
                    }).show();
                } else {
                    downloadAll();
                }

            }
        });
        isStoragePermission();
    }

    public void downloadAll() {
        new Confirm_Dialog(HomeActivity.this, "Download All", "Are you sure do you want to download all files?", "Cancel", "Download All Files", new Confirm_Dialog.onSelectListener() {
            @Override
            public void onYes() {

                new AsyncTasks() {


                    ProgressDialog progressDialog;

                    @Override
                    public void onPreExecute() {
                        progressDialog = new ProgressDialog(HomeActivity.this);
                        progressDialog.showNow();
                    }

                    @Override
                    public void doInBackground() {

                        AppDatabase appDatabase = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();


                        List<DeliveryTable> deliveryArray = appDatabase.deliveryDao().getAll();

                        if (!deliveryArray.isEmpty()) {
                            String sData = "";
                            sData = "[PT700DLVS]";
                            sData = sData + "\nBranch=" + MySession.get(getApplicationContext(), MySession.Share_BranchID);
                            sData = sData + "\n[Data]";


                            List<DeliveryTable> dataArray = new ArrayList<>();
                            for (int i = 0; i < deliveryArray.size(); i++) {
                                DeliveryTable deliveryTable1 = deliveryArray.get(i);
                                boolean isAdd = true;
                                for (int j = 0; j < dataArray.size(); j++) {
                                    DeliveryTable deliveryTable2 = dataArray.get(j);
                                    if (deliveryTable1.getBarcode().equals(deliveryTable2.getBarcode())) {
                                        float f1 = URLString.getFloat(deliveryTable1.getDeliveryQty()) + URLString.getFloat(deliveryTable2.getDeliveryQty());
                                        deliveryTable2.setDeliveryQty(URLString.decimalFormat.format(f1));
                                        dataArray.set(j, deliveryTable2);
                                        isAdd = false;
                                        break;
                                    }
                                }
                                if (isAdd) {
                                    dataArray.add(deliveryTable1);
                                }
                            }
                            for (int i = 0; i < dataArray.size(); i++) {
                                DeliveryTable deliveryTable = dataArray.get(i);
                                sData = sData + "\n" + deliveryTable.getBarcode() + " ," + deliveryTable.getDeliveryQty() + "," + deliveryTable.getDeliveryDate() + "," + deliveryTable.getOrderNumber() + "," + deliveryTable.getDeliveryNumber() + "," + deliveryTable.getBranchID();
                            }
                            MyFunction.createFile(getApplicationContext(), "DELIVRS.IMP", sData);

                        }

                        List<OrderTable> orderArray = appDatabase.orderDao().getAll();
                        if (!orderArray.isEmpty()) {
                            String sData = "";
                            sData = "[PT700ORDER]";
                            sData = sData + "\nBranch=" + MySession.get(getApplicationContext(), MySession.Share_BranchID);
                            sData = sData + "\n[Data]";

                            List<OrderTable> dataArray = new ArrayList<>();
                            for (int i = 0; i < orderArray.size(); i++) {
                                OrderTable orderTable1 = orderArray.get(i);
                                boolean isAdd = true;
                                for (int j = 0; j < dataArray.size(); j++) {
                                    OrderTable orderTable2 = dataArray.get(j);
                                    if (orderTable1.getBarcode().equals(orderTable2.getBarcode())) {
                                        float f1 = URLString.getFloat(orderTable1.getOrderQty()) + URLString.getFloat(orderTable2.getOrderQty());
                                        orderTable2.setOrderQty(URLString.decimalFormat.format(f1));
                                        dataArray.set(j, orderTable2);
                                        isAdd = false;
                                        break;
                                    }
                                }
                                if (isAdd) {
                                    dataArray.add(orderTable1);
                                }
                            }
                            for (int i = 0; i < dataArray.size(); i++) {
                                OrderTable orderTable = dataArray.get(i);
                                sData = sData + "\n" + orderTable.getBarcode() + " ," + orderTable.getOrderQty() + "," + orderTable.getOrderDate() + "," + orderTable.getOrderNumber() + "," + orderTable.getBranchID();
                            }
                            if (!sData.isEmpty()) {
                                MyFunction.createFile(getApplicationContext(), "ORDERS.IMP", sData);
                            }
                        }


                        List<ProductTable> productArray = appDatabase.productDao().getProductAllPrice();
                        if (!productArray.isEmpty()) {
                            String sData = "";
                            sData = "[PT700PRICES]";
                            sData = sData + "\nBranch=" + MySession.get(getApplicationContext(), MySession.Share_BranchID);
                            sData = sData + "\n[Data]";
                            for (int i = 0; i < productArray.size(); i++) {
                                ProductTable productTable = productArray.get(i);
                                sData = sData + "\n" + productTable.getBarcode() + " ," + productTable.getNewPrice1() + "," + productTable.getNewPrice2();

                            }
                            if (!sData.isEmpty()) {
                                MyFunction.createFile(getApplicationContext(), "PRICEDAT.IMP", sData);
                            }
                        }


                        List<StockCountTable> stockArray = appDatabase.stockCountDao().getAll();
                        if (!stockArray.isEmpty()) {
                            String sData = "";
                            sData = "[PT700STOCK]";
                            sData = sData + "\nBranch=" + MySession.get(getApplicationContext(), MySession.Share_BranchID);
                            sData = sData + "\n[Data]";
                            List<StockCountTable> dataArray = new ArrayList<>();
                            for (int i = 0; i < stockArray.size(); i++) {
                                StockCountTable stockCountTable1 = stockArray.get(i);
                                boolean isAdd = true;
                                for (int j = 0; j < dataArray.size(); j++) {
                                    StockCountTable stockCountTable2 = dataArray.get(j);
                                    if (stockCountTable1.getBarcode().equals(stockCountTable2.getBarcode())) {
                                        float f1 = URLString.getFloat(stockCountTable1.getStockCount()) + URLString.getFloat(stockCountTable2.getStockCount());
                                        stockCountTable2.setStockCount(URLString.decimalFormat.format(f1));
                                        dataArray.set(j, stockCountTable2);
                                        isAdd = false;
                                        break;
                                    }
                                }
                                if (isAdd) {
                                    dataArray.add(stockCountTable1);
                                }
                            }

                            for (int i = 0; i < dataArray.size(); i++) {
                                StockCountTable stockCountTable = dataArray.get(i);
                                sData = sData + "\n" + stockCountTable.getBarcode() + " ," + stockCountTable.getStockCount();
                            }
                            if (!sData.isEmpty()) {
                                MyFunction.createFile(getApplicationContext(), "STOCKDAT.IMP", sData);
                            }
                        }

                        List<TransferDispatchTable> transferDispatchArray = appDatabase.transferDispatchDao().getAll();
                        if (!transferDispatchArray.isEmpty()) {
                            String sData = "";
                            sData = "[PT700TRNSDSP]";
                            sData = sData + "\nBranch=" + MySession.get(getApplicationContext(), MySession.Share_BranchID);
                            sData = sData + "\n[Data]";

                            List<TransferDispatchTable> dataArray = new ArrayList<>();
                            for (int i = 0; i < transferDispatchArray.size(); i++) {
                                TransferDispatchTable transferDispatchTable1 = transferDispatchArray.get(i);
                                boolean isAdd = true;
                                for (int j = 0; j < dataArray.size(); j++) {
                                    TransferDispatchTable transferDispatchTable2 = dataArray.get(j);
                                    if (transferDispatchTable1.getBarcode().equals(transferDispatchTable2.getBarcode())) {
                                        float f1 = URLString.getFloat(transferDispatchTable1.getTransferQty()) + URLString.getFloat(transferDispatchTable2.getTransferQty());
                                        transferDispatchTable2.setTransferQty(URLString.decimalFormat.format(f1));
                                        dataArray.set(j, transferDispatchTable2);
                                        isAdd = false;
                                        break;
                                    }
                                }
                                if (isAdd) {
                                    dataArray.add(transferDispatchTable1);
                                }
                            }
                            for (int i = 0; i < dataArray.size(); i++) {
                                TransferDispatchTable transferDispatchTable = dataArray.get(i);
                                sData = sData + "\n" + transferDispatchTable.getBarcode() + " ," + transferDispatchTable.getTransferQty() + ",       ," + transferDispatchTable.getDispatchDate() + "," + transferDispatchTable.getTransferNumber() + "," + transferDispatchTable.getBranchID();
                            }
                            if (!sData.isEmpty()) {
                                MyFunction.createFile(getApplicationContext(), "TRANSDSP.IMP", sData);
                            }
                        }


                        List<TransferReceiptTable> transferReceiptArray = appDatabase.transferReceiptDao().getAll();
                        if (!transferReceiptArray.isEmpty()) {
                            String sData = "";
                            sData = "[PT700TRANSRCPT]";
                            sData = sData + "\nBranch=" + MySession.get(getApplicationContext(), MySession.Share_BranchID);
                            sData = sData + "\n[Data]";

                            List<TransferReceiptTable> dataArray = new ArrayList<>();
                            for (int i = 0; i < transferReceiptArray.size(); i++) {
                                TransferReceiptTable transferReceiptTable1 = transferReceiptArray.get(i);
                                boolean isAdd = true;
                                for (int j = 0; j < dataArray.size(); j++) {
                                    TransferReceiptTable transferReceiptTable2 = dataArray.get(j);
                                    if (transferReceiptTable1.getBarcode().equals(transferReceiptTable2.getBarcode())) {
                                        float f1 = URLString.getFloat(transferReceiptTable1.getTransferQty()) + URLString.getFloat(transferReceiptTable2.getTransferQty());
                                        transferReceiptTable2.setTransferQty(URLString.decimalFormat.format(f1));
                                        dataArray.set(j, transferReceiptTable2);
                                        isAdd = false;
                                        break;
                                    }
                                }
                                if (isAdd) {
                                    dataArray.add(transferReceiptTable1);
                                }
                            }
                            for (int i = 0; i < dataArray.size(); i++) {
                                TransferReceiptTable transferReceiptTable = dataArray.get(i);
                                sData = sData + "\n" + transferReceiptTable.getBarcode() + " ," + transferReceiptTable.getTransferQty() + ",       ," + transferReceiptTable.getReceiptDate() + "," + transferReceiptTable.getTransferNumber() + "," + transferReceiptTable.getBranchID();
                            }
                            if (!sData.isEmpty()) {
                                MyFunction.createFile(getApplicationContext(), "TRANSRCT.IMP", sData);
                            }
                        }
                    }


                    @Override
                    public void onPostExecute() {

                        progressDialog.dismissNow();
                        new Confirm_Dialog(HomeActivity.this, "Successfully Downloaded All Files", "All Files Store at: " + URLString.strMainFolderName, "", "OK", new Confirm_Dialog.onSelectListener() {
                            @Override
                            public void onYes() {

                            }

                            @Override
                            public void onNo() {

                            }
                        }).show();

                    }
                }.execute();
            }

            @Override
            public void onNo() {

            }
        }).show();

    }

    public void pinDialog() {
        new PinSet_Dialog(HomeActivity.this, new PinSet_Dialog.onSelectListener() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onDone() {
                securityDialog();
            }
        }).show();
    }

    public void securityDialog() {
        new Security_Dialog(HomeActivity.this, new Security_Dialog.onSelectListener() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onReset() {
                pinDialog();
            }
        }).show();
    }

    public void onStart() {
        super.onStart();

        if (MySession.get(getApplicationContext(), MySession.Share_BranchID).isEmpty()) {
            txtBranch.setVisibility(View.GONE);
        } else {
            txtBranch.setVisibility(View.VISIBLE);
            txtBranch.setText("Branch ID\n" + MySession.get(getApplicationContext(), MySession.Share_BranchID));
        }
        if (MySession.get(getApplicationContext(), MySession.Share_SetupDate).isEmpty()) {
            txtSetupDate.setVisibility(View.GONE);
            txtRemoveAllData.setVisibility(View.GONE);
            txtDownloadAllFiles.setVisibility(View.GONE);
        } else {
            txtSetupDate.setVisibility(View.VISIBLE);
            txtRemoveAllData.setVisibility(View.VISIBLE);
            txtDownloadAllFiles.setVisibility(View.VISIBLE);
            txtSetupDate.setText("Setup At\n" + MySession.get(getApplicationContext(), MySession.Share_SetupDate));
        }
        txtDate.setText("Current Date\n" + ConvertDate.getCurrendDateMMM());
    }


    public boolean isStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                return true;
            } else {

                new Confirm_Dialog(HomeActivity.this, "Storage Permission", "App needs storage permission to store files.", "", "Let me give", new Confirm_Dialog.onSelectListener() {
                    @Override
                    public void onYes() {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }

                    @Override
                    public void onNo() {

                    }
                }).show();
            }
        }
        return false;
    }
}