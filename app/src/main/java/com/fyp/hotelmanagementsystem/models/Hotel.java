package com.fyp.hotelmanagementsystem.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "hotel")
public class Hotel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "hotel_name")
    private String hotelName;

    @ColumnInfo(name = "latitude")
    private float latitude;

    @ColumnInfo(name = "longitude")
    private float longitude;

    @ColumnInfo(name = "user_id")
    private int userID;

    public Hotel(String hotelName, float latitude, float longitude, int userID) {
        this.hotelName = hotelName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
