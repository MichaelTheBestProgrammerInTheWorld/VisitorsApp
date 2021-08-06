package com.michaelmagdy.visitorsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OtpActivity extends AppCompatActivity {

    private EditText otpEdt;
    private TextView countDownTxt;
    private Button resendOtpBtn, nextBtn;
    private Bundle visitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otpEdt = findViewById(R.id.otp_edt);
        countDownTxt = findViewById(R.id.countdown_txt);
        resendOtpBtn = findViewById(R.id.resend_btn);
        nextBtn = findViewById(R.id.next_btn);

        startCountDown();

        if (getIntent().hasExtra("visitor")){
            visitor = getIntent().getBundleExtra("visitor");
        }

        resendOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCountDown();
                resendOtpBtn.setEnabled(false);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String otp = otpEdt.getText().toString();
                if (otp.isEmpty()){
                    Toast.makeText(OtpActivity.this,
                            getString(R.string.all_req), Toast.LENGTH_LONG).show();
                } else {

                    Intent intent = new Intent(OtpActivity.this, TakePicActivity.class);
                    intent.putExtra("visitor", visitor);
                    startActivity(intent);
                }
            }
        });
    }

    private void startCountDown() {

        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                countDownTxt.setText(" " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                resendOtpBtn.setEnabled(true);
            }

        }.start();
    }
}