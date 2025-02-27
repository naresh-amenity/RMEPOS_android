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
import com.rme.roomdb.DeliveryTable;
import com.rme.roomdb.OrderTable;
import com.rme.roomdb.StockCountTable;
import com.rme.utils.AsyncTasks;
import com.rme.utils.MyFunction;
import com.rme.utils.MySession;
import com.rme.utils.ProgressDialog;
import com.rme.utils.ToastMsg;
import com.rme.utils.URLString;
import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    ListView listView;
    List<OrderTable> orderArray = new ArrayList<>();
    TextView txtDataNotFound;
    CardView crdNewOrder;
    OrderList_Adapter orderListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity);
        TextView txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("Order");
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
                new Confirm_Dialog(OrderActivity.this, "Remove All Order Data", "Are you sure do you want to remove all order data?", "Cancel", "Remove all", new Confirm_Dialog.onSelectListener() {
                    @Override
                    public void onYes() {

                        new AsyncTasks() {

                            @Override
                            public void onPreExecute() {

                            }

                            @Override
                            public void doInBackground() {

                                AppDatabase appDatabase = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
                                appDatabase.orderDao().deleteAll();


                            }


                            @Override
                            public void onPostExecute() {
                                getOrderData();
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

                new Confirm_Dialog(OrderActivity.this, "Download File", "Are you sure do you want to download file?", "Cancel", "Yes, Download", new Confirm_Dialog.onSelectListener() {
                    @Override
                    public void onYes() {

                        new AsyncTasks() {

                            String sData = "";

                            ProgressDialog progressDialog;
                            @Override
                            public void onPreExecute() {
                                progressDialog = new ProgressDialog(OrderActivity.this);
                                progressDialog.showNow();
                            }

                            @Override
                            public void doInBackground() {
                                if (!orderArray.isEmpty()) {
                                    sData = "[PT700ORDER]";
                                    sData = sData + "\nBranch=" + MySession.get(getApplicationContext(), MySession.Share_BranchID);
                                    sData = sData + "\n[Data]";
                                }
                                List<OrderTable> dataArray = new ArrayList<>();
                                for (int i = 0; i < orderArray.size(); i++) {
                                    OrderTable orderTable1 = orderArray.get(i);
                                    boolean isAdd = true;
                                    for (int j = 0; j < dataArray.size(); j++) {
                                        OrderTable orderTable2 = dataArray.get(j);
                                        if (orderTable1.getBarcode().equals(orderTable2.getBarcode())) {
                                            float f1 = URLString.getFloat(orderTable1.getOrderQty()) + URLString.getFloat(orderTable2.getOrderQty());
                                            orderTable2.setOrderQty(URLString.decimalFormat.format(f1));
                                            dataArray.set(j,orderTable2);
                                            isAdd = false;
                                            break;
                                        }
                                    }
                                    if (isAdd){
                                        dataArray.add(orderTable1);
                                    }
                                }
                                for (int i = 0; i < dataArray.size(); i++) {
                                    OrderTable orderTable = dataArray.get(i);
                                    sData = sData + "\n" + orderTable.getBarcode() + " ," + orderTable.getOrderQty()+ "," + orderTable.getOrderDate()+ "," + orderTable.getOrderNumber()+ "," + orderTable.getBranchID();
                                }
                                if (!sData.isEmpty()) {
                                    MyFunction.createFile(getApplicationContext(), "ORDERS.IMP", sData);
                                }


                            }


                            @Override
                            public void onPostExecute() {

                                progressDialog.dismissNow();
                                if (!sData.isEmpty()) {
                                    new Confirm_Dialog(OrderActivity.this, "Successfully Downloaded", "File Store at: "+URLString.strMainFolderName+"/ORDERS.IMP", "", "OK", new Confirm_Dialog.onSelectListener() {
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
        listView = findViewById(R.id.listView);
        txtDataNotFound = findViewById(R.id.txtDataNotFound);
        crdNewOrder = findViewById(R.id.crdNewOrder);
        txtDataNotFound.setVisibility(View.GONE);
        crdNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNewOrder(new OrderTable(),"");
            }
        });
        getOrderData();
        onNewOrder(new OrderTable(),"");
    }


    public void onNewOrder(OrderTable orderTable,String sOldOrderNumber) {
        new Order_Add_Dialog(OrderActivity.this, orderTable,sOldOrderNumber, new Order_Add_Dialog.onSelectListener() {
            @Override
            public void onCancel() {
                getOrderData();
            }

            @Override
            public void onDone() {
                getOrderData();
            }

            @Override
            public void onDone(String sOldOrderNumber) {
                onNewOrder(new OrderTable(),sOldOrderNumber);

            }
        }).show();
    }

    public void getOrderData() {

        new AsyncTasks() {

            @Override
            public void onPreExecute() {

            }

            @Override
            public void doInBackground() {
                AppDatabase appDatabase = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
                orderArray = appDatabase.orderDao().getAll();

            }


            @Override
            public void onPostExecute() {

                orderListAdapter = new OrderList_Adapter();
                listView.setAdapter(orderListAdapter);
                if (orderArray.size() > 0) {
                    txtDataNotFound.setVisibility(View.GONE);
                } else {
                    txtDataNotFound.setVisibility(View.VISIBLE);
                }
            }
        }.execute();

    }

    public class OrderList_Adapter extends BaseAdapter {

        private LayoutInflater inflater = null;

        public OrderList_Adapter() {
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        public int getCount() {
            return orderArray.size();
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
                row = inflater.inflate(R.layout.order_list_adapter, null);
                holder = new MyHolderView(row);
                row.setTag(holder);
            } else {
                holder = (MyHolderView) row.getTag();
            }

            OrderTable orderTable = orderArray.get(position);
//            Log.e("map", "=" + map.toString());
            holder.txtOrderNo.setText(URLString.isNull(orderTable.getOrderNumber()));
            holder.txtBarcode.setText(URLString.isNull(orderTable.getBarcode()));
            holder.txtDesc.setText(URLString.isNull(orderTable.getDescription()));
            holder.txtQty.setText(URLString.isNull(orderTable.getOrderQty()));
            holder.txtDate.setText(URLString.isNull(orderTable.getOrderDate()));

            holder.imgOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<ListPopupItem> listPopupItems = new ArrayList<>();
                    listPopupItems.add(new ListPopupItem("Edit", R.drawable.ic_edit_green));
                    listPopupItems.add(new ListPopupItem("Delete", R.drawable.ic_delete_red));
                    ListPopupWindow popup = new ListPopupWindow(OrderActivity.this);
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
                                onNewOrder(orderTable,"");
                            } else if (sMenuTitle.equals("Delete")) {
                                new Confirm_Dialog(OrderActivity.this, "Delete Item", "Are you sure you want to delete this item?", "No", "Yes, Delete", new Confirm_Dialog.onSelectListener() {
                                    @Override
                                    public void onYes() {

                                        new AsyncTasks() {

                                            @Override
                                            public void onPreExecute() {

                                            }

                                            @Override
                                            public void doInBackground() {

                                                AppDatabase appDatabase = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
                                                appDatabase.orderDao().deleteByID(URLString.isNull(orderTable.getID()));
                                                orderArray.remove(position);

                                            }


                                            @Override
                                            public void onPostExecute() {
                                                notifyDataSetChanged();
                                                if (orderArray.isEmpty()){
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
            TextView txtOrderNo,txtBarcode, txtDesc, txtQty, txtDate;
ImageView imgOption;
            MyHolderView(View v) {
                txtOrderNo = v.findViewById(R.id.txtOrderNo);
                txtBarcode = v.findViewById(R.id.txtBarcode);
                txtDesc = v.findViewById(R.id.txtDesc);
                txtQty = v.findViewById(R.id.txtQty);
                txtDate = v.findViewById(R.id.txtDate);
                imgOption = v.findViewById(R.id.imgOption);
            }

        }
    }
}