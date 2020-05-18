package com.fyp.hotelmanagementsystem.ui.splash_activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.fyp.hotelmanagementsystem.database.AppDatabase;

public class SplashViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private AppDatabase database;

    public SplashViewModelFactory(AppDatabase database) {
        this.database = database;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SplashViewModel(database);
    }
}
