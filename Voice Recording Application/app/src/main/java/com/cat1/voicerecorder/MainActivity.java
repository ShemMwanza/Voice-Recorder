package com.cat1.voicerecorder;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;



public class MainActivity extends AppCompatActivity {

    private static String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void startRecording(View view) {
        Log.d(LOG_TAG,"Button Clicked");
        Intent intent = new Intent(this, RecordingActivity.class);
        startActivity(intent);


    }

    public void viewList(View view) {
        Log.d(LOG_TAG,"Button Clicked");
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);


    }
}