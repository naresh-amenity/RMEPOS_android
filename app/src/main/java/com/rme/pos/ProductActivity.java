package com.rme.pos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.rme.roomdb.AppDatabase;
import com.rme.roomdb.DatabaseClient;
import com.rme.roomdb.ProductTable;
import com.rme.utils.AsyncTasks;
import com.rme.utils.ConvertDate;
import com.rme.utils.MyFileUtil;
import com.rme.utils.MyFunction;
import com.rme.utils.MySession;
import com.rme.utils.ProgressDialog;
import com.rme.utils.ToastMsg;
import com.rme.utils.URLString;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    ListView listView;
    List<ProductTable> productArray = new ArrayList<>();
    ProductList_Adapter productListAdapter;
    TextView txtDataNotFound;
    CardView crdSetup, crdReloadSameFile;
    ProgressDialog progressDialog;
    String sAutoSetupFile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity);
        TextView txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("Product");
        ImageView imgBack = findViewById(R.id.imgBack);
        if (imgBack != null) {
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        if (getIntent() != null) {
            if (getIntent().hasExtra("FILE")) {
                sAutoSetupFile = getIntent().getStringExtra("FILE");
            }
        }
        listView = findViewById(R.id.listView);
        txtDataNotFound = findViewById(R.id.txtDataNotFound);
        crdSetup = findViewById(R.id.crdSetup);
        crdReloadSameFile = findViewById(R.id.crdReloadSameFile);
        txtDataNotFound.setVisibility(View.GONE);
        progressDialog = new ProgressDialog(this);
        crdSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productArray.size() > 0) {
                    new Confirm_Dialog(ProductActivity.this, "New Setup/Remove All Data", "Are you sure do you want to clear everything and setup again?", "Cancel", "New Setup", new Confirm_Dialog.onSelectListener() {
                        @Override
                        public void onYes() {
                            onPickFile();
                        }

                        @Override
                        public void onNo() {

                        }
                    }).show();
                } else {
                    onPickFile();
                }
            }
        });
        crdReloadSameFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Confirm_Dialog(ProductActivity.this, "Reload Same File", "Are you sure do you want to clear everything and reload last picked file?", "Cancel", "Reload", new Confirm_Dialog.onSelectListener() {
                    @Override
                    public void onYes() {
                        File file = new File(MySession.get(getApplicationContext(), MySession.Share_LastSetupFilePath));
                        onFileCheck(file);
                    }

                    @Override
                    public void onNo() {

                    }
                }).show();
            }
        });
        if (!MySession.get(getApplicationContext(), MySession.Share_BranchID).isEmpty()) {
            File file = new File(MySession.get(getApplicationContext(), MySession.Share_LastSetupFilePath));
            if (file.exists()) {
                crdReloadSameFile.setVisibility(View.VISIBLE);
            } else {
                crdReloadSameFile.setVisibility(View.GONE);
            }
        } else {
            crdReloadSameFile.setVisibility(View.GONE);
        }
        Log.e("sAutoSetupFile","="+sAutoSetupFile);
        if (!sAutoSetupFile.isEmpty()&&new File(sAutoSetupFile).exists()) {
            if (MySession.get(ProductActivity.this, MySession.Share_BranchID).isEmpty()){
                MySession.set(getApplicationContext(),"1",MySession.Share_BranchID);
            }
            MySession.set(getApplicationContext(), ConvertDate.getCurrendDateMMM(),MySession.Share_SetupDate);

            onFileCheck(new File(sAutoSetupFile));
        } else {
            getProduct();
        }
    }

    public void onPickFile() {
        new Branch_Dialog(ProductActivity.this, new Branch_Dialog.onSelectListener() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onDone() {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                fileIntentLauncher.launch(chooseFile);
            }
        }).show();
    }

    ActivityResultLauncher<Intent> fileIntentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        try {
                            Intent data = result.getData();
                            if (data != null) {
                                Uri fileURI = data.getData();
                                File f = MyFileUtil.from(getApplicationContext(), fileURI);
                                onFileCheck(f);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    public void onFileCheck(File f) {
        if (f.exists()) {
            int size = (int) f.length();
            if (size >= 100) {
                String sFileName = f.getName();
                String sFileExtension = sFileName.substring(sFileName.lastIndexOf(".") + 1, sFileName.length());
                Log.e("FileName", "=" + sFileName);
                Log.e("sFileExtension", "=" + sFileExtension);
                if (sFileExtension.equalsIgnoreCase("exp")) {
                    MySession.set(getApplicationContext(), f.getAbsolutePath(), MySession.Share_LastSetupFilePath);
                    convertText2Stock(f);
                } else {
                    ToastMsg.mToastMsg(getApplicationContext(), "Invalid file type.", 1);
                }
            } else {
                ToastMsg.mToastMsg(getApplicationContext(), "Unable to read file.", 1);
            }
        }
    }

    public void convertText2Stock(File filePath) {


        new AsyncTasks() {

            @Override
            public void onPreExecute() {
                if (!progressDialog.isShowing()) {
                    progressDialog.showNow();
                }
            }

            @Override
            public void doInBackground() {
                String sText = MyFunction.file2Text(getApplicationContext(), filePath);
                ArrayList<String> textArray = MyFunction.string2Array(sText, "\n");
                if (!textArray.isEmpty()) {
                    List<ProductTable> productArray = new ArrayList<>();
                    AppDatabase appDatabase = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
                    appDatabase.productDao().deleteAll();
                    for (int i = 0; i < textArray.size(); i++) {
                        String s = textArray.get(i);
                        if (s.contains(",")) {
                            ArrayList<String> dataArray = MyFunction.splitCommaArray(s);
                            if (dataArray.size() == 5) {
                                String sBarcode = dataArray.get(0);
                                String sDescription = dataArray.get(1);
                                String sStock = dataArray.get(2);
                                String sPrice1 = dataArray.get(3);
                                String sPrice2 = dataArray.get(4);
                                ProductTable productTable = new ProductTable(sBarcode, sDescription, sStock, sPrice1, sPrice2, sPrice1, sPrice2, "" + new Date().getTime(), "0");
                                productArray.add(productTable);

                            }
                        }
//                        if (i > 1000) {
//                            break;
//                        }
                    }
                    appDatabase.productDao().insert(productArray);

                    List<ProductTable> productListMulti = appDatabase.productDao().findDuplicates();
                    for (int i = 0; i < productListMulti.size(); i++) {
                        ProductTable productTableMulti = productListMulti.get(i);
                        float fQty = 0;
                        List<ProductTable> productList = appDatabase.productDao().getProductByBarcode(productTableMulti.getBarcode());
                        for (int j = 0; j < productList.size(); j++) {
                            fQty = fQty + URLString.getFloat(productList.get(j).getStock());
                        }
                        productTableMulti.setStock("" + fQty);
                        productListMulti.set(i, productTableMulti);
                        appDatabase.productDao().deleteByBarcode(productTableMulti.getBarcode());
                        appDatabase.productDao().insert(productListMulti);
                    }
                }
            }


            @Override
            public void onPostExecute() {

                getProduct();
            }
        }.execute();


    }

    public void getProduct() {

        new AsyncTasks() {

            @Override
            public void onPreExecute() {

                if (!progressDialog.isShowing()) {
                    progressDialog.showNow();
                }
            }

            @Override
            public void doInBackground() {
                AppDatabase appDatabase = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
                productArray = appDatabase.productDao().getProductAll();

            }


            @Override
            public void onPostExecute() {

                if (progressDialog.isShowing()) {
                    progressDialog.dismissNow();
                }
                productListAdapter = new ProductList_Adapter();
                listView.setAdapter(productListAdapter);
                if (productArray.isEmpty()) {
                    txtDataNotFound.setVisibility(View.VISIBLE);
                } else {
                    txtDataNotFound.setVisibility(View.GONE);
                }
            }
        }.execute();

    }

    public class ProductList_Adapter extends BaseAdapter {

        private LayoutInflater inflater = null;

        public ProductList_Adapter() {
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        public int getCount() {
            return productArray.size();
        }

        // this method through get item position or this method return item position.
        public Object getItem(int position) {
            return position;
        }

        //this method return item ID.
        public long getItemId(int position) {
            return position;
        }

        // this method through getting custom view
        // this method through set item data.
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = convertView;
            MyHolderView holder = null;
            if (row == null) {
                row = inflater.inflate(R.layout.product_list_adapter, null);
                holder = new MyHolderView(row);
                row.setTag(holder);
            } else {
                holder = (MyHolderView) row.getTag();
            }

            ProductTable productTable = productArray.get(position);
//            Log.e("map", "=" + map.toString());
            holder.txtBarcode.setText(URLString.isNull(productTable.getBarcode()));
            holder.txtDesc.setText(URLString.isNull(productTable.getDescription()));
            holder.txtStock.setText(URLString.isNull(productTable.getStock()));
            holder.txtPrice1.setText(URLString.isNull(productTable.getPrice1()));
            holder.txtPrice2.setText(URLString.isNull(productTable.getPrice2()));

            return row;
        }


        class MyHolderView {
            TextView txtBarcode, txtDesc, txtStock, txtPrice1, txtPrice2;

            MyHolderView(View v) {
                txtBarcode = v.findViewById(R.id.txtBarcode);
                txtDesc = v.findViewById(R.id.txtDesc);
                txtStock = v.findViewById(R.id.txtStock);
                txtPrice1 = v.findViewById(R.id.txtPrice1);
                txtPrice2 = v.findViewById(R.id.txtPrice2);
            }

        }
    }
}