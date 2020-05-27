package com.fyp.hotelmanagementsystem.adapters;

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
//        holder.roomImage.setImageURI(uri);
        if (uri!=null && uri.getPath()!=null){
            /*Glide.with(holder.itemView.getContext())
                    .load(new File(uri.getPath()))
                    .into(holder.roomImage);*/

            Picasso.get()
                    .load(uri)
                    .into(holder.roomImage);
        }
        holder.hotelName.setText(String.valueOf(hotelWithRooms.hotel.getHotelName()));
        holder.roomNumber.setText(String.valueOf(room.getRoomNumber()));
        holder.numberOfBedRooms.setText(String.valueOf(room.getNumberOfBeds()));
        if (room.isInternetAvailability()){
            holder.internetAvailability.setText("Available");
        } else {
            holder.internetAvailability.setText("Not Available");
        }
        holder.rent.setText(String.valueOf(room.getRent()));
        holder.roomStatus.setText(room.getStatus());
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
            roomStatus = itemView.findViewById(R.id.room_availability);
        }
    }
}
