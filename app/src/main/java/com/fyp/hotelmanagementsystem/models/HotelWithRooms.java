package com.fyp.hotelmanagementsystem.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class HotelWithRooms {
    @Embedded public Hotel hotel;
    @Relation(
            parentColumn = "id",
            entityColumn = "hotel_id"
    )
    public List<Rooms> rooms;
}
