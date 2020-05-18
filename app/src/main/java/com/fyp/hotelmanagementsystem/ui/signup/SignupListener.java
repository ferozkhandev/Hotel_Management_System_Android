package com.fyp.hotelmanagementsystem.ui.signup;

public interface SignupListener {
    void onSignupStart();
    void onSignupSuccess(int userType);
    void onSignupFailure(String error);
    void onSingupValidationError(String message, String field, boolean error);
}
