package com.fod.foodorderdelivery.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fod.foodorderdelivery.Model.Restaurant;
import com.fod.foodorderdelivery.R;
import com.fod.foodorderdelivery.RestaurantfoodActivity;
import com.fod.foodorderdelivery.URL.URL;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    Context context;
    List<Restaurant> restaurantList;

    public RestaurantAdapter(Context context, List<Restaurant> restaurantList) {
        this.context = context;
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_layout, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        final Restaurant restaurantView = restaurantList.get(position);
        holder.txtRestaurant.setText(restaurantView.getName());
        String imageName = restaurantView.getImage();
        String imagePath = URL.imagePath + imageName;
        Picasso.get().load(imagePath).into(holder.imgRestaurant);

        holder.imgRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RestaurantfoodActivity.class);
                RestaurantfoodActivity.restaurantId = restaurantView.getId();
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgRestaurant;
        TextView txtRestaurant;
        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRestaurant = itemView.findViewById(R.id.imgRestaurant);
            txtRestaurant = itemView.findViewById(R.id.txtRestaurant);

        }
    }
}
