package com.fyp.hotelmanagementsystem.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fyp.hotelmanagementsystem.R;
import com.fyp.hotelmanagementsystem.database.AppDatabase;
import com.fyp.hotelmanagementsystem.databinding.ActivityLoginBinding;
import com.fyp.hotelmanagementsystem.ui.signup.SignupActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity implements LoginListener, LifecycleOwner {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        LoginViewModelFactory factory = new LoginViewModelFactory(AppDatabase.getInstance(getApplicationContext()),
                this, Executors.newSingleThreadExecutor());
        LoginViewModel viewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);

        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        binding.createAccount.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            this.finish();
        });
    }

    @Override
    public void onLoginStart() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.loginBtn.setEnabled(false);
    }

    @Override
    public void onLoginSuccess(int userType) {
        runOnUiThread(() -> {
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.loginBtn.setEnabled(true);
            Snackbar.make(findViewById(android.R.id.content), "Success", Snackbar.LENGTH_LONG).show();
            if (userType == 1){
                //Hotel Manager

            } else {
                //User

            }
        });
    }

    @Override
    public void onLoginFailure(String error) {
        runOnUiThread(() -> {
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.loginBtn.setEnabled(true);
            Snackbar.make(findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG).show();
        });
    }
}
