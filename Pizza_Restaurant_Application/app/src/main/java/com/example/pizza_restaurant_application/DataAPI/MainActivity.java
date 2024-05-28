package com.example.pizza_restaurant_application.DataAPI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pizza_restaurant_application.R;
import com.example.pizza_restaurant_application.Registration.LoginOrSignUpActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView imageLogo;
    private DataBaseHelper dbHelper;
    private Button buttonGetStarted;
    private TextView slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DataBaseHelper(this);
        imageLogo = findViewById(R.id.image_logo);
        slogan = findViewById(R.id.textView_slogan);
        Animation rotateInfiniteSpin = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_infinite_spin);
        Animation blinkAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);

        // Ensure admin user is inserted if needed
        dbHelper.insertAdminUserIfNeeded();

        // Set the animation listener to make the ImageView visible after animation
        rotateInfiniteSpin.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageLogo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        // Start the rotation animation on the ImageView
        imageLogo.startAnimation(rotateInfiniteSpin);

        // Start the blinking animation on the slogan TextView
        slogan.startAnimation(blinkAnimation);

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
