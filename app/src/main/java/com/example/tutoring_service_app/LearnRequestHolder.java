package com.example.tutoring_service_app;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LearnRequestHolder extends RecyclerView.ViewHolder{

    TextView studentSubject;
    TextView description;
    TextView status;
    TextView requested;
    ImageView deleteImage;

    public LearnRequestHolder(@NonNull View itemView, final RecyclerViewAdapter.OnItemClickListener listener) {
        super(itemView);

        this.studentSubject = itemView.findViewById(R.id.studentSubject);
        this.description = itemView.findViewById(R.id.description);
        this.status = itemView.findViewById(R.id.status);
        this.requested = itemView.findViewById(R.id.requestedUser);
        this.deleteImage = itemView.findViewById(R.id.deleteImage);

        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(position);
                    }
                }
            }
        });
    }
}
