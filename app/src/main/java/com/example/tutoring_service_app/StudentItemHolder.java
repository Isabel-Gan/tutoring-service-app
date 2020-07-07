package com.example.tutoring_service_app;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentItemHolder extends RecyclerView.ViewHolder{

    TextView studentName;
    TextView studentSubject;

    public StudentItemHolder(@NonNull View itemView) {
        super(itemView);

        this.studentName = itemView.findViewById(R.id.studentName);
        this.studentSubject = itemView.findViewById(R.id.studentSubject);
    }
}
