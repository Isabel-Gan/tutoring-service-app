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

        // set the onClick for the 'refresh button
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshRequestList(view);

                //have to actually update view with new arr list
                rAdapter = new RecyclerViewAdapter(getActivity(), requests);
                recyclerView.setAdapter(rAdapter);
            }
        });

        Toast toast = Toast.makeText(view.getContext(), "Finished creating screen", Toast.LENGTH_SHORT);
        toast.show();

        return view;
    }

    private LearnRequestItem getRequestItem(String subject, String description, boolean status) {
        LearnRequestItem r = new LearnRequestItem();

        r.setSubject(subject);
        r.setDescription(description);
        r.setStatus(status);

        return r;
    }

    private void refreshRequestList(View view) {

        requests = new ArrayList<>();

        //test card
        requests.add(getRequestItem("bio", "mitochondria is the powerhouse of what?", false));


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
                Toast toast = Toast.makeText(view.getContext(), "Learn Request Submitted", Toast.LENGTH_SHORT);
                toast.show();

                String subject=  rs.getString("subject");
                String description = rs.getString("details");

                LearnRequestItem r = getRequestItem(subject, description, false);
                requests.add(r);
            }

            conn.close();

        } catch (Exception e) {
            Log.w("Error connection", "" + e.getMessage());
        }

    }
}
