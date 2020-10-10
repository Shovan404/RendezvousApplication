package com.fod.foodorderdelivery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.fod.foodorderdelivery.BLL.Users;
import com.fod.foodorderdelivery.StrictMode.StrictModeClass;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUser();
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        }, 2000);

    }

    private void checkUser(){
        Users users = new Users();
        SharedPreferences sharedPreferences = getSharedPreferences("usercredential",MODE_PRIVATE);
        String email = sharedPreferences.getString("email","");
        String password = sharedPreferences.getString("password","");
        StrictModeClass.StrictMode();
        users.userAuthentication(email, password);
    }

}
