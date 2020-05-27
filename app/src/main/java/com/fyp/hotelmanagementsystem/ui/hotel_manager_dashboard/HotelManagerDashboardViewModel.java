package com.fyp.hotelmanagementsystem.ui.hotel_manager_dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.fyp.hotelmanagementsystem.database.AppDatabase;
import com.fyp.hotelmanagementsystem.models.HotelWithRooms;

import java.util.concurrent.Executor;

public class HotelManagerDashboardViewModel extends ViewModel {

    private AppDatabase database;
    private Executor executor;

    HotelManagerDashboardViewModel(AppDatabase database, Executor executor) {
        this.database = database;
        this.executor = executor;
    }

    LiveData<HotelWithRooms> getHotelWithRooms(int userID){
        return database.hotelDAO().getHotelWithRooms(userID);
    }
}
