package com.fyp.hotelmanagementsystem.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.hotelmanagementsystem.R;
import com.fyp.hotelmanagementsystem.models.AvailableRooms;
import com.fyp.hotelmanagementsystem.models.Rooms;
import com.fyp.hotelmanagementsystem.ui.book_room.BookRoomActivity;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class AvailableRoomsAdapter extends RecyclerView.Adapter<AvailableRoomsAdapter.ViewHolder>  {

    private List<AvailableRooms> rooms;

    public AvailableRoomsAdapter() {
    }

    public void setHotelWithRooms(List<AvailableRooms> hotelWithRooms) {
        this.rooms = hotelWithRooms;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_room_card, parent, false);
        return new AvailableRoomsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AvailableRooms room = rooms.get(position);
        Uri uri = Uri.parse(room.rooms.getPicture());
        holder.roomImage.setImageURI(uri);
        String roomNumber = "Room Number: " + room.rooms.getRoomNumber();
        String numberOfBeds = "Number of Beds: " + room.rooms.getNumberOfBeds();
        String rent = "Rent: " + room.rooms.getRent();
        String roomStatus = "Room Status: "+ room.rooms.getStatus();
        holder.hotelName.setText(String.valueOf(room.hotelName));
        holder.roomNumber.setText(roomNumber);
        holder.numberOfBedRooms.setText(numberOfBeds);
        if (room.rooms.isInternetAvailability()){
            holder.internetAvailability.setText(R.string.internet_available);
        } else {
            holder.internetAvailability.setText(R.string.inernet_not_available);
        }
        holder.rent.setText(rent);
        holder.roomStatus.setText(roomStatus);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), BookRoomActivity.class);
            intent.putExtra("hotel_name", room.hotelName);
            intent.putExtra("hotel_id", room.rooms.getHotelId());
            intent.putExtra("room_id", room.rooms.getId());
            holder.itemView.getContext().startActivity(intent);
        });
//        holder.ratingBar.setRating();
    }

    @Override
    public int getItemCount() {
        if (rooms!=null){
            return rooms.size();
        } else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView roomImage;
        MaterialTextView hotelName, roomNumber, numberOfBedRooms, internetAvailability, rent, roomStatus;
        RatingBar ratingBar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomImage = itemView.findViewById(R.id.room_image);
            hotelName = itemView.findViewById(R.id.hotel_name);
            roomNumber = itemView.findViewById(R.id.room_number);
            numberOfBedRooms = itemView.findViewById(R.id.number_of_bedrooms);
            internetAvailability = itemView.findViewById(R.id.internet_availability);
            rent = itemView.findViewById(R.id.rent);
            roomStatus = itemView.findViewById(R.id.status);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
