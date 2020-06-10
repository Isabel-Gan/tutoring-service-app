package com.example.tutoring_service_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ProfilePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);


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
            case R.id.nav_motivate: {
                openMotivate();
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

    public void openMotivate() {
        // open motivate activity
        Toast toast = Toast.makeText(getApplicationContext(), "motivate!!!", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void openHelp() {
        // open help activity
        Toast toast = Toast.makeText(getApplicationContext(), "help!!!", Toast.LENGTH_SHORT);
        toast.show();
    }

}