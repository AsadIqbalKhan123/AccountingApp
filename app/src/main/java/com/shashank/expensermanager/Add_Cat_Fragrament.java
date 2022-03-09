package com.shashank.expensermanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shashank.expensermanager.activities.Cat_Model_Class;

import java.util.ArrayList;
import java.util.List;

public class Add_Cat_Fragrament extends Fragment implements ItemAdapator.OnNoteListener {

    CardView cardView;

    List<Cat_Model_Class> model1List;
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_add__cat__fragrament, container, false);


        recyclerView = view.findViewById(R.id.recyclerView_1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


//        initData();

//        itemAdapator itemAdapator = new itemAdapator(Ca);

        recyclerView.setAdapter(new ItemAdapator(initData(), this, getContext()));


        return view;
    }

    private List<Cat_Model_Class> initData() {
        model1List = new ArrayList<>();
        model1List.add(new Cat_Model_Class(R.drawable.ux, "Category Name"));
        model1List.add(new Cat_Model_Class(R.drawable.ux, "Category Name"));
        model1List.add(new Cat_Model_Class(R.drawable.ic_baseline_category_, "Category Icon"));


        return model1List;

    }

    @Override
    public void onNoteClick(int postion) {

        Intent intent = new Intent(getContext(), Demo_Activity.class);

        startActivity(intent);

    }
}