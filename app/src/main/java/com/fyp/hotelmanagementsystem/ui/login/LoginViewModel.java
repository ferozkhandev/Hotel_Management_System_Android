package com.fyp.hotelmanagementsystem.ui.login;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.fyp.hotelmanagementsystem.database.AppDatabase;
import com.fyp.hotelmanagementsystem.models.User;
import com.fyp.hotelmanagementsystem.utils.SharedPreferencesUtility;

import java.util.concurrent.Executor;

public class LoginViewModel extends ViewModel {

    public String email, password;
    private AppDatabase database;
    private LoginListener listener;
    private Executor executor;

    LoginViewModel(AppDatabase database, LoginListener listener, Executor executor) {
        this.database = database;
        this.listener = listener;
        this.executor = executor;
    }

    public void login(View view){
        listener.onLoginStart();
        executor.execute(() -> {
            User user = database.userDAO().login(email, password);
            SharedPreferencesUtility.setUser(user);
            if (user!=null){
                //TODO login success, write shared preferences
                listener.onLoginSuccess(user.getUserType());
            } else {
                listener.onLoginFailure("Please check your email/password");
            }
        });
    }
}
