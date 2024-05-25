package com.example.pizza_restaurant_application.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.pizza_restaurant_application.Customer.ProfileFragment;
import com.example.pizza_restaurant_application.R;
import com.example.pizza_restaurant_application.Registration.LoginActivity;
import com.google.android.material.navigation.NavigationView;

public class AdminHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Select the "Admin Profile" item by default
        navigationView.setCheckedItem(R.id.nav_admin_profile);

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Display the AdminProfileFragment initially
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new ProfileFragment()).commit();
        getSupportActionBar().setTitle("Admin Profile");
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
            case R.id.nav_admin_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                getSupportActionBar().setTitle("Admin Profile");
                break;
            case R.id.nav_add_admin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddAdminFragment()).commit();
                getSupportActionBar().setTitle("Add Admin");
                break;
            case R.id.nav_view_all_orders:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ViewAllOrdersFragment()).commit();
                getSupportActionBar().setTitle("View All Orders");
                break;
            case R.id.nav_add_special_offers:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddSpecialOfferFragment()).commit();
                getSupportActionBar().setTitle("Add Special Offers");
                break;
            case R.id.nav_view_Statistics:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminStatisticsFragment()).commit();
                getSupportActionBar().setTitle("View Statistics");
                break;
            case R.id.nav_logout:
                // Handle Logout click
                // Redirect to LoginActivity
                Intent intent = new Intent(AdminHomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Finish the current activity to prevent the user from navigating back
                Toast.makeText(getApplicationContext(), "Logout successful", Toast.LENGTH_SHORT).show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
