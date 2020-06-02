package com.fyp.hotelmanagementsystem.ui.view_rooms;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.fyp.hotelmanagementsystem.database.AppDatabase;
import com.fyp.hotelmanagementsystem.models.Rooms;
import com.fyp.hotelmanagementsystem.models.User;
import com.fyp.hotelmanagementsystem.utils.SharedPreferencesUtility;

import java.util.concurrent.Executor;

public class ViewRoomsViewModel extends ViewModel {

    private AppDatabase database;
    private Executor executor;

    public ViewRoomsViewModel(AppDatabase database, Executor executor) {
        this.database = database;
        this.executor = executor;
    }

    public LiveData<Rooms> getRoom(int roomId){
        return database.roomsDAO().getRoom(roomId);
    }

    public LiveData<Float> getRating(int roomId){
        return database.ratingDAO().getRoomRating(roomId, SharedPreferencesUtility.getUser().getUserType());
    }

    public void makeAvailable(Rooms rooms){
        rooms.setStatus("Available");
        rooms.setAvailable(true);
        executor.execute(() -> {
            database.roomsDAO().update(rooms);
        });
    }

    public LiveData<User> getUser(int userId){
        return database.userDAO().getUser(userId);
    }
}
