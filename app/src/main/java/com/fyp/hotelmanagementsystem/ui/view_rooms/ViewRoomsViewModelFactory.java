package com.fyp.hotelmanagementsystem.ui.view_rooms;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.fyp.hotelmanagementsystem.database.AppDatabase;

public class ViewRoomsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private AppDatabase database;

    ViewRoomsViewModelFactory(AppDatabase database) {
        this.database = database;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ViewRoomsViewModel(database);
    }
}
