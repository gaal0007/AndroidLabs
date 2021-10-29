package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.TextView;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        DetailsFragment df = new DetailsFragment();

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame, df).commit();
    }
}