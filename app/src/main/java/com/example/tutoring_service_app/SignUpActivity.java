package com.example.tutoring_service_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
    public void onSignUp(View view){
        EditText firstname = (EditText) findViewById(R.id.firstNameInput);
        String fname = firstname.getText().toString();
        if(fname.equals(""))
        {
            Context context = getApplicationContext();
            CharSequence text = "Please input your first name.";
            //int duration = 2;
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        EditText lastname = (EditText) findViewById(R.id.lastNameInput);
        String lname = lastname.getText().toString();
        if(lname.equals(""))
        {
            Context context = getApplicationContext();
            CharSequence text = "Please input your last name.";
            //int duration = 2;
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        EditText middleinitial = (EditText) findViewById(R.id.MIInput);
        String minitial = middleinitial.getText().toString();

        EditText birthday = (EditText) findViewById(R.id.birthdayInput);
        String bdate = birthday.getText().toString();
        if(bdate.equals(""))
        {
            Context context = getApplicationContext();
            CharSequence text = "Please input your birthday.";
            //int duration = 2;
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        EditText addressInput = (EditText) findViewById(R.id.addressInput);
        String address = addressInput.getText().toString();
        if(address.equals(""))
        {
            Context context = getApplicationContext();
            CharSequence text = "Please input your address.";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        EditText cityInput = (EditText) findViewById(R.id.cityInput);
        String city = cityInput.getText().toString();
        if(city.equals(""))
        {
            Context context = getApplicationContext();
            CharSequence text = "Please input your city.";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        EditText countryInput = (EditText) findViewById(R.id.countryInput);
        String country = countryInput.getText().toString();
        if(country.equals(""))
        {
            Context context = getApplicationContext();
            CharSequence text = "Please input your country.";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        EditText emailInput = (EditText) findViewById(R.id.emailInput);
        String email = emailInput.getText().toString();
        if(email.equals(""))
        {
            Context context = getApplicationContext();
            CharSequence text = "Please input your email.";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if(!email.contains("@"))
        {
            Context context = getApplicationContext();
            CharSequence text = "That is not a valid email address.";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        EditText usernameInput = (EditText) findViewById(R.id.usernameInput);
        String username = usernameInput.getText().toString();
        if(username.equals(""))
        {
            Context context = getApplicationContext();
            CharSequence text = "Please input a username.";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
            return;
        }
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
            ResultSet rs = stmt.executeQuery("SELECT * FROM [dbo].[account_details_table] WHERE [username] = \'"+ username +"\'");

            if(rs.next()){
                Context context = getApplicationContext();
                CharSequence text = "Username is already taken";
                Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                toast.show();
                return;
            }
            conn.close();
        } catch (Exception e)
        {
            Context context = getApplicationContext();
            CharSequence text = "Failed to Connect";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        EditText passwordInput = (EditText) findViewById(R.id.passwordInput);
        String password = passwordInput.getText().toString();
        //Check if password meets the requirements
        if(password.length() < 8 || !password.matches(".*\\d.*")
                || password.equals(password.toLowerCase()) || password.equals(password.toUpperCase()))
        {
            Context context = getApplicationContext();
            CharSequence text = "That is not a valid password";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        EditText confirmpassword = (EditText) findViewById(R.id.confirmPasswordInput);
        String cpassword = confirmpassword.getText().toString();
        if(!cpassword.equals(password))
        {
            Context context = getApplicationContext();
            CharSequence text = "Your passwords do not match.";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        //connect to the database
        Connection conn2 = null;
        try {
            String driver = "net.sourceforge.jtds.jdbc.Driver";
            Class.forName(driver);

            String connString = "jdbc:jtds:sqlserver://tutoringservice.database.windows.net:1433/EduDatabase;user=schladies@tutoringservice;password=nohotwater3@;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            conn2 = DriverManager.getConnection(connString);
            Log.w("Connection","open");
            Statement stmt = conn2.createStatement();

            //DateFormat format = new SimpleDateFormat("MM/DD/yyyy", Locale.ENGLISH);
            //Date date = format.parse(bdate);

            int num = stmt.executeUpdate("INSERT INTO [dbo].[account_details_table] " +
                    "(username, password, fname, lname, minitial, address, city, country, email, birthday) " +
                    "VALUES (\'" + username + "\', \'" + password + "\', \'" + fname + "\', \'" +
                    lname + "\', \'" + minitial + "\', \'" + address + "\', \'" + city + "\', \'" +
                    country + "\', \'" + email + "\', \'" + bdate + "\')");
            num = stmt.executeUpdate("INSERT INTO [dbo].[profile_table] " +
                    "(username) " +
                    "VALUES (\'" + username + "\'");
            conn2.close();

            Intent intent = new Intent(this, UserLanding.class);
            startActivity(intent);
        } catch (Exception e)
        {
            Context context = getApplicationContext();
            Log.i("Error", "" + e.getMessage());
            CharSequence text = "Failed to Connect";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();

        }


    }
}
