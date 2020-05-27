package com.fyp.hotelmanagementsystem.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

public class PermissionsCheck {

    public static final int GALLERY_PERMISSION_CODE = 203;
    public static int LOCATION_PERMISSION_CODE;

    public static boolean checkLocationPermission(Context context, Activity activity){
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

    public static boolean checkGalleryPermission(Context context, Activity activity){
        boolean flag = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                activity.requestPermissions(permission, GALLERY_PERMISSION_CODE);
            } else {
                flag = true;
            }
        } else {
            flag = true;
        }
        return flag;
    }
}
