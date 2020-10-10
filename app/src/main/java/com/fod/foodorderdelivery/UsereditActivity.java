package com.fod.foodorderdelivery;

import android.content.Intent;
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
import androidx.loader.content.CursorLoader;

import com.fod.foodorderdelivery.API.FampyAPI;
import com.fod.foodorderdelivery.BLL.Users;
import com.fod.foodorderdelivery.Model.User;
import com.fod.foodorderdelivery.ServerResponse.ImageResponse;
import com.fod.foodorderdelivery.StrictMode.StrictModeClass;
import com.fod.foodorderdelivery.URL.URL;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsereditActivity extends AppCompatActivity {

    TextInputLayout mChangeEmail, mChangeName, mChangePhoneNumber;
    ImageView imgProfileEdit;
    EditText etChangeEmail, etChangeName, etChangePhoneNumber, etCheckPassword;
    Button btnSaveUser;
    String imagePath = "";
    private String imageName = "", email, name, phoneNumber;
    Users users = new Users();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useredit);

        imgProfileEdit = findViewById(R.id.imgProfileEdit);
        etChangeEmail = findViewById(R.id.etChangeEmail);
        etChangeName = findViewById(R.id.etChangeName);
        etChangePhoneNumber = findViewById(R.id.etChangePhoneNumber);
        mChangeEmail = findViewById(R.id.mChangeEmail);
        mChangeName = findViewById(R.id.mChangeName);
        mChangePhoneNumber = findViewById(R.id.mChangePhoneNumber);
        etCheckPassword = findViewById(R.id.etCheckPassword);
        btnSaveUser = findViewById(R.id.btnSaveUser);
        loadLoggedInfo();

        imgProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });
        btnSaveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imagePath.equals("")) {
                    saveImageOnly();
                }
                if (validate()) {
                    checkChanges();
                    editUser();
                }
            }
        });
    }

    private void checkChanges() {
        email = etChangeEmail.getText().toString();
        name = etChangeName.getText().toString();
        phoneNumber = etChangePhoneNumber.getText().toString();
        if (TextUtils.isEmpty(etChangeEmail.getText())) {
            email = mChangeEmail.getHint().toString();
        }
        if (TextUtils.isEmpty(etChangeName.getText())) {
            name = mChangeName.getHint().toString();
        }
        if (TextUtils.isEmpty(etChangePhoneNumber.getText())) {
            phoneNumber = mChangePhoneNumber.getHint().toString();
        }
    }

    private boolean validate() {
        if (!TextUtils.isEmpty(etChangeEmail.getText())) {
            if (!Patterns.EMAIL_ADDRESS.matcher(etChangeEmail.getText()).matches()) {
                etChangeEmail.setError("Enter valid email");
                etChangeEmail.requestFocus();
                return false;
            }
        } else if (!TextUtils.isEmpty(etChangePhoneNumber.getText())) {
            if (!users.isValidPhone(etChangePhoneNumber.getText().toString())) {
                etChangePhoneNumber.setError("Invalid Phone Number");
                etChangePhoneNumber.requestFocus();
                return false;
            }
        } else if (TextUtils.isEmpty(etCheckPassword.getText())) {
            etCheckPassword.setError("Enter password");
            etCheckPassword.requestFocus();
            return false;
        }
        return true;
    }

    private void loadLoggedInfo() {
        FampyAPI fampyAPI = URL.getInstance().create(FampyAPI.class);
        Call<User> userCall = fampyAPI.userDetails(URL.token);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    mChangeEmail.setHint(response.body().getEmail());
                    mChangePhoneNumber.setHint(response.body().getPhoneNumber());
                    mChangeName.setHint(response.body().getName());
                    imageName = response.body().getImage();
                    if (response.body().getImage().equals("")) {
                        imgProfileEdit.setImageResource(R.drawable.ic_person_black_24dp);
                    } else {
                        String imgPath = URL.imagePath + response.body().getImage();
                        Picasso.get().load(imgPath).into(imgProfileEdit);
                    }
                } else {
                    Log.d("msg", "onFailure" + response.code());

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("msg", "onFailure" + t.getLocalizedMessage());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            }
        }
        try {
            Uri uri = data.getData();
            imgProfileEdit.setImageURI(uri);
            imagePath = getRealPathFromUri(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editUser() {
        String password = etCheckPassword.getText().toString();
        StrictModeClass.StrictMode();
        if (users.checkPassword(password)) {
            FampyAPI FampyAPI = URL.getInstance().create(FampyAPI.class);
            Call<User> editCall = FampyAPI.editUser(URL.token, email, name, phoneNumber, imageName);

            editCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(UsereditActivity.this, "Code " + response.code(), Toast.LENGTH_SHORT).show();
                        Log.d("msg", "onFailure" + response.code());
                        return;
                    }
                    Toast.makeText(UsereditActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(UsereditActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(UsereditActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show();
        }
    }


}
