package com.fod.foodorderdelivery.BLL;

import com.fod.foodorderdelivery.API.FampyAPI;
import com.fod.foodorderdelivery.Model.Feedback;
import com.fod.foodorderdelivery.ServerResponse.UserResponse;
import com.fod.foodorderdelivery.URL.URL;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class Feedbacks {

    public boolean sendFeedback(Feedback feedback) {
        boolean flag = false;
        FampyAPI FampyAPI = URL.getInstance().create(FampyAPI.class);
        Call<UserResponse> feedbackCall = FampyAPI.sendFeedback(feedback);
        try {
            Response<UserResponse> feedBackResponse = feedbackCall.execute();
            if (feedBackResponse.isSuccessful() &&
                    feedBackResponse.body().getStatus().equals("Feedback sent successfully")) {
                flag = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
