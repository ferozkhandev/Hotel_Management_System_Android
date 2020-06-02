package com.fyp.hotelmanagementsystem.ui.user_dashboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fyp.hotelmanagementsystem.BuildConfig;
import com.fyp.hotelmanagementsystem.R;
import com.fyp.hotelmanagementsystem.adapters.AvailableRoomsAdapter;
import com.fyp.hotelmanagementsystem.adapters.ViewRoomsAdapter;
import com.fyp.hotelmanagementsystem.database.AppDatabase;
import com.fyp.hotelmanagementsystem.databinding.ActivityUserDashboardBinding;
import com.fyp.hotelmanagementsystem.models.AvailableRooms;
import com.fyp.hotelmanagementsystem.ui.book_room.BookRoomActivity;
import com.fyp.hotelmanagementsystem.ui.hotel_manager_dashboard.HotelManagerDashboardActivity;
import com.fyp.hotelmanagementsystem.ui.login.LoginActivity;
import com.fyp.hotelmanagementsystem.utils.DisplayMessage;
import com.fyp.hotelmanagementsystem.utils.PermissionsCheck;
import com.fyp.hotelmanagementsystem.utils.SharedPreferencesUtility;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class UserDashboardActivity extends AppCompatActivity implements LifecycleOwner {

    private ActivityUserDashboardBinding binding;
    private UserDashboardViewModel viewModel;
    private Location myLocation;
    private static final int REQUEST_CHECK_SETTINGS = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_dashboard);
        UserDashboardViewModelFactory factory = new UserDashboardViewModelFactory(
                AppDatabase.getInstance(getApplicationContext()),
                LocationServices.getFusedLocationProviderClient(getApplicationContext()),
                new MutableLiveData<>()
        );
        viewModel = new ViewModelProvider(this, factory).get(UserDashboardViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        loadData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadData();
    }

    private void loadData(){
        if (SharedPreferencesUtility.getReservedRoom() != 0){
            binding.reservedRoomCard.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
            viewModel.getReservedRoom(SharedPreferencesUtility.getReservedRoom()).observe(this, availableRooms -> {
                Uri uri = Uri.parse(availableRooms.rooms.getPicture());
                binding.roomImage.setImageURI(uri);
                String roomNumber = "Room Number: " + availableRooms.rooms.getRoomNumber();
                String numberOfBeds = "Number of Beds: " + availableRooms.rooms.getNumberOfBeds();
                String rent = "Rent: " + availableRooms.rooms.getRent();
                String roomStatus = "Room Status: "+ availableRooms.rooms.getStatus();
                binding.hotelName.setText(String.valueOf(availableRooms.hotelName));
                binding.roomNumber.setText(roomNumber);
                binding.numberOfBedrooms.setText(numberOfBeds);
                if (availableRooms.rooms.isInternetAvailability()){
                    binding.internetAvailability.setText(R.string.internet_available);
                } else {
                    binding.internetAvailability.setText(R.string.inernet_not_available);
                }
                binding.rent.setText(rent);
                binding.status.setText(roomStatus);
                binding.reservedRoomCard.setOnClickListener(v -> {
                    Intent intent = new Intent(UserDashboardActivity.this, BookRoomActivity.class);
                    intent.putExtra("hotel_name", availableRooms.hotelName);
                    intent.putExtra("hotel_id", availableRooms.rooms.getHotelId());
                    intent.putExtra("room_id", availableRooms.rooms.getId());
                    startActivity(intent);
                });
            });
        } else {
            binding.reservedRoomCard.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);
            if (PermissionsCheck.checkLocationPermission(this, this)){
                verifyLocationSettings();
            }
        }
    }

    private void initRecyclerView() {
        if (myLocation!=null){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            binding.recyclerView.setLayoutManager(linearLayoutManager);
            AvailableRoomsAdapter adapter = new AvailableRoomsAdapter();
            viewModel.getAllHotelWithRooms().observe(this, availableRooms -> {
                List<AvailableRooms> roomsInRange = new ArrayList<>();
                if (availableRooms!=null){
                    for (AvailableRooms room: availableRooms) {
                        Location location = new Location("");
                        location.setLatitude(room.latitude);
                        location.setLongitude(room.longitude);
                        float diff = myLocation.distanceTo(location);
                        if (diff < 5000.0){
                            roomsInRange.add(room);
                        }
                    }
                    adapter.setHotelWithRooms(roomsInRange);
                }
            });
            binding.recyclerView.setAdapter(adapter);
        } else {
            verifyLocationSettings();
        }
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
            startActivity(new Intent(UserDashboardActivity.this, LoginActivity.class));
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionsCheck.LOCATION_PERMISSION_CODE){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                verifyLocationSettings();
                DisplayMessage.longShowMessage(this, "Granted");
            } else {
                DisplayMessage.longShowMessage(this, "Permission for required action is not given");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS){
            if (resultCode == Activity.RESULT_OK){
                getLocation();
            } else {
                DisplayMessage.longShowMessage(this, "No Proper Location Settings found");
            }
        } else {
            DisplayMessage.longShowMessage(this, "No Proper Location Settings found");
        }
    }

    private void getLocation(){
        viewModel.getLocation().observe(this, location -> myLocation = location);
        initRecyclerView();
    }

    private void verifyLocationSettings(){
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        builder.addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        LocationSettingsRequest mLocationSettingsRequest = builder.build();

        SettingsClient mSettingsClient = LocationServices.getSettingsClient(UserDashboardActivity.this);

        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(locationSettingsResponse -> {
                    //Success Perform Task Here
                    getLocation();
                })
                .addOnFailureListener(e -> {
                    int statusCode = ((ApiException) e).getStatusCode();
                    switch (statusCode) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException rae = (ResolvableApiException) e;
                                rae.startResolutionForResult(UserDashboardActivity.this, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException sie) {
                                Log.e("GPS","Unable to execute request.");
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Log.e("GPS","Location settings are inadequate, and cannot be fixed here. Fix in Settings.");
                    }
                })
                .addOnCanceledListener(() -> Log.e("GPS","checkLocationSettings -> onCanceled"));
    }
}
