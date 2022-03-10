package com.shashank.expensermanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.pdf.PdfWriter;
import com.shashank.expensermanager.adapters.AddCategoryAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Add_Category extends AppCompatActivity {


    private static final String TAG = "Add_Category";

    FloatingActionButton floatingActionButtonbutton;
    Gson gson;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;
    AddCategoryAdapter adapter;
    ArrayList<UserData> Mylist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__category);
        sharedPreferences = getSharedPreferences("Category", MODE_PRIVATE);

        loadCategory();
        floatingActionButtonbutton = findViewById(R.id.fab2);
        recyclerView = findViewById(R.id.job_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AddCategoryAdapter(Mylist, getApplicationContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), String.valueOf(Mylist.size()), Toast.LENGTH_SHORT).show();
        floatingActionButtonbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Add_Category.this, Demo_Activity.class);
                startActivity(intent);
                finish();
            }
        });


        Toolbar toolbar = findViewById(R.id.toolbaR);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();

            }
        });
    }

    public void loadCategory() {
        gson = new Gson();
        String json = null;
        json = sharedPreferences.getString("category", null);
        Type type = new TypeToken<ArrayList<UserData>>() {
        }.getType();
        Mylist = gson.fromJson(json, type);

        if (Mylist == null) {

            Mylist = new ArrayList<UserData>();
        }
    }
}