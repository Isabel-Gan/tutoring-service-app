package com.example.tutoring_service_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class RatingActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private Button submitBtn;

    private String username;
    private String tutorUsername;
    private String subject;
    //TODO:get hours on call

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        // get the usernames and subject from the intent
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        tutorUsername = bundle.getString("channel");
        subject = bundle.getString("subject");

        //get views
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        submitBtn = (Button) findViewById(R.id.submit);

        //set up submit button
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRating();
            }
        });
    }

    private void submitRating() {
        Float rating = ratingBar.getRating();

        updateDatabaseRating(rating);

        //switch back to main screen
        Intent intent = new Intent(this, UserLanding.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private void updateDatabaseRating(Float rating) {
        String sqlInsert = "INSERT INTO dbo." + tutorUsername + "_subject_table (Subject, Rating, Hours, Number) VALUES ";
        //TODO: fix temp values
        String number = "1";
        int hours = 1;
        sqlInsert += "(\'" + subject + "\', CAST(" + rating + " AS decimal(2,1)), CAST(" + hours + " AS decimal(4, 1)), \'" + number + "\')";

        // connect to the database
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;

        // try to insert into table
        try {
            String driver = "net.sourceforge.jtds.jdbc.Driver";
            Class.forName(driver);

            String connString = "jdbc:jtds:sqlserver://tutoringservice.database.windows.net:1433/EduDatabase;user=schladies@tutoringservice;password=nohotwater3@;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            conn = DriverManager.getConnection(connString);
            Log.w("Connection","open");
            Statement stmt = conn.createStatement();

            // execute the sql statement
            stmt.executeUpdate(sqlInsert);
            conn.close();

            // success!
            Toast toast = Toast.makeText(this, "Thanks!", Toast.LENGTH_SHORT);
            toast.show();

        } catch (Exception e) {
            Log.w("Error connection", "" + e.getMessage() + " " + sqlInsert);
        }
    }
}
