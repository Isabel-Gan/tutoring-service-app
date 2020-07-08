package com.example.tutoring_service_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CurrentRequestFragment extends Fragment {

    //recycler view with card views https://youtu.be/oq_xGMN0mRE
    RecyclerView recyclerView;
    RecyclerViewAdapter rAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_request, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        rAdapter = new RecyclerViewAdapter(getActivity(), getStudentList());
        recyclerView.setAdapter(rAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    private ArrayList<LearnRequestItem> getStudentList() {
        ArrayList<LearnRequestItem> requests = new ArrayList<>();

        LearnRequestItem r = new LearnRequestItem();
        r.setSubject("bio");
        r.setDescription("mitochondria is the powerhouse of what?");
        r.setStatus(false);
        requests.add(r);

        r = new LearnRequestItem();
        r.setSubject("cs");
        r.setDescription("ew android studio");
        r.setStatus(true);
        requests.add(r);

        return requests;
    }
}
