package com.fyp.hotelmanagementsystem.ui.signup;

public interface SignupListener {
    void onSignupStart();
    void onSignupSuccess();
    void onSignupFailure(String error);
    void onSingupValidationError(String message, String field, boolean error);
}
