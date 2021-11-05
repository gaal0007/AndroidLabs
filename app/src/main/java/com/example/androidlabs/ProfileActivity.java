package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    static ImageButton mImageButton = null;
    static Button mButton = null;
    static Button weatherButton = null;
    static Button toolbarButton = null;
    int toolbarActivityResult;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent goToChatActivity = new Intent( this, ChatRoomActivity.class);
        Intent goToWeatherActivity = new Intent(this, WeatherForcast.class);
        Intent goToToolbarActivity = new Intent(this, TestToolbar.class);
        mImageButton = findViewById(R.id.picButton);
        mImageButton.setOnClickListener( bt -> dispatchTakePictureIntent());
        mButton = findViewById(R.id.chatButton);
        mButton.setOnClickListener( bt -> startActivity(goToChatActivity));
        weatherButton = findViewById(R.id.weatherButton);
        weatherButton.setOnClickListener( bt -> startActivity(goToWeatherActivity));
        toolbarButton = findViewById(R.id.toolbarButton);
        toolbarButton.setOnClickListener(bt -> startActivityForResult(goToToolbarActivity, toolbarActivityResult));
        Log.e(ACTIVITY_NAME, "In function:" + " onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME, "In function:" + " onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME, "In function:" + " onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME, "In function:" + " onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(ACTIVITY_NAME, "In function:" + " onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "In function:" + " onDestroy");
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
            Log.e(ACTIVITY_NAME, "In function:" + " onActivityResult");
        }
        if(resultCode == 500)
            finish();
    }


}