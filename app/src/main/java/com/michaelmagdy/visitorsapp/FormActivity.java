package com.michaelmagdy.visitorsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FormActivity extends AppCompatActivity {

    private EditText nameEdt, emailEdt, mobileEdt, addressEdt;
    private Spinner empSpn, purpSpn;
    private Button sendOtpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        initViews();
        sendOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameEdt.getText().toString();
                String email = emailEdt.getText().toString();
                String mobile = mobileEdt.getText().toString();
                String address = addressEdt.getText().toString();
                String hostEmp = empSpn.getSelectedItem().toString();
                String purpOfVisit = purpSpn.getSelectedItem().toString();

                if (name.isEmpty() || email.isEmpty() ||
                    mobile.isEmpty() || address.isEmpty() ||
                    hostEmp.isEmpty() || purpOfVisit.isEmpty()){

                    Toast.makeText(FormActivity.this,
                            getString(R.string.all_req), Toast.LENGTH_LONG).show();
                } else {
                    Bundle visitor = new Bundle();
                    visitor.putString("name", name);
                    visitor.putString("email", email);
                    visitor.putString("mobile", mobile);
                    visitor.putString("address", address);
                    visitor.putString("hostEmp", hostEmp);
                    visitor.putString("purpOfVisit", purpOfVisit);
                    Intent intent = new Intent(FormActivity.this, OtpActivity.class);
                    intent.putExtra("visitor", visitor);
                    startActivity(intent);
                }
            }
        });
    }

    private void initViews() {

        nameEdt = findViewById(R.id.name_edt);
        emailEdt = findViewById(R.id.email_edt);
        mobileEdt = findViewById(R.id.mobile_edt);
        addressEdt = findViewById(R.id.address_edt);
        empSpn = findViewById(R.id.emp_spn);
        purpSpn = findViewById(R.id.purp_spn);
        sendOtpBtn = findViewById(R.id.send_otp_btn);

        String[] puposes = getResources().getStringArray(R.array.purposes_array);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                puposes);
        purpSpn.setAdapter(adapter);

        //TODO: POPULATE EMPSPN WITH DATA FROM API
        String[] emps = {"emp 1", "emp 2", "emp 3", "emp 4", "emp 5"};
        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                emps);
        empSpn.setAdapter(adapter);
    }
}