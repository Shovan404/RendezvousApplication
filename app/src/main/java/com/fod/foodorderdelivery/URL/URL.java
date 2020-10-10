package com.fod.foodorderdelivery.URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class URL {

   public static final String base_url="http://10.0.2.2:3000/";

    // public static final String base_url="http://192.168.2.8:3000/";


    public static String token = "";
    public static String imagePath = base_url + "uploads/";


    public static Retrofit getInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}