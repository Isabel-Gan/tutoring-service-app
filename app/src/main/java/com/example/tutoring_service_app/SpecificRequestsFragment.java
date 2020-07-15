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

        // testing accept and reject request
        changeRequest("7A03C78D-B5E8-4E4E-A57D-DFF5CDF2A18E", 0);
        changeRequest("E5BBDE2B-C788-4BE6-B528-84EDB1384405", 1);

        return view;
    }

    // 0 - reject, 1 - accept
    public void changeRequest(String id, int action) {

        // connect to the database
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;

        String sqlQuery, sqlDelete, sqlAdd;

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
            switch (action) {
                case 0:
                    // add to the rejected requests table
                    sqlAdd = "INSERT INTO [dbo].[rejected_requests_table] " +
                            "(ID, username, subject, details, rejected) " +
                            "VALUES (\'" + id + "\', \'" + requester + "\', \'" + subject + "\', \'" + details + "\', \'" + username + "\')";
                    break;
                case 1:
                    // add to the accepted requests table
                    sqlAdd = "INSERT INTO [dbo].[accepted_requests_table] " +
                            "(ID, username, subject, details, accepted) " +
                            "VALUES (\'" + id + "\', \'" + requester + "\', \'" + subject + "\', \'" + details + "\', \'" + username + "\')";
                    break;
                default:
                    // shouldn't get here
                    Toast toast = Toast.makeText(this.getContext(), "there's been a mistake :(", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
            }
            stmt.executeUpdate(sqlAdd);

            // communicate success
            String toastText = (action == 1) ? "Request accepted!" : "Request rejected!";
            Toast toast = Toast.makeText(this.getContext(), toastText, Toast.LENGTH_SHORT);
            toast.show();

        } catch (Exception e) {
            Log.w("Error connection","" + e.getMessage());
        }

    }
}
