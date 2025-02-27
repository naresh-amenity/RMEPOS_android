package com.rme.roomdb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TransferDispatchTable")
public class TransferDispatchTable {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    String Barcode;
    String Description;
    String DispatchDate;
    String TransferNumber;
    String TransferQty;
    String BranchID;
    String CreateAt;
    public TransferDispatchTable() {
        this.Barcode = "";
        this.Description = "";
        this.DispatchDate = "";
        this.TransferNumber = "";
        this.TransferQty = "";
        this.BranchID = "";
        this.CreateAt = "";
    }
    public TransferDispatchTable(String Barcode, String Description, String DispatchDate, String TransferNumber, String TransferQty, String BranchID, String CreateAt) {
        this.Barcode = Barcode;
        this.Description = Description;
        this.DispatchDate = DispatchDate;
        this.TransferNumber = TransferNumber;
        this.TransferQty = TransferQty;
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

    public String getDispatchDate() {
        return DispatchDate;
    }
    public void setDispatchDate(String DispatchDate) {
        this.DispatchDate = DispatchDate;
    }

    public String getTransferNumber() {
        return TransferNumber;
    }
    public void setTransferNumber(String TransferNumber) {
        this.TransferNumber = TransferNumber;
    }

    public String getTransferQty() {
        return TransferQty;
    }
    public void setTransferQty(String TransferQty) {
        this.TransferQty = TransferQty;
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