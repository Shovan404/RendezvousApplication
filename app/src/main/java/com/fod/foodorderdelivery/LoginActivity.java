package com.fod.foodorderdelivery;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.fod.foodorderdelivery.BLL.Users;
import com.fod.foodorderdelivery.Channel.NotificationChannel;
import com.fod.foodorderdelivery.StrictMode.StrictModeClass;

public class LoginActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManagerCompat;
    TextView tvCreateAccount,tvForgotpassword;
    Button btnLogin;
    EditText etEmail, etPassword;
    Users user = new Users();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        notificationManagerCompat = NotificationManagerCompat.from(this);
        NotificationChannel channel = new NotificationChannel(this);
        channel.notificationChannel();
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvCreateAccount = findViewById(R.id.tvCreateAccount);
        tvForgotpassword = findViewById(R.id.tvForgotpassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                    logIn();
            }
        });

        tvForgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });

        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean validate() {
        if (TextUtils.isEmpty(etEmail.getText())) {
            etEmail.setError("Enter email");
            etEmail.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText()).matches()) {
            etEmail.setError("Enter valid email");
            etEmail.requestFocus();
            return false;
        }else if (TextUtils.isEmpty(etPassword.getText())) {
            etPassword.setError("Enter password");
            etPassword.requestFocus();
            return false;
        }
        return true;
    }

    private void logInPref() {
        SharedPreferences sharedPreferences = getSharedPreferences("usercredential",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email",etEmail.getText().toString());
        editor.putString("password",etPassword.getText().toString());
        editor.commit();
    }

    private void logIn() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        StrictModeClass.StrictMode();
        if (user.userAuthentication(email, password)) {
            Toast.makeText(this, "User logged in", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            logInPref();
            loginNotification();
            finish();
        } else {
            Toast.makeText(this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
        }
    }

    private void loginNotification() {
        Notification notification = new NotificationCompat.Builder(this, NotificationChannel.CHANNEL_2)
                .setSmallIcon(R.drawable.ic_chat_black_24dp)
                .setContentTitle("Login")
                .setContentText("Successfully logged in")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(2, notification);
    }

}
