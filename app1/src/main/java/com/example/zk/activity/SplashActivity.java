package com.example.zk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private String TAG="wodelog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        Timer timer=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,Main.class));
                finish();
            }
        };
        timer.schedule(task,0);

    }
}
