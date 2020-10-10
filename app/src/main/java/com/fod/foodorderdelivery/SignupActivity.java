package com.fod.foodorderdelivery;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.loader.content.CursorLoader;

import com.fod.foodorderdelivery.API.FampyAPI;
import com.fod.foodorderdelivery.BLL.Users;
import com.fod.foodorderdelivery.Channel.NotificationChannel;
import com.fod.foodorderdelivery.Model.User;
import com.fod.foodorderdelivery.ServerResponse.ImageResponse;
import com.fod.foodorderdelivery.ServerResponse.UserResponse;
import com.fod.foodorderdelivery.StrictMode.StrictModeClass;
import com.fod.foodorderdelivery.URL.URL;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManagerCompat;
    ImageView imgProfile;
    EditText etEmail, etName, etPhoneNumber, etNewPassword, etRepassword;
    Button btnSignup;
    String imagePath = "";
    private String imageName;
    Users users = new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        notificationManagerCompat = NotificationManagerCompat.from(this);
        NotificationChannel channel = new NotificationChannel(this);
        channel.notificationChannel();

        etName = findViewById(R.id.etName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        imgProfile = findViewById(R.id.imgProfile);
        etEmail = findViewById(R.id.etEmail);
        etNewPassword = findViewById(R.id.etNewPassword);
        etRepassword = findViewById(R.id.etRepassword);
        btnSignup = findViewById(R.id.btnSignup);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imagePath.equals("")) {
                    imageName = "";
                } else {
                    saveImageOnly();
                }
                if (validate() && validatePassword()) {
                    signUp();
                    signUpPref();
                }
            }
        });
    }

    private void BrowseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(),
                uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Please select an image ", Toast.LENGTH_SHORT).show();
            }
        }
        try {
            Uri uri = data.getData();
            imgProfile.setImageURI(uri);
            imagePath = getRealPathFromUri(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        } else if (TextUtils.isEmpty(etName.getText())) {
            etName.setError("Enter Name");
            etName.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(etPhoneNumber.getText())) {
            etPhoneNumber.setError("Enter Phone Number");
            etPhoneNumber.requestFocus();
            return false;
        }  else if (!users.isValidPhone(etPhoneNumber.getText().toString())) {
            etPhoneNumber.setError("Invalid Phone Number");
            return false;
        }
        return true;
    }


    private boolean validatePassword() {
        if (TextUtils.isEmpty(etNewPassword.getText())) {
            etNewPassword.setError("Enter Password");
            etNewPassword.requestFocus();
            return false;
        } else if (etNewPassword.getText().toString().length() < 6) {
            etNewPassword.setError("Minimum 6 characters");
            etNewPassword.requestFocus();
            return false;
        } else if (etNewPassword.getText().toString().length() > 20) {
            etNewPassword.setError("Maximum 20 characters");
            etNewPassword.requestFocus();
            return false;
        } else if (!etNewPassword.getText().toString().equals(etRepassword.getText().toString())) {
            etRepassword.setError("Incorrect confirm password");
            etRepassword.requestFocus();
            return false;
        }
        return true;
    }

    private void saveImageOnly() {
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile",
                file.getName(), requestBody);

        FampyAPI FampyAPI = URL.getInstance().create(FampyAPI.class);
        Call<ImageResponse> responseBodyCall = FampyAPI.uploadImage(body);

        StrictModeClass.StrictMode();
        try {
            Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
            imageName = imageResponseResponse.body().getFilename();
        } catch (IOException e) {
            Toast.makeText(this, "Error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void signUp() {
        String email = etEmail.getText().toString();
        String name = etName.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();
        String password = etNewPassword.getText().toString();

        User users = new User(email, name, phoneNumber, password, imageName);

        FampyAPI FampyAPI = URL.getInstance().create(FampyAPI.class);
        Call<UserResponse> signUpCall = FampyAPI.registerUser(users);

        signUpCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(SignupActivity.this, "Code " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.d("msg", "onFailure" + response.code());
                    return;
                }
                URL.token = "Bearer " + response.body().getToken();
                signupNotification();
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signUpPref() {
        SharedPreferences sharedPreferences = getSharedPreferences("usercredential", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", etEmail.getText().toString());
        editor.putString("password", etNewPassword.getText().toString());
        editor.commit();
    }

    private void signupNotification() {
        Notification notification = new NotificationCompat.Builder(this, NotificationChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_done_all_black_24dp)
                .setContentTitle("Sign Up")
                .setContentText("Account has been registered successfully")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(1, notification);
    }
}
