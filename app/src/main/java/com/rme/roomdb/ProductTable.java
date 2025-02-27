package com.rme.roomdb;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ProductTable")
public class ProductTable {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    String Barcode;
    String Description;
    String Stock;
    String Price1;
    String Price2;
    String NewPrice1;
    String NewPrice2;
    String CreateAt;
    String IsPriceUpdate;
    public ProductTable() {
        this.Barcode = "";
        this.Description = "";
        this.Stock = "";
        this.Price1 = "";
        this.Price2 = "";
        this.NewPrice1 = "";
        this.NewPrice2 = "";
        this.CreateAt = "";
        this.IsPriceUpdate = "";
    }
    public ProductTable(String Barcode, String Description, String Stock, String Price1, String Price2, String NewPrice1, String NewPrice2, String CreateAt,String IsPriceUpdate) {
        this.Barcode = Barcode;
        this.Description = Description;
        this.Stock = Stock;
        this.Price1 = Price1;
        this.Price2 = Price2;
        this.NewPrice1 = NewPrice1;
        this.NewPrice2 = NewPrice2;
        this.CreateAt = CreateAt;
        this.IsPriceUpdate = IsPriceUpdate;
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

    public String getStock() {
        return Stock;
    }

    public void setStock(String Stock) {
        this.Stock = Stock;
    }

    public String getPrice1() {
        return Price1;
    }

    public void setPrice1(String Price1) {
        this.Price1 = Price1;
    }
    public String getPrice2() {
        return Price2;
    }

    public void setPrice2(String Price2) {
        this.Price2 = Price2;
    }
    public String getNewPrice1() {
        return NewPrice1;
    }

    public void setNewPrice1(String NewPrice1) {
        this.NewPrice1 = NewPrice1;
    }
    public String getNewPrice2() {
        return NewPrice2;
    }

    public void setNewPrice2(String NewPrice2) {
        this.NewPrice2 = NewPrice2;
    }
    public String getCreateAt() {
        return CreateAt;
    }

    public void setCreateAt(String CreateAt) {
        this.CreateAt = CreateAt;
    }
    public String getIsPriceUpdate() {
        return IsPriceUpdate;
    }

    public void setIsPriceUpdate(String IsPriceUpdate) {
        this.IsPriceUpdate = IsPriceUpdate;
    }
}