package com.example.tutoring_service_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<LearnRequestHolder> {

    Context c;
    ArrayList<LearnRequestItem> learnRequestItems;
    OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public RecyclerViewAdapter(Context c, ArrayList<LearnRequestItem> learnRequestItems) {
        this.c = c;
        this.learnRequestItems = learnRequestItems;
    }

    @NonNull
    @Override
    public LearnRequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflate the student items
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.learn_request_item, null);

        return new LearnRequestHolder(view, (OnItemClickListener) mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull LearnRequestHolder holder, int i) {
        holder.deleteImage.setImageResource(learnRequestItems.get(i).getImageResource());
        holder.studentSubject.setText(learnRequestItems.get(i).getSubject());
        holder.description.setText("Description: "+ learnRequestItems.get(i).getDescription());
        holder.requested.setText(learnRequestItems.get(i).getRequested());
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
