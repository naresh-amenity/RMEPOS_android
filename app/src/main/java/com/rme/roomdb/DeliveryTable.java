package com.rme.roomdb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "DeliveryTable")
public class DeliveryTable {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    String Barcode;
    String Description;
    String DeliveryDate;
    String OrderNumber;
    String DeliveryNumber;
    String DeliveryQty;
    String BranchID;
    String CreateAt;

    public DeliveryTable() {
        this.Barcode = "";
        this.Description = "";
        this.DeliveryDate = "";
        this.OrderNumber = "";
        this.DeliveryNumber = "";
        this.DeliveryQty = "";
        this.BranchID = "";
        this.CreateAt = "";
    }
    public DeliveryTable(String Barcode, String Description, String DeliveryDate,  String OrderNumber,  String DeliveryNumber,  String DeliveryQty,  String BranchID, String CreateAt) {
        this.Barcode = Barcode;
        this.Description = Description;
        this.DeliveryDate = DeliveryDate;
        this.OrderNumber = OrderNumber;
        this.DeliveryNumber = DeliveryNumber;
        this.DeliveryQty = DeliveryQty;
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

    public String getDeliveryDate() {
        return DeliveryDate;
    }
    public void setDeliveryDate(String DeliveryDate) {
        this.DeliveryDate = DeliveryDate;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }
    public void setOrderNumber(String OrderNumber) {
        this.OrderNumber = OrderNumber;
    }

    public String getDeliveryNumber() {
        return DeliveryNumber;
    }
    public void setDeliveryNumber(String DeliveryNumber) {
        this.DeliveryNumber = DeliveryNumber;
    }

    public String getDeliveryQty() {
        return DeliveryQty;
    }
    public void setDeliveryQty(String DeliveryQty) {
        this.DeliveryQty = DeliveryQty;
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