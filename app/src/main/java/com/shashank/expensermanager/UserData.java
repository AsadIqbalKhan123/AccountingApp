package com.shashank.expensermanager;



public class UserData  {

    String name;
    int imgName;

    public UserData(String name, int imgName) {
        this.name=name;
        this.imgName=imgName;
    }

    public UserData() {

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgName() {
        return imgName;
    }

    public void setImgName(int imgName) {
        this.imgName = imgName;
    }
}
