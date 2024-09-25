package com.example.tastytray;



import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tastytray.databinding.ActivityOrderBinding;

public class OrderActivity extends AppCompatActivity {

    private ActivityOrderBinding binding;
    private DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        helper = new DBHelper(this);


        double price = getIntent().getDoubleExtra("price", 0.0);//original price and sets it into tvProductPrice
        binding.tvProductPrice.setText(String.valueOf(price));

        binding.btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = binding.etCustomerName.getText().toString();
                String phoneNumber = binding.etPhoneNumber.getText().toString();
                String quantityStr = binding.etQuantity.getText().toString();
                String priceStr = binding.tvProductPrice.getText().toString();
                if (TextUtils.isEmpty(productName) || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(quantityStr)) {
                    Toast.makeText(OrderActivity.this, "All fields are required.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int orderQuantity;
                double price;
                try {
                    orderQuantity = Integer.parseInt(quantityStr);
                    price = Double.parseDouble(priceStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(OrderActivity.this, "Invalid quantity or price.", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isInserted = helper.insertOrder(productName, phoneNumber, orderQuantity);
                //boolean isInserted = helper.insertOrder(productName, phoneNumber, orderQuantity, doubledPrice);
                if (isInserted) {
                    Toast.makeText(OrderActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OrderActivity.this, "Error.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.btnViewOrders.setOnClickListener(v -> {
            Intent intent = new Intent(OrderActivity.this, MyOrdersActivity.class);
            startActivity(intent);
        });
    }
}

