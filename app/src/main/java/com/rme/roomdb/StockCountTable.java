package com.rme.roomdb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "StockCountTable")
public class StockCountTable {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    String Barcode;
    String Description;
    String StockCount;
    String CreateAt;

    public StockCountTable() {
        this.Barcode = "";
        this.Description = "";
        this.StockCount = "";
        this.CreateAt = "";
    }
    public StockCountTable(String Barcode, String Description, String StockCount, String CreateAt) {
        this.Barcode = Barcode;
        this.Description = Description;
        this.StockCount = StockCount;
        this.CreateAt = CreateAt;
    }
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public String getBarcode() {
        return Barcode;
    }
    public void setBarcode(String Barcode) {
        this.Barcode = Barcode;
    }

    public String getDescription() {
        return Description;
    }
    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getStockCount() {
        return StockCount;
    }
    public void setStockCount(String StockCount) {
        this.StockCount = StockCount;
    }

    public String getCreateAt() {
        return CreateAt;
    }
    public void setCreateAt(String CreateAt) {
        this.CreateAt = CreateAt;
    }
}