package com.example.tutoring_service_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<StudentItemHolder> {

    Context c;
    ArrayList<StudentItem> studentItems;

    public RecyclerViewAdapter(Context c, ArrayList<StudentItem> studentItems) {
        this.c = c;
        this.studentItems = studentItems;
    }

    @NonNull
    @Override
    public StudentItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflate the student items
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, null);

        return new StudentItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentItemHolder holder, int i) {
        holder.studentName.setText(studentItems.get(i).getName());
        holder.studentSubject.setText(studentItems.get(i).getSubject());
    }

    @Override
    public int getItemCount() {
        return studentItems.size();
    }
}
