package com.shashank.expensermanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lusfold.spinnerloading.SpinnerLoading;

public class Proposition_Activity extends AppCompatActivity {

    SpinnerLoading spl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposition_);

        SpinnerLoading view = findViewById(R.id.spinner_loading);
        view.setPaintMode(1);
        view.setCircleRadius(30);
        view.setItemCount(8);


    }
}