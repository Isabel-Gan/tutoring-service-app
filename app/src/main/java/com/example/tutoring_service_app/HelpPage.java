package com.example.tutoring_service_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class HelpPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private String username;

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);

        // set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.ic_action_menu);
        actionbar.setDisplayHomeAsUpEnabled(true);

        // setup navigation drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setNavigationViewListener();

        // get the username from the intent
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
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
        }

        // close the navigation drawer
        drawerLayout.closeDrawers();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            case android.R.id.home: {
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
            case R.id.action_logout:
                logout();
                return true;
        }
        return true;
    }

    // methods for opening separate activities
    public void openHome() {
        // open user landing activity
        Intent intent = new Intent(this, UserLanding.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void openEducate() {
        // open educate activity
        Intent intent = new Intent(this, EducateActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void openLearn() {
        // open learn activity
        Intent intent = new Intent(this, LearnRequestActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void openMotivate() {
        // open motivate activity
        Intent intent = new Intent(this, MotivateActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void openProfile(View view) {
        // open profile page activity
        Intent intent = new Intent(this, ProfilePage.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void logout() {

        // connect to the database
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;

        try {

            // change the entry to logged out
            String driver = "net.sourceforge.jtds.jdbc.Driver";
            Class.forName(driver);
            String connString = "jdbc:jtds:sqlserver://tutoringservice.database.windows.net:1433/EduDatabase;user=schladies@tutoringservice;password=nohotwater3@;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            conn = DriverManager.getConnection(connString);
            Log.w("Connection","open");
            Statement stmt = conn.createStatement();

            // set the user's status to online
            String updateStatus = "UPDATE [dbo].[account_details_table] " +
                    "SET [logged_in] = 0 " +
                    "WHERE [username] = \'" + username + "\'";
            stmt.executeUpdate(updateStatus);

            // close connection
            conn.close();

            // exit, go back to the main page
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);


        } catch (Exception e) {
            Log.w("Error connection","" + e.getMessage());
        }

    }

}
