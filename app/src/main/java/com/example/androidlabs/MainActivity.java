package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grid);

        final Button btn = findViewById(R.id.button);
        btn.setOnClickListener( (click) -> { Toast.makeText(MainActivity.this, getString(R.string.toast_message), Toast.LENGTH_LONG).show(); } );
        final Switch toggle = findViewById(R.id.toggle);
        toggle.setChecked(true);
        toggle.setOnCheckedChangeListener( (CompoundButton cb, boolean b) -> {Snackbar.make(cb, "The switch is now " + b, Snackbar.LENGTH_LONG)
                                                                                      .setAction("Undo", click -> cb.setChecked(!b))
                                                                                      .show(); } );
        final CheckBox checkBox = findViewById(R.id.checkbox);
        checkBox.setChecked(true);
        checkBox.setOnCheckedChangeListener( (CompoundButton cb, boolean b) -> {Snackbar.make(cb, "The checkbox is now " + b, Snackbar.LENGTH_LONG)
                .setAction("Undo", click -> cb.setChecked(!b))
                .show(); } );

    }

}