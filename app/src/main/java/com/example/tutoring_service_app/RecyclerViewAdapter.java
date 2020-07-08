package com.example.tutoring_service_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<LearnRequestHolder> {

    Context c;
    ArrayList<LearnRequestItem> learnRequestItems;

    public RecyclerViewAdapter(Context c, ArrayList<LearnRequestItem> learnRequestItems) {
        this.c = c;
        this.learnRequestItems = learnRequestItems;
    }

    @NonNull
    @Override
    public LearnRequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflate the student items
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.learn_request_item, null);

        return new LearnRequestHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LearnRequestHolder holder, int i) {
        holder.studentSubject.setText(learnRequestItems.get(i).getSubject());
        holder.description.setText("Description: "+ learnRequestItems.get(i).getDescription());
        if (learnRequestItems.get(i).getStatus()) {
            holder.status.setText("Status: Accepted");
        }
        else {
            holder.status.setText("Status: Pending");
        }
    }

    @Override
    public int getItemCount() {
        return learnRequestItems.size();
    }
}
