package com.fyp.hotelmanagementsystem.ui.view_rooms;

import androidx.lifecycle.ViewModel;

import com.fyp.hotelmanagementsystem.database.AppDatabase;

class ViewRoomsViewModel extends ViewModel {

    private AppDatabase database;

    ViewRoomsViewModel(AppDatabase database) {
        this.database = database;
    }
}
