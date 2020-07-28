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

import org.mindrot.jbcrypt.BCrypt;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
    public void onSignUp(View view){

        // first name
        EditText firstName = (EditText) findViewById(R.id.firstNameInput);
        String fname = firstName.getText().toString();
        if(fname.equals(""))
        {
            Context context = getApplicationContext();
            CharSequence text = "Please input your first name.";
            //int duration = 2;
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        // last name
        EditText lastName = (EditText) findViewById(R.id.lastNameInput);
        String lname = lastName.getText().toString();
        if(lname.equals(""))
        {
            Context context = getApplicationContext();
            CharSequence text = "Please input your last name.";
            //int duration = 2;
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        // middle initial
        EditText middleInitial = (EditText) findViewById(R.id.MIInput);
        String mInitial = middleInitial.getText().toString();

        // birthday
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

        // address
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

        // city
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

        // country
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

        // email
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

        // username
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

        // check if the username has been taken already
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

            if (rs.next()){
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
            Log.w("Error1", "" + e.getMessage());
            CharSequence text = "Failed to Connect";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        // password
        EditText passwordInput = (EditText) findViewById(R.id.passwordInput);
        String password = passwordInput.getText().toString();

        // check if password meets the requirements
        if(password.length() < 8 || !password.matches(".*\\d.*")
                || password.equals(password.toLowerCase()) || password.equals(password.toUpperCase()))
        {
            Context context = getApplicationContext();
            CharSequence text = "That is not a valid password";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        // confirm password
        EditText confirmPassword = (EditText) findViewById(R.id.confirmPasswordInput);
        String cpassword = confirmPassword.getText().toString();
        if(!cpassword.equals(password))
        {
            Context context = getApplicationContext();
            CharSequence text = "Your passwords do not match.";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        // hash the password
        String salt = BCrypt.gensalt(10);
        String hashedPassword = BCrypt.hashpw(password, salt);

        //connect to the database
        Connection conn2 = null;
        try {
            String driver = "net.sourceforge.jtds.jdbc.Driver";
            Class.forName(driver);

            String connString = "jdbc:jtds:sqlserver://tutoringservice.database.windows.net:1433/EduDatabase;user=schladies@tutoringservice;password=nohotwater3@;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            conn2 = DriverManager.getConnection(connString);
            Log.w("Connection","open");
            Statement stmt = conn2.createStatement();

            // write the user's data to the database, set their status to online
            int num = stmt.executeUpdate("INSERT INTO [dbo].[account_details_table] " +
                    "(username, password, fname, lname, minitial, address, city, country, email, birthday, logged_in) " +
                    "VALUES (\'" + username + "\', \'" + hashedPassword + "\', \'" + fname + "\', \'" +
                    lname + "\', \'" + mInitial + "\', \'" + address + "\', \'" + city + "\', \'" +
                    country + "\', \'" + email + "\', \'" + bdate + "\'," + 1 + ")");
            num = stmt.executeUpdate("INSERT INTO [dbo].[profile_table] " +
                    "(username) " +
                    "VALUES (\'" + username + "\')");

            // create a subjects table for the user
            String sqlCreateTable = "CREATE TABLE dbo." + username + "_subject_table " +
                                    "(Subject VARCHAR(50) NOT NULL PRIMARY KEY, Rating DECIMAL(2,1), Hours DECIMAL(4,1) NOT NULL, Number NCHAR(10) NOT NULL)";
            num = stmt.executeUpdate(sqlCreateTable);
            conn2.close();

            // let the new user into the landing page
            Intent intent = new Intent(this, UserLanding.class);
            startActivity(intent);
        } catch (Exception e)
        {
            Context context = getApplicationContext();
            Log.w("Error2", "" + e.getMessage());
            CharSequence text = "Failed to Connect";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();

        }


    }
}
