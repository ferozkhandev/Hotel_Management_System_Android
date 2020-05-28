package com.fyp.hotelmanagementsystem.ui.add_room;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.fyp.hotelmanagementsystem.R;
import com.fyp.hotelmanagementsystem.database.AppDatabase;
import com.fyp.hotelmanagementsystem.databinding.FragmentAddRoomDialogBinding;
import com.fyp.hotelmanagementsystem.models.Rooms;
import com.fyp.hotelmanagementsystem.utils.DisplayMessage;
import com.fyp.hotelmanagementsystem.utils.PermissionsCheck;
import com.fyp.hotelmanagementsystem.utils.SharedPreferencesUtility;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class AddRoomDialogFragment extends AppCompatDialogFragment {

    private FragmentAddRoomDialogBinding binding;
    private AddRoomViewModel viewModel;
    private AddRoomListener listener;
    private static final int PICK_IMAGE = 1;
    private Uri selectedImageUri = null;
    private int hotelId;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_room_dialog, null, true);
        AddRoomViewModelFactory factory = new AddRoomViewModelFactory(AppDatabase.getInstance(getContext()),
                Executors.newSingleThreadExecutor());
        viewModel = new ViewModelProvider(this, factory).get(AddRoomViewModel.class);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        populateSpinner();
        builder.setView(binding.getRoot()).setTitle("Add Room");
        viewModel.getHotelId(SharedPreferencesUtility.getUser().getId()).observe(this, integer -> {
            hotelId = integer;
        });
        binding.addImage.setOnClickListener(v -> {
            if (PermissionsCheck.checkGalleryPermission(binding.getRoot().getContext(), getActivity())){
                pickImage();
            }
        });
        binding.addRoomBtn.setOnClickListener(v -> {
            if (selectedImageUri!=null){
                addRoom();
            } else {
                DisplayMessage.longShowMessage(getActivity(), "Please add an image first.");
            }
        });
        viewModel.getImageUri().observe(this, imageUri -> {
            if (imageUri!=null){
                selectedImageUri = imageUri;
                binding.viewGalleryImage.setImageURI(imageUri);
            }
        });
        return builder.create();
    }

    private void addRoom(){
        String roomNumber = binding.roomNumber.getText().toString().trim();
        String numberOfBeds = binding.numberOfBeds.getText().toString().trim();
        int internetSpinner = binding.internetAvailability.getSelection();
        boolean internetAvailability = false;
        if (internetSpinner == 1){
            internetAvailability = true;
        }
        String status = "";
        int selectedStatus = binding.status.getSelection();
        if (selectedStatus == 0){
            status = "Available";
        } else if (selectedStatus == 1){
            status = "Not Available";
        } else if (selectedStatus == 2){
            status = "Check In";
        } else if (selectedStatus == 3){
            status = "Check Out";
        }
        String rent = binding.rent.getText().toString().trim();
        Rooms rooms = new Rooms();
        rooms.setRoomNumber(Integer.parseInt(roomNumber));
        rooms.setNumberOfBeds(Integer.parseInt(numberOfBeds));
        rooms.setInternetAvailability(internetAvailability);
        rooms.setRent(Integer.parseInt(rent));
        rooms.setStatus(status);
        rooms.setPicture(selectedImageUri.toString());
        rooms.setHotelId(hotelId);
        viewModel.addRoom(rooms);
    }

    private void pickImage() {
        Intent getIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    private void populateSpinner(){
        List<String> internetAvailability = new ArrayList<>();
        internetAvailability.add("Available");
        internetAvailability.add("Not Available");
        ArrayAdapter<String> adapterInternet = new ArrayAdapter<>(binding.getRoot().getContext(),
                android.R.layout.simple_spinner_item, internetAvailability);
        adapterInternet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.internetAvailability.setAdapter(adapterInternet);

        List<String> status = new ArrayList<>();
        status.add("Available");
        status.add("Not Available");
        status.add("Check In");
        status.add("Check Out");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(binding.getRoot().getContext(),
                android.R.layout.simple_spinner_item, status);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.status.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            Intent intent = new Intent();
            intent.setData(imageUri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (imageUri!=null){
                viewModel.setImageUri(intent.getData());
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AddRoomListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ "Must implement listener");
        }
    }

    public interface AddRoomListener{
        void onAddRoomSuccess();
        void onAddRoomFailure(String error);
    }
}
