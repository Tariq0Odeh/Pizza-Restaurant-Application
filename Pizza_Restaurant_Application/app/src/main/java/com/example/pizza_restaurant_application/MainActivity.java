package com.example.pizza_restaurant_application;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DataBaseHelper(this);
        dbHelper.insertAdminUser();
        Button getStarted = findViewById(R.id.button_get_started);
        

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginOrSignUp();
            }
        });
    }

    private void goToLoginOrSignUp() {
        Intent intent = new Intent(MainActivity.this, LoginOrSignUpActivity.class);
        startActivity(intent);
    }
}
