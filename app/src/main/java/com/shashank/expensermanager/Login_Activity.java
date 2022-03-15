package com.shashank.expensermanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.expensermanager.activities.MainActivity;
import com.shashank.expensermanager.transactionDb.AppDatabase;
import com.shashank.expensermanager.transactionDb.TransactionDao;
import com.shashank.expensermanager.transactionDb.TransactionEntry;

public class Login_Activity extends AppCompatActivity {

    EditText userId, passowrd;
    Button login_btn;
    TextView reg_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        userId = findViewById(R.id.userId_edit_);
        passowrd = findViewById(R.id.password_edit_text);
        login_btn = findViewById(R.id.login_button);

        reg_txt = findViewById(R.id.signUp_btn2);

        reg_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Login_Activity.this, Register_Activity.class));


            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userIdText = userId.getText().toString();
                String passwordText = passowrd.getText().toString();
                if (userIdText.isEmpty() || passwordText.isEmpty()) {

                    Toast.makeText(Login_Activity.this, "Please Fill all Fields", Toast.LENGTH_SHORT).show();

                } else {
                    AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
                    TransactionDao transactionDao = appDatabase.transactionDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            TransactionEntry transactionEntry = transactionDao.login(userIdText, passwordText);
                            if (transactionEntry == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Login_Activity.this, "Invalid Data Please Try Again!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {

                                String name = transactionEntry.name;
                                startActivity(new Intent(Login_Activity.this, MainActivity.class)
                                        .putExtra("name", name));

                            }

                        }
                    }).start();

                }

            }
        });

    }
}