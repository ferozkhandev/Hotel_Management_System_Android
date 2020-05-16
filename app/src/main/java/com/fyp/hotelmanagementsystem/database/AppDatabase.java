package com.fyp.hotelmanagementsystem.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {}, version = 1, exportSchema = false)
public abstract class AppDatabase  extends RoomDatabase {

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context, AppDatabase.class, "hotel_management_System.db")
                    .build();
        }
        return instance;
    }
}
