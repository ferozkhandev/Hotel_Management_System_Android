package com.fyp.hotelmanagementsystem.ui.give_rating_dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.fyp.hotelmanagementsystem.R;
import com.fyp.hotelmanagementsystem.database.AppDatabase;
import com.fyp.hotelmanagementsystem.databinding.FragmentGiveRatingDialogBinding;
import com.fyp.hotelmanagementsystem.models.Rating;
import com.fyp.hotelmanagementsystem.ui.add_hotel.AddHotelConfirmationDialog;
import com.fyp.hotelmanagementsystem.utils.SharedPreferencesUtility;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.concurrent.Executors;

public class GiveRatingDialog extends AppCompatDialogFragment {

    private FragmentGiveRatingDialogBinding binding;
    private GiveRatingViewModel viewModel;
    private GiveRatingDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_give_rating_dialog, null, true);
        GiveRatingViewModelFactory factory = new GiveRatingViewModelFactory(
                AppDatabase.getInstance(binding.getRoot().getContext()),
                Executors.newSingleThreadExecutor()
        );
        viewModel = new ViewModelProvider(this, factory).get(GiveRatingViewModel.class);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        builder.setView(binding.getRoot())
                .setTitle("How many start will you give to this room?")
                .setNegativeButton("Cancel", (dialog, which) -> listener.onGiveRatingCancel())
                .setPositiveButton("Save", (dialog, which) -> {
                    Rating rating = new Rating(
                            SharedPreferencesUtility.getUser().getId(),
                            0, SharedPreferencesUtility.getReservedRoom(),
                            binding.ratingBar.getRating(), 0);
                    viewModel.saveRating(rating);
                    listener.onGiveRatingSave();
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (GiveRatingDialog.GiveRatingDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ "Must implement listener");
        }
    }

    public interface GiveRatingDialogListener {
        void onGiveRatingSave();
        void onGiveRatingCancel();
    }
}
