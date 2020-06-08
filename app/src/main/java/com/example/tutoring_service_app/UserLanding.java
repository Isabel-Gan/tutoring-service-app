package com.example.tutoring_service_app;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class UserLanding extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_landing);

        // set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.ic_action_menu);
        actionbar.setDisplayHomeAsUpEnabled(true);

        // setup navigation drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setNavigationViewListener();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_landing, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // handles navigation view item clicks
        switch (item.getItemId()) {

            case R.id.nav_educate: {
                openEducate(null);
                break;
            }
            case R.id.nav_learn: {
                openLearn(null);
                break;
            }
            case R.id.nav_motivate: {
                openMotivate(null);
                break;
            }
        }

        // close the navigation drawer
        drawerLayout.closeDrawers();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            // Android home
            case android.R.id.home: {
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return true;
    }

    public void openEducate(View view) {
        // open educate activity
        Toast toast = Toast.makeText(getApplicationContext(), "educate!!!", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void openLearn(View view) {
        // open learn activity
        Toast toast = Toast.makeText(getApplicationContext(), "learn!!!", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void openMotivate(View view) {
        // open motivate activity
        Toast toast = Toast.makeText(getApplicationContext(), "motivate!!!", Toast.LENGTH_SHORT);
        toast.show();
    }

}