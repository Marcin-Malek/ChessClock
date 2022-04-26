package com.mbli.chessclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView text1, text2, text3, text4;
    CountDownTimer timer1, timer2;
    RelativeLayout lay1, lay2;
    Button reset, settings, pause;
    Long time1, time2, time, increment;
    ConstraintSet set;
    View timeline1, timeline2;
    ConstraintLayout cLayout;
    Integer clickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        cLayout = findViewById(R.id.clayout);

        lay1 = findViewById(R.id.lay1);
        lay2 = findViewById(R.id.lay2);

        timeline1 = findViewById(R.id.timeline1);
        timeline2 = findViewById(R.id.timeline2);

        reset = findViewById(R.id.reset);
        pause = findViewById(R.id.pause);
        settings = findViewById(R.id.settings);

        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text4 = findViewById(R.id.text4);

        set = new ConstraintSet();

        time = 600000L;
        increment = 0L;


        final SpinnerDialog mSpinnerDialog = new SpinnerDialog(MainActivity.this, new SpinnerDialog.DialogListener() {
            public void cancelled() {

            }
            public void ready(int t, int i) {
                ResetClock();

                time = Long.valueOf(t);
                increment = Long.valueOf(i*1000);
                time1 = time2 = time;

                int seconds = (int) (time / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                text1.setText(String.format("%d:%02d", minutes, seconds));
                text2.setText(String.format("%d:%02d", minutes, seconds));

            }
        });

        time1 = time2 = time;

        ResetClock();


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSpinnerDialog.show();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetClock();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PauseClock();
            }
        });

        lay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lay1.setClickable(false);
                lay2.setClickable(true);
                if (time2 >= time*0.1){
                    lay2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }else{
                    lay2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                lay1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                if (clickCount >= 1) {
                    time1 = time1 + increment;
                    int seconds = (int) (time1 / 1000);
                    int minutes = seconds / 60;
                    seconds = seconds % 60;
                    text1.setText(String.format("%d:%02d", minutes, seconds));
                    text3.setText(String.format("%d:%02d", minutes, seconds));
                    set.clone(cLayout);
                    set.constrainPercentWidth(R.id.timeline1, (float) time1/time);
                    set.applyTo(cLayout);
                }
                BlackMove();
            }
        });

        lay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lay2.setClickable(false);
                lay1.setClickable(true);
                if (time1 >= time*0.1){
                    lay1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }else{
                    lay1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                lay2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                if (clickCount >= 1) {
                    time2 = time2 + increment;
                    int seconds = (int) (time2 / 1000);
                    int minutes = seconds / 60;
                    seconds = seconds % 60;
                    text2.setText(String.format("%d:%02d", minutes, seconds));
                    text4.setText(String.format("%d:%02d", minutes, seconds));
                    set.clone(cLayout);
                    set.constrainPercentWidth(R.id.timeline2, (float) time2/time);
                    set.applyTo(cLayout);
                }
                WhiteMove();
            }
        });

    }

    private void WhiteMove() {
        clickCount += 1;
        if (clickCount >= 2) {
            timer2.cancel();
        }
        timer1 = new CountDownTimer (time1, 20) {

            public void onTick(long millisUntilFinished) {
                time1 = millisUntilFinished;
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                text1.setText(String.format("%d:%02d", minutes, seconds));
                text3.setText(String.format("%d:%02d", minutes, seconds));
                set.clone(cLayout);
                set.constrainPercentWidth(R.id.timeline1, (float) time1/time);
                set.applyTo(cLayout);
                if (time1 <= time*0.1){
                    lay1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
            }

            public void onFinish() {
                text1.setText("0:00");
                lay1.setClickable(false);
                lay2.setClickable(false);
            }
        };
        timer1.start();
    }

    private void BlackMove() {
        clickCount += 1;
        if (clickCount >= 2) {
            timer1.cancel();
        }
        timer2 = new CountDownTimer (time2, 20) {

            public void onTick(long millisUntilFinished) {
                time2 = millisUntilFinished;
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                text2.setText(String.format("%d:%02d", minutes, seconds));
                text4.setText(String.format("%d:%02d", minutes, seconds));
                set.clone(cLayout);
                set.constrainPercentWidth(R.id.timeline2, (float) time2/time);
                set.applyTo(cLayout);

                if (time2 <= time*0.1){
                    lay2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
            }

            public void onFinish() {
                text2.setText("0:00");
                lay1.setClickable(false);
                lay2.setClickable(false);
            }
        };
        timer2.start();
    }

    private void ResetClock() {
        if (!time1.equals(time)){
            timer1.cancel();
        }
        if (!time2.equals(time)) {
            timer2.cancel();
        }
        int seconds = (int) (time / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        text1.setText(String.format("%d:%02d", minutes, seconds));
        text2.setText(String.format("%d:%02d", minutes, seconds));
        text3.setText(null);
        text4.setText(null);
        lay1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        lay2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        set.clone(cLayout);
        set.constrainPercentWidth(R.id.timeline1, 1);
        set.applyTo(cLayout);
        set.clone(cLayout);
        set.constrainPercentWidth(R.id.timeline2, 1);
        set.applyTo(cLayout);
        time1 = time2 = time;
        lay1.setClickable(true);
        lay2.setClickable(true);
        clickCount = 0;
    }

    private void PauseClock(){
        if (!time1.equals(time)){
            timer1.cancel();
        }
        if (!time2.equals(time)) {
            timer2.cancel();
        }
        lay1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        lay2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        lay1.setClickable(true);
        lay2.setClickable(true);
    }

}
