package com.shashank.expensermanager.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.shashank.expensermanager.R;
import com.shashank.expensermanager.transactionDb.TransactionEntry;
import com.shashank.expensermanager.transactionDb.TransactionViewModel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Sample_Act extends AppCompatActivity {

    public TransactionViewModel transactionViewModel;
    private List<TransactionEntry> transactionEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_);
        transactionEntries = new ArrayList<>();

        setupViewModel();
    }

    private void genrate(List<TransactionEntry> list) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = new Document();
                    String path = getApplicationContext().getCacheDir() + "/doc.pdf";
                    PdfWriter.getInstance(document, new FileOutputStream(path));
                    document.open();
                    document.setPageSize(PageSize.A4);
                    document.addCreationDate();
                    document.newPage();
                    PdfPTable table = new PdfPTable(5);
                    table.setWidthPercentage(100.0f);
//                    transactionEntries = new ArrayList<>();
                    try {
                        for (int i = 0; i < list.size(); i++) {
                            Paragraph paragraph = new Paragraph(String.valueOf(transactionEntries.get(i)), FontFactory.getFont("Arial", 6));
                            paragraph.setAlignment(Element.ALIGN_CENTER);
                            PdfPCell cell = new PdfPCell();
                            cell.setBorder(Rectangle.BOX);
                            cell.setBorderColor(new BaseColor(255, 255, 255, 0));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);

                            cell.addElement(paragraph);
                            table.addCell(cell);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    table.completeRow();
                    document.add(table);
//                    progressDialog.dismiss();
                    document.close();

                } catch (FileNotFoundException | DocumentException e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }

    public void setupViewModel() {
        transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);

        transactionViewModel.getExpenseList().observe(this, new Observer<List<TransactionEntry>>() {
            @Override
            public void onChanged(@Nullable List<TransactionEntry> transactionEntriesFromDb) {
                Log.i("", "Actively retrieving from DB");

                transactionEntries = transactionEntriesFromDb;
                genrate(transactionEntries);
            }
        });
    }


}