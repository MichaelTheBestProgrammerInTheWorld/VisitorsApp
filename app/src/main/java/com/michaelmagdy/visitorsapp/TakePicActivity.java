package com.michaelmagdy.visitorsapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakePicActivity extends AppCompatActivity {

    private ImageView visitorPicImg;
    private Button openCameraBtn, submitBtn;
    private Bundle visitor;
    private boolean picTaken = false;
    private Uri photoUri;
    public static final int CAMERA_PHOTO_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_pic);

        visitorPicImg = findViewById(R.id.visitor_img);
        openCameraBtn = findViewById(R.id.open_camera_btn);
        submitBtn = findViewById(R.id.submit_btn);

        if (getIntent().hasExtra("visitor")){
            visitor = getIntent().getBundleExtra("visitor");
        }

        openCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                photoUri = FileProvider.getUriForFile(TakePicActivity.this, getApplicationContext().getPackageName() + ".provider", getOutputPhotoFile());
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                startActivityForResult(intent, CAMERA_PHOTO_REQUEST_CODE);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (picTaken){
                    String name = visitor.getString("name");
                    String email = visitor.getString("email");
                    String mobile = visitor.getString("mobile");
                    String address = visitor.getString("address");
                    String hostEmp = visitor.getString("hostEmp");
                    String purpOfVisit = visitor.getString("purpOfVisit");

                    Toast.makeText(TakePicActivity.this,
                            "Thanks " + name + "we will get back to you in a few minutes",
                            Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(TakePicActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(TakePicActivity.this,
                            getString(R.string.take_pic), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private File getOutputPhotoFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_PHOTO_REQUEST_CODE && resultCode == RESULT_OK){

            visitorPicImg.setImageURI(photoUri);
            picTaken = true;
        }
    }
}