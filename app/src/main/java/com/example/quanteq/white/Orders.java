package com.example.quanteq.white;

import android.provider.Telephony;

/**
 * Created by Quanteq on 2/9/2018.
 */

public class Orders {
    String itemname,Table,Address,Quantity,Username;

    public Orders(){

    }
    public Orders(String itemname, String Table, String Address, String Quantity,String Username){
        this.itemname = itemname;
        this.Table = Table;
        this.Address = Address;
        this.Quantity = Quantity;
        this.Username = Username;
    }


    public String getItemname() {
        return itemname;
    }

    public String getQuantity() {
        return Quantity;
    }

    public String getUsername() {
        return Username;
    }

    public String getAddress() {
        return Address;
    }

    public String getTable() {
        return Table;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setAddress(String Address) {this.Address = Address;

    }

    public void setTable(String table) {
        this.Table = table;
    }

    public void setQuantity(String quantity) {
        this.Quantity = quantity;
    }
}
