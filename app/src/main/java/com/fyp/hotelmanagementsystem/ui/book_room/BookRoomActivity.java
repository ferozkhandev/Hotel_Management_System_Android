package com.fyp.hotelmanagementsystem.ui.book_room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.fyp.hotelmanagementsystem.R;
import com.fyp.hotelmanagementsystem.database.AppDatabase;
import com.fyp.hotelmanagementsystem.databinding.ActivityBookRoomBinding;
import com.fyp.hotelmanagementsystem.models.Rooms;
import com.fyp.hotelmanagementsystem.utils.SharedPreferencesUtility;

import java.util.concurrent.Executors;

public class BookRoomActivity extends AppCompatActivity implements BookRoomListener, LifecycleOwner {

    private ActivityBookRoomBinding binding;
    private BookRoomViewModel viewModel;
    private Rooms rooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_room);
        BookRoomViewModelFactory factory = new BookRoomViewModelFactory(
                this,
                AppDatabase.getInstance(getApplicationContext()),
                Executors.newSingleThreadExecutor()
        );
        viewModel = new ViewModelProvider(this, factory).get(BookRoomViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        String hotelName = getIntent().getStringExtra("hotel_name");
        int hotelId = getIntent().getIntExtra("hotel_id", 0);
        int roomId = getIntent().getIntExtra("room_id", 0);

        binding.hotelName.setText(hotelName);

        viewModel.getRoom(roomId).observe(this, rooms -> {
            this.rooms = rooms;
            binding.appCompatImageView.setImageURI(Uri.parse(rooms.getPicture()));
            String roomNumber = "Room Number: " + rooms.getRoomNumber();
            binding.roomNumber.setText(roomNumber);
            String numberOfBeds = "Number of Bedrooms: "+ rooms.getNumberOfBeds();
            binding.numberOfBedrooms.setText(numberOfBeds);
            if (rooms.isInternetAvailability()){
                binding.internetAvailability.setText(R.string.internet_available);
            } else {
                binding.internetAvailability.setText(R.string.inernet_not_available);
            }
            if (SharedPreferencesUtility.getReservedRoom() == rooms.getId()){
                binding.bookNowBtn.setVisibility(View.GONE);
                switch (rooms.getStatus()) {
                    case "Checked In":
                        binding.checkInBtn.setVisibility(View.GONE);
                        binding.checkOutBtn.setVisibility(View.VISIBLE);
                        binding.message.setVisibility(View.GONE);
                        break;
                    case "Checked Out":
                        binding.checkInBtn.setVisibility(View.GONE);
                        binding.checkOutBtn.setVisibility(View.GONE);
                        binding.message.setVisibility(View.VISIBLE);
                        binding.message.setText("You have been checked out from this room.");
                        break;
                    case "Available":
                        binding.bookNowBtn.setVisibility(View.VISIBLE);
                        binding.checkInBtn.setVisibility(View.GONE);
                        binding.checkOutBtn.setVisibility(View.GONE);
                        binding.message.setVisibility(View.GONE);
                        break;
                    case "Not Available":
                        if (rooms.getReservedBy() == SharedPreferencesUtility.getUser().getId()){
                            binding.checkInBtn.setVisibility(View.VISIBLE);
                            binding.checkOutBtn.setVisibility(View.GONE);
                            binding.message.setVisibility(View.GONE);
                        } else {
                            binding.checkInBtn.setVisibility(View.GONE);
                            binding.checkOutBtn.setVisibility(View.GONE);
                            binding.message.setVisibility(View.VISIBLE);
                            binding.message.setText("This room is not available");
                        }
                        break;
                }
            } else {
                binding.bookNowBtn.setVisibility(View.VISIBLE);
                binding.checkInBtn.setVisibility(View.GONE);
                binding.checkOutBtn.setVisibility(View.GONE);
            }
        });

        viewModel.getRating(roomId).observe(this, aFloat -> {
           if (aFloat!=null){
               binding.ratingBar2.setRating(aFloat);
           }
        });

        binding.bookNowBtn.setOnClickListener(v -> {
            if (rooms!=null){
                viewModel.bookRoom(rooms);
            }
        });
    }
}