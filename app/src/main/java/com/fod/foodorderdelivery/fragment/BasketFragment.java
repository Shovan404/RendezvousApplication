package com.fod.foodorderdelivery.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fod.foodorderdelivery.Adapter.BasketAdapter;
import com.fod.foodorderdelivery.BLL.Orders;
import com.fod.foodorderdelivery.FeedbackActivity;
import com.fod.foodorderdelivery.Model.Basket;
import com.fod.foodorderdelivery.R;
import com.fod.foodorderdelivery.Services.FampyService;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasketFragment extends Fragment {
    public static List<Basket> basketList = new ArrayList<>();
    Button btnSendFeedback, btnOrderBasket;
    TextView tvSubTotal, tvVAT, tvDeliveryCharge, tvNetTotal;
    RecyclerView basketRecyclerView;
    Orders orders = new Orders();
    public static int count;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basket, container, false);
        btnSendFeedback = view.findViewById(R.id.btnSendFeedback);
        btnOrderBasket = view.findViewById(R.id.btnOrderBasket);
        tvSubTotal = view.findViewById(R.id.tvSubTotal);
        tvVAT = view.findViewById(R.id.tvVAT);
        tvDeliveryCharge = view.findViewById(R.id.tvDeliveryCharge);
        tvNetTotal = view.findViewById(R.id.tvNetTotal);
        basketRecyclerView = view.findViewById(R.id.basketRecyclerView);
        load();
        btnSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(intent);
            }
        });

        btnOrderBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 0;
                order();
            }
        });
        return view;
    }

    public void load() {
        if (!basketList.isEmpty()) {
            BasketAdapter basketAdapter = new BasketAdapter(getContext(), basketList);
            basketRecyclerView.setAdapter(basketAdapter);
            basketRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        calculateTotal();
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    public void calculateTotal() {
        double price = 0;
        for (int counter = 0; counter < basketList.size(); counter++) {
            price = price + (basketList.get(counter).getPrice() * basketList.get(counter).getQuantity());
        }
        tvSubTotal.setText(price + "");
        tvVAT.setText(String.format("%.2f",orders.calculateVAT(price)) + "");
        tvDeliveryCharge.setText(String.format("%.2f",orders.calculateDeliveryCost(price)) + "");
        tvNetTotal.setText(String.format("%.2f",orders.calculateNetTotal(price)) + "");
    }

    public void order() {
        String foodId;
        int quantity;
        double price;
        getLocationPref();
        int counter;
        if (orders.locationId.equals("")) {
            Toast.makeText(getContext(), "Set location for the order", Toast.LENGTH_SHORT).show();
            return;
        }
        for (counter = 0; counter < basketList.size(); counter++) {
            foodId = basketList.get(counter).getFoodId();
            quantity = basketList.get(counter).getQuantity();
            price = basketList.get(counter).getPrice();
            orders.orderBasketList(foodId, quantity, price);
        }
        if (count == counter) {
            Toast.makeText(getContext(), "All food added for order successfully", Toast.LENGTH_SHORT).show();
            startMyService();
        }
    }

    private void getLocationPref() {
        if (Orders.locationId.equals("")) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("location", getContext().MODE_PRIVATE);
            Orders.locationId = sharedPreferences.getString("locationId", "");
        }
    }

    private void startMyService() {
        getActivity().startService(new Intent(getContext(), FampyService.class));
    }


}
