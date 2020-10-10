package com.fod.foodorderdelivery.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.fod.foodorderdelivery.API.FampyAPI;
import com.fod.foodorderdelivery.Adapter.FoodAdapter;
import com.fod.foodorderdelivery.Adapter.RestaurantAdapter;
import com.fod.foodorderdelivery.Adapter.viewPageAdapter;
import com.fod.foodorderdelivery.Model.Food;
import com.fod.foodorderdelivery.Model.Restaurant;
import com.fod.foodorderdelivery.R;
import com.fod.foodorderdelivery.URL.URL;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    ViewPager viewPager;
    RecyclerView restaurantRecyclerView, foodRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = view.findViewById(R.id.myPageView);
        restaurantRecyclerView = view.findViewById(R.id.restaurantRecyclerView);
        foodRecyclerView = view.findViewById(R.id.foodRecyclerView);
        viewPageAdapter viewpageAdapter = new viewPageAdapter(getContext());
        viewPager.setAdapter(viewpageAdapter);
        try {
            RecyclerFood();
            RecyclerRestaurant();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void RecyclerFood() {
        FampyAPI fampyAPI = URL.getInstance().create(FampyAPI.class);
        Call<List<Food>> listCall = fampyAPI.getFoodList();

        listCall.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Error code" + response.code(), Toast.LENGTH_SHORT).show();
                    Log.d("msg", "onFailure" + response.code());
                    return;
                }
                List<Food> foodList = response.body();
                FoodAdapter productAdapter = new FoodAdapter(getActivity(), foodList);
                foodRecyclerView.setAdapter(productAdapter);
                foodRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                Log.d("msg", "onFailure " + t.getLocalizedMessage());
                Toast.makeText(getActivity(), "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void RecyclerRestaurant() {
        FampyAPI fampyAPI = URL.getInstance().create(FampyAPI.class);
        Call<List<Restaurant>> listCall = fampyAPI.getRestaurantList();

        listCall.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Error code" + response.code(), Toast.LENGTH_SHORT).show();
                    Log.d("msg", "onFailure" + response.code());
                    return;
                }
                List<Restaurant> restaurantList = response.body();
                RestaurantAdapter restaurantAdapter = new RestaurantAdapter(getActivity(), restaurantList);
                restaurantRecyclerView.setAdapter(restaurantAdapter);
                restaurantRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                Log.d("msg", "onFailure " + t.getLocalizedMessage());
                Toast.makeText(getActivity(), "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
