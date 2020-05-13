package com.fyp.hotelmanagementsystem.ui.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;

import android.os.Bundle;

import com.fyp.hotelmanagementsystem.R;

public class SignupActivity extends AppCompatActivity implements SignupListener, LifecycleOwner {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    @Override
    public void onSignupStart() {

    }

    @Override
    public void onSignupSuccess() {

    }

    @Override
    public void onSignupFailure(String error) {

    }
}
