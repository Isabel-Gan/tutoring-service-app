package com.example.tutoring_service_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RatingActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private Button submitBtn;

    //TODO: need to get username of tutor being reviewed
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        // get the username from the intent
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");

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

        //TODO: update database

        Toast toast = Toast.makeText(this, "thanks!", Toast.LENGTH_SHORT);
        toast.show();

        //switch back to main screen
        Intent intent = new Intent(this, UserLanding.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}
