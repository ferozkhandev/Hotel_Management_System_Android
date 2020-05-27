package com.fyp.hotelmanagementsystem.ui.splash_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.fyp.hotelmanagementsystem.R;
import com.fyp.hotelmanagementsystem.database.AppDatabase;
import com.fyp.hotelmanagementsystem.models.Hotel;
import com.fyp.hotelmanagementsystem.models.User;
import com.fyp.hotelmanagementsystem.ui.add_hotel.AddHotelActivity;
import com.fyp.hotelmanagementsystem.ui.hotel_manager_dashboard.HotelManagerDashboardActivity;
import com.fyp.hotelmanagementsystem.ui.login.LoginActivity;
import com.fyp.hotelmanagementsystem.ui.user_dashboard.UserDashboardActivity;
import com.fyp.hotelmanagementsystem.utils.SharedPreferencesUtility;

import java.util.List;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SplashViewModelFactory factory = new SplashViewModelFactory(AppDatabase.getInstance(getApplicationContext()));
        SplashViewModel viewModel = new ViewModelProvider(this, factory).get(SplashViewModel.class);

        new Handler().postDelayed(() -> {
            User user = SharedPreferencesUtility.getUser();
            if (user!=null){
                if (user.getUserType() == 1){
                    //Hotel Manager
                    viewModel.getHotel(user.getId()).observe(this, hotels -> {
                        if (hotels != null && !hotels.isEmpty()){
                            //Move to his Dashboard
                            startActivity(new Intent(SplashActivity.this, HotelManagerDashboardActivity.class));
                            SplashActivity.this.finish();
                        } else {
                            //Move to Add hotel
                            startActivity(new Intent(SplashActivity.this, AddHotelActivity.class));
                            SplashActivity.this.finish();
                        }
                    });
                } else {
                    //User
                    startActivity(new Intent(SplashActivity.this, UserDashboardActivity.class));
                    SplashActivity.this.finish();
                }
            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                SplashActivity.this.finish();
            }
        }, 2000);
    }
}
