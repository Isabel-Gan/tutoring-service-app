package com.example.tutoring_service_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GeneralRequestsFragment extends Fragment {

    private String username;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // inflate the view
        View view = inflater.inflate(R.layout.fragment_general_requests, container, false);

        // get the username from the intent
        username = getArguments().getString("username");

        return view;
    }
}
