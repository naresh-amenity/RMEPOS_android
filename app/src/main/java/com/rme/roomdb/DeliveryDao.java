package com.rme.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DeliveryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DeliveryTable deliveryTable);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<DeliveryTable> deliveryTableList);

    @Update
    void update(DeliveryTable deliveryTable);

    @Update
    void update(List<DeliveryTable> deliveryTableList);

    @Query("SELECT * from DeliveryTable ORDER By ID Asc")
    List<DeliveryTable> getAll();


    @Query("DELETE FROM DeliveryTable WHERE Barcode = :Barcode")
    abstract void deleteByBarcode(String Barcode);

    @Query("DELETE FROM DeliveryTable WHERE ID = :ID")
    abstract void deleteByID(String ID);

    @Query("DELETE from DeliveryTable")
    void deleteAll();
}