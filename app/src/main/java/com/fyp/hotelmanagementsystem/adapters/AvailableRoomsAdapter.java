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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.hotelmanagementsystem.R;
import com.fyp.hotelmanagementsystem.models.AvailableRooms;
import com.fyp.hotelmanagementsystem.models.Rooms;
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
        if (uri!=null && uri.getPath()!=null){
            holder.itemView.getContext().getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            holder.roomImage.setImageURI(uri);
            try {
                InputStream imageStream = holder.itemView.getContext().getContentResolver().openInputStream(uri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                holder.roomImage.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            /*Glide.with(holder.itemView.getContext())
                    .load(new File(uri.getPath()))
                    .into(holder.roomImage);*/

            /*Picasso.get()
                    .load(uri)
                    .into(holder.roomImage);*/
        }
        holder.hotelName.setText(String.valueOf(room.hotelName));
        holder.roomNumber.setText(String.valueOf(room.rooms.getRoomNumber()));
        holder.numberOfBedRooms.setText(String.valueOf(room.rooms.getNumberOfBeds()));
        if (room.rooms.isInternetAvailability()){
            holder.internetAvailability.setText("Available");
        } else {
            holder.internetAvailability.setText("Not Available");
        }
        holder.rent.setText(String.valueOf(room.rooms.getRent()));
        holder.roomStatus.setText(room.rooms.getStatus());
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
