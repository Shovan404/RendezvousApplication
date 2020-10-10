package com.fod.foodorderdelivery.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fod.foodorderdelivery.API.FampyAPI;
import com.fod.foodorderdelivery.Adapter.FoodAdapter;
import com.fod.foodorderdelivery.Model.Food;
import com.fod.foodorderdelivery.R;
import com.fod.foodorderdelivery.URL.URL;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    SearchView searchView;
    RecyclerView searchRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchView = view.findViewById(R.id.searchView);
        searchRecyclerView = view.findViewById(R.id.searchRecyclerView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchRecycler(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return view;
    }

    private void searchRecycler(String searchText) {
        FampyAPI fampyAPI = URL.getInstance().create(FampyAPI.class);
        Call<List<Food>> listCall = fampyAPI.searchFoods(searchText);

        listCall.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Error code" + response.code(), Toast.LENGTH_SHORT).show();
                    Log.d("msg", "onFailure" + response.code());
                    return;
                }
                List<Food> foodList = response.body();
                FoodAdapter foodAdapter = new FoodAdapter(getActivity(), foodList);
                searchRecyclerView.setAdapter(foodAdapter);
                searchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                Log.d("msg", "onFailure " + t.getLocalizedMessage());
                Toast.makeText(getActivity(), "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
