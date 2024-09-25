package com.example.tastytray;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    Button btnInsertProduct, btnUpdateProduct, btnDeleteProduct, btnViewProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnInsertProduct = findViewById(R.id.btnInsertProduct);
        btnUpdateProduct = findViewById(R.id.btnUpdateProduct);
        btnDeleteProduct = findViewById(R.id.btnDeleteProduct);
        btnViewProducts = findViewById(R.id.btnViewProducts);

        btnInsertProduct.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, InsertProductActivity.class);
            startActivity(intent);
        });

        btnUpdateProduct.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, UpdateProductActivity.class);
            startActivity(intent);
        });

        btnDeleteProduct.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, DeleteProductActivity.class);
            startActivity(intent);
        });

        btnViewProducts.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, ViewProductsActivity.class);
            startActivity(intent);
        });
    }
}
