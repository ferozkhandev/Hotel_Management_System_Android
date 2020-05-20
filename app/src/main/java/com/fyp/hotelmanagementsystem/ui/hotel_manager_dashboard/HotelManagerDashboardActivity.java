package com.fyp.hotelmanagementsystem.ui.hotel_manager_dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.fyp.hotelmanagementsystem.BuildConfig;
import com.fyp.hotelmanagementsystem.R;
import com.fyp.hotelmanagementsystem.database.AppDatabase;
import com.fyp.hotelmanagementsystem.databinding.ActivityHotelManagerDashboardBinding;
import com.fyp.hotelmanagementsystem.ui.login.LoginActivity;

import java.util.concurrent.Executors;

public class HotelManagerDashboardActivity extends AppCompatActivity implements HotelManagerDashboardListener, LifecycleOwner {

    private HotelManagerDashboardViewModel viewModel;
    private ActivityHotelManagerDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hotel_manager_dashboard);
        HotelManagerDashboardViewModelFactory factory = new HotelManagerDashboardViewModelFactory(
                AppDatabase.getInstance(getApplicationContext()),
                Executors.newSingleThreadExecutor()
        );
        viewModel = new ViewModelProvider(this, factory).get(HotelManagerDashboardViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.signout){
            this.getSharedPreferences(BuildConfig.sp_name, 0).edit().clear().apply();
            startActivity(new Intent(HotelManagerDashboardActivity.this, LoginActivity.class));
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
