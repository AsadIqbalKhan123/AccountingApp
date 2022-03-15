package com.shashank.expensermanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shashank.expensermanager.transactionDb.AppDatabase;
import com.shashank.expensermanager.transactionDb.TransactionDao;
import com.shashank.expensermanager.transactionDb.TransactionEntry;

public class Register_Activity extends AppCompatActivity {

    EditText userId, name, password;
    Button register_btn, log_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);

        userId = findViewById(R.id.userID);
        name = findViewById(R.id.name_ed);
        password = findViewById(R.id.password_ed);
        register_btn = findViewById(R.id.register_btn);
        log_btn = findViewById(R.id.login_btn);

        register_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//                String nam = name.getText().toString();
////                String pass = password.getText().toString();
//
//                if (TextUtils.isEmpty(nam))
//                    name.setError("Please Enter the Name!");

//                } else if (TextUtils.isEmpty(pass)) {
//                    password.setError("Please Enter Password!");
//                    return;
//                }
                TransactionEntry transactionEntry = new TransactionEntry();
                transactionEntry.setUserId(userId.getText().toString());
                transactionEntry.setPassword(password.getText().toString());
                transactionEntry.setName(name.getText().toString());

                if (validateInput(transactionEntry)) {

                    AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
                    TransactionDao transactionDao = appDatabase.transactionDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // User Register
                            transactionDao.registerUser(transactionEntry);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(Register_Activity.this, "User Successfully Register", Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    }).start();


                } else {

                    Toast.makeText(Register_Activity.this, "Please Fill all the Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });


        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register_Activity.this, Login_Activity.class));
                finish();
            }
        });


    }

    private Boolean validateInput(TransactionEntry transactionEntry) {
        if (transactionEntry.getName().isEmpty() || transactionEntry.getPassword().isEmpty()) {

            return false;
        }

        return true;
    }
}