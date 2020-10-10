package com.fod.foodorderdelivery.BLL;

import com.fod.foodorderdelivery.API.FampyAPI;
import com.fod.foodorderdelivery.Model.Basket;
import com.fod.foodorderdelivery.ServerResponse.UserResponse;
import com.fod.foodorderdelivery.StrictMode.StrictModeClass;
import com.fod.foodorderdelivery.URL.URL;
import com.fod.foodorderdelivery.fragment.BasketFragment;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class Orders {

    public static String locationId = "";
    public static String foodId = "";
    public static String foodName = "";
    public static int quantity;
    public static double price;

    public boolean order() {
        boolean flag = false;
        if (validate()) {
            BasketFragment.basketList.add(new Basket(foodId, foodName, quantity, price));
            flag = true;
        }
        return flag;
    }

    private boolean validate() {
        if (foodId.equals("")) {
            return false;
        } else if (foodName.equals("")) {
            return false;
        }
        return true;
    }

    public void orderBasketList(String foodId, int quantity, double price) {
        StrictModeClass.StrictMode();
        FampyAPI FampyAPI = URL.getInstance().create(FampyAPI.class);
        Call<UserResponse> orderCall = FampyAPI.orderBasket(URL.token, locationId, foodId, quantity, price);
        try {
            Response<UserResponse> orderResponse = orderCall.execute();
            if (orderResponse.isSuccessful() && orderResponse.body().getStatus().equals("Created successfully")) {
                BasketFragment.count = BasketFragment.count+1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double calculateVAT(double total){
        double Vat;
        Vat = total * (13f/100);
        return Vat;
    }

    public double calculateDeliveryCost (double total){
        double deliveryCost;
        deliveryCost = total * (7f/100);
        return deliveryCost;
    }

    public double calculateNetTotal (double total){
        double netTotal;
        netTotal = total+ calculateVAT(total) + calculateDeliveryCost(total);
        return netTotal;
    }

    public boolean deliveryStatus() {
        boolean flag = false;
        FampyAPI FampyAPI = URL.getInstance().create(FampyAPI.class);
        Call<UserResponse> statusCall = FampyAPI.BasketStatus(URL.token);
        try {
            Response<UserResponse> orderResponse = statusCall.execute();
            if (orderResponse.isSuccessful() && orderResponse.body().getStatus().equals("Delivered")) {
                flag = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }


}
