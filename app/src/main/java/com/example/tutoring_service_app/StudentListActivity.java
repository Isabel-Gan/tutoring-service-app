package com.example.tutoring_service_app;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends AppCompatActivity {

    private ListView students;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        //https://stackoverflow.com/questions/5070830/populating-a-listview-using-an-arraylist
        students = (ListView) findViewById(R.id.studentsList);

        // Instantiating an array list (you don't need to do this,
        // you already have yours).
        List<String> studentArrList = new ArrayList<String>();
        studentArrList.add("foo");
        studentArrList.add("bar");

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                studentArrList);

        students.setAdapter(arrayAdapter);
    }

}
