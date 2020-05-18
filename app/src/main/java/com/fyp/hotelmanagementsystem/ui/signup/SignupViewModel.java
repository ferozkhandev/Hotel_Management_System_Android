package com.fyp.hotelmanagementsystem.ui.signup;

import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.InverseBindingMethod;
import androidx.databinding.InverseBindingMethods;
import androidx.databinding.adapters.AdapterViewBindingAdapter;
import androidx.lifecycle.ViewModel;

import com.fyp.hotelmanagementsystem.database.AppDatabase;
import com.fyp.hotelmanagementsystem.models.User;
import com.fyp.hotelmanagementsystem.utils.Tags;
import com.tiper.MaterialSpinner;

import java.util.concurrent.Executor;

public class SignupViewModel extends ViewModel {

    public String name, email, password;
    public int userType = -1;
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
            executor.execute(() -> {
                try {
                    long inserted = database.userDAO().insert(new User(name, email, password, userType));
                    if (inserted>0){
                        listener.onSignupSuccess();
                    } else {
                        listener.onSignupFailure("Unable to Signup");
                    }
                } catch (Exception ex){
                    listener.onSignupFailure("This user is already exists.");
                }
            });
        }
    }

    @BindingAdapter("android:selectedItemPosition")
    public static void setSelectedItemPosition(AdapterView view, int position) {
        if (view.getSelectedItemPosition() != position) {
            view.setSelection(position);
        }
    }

    @BindingAdapter(value = {"android:onItemSelected", "android:onNothingSelected",
            "android:selectedItemPositionAttrChanged" }, requireAll = false)
    public static void setOnItemSelectedListener(AdapterView view, final AdapterViewBindingAdapter.OnItemSelected selected,
                                                 final AdapterViewBindingAdapter.OnNothingSelected nothingSelected, final InverseBindingListener attrChanged) {
        if (selected == null && nothingSelected == null && attrChanged == null) {
            view.setOnItemSelectedListener(null);
        } else {
            view.setOnItemSelectedListener(
                    new AdapterViewBindingAdapter.OnItemSelectedComponentListener(selected, nothingSelected, attrChanged));
        }
    }

    @BindingAdapter("android:selectedValueAttrChanged")
    public static void setSelectedValueListener(AdapterView view,
                                                final InverseBindingListener attrChanged) {
        if (attrChanged == null) {
            view.setOnItemSelectedListener(null);
        } else {
            view.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    attrChanged.onChange();
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    attrChanged.onChange();
                }
            });
        }
    }

    @BindingAdapter("android:selectedValue")
    public static void setSelectedValue(AdapterView<?> view, Object selectedValue) {
        Adapter adapter = view.getAdapter();
        if (adapter == null) {
            return;
        }
        // I haven't tried this, but maybe setting invalid position will
        // clear the selection?
        int position = AdapterView.INVALID_POSITION;

        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i) == selectedValue) {
                position = i;
                break;
            }
        }
        view.setSelection(position);
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
        /*if (userType==-1){
            isValid = false;
            listener.onSingupValidationError("Please select a category", Tags.USERTYPE, true);
        } else {
            listener.onSingupValidationError("Please select a category", Tags.USERTYPE, false);
        }*/
        return isValid;
    }
}
