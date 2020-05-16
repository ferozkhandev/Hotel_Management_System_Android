package com.fyp.hotelmanagementsystem.ui.login;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.fyp.hotelmanagementsystem.database.AppDatabase;
import com.fyp.hotelmanagementsystem.models.User;

class LoginViewModel extends ViewModel {

    public String email, password;
    private AppDatabase database;

    LoginViewModel(AppDatabase database) {
        this.database = database;
    }

    public void login(View view){
        User user = database.userDAO().login(email, password);
        if (user!=null){
            //TODO login success, write shared preferences
        }
    }
}
