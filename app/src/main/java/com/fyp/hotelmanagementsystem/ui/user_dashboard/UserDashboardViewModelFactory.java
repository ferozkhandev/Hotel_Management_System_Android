package com.fyp.hotelmanagementsystem.ui.user_dashboard;

import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.fyp.hotelmanagementsystem.database.AppDatabase;
import com.google.android.gms.location.FusedLocationProviderClient;

public class UserDashboardViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private AppDatabase database;
    private FusedLocationProviderClient fusedLocationClient;
    private MutableLiveData<Location> locationMutableLiveData;

    public UserDashboardViewModelFactory(AppDatabase database, FusedLocationProviderClient fusedLocationClient, MutableLiveData<Location> locationMutableLiveData) {
        this.database = database;
        this.fusedLocationClient = fusedLocationClient;
        this.locationMutableLiveData = locationMutableLiveData;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UserDashboardViewModel(database, fusedLocationClient, locationMutableLiveData);
    }
}
