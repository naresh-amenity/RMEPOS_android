package com.rme.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.rme.utils.URLString;

import java.util.ArrayList;
import java.util.HashMap;

public class DBClass extends SQLiteOpenHelper {

    public static String D_Name = "RMePOS_Database";
    public static int Version = 1;


    SQLiteDatabase db;

    public static String getRootDatabaseCache(Context context) {
        String sDBPath = "";

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            sDBPath = context.getCacheDir().getAbsolutePath();
        } else {
            sDBPath = URLString.getRootPath(context);
        }
        sDBPath = sDBPath + "/" + D_Name;


        return sDBPath;
    }

    public DBClass(Context context, String name, CursorFactory factory, int version) {
        super(context, getRootDatabaseCache(context), null, Version);
        // TODO Auto-generated constructor stub

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub


        try {
            String data = "CREATE TABLE IF NOT EXISTS " + P_Product.TableName + "( " + P_Product.ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT," + P_Product.Barcode + " VARCHAR(100),"
                    + P_Product.Description + " VARCHAR(100),"
                    + P_Product.Stock + " VARCHAR(100),"
                    + P_Product.Price1 + " VARCHAR(100),"
                    + P_Product.Price2 + " VARCHAR(100),"
                    + P_Product.NewPrice1 + " VARCHAR(100),"
                    + P_Product.NewPrice2 + " VARCHAR(100),"
                    + P_Product.CreateAt + " VARCHAR(100),"
                    + P_Product.IsPriceUpdate + " VARCHAR(100))";
            db.execSQL(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            String data = "CREATE TABLE IF NOT EXISTS " + P_Order.TableName + "( " + P_Order.ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT," + P_Order.Barcode + " VARCHAR(100),"
                    + P_Order.OrderNumber + " VARCHAR(100),"
                    + P_Order.Description + " VARCHAR(300),"
                    + P_Order.OrderQty + " VARCHAR(100),"
                    + P_Order.OrderDate + " VARCHAR(100),"
                    + P_Order.BranchID + " VARCHAR(100),"
                    + P_Order.CreateAt + " VARCHAR(100))";
            db.execSQL(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            String data = "CREATE TABLE IF NOT EXISTS " + P_StockCount.TableName + "( " + P_StockCount.ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT," + P_StockCount.Barcode + " VARCHAR(100),"
                    + P_StockCount.Description + " VARCHAR(300),"
                    + P_StockCount.StockCount + " VARCHAR(100),"
                    + P_StockCount.CreateAt + " VARCHAR(100))";
            db.execSQL(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            String data = "CREATE TABLE IF NOT EXISTS " + P_Delivery.TableName + "( " + P_Delivery.ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT," + P_Delivery.Barcode + " VARCHAR(100),"
                    + P_Delivery.Description + " VARCHAR(300),"
                    + P_Delivery.DeliveryDate + " VARCHAR(100),"
                    + P_Delivery.OrderNumber + " VARCHAR(100),"
                    + P_Delivery.DeliveryNumber + " VARCHAR(100),"
                    + P_Delivery.DeliveryQty + " VARCHAR(100),"
                    + P_Delivery.BranchID + " VARCHAR(100),"
                    + P_Delivery.CreateAt + " VARCHAR(100))";
            db.execSQL(data);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            String data = "CREATE TABLE IF NOT EXISTS " + P_TransferDispatch.TableName + "( " + P_TransferDispatch.ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT," + P_TransferDispatch.Barcode + " VARCHAR(100),"
                    + P_TransferDispatch.Description + " VARCHAR(300),"
                    + P_TransferDispatch.DispatchDate + " VARCHAR(100),"
                    + P_TransferDispatch.TransferNumber + " VARCHAR(100),"
                    + P_TransferDispatch.TransferQty + " VARCHAR(100),"
                    + P_TransferDispatch.BranchID + " VARCHAR(100),"
                    + P_TransferDispatch.CreateAt + " VARCHAR(100))";
            db.execSQL(data);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            String data = "CREATE TABLE IF NOT EXISTS " + P_TransferReceipt.TableName + "( " + P_TransferReceipt.ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT," + P_TransferReceipt.Barcode + " VARCHAR(100),"
                    + P_TransferReceipt.Description + " VARCHAR(300),"
                    + P_TransferReceipt.ReceiptDate + " VARCHAR(100),"
                    + P_TransferReceipt.TransferNumber + " VARCHAR(100),"
                    + P_TransferReceipt.TransferQty + " VARCHAR(100),"
                    + P_TransferReceipt.BranchID + " VARCHAR(100),"
                    + P_TransferReceipt.CreateAt + " VARCHAR(100))";
            db.execSQL(data);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

        if (newVersion > oldVersion) {

//            db.execSQL("ALTER TABLE " + T_MahavirData + " ADD COLUMN " + C_M_ButterBhajiJain + " VARCHAR(100)");
//            db.execSQL("ALTER TABLE " + T_MahavirData + " ADD COLUMN " + C_M_TelBhajiJain + " VARCHAR(100)");
//            db.execSQL("ALTER TABLE " + T_MahavirData + " ADD COLUMN " + C_M_PoniBhajiJain + " VARCHAR(100)");
//            db.execSQL("ALTER TABLE " + T_MahavirData + " ADD COLUMN " + C_M_ColdDrinks + " VARCHAR(100)");
        }


    }


    public Cursor getProductByBarcode(String aBarcode) {
        checkDB(false);
        String selectGroupContact = "";
        selectGroupContact = "SELECT * FROM " + P_Product.TableName + " WHERE " + P_Product.Barcode + " = '" + aBarcode + "' ORDER BY " + P_Product.Barcode + " ASC";

//        Log.e("selectGroupContact","="+selectGroupContact);
        Cursor cursor = db.rawQuery(selectGroupContact, null);
        return cursor;
    }

    public Cursor getAllProduct() {
        checkDB(false);
        String selectGroupContact = "";
        selectGroupContact = "SELECT * FROM " + P_Product.TableName + " ORDER BY " + P_Product.ID + " ASC";
        Cursor cursor = db.rawQuery(selectGroupContact, null);

        return cursor;
    }

    public Cursor getAllProductPrice() {
        checkDB(false);
        String selectGroupContact = "";
        selectGroupContact = "SELECT * FROM " + P_Product.TableName + " WHERE " + P_Product.IsPriceUpdate + " == '1' ORDER BY " + P_Product.ID + " ASC";

//        selectGroupContact = "SELECT * FROM " + P_Product.TableName + " ORDER BY " + P_Product.ID + " ASC";
        Cursor cursor = db.rawQuery(selectGroupContact, null);

        return cursor;
    }

    public Cursor getOrderByBarcode(String aBarcode) {
        checkDB(false);
        String selectGroupContact = "";
        selectGroupContact = "SELECT * FROM " + P_Order.TableName + " WHERE " + P_Order.Barcode + " = '" + aBarcode + "' ORDER BY " + P_Order.Barcode + " ASC";

//        Log.e("selectGroupContact","="+selectGroupContact);
        Cursor cursor = db.rawQuery(selectGroupContact, null);
        return cursor;
    }

    public Cursor getAllOrder() {
        checkDB(false);
        String selectGroupContact = "";
        selectGroupContact = "SELECT * FROM " + P_Order.TableName + " ORDER BY " + P_Order.ID + " ASC";
        Cursor cursor = db.rawQuery(selectGroupContact, null);

        return cursor;
    }

    public Cursor getStockCountByBarcode(String aBarcode) {
        checkDB(false);
        String selectGroupContact = "";
        selectGroupContact = "SELECT * FROM " + P_StockCount.TableName + " WHERE " + P_StockCount.Barcode + " = '" + aBarcode + "' ORDER BY " + P_StockCount.Barcode + " ASC";

//        Log.e("selectGroupContact","="+selectGroupContact);
        Cursor cursor = db.rawQuery(selectGroupContact, null);
        return cursor;
    }

    public Cursor getAllStockCount() {
        checkDB(false);
        String selectGroupContact = "";
        selectGroupContact = "SELECT * FROM " + P_StockCount.TableName + " ORDER BY " + P_StockCount.ID + " ASC";
        Cursor cursor = db.rawQuery(selectGroupContact, null);

        return cursor;
    }

    public Cursor getDeliveryByBarcode(String aBarcode) {
        checkDB(false);
        String selectGroupContact = "";
        selectGroupContact = "SELECT * FROM " + P_Delivery.TableName + " WHERE " + P_Delivery.Barcode + " = '" + aBarcode + "' ORDER BY " + P_Delivery.Barcode + " ASC";
//        Log.e("selectGroupContact","="+selectGroupContact);
        Cursor cursor = db.rawQuery(selectGroupContact, null);
        return cursor;
    }

    public Cursor getAllDelivery() {
        checkDB(false);
        String selectGroupContact = "";
        selectGroupContact = "SELECT * FROM " + P_Delivery.TableName + " ORDER BY " + P_Delivery.ID + " ASC";
        Cursor cursor = db.rawQuery(selectGroupContact, null);

        return cursor;
    }

    public Cursor getTransferDispatchByBarcode(String aBarcode) {
        checkDB(false);
        String selectGroupContact = "";
        selectGroupContact = "SELECT * FROM " + P_TransferDispatch.TableName + " WHERE " + P_TransferDispatch.Barcode + " = '" + aBarcode + "' ORDER BY " + P_TransferDispatch.Barcode + " ASC";
//        Log.e("selectGroupContact","="+selectGroupContact);
        Cursor cursor = db.rawQuery(selectGroupContact, null);
        return cursor;
    }

    public Cursor getAllTransferDispatch() {
        checkDB(false);
        String selectGroupContact = "";
        selectGroupContact = "SELECT * FROM " + P_TransferDispatch.TableName + " ORDER BY " + P_TransferDispatch.ID + " ASC";
        Cursor cursor = db.rawQuery(selectGroupContact, null);

        return cursor;
    }

    public Cursor getTransferReceiptByBarcode(String aBarcode) {
        checkDB(false);
        String selectGroupContact = "";
        selectGroupContact = "SELECT * FROM " + P_TransferReceipt.TableName + " WHERE " + P_TransferReceipt.Barcode + " = '" + aBarcode + "' ORDER BY " + P_TransferReceipt.Barcode + " ASC";
//        Log.e("selectGroupContact","="+selectGroupContact);
        Cursor cursor = db.rawQuery(selectGroupContact, null);
        return cursor;
    }

    public Cursor getAllTransferReceipt() {
        checkDB(false);
        String selectGroupContact = "";
        selectGroupContact = "SELECT * FROM " + P_TransferReceipt.TableName + " ORDER BY " + P_TransferReceipt.ID + " ASC";
        Cursor cursor = db.rawQuery(selectGroupContact, null);

        return cursor;
    }


    public boolean addProduct(String sBarcode, String sDescription, String sStock, String sPrice1, String sPrice2, String sNewPrice1, String sNewPrice2, String sCreateAt, String sIsPriceUpdate) {

        checkDB(true);
        boolean isAdd = false;
        String selectContact = "SELECT * FROM " + P_Product.TableName + " WHERE " + P_Product.Barcode + " = '" + sBarcode + "'";
        Cursor cursor = db.rawQuery(selectContact, null);
        ContentValues conValues = new ContentValues();
        if (cursor.getCount() == 0) {
            conValues.put(P_Product.Barcode, sBarcode);
            conValues.put(P_Product.Description, sDescription);
            conValues.put(P_Product.Stock, sStock);
            conValues.put(P_Product.Price1, sPrice1);
            conValues.put(P_Product.Price2, sPrice2);
            conValues.put(P_Product.NewPrice1, sNewPrice1);
            conValues.put(P_Product.NewPrice2, sNewPrice2);
            conValues.put(P_Product.CreateAt, sCreateAt);
            conValues.put(P_Product.IsPriceUpdate, sIsPriceUpdate);
            db.insert(P_Product.TableName, null, conValues);
            conValues.clear();
            isAdd = true;
        }
        return isAdd;
    }


    public boolean addUpdateProductStock(String sBarcode, String sDescription, String sStock, String sPrice1, String sPrice2, String sNewPrice1, String sNewPrice2, String sCreateAt, String sIsPriceUpdate) {

        checkDB(true);
        boolean isAdd = false;
        String selectContact = "SELECT * FROM " + P_Product.TableName + " WHERE " + P_Product.Barcode + " = '" + sBarcode + "'";
        Cursor cursor = db.rawQuery(selectContact, null);


        if (cursor.getCount() == 0) {
            ContentValues conValues = new ContentValues();
            conValues.put(P_Product.Barcode, sBarcode);
            conValues.put(P_Product.Description, sDescription);
            conValues.put(P_Product.Stock, sStock);
            conValues.put(P_Product.Price1, sPrice1);
            conValues.put(P_Product.Price2, sPrice2);
            conValues.put(P_Product.NewPrice1, sNewPrice1);
            conValues.put(P_Product.NewPrice2, sNewPrice2);
            conValues.put(P_Product.CreateAt, sCreateAt);
            conValues.put(P_Product.IsPriceUpdate, sIsPriceUpdate);
            db.insert(P_Product.TableName, null, conValues);
            conValues.clear();
            isAdd = true;
        } else {
            float fQty = URLString.getFloat(sStock);
            String sID = "";
            if (cursor.moveToFirst()) {
                do {
                    try {
                        sID = get(cursor, P_Product.ID);
                        fQty = fQty + URLString.getFloat(get(cursor, P_Product.Stock));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }
            ContentValues conValues = new ContentValues();
            conValues.put(P_Product.Barcode, sBarcode);
            conValues.put(P_Product.Description, sDescription);
            conValues.put(P_Product.Stock, URLString.decimalFormat.format(fQty));
            conValues.put(P_Product.Price1, sPrice1);
            conValues.put(P_Product.Price2, sPrice2);
            conValues.put(P_Product.NewPrice1, sNewPrice1);
            conValues.put(P_Product.NewPrice2, sNewPrice2);
            conValues.put(P_Product.CreateAt, sCreateAt);
            conValues.put(P_Product.IsPriceUpdate, sIsPriceUpdate);
            String where = P_Product.ID + "=" + sID;
//        String[] whereArgs = null;
            db.update(P_Product.TableName, conValues, where, null);
            isAdd = true;
        }
        cursor.close();
        return isAdd;
    }

    public boolean addProductStock(String sBarcode, String sDescription, String sStock, String sPrice1, String sPrice2, String sNewPrice1, String sNewPrice2, String sCreateAt, String sIsPriceUpdate) {

        checkDB(true);
        boolean isAdd = false;

        ContentValues conValues = new ContentValues();
        conValues.put(P_Product.Barcode, sBarcode);
        conValues.put(P_Product.Description, sDescription);
        conValues.put(P_Product.Stock, sStock);
        conValues.put(P_Product.Price1, sPrice1);
        conValues.put(P_Product.Price2, sPrice2);
        conValues.put(P_Product.NewPrice1, sNewPrice1);
        conValues.put(P_Product.NewPrice2, sNewPrice2);
        conValues.put(P_Product.CreateAt, sCreateAt);
        conValues.put(P_Product.IsPriceUpdate, sIsPriceUpdate);
        db.insert(P_Product.TableName, null, conValues);
        conValues.clear();

        return true;
    }

    public boolean addOrUpdateProduct(String sBarcode, String sDescription, String sStock, String sPrice1, String sPrice2, String sNewPrice1, String sNewPrice2, String sCreateAt, String sIsPriceUpdate) {

        checkDB(true);
        boolean isAdd = false;
        String selectContact = "SELECT * FROM " + P_Product.TableName + " WHERE " + P_Product.Barcode + " = '" + sBarcode + "'";
        Cursor cursor = db.rawQuery(selectContact, null);


        if (cursor.getCount() == 0) {
            ContentValues conValues = new ContentValues();
            conValues.put(P_Product.Barcode, sBarcode);
            conValues.put(P_Product.Description, sDescription);
            conValues.put(P_Product.Stock, sStock);
            conValues.put(P_Product.Price1, sPrice1);
            conValues.put(P_Product.Price2, sPrice2);
            conValues.put(P_Product.NewPrice1, sNewPrice1);
            conValues.put(P_Product.NewPrice2, sNewPrice2);
            conValues.put(P_Product.CreateAt, sCreateAt);
            conValues.put(P_Product.IsPriceUpdate, sIsPriceUpdate);
            db.insert(P_Product.TableName, null, conValues);
            conValues.clear();
            isAdd = true;
        } else {
            String sID = "";
            if (cursor.moveToFirst()) {
                do {
                    try {
                        sID = get(cursor, P_Product.ID);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }
            ContentValues conValues = new ContentValues();
            conValues.put(P_Product.Barcode, sBarcode);
            conValues.put(P_Product.Description, sDescription);
            conValues.put(P_Product.Stock, sStock);
            conValues.put(P_Product.Price1, sPrice1);
            conValues.put(P_Product.Price2, sPrice2);
            conValues.put(P_Product.NewPrice1, sNewPrice1);
            conValues.put(P_Product.NewPrice2, sNewPrice2);
            conValues.put(P_Product.CreateAt, sCreateAt);
            conValues.put(P_Product.IsPriceUpdate, sIsPriceUpdate);
            String where = P_Product.ID + "=" + sID;
//        String[] whereArgs = null;
            db.update(P_Product.TableName, conValues, where, null);
            isAdd = true;
        }
        cursor.close();
        return isAdd;
    }

    public void updateProduct(String sID, String sBarcode, String sDescription, String sStock, String sPrice1, String sPrice2, String sNewPrice1, String sNewPrice2, String sCreateAt, String sIsPriceUpdate) {
        checkDB(true);

        ContentValues conValues = new ContentValues();
        conValues.put(P_Product.Barcode, sBarcode);
        conValues.put(P_Product.Description, sDescription);
        conValues.put(P_Product.Stock, sStock);
        conValues.put(P_Product.Price1, sPrice1);
        conValues.put(P_Product.Price2, sPrice2);
        conValues.put(P_Product.NewPrice1, sNewPrice1);
        conValues.put(P_Product.NewPrice2, sNewPrice2);
        conValues.put(P_Product.CreateAt, sCreateAt);
        conValues.put(P_Product.IsPriceUpdate, sIsPriceUpdate);
        String where = P_Product.ID + "=" + sID;
//        String[] whereArgs = null;
        db.update(P_Product.TableName, conValues, where, null);

    }

    public boolean addOrder(String sBarcode, String sOrderNumber, String sDescription, String sOrderQty, String sOrderDate, String sBranchID, String sCreateAt) {

        checkDB(true);
        boolean isAdd = false;
        ContentValues conValues = new ContentValues();
        conValues.put(P_Order.Barcode, sBarcode);
        conValues.put(P_Order.OrderNumber, sOrderNumber);
        conValues.put(P_Order.Description, sDescription);
        conValues.put(P_Order.OrderQty, sOrderQty);
        conValues.put(P_Order.OrderDate, sOrderDate);
        conValues.put(P_Order.BranchID, sBranchID);
        conValues.put(P_Order.CreateAt, sCreateAt);
        db.insert(P_Order.TableName, null, conValues);
        conValues.clear();
        return true;
    }


    public void updateOrder(String sID, String sBarcode, String sOrderNumber, String sDescription, String sOrderQty, String sOrderDate, String sBranchID, String sCreateAt) {
        checkDB(true);

        ContentValues conValues = new ContentValues();
        conValues.put(P_Order.Barcode, sBarcode);
        conValues.put(P_Order.OrderNumber, sOrderNumber);
        conValues.put(P_Order.Description, sDescription);
        conValues.put(P_Order.OrderQty, sOrderQty);
        conValues.put(P_Order.OrderDate, sOrderDate);
        conValues.put(P_Order.BranchID, sBranchID);
        conValues.put(P_Order.CreateAt, sCreateAt);
        String where = P_Order.ID + "=" + sID;
//        String[] whereArgs = null;
        db.update(P_Order.TableName, conValues, where, null);

    }

    public boolean addStockCount(String sBarcode, String sDescription, String sStockCount, String sCreateAt) {

        checkDB(true);
        boolean isAdd = false;

        ContentValues conValues = new ContentValues();
        conValues.put(P_StockCount.Barcode, sBarcode);
        conValues.put(P_StockCount.Description, sDescription);
        conValues.put(P_StockCount.StockCount, sStockCount);
        conValues.put(P_StockCount.CreateAt, sCreateAt);
        db.insert(P_StockCount.TableName, null, conValues);
        conValues.clear();
        isAdd = true;

        return isAdd;
    }

    public boolean addOrUpdateStockCount(String sBarcode, String sDescription, String sStockCount, String sCreateAt) {

        checkDB(true);
        boolean isAdd = false;
        String selectContact = "SELECT * FROM " + P_StockCount.TableName + " WHERE " + P_StockCount.Barcode + " = '" + sBarcode + "'";
        Cursor cursor = db.rawQuery(selectContact, null);


        if (cursor.getCount() == 0) {
            ContentValues conValues = new ContentValues();
            conValues.put(P_StockCount.Barcode, sBarcode);
            conValues.put(P_StockCount.Description, sDescription);
            conValues.put(P_StockCount.StockCount, sStockCount);
            conValues.put(P_StockCount.CreateAt, sCreateAt);
            db.insert(P_StockCount.TableName, null, conValues);
            conValues.clear();
            isAdd = true;
        } else {
            String sID = "";
            if (cursor.moveToFirst()) {
                do {
                    try {
                        sID = get(cursor, P_StockCount.ID);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }
            ContentValues conValues = new ContentValues();
            conValues.put(P_StockCount.Barcode, sBarcode);
            conValues.put(P_StockCount.Description, sDescription);
            conValues.put(P_StockCount.StockCount, sStockCount);
            conValues.put(P_StockCount.CreateAt, sCreateAt);
            String where = P_StockCount.ID + "=" + sID;
//        String[] whereArgs = null;
            db.update(P_StockCount.TableName, conValues, where, null);
            isAdd = true;
        }
        cursor.close();
        return isAdd;
    }

    public boolean updateStockCount(String sID, String sBarcode, String sDescription, String sStockCount, String sCreateAt) {

        checkDB(true);
        ContentValues conValues = new ContentValues();
        conValues.put(P_StockCount.Barcode, sBarcode);
        conValues.put(P_StockCount.Description, sDescription);
        conValues.put(P_StockCount.StockCount, sStockCount);
        conValues.put(P_StockCount.CreateAt, sCreateAt);
        String where = P_StockCount.ID + "=" + sID;
//        String[] whereArgs = null;
        db.update(P_StockCount.TableName, conValues, where, null);

        return true;
    }

    public boolean addDelivery(String sDeliveryDate, String sOrderNumber, String sDeliveryNumber, String sBarcode, String sDescription, String sDeliveryQty, String sBranchID, String sCreateAt) {

        checkDB(true);
        ContentValues conValues = new ContentValues();
        conValues.put(P_Delivery.DeliveryDate, sDeliveryDate);
        conValues.put(P_Delivery.OrderNumber, sOrderNumber);
        conValues.put(P_Delivery.DeliveryNumber, sDeliveryNumber);
        conValues.put(P_Delivery.Barcode, sBarcode);
        conValues.put(P_Delivery.Description, sDescription);
        conValues.put(P_Delivery.DeliveryQty, sDeliveryQty);
        conValues.put(P_Delivery.BranchID, sBranchID);
        conValues.put(P_Delivery.CreateAt, sCreateAt);
        db.insert(P_Delivery.TableName, null, conValues);
        conValues.clear();
        return true;
    }

    public boolean addOrUpdateDelivery(String sDeliveryDate, String sOrderNumber, String sDeliveryNumber, String sBarcode, String sDescription, String sDeliveryQty, String sBranchID, String sCreateAt) {

        checkDB(true);
        boolean isAdd = false;
        String selectContact = "SELECT * FROM " + P_Delivery.TableName + " WHERE " + P_Delivery.Barcode + " = '" + sBarcode + "'";
        Cursor cursor = db.rawQuery(selectContact, null);


        if (cursor.getCount() == 0) {
            ContentValues conValues = new ContentValues();
            conValues.put(P_Delivery.DeliveryDate, sDeliveryDate);
            conValues.put(P_Delivery.OrderNumber, sOrderNumber);
            conValues.put(P_Delivery.DeliveryNumber, sDeliveryNumber);
            conValues.put(P_Delivery.Barcode, sBarcode);
            conValues.put(P_Delivery.Description, sDescription);
            conValues.put(P_Delivery.DeliveryQty, sDeliveryQty);
            conValues.put(P_Delivery.BranchID, sBranchID);
            conValues.put(P_Delivery.CreateAt, sCreateAt);
            db.insert(P_Delivery.TableName, null, conValues);
            conValues.clear();
            isAdd = true;
        } else {
            String sID = "";
            if (cursor.moveToFirst()) {
                do {
                    try {
                        sID = get(cursor, P_Delivery.ID);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }
            ContentValues conValues = new ContentValues();

            conValues.put(P_Delivery.DeliveryDate, sDeliveryDate);
            conValues.put(P_Delivery.OrderNumber, sOrderNumber);
            conValues.put(P_Delivery.DeliveryNumber, sDeliveryNumber);
            conValues.put(P_Delivery.Barcode, sBarcode);
            conValues.put(P_Delivery.Description, sDescription);
            conValues.put(P_Delivery.DeliveryQty, sDeliveryQty);
            conValues.put(P_Delivery.BranchID, sBranchID);
            conValues.put(P_Delivery.CreateAt, sCreateAt);
            String where = P_Delivery.ID + "=" + sID;
//        String[] whereArgs = null;
            db.update(P_Delivery.TableName, conValues, where, null);
            isAdd = true;
        }
        cursor.close();
        return isAdd;
    }

    public void updateDelivery(String sID, String sDeliveryDate, String sOrderNumber, String sDeliveryNumber, String sBarcode, String sDescription, String sDeliveryQty, String sBranchID, String sCreateAt) {
        checkDB(true);

        ContentValues conValues = new ContentValues();
        conValues.put(P_Delivery.DeliveryDate, sDeliveryDate);
        conValues.put(P_Delivery.OrderNumber, sOrderNumber);
        conValues.put(P_Delivery.DeliveryNumber, sDeliveryNumber);
        conValues.put(P_Delivery.Barcode, sBarcode);
        conValues.put(P_Delivery.Description, sDescription);
        conValues.put(P_Delivery.DeliveryQty, sDeliveryQty);
        conValues.put(P_Delivery.BranchID, sBranchID);
        conValues.put(P_Delivery.CreateAt, sCreateAt);
        String where = P_Delivery.ID + "=" + sID;
//        String[] whereArgs = null;
        db.update(P_Delivery.TableName, conValues, where, null);

    }

    public boolean addTransferDispatch(String sDispatchDate, String sTransferNumber, String sBarcode, String sDescription, String sTransferQty, String sBranchID, String sCreateAt) {

        checkDB(true);
        ContentValues conValues = new ContentValues();
        conValues.put(P_TransferDispatch.DispatchDate, sDispatchDate);
        conValues.put(P_TransferDispatch.TransferNumber, sTransferNumber);
        conValues.put(P_TransferDispatch.Barcode, sBarcode);
        conValues.put(P_TransferDispatch.Description, sDescription);
        conValues.put(P_TransferDispatch.TransferQty, sTransferQty);
        conValues.put(P_TransferDispatch.BranchID, sBranchID);
        conValues.put(P_TransferDispatch.CreateAt, sCreateAt);
        db.insert(P_TransferDispatch.TableName, null, conValues);
        conValues.clear();
        return true;
    }

    public boolean addOrUpdateTransferDispatch(String sDispatchDate, String sTransferNumber, String sBarcode, String sDescription, String sTransferQty, String sBranchID, String sCreateAt) {

        checkDB(true);
        boolean isAdd = false;
        String selectContact = "SELECT * FROM " + P_TransferDispatch.TableName + " WHERE " + P_TransferDispatch.Barcode + " = '" + sBarcode + "'";
        Cursor cursor = db.rawQuery(selectContact, null);


        if (cursor.getCount() == 0) {
            ContentValues conValues = new ContentValues();
            conValues.put(P_TransferDispatch.DispatchDate, sDispatchDate);
            conValues.put(P_TransferDispatch.TransferNumber, sTransferNumber);
            conValues.put(P_TransferDispatch.Barcode, sBarcode);
            conValues.put(P_TransferDispatch.Description, sDescription);
            conValues.put(P_TransferDispatch.TransferQty, sTransferQty);
            conValues.put(P_TransferDispatch.BranchID, sBranchID);
            conValues.put(P_TransferDispatch.CreateAt, sCreateAt);
            db.insert(P_TransferDispatch.TableName, null, conValues);
            conValues.clear();
            isAdd = true;
        } else {
            String sID = "";
            if (cursor.moveToFirst()) {
                do {
                    try {
                        sID = get(cursor, P_TransferDispatch.ID);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }
            ContentValues conValues = new ContentValues();
            conValues.put(P_TransferDispatch.DispatchDate, sDispatchDate);
            conValues.put(P_TransferDispatch.TransferNumber, sTransferNumber);
            conValues.put(P_TransferDispatch.Barcode, sBarcode);
            conValues.put(P_TransferDispatch.Description, sDescription);
            conValues.put(P_TransferDispatch.TransferQty, sTransferQty);
            conValues.put(P_TransferDispatch.BranchID, sBranchID);
            conValues.put(P_TransferDispatch.CreateAt, sCreateAt);
            String where = P_TransferDispatch.ID + "=" + sID;
//        String[] whereArgs = null;
            db.update(P_TransferDispatch.TableName, conValues, where, null);
            isAdd = true;
        }
        cursor.close();
        return isAdd;
    }

    public void updateTransferDispatch(String sID, String sDispatchDate, String sTransferNumber, String sBarcode, String sDescription, String sTransferQty, String sBranchID, String sCreateAt) {
        checkDB(true);

        ContentValues conValues = new ContentValues();
        conValues.put(P_TransferDispatch.DispatchDate, sDispatchDate);
        conValues.put(P_TransferDispatch.TransferNumber, sTransferNumber);
        conValues.put(P_TransferDispatch.Barcode, sBarcode);
        conValues.put(P_TransferDispatch.Description, sDescription);
        conValues.put(P_TransferDispatch.TransferQty, sTransferQty);
        conValues.put(P_TransferDispatch.BranchID, sBranchID);
        conValues.put(P_TransferDispatch.CreateAt, sCreateAt);
        String where = P_TransferDispatch.ID + "=" + sID;
//        String[] whereArgs = null;
        db.update(P_TransferDispatch.TableName, conValues, where, null);

    }

    public boolean addTransferReceipt(String sReceiptDate, String sTransferNumber, String sBarcode, String sDescription, String sTransferQty, String sBranchID, String sCreateAt) {
        checkDB(true);
        ContentValues conValues = new ContentValues();
        conValues.put(P_TransferReceipt.ReceiptDate, sReceiptDate);
        conValues.put(P_TransferReceipt.TransferNumber, sTransferNumber);
        conValues.put(P_TransferReceipt.Barcode, sBarcode);
        conValues.put(P_TransferReceipt.Description, sDescription);
        conValues.put(P_TransferReceipt.TransferQty, sTransferQty);
        conValues.put(P_TransferReceipt.BranchID, sBranchID);
        conValues.put(P_TransferReceipt.CreateAt, sCreateAt);
        db.insert(P_TransferReceipt.TableName, null, conValues);
        conValues.clear();
        return true;
    }

    public boolean addOrUpdateTransferReceipt(String sReceiptDate, String sTransferNumber, String sBarcode, String sDescription, String sTransferQty, String sBranchID, String sCreateAt) {

        checkDB(true);
        boolean isAdd = false;
        String selectContact = "SELECT * FROM " + P_TransferReceipt.TableName + " WHERE " + P_TransferReceipt.Barcode + " = '" + sBarcode + "'";
        Cursor cursor = db.rawQuery(selectContact, null);


        if (cursor.getCount() == 0) {
            ContentValues conValues = new ContentValues();
            conValues.put(P_TransferReceipt.ReceiptDate, sReceiptDate);
            conValues.put(P_TransferReceipt.TransferNumber, sTransferNumber);
            conValues.put(P_TransferReceipt.Barcode, sBarcode);
            conValues.put(P_TransferReceipt.Description, sDescription);
            conValues.put(P_TransferReceipt.TransferQty, sTransferQty);
            conValues.put(P_TransferReceipt.BranchID, sBranchID);
            conValues.put(P_TransferReceipt.CreateAt, sCreateAt);
            db.insert(P_TransferReceipt.TableName, null, conValues);
            conValues.clear();
            isAdd = true;
        } else {
            String sID = "";
            if (cursor.moveToFirst()) {
                do {
                    try {
                        sID = get(cursor, P_TransferReceipt.ID);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }
            ContentValues conValues = new ContentValues();
            conValues.put(P_TransferReceipt.ReceiptDate, sReceiptDate);
            conValues.put(P_TransferReceipt.TransferNumber, sTransferNumber);
            conValues.put(P_TransferReceipt.Barcode, sBarcode);
            conValues.put(P_TransferReceipt.Description, sDescription);
            conValues.put(P_TransferReceipt.TransferQty, sTransferQty);
            conValues.put(P_TransferReceipt.BranchID, sBranchID);
            conValues.put(P_TransferReceipt.CreateAt, sCreateAt);
            String where = P_TransferReceipt.ID + "=" + sID;
//        String[] whereArgs = null;
            db.update(P_TransferReceipt.TableName, conValues, where, null);
            isAdd = true;
        }
        cursor.close();
        return isAdd;
    }

    public void updateTransferReceipt(String sID, String sReceiptDate, String sTransferNumber, String sBarcode, String sDescription, String sTransferQty, String sBranchID, String sCreateAt) {
        checkDB(true);

        ContentValues conValues = new ContentValues();
        conValues.put(P_TransferReceipt.ReceiptDate, sReceiptDate);
        conValues.put(P_TransferReceipt.TransferNumber, sTransferNumber);
        conValues.put(P_TransferReceipt.Barcode, sBarcode);
        conValues.put(P_TransferReceipt.Description, sDescription);
        conValues.put(P_TransferReceipt.TransferQty, sTransferQty);
        conValues.put(P_TransferReceipt.BranchID, sBranchID);
        conValues.put(P_TransferReceipt.CreateAt, sCreateAt);
        String where = P_TransferReceipt.ID + "=" + sID;
//        String[] whereArgs = null;
        db.update(P_TransferReceipt.TableName, conValues, where, null);

    }

    public int deleteProduct(String sID) {
        checkDB(true);
        String where1 = P_Product.ID + "='" + sID + "'";
        return db.delete(P_Product.TableName, where1, null);
    }

    public int deleteProductAll() {
        checkDB(true);
        return db.delete(P_Product.TableName, null, null);
    }

    public int deleteOrder(String sID) {
        checkDB(true);
        String where1 = P_Order.ID + "='" + sID + "'";
        return db.delete(P_Order.TableName, where1, null);
    }

    public int deleteOrderAll() {
        checkDB(true);
        return db.delete(P_Order.TableName, null, null);
    }

    public int deleteStockCount(String sID) {
        checkDB(true);
        String where1 = P_StockCount.ID + "='" + sID + "'";
        return db.delete(P_StockCount.TableName, where1, null);
    }

    public int deleteStockCountAll() {
        checkDB(true);
        return db.delete(P_StockCount.TableName, null, null);
    }

    public int deleteDelivery(String sID) {
        checkDB(true);
        String where1 = P_Delivery.ID + "='" + sID + "'";
        return db.delete(P_Delivery.TableName, where1, null);
    }

    public int deleteDeliveryAll() {
        checkDB(true);
        return db.delete(P_Delivery.TableName, null, null);
    }

    public int deleteTransferDispatch(String sID) {
        checkDB(true);
        String where1 = P_TransferDispatch.ID + "='" + sID + "'";
        return db.delete(P_TransferDispatch.TableName, where1, null);
    }

    public int deleteTransferDispatchAll() {
        checkDB(true);
        return db.delete(P_TransferDispatch.TableName, null, null);
    }

    public int deleteTransferReceipt(String sID) {
        checkDB(true);
        String where1 = P_TransferReceipt.ID + "='" + sID + "'";
        return db.delete(P_TransferReceipt.TableName, where1, null);
    }

    public int deleteTransferReceiptAll() {
        checkDB(true);
        return db.delete(P_TransferReceipt.TableName, null, null);
    }

    public void deleteAll() {
        checkDB(true);
        db.delete(P_Product.TableName, null, null);
        db.delete(P_Order.TableName, null, null);
        db.delete(P_StockCount.TableName, null, null);
        db.delete(P_Delivery.TableName, null, null);
        db.delete(P_TransferDispatch.TableName, null, null);
        db.delete(P_TransferReceipt.TableName, null, null);
    }

    @SuppressLint("Range")
    public String get(Cursor cursor, String key) {
        String s = null;
        try {
            s = "" + cursor.getString(cursor.getColumnIndex(key));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (s.toLowerCase().equals("null")) {
            s = "";
        }
        return s;
    }


    public ArrayList<HashMap<String, Object>> cursor2Array(Cursor cursor) {
        ArrayList<HashMap<String, Object>> array = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    try {
                        String[] nameArray = cursor.getColumnNames();
                        if (nameArray != null) {
                            HashMap<String, Object> map = new HashMap<>();
                            for (int i = 0; i < nameArray.length; i++) {
                                map.put(nameArray[i], get(cursor, nameArray[i]));
                            }
                            array.add(map);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return array;
    }

    public void checkDB(boolean isWrite) {
        if (db == null) {
            if (isWrite) {
                db = this.getWritableDatabase();
            } else {
                db = this.getReadableDatabase();
            }
        } else {
            if (!db.isOpen()) {
                if (isWrite) {
                    db = this.getWritableDatabase();
                } else {
                    db = this.getReadableDatabase();
                }
            } else {
                if (isWrite) {
                    if (db.isReadOnly()) {
                        db = this.getWritableDatabase();
                    }
                }
            }
        }
    }

    public void CloseDB() {
        try {
            db.close();
            db = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}