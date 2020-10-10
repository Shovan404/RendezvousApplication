package com.fod.foodorderdelivery.API;

import com.fod.foodorderdelivery.Model.Feedback;
import com.fod.foodorderdelivery.Model.Food;
import com.fod.foodorderdelivery.Model.Location;
import com.fod.foodorderdelivery.Model.Restaurant;
import com.fod.foodorderdelivery.Model.User;
import com.fod.foodorderdelivery.ServerResponse.ImageResponse;
import com.fod.foodorderdelivery.ServerResponse.UserResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface FampyAPI {

    @POST("signup")
    Call<UserResponse> registerUser(@Body User users);

    @FormUrlEncoded
    @POST("login")
    Call<UserResponse> checkUser(@Field("email") String email, @Field("password") String password);

    @Multipart
    @POST("upload")
    Call<ImageResponse> uploadImage(@Part MultipartBody.Part img);

    @GET("me")
    Call<User> userDetails(@Header("Authorization") String token);

    @FormUrlEncoded
    @PUT("me")
    Call<User> editUser(@Header("Authorization") String token, @Field("email") String email, @Field("name") String name, @Field("phoneNumber") String phoneNumber, @Field("image") String image);

    @FormUrlEncoded
    @POST("user/change")
    Call<UserResponse> checkPassword(@Header("Authorization") String token,@Field("password") String password);

    @FormUrlEncoded
    @PUT("user/change")
    Call<UserResponse> editPassword(@Header("Authorization") String token, @Field("password") String password);

    @POST("mylocation")
    Call<Location> addMyLocation(@Header("Authorization") String token,@Body Location locations);

    @GET("mylocation")
    Call<List<Location>> getMyLocationList(@Header("Authorization") String token);

    @DELETE("mylocation/{id}")
    Call<Location> deleteMyLocation(@Header("Authorization") String token, @Path("id") String id);

    @POST("feedback")
    Call<UserResponse> sendFeedback (@Body Feedback feedback);

    @GET("restaurant")
    Call<List<Restaurant>> getRestaurantList();

    @GET("restaurant/{id}")
    Call<Restaurant> getRestaurant(@Path("id") String id);

    @GET("food")
    Call<List<Food>> getFoodList();


    @GET("restaurant/food/{id}")
    Call<List<Food>> getRestFoodList(@Path("id") String id);

    @GET("searchfood/{search}")
    Call<List<Food>> searchFoods(@Path("search") String id);

    @FormUrlEncoded
    @POST("order/{id}")
    Call<UserResponse> orderBasket(@Header("Authorization") String token, @Path("id") String id, @Field("foodId") String foodId, @Field("quantity") int quantity, @Field("price") Double price);

    @GET("order/user")
    Call<UserResponse> BasketStatus(@Header("Authorization") String token);
}
