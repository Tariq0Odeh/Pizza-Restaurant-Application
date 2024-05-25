package com.example.pizza_restaurant_application.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pizza_restaurant_application.DataAPI.ConnectionAsyncTask;
import com.example.pizza_restaurant_application.DataAPI.DataBaseHelper;
import com.example.pizza_restaurant_application.Customer.Pizza;
import com.example.pizza_restaurant_application.R;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView imageLogo;
    private DataBaseHelper dbHelper;
    private Button buttonGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DataBaseHelper(this);
        imageLogo = findViewById(R.id.image_logo);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_infinite_spin);

        // Ensure admin user is inserted if needed
        dbHelper.insertAdminUserIfNeeded();


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

        buttonGetStarted = findViewById(R.id.button_get_started);
        buttonGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the asynchronous task for connecting to the server
                ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(MainActivity.this);
                connectionAsyncTask.execute("https://2d6aa7d77e8248cc819204adafaddd50.api.mockbin.io/");
            }
        });
    }

    public void goToLoginOrSignUp() {
        Intent intent = new Intent(this, LoginOrSignUpActivity.class);
        startActivity(intent);
        finish();
    }

    public void showErrorToast() {
        Toast.makeText(MainActivity.this, "Connection failed. Please try again.", Toast.LENGTH_SHORT).show();
    }

    public void setButtonText(String text) {
        buttonGetStarted.setText(text);
    }

    public void fillPizzas(List<Pizza> pizzas) {
        for (Pizza pizza : pizzas) {
            dbHelper.insertPizza(pizza);
        }
    }
}
