package com.fyp.hotelmanagementsystem.ui.hotel_manager_dashboard;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.fyp.hotelmanagementsystem.database.AppDatabase;

import java.util.concurrent.Executor;

public class HotelManagerDashboardViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private AppDatabase database;
    private Executor executor;

    HotelManagerDashboardViewModelFactory(AppDatabase database, Executor executor) {
        this.database = database;
        this.executor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new HotelManagerDashboardViewModel(database, executor);
    }
}
