package com.example.tutoring_service_app;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SpecificRequestsFragment extends Fragment {

    private String username;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // inflate the view
        View view = inflater.inflate(R.layout.fragment_specific_requests, container, false);

        // get the username from the intent
        username = getArguments().getString("username");

        // testing acceptRequest
        acceptRequest("6B6722F7-4FF4-4593-9D98-80A718BA371A");

        return view;
    }

    public void acceptRequest(String id) {

        // connect to the database
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;

        String sqlQuery = "hello";
        String sqlDelete;
        String sqlAdd;

        try {
            String driver = "net.sourceforge.jtds.jdbc.Driver";
            Class.forName(driver);

            String connString = "jdbc:jtds:sqlserver://tutoringservice.database.windows.net:1433/EduDatabase;user=schladies@tutoringservice;password=nohotwater3@;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            conn = DriverManager.getConnection(connString);
            Log.w("Connection","open");
            Statement stmt = conn.createStatement();
            sqlQuery = "SELECT username, subject, details, requested " +
                    "FROM dbo.learn_requests_table " +
                    "WHERE ID = \'"+ id + "\'";
            ResultSet rs = stmt.executeQuery(sqlQuery);
            if (!rs.next()) {
                Toast toast = Toast.makeText(this.getContext(), "there's been a mistake :(", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            // check type of request
            String requested = rs.getString("requested");
            if (requested != null) {
                // delete from the requests table
                sqlDelete = "DELETE FROM [dbo].[learn_requests_table] WHERE ID = \'" + id + "\'";
                stmt.executeUpdate(sqlDelete);
            }

            // insert into the accepted requests table
            String subject = rs.getString("subject");
            String details = rs.getString("details");
            String requester = rs.getString("username");
            sqlAdd = "INSERT INTO [dbo].[accepted_requests_table] " +
                            "(ID, username, subject, details, accepted) " +
                            "VALUES (\'" + id + "\', \'" + requester + "\', \'" + subject + "\', \'" + details + "\', \'" + username + "\')";
            stmt.executeUpdate(sqlAdd);

            // communicate success
            Toast toast = Toast.makeText(this.getContext(), "Request accepted!", Toast.LENGTH_SHORT);
            toast.show();

        } catch (Exception e) {
            Log.w("Error connection","" + e.getMessage() + " " + sqlQuery);
        }

    }
}
