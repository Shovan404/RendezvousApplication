package com.fod.foodorderdelivery.BLL;

import com.fod.foodorderdelivery.API.FampyAPI;
import com.fod.foodorderdelivery.Model.Location;
import com.fod.foodorderdelivery.URL.URL;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class Locations {

    public boolean deleteLocation(String id) {
        boolean flag = false;
        FampyAPI fampyAPI = URL.getInstance().create(FampyAPI.class);
        Call<Location> locationCall = fampyAPI.deleteMyLocation(URL.token, id);
        try {
            Response<Location> deleteResponse = locationCall.execute();
            if (deleteResponse.isSuccessful()) {
                flag = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
