package com.example.tastytray;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class InsertProductActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1; //image pick request code
    EditText etProductName, etProductPrice, etProductQuantity;
    Button btnInsertProduct, btnSelectImage;
    ImageView ivProductPhoto;
    DatabaseHelper db;
    byte[] productPhoto;//byte array

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_product);
        //setContentView(R.layout.activity_insert_product);

        db = new DatabaseHelper(this);

        etProductName = findViewById(R.id.etProductName);
        etProductPrice = findViewById(R.id.etProductPrice);
        etProductQuantity = findViewById(R.id.etProductQuantity);
        ivProductPhoto = findViewById(R.id.ivProductPhoto);
        btnInsertProduct = findViewById(R.id.btnInsertProduct);
        btnSelectImage = findViewById(R.id.btnSelectImage);

        btnSelectImage.setOnClickListener(v -> openImageChooser());

        btnInsertProduct.setOnClickListener(v -> {
            String name = etProductName.getText().toString();
            String price = etProductPrice.getText().toString();
            String quantity = etProductQuantity.getText().toString();

            if (name.isEmpty() || price.isEmpty() || quantity.isEmpty() || productPhoto == null) {
                Toast.makeText(InsertProductActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                boolean isInserted = db.addProduct(name, Double.parseDouble(price), Integer.parseInt(quantity), productPhoto);
                if (isInserted) {
                    Toast.makeText(InsertProductActivity.this, "Product inserted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(InsertProductActivity.this, "Insertion failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");// Setting the type to image.
        intent.setAction(Intent.ACTION_GET_CONTENT);// get content
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);//pick an image
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();//Getting the URI of the selected image.
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);//bitmap from the URI.
                ivProductPhoto.setImageBitmap(bitmap);
                productPhoto = getBytes(bitmap);// convert bit to byte
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);//bitmap into PNG
        return stream.toByteArray();
    }
}
