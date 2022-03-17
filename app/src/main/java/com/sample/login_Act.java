package com.sample;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.sample.MyDB.MyDatabase;
import com.sample.MyDB.UserDao;
import com.shashank.expensermanager.activities.MainActivity;
import com.shashank.expensermanager.databinding.ActivityLogin2Binding;

public class login_Act extends AppCompatActivity {

    ActivityLogin2Binding binding;
    MyDatabase myDB;
    UserDao userDao;


    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogin2Binding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        myDB = Room.databaseBuilder(this, MyDatabase.class, "usertable")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();

        userDao = myDB.getDao();

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = binding.usernameLg.getText().toString();
                String password = binding.passwordLg.getText().toString();

                if (userDao.login(userName, password)) {


                    startActivity(new Intent(login_Act.this, MainActivity.class)
                            .putExtra("name", userName)
                            .putExtra("names", userName));
                    finish();

                } else {
                    if (userName.isEmpty() && password.isEmpty()) {
                        binding.usernameLg.setError("Please Enter the UserName");
                        binding.passwordLg.setError("Please Enter the Password");
                        Toast.makeText(login_Act.this, "Invalid UserName or Password", Toast.LENGTH_SHORT).show();
                    }
                }
                // yeh bad mai kro gaa yaaad say !!1
            }

        });


        binding.gotosignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login_Act.this, SignUp.class));
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}