package com.fyp.hotelmanagementsystem.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "room")
public class Rooms {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "room_number")
    private int roomNumber;

    @ColumnInfo(name = "number_of_beds")
    private int numberOfBeds;

    @ColumnInfo(name = "internet_availability")
    private boolean internetAvailability;

    @ColumnInfo(name = "rent")
    private int rent;

    @ColumnInfo(name = "picture")
    private String picture;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "is_available")
    private boolean isAvailable;

    @ColumnInfo(name = "hotel_id")
    private int hotelId;

    public Rooms(int roomNumber, int numberOfBeds, boolean internetAvailability, int rent, String picture, String status, boolean isAvailable, int hotelId) {
        this.roomNumber = roomNumber;
        this.numberOfBeds = numberOfBeds;
        this.internetAvailability = internetAvailability;
        this.rent = rent;
        this.picture = picture;
        this.status = status;
        this.isAvailable = isAvailable;
        this.hotelId = hotelId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public boolean isInternetAvailability() {
        return internetAvailability;
    }

    public void setInternetAvailability(boolean internetAvailability) {
        this.internetAvailability = internetAvailability;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }
}
