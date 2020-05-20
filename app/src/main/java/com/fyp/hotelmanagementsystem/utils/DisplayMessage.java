package com.fyp.hotelmanagementsystem.utils;

import android.app.Activity;

import com.google.android.material.snackbar.Snackbar;

public class DisplayMessage {
    public static void longShowMessage(Activity activity, String message){
        Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    public static void shortShowMessage(Activity activity, String message){
        Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }
}
