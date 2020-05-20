package com.fyp.hotelmanagementsystem.ui.add_hotel;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.fyp.hotelmanagementsystem.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

public class AddHotelConfirmationDialog extends AppCompatDialogFragment {

    private TextInputEditText hotelName;
    private AddHotelConfirmationDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_add_hotel_dialog, null);
        builder.setView(view)
                .setTitle("Add Hotel")
                .setNegativeButton("Cancel", (dialog, which) -> listener.cancelAddHotel())
                .setPositiveButton("Add", (dialog, which) -> {
                    if (hotelName.getText() != null && hotelName.getText().length()>0){
                        hotelName.setError(null);
                        listener.addHotel(hotelName.getText().toString().trim());
                    } else {
                        listener.addHotelNameError();
                    }
                });
        hotelName = view.findViewById(R.id.hotel_name);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AddHotelConfirmationDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ "Must implement listener");
        }
    }

    public interface AddHotelConfirmationDialogListener{
        void addHotel(String hotelName);
        void cancelAddHotel();
        void addHotelNameError();
    }
}
