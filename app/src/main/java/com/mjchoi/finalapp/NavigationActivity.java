package com.mjchoi.finalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        // Instantiate tool bar and set it as an action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Plan Your Day");


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Action bar toggle class requires open and close string values as input
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState(); // Rotates hamburger icon


        if (savedInstanceState == null) {
            // Get To Do fragment immediately when we start navigation activity
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ToDoFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_to_do);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_to_do:
                startActivity(new Intent(NavigationActivity.this, ToDoActivity.class));
                break;

            case R.id.nav_habit:
                startActivity(new Intent(NavigationActivity.this, HabitActivity.class));
                break;

            case R.id.nav_quote:
                startActivity(new Intent(NavigationActivity.this, QuotesActivity.class));
                break;

            case R.id.nav_like:
                Toast.makeText(this, "Thank you!", Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_logout:
                Toast.makeText(this, "Logged Out", Toast.LENGTH_LONG).show();
                startActivity(new Intent(NavigationActivity.this, MainActivity.class));
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    // When back button is pressed, close the navigation drawer
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);    // if navigation drawer is on the right side of the screen, use END instead of START
        } else {
            super.onBackPressed();  // If drawer is not open, close activity
        }
    }
}
