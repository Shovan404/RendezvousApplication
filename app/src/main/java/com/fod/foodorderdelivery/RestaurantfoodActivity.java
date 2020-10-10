package com.fod.foodorderdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fod.foodorderdelivery.API.FampyAPI;
import com.fod.foodorderdelivery.Adapter.RestaurantFoodAdapter;
import com.fod.foodorderdelivery.BLL.Orders;
import com.fod.foodorderdelivery.Model.Food;
import com.fod.foodorderdelivery.Model.Restaurant;
import com.fod.foodorderdelivery.URL.URL;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantfoodActivity extends AppCompatActivity implements RestaurantFoodAdapter.RestaurantFoodAdapterEvent {

    public static String restaurantId;
    ImageView imgRestPic;
    TextView tvRestName, tvRestLocation, tvRestAbout;
    RecyclerView restaurantFoodRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantfood);
        imgRestPic = findViewById(R.id.imgRestPic);
        tvRestName = findViewById(R.id.tvRestName);
        tvRestLocation = findViewById(R.id.tvRestLocation);
        tvRestAbout = findViewById(R.id.tvRestAbout);
        restaurantFoodRecyclerView = findViewById(R.id.restaurantFoodRecyclerView);
        loadRestaurantDetail();
        loadRestaurantFoodList();

    }

    private void loadRestaurantDetail() {
        FampyAPI fampyAPI = URL.getInstance().create(FampyAPI.class);
        Call<Restaurant> restaurantCall = fampyAPI.getRestaurant(restaurantId);
        try {
            Response<Restaurant> restaurantResponse = restaurantCall.execute();
            if (restaurantResponse.isSuccessful()) {
                tvRestName.setText(restaurantResponse.body().getName());
                tvRestLocation.setText(restaurantResponse.body().getLocation());
                tvRestAbout.setText(restaurantResponse.body().getAbout());
                String imgPath = URL.imagePath + restaurantResponse.body().getImage();
                Picasso.get().load(imgPath).into(imgRestPic);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadRestaurantFoodList() {
        FampyAPI fampyAPI = URL.getInstance().create(FampyAPI.class);
        Call<List<Food>> listRestCall = fampyAPI.getRestFoodList(restaurantId);

        listRestCall.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(RestaurantfoodActivity.this, "Error code" + response.code(), Toast.LENGTH_SHORT).show();
                    Log.d("msg", "onFailure" + response.code());
                    return;
                }
                List<Food> restFoodList = response.body();
                RestaurantFoodAdapter restaurantFoodAdapterAdapter = new RestaurantFoodAdapter(RestaurantfoodActivity.this, restFoodList, RestaurantfoodActivity.this);
                restaurantFoodRecyclerView.setAdapter(restaurantFoodAdapterAdapter);
                restaurantFoodRecyclerView.setLayoutManager(new LinearLayoutManager(RestaurantfoodActivity.this, LinearLayoutManager.VERTICAL, false));
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                Log.d("msg", "onFailure " + t.getLocalizedMessage());
                Toast.makeText(RestaurantfoodActivity.this, "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRestaurantFoodClicked(Food food) {
        Intent intent = new Intent(this, FoodDetailActivity.class);
        Orders orders = new Orders();
        orders.foodId = food.getId();
        orders.foodName = food.getFoodName();
        orders.price = Double.parseDouble(food.getFoodPrice());
        startActivity(intent);
    }
}
