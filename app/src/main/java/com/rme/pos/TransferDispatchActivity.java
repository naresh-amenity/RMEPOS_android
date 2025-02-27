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
import com.rme.roomdb.StockCountTable;
import com.rme.roomdb.TransferDispatchTable;
import com.rme.utils.AsyncTasks;
import com.rme.utils.MyFunction;
import com.rme.utils.MySession;
import com.rme.utils.ProgressDialog;
import com.rme.utils.ToastMsg;
import com.rme.utils.URLString;
import java.util.ArrayList;
import java.util.List;

public class TransferDispatchActivity extends AppCompatActivity {
    ListView listView;
    List<TransferDispatchTable> transferArray = new ArrayList<>();
    TextView txtDataNotFound;
    CardView crdNewDispatch;
    TransferList_Adapter transferListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_dispatch_activity);
        TextView txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText("Transfer Dispatch");
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
                new Confirm_Dialog(TransferDispatchActivity.this, "Remove All Dispatch Data", "Are you sure do you want to remove all dispatch data?", "Cancel", "Remove all", new Confirm_Dialog.onSelectListener() {
                    @Override
                    public void onYes() {

                        new AsyncTasks() {

                            @Override
                            public void onPreExecute() {

                            }

                            @Override
                            public void doInBackground() {
                                AppDatabase appDatabase = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
                                appDatabase.transferDispatchDao().deleteAll();
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

                new Confirm_Dialog(TransferDispatchActivity.this, "Download File", "Are you sure do you want to download file?", "Cancel", "Yes, Download", new Confirm_Dialog.onSelectListener() {
                    @Override
                    public void onYes() {

                        new AsyncTasks() {

                            String sData = "";

                            ProgressDialog progressDialog;
                            @Override
                            public void onPreExecute() {
                                progressDialog = new ProgressDialog(TransferDispatchActivity.this);
                                progressDialog.showNow();
                            }

                            @Override
                            public void doInBackground() {
                                if (!transferArray.isEmpty()) {
                                    sData = "[PT700TRNSDSP]";
                                    sData = sData + "\nBranch=" + MySession.get(getApplicationContext(), MySession.Share_BranchID);
                                    sData = sData + "\n[Data]";
                                }

                                List<TransferDispatchTable> dataArray = new ArrayList<>();
                                for (int i = 0; i < transferArray.size(); i++) {
                                    TransferDispatchTable transferDispatchTable1 = transferArray.get(i);
                                    boolean isAdd = true;
                                    for (int j = 0; j < dataArray.size(); j++) {
                                        TransferDispatchTable transferDispatchTable2 = dataArray.get(j);
                                        if (transferDispatchTable1.getBarcode().equals(transferDispatchTable2.getBarcode())) {
                                            float f1 = URLString.getFloat(transferDispatchTable1.getTransferQty()) + URLString.getFloat(transferDispatchTable2.getTransferQty());
                                            transferDispatchTable2.setTransferQty(URLString.decimalFormat.format(f1));
                                            dataArray.set(j,transferDispatchTable2);
                                            isAdd = false;
                                            break;
                                        }
                                    }
                                    if (isAdd){
                                        dataArray.add(transferDispatchTable1);
                                    }
                                }
                                for (int i = 0; i < dataArray.size(); i++) {
                                    TransferDispatchTable transferDispatchTable = dataArray.get(i);
                                    sData = sData + "\n" + transferDispatchTable.getBarcode() + " ," + transferDispatchTable.getTransferQty()+ ",       ," + transferDispatchTable.getDispatchDate()+ "," + transferDispatchTable.getTransferNumber()+ "," + transferDispatchTable.getBranchID();
                                }
                                if (!sData.isEmpty()) {
                                    MyFunction.createFile(getApplicationContext(), "TRANSDSP.IMP", sData);
                                }


                            }


                            @Override
                            public void onPostExecute() {

                                progressDialog.dismissNow();
                                if (!sData.isEmpty()) {
                                    new Confirm_Dialog(TransferDispatchActivity.this, "Successfully Downloaded", "File Store at: "+URLString.strMainFolderName+"/TRANSDSP.IMP", "", "OK", new Confirm_Dialog.onSelectListener() {
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
        crdNewDispatch = findViewById(R.id.crdNewDispatch);
        txtDataNotFound.setVisibility(View.GONE);
        crdNewDispatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNewTransfer(new TransferDispatchTable(),"");
            }
        });
        getData();
        onNewTransfer(new TransferDispatchTable(),"");
    }


    public void onNewTransfer(TransferDispatchTable transferDispatchTable, String sOldTransferNumber) {
        new Transfer_Dispatch_Add_Dialog(TransferDispatchActivity.this, transferDispatchTable,sOldTransferNumber, new Transfer_Dispatch_Add_Dialog.onSelectListener() {
            @Override
            public void onCancel() {
                getData();
            }

            @Override
            public void onDone() {
                getData();
            }

            @Override
            public void onDone(String sOldTransferNumber) {
                onNewTransfer(new TransferDispatchTable(),sOldTransferNumber);

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
                transferArray = appDatabase.transferDispatchDao().getAll();
            }


            @Override
            public void onPostExecute() {

                transferListAdapter = new TransferList_Adapter();
                listView.setAdapter(transferListAdapter);
                if (transferArray.size() > 0) {
                    txtDataNotFound.setVisibility(View.GONE);
                } else {
                    txtDataNotFound.setVisibility(View.VISIBLE);
                }
            }
        }.execute();

    }

    public class TransferList_Adapter extends BaseAdapter {

        private LayoutInflater inflater = null;

        public TransferList_Adapter() {
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        public int getCount() {
            return transferArray.size();
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
                row = inflater.inflate(R.layout.transfer_dispatch_list_adapter, null);
                holder = new MyHolderView(row);
                row.setTag(holder);
            } else {
                holder = (MyHolderView) row.getTag();
            }

           TransferDispatchTable transferDispatchTable= transferArray.get(position);
//            Log.e("map", "=" + map.toString());
            holder.txtTransferNo.setText(URLString.isNull(transferDispatchTable.getTransferNumber()));
            holder.txtBarcode.setText(URLString.isNull(transferDispatchTable.getBarcode()));
            holder.txtDesc.setText(URLString.isNull(transferDispatchTable.getDescription()));
            holder.txtQty.setText(URLString.isNull(transferDispatchTable.getTransferQty()));
            holder.txtDate.setText(URLString.isNull(transferDispatchTable.getDispatchDate()));

            holder.imgOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<ListPopupItem> listPopupItems = new ArrayList<>();
                    listPopupItems.add(new ListPopupItem("Edit", R.drawable.ic_edit_green));
                    listPopupItems.add(new ListPopupItem("Delete", R.drawable.ic_delete_red));
                    ListPopupWindow popup = new ListPopupWindow(TransferDispatchActivity.this);
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
                                onNewTransfer(transferDispatchTable,"");
                            } else if (sMenuTitle.equals("Delete")) {
                                new Confirm_Dialog(TransferDispatchActivity.this, "Delete Item", "Are you sure you want to delete this item?", "No", "Yes, Delete", new Confirm_Dialog.onSelectListener() {
                                    @Override
                                    public void onYes() {

                                        new AsyncTasks() {

                                            @Override
                                            public void onPreExecute() {

                                            }

                                            @Override
                                            public void doInBackground() {
                                                AppDatabase appDatabase = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
                                                appDatabase.transferDispatchDao().deleteByID(URLString.isNull(transferDispatchTable.getID()));
                                                transferArray.remove(position);
                                            }


                                            @Override
                                            public void onPostExecute() {
                                                notifyDataSetChanged();
                                                if (transferArray.isEmpty()){
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
            TextView txtTransferNo,txtBarcode, txtDesc, txtQty, txtDate;
            ImageView imgOption;
            MyHolderView(View v) {
                txtTransferNo = v.findViewById(R.id.txtTransferNo);
                txtBarcode = v.findViewById(R.id.txtBarcode);
                txtDesc = v.findViewById(R.id.txtDesc);
                txtQty = v.findViewById(R.id.txtQty);
                txtDate = v.findViewById(R.id.txtDate);
                imgOption = v.findViewById(R.id.imgOption);
            }

        }
    }
}