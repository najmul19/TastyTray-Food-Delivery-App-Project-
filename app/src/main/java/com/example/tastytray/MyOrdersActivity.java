package com.example.tastytray;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.tastytray.databinding.ActivityMyOrdersBinding;

import java.util.ArrayList;

public class MyOrdersActivity extends AppCompatActivity {

    private ActivityMyOrdersBinding binding;
    private DBHelper dbHelper;
    private MyOrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DBHelper(this);
        ArrayList<MyOrderModel> orderList = dbHelper.getOrders();

        orderAdapter = new MyOrderAdapter(orderList,this);
        binding.recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewOrders.setAdapter(orderAdapter);
    }
}
