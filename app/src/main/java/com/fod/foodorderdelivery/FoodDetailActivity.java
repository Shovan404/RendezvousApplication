package com.fod.foodorderdelivery;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fod.foodorderdelivery.BLL.Orders;

public class FoodDetailActivity extends AppCompatActivity {

    Button btnConfirmFoodOrder;
    EditText etQuantity;
    TextView tvmFoodPrice, tvmFoodName;
    Orders orders = new Orders();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .6), (int) (height * .3));

        btnConfirmFoodOrder = findViewById(R.id.btnConfirmFoodOrder);
        tvmFoodPrice = findViewById(R.id.tvmFoodPrice);
        tvmFoodName = findViewById(R.id.tvmFoodName);
        etQuantity = findViewById(R.id.etQuantity);

        loadFoodDetails();

        btnConfirmFoodOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    orders.quantity = Integer.parseInt(etQuantity.getText().toString());
                    if (orders.order()) {
                        Toast.makeText(FoodDetailActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(FoodDetailActivity.this, "Failed to add", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            }
        });

    }

    private boolean validate() {
        if (TextUtils.isEmpty(etQuantity.getText())) {
            etQuantity.setError("Enter quantity");
            etQuantity.requestFocus();
            return false;
        } else if (Integer.parseInt(etQuantity.getText().toString()) <= 0) {
            etQuantity.setError("Invalid quantity");
            etQuantity.requestFocus();
            return false;
        }
        return true;
    }

    private void loadFoodDetails() {

        String foodName = orders.foodName;
        Double price = orders.price;
        tvmFoodName.setText(foodName);
        tvmFoodPrice.setText(price.toString());


    }

}
