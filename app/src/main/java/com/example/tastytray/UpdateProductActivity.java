package com.example.tastytray;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UpdateProductActivity extends AppCompatActivity {

    TextView tvProductId;
    EditText etProductName, etProductPrice, etProductQuantity;
    ImageView ivProductPhoto;
    Button btnSelectImage, btnUpdateProduct, btnSearchProduct;
    DatabaseHelper db;
    Bitmap selectedBitmap;

    private static final int REQUEST_CODE_SELECT_IMAGE = 1;// Request code for image selection

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        db = new DatabaseHelper(this);

        tvProductId = findViewById(R.id.tvProductId);
        etProductName = findViewById(R.id.etProductName);
        etProductPrice = findViewById(R.id.etProductPrice);
        etProductQuantity = findViewById(R.id.etProductQuantity);
        ivProductPhoto = findViewById(R.id.ivProductPhoto);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnUpdateProduct = findViewById(R.id.btnUpdateProduct);
        btnSearchProduct = findViewById(R.id.btnSearchProduct);

        btnSelectImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//select an image
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE); // Start the activity for result
        });

        btnSearchProduct.setOnClickListener(v -> {
            String name = etProductName.getText().toString();
            if (name.isEmpty()) {
                Toast.makeText(UpdateProductActivity.this, "Please enter a product name", Toast.LENGTH_SHORT).show();
            } else {
                searchProductByName(name);
            }
        });

        btnUpdateProduct.setOnClickListener(v -> {
            String id = tvProductId.getText().toString().replace("Product ID: ", "");
            String name = etProductName.getText().toString();
            String price = etProductPrice.getText().toString();
            String quantity = etProductQuantity.getText().toString();

            if (id.isEmpty() || name.isEmpty() || price.isEmpty() || quantity.isEmpty()) {
                Toast.makeText(UpdateProductActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else if (selectedBitmap == null) {
                Toast.makeText(UpdateProductActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
            } else {
                byte[] photoByteArray = bitmapToByteArray(selectedBitmap);//Bitmap to byte array

                boolean isUpdated = db.updateProduct(Integer.parseInt(id), name, Double.parseDouble(price), Integer.parseInt(quantity), photoByteArray);
                if (isUpdated) {
                    Toast.makeText(UpdateProductActivity.this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UpdateProductActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            try {
                if (data != null && data.getData() != null) {
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());//image as InputStream
                    selectedBitmap = BitmapFactory.decodeStream(inputStream);//InputStream to Bitmap
                    ivProductPhoto.setImageBitmap(selectedBitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); //Bitmap to PNG format
        return stream.toByteArray();
    }

    private void searchProductByName(String name) {
        Cursor cursor = db.getProductByName(name);// Get product from database
        if (cursor != null && cursor.moveToFirst()) { //details from cursor
            int idIndex = cursor.getColumnIndexOrThrow("id");
            int priceIndex = cursor.getColumnIndexOrThrow("price");
            int quantityIndex = cursor.getColumnIndexOrThrow("quantity");
            int photoIndex = cursor.getColumnIndexOrThrow("photo");

            tvProductId.setText("Product ID: " + cursor.getString(idIndex));
            etProductPrice.setText(cursor.getString(priceIndex));
            etProductQuantity.setText(cursor.getString(quantityIndex));

            byte[] photo = cursor.getBlob(photoIndex);//get photo and send it into image view
            if (photo != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                ivProductPhoto.setImageBitmap(bitmap);
                selectedBitmap = bitmap;
            } else {
                ivProductPhoto.setImageResource(R.drawable.burgur); // Placeholder image
            }

            cursor.close();
        } else {
            Toast.makeText(UpdateProductActivity.this, "Product not found", Toast.LENGTH_SHORT).show();
        }
    }
}
