package com.fyp.hotelmanagementsystem.ui.signup;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.fyp.hotelmanagementsystem.database.AppDatabase;
import com.fyp.hotelmanagementsystem.models.User;
import com.fyp.hotelmanagementsystem.utils.Tags;

import java.util.concurrent.Executor;

public class SignupViewModel extends ViewModel {

    public String name, email, password, userType;
    private AppDatabase database;
    private SignupListener listener;
    private Executor executor;

    SignupViewModel(AppDatabase database, SignupListener listener, Executor executor) {
        this.database = database;
        this.listener = listener;
        this.executor = executor;
    }

    public void signup(View view){
        listener.onSignupStart();
        if (isValid()){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    long inserted = database.userDAO().insert(new User(name, email, password, Integer.parseInt(userType)));
                    if (inserted>0){
                        listener.onSignupSuccess();
                    } else {
                        listener.onSignupFailure("Unable to Signup");
                    }
                }
            });
        }
    }

    private boolean isValid(){
        boolean isValid = true;
        if (name==null || name.length()<3){
            isValid = false;
            listener.onSingupValidationError("Please enter a valid Name", Tags.NAME, true);
        } else {
            listener.onSingupValidationError("Please enter a valid Name", Tags.NAME, false);
        }
        if (email==null || email.length()<3){
            isValid = false;
            listener.onSingupValidationError("Please enter a valid Email", Tags.EMAIL, true);
        } else {
            listener.onSingupValidationError("Please enter a valid Email", Tags.EMAIL, false);
        }
        if (password==null || password.length()<6){
            isValid = false;
            listener.onSingupValidationError("Please enter a valid Password", Tags.PASSWORD, true);
        } else {
            listener.onSingupValidationError("Please enter a valid Password", Tags.PASSWORD, false);
        }
        if (userType==null){
            isValid = false;
            listener.onSingupValidationError("Please select a category", Tags.USERTYPE, true);
        } else {
            listener.onSingupValidationError("Please select a category", Tags.USERTYPE, false);
        }
        return isValid;
    }
}
