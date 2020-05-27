package com.fyp.hotelmanagementsystem.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Relation;

public class AvailableRooms {

    @ColumnInfo(name = "hotel_name")
    public String hotelName;

    @ColumnInfo(name = "latitude")
    public double latitude;

    @ColumnInfo(name = "longitude")
    public double longitude;

    @Embedded
    public Rooms rooms;
}
