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

public class KwadratyActivity extends AppCompatActivity implements View.OnClickListener{
    private Button gameBtn16;
    private TextView countdownTxt;
    private TextView anscheckTxt;
    private TextView scoreLabel;
    private ImageView gameImage;
    private String answer;
    private long time;
    private Class gp;
    CountDownTimer cTimer = null;
    result res = new result();
    int point = res.getPoint();
    private int tura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_kwadraty_activity);

        gameBtn16 = (Button) findViewById(R.id.gameBtn16);
        countdownTxt = (TextView) findViewById(R.id.countdownTxt);
        countdownTxt.setGravity(Gravity.CENTER);
        anscheckTxt = (TextView) findViewById(R.id.anscheckTxt);
        anscheckTxt.setGravity(Gravity.CENTER);
        gameImage = (ImageView) findViewById(R.id.gameImg16);
        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        scoreLabel.setText("Score : " + point);
        setScreen();
        tura++;
    }
    public int checkAnsw(){
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
        Kwadraty kwadraty = new Kwadraty(this);
        answer = kwadraty.generateQuestion();
        int nr =  kwadraty.getResID();
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
