package com.fyp.hotelmanagementsystem.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.fyp.hotelmanagementsystem.models.Hotel;
import com.fyp.hotelmanagementsystem.models.Rating;
import com.fyp.hotelmanagementsystem.models.Rooms;
import com.fyp.hotelmanagementsystem.models.User;

@Database(entities = {User.class, Hotel.class, Rooms.class, Rating.class}, version = 2, exportSchema = false)
public abstract class AppDatabase  extends RoomDatabase {

    private static AppDatabase instance;
    public abstract UserDAO userDAO();
    public abstract HotelDAO hotelDAO();
    public abstract RoomsDAO roomsDAO();
    public abstract RatingDAO ratingDAO();

    public static synchronized AppDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context, AppDatabase.class, "hotel_management_System.db")
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return instance;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE room "
                    + " ADD COLUMN reserved_by INTEGER NOT NULL DEFAULT 0");
        }
    };
}
