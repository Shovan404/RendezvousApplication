package com.fod.foodorderdelivery.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fod.foodorderdelivery.Model.Food;
import com.fod.foodorderdelivery.R;

import java.util.List;

public class RestaurantFoodAdapter extends RecyclerView.Adapter<RestaurantFoodAdapter.RestaurantFoodViewHolder> {

    Context context;
    List<Food> restaurantFoodList;
    private RestaurantFoodAdapterEvent restaurantFoodAdapterEvent;

    public RestaurantFoodAdapter(Context context, List<Food> restaurantFoodList,RestaurantFoodAdapterEvent restaurantFoodAdapterEvent) {
        this.context = context;
        this.restaurantFoodList = restaurantFoodList;
        this.restaurantFoodAdapterEvent = restaurantFoodAdapterEvent;
    }

    @NonNull
    @Override
    public RestaurantFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rest_food_layout, parent, false);
        return new RestaurantFoodAdapter.RestaurantFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantFoodViewHolder holder, int position) {
        final Food restaurantFoodView = restaurantFoodList.get(position);
        holder.tvRestaurantFoodName.setText(restaurantFoodView.getFoodName());
        holder.tvPriceRestaurantFood.setText(restaurantFoodView.getFoodPrice());
    }

    @Override
    public int getItemCount() {
        return restaurantFoodList.size();
    }

    public class RestaurantFoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvPriceRestaurantFood, tvRestaurantFoodName;
        public RestaurantFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPriceRestaurantFood = itemView.findViewById(R.id.tvPriceRestaurantFood);
            tvRestaurantFoodName = itemView.findViewById(R.id.tvRestaurantFoodName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            restaurantFoodAdapterEvent.onRestaurantFoodClicked(restaurantFoodList.get(position));
        }
    }

    public interface RestaurantFoodAdapterEvent{
        void onRestaurantFoodClicked(Food food);
    }
}
