package com.example.maj.gierka;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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

public class AlfabetActivity extends AppCompatActivity implements View.OnClickListener{
    private Button gameBtn4;
    private TextView countdownTxt;
    private TextView anscheckTxt;
    //private TextView pointTxt4;
    private ImageView gameImage;
    private String answer;
    private long time;
    private Class gp;
    CountDownTimer cTimer = null;
    private int point;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_alfabet_activity);

        gameBtn4 = (Button) findViewById(R.id.gameBtn4);
        countdownTxt = (TextView) findViewById(R.id.countdownTxt);
        countdownTxt.setGravity(Gravity.CENTER);
        anscheckTxt = (TextView) findViewById(R.id.anscheckTxt);
        anscheckTxt.setGravity(Gravity.CENTER);
        //pointTxt4 = (TextView) findViewById(R.id.pointTxt4);
        //pointTxt4.setGravity(Gravity.CENTER);
        gameImage = (ImageView) findViewById(R.id.gameImg4);
        setScreen();




    }
    public int checkAnsw(){
        //int point;
        if(answer.equalsIgnoreCase("tak")){
            point = 1;
            //pointTxt4.setText(Integer.toString(point) + " PKT");
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
        Alfabet alfabet = new Alfabet(this);
        answer = alfabet.generateQuestion();
        int nr =  alfabet.getResID();
        gameImage.setImageResource(nr);
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
