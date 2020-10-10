package com.fod.foodorderdelivery;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fod.foodorderdelivery.BLL.Feedbacks;
import com.fod.foodorderdelivery.Model.Feedback;
import com.fod.foodorderdelivery.StrictMode.StrictModeClass;

public class FeedbackActivity extends AppCompatActivity {

    EditText etFeedbackMsg, etFeedbackEmail;
    Button btnFeedback;
    Feedbacks feedbacks = new Feedbacks();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        etFeedbackMsg = findViewById(R.id.etFeedbackMsg);
        etFeedbackEmail = findViewById(R.id.etFeedbackEmail);
        btnFeedback = findViewById(R.id.btnFeedback);

        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    sendFeedback();
                }
            }
        });

    }


    private boolean validate() {
        if (TextUtils.isEmpty(etFeedbackEmail.getText())) {
            etFeedbackEmail.setError("Enter email");
            etFeedbackEmail.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(etFeedbackEmail.getText()).matches()) {
            etFeedbackEmail.setError("Enter valid email");
            etFeedbackEmail.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(etFeedbackMsg.getText())) {
            etFeedbackMsg.setError("Enter Feedback message");
            etFeedbackMsg.requestFocus();
            return false;
        }
        return true;
    }

    private void sendFeedback() {
        String email = etFeedbackEmail.getText().toString();
        String message = etFeedbackMsg.getText().toString();

        Feedback feedback = new Feedback(email, message);
        StrictModeClass.StrictMode();
        if (feedbacks.sendFeedback(feedback)) {
            Toast.makeText(this, "Feedback Sent successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to send", Toast.LENGTH_SHORT).show();
        }

    }

}
