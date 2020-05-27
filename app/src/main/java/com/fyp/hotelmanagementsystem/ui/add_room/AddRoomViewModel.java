package com.fyp.hotelmanagementsystem.ui.add_room;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fyp.hotelmanagementsystem.database.AppDatabase;
import com.fyp.hotelmanagementsystem.models.Rooms;

import java.util.concurrent.Executor;

public class AddRoomViewModel extends ViewModel {

    private AppDatabase database;
    private Executor executor;
    private MutableLiveData<Uri> uriMutableLiveData = new MutableLiveData<>();

    public AddRoomViewModel(AppDatabase database, Executor executor) {
        this.database = database;
        this.executor = executor;
    }

    public void addRoom(Rooms rooms){
        executor.execute(() -> database.roomsDAO().insert(rooms));
    }

    public LiveData<Uri> getImageUri(){
        return uriMutableLiveData;
    }

    public void setImageUri(Uri imageUri){
        uriMutableLiveData.setValue(imageUri);
    }

    public LiveData<Integer> getHotelId(int userID){
        return database.hotelDAO().getHotelId(userID);
    }
}
