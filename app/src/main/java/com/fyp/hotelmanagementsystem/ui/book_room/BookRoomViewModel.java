package com.fyp.hotelmanagementsystem.ui.book_room;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.fyp.hotelmanagementsystem.R;
import com.fyp.hotelmanagementsystem.database.AppDatabase;
import com.fyp.hotelmanagementsystem.models.Rooms;
import com.fyp.hotelmanagementsystem.utils.SharedPreferencesUtility;

import java.util.concurrent.Executor;

public class BookRoomViewModel extends ViewModel {

    private BookRoomListener listener;
    private AppDatabase database;
    private Executor executor;

    public BookRoomViewModel(BookRoomListener listener, AppDatabase database, Executor executor) {
        this.listener = listener;
        this.database = database;
        this.executor = executor;
    }

    public LiveData<Rooms> getRoom(int roomId){
        return database.roomsDAO().getRoom(roomId);
    }

    public LiveData<Float> getRating(int roomId){
        return database.ratingDAO().getRoomRating(roomId, SharedPreferencesUtility.getUser().getUserType());
    }

    public void bookRoom(Rooms rooms) {
        rooms.setReservedBy(SharedPreferencesUtility.getUser().getId());
        rooms.setStatus("Not Available");
        rooms.setAvailable(false);
        executor.execute(() -> database.roomsDAO().update(rooms));
        SharedPreferencesUtility.setReservedRoom(rooms.getId());
    }
}
