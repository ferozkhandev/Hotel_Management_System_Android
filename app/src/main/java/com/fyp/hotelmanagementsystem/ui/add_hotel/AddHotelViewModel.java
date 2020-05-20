package com.fyp.hotelmanagementsystem.ui.add_hotel;

import android.location.Location;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

import java.util.List;
import java.util.concurrent.Executor;

public class AddHotelViewModel extends ViewModel {

    private FusedLocationProviderClient fusedLocationClient;
    private MutableLiveData<Location> locationMutableLiveData;

    AddHotelViewModel(FusedLocationProviderClient fusedLocationClient, MutableLiveData<Location> locationMutableLiveData) {
        this.fusedLocationClient = fusedLocationClient;
        this.locationMutableLiveData = locationMutableLiveData;
    }

    LiveData<Location> getLocation(){
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location!=null){
                        locationMutableLiveData.setValue(location);
                    } else {
                        LocationRequest locationRequest = LocationRequest.create();
                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        LocationCallback locationCallback;
                        locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                if (locationResult == null) {
                                    return;
                                }
                                List<Location> locations = locationResult.getLocations();
                                if (locations.size()>0){
                                    Location location = locations.get(locations.size() - 1);
                                    locationMutableLiveData.setValue(location);
                                }
                            }
                        };
                        fusedLocationClient.requestLocationUpdates(locationRequest,
                                locationCallback,
                                Looper.getMainLooper());
                    }
                });
        return locationMutableLiveData;
    }

    public void addHotel(){

    }
}
