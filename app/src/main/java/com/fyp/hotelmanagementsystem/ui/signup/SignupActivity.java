package com.fyp.hotelmanagementsystem.ui.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.fyp.hotelmanagementsystem.R;
import com.fyp.hotelmanagementsystem.database.AppDatabase;
import com.fyp.hotelmanagementsystem.databinding.ActivitySignupBinding;
import com.fyp.hotelmanagementsystem.utils.Tags;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class SignupActivity extends AppCompatActivity implements SignupListener, LifecycleOwner {

    private ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);

        SignupViewModelFactory factory = new SignupViewModelFactory(AppDatabase.getInstance(getApplicationContext()),
                this, Executors.newSingleThreadExecutor());
        SignupViewModel viewModel = new ViewModelProvider(this, factory).get(SignupViewModel.class);

        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }

    @Override
    public void onSignupStart() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.signupBtn.setEnabled(false);
    }

    @Override
    public void onSignupSuccess() {
        runOnUiThread(() -> {
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.signupBtn.setEnabled(true);
            Snackbar.make(findViewById(android.R.id.content), "Success", Snackbar.LENGTH_LONG).show();
        });
    }

    @Override
    public void onSignupFailure(String error) {
        runOnUiThread(() -> {
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.signupBtn.setEnabled(true);
            Snackbar.make(findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG).show();
        });
    }

    @Override
    public void onSingupValidationError(String message, String field, boolean error) {
        if (field.equals(Tags.NAME)){
            if (error){
                binding.name.setError(message);
            } else {
                binding.name.setError(null);
            }
        }
        if (field.equals(Tags.EMAIL)){
            if (error){
                binding.email.setError(message);
            } else {
                binding.email.setError(null);
            }
        }
        if (field.equals(Tags.PASSWORD)){
            if (error){
                binding.password.setError(message);
            } else {
                binding.password.setError(null);
            }
        }
        binding.progressBar.setVisibility(View.INVISIBLE);
    }
}
