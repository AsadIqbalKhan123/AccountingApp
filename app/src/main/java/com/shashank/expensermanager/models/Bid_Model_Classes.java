package com.shashank.expensermanager.models;

import java.io.Serializable;

public class Bid_Model_Classes implements Serializable {

    String des;
    double amount;
    int status; // 1-> Accepted , 2 -> Rejected, 3->Deleted


    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
