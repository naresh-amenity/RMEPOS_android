package com.rme.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TransferDispatchDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TransferDispatchTable transferDispatchTable);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<TransferDispatchTable> transferDispatchTableList);

    @Update
    void update(TransferDispatchTable transferDispatchTable);

    @Update
    void update(List<TransferDispatchTable> transferDispatchTableList);

    @Query("SELECT * from TransferDispatchTable ORDER By ID Asc")
    List<TransferDispatchTable> getAll();


    @Query("DELETE FROM TransferDispatchTable WHERE Barcode = :Barcode")
    abstract void deleteByBarcode(String Barcode);

    @Query("DELETE FROM TransferDispatchTable WHERE ID = :ID")
    abstract void deleteByID(String ID);

    @Query("DELETE from TransferDispatchTable")
    void deleteAll();
}