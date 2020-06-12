package com.example.tutoring_service_app;

import androidx.appcompat.app.AppCompatActivity;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }
    public void clickLogin(View view){
        EditText username = (EditText) findViewById(R.id.username);
        String usr = username.getText().toString();
        EditText password = (EditText) findViewById(R.id.password);
        String psw = password.getText().toString();
        boolean loggedin = checkLogin(usr,psw);

        if(loggedin){
            Intent intent = new Intent(this, UserLanding.class);
            startActivity(intent);
        }
        else{
            Context context = getApplicationContext();
            CharSequence text = "Your username and/or password was wrong.";
            //int duration = 2;
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
        }


    }
    public boolean checkLogin(String usr, String psw){
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
            ResultSet rs = stmt.executeQuery("SELECT * FROM [dbo].[account_details_table] WHERE [username] = \'"+ usr + "\' AND [password] = \'" + psw + "\'");
            //rs.last();
            //Log.i("Android", "Got past rs.last()");
            while(rs.next()){
                conn.close();
                return true;
            }
            conn.close();
            return false;
        } catch (Exception e)
        {
            Log.w("Error connection","" + e.getMessage());
            return false;
        }
    }



}
