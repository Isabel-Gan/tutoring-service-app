package com.example.tutoring_service_app;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LearnRequestHolder extends RecyclerView.ViewHolder{

    TextView studentSubject;
    TextView description;
    TextView status;

    public LearnRequestHolder(@NonNull View itemView) {
        super(itemView);

        this.studentSubject = itemView.findViewById(R.id.studentSubject);
        this.description = itemView.findViewById(R.id.description);
        this.status = itemView.findViewById(R.id.status);
    }
}
