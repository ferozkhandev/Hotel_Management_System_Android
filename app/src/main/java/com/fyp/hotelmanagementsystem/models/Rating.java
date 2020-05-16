package com.fyp.hotelmanagementsystem.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "rating")
public class Rating {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "user_id")
    private int user_id;

    @ColumnInfo(name = "hotel_id")
    private int hotel_id;

    @ColumnInfo(name = "room_id")
    private int room_id;

    @ColumnInfo(name = "rating")
    private int rating;

    @ColumnInfo(name = "rating_to_usertype")
    private int ratingToUserType;

    public Rating(int user_id, int hotel_id, int room_id, int rating) {
        this.user_id = user_id;
        this.hotel_id = hotel_id;
        this.room_id = room_id;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
