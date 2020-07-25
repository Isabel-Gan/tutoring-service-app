package com.example.tutoring_service_app;

import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class AcceptedRequestFragment extends Fragment {

    private String username;
    private Button refresh;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter rAdapter;

    private ArrayList<LearnRequestItem> requests;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // inflate the view
        final View view = inflater.inflate(R.layout.fragment_specific_requests, container, false);

        // get the username from the intent
        username = getArguments().getString("username");

        // get view
        refresh = (Button) view.findViewById(R.id.refreshButton);

        //set up the recyclerview and adapter
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //set up the adaptor with whatever's initially in the database
        setUpAdapter(view);

        // set the onClick for the 'refresh button
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpAdapter(view);
            }
        });

        return view;
    }

    //refreshes the request list and sets up the recycler view adapter
    private void setUpAdapter(final View view) {
        refreshRequestList();

        //have to actually update view with new arr list
        rAdapter = new RecyclerViewAdapter(getActivity(), requests);
        recyclerView.setAdapter(rAdapter);

        //set the image button listeners
        rAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                //TODO: maybe do something different here? at least notify the educator somehow
                //changeRequest(requests.get(position).getID(), 0);
                requests.remove(position);
                rAdapter.notifyItemRemoved(position);
            }

            @Override
            public void onAcceptClick(int position) {
                //TODO: also prob do something different here - link to profile/video call somehow
                //changeRequest(requests.get(position).getID(), 1);
                requests.remove(position);
                rAdapter.notifyItemRemoved(position);

                // go to the video call
                Intent intent = new Intent(view.getContext(), VideoCallActivity.class);
                intent.putExtra("username", username);
                String accepted = requests.get(position).getRequested().substring(13);
                intent.putExtra("channel", accepted);
                startActivity(intent);
            }
        });
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

            String query = "SELECT * FROM [dbo].[accepted_requests_table] WHERE [username] = \'" + username + "\'";

            // execute the sql statement
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //create a new learn request item to add to the arraylist
                LearnRequestItem newItem = new LearnRequestItem();

                newItem.setSubject(rs.getString("subject"));
                //wrap the description and requested user with labels
                newItem.setDescription("Description: " + rs.getString("details"));
                newItem.setRequested("Accepted by: " + rs.getString("accepted"));
                newItem.setStatus(true); //TODO: lmao unnecessary?
                newItem.setDeleteImageResource(R.drawable.ic_baseline_delete_24);
                newItem.setAcceptImageResource(R.drawable.ic_baseline_check_24);
                newItem.setID(rs.getString("ID"));

                requests.add(newItem);
            }

            conn.close();

        } catch (Exception e) {
            Log.w("Error connection", "" + e.getMessage());
        }

    }

}
