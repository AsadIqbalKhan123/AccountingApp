package com.shashank.expensermanager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Report_Activity extends AppCompatActivity {

    // constant code for runtime permissions
    private static final int PERMISSION_REQUEST_CODE = 200;
    Button generatePDFbtn, info;
    // declaring width and height
    // for our PDF file.
    int pageHeight = 1120;
    int pagewidth = 790;
    // creating a bitmap variable
    // for storing our images
    Bitmap bmp, scaledbmp;
    ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_);

//        info = findViewById(R.id.info_id);

//        info.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });

        Toolbar toolbar = findViewById(R.id.toolbaR);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();

            }
        });

        // initializing our variables.
        generatePDFbtn = findViewById(R.id.idBtnGeneratePDF);


        bmp = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.chart1);


        scaledbmp = Bitmap.createScaledBitmap(bmp, 800, 880, false);

//        bmp = Bitmap.createScaledBitmap(scaledbmp, 600, 400, false);


        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }

        generatePDFbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generatePDF();
            }

        });
    }

    private void generatePDF() {

        Intent intent = getIntent();
        String message = intent.getStringExtra("message");


        PdfDocument pdfDocument = new PdfDocument();

//        ExpenseFragment expenseFragment = new ExpenseFragment();

        // two variables for paint "paint" is used
        // for drawing shapes and we will use "title"
        // for adding text in our PDF file.
        Paint paint = new Paint();
        Paint title = new Paint();

        // we are adding page info to our PDF file
        // in which we will be passing our pageWidth,
        // pageHeight and number of pages and after that
        // we are calling it to create our PDF.
        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();


        // below line is used for setting
        // start page for our PDF file.
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        // creating a variable for canvas
        // from our page of PDF.
        Canvas canvas = myPage.getCanvas();

        // below line is used to draw our image on our PDF file.
        // the first parameter of our drawbitmap method is
        // our bitmap
        // second parameter is position from left
        // third parameter is position from top and last
        // one is our variable for paint.
        canvas.drawBitmap(scaledbmp, 56, 40, paint);

        // below line is used for adding typeface for
        // our text which we will be adding in our PDF file.
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // below line is used for setting text size
        // which we will be displaying in our PDF file.
        title.setTextSize(17);

        // below line is sued for setting color
        // of our text inside our PDF file.
        title.setColor(ContextCompat.getColor(getApplicationContext(), R.color.sky_blue));

        // below line is used to draw text in our PDF file.
        // the first parameter is our text, second parameter
        // is position from start, third parameter is position from top
        // and then we are passing our variable of paint which is title.


//        title.setTextAlign(Paint.Align.LEFT);
//        canvas.drawText("Report Generated ", 309, 580, title);


        // similarly we are creating another text and in this
        // we are aligning this text to center of our PDF file.
        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        title.setColor(ContextCompat.getColor(getApplicationContext(), R.color.sky_blue));
        title.setTextSize(18);

        // below line is used for setting
        // our text to center of PDF.
        title.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("This is PDF document which we have Generate.", 400, 960, title);


        // after adding all attributes to our
        // PDF file we will be finishing our page.
        pdfDocument.finishPage(myPage);

//         below line is used to set the name of
//         our PDF file and its path.
        File file = new File(Environment.getExternalStorageDirectory(), "Report.pdf");

        try {
            // after creating a file name we will
            // write our PDF file to that location.
            pdfDocument.writeTo(new FileOutputStream(file));

            // below line is to print toast message
            // on completion of PDF generation.
            Toast.makeText(Report_Activity.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // below line is used
            // to handle error
            e.printStackTrace();
        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        pdfDocument.close();


    }

    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(Report_Activity.this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        {

            if (requestCode == PERMISSION_REQUEST_CODE) {
                if (grantResults.length > 0) {

                    // after requesting permissions we are showing
                    // users a toast message of permission granted.
                    boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (writeStorage && readStorage) {
                        Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        }
    }


}
