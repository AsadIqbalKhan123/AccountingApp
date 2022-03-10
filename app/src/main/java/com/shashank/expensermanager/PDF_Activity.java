package com.shashank.expensermanager;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.shashank.expensermanager.models.Expense;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PDF_Activity extends AppCompatActivity {

    private static final String TAG = "PdfCreatorActivity";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
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
    Button btn_pdf;
    private File pdfFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_);

        btn_pdf = findViewById(R.id.print_and_Gen);


        btn_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openPdf();

            }
        });


//
//        // in below  code i used libibaray you can check in gradle files .......
////        implementation 'com.uttampanchasara.pdfgenerator:pdfgenerator:1.3'
//
//        btn_pdf.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                new CreatePdf(view.getContext())
//                        .setPdfName("FirstPdf")
//                        .openPrintDialog(false)
//                        .setContentBaseUrl(null)
//                        .setPageSize(PrintAttributes.MediaSize.ISO_A4)
//                        .setContent("")   // yaha par samjh nhi a rha kya ay ga and kesy recycler view ay ga and us ka data show hoga ......
//                        .setFilePath(Environment.getExternalStorageDirectory() + "/FilZZ")
//                        .setCallbackListener(new CreatePdf.PdfCallbackListener() {
//                            @Override
//                            public void onFailure(@NotNull String s) {
//                                // handle error
//                            }
//
//                            @Override
//                            public void onSuccess(@NotNull String s) {
//                                // do your stuff here
////                        PdfDocument pdfDocument = new PdfDocument();
////
////                        File file = new File(Environment.getExternalStorageDirectory(), "Report.pdf");
////
////                        try {
////                            // after creating a file name we will
////                            // write our PDF file to that location.
////                            pdfDocument.writeTo(new FileOutputStream(file));
////
////                            // below line is to print toast message
////                            // on completion of PDF generation.
////                            Toast.makeText(PDF_Activity.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
////                        } catch (IOException e) {
////                            // below line is used
////                            // to handle error
////                            e.printStackTrace();
////                        }
//
//                            }
//                        })
//                        .create();
//
//            }
//        });


    }

    private void openPdf() {
        /**
         * Creating Document
         */
        Document document = new Document();
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "Repoo.pdf");

// Location to save
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

// Open to write
        document.open();

        // Document Settings
        document.setPageSize(PageSize.A4);
        document.addCreationDate();
        document.addAuthor("Android School");
        document.addCreator("ASAD IQBAL ");

        BaseColor mColorAccent = new BaseColor(0, 153, 204, 255);
        float mHeadingFontSize = 20.0f;
        float mValueFontSize = 26.0f;

        // LINE SEPARATOR
        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));


        BaseFont urName = null;
        try {
            urName = BaseFont.createFont("assets/your font.ttf", "UTF-8", BaseFont.EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Title Order Details...
// Adding Title....
        Font mOrderDetailsTitleFont = new Font(urName, 36.0f, Font.NORMAL, BaseColor.BLACK);
// Creating Chunk
        Chunk mOrderDetailsTitleChunk = new Chunk("User Details", mOrderDetailsTitleFont);
// Creating Paragraph to add...
        Paragraph mOrderDetailsTitleParagraph = new Paragraph(mOrderDetailsTitleChunk);
// Setting Alignment for Heading
        mOrderDetailsTitleParagraph.setAlignment(Element.ALIGN_CENTER);
// Finally Adding that Chunk
        try {
            document.add(mOrderDetailsTitleParagraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

// Fields of Order Details...
// Adding Chunks for Title and value
        Font mOrderIdFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);
        Chunk mOrderIdChunk = new Chunk("Record No:", mOrderIdFont);
        Paragraph mOrderIdParagraph = new Paragraph(mOrderIdChunk);
        try {
            document.add(mOrderIdParagraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        try {
            document.add(new Paragraph(""));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        try {
            document.add(new Chunk(lineSeparator));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        try {
            document.add(new Paragraph(""));
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document.close();

    }

}