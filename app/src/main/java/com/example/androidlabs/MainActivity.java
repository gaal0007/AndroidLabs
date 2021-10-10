package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{
    SharedPreferences prefs = null;
    SharedPreferences.Editor edit = null;
    EditText email = null;
    Button loginButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        edit = prefs.edit();
        String savedEmail = prefs.getString("ReserveName", "");
        email = findViewById(R.id.emailField);
        email.setText(savedEmail);
        Intent goToProfileActivity = new Intent(this, ProfileActivity.class);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(bt -> startActivity(goToProfileActivity));
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        String enteredEmail = email.getText().toString();
        edit.putString("ReserveName", enteredEmail);
    }
}