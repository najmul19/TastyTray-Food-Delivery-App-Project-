package com.example.tastytray;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewProductsActivity extends AppCompatActivity implements ProductAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    DatabaseHelper db;
    List<Product> productList;
    ProductAdapter adapter;//binding product data to the RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_products);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new DatabaseHelper(this);
        productList = db.getAllProducts();

        adapter = new ProductAdapter(productList, this); //this activity as a item click listener
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {//ProductAdapter.OnItemClickListener interface
        Product selectedProduct = productList.get(position);
        Intent intent = new Intent(ViewProductsActivity.this, OrderActivity.class);//OrderActivity with product details
        intent.putExtra("productId", selectedProduct.getId());
        startActivity(intent);
    }
}
