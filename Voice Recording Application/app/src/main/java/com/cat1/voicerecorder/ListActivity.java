package com.cat1.voicerecorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class ListActivity extends AppCompatActivity implements RecordingListAdapter.onItemListClick {


    private RecyclerView recordingsList;
    private File[] files;
    private RecordingListAdapter recordingListAdapter;
    private boolean isPlaying = false;
    private MediaPlayer mediaPlayer = null;
    private File fileToPlay;
    private int position;

    private Button pauseBtn;
    private Button prevBtn;
    private TextView totalTimer;
    private TextView recordingName;
    private TextView textView;
    private SeekBar seekBar;
    private Handler handler;
    private Runnable updateSeekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recordingsList = findViewById(R.id.audio_list_view);
        pauseBtn = findViewById(R.id.pause);
        recordingName = findViewById(R.id.recordingName);
        textView = findViewById(R.id.textView);
        seekBar = findViewById(R.id.seekBar);
        totalTimer = findViewById(R.id.totalTime);

        String path = this.getExternalFilesDir("/").getAbsolutePath();
        File directory = new File(path);
        files = directory.listFiles();
        recordingListAdapter = new RecordingListAdapter(files, this);
        recordingsList.setHasFixedSize(true);
        recordingsList.setLayoutManager(new LinearLayoutManager(this));
        recordingsList.setAdapter(recordingListAdapter);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                pauseAudio();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                mediaPlayer.seekTo(progress);
                resumeAudio();
            }
        });

    }

    @Override
    public void onClickListener(File file, int position) {
        fileToPlay = file;


        if (isPlaying) {
            stopRecording();
            textView.setText(R.string.not_playing);
        }
        playRecording(fileToPlay);
    }

    private void pauseAudio() {
        isPlaying = false;
        mediaPlayer.pause();
        pauseBtn.setBackgroundResource(R.drawable.ic_play);
        handler.removeCallbacks(updateSeekbar);
        textView.setText(R.string.not_playing);
    }

    private void resumeAudio() {
        isPlaying = true;
        mediaPlayer.start();
        pauseBtn.setBackgroundResource(R.drawable.ic_pause);
        updateRunnable();
        handler.postDelayed(updateSeekbar, 0);
    }

    private void stopRecording() {
        isPlaying = false;
        pauseBtn.setBackgroundResource(R.drawable.ic_play);
        mediaPlayer.stop();
        textView.setText(R.string.not_playing);
    }

    private void playRecording(File fileToPlay) {
        mediaPlayer = new MediaPlayer();
        try {

            mediaPlayer.setDataSource(fileToPlay.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pauseBtn.setBackgroundResource(R.drawable.ic_pause);
        recordingName.setText(fileToPlay.getName());
        textView.setText(R.string.playing);

        isPlaying = true;

        mediaPlayer.setOnCompletionListener(mp -> {
            stopRecording();
            textView.setText(R.string.complete);
        });
        seekBar.setMax(mediaPlayer.getDuration());
        handler = new Handler();
        updateRunnable();
        handler.postDelayed(updateSeekbar, 0);
        String totalTime = createTimerLabel(mediaPlayer.getDuration());
        totalTimer.setText(totalTime);
    }

    private void updateRunnable() {
        updateSeekbar = new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 500);
            }
        };
    }

    public void control(View view) {
        if (isPlaying) {
            resumeAudio();
        } else {
            if (fileToPlay != null) {
                pauseAudio();
            } else {
                textView.setText(R.string.not_playing);
            }

        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (isPlaying) {
            stopRecording();
        }
    }


    public void repeat(View view) {
        mediaPlayer.stop();
        mediaPlayer.release();
        position = (int) ((position + 1) % fileToPlay.length());
        Uri u = Uri.parse(fileToPlay.getAbsoluteFile().toString());
        mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
        recordingName.setText(fileToPlay.getName());
        mediaPlayer.start();
    }

    public String createTimerLabel(int duration) {
        String timerLabel = " ";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;
        timerLabel += min + ":";
        if (sec < 10) timerLabel += "0";
        timerLabel += sec;

        return timerLabel;

    }
}