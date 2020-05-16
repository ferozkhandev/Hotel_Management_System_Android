package com.fyp.hotelmanagementsystem.ui.view_rooms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.fyp.hotelmanagementsystem.R;
import com.fyp.hotelmanagementsystem.database.AppDatabase;

public class ViewRoomsActivity extends AppCompatActivity implements ViewRoomsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rooms);

        ViewRoomsViewModelFactory factory = new ViewRoomsViewModelFactory(AppDatabase.getInstance(getApplicationContext()));
        ViewRoomsViewModel viewModel = new ViewModelProvider(this, factory).get(ViewRoomsViewModel.class);
    }

    @Override
    public void onViewRoomsStart() {

    }

    @Override
    public void onViewRoomsSuccess() {

    }

    @Override
    public void onViewRoomsFailure(String error) {

    }
}
