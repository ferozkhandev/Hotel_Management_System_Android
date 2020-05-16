package com.fyp.hotelmanagementsystem.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.fyp.hotelmanagementsystem.R;
import com.fyp.hotelmanagementsystem.database.AppDatabase;

public class LoginActivity extends AppCompatActivity implements LoginListener, LifecycleOwner {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginViewModelFactory factory = new LoginViewModelFactory(AppDatabase.getInstance(getApplicationContext()));
        LoginViewModel viewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);
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
