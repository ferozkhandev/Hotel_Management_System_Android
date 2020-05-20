package com.fyp.hotelmanagementsystem.ui.add_hotel;

import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;

public class AddHotelViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private FusedLocationProviderClient fusedLocationClient;
    private MutableLiveData<Location> locationMutableLiveData;

    AddHotelViewModelFactory(FusedLocationProviderClient fusedLocationClient, MutableLiveData<Location> locationMutableLiveData) {
        this.fusedLocationClient = fusedLocationClient;
        this.locationMutableLiveData = locationMutableLiveData;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddHotelViewModel(fusedLocationClient, locationMutableLiveData);
    }
}
