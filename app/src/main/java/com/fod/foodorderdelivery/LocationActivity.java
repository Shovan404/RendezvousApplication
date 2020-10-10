package com.fod.foodorderdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fod.foodorderdelivery.API.FampyAPI;
import com.fod.foodorderdelivery.Model.Location;
import com.fod.foodorderdelivery.URL.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationActivity extends AppCompatActivity {

    public static Double latitude, longitude;
    Button btnAddCurrentLocation, btnSaveLocation;
    EditText etLocationName, etAdditionalInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        btnAddCurrentLocation = findViewById(R.id.btnAddCurrentLocation);
        btnSaveLocation = findViewById(R.id.btnSaveLocation);
        etLocationName = findViewById(R.id.etLocationName);
        etAdditionalInfo = findViewById(R.id.etAdditionalInfo);

        btnAddCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        btnSaveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    addLocation();
                    Intent intent = new Intent(LocationActivity.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }


    private void addLocation() {
        String name = etLocationName.getText().toString();
        String lati = latitude.toString();
        String longi = longitude.toString();
        String etadditionalInfo = etAdditionalInfo.getText().toString();

        Location location = new Location(name, lati, longi, etadditionalInfo);

        FampyAPI FampyAPI = URL.getInstance().create(FampyAPI.class);
        Call<Location> locationCall = FampyAPI.addMyLocation(URL.token, location);

        locationCall.enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(LocationActivity.this, "Code " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.d("msg", "onFailure" + response.code());
                    return;
                }
                Toast.makeText(LocationActivity.this, "Location added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {
                Toast.makeText(LocationActivity.this, "Code " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("msg", "onFailure" + t.getLocalizedMessage());
            }
        });
    }

    private boolean validate() {
        if (TextUtils.isEmpty(etLocationName.getText())) {
            etLocationName.setError("Enter Location name");
            etLocationName.requestFocus();
            return false;
        } else if (latitude == null && longitude == null) {
            Toast.makeText(this, "Select the current location", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
