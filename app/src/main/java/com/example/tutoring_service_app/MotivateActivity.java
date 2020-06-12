package com.example.tutoring_service_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MotivateActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void addToNavigationDrawer() {
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        Menu menu = navView.getMenu();
        menu.findItem(R.id.nav_communities).setVisible(true);

        // TODO: replace with actual community names
        menu.findItem(R.id.nav_comm0).setTitle("community 1");
        menu.findItem(R.id.nav_comm1).setTitle("community 2");
        menu.findItem(R.id.nav_comm2).setTitle("community 3");
        menu.findItem(R.id.nav_comm3).setTitle("community 4");
        menu.findItem(R.id.nav_comm4).setTitle("community 5");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivate);

        // set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // setup navigation drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setNavigationViewListener();

        // add items to the navigation drawer
        addToNavigationDrawer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.motivate, menu);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // handles navigation view item clicks
        switch (item.getItemId()) {

            case R.id.nav_home: {
                openHome();
                break;
            }
            case R.id.nav_educate: {
                openEducate();
                break;
            }
            case R.id.nav_learn: {
                openLearn();
                break;
            }
            case R.id.nav_help: {
                openHelp();
                break;
            }
        }

        // close the navigation drawer
        drawerLayout.closeDrawers();
        return true;
    }

    // methods for opening separate activities

    public void openHome() {
        // open user landing activity
        Intent intent = new Intent(this, UserLanding.class);
        startActivity(intent);
    }

    public void openEducate() {
        // open educate activity
        Toast toast = Toast.makeText(getApplicationContext(), "educate!!!", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void openLearn() {
        // open learn activity
        Toast toast = Toast.makeText(getApplicationContext(), "learn!!!", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void openHelp() {
        // open help activity
        Intent intent = new Intent(this, HelpPage.class);
        startActivity(intent);
    }

    public void openProfile(View view) {
        // open profile page activity
        Intent intent = new Intent(this, ProfilePage.class);
        startActivity(intent);
    }
}
