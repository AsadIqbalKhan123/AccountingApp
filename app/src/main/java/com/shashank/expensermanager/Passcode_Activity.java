package com.shashank.expensermanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hanks.passcodeview.PasscodeView;
import com.shashank.expensermanager.activities.MainActivity;

public class Passcode_Activity extends AppCompatActivity {

    PasscodeView passcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode_);

        passcodeView = findViewById(R.id.passcodeView);

        passcodeView.setPasscodeLength(5)
                .setLocalPasscode("00000")
                .setListener(new PasscodeView.PasscodeViewListener() {
                    @Override
                    public void onFail() {
                        Toast.makeText(Passcode_Activity.this, "Please Enter Correct Password", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(String number) {

                        startActivity(new Intent(Passcode_Activity.this, MainActivity.class));
                        finish();

                    }
                });

    }
}