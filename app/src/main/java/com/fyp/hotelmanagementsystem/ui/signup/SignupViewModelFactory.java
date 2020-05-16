package com.fyp.hotelmanagementsystem.ui.signup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.fyp.hotelmanagementsystem.database.AppDatabase;

public class SignupViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private AppDatabase database;

    SignupViewModelFactory(AppDatabase database) {
        this.database = database;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SignupViewModel(database);
    }
}
