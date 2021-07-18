package com.michaelmagdy.visitorsapp.viewmodel;


import android.annotation.SuppressLint;
import android.app.Application;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SplashActivtyViewModel extends AndroidViewModel {

    private FusedLocationProviderClient mFusedLocationClient;
    private int PERMISSION_ID = 44;
    private Application application;
    private String cityName = "Not Found";
    private MutableLiveData<String> cityNameLiveData = new MutableLiveData<>();

    public SplashActivtyViewModel(@NonNull @NotNull Application application) {
        super(application);

        this.application = application;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(application);
        // method to get the location
        getLastLocation();
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {

        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location == null) {
                    requestNewLocationData();
                } else {
                    getLocationName(location.getLatitude(), location.getLongitude());
                }
            }
        });


    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(application);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            getLocationName(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }
    };

    public void getLocationName(double lattitude, double longitude) {


        Geocoder gcd = new Geocoder(application.getBaseContext(), Locale.getDefault());
        try {

            List<Address> addresses = gcd.getFromLocation(lattitude, longitude,
                    10);

            for (Address adrs : addresses) {
                if (adrs != null) {

                    String city = adrs.getLocality();
                    if (city != null && !city.equals("")) {
                        cityName = city;
                        cityNameLiveData.setValue(cityName);
                        System.out.println("city ::  " + cityName);

                        return;

                    } else {

                    }
                    // // you should also try with addresses.get(0).toSring();

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        cityNameLiveData.setValue(cityName);

    }

    public MutableLiveData<String> getCityNameLiveData() {
        return cityNameLiveData;
    }
}
