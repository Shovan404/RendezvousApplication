package com.fod.foodorderdelivery.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fod.foodorderdelivery.Model.Basket;
import com.fod.foodorderdelivery.R;

import java.util.List;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.BasketViewHolder> {

    Context context;
    List<Basket> basketList;

    public BasketAdapter(Context context, List<Basket> basketList) {
        this.context = context;
        this.basketList = basketList;
    }

    @NonNull
    @Override
    public BasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.basket_layout, parent, false);
        return new BasketAdapter.BasketViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BasketViewHolder holder, final int position) {
        final Basket basketView = basketList.get(position);
        holder.tvBasketFoodName.setText(basketView.getFoodName());
        holder.tvBasketQuantity.setText(basketView.getQuantity() + "");
        double price = basketView.getPrice()*basketView.getQuantity();
        holder.tvBasketPrice.setText( price + "");
        holder.imgDeleteBasketItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basketList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, basketList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return basketList.size();
    }

    public class BasketViewHolder extends RecyclerView.ViewHolder {

        TextView tvBasketFoodName, tvBasketPrice, tvBasketQuantity;
        ImageView imgDeleteBasketItem;

        public BasketViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBasketFoodName = itemView.findViewById(R.id.tvBasketFoodName);
            tvBasketPrice = itemView.findViewById(R.id.tvBasketPrice);
            tvBasketQuantity = itemView.findViewById(R.id.tvBasketQuantity);
            imgDeleteBasketItem = itemView.findViewById(R.id.imgDeleteBasketItem);
        }
    }
}
