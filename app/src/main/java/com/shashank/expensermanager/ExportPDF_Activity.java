package com.shashank.expensermanager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExportPDF_Activity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 10;
    private static final String TAG = "myapp";
    private static final int CREATE_FILE = 1;
    Button btn_create;
    int pdfHeight = 1080;
    int pdfWidth = 720;
    private PdfDocument document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_p_d_f__acitivity);


        Toolbar toolbar = findViewById(R.id.toolbar_pdf);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();

            }
        });


    }

    public void createPdfFromView(View view) {

        final Dialog invoicedialog = new Dialog(this);
        invoicedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        invoicedialog.setContentView(R.layout.zigzag_layout);
        invoicedialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(invoicedialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        invoicedialog.getWindow().setAttributes(lp);
        Button downlondinvoiceBtn = invoicedialog.findViewById(R.id.print_and_Gen1);
        invoicedialog.show();

        downlondinvoiceBtn.setOnClickListener(view1 -> {
            generatePdfFromView(invoicedialog.findViewById(R.id.zigzagView));
        });

    }

    public void generatePdfFromView(View view) {

        Bitmap bitmap = getBitmapFromView(view);
        document = new PdfDocument();
        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        PdfDocument.Page mypage = document.startPage(mypageInfo);
        Canvas canvas = mypage.getCanvas();
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(mypage);
        createFile();

    }

    private void createFile() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE, "invoice.pdf");
        startActivityForResult(intent, CREATE_FILE);

    }

    private Bitmap getBitmapFromView(View view) {

        Bitmap returneBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returneBitmap);

        Drawable bgDrawable = view.getBackground();

        if (bgDrawable != null) {

            bgDrawable.draw(canvas);

        } else {
            canvas.drawColor(Color.WHITE);
        }

        view.draw(canvas);
        return returneBitmap;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        if (requestCode == CREATE_FILE &&
                resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                if (document != null) {
                    ParcelFileDescriptor pdff = null;
                    try {
                        assert uri != null;
                        pdff = getContentResolver().openFileDescriptor(uri, "w");

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(pdff.getFileDescriptor());
                    try {
                        document.writeTo(fileOutputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    document.close();
                    Toast.makeText(this, "PDF is Successfully Generated ", Toast.LENGTH_SHORT).show();

                }

            }
        }
    }
}