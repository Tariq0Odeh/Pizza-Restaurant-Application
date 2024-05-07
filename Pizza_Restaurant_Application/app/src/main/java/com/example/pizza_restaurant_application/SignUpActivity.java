package com.example.pizza_restaurant_application;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailEditText, phoneEditText, firstNameEditText, lastNameEditText, passwordEditText, confirmPasswordEditText;
    private Spinner genderSpinner;
    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dbHelper = new DataBaseHelper(this);

        emailEditText = findViewById(R.id.email_edit_text);
        phoneEditText = findViewById(R.id.phone_edit_text);
        firstNameEditText = findViewById(R.id.first_name_edit_text);
        lastNameEditText = findViewById(R.id.last_name_edit_text);
        genderSpinner = findViewById(R.id.gender_spinner);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);

        Button signUpButton = findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String gender = genderSpinner.getSelectedItem().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (validateInputs(email, phone, firstName, lastName, password, confirmPassword)) {
            if (dbHelper.checkEmailExists(email)) {
                Toast.makeText(SignUpActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
            } else if (dbHelper.checkPhoneExists(phone)) {
                Toast.makeText(SignUpActivity.this, "Phone number already exists", Toast.LENGTH_SHORT).show();
            } else {
                // Encrypt password
                String encryptedPassword = password;

                // Insert user into database
                User user = new User(email, phone, firstName, lastName, gender, encryptedPassword);
                boolean inserted = dbHelper.insertUser(user);

                if (inserted) {
                    Toast.makeText(SignUpActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignUpActivity.this, "Failed to register user", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean validateInputs(String email, String phone, String firstName, String lastName, String password, String confirmPassword) {
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter a valid email address");
            return false;
        }

        if (TextUtils.isEmpty(phone) || !phone.startsWith("05") || phone.length() != 10) {
            phoneEditText.setError("Enter a valid phone number starting with '05' and 10 digits");
            return false;
        }

        if (TextUtils.isEmpty(firstName) || firstName.length() < 3) {
            firstNameEditText.setError("Enter a valid first name (at least 3 characters)");
            return false;
        }

        if (TextUtils.isEmpty(lastName) || lastName.length() < 3) {
            lastNameEditText.setError("Enter a valid last name (at least 3 characters)");
            return false;
        }

        if (TextUtils.isEmpty(password) || password.length() < 8 || !password.matches(".*\\d.*") || !password.matches(".*[a-zA-Z].*")) {
            passwordEditText.setError("Password must be at least 8 characters long and contain at least one letter and one number");
            return false;
        }

        if (!TextUtils.equals(password, confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            return false;
        }

        return true;
    }
}
