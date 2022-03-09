package com.shashank.expensermanager;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.shashank.expensermanager.models.Expense;

import java.io.File;
import java.util.ArrayList;

public class PDF_Activity extends AppCompatActivity {

    private static final String TAG = "PdfCreatorActivity";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    private File pdfFile;
    ImageView imgdownload;


    ArrayList<Expense> MyList1;
    Expense expense;
    Context context;

    Expense name;
    Expense price;
    Expense category;
    Expense url;
    Expense type;
    Expense date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_);


    }

}