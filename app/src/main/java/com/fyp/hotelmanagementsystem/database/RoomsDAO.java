package com.fyp.hotelmanagementsystem.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fyp.hotelmanagementsystem.models.Rooms;

import java.util.List;

@Dao
public interface RoomsDAO {

    @Insert
    void insert(Rooms rooms);

    @Update
    void update(Rooms rooms);

    @Delete
    void delete(Rooms rooms);

    @Query("SELECT * FROM room WHERE hotel_id=:hotelID")
    LiveData<List<Rooms>> getRooms(int hotelID);

    @Query("SELECT * FROM room WHERE hotel_id=:hotelID AND is_available=1")
    LiveData<List<Rooms>> getAvailableRooms(int hotelID);
}
