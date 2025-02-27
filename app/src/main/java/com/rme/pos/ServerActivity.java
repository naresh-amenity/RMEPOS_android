package com.rme.pos;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.rme.roomdb.AppDatabase;
import com.rme.roomdb.DatabaseClient;
import com.rme.roomdb.DeliveryTable;
import com.rme.roomdb.OrderTable;
import com.rme.roomdb.ProductTable;
import com.rme.roomdb.StockCountTable;
import com.rme.roomdb.TransferDispatchTable;
import com.rme.roomdb.TransferReceiptTable;
import com.rme.utils.AsyncTasks;
import com.rme.utils.MyFunction;
import com.rme.utils.MySession;
import com.rme.utils.ProgressDialog;
import com.rme.utils.URLString;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class ServerActivity extends AppCompatActivity {
    public static final int DEFAULT_PORT = 8080;

    public AndroidWebServer androidWebServer;
    boolean isStarted = false;

    LinearLayout layIP, layMain;
    public TextView txtIP, txtIntro;
    EditText edPort;
    Button btnStart, btnStop;


    List<String> deliverySArray = new ArrayList<>();
    List<String> orderSArray = new ArrayList<>();
    List<String> productSArray = new ArrayList<>();
    List<String> stockSArray = new ArrayList<>();
    List<String> transferDispatchSArray = new ArrayList<>();
    List<String> transferReceiptSArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_activity);
        TextView txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("Connect Device");
        ImageView imgBack = findViewById(R.id.imgBack);
        if (imgBack != null) {
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        edPort = findViewById(R.id.edPort);
        txtIP = findViewById(R.id.txtIP);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        Button btnOpenWeb = findViewById(R.id.btnOpenWeb);
        layMain = findViewById(R.id.layMain);
        txtIntro = findViewById(R.id.txtIntro);
        layIP = findViewById(R.id.layIP);
        btnStop.setVisibility(View.GONE);
        txtIntro.setVisibility(View.GONE);
        layIP.setVisibility(View.GONE);
        btnOpenWeb.setVisibility(View.GONE);
        btnStart.setVisibility(View.VISIBLE);
        edPort.setEnabled(true);
        edPort.setText(""+DEFAULT_PORT);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectedInWifi()) {
                    if (!isStarted && startAndroidWebServer()) {
                        isStarted = true;
                        btnStart.setVisibility(View.GONE);
                        btnStop.setVisibility(View.VISIBLE);
                        txtIntro.setVisibility(View.VISIBLE);
                        layIP.setVisibility(View.VISIBLE);
                        edPort.setEnabled(false);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please connect with WIFI.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStarted && stopAndroidWebServer()) {
                    txtIntro.setVisibility(View.GONE);
                    layIP.setVisibility(View.GONE);
                    isStarted = false;
                    btnStop.setVisibility(View.GONE);
                    btnStart.setVisibility(View.VISIBLE);
                    edPort.setEnabled(true);
                }
            }
        });
