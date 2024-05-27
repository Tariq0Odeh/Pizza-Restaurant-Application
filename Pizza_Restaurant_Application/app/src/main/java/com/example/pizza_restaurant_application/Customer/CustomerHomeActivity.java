package com.example.pizza_restaurant_application.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.pizza_restaurant_application.R;
import com.example.pizza_restaurant_application.Registration.LoginActivity;
import com.example.pizza_restaurant_application.Admin.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

public class CustomerHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        // Initialize the DrawerLayout and NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Select the "Home" item by default
        navigationView.setCheckedItem(R.id.nav_home);

        // Set up the ActionBarDrawerToggle
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
        getSupportActionBar().setTitle("Home");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here
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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CallFindUsFragment()).commit();
                getSupportActionBar().setTitle("Call us or Find us");
                break;
            case R.id.nav_logout:
                Intent intent = new Intent(CustomerHomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), "Logout successful", Toast.LENGTH_SHORT).show();
                break;
        }

        // Close the drawer after item selection
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
