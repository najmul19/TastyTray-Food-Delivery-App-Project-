package com.example.tastytray;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements ProductAdapter.OnItemClickListener {

    private static final String TAG = "HomeActivity";

    RecyclerView recyclerView;
    DatabaseHelper db;
    List<Product> productList;
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//which is called when the activity is create
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new DatabaseHelper(this);
        try {
            productList = db.getAllProducts();//Retrieves all products from the database and assigns them to productList
            adapter = new ProductAdapter(productList, this);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving products: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public void onItemClick(int position) {
        if (productList != null && position >= 0 && position < productList.size()) {
            Product selectedProduct = productList.get(position);
            Intent intent = new Intent(HomeActivity.this, OrderActivity.class);
            intent.putExtra("productId", selectedProduct.getId());
            intent.putExtra("price", selectedProduct.getPrice());
            startActivity(intent);
        } else {
            Log.e(TAG, "Invalid product position: " + position);//Logs an error message if the position is invalid
        }
    }
}
