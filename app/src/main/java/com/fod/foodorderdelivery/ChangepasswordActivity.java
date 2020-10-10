package com.fod.foodorderdelivery;

import android.app.Notification;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.fod.foodorderdelivery.BLL.Users;
import com.fod.foodorderdelivery.Channel.NotificationChannel;
import com.fod.foodorderdelivery.StrictMode.StrictModeClass;

public class ChangepasswordActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManagerCompat;
    EditText etOldPassword, etChangePassword, etReChangePassword;
    Button btnChangePassword;
    Users users = new Users();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        notificationManagerCompat = NotificationManagerCompat.from(this);
        NotificationChannel channel = new NotificationChannel(this);
        channel.notificationChannel();
        etOldPassword = findViewById(R.id.etOldPassword);
        etChangePassword = findViewById(R.id.etChangePassword);
        etReChangePassword = findViewById(R.id.etReChangePassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    changePassword();
                }
            }
        });
    }

    private boolean validate() {
        if (TextUtils.isEmpty(etOldPassword.getText())) {
            etOldPassword.setError("Enter old password");
            etOldPassword.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(etChangePassword.getText())) {
            etChangePassword.setError("Enter new password");
            etChangePassword.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(etReChangePassword.getText())) {
            etReChangePassword.setError("Enter confirm new password");
            etReChangePassword.requestFocus();
            return false;
        } else if (etChangePassword.getText().toString().length() < 6) {
            etChangePassword.setError("Minimum 6 characters");
            etChangePassword.requestFocus();
            return false;
        } else if (etChangePassword.getText().toString().length() > 20) {
            etChangePassword.setError("Maximum 20 characters");
            etChangePassword.requestFocus();
            return false;
        }else if (etOldPassword.getText().toString().equals(etChangePassword.getText().toString())) {
            etChangePassword.setError("Cannot be old password");
            etChangePassword.requestFocus();
            return false;
        } else if (!etChangePassword.getText().toString().equals(etReChangePassword.getText().toString())) {
            etReChangePassword.setError("Incorrect confirm password");
            etReChangePassword.requestFocus();
            return false;
        }
        return true;
    }


    private void changePassword() {
        String oldPassword = etOldPassword.getText().toString();
        String newPassword = etChangePassword.getText().toString();

        StrictModeClass.StrictMode();
        if (users.checkPassword(oldPassword)) {
            if (users.changePassword(newPassword)) {
                changeNotification();
                changePasswordPref();
                finish();
            }
        } else {
            Toast.makeText(this, "Incorrect old password", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeNotification() {
        Notification notification = new NotificationCompat.Builder(this, NotificationChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_done_all_black_24dp)
                .setContentTitle("Password  changed")
                .setContentText("Account password has been changed")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(1, notification);
    }

    private void changePasswordPref() {
        SharedPreferences sharedPreferences = getSharedPreferences("usercredential",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("password",etChangePassword.getText().toString());
        editor.commit();
    }

}
