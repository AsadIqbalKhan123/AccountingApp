package com.shashank.expensermanager.activities;

import java.io.Serializable;

public class Cat_Model_Class implements Serializable {

    public Cat_Model_Class(int image, String name) {
        this.image = image;
        this.name = name;
    }

    int image;
    String name;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
