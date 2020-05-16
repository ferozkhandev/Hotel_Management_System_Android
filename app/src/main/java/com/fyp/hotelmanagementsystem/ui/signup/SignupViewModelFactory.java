package com.fyp.hotelmanagementsystem.ui.signup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.fyp.hotelmanagementsystem.database.AppDatabase;

import java.util.concurrent.Executor;

public class SignupViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private AppDatabase database;
    private SignupListener listener;
    private Executor executor;

    SignupViewModelFactory(AppDatabase database, SignupListener listener, Executor executor) {
        this.database = database;
        this.listener = listener;
        this.executor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SignupViewModel(database, listener, executor);
    }
}
