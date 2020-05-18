package com.fyp.hotelmanagementsystem.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.fyp.hotelmanagementsystem.R;
import com.fyp.hotelmanagementsystem.ui.login.LoginActivity;
import com.fyp.hotelmanagementsystem.ui.signup.SignupActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, SignupActivity.class));
            SplashActivity.this.finish();
        }, 2000);
    }
}
