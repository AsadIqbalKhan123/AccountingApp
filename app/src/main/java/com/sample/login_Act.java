package com.sample;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sample.MyDB.MyDatabase;
import com.sample.MyDB.UserDao;
import com.shashank.expensermanager.activities.MainActivity;
import com.shashank.expensermanager.databinding.ActivityLogin2Binding;

public class login_Act extends AppCompatActivity {

    private static final String LOG_TAG = "Login";

    String namePattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    ActivityLogin2Binding binding;
    MyDatabase myDB;
    UserDao userDao;


    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogin2Binding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        SharedPreferences prefs;
        prefs = getSharedPreferences("your_pref", MODE_PRIVATE);

        boolean login_status = prefs.getBoolean("login_status", false);

        if (login_status) {

            Log.v(LOG_TAG, "UserInfo>>User already logged in");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            this.finish();

        }


        myDB = Room.databaseBuilder(this, MyDatabase.class, "usertable")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();

        userDao = myDB.getDao();

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = binding.emailLg.getText().toString();
                String password = binding.passwordLg.getText().toString();

                if (userDao.login(userEmail, password)) {


                    startActivity(new Intent(login_Act.this, MainActivity.class)
                            .putExtra("name", userEmail));

                    finish();

                } else if (password.isEmpty() || password.length() < 3) {
                    binding.passwordLg.setError("Please Enter the Password");
                } else if (userEmail.isEmpty()) {
                    binding.emailLg.setError("UserName can not be empty !");
                } else if (userEmail.equalsIgnoreCase(userEmail)) {
                    binding.emailLg.setError("Enter the User Name correctly");

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