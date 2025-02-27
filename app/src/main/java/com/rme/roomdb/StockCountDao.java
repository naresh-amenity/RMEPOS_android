package com.rme.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StockCountDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(StockCountTable stockCountTable);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<StockCountTable> stockCountTableList);

    @Update
    void update(StockCountTable stockCountTable);

    @Update
    void update(List<StockCountTable> stockCountTableList);

    @Query("SELECT * from StockCountTable ORDER By ID Asc")
    List<StockCountTable> getAll();

    @Query("SELECT * from StockCountTable WHERE Barcode = :Barcode ORDER By ID Asc")
    List<StockCountTable> getByBarcode(String Barcode);

    @Query("DELETE FROM StockCountTable WHERE Barcode = :Barcode")
    abstract void deleteByBarcode(String Barcode);

    @Query("DELETE FROM StockCountTable WHERE ID = :ID")
    abstract void deleteByID(String ID);

    @Query("DELETE from StockCountTable")
    void deleteAll();
}