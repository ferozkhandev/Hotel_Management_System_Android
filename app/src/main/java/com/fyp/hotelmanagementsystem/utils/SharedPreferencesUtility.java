package com.fyp.hotelmanagementsystem.utils;

import android.app.Application;
import android.content.SharedPreferences;

import com.facebook.stetho.Stetho;
import com.fyp.hotelmanagementsystem.BuildConfig;
import com.fyp.hotelmanagementsystem.models.User;
import com.google.gson.Gson;

public class SharedPreferencesUtility extends Application {

    private static Application application;

    public SharedPreferencesUtility(){
        application = this;
    }

    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }

    public static void setUser(User user){
        SharedPreferences.Editor editor = application.getSharedPreferences(BuildConfig.sp_name, MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(BuildConfig.sp_user, json);
        editor.apply();
    }

    public static User getUser(){
        SharedPreferences prefs = application.getSharedPreferences(BuildConfig.sp_name, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(BuildConfig.sp_user, null);
        return gson.fromJson(json, User.class);
    }

    public static void setReservedRoom(int roomId){
        SharedPreferences.Editor editor = application.getSharedPreferences(BuildConfig.sp_name, MODE_PRIVATE).edit();
        editor.putInt(BuildConfig.sp_reserved_room, roomId);
        editor.apply();
    }

    public static int getReservedRoom(){
        SharedPreferences prefs = application.getSharedPreferences(BuildConfig.sp_name, MODE_PRIVATE);
        return prefs.getInt(BuildConfig.sp_reserved_room, 0);
    }
}