//        layTop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String s = txtIP.getText().toString() + "" + edPort.getText().toString();
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
//                startActivity(browserIntent);
//
//            }
//        });
        btnOpenWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = txtIP.getText().toString() + "" + edPort.getText().toString();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
                startActivity(browserIntent);

            }
        });

        setIpAccess();
        onPrepareData();
    }
    @Override
    public void onResume() {
        super.onResume();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onPause() {
        super.onPause();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (isStarted){
            btnStop.performClick();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
       if (isStarted){
           stopAndroidWebServer();
       }

    }
    public boolean startAndroidWebServer() {
        if (!isStarted) {
            int port = getPortFromEditText();
            try {
                if (port == 0) {
                    throw new Exception();
                }
                androidWebServer = new AndroidWebServer(getApplicationContext(), port, new AndroidWebServer.onSelectListener() {
                    @Override
                    public void onFileStored(String sFilePath) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
//                                new Confirm_Dialog(ServerActivity.this, "Successfully Downloaded", "File Store at: "+URLString.strMainFolderName+"/PLU.EXP", "", "OK", new Confirm_Dialog.onSelectListener() {
//                                    @Override
//                                    public void onYes() {
//
//
//                                    }
//
//                                    @Override
//                                    public void onNo() {
//
//                                    }
//                                }).show();

                                Log.e("sFilePath","="+sFilePath);
                                String sTitle = "PLU File Received";
                                String sDetails = "New PLU File Received, Do you want to setup now?";
                                new Confirm_Dialog(ServerActivity.this, sTitle, sDetails, "No", "Yes, Setup Now", new Confirm_Dialog.onSelectListener() {
                                    @Override
                                    public void onYes() {

                                        Intent intent = new Intent(ServerActivity.this, ProductActivity.class);
                                        intent.putExtra("FILE",sFilePath);
                                        startActivity(intent);

                                    }

                                    @Override
                                    public void onNo() {

                                    }
                                }).show();
                            }
                        });
                    }
                });
//                androidWebServer.setMsgData(smsMapArray);
                androidWebServer.setData(deliverySArray, orderSArray, productSArray, stockSArray, transferDispatchSArray, transferReceiptSArray);
                androidWebServer.start(20 * 60000);

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean stopAndroidWebServer() {
        if (isStarted && androidWebServer != null) {
            androidWebServer.stop();
            return true;
        }
        return false;
    }

    public void setIpAccess() {
        txtIP.setText(getIpAccess());
    }


    public String getIpAccess() {
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        final String formatedIpAddress = String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        return "http://" + formatedIpAddress + ":";
    }

    public int getPortFromEditText() {
        String valueEditText = edPort.getText().toString();
        return (valueEditText.length() > 0) ? Integer.parseInt(valueEditText) : DEFAULT_PORT;
    }

    public boolean isConnectedInWifi() {
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        NetworkInfo networkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()
                && wifiManager.isWifiEnabled() && networkInfo.getTypeName().equals("WIFI")) {
            return true;
        }
        return false;
    }

    public void onPrepareData() {

        new AsyncTasks() {


            ProgressDialog progressDialog;

            List<DeliveryTable> deliveryArray = new ArrayList<>();
            List<OrderTable> orderArray = new ArrayList<>();
            List<ProductTable> productArray = new ArrayList<>();
            List<StockCountTable> stockArray = new ArrayList<>();
            List<TransferDispatchTable> transferDispatchArray = new ArrayList<>();
            List<TransferReceiptTable> transferReceiptArray = new ArrayList<>();
            @Override
            public void onPreExecute() {
                progressDialog = new ProgressDialog(ServerActivity.this);
                progressDialog.showNow();
            }

            @Override
            public void doInBackground() {
                AppDatabase appDatabase = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
                deliveryArray = appDatabase.deliveryDao().getAll();

                if (!deliveryArray.isEmpty()) {
                    deliverySArray = new ArrayList<>();
                    deliverySArray.add("[PT700DLVS]");
                    deliverySArray.add("Branch=" + MySession.get(getApplicationContext(), MySession.Share_BranchID));
                    deliverySArray.add("[Data]");

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
                       String sData = deliveryTable.getBarcode() + " ," + deliveryTable.getDeliveryQty() + "," + deliveryTable.getDeliveryDate() + "," + deliveryTable.getOrderNumber() + "," + deliveryTable.getDeliveryNumber() + "," + deliveryTable.getBranchID();
                        deliverySArray.add(sData);
                    }

                }

                orderArray = appDatabase.orderDao().getAll();
                if (!orderArray.isEmpty()) {
                    orderSArray = new ArrayList<>();
                    orderSArray.add("[PT700ORDER]");
                    orderSArray.add("Branch=" + MySession.get(getApplicationContext(), MySession.Share_BranchID));
                    orderSArray.add("[Data]");

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
                        String sData = orderTable.getBarcode() + " ," + orderTable.getOrderQty() + "," + orderTable.getOrderDate() + "," + orderTable.getOrderNumber() + "," + orderTable.getBranchID();
                        orderSArray.add(sData);
                    }
                }


                productArray = appDatabase.productDao().getProductAllPrice();
                if (!productArray.isEmpty()) {
                    productSArray = new ArrayList<>();
                    productSArray.add("[PT700PRICES]");
                    productSArray.add("Branch=" + MySession.get(getApplicationContext(), MySession.Share_BranchID));
                    productSArray.add("[Data]");
                    for (int i = 0; i < productArray.size(); i++) {
                        ProductTable productTable = productArray.get(i);
                        String sData = productTable.getBarcode() + " ," + productTable.getNewPrice1() + "," + productTable.getNewPrice2();
                        productSArray.add(sData);
                    }
                }


                stockArray = appDatabase.stockCountDao().getAll();
                if (!stockArray.isEmpty()) {
                    stockSArray = new ArrayList<>();
                    stockSArray.add("[PT700STOCK]");
                    stockSArray.add("Branch=" + MySession.get(getApplicationContext(), MySession.Share_BranchID));
                    stockSArray.add("[Data]");
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
                       String sData =  stockCountTable.getBarcode() + " ," + stockCountTable.getStockCount();
                        stockSArray.add(sData);
                    }
                }

                transferDispatchArray = appDatabase.transferDispatchDao().getAll();
                if (!transferDispatchArray.isEmpty()) {
                    transferDispatchSArray = new ArrayList<>();
                    transferDispatchSArray.add("[PT700TRNSDSP]");
                    transferDispatchSArray.add("Branch=" + MySession.get(getApplicationContext(), MySession.Share_BranchID));
                    transferDispatchSArray.add("[Data]");

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
                        String sData =  transferDispatchTable.getBarcode() + " ," + transferDispatchTable.getTransferQty() + ",       ," + transferDispatchTable.getDispatchDate() + "," + transferDispatchTable.getTransferNumber() + "," + transferDispatchTable.getBranchID();
                        transferDispatchSArray.add(sData);
                    }
                }


                transferReceiptArray = appDatabase.transferReceiptDao().getAll();
                if (!transferReceiptArray.isEmpty()) {
                    transferReceiptSArray = new ArrayList<>();
                    transferReceiptSArray.add("[PT700TRANSRCPT]");
                    transferReceiptSArray.add("Branch=" + MySession.get(getApplicationContext(), MySession.Share_BranchID));
                    transferReceiptSArray.add("[Data]");

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
                       String sData =  transferReceiptTable.getBarcode() + " ," + transferReceiptTable.getTransferQty() + ",       ," + transferReceiptTable.getReceiptDate() + "," + transferReceiptTable.getTransferNumber() + "," + transferReceiptTable.getBranchID();
                        transferReceiptSArray.add(sData);
                    }
                }
            }


            @Override
            public void onPostExecute() {

                progressDialog.dismissNow();


            }
        }.execute();
    }

}
