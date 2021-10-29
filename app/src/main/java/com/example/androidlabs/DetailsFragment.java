package com.example.androidlabs;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


public class DetailsFragment extends Fragment {

    static Button hideButton = null;
    private AppCompatActivity parentActivity;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle dataPassed = getArguments();
        View myView = inflater.inflate(R.layout.fragment_details, container, false);

        hideButton = myView.findViewById(R.id.hideFragment);
        hideButton.setOnClickListener( btn -> hideFragment());

        TextView fragmentMessage = myView.findViewById(R.id.fragmentMessage);
        TextView fragmentId = myView.findViewById(R.id.fragmentId);
        CheckBox isSendMessage = myView.findViewById(R.id.isSendMessage);

        fragmentMessage.setText(dataPassed.getString("content"));
        fragmentId.setText(String.valueOf(dataPassed.getLong("id")));
        isSendMessage.setActivated(dataPassed.getBoolean("isSent"));

        return myView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        parentActivity = (AppCompatActivity)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void hideFragment()
    {
        parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}