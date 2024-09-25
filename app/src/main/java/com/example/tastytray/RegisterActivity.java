package com.example.tastytray;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText etUsername, etEmail, etPhone, etPassword, etConfirmPassword;
    Button btnRegister;
    TextView tvLogin;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String email = etEmail.getText().toString();
            String phone = etPhone.getText().toString();
            String password = etPassword.getText().toString();
            String confirmPassword = etConfirmPassword.getText().toString();

            if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else if (!isValidEmail(email)) {
                Toast.makeText(RegisterActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
            } else if (!isValidBangladeshiPhoneNumber(phone)) {
                Toast.makeText(RegisterActivity.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else if(!isValidPassword(password)){
                Toast.makeText(RegisterActivity.this, "At least 6 characters long\n", Toast.LENGTH_SHORT).show();
                Toast.makeText(RegisterActivity.this, "Contains at least one uppercase letter\n", Toast.LENGTH_SHORT).show();
                Toast.makeText(RegisterActivity.this, "Contains at least one lowercase letter\n", Toast.LENGTH_SHORT).show();
                Toast.makeText(RegisterActivity.this, "Contains at least one digit\n", Toast.LENGTH_SHORT).show();

            }
            else {
                boolean isUserAdded = db.addUser(username, email, phone, password);
                if (isUserAdded) {
                    Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-z]+[0-9]*@gmail\\.[a-z]{3}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidBangladeshiPhoneNumber(String phone) {
        Pattern pattern = Pattern.compile("^(01[3-9]\\d{8})$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
    private boolean isValidPassword(String password) {
        if (password.length() < 8 &&  password.length()>20 ) {
            return false;
        }
        boolean UpperCase = false;
        boolean LowerCase = false;
        boolean Digit = false;

        boolean SChar = false;
        String specialCharacters = "!@#$%^&*()-+";

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isUpperCase(ch)) {
                UpperCase = true;
            } else if (Character.isLowerCase(ch)) {
                LowerCase = true;
            } else if (Character.isDigit(ch)) {
                Digit = true;
            } else if (specialCharacters.indexOf(ch) >= 0) {
                SChar = true;}
            if (UpperCase && LowerCase && Digit && SChar) {
                return true;
            }
        }
        return false;
    }

}
