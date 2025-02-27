package com.rme.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(OrderTable orderTable);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<OrderTable> orderTableList);

    @Update
    void update(OrderTable orderTable);

    @Update
    void update(List<OrderTable> orderTableList);

    @Query("SELECT * from OrderTable ORDER By ID Asc")
    List<OrderTable> getAll();


    @Query("DELETE FROM OrderTable WHERE Barcode = :Barcode")
    abstract void deleteByBarcode(String Barcode);

    @Query("DELETE FROM OrderTable WHERE ID = :ID")
    abstract void deleteByID(String ID);

    @Query("DELETE from OrderTable")
    void deleteAll();
}