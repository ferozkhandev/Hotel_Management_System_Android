package com.fyp.hotelmanagementsystem.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.fyp.hotelmanagementsystem.models.AvailableRooms;
import com.fyp.hotelmanagementsystem.models.Hotel;
import com.fyp.hotelmanagementsystem.models.HotelWithRooms;

import java.util.List;

@Dao
public interface HotelDAO {

    @Insert
    void insert(Hotel hotel);

    @Update
    void update(Hotel hotel);

    @Delete
    void delete(Hotel hotel);

    @Query("SELECT * FROM hotel WHERE user_id=:userID")
    LiveData<List<Hotel>> getHotel(int userID);

    @Transaction
    @Query("SELECT * FROM hotel WHERE user_id=:userID")
    LiveData<HotelWithRooms> getHotelWithRooms(int userID);

//    @Transaction
    @Query("SELECT hotel.hotel_name, hotel.latitude, hotel.longitude, room.* FROM room LEFT JOIN hotel ON room.hotel_id = hotel.id")
    LiveData<List<AvailableRooms>> getAllHotelWithRooms();

    @Query("SELECT hotel.hotel_name, hotel.latitude, hotel.longitude, room.* FROM room LEFT JOIN hotel ON room.hotel_id = hotel.id WHERE room.id=:roomId")
    LiveData<AvailableRooms> getReservedRoom(int roomId);

    @Query("SELECT id FROM hotel WHERE user_id=:userID")
    LiveData<Integer> getHotelId(int userID);
}
