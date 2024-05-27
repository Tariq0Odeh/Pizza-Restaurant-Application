package com.example.pizza_restaurant_application.Registration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pizza_restaurant_application.Admin.AdminHomeActivity;
import com.example.pizza_restaurant_application.Customer.CustomerHomeActivity;
import com.example.pizza_restaurant_application.DataAPI.DataBaseHelper;
import com.example.pizza_restaurant_application.R;
import com.example.pizza_restaurant_application.DataAPI.User;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private CheckBox rememberMeCheckBox;
    private SharedPreferences sharedPreferences;
    private static final String PREF_EMAIL_KEY = "email";
    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DataBaseHelper(this);
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        rememberMeCheckBox = findViewById(R.id.remember_me_checkbox);
        Button loginButton = findViewById(R.id.login_button);
        Button signUpButton = findViewById(R.id.sign_up_button);

        // Get shared preferences instance for login
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString(PREF_EMAIL_KEY, "");
        emailEditText.setText(savedEmail);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to sign up activity
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    // Method to handle login process
    private void login() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Email and password are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if login credentials are valid
        if (dbHelper.checkLogin(email, password)) {
            if (rememberMeCheckBox.isChecked()) {
                sharedPreferences.edit().putString(PREF_EMAIL_KEY, email).apply();
            } else {
                sharedPreferences.edit().remove(PREF_EMAIL_KEY).apply();
            }

            dbHelper.setUserEmail(email);

            // Retrieve user information from database
            User user = dbHelper.getUserByEmail();
            Intent homeIntent;
            if (user != null && user.isAdmin()) {
                homeIntent = new Intent(LoginActivity.this, AdminHomeActivity.class);
            } else {
                homeIntent = new Intent(LoginActivity.this, CustomerHomeActivity.class);
            }
            startActivity(homeIntent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }
}
