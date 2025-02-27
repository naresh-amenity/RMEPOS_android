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
import com.rme.roomdb.StockCountTable;
import com.rme.utils.AsyncTasks;
import com.rme.utils.MyFunction;
import com.rme.utils.MySession;
import com.rme.utils.ProgressDialog;
import com.rme.utils.ToastMsg;
import com.rme.utils.URLString;

import java.util.ArrayList;
import java.util.List;

public class StockListActivity extends AppCompatActivity {
    ListView listView;
    List<StockCountTable> stockArray = new ArrayList<>();
    StockList_Adapter stockListAdapter;
    TextView txtDataNotFound;
    CardView crdNewStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_list_activity);
        TextView txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("Stock Take");
        ImageView imgBack = findViewById(R.id.imgBack);
        ImageView imgDelete = findViewById(R.id.imgDelete);
        ImageView imgDownload = findViewById(R.id.imgDownload);
        ImageView imgSettings = findViewById(R.id.imgSettings);
        imgDelete.setVisibility(View.VISIBLE);
        imgDownload.setVisibility(View.VISIBLE);
        imgSettings.setVisibility(View.VISIBLE);
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

                new Confirm_Dialog(StockListActivity.this, "Remove All Stock Count", "Are you sure do you want to remove all stock count?", "Cancel", "Remove all", new Confirm_Dialog.onSelectListener() {
                    @Override
                    public void onYes() {

                        new AsyncTasks() {

                            @Override
                            public void onPreExecute() {


                            }

                            @Override
                            public void doInBackground() {

                                AppDatabase appDatabase = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
                                appDatabase.stockCountDao().deleteAll();


                            }


                            @Override
                            public void onPostExecute() {

                                getStockData();
                            }
                        }.execute();

                        getStockData();
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

                new Confirm_Dialog(StockListActivity.this, "Download File", "Are you sure do you want to download file?", "Cancel", "Yes, Download", new Confirm_Dialog.onSelectListener() {
                    @Override
                    public void onYes() {

                        new AsyncTasks() {

                            String sData = "";

                            ProgressDialog progressDialog;

                            @Override
                            public void onPreExecute() {
                                progressDialog = new ProgressDialog(StockListActivity.this);
                                progressDialog.showNow();
                            }

                            @Override
                            public void doInBackground() {
                                if (!stockArray.isEmpty()) {
                                    sData = "[PT700STOCK]";
                                    sData = sData + "\nBranch=" + MySession.get(getApplicationContext(), MySession.Share_BranchID);
                                    sData = sData + "\n[Data]";
                                }
                                List<StockCountTable> dataArray = new ArrayList<>();
                                for (int i = 0; i < stockArray.size(); i++) {
                                    StockCountTable stockCountTable1 = stockArray.get(i);
                                    boolean isAdd = true;
                                    for (int j = 0; j < dataArray.size(); j++) {
                                        StockCountTable stockCountTable2 = dataArray.get(j);
                                        if (stockCountTable1.getBarcode().equals(stockCountTable2.getBarcode())) {
                                            float f1 = URLString.getFloat(stockCountTable1.getStockCount()) + URLString.getFloat(stockCountTable2.getStockCount());
                                            stockCountTable2.setStockCount(URLString.decimalFormat.format(f1));
                                            dataArray.set(j,stockCountTable2);
                                            isAdd = false;
                                            break;
                                        }
                                    }
                                    if (isAdd){
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


                            @Override
                            public void onPostExecute() {

                                progressDialog.dismissNow();
                                if (!sData.isEmpty()) {
                                    new Confirm_Dialog(StockListActivity.this, "Successfully Downloaded", "File Store at: " + URLString.strMainFolderName + "/STOCKDAT.IMP", "", "OK", new Confirm_Dialog.onSelectListener() {
                                        @Override
                                        public void onYes() {

                                        }

                                        @Override
                                        public void onNo() {

                                        }
                                    }).show();
                                } else {
                                    ToastMsg.mToastMsg(getApplicationContext(), "No data found to download.", 1);
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
        imgSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Stock_Default_Dialog(StockListActivity.this, new Stock_Default_Dialog.onSelectListener() {
                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onDone() {

                    }
                }).show();
            }
        });
        listView = findViewById(R.id.listView);
        txtDataNotFound = findViewById(R.id.txtDataNotFound);
        crdNewStock = findViewById(R.id.crdNewStock);
        txtDataNotFound.setVisibility(View.GONE);
        crdNewStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stockDialog(new StockCountTable());

            }
        });
        getStockData();
        stockDialog(new StockCountTable());
    }

    public void stockDialog(StockCountTable stockCountTable) {
        new StockCount_Add_Dialog(StockListActivity.this, stockCountTable, new StockCount_Add_Dialog.onSelectListener() {
            @Override
            public void onCancel() {
                getStockData();
            }

            @Override
            public void onDone() {
                getStockData();
            }

            @Override
            public void onDoneAgain() {
                stockDialog(new StockCountTable());
            }
        }).show();

    }

    public void getStockData() {

        new AsyncTasks() {

            @Override
            public void onPreExecute() {

            }

            @Override
            public void doInBackground() {

                AppDatabase appDatabase = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
                stockArray = appDatabase.stockCountDao().getAll();

            }


            @Override
            public void onPostExecute() {

                stockListAdapter = new StockList_Adapter();
                listView.setAdapter(stockListAdapter);
                if (stockArray.size() > 0) {
                    txtDataNotFound.setVisibility(View.GONE);
                } else {
                    txtDataNotFound.setVisibility(View.VISIBLE);
                }
            }
        }.execute();

    }


    public class StockList_Adapter extends BaseAdapter {

        private LayoutInflater inflater = null;

        public StockList_Adapter() {
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        public int getCount() {
            return stockArray.size();
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
                row = inflater.inflate(R.layout.stock_list_adapter, null);
                holder = new MyHolderView(row);
                row.setTag(holder);
            } else {
                holder = (MyHolderView) row.getTag();
            }

            StockCountTable stockCountTable = stockArray.get(position);
            holder.txtBarcode.setText(URLString.isNull(stockCountTable.getBarcode()));
            holder.txtDesc.setText(URLString.isNull(stockCountTable.getDescription()));
            holder.txtStockCount.setText(URLString.isNull(stockCountTable.getStockCount()));


            holder.imgOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<ListPopupItem> listPopupItems = new ArrayList<>();
                    listPopupItems.add(new ListPopupItem("Edit", R.drawable.ic_edit_green));
                    listPopupItems.add(new ListPopupItem("Delete", R.drawable.ic_delete_red));
                    ListPopupWindow popup = new ListPopupWindow(StockListActivity.this);
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
                                stockDialog(stockCountTable);
                            } else if (sMenuTitle.equals("Delete")) {
                                new Confirm_Dialog(StockListActivity.this, "Delete Item", "Are you sure you want to delete this item?", "No", "Yes, Delete", new Confirm_Dialog.onSelectListener() {
                                    @Override
                                    public void onYes() {

                                        new AsyncTasks() {

                                            @Override
                                            public void onPreExecute() {

                                            }

                                            @Override
                                            public void doInBackground() {

                                                AppDatabase appDatabase = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
                                                appDatabase.stockCountDao().deleteByID(URLString.isNull(stockCountTable.getID()));
                                                stockArray.remove(position);


                                            }


                                            @Override
                                            public void onPostExecute() {

                                                notifyDataSetChanged();
                                                if (stockArray.isEmpty()) {
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
            TextView txtBarcode, txtDesc, txtStockCount;
            ImageView imgOption;

            MyHolderView(View v) {
                txtBarcode = v.findViewById(R.id.txtBarcode);
                txtDesc = v.findViewById(R.id.txtDesc);
                txtStockCount = v.findViewById(R.id.txtStockCount);
                imgOption = v.findViewById(R.id.imgOption);
            }

        }
    }
}