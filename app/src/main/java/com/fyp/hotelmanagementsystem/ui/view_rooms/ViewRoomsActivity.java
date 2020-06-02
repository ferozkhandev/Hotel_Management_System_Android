package com.fyp.hotelmanagementsystem.ui.view_rooms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fyp.hotelmanagementsystem.BuildConfig;
import com.fyp.hotelmanagementsystem.R;
import com.fyp.hotelmanagementsystem.database.AppDatabase;
import com.fyp.hotelmanagementsystem.databinding.ActivityViewRoomsBinding;
import com.fyp.hotelmanagementsystem.models.Rooms;
import com.fyp.hotelmanagementsystem.models.User;
import com.fyp.hotelmanagementsystem.ui.give_rating_dialog.GiveRatingDialog;
import com.fyp.hotelmanagementsystem.ui.give_rating_to_user.GiveRatingToUserDialog;
import com.fyp.hotelmanagementsystem.ui.hotel_manager_dashboard.HotelManagerDashboardActivity;
import com.fyp.hotelmanagementsystem.ui.login.LoginActivity;
import com.fyp.hotelmanagementsystem.utils.SharedPreferencesUtility;

import java.util.concurrent.Executors;

public class ViewRoomsActivity extends AppCompatActivity implements ViewRoomsListener, LifecycleOwner, GiveRatingToUserDialog.GiveRatingToUserDialogListener {

    private ActivityViewRoomsBinding binding;
    private ViewRoomsViewModel viewModel;
    private Rooms rooms;
    private GiveRatingToUserDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_rooms);

        ViewRoomsViewModelFactory factory = new ViewRoomsViewModelFactory(
                AppDatabase.getInstance(getApplicationContext()),
                Executors.newSingleThreadExecutor()
        );
        viewModel = new ViewModelProvider(this, factory).get(ViewRoomsViewModel.class);

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
            String rent = "Rent: " + rooms.getRent() + "$";
            binding.rent.setText(rent);
            String status = "Status: " + rooms.getStatus();
            binding.status.setText(status);
            if (rooms.getStatus().equals("Checked Out")){
                binding.makeAvailableBtn.setVisibility(View.VISIBLE);
                binding.checkOutBtn.setVisibility(View.GONE);
                binding.message.setVisibility(View.GONE);
            } else {
                binding.makeAvailableBtn.setVisibility(View.GONE);
                binding.checkOutBtn.setVisibility(View.GONE);
                binding.message.setVisibility(View.GONE);
            }
            viewModel.getUser(rooms.getReservedBy()).observe(this, user -> {
                if (user!=null){
                    String userName = "Reserved By: "+ user.getName();
                    String userEmail = "Email of the user: " + user.getEmail();
                    binding.reservedBy.setVisibility(View.VISIBLE);
                    binding.reservedByEmail.setVisibility(View.VISIBLE);
                    binding.reservedBy.setText(userName);
                    binding.reservedByEmail.setText(userEmail);
                } else {
                    binding.reservedBy.setVisibility(View.GONE);
                    binding.reservedByEmail.setVisibility(View.GONE);
                }
            });
        });

        binding.makeAvailableBtn.setOnClickListener(v -> {
            viewModel.makeAvailable(rooms);
            openDialog();
        });

        viewModel.getRating(roomId).observe(this, aFloat -> {
            if (aFloat!=null){
                binding.ratingBar2.setRating(aFloat);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.signout){
            this.getSharedPreferences(BuildConfig.sp_name, 0).edit().clear().apply();
            startActivity(new Intent(ViewRoomsActivity.this, LoginActivity.class));
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openDialog(){
        dialog = new GiveRatingToUserDialog();
        dialog.show(getSupportFragmentManager(), "Give Rating to User Dialog");
    }

    @Override
    public void onViewRoomsStart() {

    }

    @Override
    public void onViewRoomsSuccess() {

    }

    @Override
    public void onViewRoomsFailure(String error) {

    }

    @Override
    public void onGiveRatingSave() {
        dialog.dismiss();
    }

    @Override
    public void onGiveRatingCancel() {
        dialog.dismiss();
    }
}
