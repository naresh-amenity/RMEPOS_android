package com.rme.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.rme.DB.P_Product;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ProductTable productTable);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<ProductTable> productTables);

    @Update
    void update(ProductTable productTables);

    @Update
    void update(List<ProductTable> productTables);

    @Query("SELECT * from ProductTable ORDER By ID Asc")
    List<ProductTable> getProductAll();

    @Query("SELECT * from ProductTable WHERE IsPriceUpdate = '1' ORDER By ID Asc")
    List<ProductTable> getProductAllPrice();

    @Query("SELECT * from ProductTable WHERE Barcode = :Barcode ORDER By ID Asc")
    List<ProductTable> getProductByBarcode(String Barcode);

    @Query("SELECT *, COUNT(*) c FROM ProductTable GROUP BY Barcode HAVING c > 1")
//    @Query("SELECT * FROM ProductTable WHERE ID NOT IN ( SELECT MIN(rowid) FROM ProductTable GROUP BY Barcode)")
    List<ProductTable> findDuplicates();

    @Query("DELETE FROM ProductTable WHERE Barcode = :Barcode")
    abstract void deleteByBarcode(String Barcode);

    @Query("DELETE FROM ProductTable WHERE ID = :ID")
    abstract void deleteByID(String ID);

    @Query("DELETE from ProductTable")
    void deleteAll();
}