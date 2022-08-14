
package com.cat1.voicerecorder;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.Manifest.permission.RECORD_AUDIO;

public class RecordingActivity extends AppCompatActivity {

    private boolean isRecording= true;
    private Button record;
    private MediaRecorder mediaRecorder;
    private static String recordFile = null;
    private static final String LOG_TAG = "AudioRecording";
    private Chronometer timer;
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        record = findViewById(R.id.record);
        timer = findViewById(R.id.timer);
//        if(checkPermissions()){

        if(CheckPermissions()) {
            startRecording();

        }
        else{
            RequestPermissions();
            onRestart();
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_AUDIO_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (permissionToRecord && permissionToStore) {
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                        onRestart();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
    public boolean CheckPermissions() {
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result1 == PackageManager.PERMISSION_GRANTED;
    }
    private void RequestPermissions() {
        ActivityCompat.requestPermissions(RecordingActivity.this, new String[]{RECORD_AUDIO}, REQUEST_AUDIO_PERMISSION_CODE);
        onRestart();
    }


    public void recording(View view) {
        if(isRecording){
            stopRecording();
            isRecording = false;

        }
        else{
            startRecording();
            isRecording = true;
        }
    }

    private void stopRecording() {

        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder= null;
        timer.stop();
        record.setText("RECORD");
        Toast toastStop = Toast.makeText(getBaseContext(), "Recording stopped",Toast.LENGTH_SHORT);
        toastStop.show();
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);


    }
    private void startRecording(){

        record.setText("STOP");
        String recordFilePath = this.getExternalFilesDir("/").getAbsolutePath();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.ENGLISH);
        Date current = new Date();
        recordFile = "Recording_"+ format1.format(current) +".3gp";
        Toast toastStart = Toast.makeText(this, "Recording started",Toast.LENGTH_SHORT);
        toastStart.show();
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(recordFilePath + "/" + recordFile);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaRecorder.start();
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();

    }
    @Override
    public void onStop(){
        super.onStop();
        if(isRecording) {
            stopRecording();
        }
    }
}