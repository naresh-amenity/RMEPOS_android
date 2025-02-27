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

public class DeliveryActivity extends AppCompatActivity {
    ListView listView;
    List<DeliveryTable> deliveryArray = new ArrayList<>();
    TextView txtDataNotFound;
    CardView crdNewDelivery;
    DeliveryList_Adapter deliveryListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_activity);
        TextView txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("Delivery");
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
                new Confirm_Dialog(DeliveryActivity.this, "Remove All Delivery Data", "Are you sure do you want to remove all delivery data?", "Cancel", "Remove all", new Confirm_Dialog.onSelectListener() {
                    @Override
                    public void onYes() {

                        new AsyncTasks() {

                            @Override
                            public void onPreExecute() {
                            }

                            @Override
                            public void doInBackground() {
                                AppDatabase appDatabase = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
                                appDatabase.deliveryDao().deleteAll();

                            }


                            @Override
                            public void onPostExecute() {
                                getData();
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

                new Confirm_Dialog(DeliveryActivity.this, "Download File", "Are you sure do you want to download file?", "Cancel", "Yes, Download", new Confirm_Dialog.onSelectListener() {
                    @Override
                    public void onYes() {

                        new AsyncTasks() {

                            String sData = "";

                            ProgressDialog progressDialog;
                            @Override
                            public void onPreExecute() {
                                progressDialog = new ProgressDialog(DeliveryActivity.this);
                                progressDialog.showNow();
                            }

                            @Override
                            public void doInBackground() {
                                if (!deliveryArray.isEmpty()) {
                                    sData = "[PT700DLVS]";
                                    sData = sData + "\nBranch=" + MySession.get(getApplicationContext(), MySession.Share_BranchID);
                                    sData = sData + "\n[Data]";
                                }

                                List<DeliveryTable> dataArray = new ArrayList<>();
                                for (int i = 0; i < deliveryArray.size(); i++) {
                                    DeliveryTable deliveryTable1 = deliveryArray.get(i);
                                    boolean isAdd = true;
                                    for (int j = 0; j < dataArray.size(); j++) {
                                        DeliveryTable deliveryTable2 = dataArray.get(j);
                                        if (deliveryTable1.getBarcode().equals(deliveryTable2.getBarcode())) {
                                            float f1 = URLString.getFloat(deliveryTable1.getDeliveryQty()) + URLString.getFloat(deliveryTable2.getDeliveryQty());
                                            deliveryTable2.setDeliveryQty(URLString.decimalFormat.format(f1));
                                            dataArray.set(j,deliveryTable2);
                                            isAdd = false;
                                            break;
                                        }
                                    }
                                    if (isAdd){
                                        dataArray.add(deliveryTable1);
                                    }
                                }
                                for (int i = 0; i < dataArray.size(); i++) {
                                    DeliveryTable deliveryTable = dataArray.get(i);
                                    sData = sData + "\n" + deliveryTable.getBarcode() + " ," + deliveryTable.getDeliveryQty()+ "," + deliveryTable.getDeliveryDate()+ "," + deliveryTable.getOrderNumber()+ "," + deliveryTable.getDeliveryNumber()+ "," + deliveryTable.getBranchID();
                                }
                                if (!sData.isEmpty()) {
                                    MyFunction.createFile(getApplicationContext(), "DELIVRS.IMP", sData);
                                }


                            }


                            @Override
                            public void onPostExecute() {

                                progressDialog.dismissNow();
                                if (!sData.isEmpty()) {
                                    new Confirm_Dialog(DeliveryActivity.this, "Successfully Downloaded", "File Store at: "+URLString.strMainFolderName+"/DELIVRS.IMP", "", "OK", new Confirm_Dialog.onSelectListener() {
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
        crdNewDelivery = findViewById(R.id.crdNewDelivery);
        txtDataNotFound.setVisibility(View.GONE);
        crdNewDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNewDelivery(new DeliveryTable(), "", "");
            }
        });
        getData();
        onNewDelivery(new DeliveryTable(), "", "");
    }


    public void onNewDelivery(DeliveryTable deliveryTable, String sOldOrderNumber, String sOldDeliveryNo) {
        new Delivery_Add_Dialog(DeliveryActivity.this, deliveryTable, sOldOrderNumber, sOldDeliveryNo, new Delivery_Add_Dialog.onSelectListener() {
            @Override
            public void onCancel() {
                getData();
            }

            @Override
            public void onDone() {
                getData();
            }

            @Override
            public void onDone(String sOldOrderNumber, String sOldDeliveryNo) {
                onNewDelivery(new DeliveryTable(), sOldOrderNumber, sOldDeliveryNo);

            }
        }).show();
    }

    public void getData() {

        new AsyncTasks() {

            @Override
            public void onPreExecute() {

            }

            @Override
            public void doInBackground() {
                AppDatabase appDatabase = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
                deliveryArray = appDatabase.deliveryDao().getAll();

            }


            @Override
            public void onPostExecute() {

                deliveryListAdapter = new DeliveryList_Adapter();
                listView.setAdapter(deliveryListAdapter);
                if (deliveryArray.size() > 0) {
                    txtDataNotFound.setVisibility(View.GONE);
                } else {
                    txtDataNotFound.setVisibility(View.VISIBLE);
                }
            }
        }.execute();

    }

    public class DeliveryList_Adapter extends BaseAdapter {

        private LayoutInflater inflater = null;

        public DeliveryList_Adapter() {
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        public int getCount() {
            return deliveryArray.size();
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
                row = inflater.inflate(R.layout.delivery_list_adapter, null);
                holder = new MyHolderView(row);
                row.setTag(holder);
            } else {
                holder = (MyHolderView) row.getTag();
            }

            DeliveryTable deliveryTable = deliveryArray.get(position);
//            Log.e("map", "=" + map.toString());
            holder.txtOrderNo.setText(URLString.isNull(deliveryTable.getOrderNumber()));
            holder.txtDeliveryNo.setText(URLString.isNull(deliveryTable.getDeliveryNumber()));
            holder.txtBarcode.setText(URLString.isNull(deliveryTable.getBarcode()));
            holder.txtDesc.setText(URLString.isNull(deliveryTable.getDescription()));
            holder.txtQty.setText(URLString.isNull(deliveryTable.getDeliveryQty()));
            holder.txtDate.setText(URLString.isNull(deliveryTable.getDeliveryDate()));

            holder.imgOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<ListPopupItem> listPopupItems = new ArrayList<>();
                    listPopupItems.add(new ListPopupItem("Edit", R.drawable.ic_edit_green));
                    listPopupItems.add(new ListPopupItem("Delete", R.drawable.ic_delete_red));
                    ListPopupWindow popup = new ListPopupWindow(DeliveryActivity.this);
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
                                onNewDelivery(deliveryTable, "", "");
                            } else if (sMenuTitle.equals("Delete")) {
                                new Confirm_Dialog(DeliveryActivity.this, "Delete Item", "Are you sure you want to delete this item?", "No", "Yes, Delete", new Confirm_Dialog.onSelectListener() {
                                    @Override
                                    public void onYes() {

                                        new AsyncTasks() {

                                            @Override
                                            public void onPreExecute() {

                                            }

                                            @Override
                                            public void doInBackground() {

                                                AppDatabase appDatabase = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
                                                appDatabase.deliveryDao().deleteByID(URLString.isNull(deliveryTable.getID()));
                                                deliveryArray.remove(position);

                                            }


                                            @Override
                                            public void onPostExecute() {
                                                notifyDataSetChanged();
                                                if (deliveryArray.isEmpty()) {
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
            TextView txtOrderNo, txtDeliveryNo, txtBarcode, txtDesc, txtQty, txtDate;
            ImageView imgOption;

            MyHolderView(View v) {
                txtOrderNo = v.findViewById(R.id.txtOrderNo);
                txtDeliveryNo = v.findViewById(R.id.txtDeliveryNo);
                txtBarcode = v.findViewById(R.id.txtBarcode);
                txtDesc = v.findViewById(R.id.txtDesc);
                txtQty = v.findViewById(R.id.txtQty);
                txtDate = v.findViewById(R.id.txtDate);
                imgOption = v.findViewById(R.id.imgOption);
            }

        }
    }
}