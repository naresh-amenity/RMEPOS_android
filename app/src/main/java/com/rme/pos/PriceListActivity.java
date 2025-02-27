package com.rme.pos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.rme.other.ListPopupItem;
import com.rme.other.ListPopupWindowAdapter;
import com.rme.roomdb.AppDatabase;
import com.rme.roomdb.DatabaseClient;
import com.rme.roomdb.ProductTable;
import com.rme.utils.AsyncTasks;
import com.rme.utils.MyFunction;
import com.rme.utils.MySession;
import com.rme.utils.ProgressDialog;
import com.rme.utils.ToastMsg;
import com.rme.utils.URLString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PriceListActivity extends AppCompatActivity {
    ListView listView;
    List<ProductTable> productArray = new ArrayList<>();
    TextView txtDataNotFound;
    CardView crdNewPrice;
    ProductList_Adapter productListAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.price_list_activity);
        TextView txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("Price");
        ImageView imgBack = findViewById(R.id.imgBack);
        ImageView imgDelete = findViewById(R.id.imgDelete);
        ImageView imgDownload = findViewById(R.id.imgDownload);
        imgDelete.setVisibility(View.VISIBLE);
        imgDownload.setVisibility(View.VISIBLE);
        if (imgBack != null) {
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Confirm_Dialog(PriceListActivity.this, "Remove All Price Data", "Are you sure do you want to remove all price data?", "Cancel", "Remove all", new Confirm_Dialog.onSelectListener() {
                    @Override
                    public void onYes() {

                        new AsyncTasks() {

                            @Override
                            public void onPreExecute() {


                            }

                            @Override
                            public void doInBackground() {
                                for (int i = 0; i < productArray.size(); i++) {
                                    ProductTable productTable = productArray.get(i);
                                    productTable.setNewPrice1(productTable.getPrice1());
                                    productTable.setNewPrice2(productTable.getPrice2());
                                    productTable.setIsPriceUpdate("0");
                                    productArray.set(i, productTable);
                                }
                                AppDatabase appDatabase = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
                                appDatabase.productDao().update(productArray);


                            }


                            @Override
                            public void onPostExecute() {

                                getPriceData();
                            }
                        }.execute();
                    }

                    @Override
                    public void onNo() {

                    }
                }).show();
            }
        });
        imgDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Confirm_Dialog(PriceListActivity.this, "Download File", "Are you sure do you want to download file?", "Cancel", "Yes, Download", new Confirm_Dialog.onSelectListener() {
                    @Override
                    public void onYes() {

                        new AsyncTasks() {

                            String sData = "";

                            ProgressDialog progressDialog;
                            @Override
                            public void onPreExecute() {
                                progressDialog = new ProgressDialog(PriceListActivity.this);
                                progressDialog.showNow();
                            }

                            @Override
                            public void doInBackground() {
                                if (!productArray.isEmpty()) {
                                    sData = "[PT700PRICES]";
                                    sData = sData + "\nBranch=" + MySession.get(getApplicationContext(), MySession.Share_BranchID);
                                    sData = sData + "\n[Data]";
                                }
                                for (int i = 0; i < productArray.size(); i++) {
                                    ProductTable productTable = productArray.get(i);
                                    sData = sData + "\n" + productTable.getBarcode() + " ," + productTable.getNewPrice1() + "," + productTable.getNewPrice2();

                                }
                                if (!sData.isEmpty()) {
                                    MyFunction.createFile(getApplicationContext(), "PRICEDAT.IMP", sData);
                                }


                            }


                            @Override
                            public void onPostExecute() {

                                progressDialog.dismissNow();
                                if (!sData.isEmpty()) {
                                    new Confirm_Dialog(PriceListActivity.this, "Successfully Downloaded", "File Store at: "+URLString.strMainFolderName+"/PRICEDAT.IMP", "", "OK", new Confirm_Dialog.onSelectListener() {
                                        @Override
                                        public void onYes() {

                                        }

                                        @Override
                                        public void onNo() {

                                        }
                                    }).show();
                                }else{
                                    ToastMsg.mToastMsg(getApplicationContext(),"No data found to download.",1);
                                }
                            }
                        }.execute();
                    }

                    @Override
                    public void onNo() {

                    }
                }).show();
            }
        });
        progressDialog = new ProgressDialog(this);
        listView = findViewById(R.id.listView);
        txtDataNotFound = findViewById(R.id.txtDataNotFound);
        crdNewPrice = findViewById(R.id.crdNewPrice);
        txtDataNotFound.setVisibility(View.GONE);
        crdNewPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceDialog(new ProductTable());
            }
        });
        getPriceData();
        priceDialog(new ProductTable());
    }


    public void priceDialog(ProductTable productTable) {

        new New_Price_Add_Dialog(PriceListActivity.this, productTable, new New_Price_Add_Dialog.onSelectListener() {
            @Override
            public void onCancel() {
                getPriceData();
            }

            @Override
            public void onDone() {
                getPriceData();
            }

            @Override
            public void onDoneAndNext() {
                priceDialog(new ProductTable());
            }
        }).show();
    }

    public void getPriceData() {

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

                productArray = appDatabase.productDao().getProductAllPrice();

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
                row = inflater.inflate(R.layout.product_price_list_adapter, null);
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
            holder.txtNewPrice1.setText(URLString.isNull(productTable.getNewPrice1()));
            holder.txtNewPrice2.setText(URLString.isNull(productTable.getNewPrice2()));
            holder.imgOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<ListPopupItem> listPopupItems = new ArrayList<>();
                    listPopupItems.add(new ListPopupItem("Edit", R.drawable.ic_edit_green));
                    listPopupItems.add(new ListPopupItem("Delete", R.drawable.ic_delete_red));
                    ListPopupWindow popup = new ListPopupWindow(PriceListActivity.this);
                    ListAdapter adapter = new ListPopupWindowAdapter(getApplicationContext(), listPopupItems);
                    popup.setAnchorView(v);
                    int dp180 = getResources().getDimensionPixelSize(R.dimen.dp180);
                    int dp10 = getResources().getDimensionPixelSize(R.dimen.dp10);

                    int finalSize = dp180 - dp10;
                    popup.setWidth(dp180);
                    popup.setHorizontalOffset(-finalSize);
                    popup.setAdapter(adapter);
                    popup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int positionMenu, long id) {
                            popup.dismiss();
                            String sMenuTitle = listPopupItems.get(positionMenu).getTitle();
                            if (sMenuTitle.equals("Edit")) {
                                priceDialog(productTable);
                            } else if (sMenuTitle.equals("Delete")) {
                                new Confirm_Dialog(PriceListActivity.this, "Delete Item", "Are you sure you want to delete this item?", "No", "Yes, Delete", new Confirm_Dialog.onSelectListener() {
                                    @Override
                                    public void onYes() {

                                        new AsyncTasks() {

                                            @Override
                                            public void onPreExecute() {


                                            }

                                            @Override
                                            public void doInBackground() {
                                                productTable.setNewPrice1(productTable.getPrice1());
                                                productTable.setNewPrice2(productTable.getPrice2());
                                                productTable.setIsPriceUpdate("0");
                                                AppDatabase appDatabase = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
                                                appDatabase.productDao().update(productTable);
                                                productArray.remove(position);


                                            }


                                            @Override
                                            public void onPostExecute() {

                                                notifyDataSetChanged();
                                                if (productArray.isEmpty()) {
                                                    txtDataNotFound.setVisibility(View.VISIBLE);
                                                }
                                            }
                                        }.execute();

                                    }

                                    @Override
                                    public void onNo() {

                                    }
                                }).show();
                            }

                        }
                    });
                    popup.show();
                }
            });
            return row;
        }


        class MyHolderView {
            TextView txtBarcode, txtDesc, txtStock, txtPrice1, txtNewPrice1, txtPrice2, txtNewPrice2;
            ImageView imgOption;

            MyHolderView(View v) {
                txtBarcode = v.findViewById(R.id.txtBarcode);
                txtDesc = v.findViewById(R.id.txtDesc);
                txtStock = v.findViewById(R.id.txtStock);
                txtPrice1 = v.findViewById(R.id.txtPrice1);
                txtNewPrice1 = v.findViewById(R.id.txtNewPrice1);
                txtPrice2 = v.findViewById(R.id.txtPrice2);
                txtNewPrice2 = v.findViewById(R.id.txtNewPrice2);
                imgOption = v.findViewById(R.id.imgOption);
            }

        }
    }
}