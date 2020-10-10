package com.fod.foodorderdelivery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fod.foodorderdelivery.API.FampyAPI;
import com.fod.foodorderdelivery.Adapter.LocationAdapter;
import com.fod.foodorderdelivery.ChangepasswordActivity;
import com.fod.foodorderdelivery.LocationActivity;
import com.fod.foodorderdelivery.Model.Location;
import com.fod.foodorderdelivery.Model.User;
import com.fod.foodorderdelivery.R;
import com.fod.foodorderdelivery.URL.URL;
import com.fod.foodorderdelivery.UsereditActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    TextView tvmyEmail,tvmyName,tvmyPhonenumber;
    ImageView imgProfiledisplay;
    Button btnAddLocation, btnChangeInfo,btnToChangePassword;
    RecyclerView locationRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        btnAddLocation = view.findViewById(R.id.btnAddLocation);
        tvmyEmail = view.findViewById(R.id.tvmyEmail);
        tvmyName = view.findViewById(R.id.tvmyName);
        imgProfiledisplay = view.findViewById(R.id.imgProfiledisplay);
        tvmyPhonenumber = view.findViewById(R.id.tvmyPhonenumber);
        btnChangeInfo = view.findViewById(R.id.btnChangeInfo);
        btnToChangePassword = view.findViewById(R.id.btnToChangePassword);
        locationRecyclerView = view.findViewById(R.id.locationRecyclerView);

        loadLoggedUserInfo();
        loadMyLocation();

        btnAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getContext(), LocationActivity.class);
                    startActivity(intent);
            }
        });

        btnChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UsereditActivity.class);
                startActivity(intent);
            }
        });

        btnToChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangepasswordActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void loadLoggedUserInfo() {
        FampyAPI fampyAPI = URL.getInstance().create(FampyAPI.class);
        Call<User> userCall = fampyAPI.userDetails(URL.token);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    tvmyEmail.setText(response.body().getEmail());
                    tvmyPhonenumber.setText(response.body().getPhoneNumber());
                    tvmyName.setText(response.body().getName());
                    if (response.body().getImage().equals("")) {
                        imgProfiledisplay.setImageResource(R.drawable.ic_person_black_24dp);
                    } else {
                        String imgPath = URL.imagePath + response.body().getImage();
                        Picasso.get().load(imgPath).into(imgProfiledisplay);
                    }
                }else{
                    Log.d("msg", "onFailure" + response.code());

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("msg", "onFailure" + t.getLocalizedMessage());
            }
        });
    }

    private void loadMyLocation() {
        FampyAPI fampyAPI = URL.getInstance().create(FampyAPI.class);
        Call<List<Location>> locationCall = fampyAPI.getMyLocationList(URL.token);
        locationCall.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Error code" + response.code(), Toast.LENGTH_SHORT).show();
                    Log.d("msg", "onFailure" + response.code());
                    return;
                }
                List<Location> locationList = response.body();
                LocationAdapter locationAdapter = new LocationAdapter(getActivity(), locationList);
                locationRecyclerView.setAdapter(locationAdapter);
                locationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                Log.d("msg", "onFailure " + t.getLocalizedMessage());
                Toast.makeText(getActivity(), "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
