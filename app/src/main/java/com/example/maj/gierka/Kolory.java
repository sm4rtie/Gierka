package com.example.maj.gierka;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Kolory extends AppCompatActivity implements View.OnClickListener{
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private static final int REQUEST_ENABLE_BT = 1;
    private ImageView appImageView;
    private Drawable [] drawables = null;
    private Button correctAnsw;
    private Button ans2;
    private Button ans3;
    private String correct;
    private String userAnswer;
    private TextView anscheckTxt;
    private TextView countdownTxt;
    private long time;
    private Class gp;
    private CountDownTimer cTimer=null;
    private int tura;

    ArrayList<String> answ = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_kolory_activity);

        countdownTxt = (TextView) findViewById(R.id.countdownTxt);
        countdownTxt.setGravity(Gravity.CENTER);
        countdownTxt.setTextSize(25);
        countdownTxt.setTextColor(Color.WHITE);
        anscheckTxt = (TextView) findViewById(R.id.anscheckTxt);
        anscheckTxt.setGravity(Gravity.CENTER);
        anscheckTxt.setTextSize(25);
        anscheckTxt.setTextColor(Color.WHITE);
        appImageView = (ImageView) findViewById(R.id.imageView2);
        drawables = new Drawable[]{
                getResources().getDrawable(R.drawable.game_bg),
                getResources().getDrawable(R.drawable.bgtest)
        };

        setScreen();
        tura=1;
    }

    private void setScreen(){
        if(tura<=answ.size()){
            startTimer();
            // startTimer2();
            KoloryActivity qz = (new KoloryActivity(this));
            qz.genAnswers();
            int nr =  qz.getResID();
            answ = qz.getAnswers();
            correct = answ.get(0);
            Collections.shuffle(answ);
            correctAnsw = (Button) findViewById(R.id.button);
            userAnswer = answ.get(new Random().nextInt(answ.size()));
            correctAnsw.setText("Czy to kolor " + userAnswer+"?");
            appImageView.setImageResource(nr);
            tura+=1; }
        else {
            gp = new GamePicker().getRandGame();
            openActivity(gp);
        }
    }

    public int checkAnsw(String answ){
        int point;
        if(answ.equals(correct)){
            point = 1;
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
        checkAnsw(userAnswer);
    }

    public void openActivity(Class class_) {
        Intent intent = new Intent(getApplicationContext(), class_);
        startActivity(intent);
    }

    void startTimer() {
        cTimer = new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
                countdownTxt.setText(" " + millisUntilFinished / 1000);
                time = 6000 - millisUntilFinished;
                //userAnswer = answ.get(new Random().nextInt(answ.size()));
                //correctAnsw.setText("Czy to "+ userAnswer+"?");

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
