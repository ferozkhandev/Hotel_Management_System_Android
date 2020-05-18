package com.fyp.hotelmanagementsystem.ui.login;

public interface LoginListener {
    void onLoginStart();
    void onLoginSuccess(int userType);
    void onLoginFailure(String error);
}
