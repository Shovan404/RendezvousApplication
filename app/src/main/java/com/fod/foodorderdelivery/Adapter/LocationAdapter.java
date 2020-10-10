package com.fod.foodorderdelivery.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fod.foodorderdelivery.BLL.Locations;
import com.fod.foodorderdelivery.BLL.Orders;
import com.fod.foodorderdelivery.Model.Location;
import com.fod.foodorderdelivery.R;
import com.fod.foodorderdelivery.StrictMode.StrictModeClass;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    Context context;
    List<Location> locationList;
    Locations locations = new Locations();

    public LocationAdapter(Context context, List<Location> locationList) {
        this.context = context;
        this.locationList = locationList;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_layout, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, final int position) {
        final Location locationView = locationList.get(position);
        holder.tvLocationName.setText(locationView.getName());

        holder.imgDeleteLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrictModeClass.StrictMode();
                if (locations.deleteLocation(locationView.getId())) {
                    locationList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, locationList.size());
                    Toast.makeText(context, "Location deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Unable to delete", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.imgDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Orders orders = new Orders();
                orders.locationId = locationView.getId();
                locationPref(locationView.getId());
                Toast.makeText(context, locationView.getName()+ " Selected for order", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder{
        TextView tvLocationName;
        ImageView imgDeleteLocation,imgDefault;
        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLocationName = itemView.findViewById(R.id.tvLocationName);
            imgDeleteLocation = itemView.findViewById(R.id.imgDeleteLocation);
            imgDefault = itemView.findViewById(R.id.imgDefault);

        }
    }

    private void locationPref(String location) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("location",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("locationId",location);
        editor.commit();
    }
}
