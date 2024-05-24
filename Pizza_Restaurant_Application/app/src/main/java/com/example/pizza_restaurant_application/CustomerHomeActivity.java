package com.example.pizza_restaurant_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class CustomerHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Select the "Home" item by default
        navigationView.setCheckedItem(R.id.nav_home);

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Display the HomeFragment initially
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
        getSupportActionBar().setTitle("Home");
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                getSupportActionBar().setTitle("Home");
                break;
            case R.id.nav_pizza_menu:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PizzaMenuFragment()).commit();
                getSupportActionBar().setTitle("Pizza Menu");
                break;
            case R.id.nav_orders:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new OrdersFragment()).commit();
                getSupportActionBar().setTitle("Your Orders");
                break;
            case R.id.nav_favorites_pizza:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FavoritesFragment()).commit();
                getSupportActionBar().setTitle("Your Favorites Pizza");
                break;
            case R.id.nav_favorites_offers:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FavoritesOffersFragment()).commit();
                getSupportActionBar().setTitle("Your Favorites Offers");
                break;
            case R.id.nav_special_offers:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SpecialOffersFragment()).commit();
                getSupportActionBar().setTitle("Special Offers");
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                getSupportActionBar().setTitle("Profile");
                break;
            case R.id.nav_call_find_us:
                // Handle Call Us or Find Us click
                // Replace the fragment_container with Call Us or Find Us fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CallFindUsFragment()).commit();
                getSupportActionBar().setTitle("Call us or Find us");
                break;

            case R.id.nav_logout:
                // Handle Logout click
                // Redirect to LoginActivity
                Intent intent = new Intent(CustomerHomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Finish the current activity to prevent the user from navigating back
                Toast.makeText(getApplicationContext(), "Logout successful", Toast.LENGTH_SHORT).show();
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
