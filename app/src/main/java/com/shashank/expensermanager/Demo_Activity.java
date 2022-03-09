package com.shashank.expensermanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Demo_Activity extends AppCompatActivity {

    private static final String TAG = "Demo_Activity";
    ArrayList<UserData> list = new ArrayList<>();
    Gson gson;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText et_name;
    Button add;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_);
        sharedPreferences = getSharedPreferences("Category", MODE_PRIVATE);
        loadCategory();
        et_name = (EditText) findViewById(R.id.et_name);
        add = findViewById(R.id.btn_add);
        button = findViewById(R.id.Save_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = et_name.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    et_name.setError("Please Enter the Name!");
                    return;
                }
                addCategory(s, 0);
                startActivity(new Intent(Demo_Activity.this, Add_Category.class));
                finish();
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar00);
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
        list = gson.fromJson(json, type);

        if (list == null) {

            list = new ArrayList<UserData>();
        }
    }

    public void addCategory(String name, int imgName) {
        sharedPreferences = getSharedPreferences("Category", MODE_PRIVATE);
        list.add(new UserData(name, imgName));
        gson = new Gson();
        String json = gson.toJson(list);
        editor = sharedPreferences.edit();
        editor.putString("category", json);
        editor.apply();
    }
}