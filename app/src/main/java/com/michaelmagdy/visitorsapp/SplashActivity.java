package com.michaelmagdy.visitorsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.michaelmagdy.visitorsapp.viewmodel.SplashActivtyViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    private int PERMISSION_ID = 44;
    private ProgressBar progressBar;
    private TextView textView;
    private SplashActivtyViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (requestPermissions()){
            //viewModel = new  ViewModelProvider(this).get(SplashActivtyViewModel.class);
        }

        progressBar = findViewById(R.id.splash_progressBar);
        textView = findViewById(R.id.splash_textView);


        if (checkPermissions()) {

            viewModel = new  ViewModelProvider(this).get(SplashActivtyViewModel.class);
            // check if location is enabled
            if (isLocationEnabled()) {

                observeLocation();

            } else {
                textView.setText(getString(R.string.location_turn_on));
                progressBar.setVisibility(View.GONE);
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }

    }

    // method to request for permissions
    private boolean requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);

        return true;
    }



    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }



    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //getLastLocation();
                observeLocation();
            } else {
                textView.setText(getString(R.string.location_permission));
                progressBar.setVisibility(View.GONE);

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            progressBar.setVisibility(View.VISIBLE);
            textView.setText(getString(R.string.splash_intro));
            //getLastLocation();
            //observeLocation();
        }
    }

    private void observeLocation(){

        if (viewModel != null){
            viewModel.getCityNameLiveData().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    //Toast.makeText(SplashActivity.this, "LiveData : " + s, Toast.LENGTH_SHORT).show();
                    goToMainActivity(s);
                }
            });
        } else {

            //TODO: RESTART THE APP
            Toast.makeText(this, getString(R.string.restart), Toast.LENGTH_SHORT).show();
        }
    }

    private void goToMainActivity(String cityName) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("cityName", cityName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}