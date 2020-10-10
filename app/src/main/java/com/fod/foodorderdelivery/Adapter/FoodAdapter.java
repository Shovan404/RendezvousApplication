package com.fod.foodorderdelivery.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fod.foodorderdelivery.BLL.Orders;
import com.fod.foodorderdelivery.FoodDetailActivity;
import com.fod.foodorderdelivery.Model.Food;
import com.fod.foodorderdelivery.R;
import com.fod.foodorderdelivery.URL.URL;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    Context context;
    List<Food> foodList;

    public FoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }


    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_layout, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodViewHolder holder, int position) {
        final Food foodView = foodList.get(position);
        holder.tvFoodName.setText(foodView.getFoodName());
        String price = "Rs. " + foodView.getFoodPrice();
        holder.tvFoodPrice.setText(price);
        String imageName = foodView.getImage();
        String imagePath = URL.imagePath + imageName;
        Picasso.get().load(imagePath).into(holder.imgFood);

        holder.imgAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FoodDetailActivity.class);
                Orders orders = new Orders();
                orders.foodId = foodView.getId();
                orders.foodName = foodView.getFoodName();
                orders.price = Double.parseDouble(foodView.getFoodPrice());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodName,tvFoodPrice;
        ImageView imgFood, imgAddToCart;


        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvFoodPrice = itemView.findViewById(R.id.tvFoodPrice);
            imgFood = itemView.findViewById(R.id.imgFood);
            imgAddToCart = itemView.findViewById(R.id.imgAddToCart);
        }
    }


}
