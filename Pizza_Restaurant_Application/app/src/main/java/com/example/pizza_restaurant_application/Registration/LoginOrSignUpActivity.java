package com.example.pizza_restaurant_application.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.pizza_restaurant_application.R;

public class LoginOrSignUpActivity extends AppCompatActivity {

    private ImageView imageLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_sign_up);

        Button loginButton = findViewById(R.id.login_button);
        Button signUpButton = findViewById(R.id.sign_up_button);
        imageLogo = findViewById(R.id.image_logo);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_infinite_spin);

        // Set the animation listener to make the ImageView visible after animation
        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Animation started
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Animation ended
                imageLogo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Animation repeated
            }
        });

        // Start the animation on the ImageView
        imageLogo.startAnimation(fadeInAnimation);

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
