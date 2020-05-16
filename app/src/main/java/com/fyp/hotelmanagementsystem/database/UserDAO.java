package com.fyp.hotelmanagementsystem.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fyp.hotelmanagementsystem.models.User;

@Dao
public interface UserDAO {

    @Insert
    long insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM user WHERE id=:id")
    LiveData<User> getUser(int id);

    @Query("SELECT * FROM user WHERE email=:email AND password=:password")
    User login(String email, String password);
}
