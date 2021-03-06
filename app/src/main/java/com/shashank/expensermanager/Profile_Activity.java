package com.shashank.expensermanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.Demo_Class;


public class Profile_Activity extends AppCompatActivity {

    ImageView imageView1;
    AppCompatImageView appCompatImageView;
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);

//        imageButton = findViewById(R.id.add_imge_btn);
        appCompatImageView = findViewById(R.id.imgProfile);
        imageView1 = findViewById(R.id.back_press);
        floatingActionButton = findViewById(R.id.fab_add_photo);


        String name = getIntent().getStringExtra("person");
        EditText userName = findViewById(R.id.userEmail_textview);
        userName.setText(name);

        userName.setEnabled(false);
        userName.setFocusable(false);

        EditText userName1 = findViewById(R.id.user_textview);
        userName1.setText(Demo_Class.message);
        userName1.setEnabled(false);
        userName1.setFocusable(false);


//        userName.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//
//
////                String myName = userName.getText().toString();
//                view.setEnabled(true);
//                view.setFocusable(true);
////                userName.setText(myName);
//
//                return false;
//            }
//        });
//
//        String name1 = getIntent().getStringExtra("table");
//
//            @SuppressLint("CutPasteId") TextView userName1 = findViewById(R.id.user_textview);
//            userName1.setText(name1);


        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();


            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chooseProfilePicture();
            }
        });
    }


    private void chooseProfilePicture() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile_Activity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_profile_picture, null);
        builder.setCancelable(false);
        builder.setView(dialogView);

        ImageView imageViewADPPCamera = dialogView.findViewById(R.id.camera_view);
        ImageView imageViewADPPGallery = dialogView.findViewById(R.id.gallery_view);


        final AlertDialog alertDialogProfilePicture = builder.create();
        {
            alertDialogProfilePicture.show();

            imageViewADPPCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkAndRequestPermissions()) {
                        takePictureFromCamera();
                        alertDialogProfilePicture.dismiss();
                    }
                }
            });
            imageViewADPPGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    takePictureFromGallery();
                    alertDialogProfilePicture.dismiss();
                }
            });
            alertDialogProfilePicture.setCanceledOnTouchOutside(true);
        }
    }

    private void takePictureFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);
    }

    private void takePictureFromCamera() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePicture, 2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImageUri = data.getData();
                    appCompatImageView.setImageURI(selectedImageUri);
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    Bitmap bitmapImage = (Bitmap) bundle.get("data");
                    appCompatImageView.setImageBitmap(bitmapImage);
                }
                break;
        }
    }

    private boolean checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            int cameraPermission = ActivityCompat.checkSelfPermission(Profile_Activity.this, Manifest.permission.CAMERA);
            if (cameraPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(Profile_Activity.this, new String[]{Manifest.permission.CAMERA}, 20);
                return false;
            }
        }
        return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 20 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takePictureFromCamera();
        } else
            Toast.makeText(this, "Permission not Granted", Toast.LENGTH_SHORT).show();
    }


}




