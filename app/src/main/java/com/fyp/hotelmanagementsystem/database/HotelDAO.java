package com.fyp.hotelmanagementsystem.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fyp.hotelmanagementsystem.models.Hotel;

@Dao
public interface HotelDAO {

    @Insert
    void insert(Hotel hotel);

    @Update
    void update(Hotel hotel);

    @Delete
    void delete(Hotel hotel);

    @Query("SELECT * FROM hotel WHERE user_id=:userID")
    void getHotel(int userID);
}
