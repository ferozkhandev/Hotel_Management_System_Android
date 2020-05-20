package com.fyp.hotelmanagementsystem.ui.add_hotel;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.fyp.hotelmanagementsystem.R;
import com.fyp.hotelmanagementsystem.databinding.ActivityAddHotelBinding;
import com.fyp.hotelmanagementsystem.utils.DisplayMessage;
import com.fyp.hotelmanagementsystem.utils.PermissionsCheck;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddHotelActivity extends FragmentActivity implements OnMapReadyCallback, AddHotelConfirmationDialog.AddHotelConfirmationDialogListener {

    private GoogleMap mMap;
    private ActivityAddHotelBinding binding;
    private AddHotelViewModel viewModel;
    private static final int REQUEST_CHECK_SETTINGS = 99;
    private Location currentLocation, addedLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_hotel);
        AddHotelViewModelFactory factory = new AddHotelViewModelFactory(
                LocationServices.getFusedLocationProviderClient(getApplicationContext()),
                new MutableLiveData<>()
        );
        viewModel = new ViewModelProvider(this, factory).get(AddHotelViewModel.class);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        binding.myLocation.setOnClickListener(v -> {
            if (PermissionsCheck.checkPermission(this, this)){
                verifyLocationSettings();
            }
        });

        binding.addLocation.setOnClickListener(v -> {
            binding.simpleMapButtons.setVisibility(View.INVISIBLE);
            binding.addLocationMapButtons.setVisibility(View.VISIBLE);
            mMap.setOnMapClickListener(latLng -> {
                mMap.clear();
                LatLng sydney = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions().position(sydney).title("Me");
                markerOptions.icon(bitmapDescriptorFromVector(this, R.drawable.ic_person_pin_circle_black_24dp));
                mMap.addMarker(markerOptions);
                mMap.addMarker(new MarkerOptions().position(latLng));
                addedLocation = new Location(LocationManager.GPS_PROVIDER);
                addedLocation.setLatitude(latLng.latitude);
                addedLocation.setLongitude(latLng.longitude);
            });
        });

        binding.saveLocation.setOnClickListener(v -> {
            if (addedLocation!=null){
                AddHotelConfirmationDialog dialog = new AddHotelConfirmationDialog();
                dialog.show(getSupportFragmentManager(), "Add Hotel Dialog");
            } else {
                DisplayMessage.longShowMessage(this, "Please add a location first");
            }
        });

        binding.cancelAddLocation.setOnClickListener(v -> {
            binding.simpleMapButtons.setVisibility(View.VISIBLE);
            binding.addLocationMapButtons.setVisibility(View.INVISIBLE);
            mMap.setOnMapClickListener(null);
            addedLocation = null;
            mMap.clear();
            LatLng sydney = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions().position(sydney).title("Me");
            markerOptions.icon(bitmapDescriptorFromVector(this, R.drawable.ic_person_pin_circle_black_24dp));
            mMap.addMarker(markerOptions);
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (PermissionsCheck.checkPermission(this, this)){
            verifyLocationSettings();
        }
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
        viewModel.getLocation().observe(this, location -> {
            mMap.clear();
            currentLocation = location;
            LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions().position(sydney).title("Me");
            markerOptions.icon(bitmapDescriptorFromVector(this, R.drawable.ic_person_pin_circle_black_24dp));
            mMap.addMarker(markerOptions);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 18.0f));
        });
    }

    private void verifyLocationSettings(){
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        builder.addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        LocationSettingsRequest mLocationSettingsRequest = builder.build();

        SettingsClient mSettingsClient = LocationServices.getSettingsClient(AddHotelActivity.this);

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
                                rae.startResolutionForResult(AddHotelActivity.this, REQUEST_CHECK_SETTINGS);
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

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_person_pin_circle_black_24dp);
        background.setBounds(0, 0, background.getIntrinsicWidth()+30, background.getIntrinsicHeight()+40);
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 80, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 80);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth()+30, background.getIntrinsicHeight()+40, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void addHotel(String hotelName) {
        binding.simpleMapButtons.setVisibility(View.VISIBLE);
        binding.addLocationMapButtons.setVisibility(View.INVISIBLE);
        DisplayMessage.longShowMessage(this, hotelName);
    }

    @Override
    public void cancelAddHotel() {
        addedLocation = null;
        verifyLocationSettings();
    }

    @Override
    public void addHotelNameError() {
        DisplayMessage.longShowMessage(this,"Looks like you haven't added a hotel name");
    }
}
