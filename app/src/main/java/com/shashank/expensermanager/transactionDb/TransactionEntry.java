package com.shashank.expensermanager.transactionDb;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "transactionTable", indices = {@Index(value = {"userId"}, unique = true)})
public class TransactionEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int amount;
    private String category;
    private String description;
    private Date date;                  // COMPLETED: 13-09-2018 Add appropriate type converter
    private String transactionType; //to decide whether income or expense


    public TransactionEntry(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    @ColumnInfo(name = "userId")
    String userId;

    @ColumnInfo(name = "password")
    String password;

    @ColumnInfo(name = "name")
    public
    String name;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }









    @Ignore
    public TransactionEntry(int amount,String category,String description,Date date,String transactionType){
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
        this.transactionType=transactionType;
    }


    public TransactionEntry(int id,int amount,String category,String description,Date date,String transactionType){
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
        this.transactionType=transactionType;
    }

    public TransactionEntry()
    {

    }



    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}