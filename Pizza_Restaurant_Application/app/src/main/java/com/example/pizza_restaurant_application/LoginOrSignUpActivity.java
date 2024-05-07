package com.example.pizza_restaurant_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class LoginOrSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_sign_up);

        Button loginButton = findViewById(R.id.login_button);
        Button signUpButton = findViewById(R.id.sign_up_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignUp();
            }
        });
    }

    private void goToLogin() {
        Intent intent = new Intent(LoginOrSignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void goToSignUp() {
        Intent intent = new Intent(LoginOrSignUpActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}
