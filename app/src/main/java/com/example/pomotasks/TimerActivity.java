package com.example.pomotasks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS =  30000;
    private TextView timer;
    private TextView name;
    private FloatingActionButton pauseplay;
    private FloatingActionButton stop;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long timeLeft = START_TIME_IN_MILLIS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        pauseplay = findViewById(R.id.startPauseButton);
        stop = findViewById(R.id.stopButton);
        timer = findViewById(R.id.timerScreen);
        name = findViewById(R.id.taskNameDisplay);

        Intent intent = getIntent();
        String tname = intent.getStringExtra("taskName");
        name.setText(tname);

        pauseplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerRunning){
                    pauseTimer();
                } else{
                    startTimer();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    resetTimer();
            }
        });

        updateCountDownText();

    }

    private void startTimer(){
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                pauseplay.setImageResource(R.drawable.ic_play);
//                pauseplay.setVisibility(View.INVISIBLE);
                pauseplay.setEnabled(false);
                stop.setEnabled(true);
                stop.setImageResource(R.drawable.ic_refresh);
            }
        }.start();
        timerRunning = true;
        pauseplay.setImageResource(R.drawable.ic_pause);
//        stop.setVisibility(View.INVISIBLE);
        stop.setEnabled(false);
    }

    private void pauseTimer(){
        countDownTimer.cancel();
        timerRunning = false;
        pauseplay.setImageResource(R.drawable.ic_play);
//        stop.setVisibility(View.VISIBLE);
        stop.setEnabled(true);
    }

    private void resetTimer(){
        timeLeft = START_TIME_IN_MILLIS;
        updateCountDownText();
//        stop.setVisibility(View.INVISIBLE);
//        pauseplay.setVisibility(View.VISIBLE);
        stop.setEnabled(false);
        pauseplay.setEnabled(true);
    }

    private void updateCountDownText(){
        int minutes = (int) (timeLeft/1000)/60;
        int seconds = (int) (timeLeft/1000)%60;
        String timeLeft = String.format(Locale.getDefault(), "%02d:%02d",minutes,seconds);
        timer.setText((timeLeft));
    }
}