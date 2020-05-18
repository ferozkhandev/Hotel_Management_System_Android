package com.fyp.hotelmanagementsystem.ui.splash_activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.fyp.hotelmanagementsystem.database.AppDatabase;
import com.fyp.hotelmanagementsystem.models.Hotel;

import java.util.List;

public class SplashViewModel extends ViewModel {

    private AppDatabase database;

    public SplashViewModel(AppDatabase database) {
        this.database = database;
    }

    LiveData<List<Hotel>> getHotel(int userId){
        return database.hotelDAO().getHotel(userId);
    }
}
