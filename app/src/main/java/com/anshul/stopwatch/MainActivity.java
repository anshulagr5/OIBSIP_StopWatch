package com.anshul.stopwatch;

import android.app.Activity;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.os.Bundle;

import android.widget.Chronometer;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    Chronometer chronometer;
    ImageButton start,stop;

    private boolean isResume;
    Handler handler;
    long tMS, tS, tB, Update =0L;
    int sec,min,milliSec;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer=findViewById(R.id.chronometer);
        start=findViewById(R.id.start);
        stop=findViewById(R.id.stop);

        handler=new Handler();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isResume)
                {
                    tS =SystemClock.uptimeMillis();
                    handler.postDelayed(runnable,0);
                    chronometer.start();
                    isResume=true;
                    stop.setVisibility(View.GONE);
                    start.setImageDrawable(getResources().getDrawable(R.drawable.ic_hold));
                }
                else
                {
                    tB += tMS;
                    handler.removeCallbacks(runnable);
                    chronometer.stop();
                    isResume=false;
                    stop.setVisibility(View.VISIBLE);
                    start.setImageDrawable(getResources().getDrawable(R.drawable.ic_start));

                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isResume)
                {
                    start.setImageDrawable(getResources().getDrawable(R.drawable.ic_start));
                    tMS =0L;tS =0L;tB =0L;Update =0L;
                    sec=0;min=0;milliSec=0;
                    chronometer.setText("00:00:00");
                }
            }
        });

    }

    public Runnable runnable=new Runnable() {
        @Override
        public void run() {
            tMS = SystemClock.uptimeMillis()- tS;
            Update = tB + tMS;
            sec=(int)(Update /1000);
            min=sec/60;
            sec=sec%60;
            milliSec=(int) (Update %100);
            chronometer.setText(String.format("%02d",min)+":"+
                                String.format("%02d",sec)+":"+String.format("%02d",milliSec));
            handler.postDelayed(this,60);
        }
    };
}
