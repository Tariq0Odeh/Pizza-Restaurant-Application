// MainActivity.java
package com.example.pizza_restaurant_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DataBaseHelper(this);
        dbHelper.insertAdminUser();
        
        button = findViewById(R.id.button_get_started);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(MainActivity.this);
                connectionAsyncTask.execute("https://2d6aa7d77e8248cc819204adafaddd50.api.mockbin.io/");
            }
        });
    }

    public void goToLoginOrSignUp() {
        Intent intent = new Intent(this, LoginOrSignUpActivity.class);
        startActivity(intent);
        finish(); // Optional: finish the current activity
    }

    public void showErrorToast() {
        Toast.makeText(MainActivity.this, "Connection failed. Please try again.", Toast.LENGTH_SHORT).show();
    }

    public void setButtonText(String text) {
        button.setText(text);
    }

    public void fillPizzas(List<Pizza> pizzas) {
        DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this);
        for (Pizza pizza : pizzas) {
            dbHelper.insertPizza(pizza);
        }
    }
}
