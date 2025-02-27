package com.rme.roomdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ProductTable.class,DeliveryTable.class,OrderTable.class,StockCountTable.class,TransferDispatchTable.class,TransferReceiptTable.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
    public abstract DeliveryDao deliveryDao();
    public abstract OrderDao orderDao();
    public abstract StockCountDao stockCountDao();
    public abstract TransferDispatchDao transferDispatchDao();
    public abstract TransferReceiptDao transferReceiptDao();
}
