package com.fyp.hotelmanagementsystem.ui.give_rating_dialog;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.fyp.hotelmanagementsystem.database.AppDatabase;

import java.util.concurrent.Executor;

public class GiveRatingViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private AppDatabase database;
    private Executor executor;

    public GiveRatingViewModelFactory(AppDatabase database, Executor executor) {
        this.database = database;
        this.executor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GiveRatingViewModel(database, executor);
    }
}
