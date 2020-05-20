package com.fyp.hotelmanagementsystem.ui.add_hotel;

import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.fyp.hotelmanagementsystem.database.AppDatabase;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.concurrent.Executor;

public class AddHotelViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private FusedLocationProviderClient fusedLocationClient;
    private MutableLiveData<Location> locationMutableLiveData;
    private AppDatabase database;
    private Executor executor;

    AddHotelViewModelFactory(FusedLocationProviderClient fusedLocationClient, MutableLiveData<Location> locationMutableLiveData, AppDatabase database, Executor executor) {
        this.fusedLocationClient = fusedLocationClient;
        this.locationMutableLiveData = locationMutableLiveData;
        this.database = database;
        this.executor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddHotelViewModel(fusedLocationClient, locationMutableLiveData, database, executor);
    }
}
