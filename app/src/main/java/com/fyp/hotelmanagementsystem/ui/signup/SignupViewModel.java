package com.fyp.hotelmanagementsystem.ui.signup;

import androidx.lifecycle.ViewModel;

import com.fyp.hotelmanagementsystem.database.AppDatabase;

public class SignupViewModel extends ViewModel {

    private AppDatabase database;

    SignupViewModel(AppDatabase database) {
        this.database = database;
    }
}
