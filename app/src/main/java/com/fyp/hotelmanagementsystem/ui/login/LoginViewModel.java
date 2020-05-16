package com.fyp.hotelmanagementsystem.ui.login;

import androidx.lifecycle.ViewModel;

import com.fyp.hotelmanagementsystem.database.AppDatabase;

class LoginViewModel extends ViewModel {

    private AppDatabase database;

    LoginViewModel(AppDatabase database) {
        this.database = database;
    }
}
