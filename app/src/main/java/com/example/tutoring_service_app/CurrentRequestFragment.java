package com.example.tutoring_service_app;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CurrentRequestFragment extends Fragment {

    private String username;
    private Button refresh;

    //recycler view with card views https://youtu.be/oq_xGMN0mRE
    private RecyclerView recyclerView;
    private RecyclerViewAdapter rAdapter;

    private ArrayList<LearnRequestItem> requests;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_current_request, container, false);

        // get the username from the intent
        username = getArguments().getString("username");

        // get view
        refresh = (Button) view.findViewById(R.id.refreshButton);

        //set up the recyclerview and adapter
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //set up the adaptor with whatever's initially in the database
        setUpAdapter();

        // set the onClick for the 'refresh button
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpAdapter();
            }
        });

        return view;
    }

    //refreshes the request list and sets up the recycler view adapter
    private void setUpAdapter() {
        refreshRequestList();

        //have to actually update view with new arr list
        rAdapter = new RecyclerViewAdapter(getActivity(), requests);
        recyclerView.setAdapter(rAdapter);

        //set the delete button listener
        rAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                deleteFromDatabase(requests.get(position));
                requests.remove(position);
                rAdapter.notifyItemRemoved(position);
            }
        });
    }

    //returns the description without the label
    private String unwrappedDescription(String d) {
        return d.substring("Description: ".length(), d.length());
    }

    //returns the requested user without the label
    private String unwrappedRequestedUser(String s) {
        return s.substring("Requested User: ".length(), s.length());
    }

    //deletes a certain learn request from the database table
    private void deleteFromDatabase(LearnRequestItem request) {
        // connect to the database
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;

        // try to delete from the table
        try {
            String driver = "net.sourceforge.jtds.jdbc.Driver";
            Class.forName(driver);

            String connString = "jdbc:jtds:sqlserver://tutoringservice.database.windows.net:1433/EduDatabase;user=schladies@tutoringservice;password=nohotwater3@;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            conn = DriverManager.getConnection(connString);
            Log.w("Connection","open");
            Statement stmt = conn.createStatement();

            //create the delete command (identifying a request by username, subject, description, and requested)
            String sqlDelete = "DELETE FROM dbo.learn_requests_table WHERE [username] = \'" + username + "\' AND [subject] = \'" + request.getSubject() + "\' AND [details] = \'" + unwrappedDescription(request.getDescription()) + "\' ";
            if (request.getRequested().equals("General Request")) {
                sqlDelete += "AND [requested] IS NULL";
            }
            else {
                sqlDelete += "AND [requested] = \'" + unwrappedRequestedUser(request.getRequested()) + "\'";
            }

            // execute the sql statement
            stmt.executeUpdate(sqlDelete);
            conn.close();

        } catch (Exception e) {
            Log.w("Error connection", "" + e.getMessage());
        }
    }

    //refreshes the arraylist of requests by querying the database
    private void refreshRequestList() {
        requests = new ArrayList<>();

        // connect to the database
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;

        // try to query the table
        try {
            String driver = "net.sourceforge.jtds.jdbc.Driver";
            Class.forName(driver);

            String connString = "jdbc:jtds:sqlserver://tutoringservice.database.windows.net:1433/EduDatabase;user=schladies@tutoringservice;password=nohotwater3@;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            conn = DriverManager.getConnection(connString);
            Log.w("Connection","open");
            Statement stmt = conn.createStatement();

            String query = "SELECT * FROM dbo.learn_requests_table WHERE [username] = \'" + username + "\'";

            // execute the sql statement
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //create a new learn request item to add to the arraylist
                LearnRequestItem newItem = new LearnRequestItem();

                newItem.setSubject(rs.getString("subject"));
                //wrap the description and requested user with labels
                newItem.setDescription("Description: " + rs.getString("details"));
                if (rs.getString("requested") != null) {
                    newItem.setRequested("Requested User: " + rs.getString("requested"));
                }
                else {
                    newItem.setRequested("General Request");
                }
                newItem.setStatus(false);
                newItem.setImageResource(R.drawable.ic_baseline_delete_24);

                requests.add(newItem);
            }

            conn.close();

        } catch (Exception e) {
            Log.w("Error connection", "" + e.getMessage());
        }

    }
}
