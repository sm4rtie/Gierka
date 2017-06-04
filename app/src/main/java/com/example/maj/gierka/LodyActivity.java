package com.example.maj.gierka;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class LodyActivity extends AppCompatActivity implements View.OnClickListener{
    private Button gameBtn5;
    private TextView countdownTxt;
    private TextView anscheckTxt;
    //private TextView pointTxt5;
    private ImageView gameImg5;
    private String answer;
    private long time;
    private Class gp;
    CountDownTimer cTimer = null;
    private int point;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_lody_activity);

        gameBtn5 = (Button) findViewById(R.id.gameBtn5);
        countdownTxt = (TextView) findViewById(R.id.countdownTxt);
        countdownTxt.setGravity(Gravity.CENTER);
        anscheckTxt = (TextView) findViewById(R.id.anscheckTxt);
        anscheckTxt.setGravity(Gravity.CENTER);
        //pointTxt5 = (TextView) findViewById(R.id.pointTxt5);
        //pointTxt5.setGravity(Gravity.CENTER);
        gameImg5 = (ImageView) findViewById(R.id.gameImg5);
        setScreen();
    }

    public int checkAnsw(){
        //int point;
        if(answer.equalsIgnoreCase("tak")){
            point = 1;
            //pointTxt5.setText(Integer.toString(point) + " PKT");
            anscheckTxt.setText("Punkt!");
            gp = new GamePicker().getRandGame();
            openActivity(gp);
        } else {
            point=0;
            anscheckTxt.setText("Źle!");
            setScreen();

        }
        return point;
    }

    @Override
    public void onClick(View view) {
        cancelTimer();
        checkAnsw();
    }
    public void openActivity(Class class_) {
        Intent intent = new Intent(getApplicationContext(), class_);
        startActivity(intent);
    }
    public void setScreen(){
        startTimer();
        Random rand = new Random();
        Lody lody = new Lody(this);
        answer = lody.generateQuestion();
        int nr =  lody.getResID();
        gameImg5.setImageResource(nr);
    }

    void startTimer() {
        cTimer = new CountDownTimer(6000, 1000) {
            public void onTick(long millisUntilFinished) {
                countdownTxt.setText(" " + millisUntilFinished / 1000);
                time = 6000 - millisUntilFinished;
            }

            public void onFinish() {
                cancelTimer();
                setScreen();
            }
        };
        cTimer.start();
    }

    //cancel timer
    void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }
}
