package com.fyp.hotelmanagementsystem.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

public class PermissionsCheck {

    public static int LOCATION_PERMISSION_CODE;

    public static boolean checkPermission(Context context, Activity activity){
        boolean flag = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_DENIED || context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_DENIED){
                String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                activity.requestPermissions(permission, LOCATION_PERMISSION_CODE);
            } else {
                flag = true;
            }
        } else {
            flag = true;
        }
        return flag;
    }
}
