package com.fyp.hotelmanagementsystem.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fyp.hotelmanagementsystem.R;
import com.fyp.hotelmanagementsystem.models.HotelWithRooms;
import com.fyp.hotelmanagementsystem.models.Rooms;
import com.fyp.hotelmanagementsystem.ui.book_room.BookRoomActivity;
import com.fyp.hotelmanagementsystem.ui.view_rooms.ViewRoomsActivity;
import com.fyp.hotelmanagementsystem.utils.SharedPreferencesUtility;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class ViewRoomsAdapter extends RecyclerView.Adapter<ViewRoomsAdapter.ViewHolder> {

    private HotelWithRooms hotelWithRooms;
    private List<Rooms> rooms;

    public void setHotelWithRooms(HotelWithRooms hotelWithRooms) {
        this.hotelWithRooms = hotelWithRooms;
        this.rooms = hotelWithRooms.rooms;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_room_card, parent, false);
        return new ViewRoomsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Rooms room = rooms.get(position);
        Uri uri = Uri.parse(room.getPicture());
        holder.roomImage.setImageURI(uri);
        holder.hotelName.setText(hotelWithRooms.hotel.getHotelName());
        String roomNumber = "Room Number: " + room.getRoomNumber();
        holder.roomNumber.setText(roomNumber);
        String numberOfBeds = "Number of Beds: " + room.getNumberOfBeds();
        holder.numberOfBedRooms.setText(numberOfBeds);
        if (room.isInternetAvailability()){
            holder.internetAvailability.setText(R.string.internet_available);
        } else {
            holder.internetAvailability.setText(R.string.inernet_not_available);
        }
        String rent = "Rent: " + room.getRent();
        holder.rent.setText(rent);
        String roomStatus = "Room Status: "+ room.getStatus();
        holder.roomStatus.setText(roomStatus);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ViewRoomsActivity.class);
            intent.putExtra("hotel_name", hotelWithRooms.hotel.getHotelName());
            intent.putExtra("hotel_id", room.getHotelId());
            intent.putExtra("room_id", room.getId());
            holder.itemView.getContext().startActivity(intent);
        });
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

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomImage = itemView.findViewById(R.id.room_image);
            hotelName = itemView.findViewById(R.id.hotel_name);
            roomNumber = itemView.findViewById(R.id.room_number);
            numberOfBedRooms = itemView.findViewById(R.id.number_of_bedrooms);
            internetAvailability = itemView.findViewById(R.id.internet_availability);
            rent = itemView.findViewById(R.id.rent);
            roomStatus = itemView.findViewById(R.id.status);
        }
    }
}
