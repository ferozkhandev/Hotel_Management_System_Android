package com.fyp.hotelmanagementsystem.ui.add_room;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.fyp.hotelmanagementsystem.database.AppDatabase;

import java.util.concurrent.Executor;

public class AddRoomViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private AppDatabase database;
    private Executor executor;

    public AddRoomViewModelFactory(AppDatabase database, Executor executor) {
        this.database = database;
        this.executor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddRoomViewModel(database, executor);
    }
}
