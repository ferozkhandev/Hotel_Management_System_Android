package com.fyp.hotelmanagementsystem.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.fyp.hotelmanagementsystem.database.AppDatabase;

import java.util.concurrent.Executor;

public class LoginViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private AppDatabase database;
    private LoginListener listener;
    private Executor executor;

    LoginViewModelFactory(AppDatabase database, LoginListener listener, Executor executor) {
        this.database = database;
        this.listener = listener;
        this.executor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LoginViewModel(database, listener, executor);
    }
}
