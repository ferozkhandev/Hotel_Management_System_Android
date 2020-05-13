package com.fyp.hotelmanagementsystem.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;

import android.os.Bundle;

import com.fyp.hotelmanagementsystem.R;

public class LoginActivity extends AppCompatActivity implements LoginListener, LifecycleOwner {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onLoginStart() {

    }

    @Override
    public void onLoginSuccess() {

    }

    @Override
    public void onLoginFailure(String error) {

    }
}
