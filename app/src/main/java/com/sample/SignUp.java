package com.sample;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.Demo_Class;
import com.sample.MyDB.MyDatabase;
import com.sample.MyDB.UserDao;
import com.sample.MyDB.UserTable;
import com.shashank.expensermanager.databinding.ActivitySignupBinding;


public class SignUp extends AppCompatActivity {

    public static boolean isAllowed = false;

    ActivitySignupBinding binding;
    MyDatabase myDB;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myDB = Room.databaseBuilder(this, MyDatabase.class, "usertable")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();

        userDao = myDB.getDao();


        binding.emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String userName = editable.toString();
                String userEmail = editable.toString();
                if (userDao.is_taken(userEmail) && userDao.is_taken(userName)) {

                    isAllowed = false;
                    Toast.makeText(SignUp.this, "Email Already Taken ! Please Try Another ", Toast.LENGTH_SHORT).show();
                } else {
                    isAllowed = true;
                }


            }
        });

        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllowed) {
                    UserTable userTable = new UserTable(0, binding.usernameEditText.getText().toString(), binding.passwordEditText.getText().toString(), binding.emailEditText.getText().toString());
                    userDao.insertUser(userTable);
                    Demo_Class.message = binding.usernameEditText.getText().toString();

                    Toast.makeText(SignUp.this, "SuccessFully Register", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUp.this, "UserName is Already Taken ! Please Try Another", Toast.LENGTH_SHORT).show();
                }
            }


        });


        binding.gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, login_Act.class));

                finish();

            }
        });
    }
}