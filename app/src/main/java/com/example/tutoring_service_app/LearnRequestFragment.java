package com.example.tutoring_service_app;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class LearnRequestFragment extends Fragment {

    // array list to hold requested users and adapter to work with the ListView
    ArrayList<String> requestedUsers = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    private ListView users;
    private EditText user;
    private Button addUser;
    private Switch generalRequest;
    private TextView lblUser;

    // TODO: database stuff

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // inflate the view
        View view = inflater.inflate(R.layout.fragment_learn_request, container, false);

        // get views
        user = (EditText) view.findViewById(R.id.editUser);
        users = (ListView) view.findViewById(R.id.lstUsers);
        addUser = (Button) view.findViewById(R.id.btnAddUser);
        generalRequest = (Switch) view.findViewById(R.id.swGeneralReq);
        lblUser = (TextView) view.findViewById(R.id.lblUser);

        // link the adapter to the ArrayList to the ListView
        adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, requestedUsers);
        users.setAdapter(adapter);

        // set the onClick for the 'add users' button
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get user
                String userToAdd = user.getText().toString();

                // check if user exists
                if (isUser(userToAdd)) {
                    // add to list
                    requestedUsers.add(userToAdd);
                    adapter.notifyDataSetChanged();
                } else {
                    // don't add to list, notify user
                    Toast toast = Toast.makeText(v.getContext(), "User d.n.e", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        // set the onCheckedChange listener for the 'general request' switch
        generalRequest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // make the addUser label, editor, button, and list gone
                    lblUser.setVisibility(View.GONE);
                    user.setVisibility(View.GONE);
                    addUser.setVisibility(View.GONE);
                    users.setVisibility(View.GONE);
                } else {
                    // make those things visible
                    lblUser.setVisibility(View.VISIBLE);
                    user.setVisibility(View.VISIBLE);
                    addUser.setVisibility(View.VISIBLE);
                    users.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

    // checks if a user exists
    public boolean isUser(String username) {

        // connect to the database and query for the given username
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;

        try {
            String driver = "net.sourceforge.jtds.jdbc.Driver";
            Class.forName(driver);

            String connString = "jdbc:jtds:sqlserver://tutoringservice.database.windows.net:1433/EduDatabase;user=schladies@tutoringservice;password=nohotwater3@;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            conn = DriverManager.getConnection(connString);
            Log.w("Connection","open");
            Statement stmt = conn.createStatement();

            // query the database for the given username
            String sqlQuery = "SELECT username, password " +
                    "FROM [dbo].[account_details_table] " +
                    "WHERE [username] = \'"+ username + "\'";
            ResultSet rs = stmt.executeQuery(sqlQuery);

            // check whether there was a response or not
            return rs.next();

        } catch (Exception e) {
            Log.w("Error connection", "" + e.getMessage());
            return false;
        }

    }
}
