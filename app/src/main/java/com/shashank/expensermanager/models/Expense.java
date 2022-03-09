package com.shashank.expensermanager.models;

import android.icu.util.ULocale;

import java.io.Serializable;
import java.util.Date;

public class Expense implements Serializable {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ULocale.Category getCategory() {
        return category;
    }

    public void setCategory(ULocale.Category category) {
        this.category = category;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    private String id;

    private String description;
    private Date date;
    private int type;
    private ULocale.Category category;
    private float total;



}
