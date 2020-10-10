package com.fod.foodorderdelivery.BLL;

import com.fod.foodorderdelivery.API.FampyAPI;
import com.fod.foodorderdelivery.ServerResponse.UserResponse;
import com.fod.foodorderdelivery.URL.URL;

import java.io.IOException;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Response;

public class Users {

    public boolean userAuthentication(String email, String password) {

        boolean loginFlag = false;

        FampyAPI fampyAPI = URL.getInstance().create(FampyAPI.class);
        Call<UserResponse> usersCall = fampyAPI.checkUser(email, password);
        try {
            Response<UserResponse> loginResponse = usersCall.execute();
            if (loginResponse.isSuccessful() &&
                    loginResponse.body().getStatus().equals("Login success!")) {
                URL.token = "Bearer " + loginResponse.body().getToken();
                loginFlag = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loginFlag;
    }

    public boolean checkPassword(String password){
        boolean flag = false;

        FampyAPI fampyAPI = URL.getInstance().create(FampyAPI.class);
        Call<UserResponse> usersCall = fampyAPI.checkPassword(URL.token,password);
        try {
            Response<UserResponse> checkResponse = usersCall.execute();
            if (checkResponse.isSuccessful() &&
                    checkResponse.body().getStatus().equals("Correct password!")) {
                flag = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean changePassword(String password){
        boolean flag = false;

        FampyAPI fampyAPI = URL.getInstance().create(FampyAPI.class);
        Call<UserResponse> usersCall = fampyAPI.editPassword(URL.token,password);
        try {
            Response<UserResponse> checkResponse = usersCall.execute();
            if (checkResponse.isSuccessful() &&
                    checkResponse.body().getStatus().equals("password changed")) {
                flag = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }


    public boolean isValidPhone(String phone) {
        boolean check;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() < 6 || phone.length() > 13) {
                check = false;
            } else {
                check = true;
            }
        } else {
            check = false;
        }
        return check;
    }

}
