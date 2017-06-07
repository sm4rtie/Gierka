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

public class KoloryActivity extends AppCompatActivity implements View.OnClickListener{
    private Button gameBtn8;
    private TextView countdownTxt;
    private TextView anscheckTxt;
    private TextView scoreLabel;
    private ImageView gameImage;
    private String answer;
    private long time;
    private Class gp;
    CountDownTimer cTimer = null;
    TextView tv;
    result res = new result();
    int point = res.getPoint();
    private int tura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_kolory_activity);

        gameBtn8 = (Button) findViewById(R.id.gameBtn8);
        countdownTxt = (TextView) findViewById(R.id.countdownTxt);
        countdownTxt.setGravity(Gravity.CENTER);
        anscheckTxt = (TextView) findViewById(R.id.anscheckTxt);
        anscheckTxt.setGravity(Gravity.CENTER);
        gameImage = (ImageView) findViewById(R.id.gameImg8);
        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        scoreLabel.setText("Score : " + point);
        setScreen();
        tura++;
    }

    public int checkAnsw(){
        //int point;
        if(answer.equalsIgnoreCase("tak")){
            res.setPoint(point+1);
            point += 1;
            scoreLabel.setText("Score : " + point);
            anscheckTxt.setText("Punkt!");
            gp = new GamePicker().getRandGame();
            openActivity(gp);
        } else {
            anscheckTxt.setText("Źle!");
            res.setPoint(point-1);
            point -= 1;
            scoreLabel.setText("Score : " + point);
            if(tura>6){
                gp = new GamePicker().getRandGame();
                openActivity(gp);
            }
            else {
                setScreen();
                tura++;
            }

        }

        //Intent intent = new Intent(getApplicationContext(), result.class);
        //intent.putExtra("SCORE", point);
        //startActivity(intent);
        BluetoothDev.writeResult(point);
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
        Kolory kolory = new Kolory(this);
        answer = kolory.generateQuestion();
        int nr =  kolory.getResID();
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
