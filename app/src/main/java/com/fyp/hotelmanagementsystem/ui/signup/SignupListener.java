package com.fyp.hotelmanagementsystem.ui.signup;

public interface SignupListener {
    void onSignupStart();
    void onSignupSuccess();
    void onSignupFailure(String error);
}
