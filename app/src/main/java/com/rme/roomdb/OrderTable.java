package com.rme.roomdb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "OrderTable")
public class OrderTable {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    String Barcode;
    String Description;
    String OrderNumber;
    String OrderQty;
    String OrderDate;
    String BranchID;
    String CreateAt;

    public OrderTable() {
        this.Barcode = "";
        this.Description = "";
        this.OrderNumber = "";
        this.OrderQty = "";
        this.OrderDate = "";
        this.BranchID = "";
        this.CreateAt = "";
    }
    public OrderTable(String Barcode, String Description, String OrderNumber, String OrderQty, String OrderDate, String BranchID, String CreateAt) {
        this.Barcode = Barcode;
        this.Description = Description;
        this.OrderNumber = OrderNumber;
        this.OrderQty = OrderQty;
        this.OrderDate = OrderDate;
        this.BranchID = BranchID;
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

    public String getOrderNumber() {
        return OrderNumber;
    }
    public void setOrderNumber(String OrderNumber) {
        this.OrderNumber = OrderNumber;
    }

    public String getOrderQty() {
        return OrderQty;
    }
    public void setOrderQty(String OrderQty) {
        this.OrderQty = OrderQty;
    }

    public String getOrderDate() {
        return OrderDate;
    }
    public void setOrderDate(String OrderDate) {
        this.OrderDate = OrderDate;
    }

    public String getBranchID() {
        return BranchID;
    }
    public void setBranchID(String BranchID) {
        this.BranchID = BranchID;
    }

    public String getCreateAt() {
        return CreateAt;
    }
    public void setCreateAt(String CreateAt) {
        this.CreateAt = CreateAt;
    }
}