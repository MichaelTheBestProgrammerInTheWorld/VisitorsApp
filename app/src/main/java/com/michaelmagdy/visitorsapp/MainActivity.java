package com.michaelmagdy.visitorsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button bookBtn;
    private boolean hasLocation = false;
    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = findViewById(R.id.txt);
        bookBtn = findViewById(R.id.book_btn);
        if (getIntent().hasExtra("cityName")) {
            cityName = getIntent().getStringExtra("cityName");

            hasLocation = true;
            textView.setText(getString(R.string.welcome) + "   \n" + cityName);
            bookBtn.setEnabled(true);

        }


        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasLocation) {

                    goToFormActivity(cityName);
                }

            }
        });
    }

    private void goToFormActivity(String cityName) {

        Intent intent = new Intent(this, FormActivity.class);
        intent.putExtra("cityName", cityName);
        startActivity(intent);
    }


}