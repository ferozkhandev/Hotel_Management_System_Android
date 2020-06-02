package com.fyp.hotelmanagementsystem.ui.user_dashboard;

import android.location.Location;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fyp.hotelmanagementsystem.database.AppDatabase;
import com.fyp.hotelmanagementsystem.models.AvailableRooms;
import com.fyp.hotelmanagementsystem.models.HotelWithRooms;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

import java.util.List;

public class UserDashboardViewModel extends ViewModel {

    private AppDatabase database;
    private FusedLocationProviderClient fusedLocationClient;
    private MutableLiveData<Location> locationMutableLiveData;

    public UserDashboardViewModel(AppDatabase database, FusedLocationProviderClient fusedLocationClient, MutableLiveData<Location> locationMutableLiveData) {
        this.database = database;
        this.fusedLocationClient = fusedLocationClient;
        this.locationMutableLiveData = locationMutableLiveData;
    }

    LiveData<List<AvailableRooms>> getAllHotelWithRooms(){
        return database.hotelDAO().getAllHotelWithRooms();
    }

    LiveData<AvailableRooms> getReservedRoom(int roomId){
        return database.hotelDAO().getReservedRoom(roomId);
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
}
