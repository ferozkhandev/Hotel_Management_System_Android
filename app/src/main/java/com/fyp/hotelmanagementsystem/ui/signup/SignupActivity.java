package com.fyp.hotelmanagementsystem.ui.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.fyp.hotelmanagementsystem.R;
import com.fyp.hotelmanagementsystem.database.AppDatabase;

public class SignupActivity extends AppCompatActivity implements SignupListener, LifecycleOwner {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        SignupViewModelFactory factory = new SignupViewModelFactory(AppDatabase.getInstance(getApplicationContext()));
        SignupViewModel viewModel = new ViewModelProvider(this, factory).get(SignupViewModel.class);
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
