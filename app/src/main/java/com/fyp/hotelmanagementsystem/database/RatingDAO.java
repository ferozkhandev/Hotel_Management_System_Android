package com.fyp.hotelmanagementsystem.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fyp.hotelmanagementsystem.models.Rating;

import java.util.List;

@Dao
public interface RatingDAO {

    @Insert
    void insert(Rating rating);

    @Update
    void update(Rating rating);

    @Delete
    void delete(Rating rating);

    @Query("SELECT * FROM rating WHERE user_id=:userID AND rating_to_usertype=:ratingToUserType")
    LiveData<List<Rating>> getRating(int userID, int ratingToUserType);

    @Query("SELECT * FROM rating WHERE hotel_id=:hotelID AND room_id=:roomID AND rating_to_usertype=:ratingToUserType")
    LiveData<List<Rating>> getRating(int hotelID, int roomID, int ratingToUserType);
}
