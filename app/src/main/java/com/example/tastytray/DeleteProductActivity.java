package com.example.tastytray;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteProductActivity extends AppCompatActivity {

    EditText etProductName;
    TextView tvProductId;
    ImageView ivProductPhoto;
    Button btnSearchProduct, btnDeleteProduct;
    DatabaseHelper db;
    Bitmap selectedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product);

        db = new DatabaseHelper(this);

        etProductName = findViewById(R.id.etProductName);
        tvProductId = findViewById(R.id.tvProductId);
        ivProductPhoto = findViewById(R.id.ivProductPhoto);
        btnSearchProduct = findViewById(R.id.btnSearchProduct);
        btnDeleteProduct = findViewById(R.id.btnDeleteProduct);

        btnSearchProduct.setOnClickListener(v -> {
            String name = etProductName.getText().toString();
            if (name.isEmpty()) {
                Toast.makeText(DeleteProductActivity.this, "Please enter a product name", Toast.LENGTH_SHORT).show();
            } else {
                searchProductByName(name);
            }
        });

        btnDeleteProduct.setOnClickListener(v -> {
            String id = tvProductId.getText().toString();
            if (id.isEmpty()) {
                Toast.makeText(DeleteProductActivity.this, "No product selected", Toast.LENGTH_SHORT).show();
            } else {
                boolean isDeleted = db.deleteProduct(Integer.parseInt(id));
                if (isDeleted) {
                    Toast.makeText(DeleteProductActivity.this, "Product deleted successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(DeleteProductActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void searchProductByName(String name) {
        Cursor cursor = db.getProductByName(name);
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndexOrThrow("id");
            int photoIndex = cursor.getColumnIndexOrThrow("photo");
            tvProductId.setText(cursor.getString(idIndex));
            byte[] photo = cursor.getBlob(photoIndex);
            if (photo != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                ivProductPhoto.setImageBitmap(bitmap);
                selectedBitmap = bitmap;
            } else {
                ivProductPhoto.setImageResource(R.drawable.burgur);
            }

            cursor.close();
        } else {
            Toast.makeText(DeleteProductActivity.this, "Product not found", Toast.LENGTH_SHORT).show();
        }
    }
    private void clearFields() {
        tvProductId.setText("");//Clear product id
        etProductName.setText("");
        ivProductPhoto.setImageResource(R.drawable.burgur);
    }
}
