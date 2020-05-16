package com.fyp.hotelmanagementsystem.ui.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.fyp.hotelmanagementsystem.R;
import com.fyp.hotelmanagementsystem.database.AppDatabase;
import com.fyp.hotelmanagementsystem.databinding.ActivitySignupBinding;
import com.fyp.hotelmanagementsystem.utils.Tags;

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
    }

    @Override
    public void onSignupSuccess() {
        binding.progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSignupFailure(String error) {
        binding.progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSingupValidationError(String message, String field, boolean error) {
        if (field.equals(Tags.NAME)){
            if (error){

            } else {

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
        if (field.equals(Tags.USERTYPE)){

        }
        binding.progressBar.setVisibility(View.INVISIBLE);
    }
}
