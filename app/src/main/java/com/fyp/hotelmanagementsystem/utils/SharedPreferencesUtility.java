package com.fyp.hotelmanagementsystem.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.fyp.hotelmanagementsystem.BuildConfig;
import com.fyp.hotelmanagementsystem.models.User;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesUtility extends Application {

    private static Context context;

    SharedPreferencesUtility(){
        context = getApplicationContext();
    }

    public static void setUser(User user){
        SharedPreferences.Editor editor = context.getSharedPreferences(BuildConfig.sp_name, MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(BuildConfig.sp_user, json);
        editor.apply();
    }

    public static User getUser(){
        SharedPreferences prefs = context.getSharedPreferences(BuildConfig.sp_name, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(BuildConfig.sp_user, null);
        return gson.fromJson(json, User.class);
    }
}
