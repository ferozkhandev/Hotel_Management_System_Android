package com.fyp.hotelmanagementsystem.ui.login;

public interface LoginListener {
    void onLoginStart();
    void onLoginSuccess();
    void onLoginFailure(String error);
}
