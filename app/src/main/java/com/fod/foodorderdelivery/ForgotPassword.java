package com.fod.foodorderdelivery;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPassword extends AppCompatActivity {

    EditText etForgotEmail;
    Button btnForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        etForgotEmail = findViewById(R.id.etForgotEmail);
        btnForgotPassword = findViewById(R.id.btnForgotPassword);

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    Toast.makeText(ForgotPassword.this, "Request sent.\n Please check your email.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private boolean validate() {
        if (TextUtils.isEmpty(etForgotEmail.getText())) {
            etForgotEmail.setError("Enter email");
            etForgotEmail.requestFocus();
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(etForgotEmail.getText()).matches()) {
            etForgotEmail.setError("Enter valid email");
            etForgotEmail.requestFocus();
            return false;
        }
        return true;
    }
}
