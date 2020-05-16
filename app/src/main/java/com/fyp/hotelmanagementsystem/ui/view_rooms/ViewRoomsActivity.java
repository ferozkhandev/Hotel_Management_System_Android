package com.fyp.hotelmanagementsystem.ui.view_rooms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.fyp.hotelmanagementsystem.R;

public class ViewRoomsActivity extends AppCompatActivity implements ViewRoomsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rooms);
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
