package com.rme.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TransferReceiptDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TransferReceiptTable transferReceiptTable);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<TransferReceiptTable> transferReceiptTableList);

    @Update
    void update(TransferReceiptTable transferReceiptTable);

    @Update
    void update(List<TransferReceiptTable> transferReceiptTableList);

    @Query("SELECT * from TransferReceiptTable ORDER By ID Asc")
    List<TransferReceiptTable> getAll();


    @Query("DELETE FROM TransferReceiptTable WHERE Barcode = :Barcode")
    abstract void deleteByBarcode(String Barcode);

    @Query("DELETE FROM TransferReceiptTable WHERE ID = :ID")
    abstract void deleteByID(String ID);

    @Query("DELETE from TransferReceiptTable")
    void deleteAll();
}