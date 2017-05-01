package com.example.maj.gierka;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Game extends AppCompatActivity implements View.OnClickListener{
    private ImageView appImageView;
    private Button appButton;
    private Drawable drawable;
    private Random random;
    private Drawable [] drawables = null;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button correctAnsw;
    private Button ans2;
    private Button ans3;
    private String correct;
    private TextView countdown;
    private long time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_game);
        countdown = (TextView) findViewById(R.id.textView2);
        appImageView = (ImageView) findViewById(R.id.imageView);
        drawables = new Drawable[]{
                getResources().getDrawable(R.drawable.game_bg),
                getResources().getDrawable(R.drawable.bgtest)
        };
        // String[] imageArray = {"Phoebe_Buffay.png", "Monica_Geller.jpg"};
        Random rand = new Random();

       // int rndInt = rand.nextInt(drawables.length);
       // drawable = drawables[rndInt];

       // final TypedArray imgs = getResources().obtainTypedArray(R.array.apptour);
       // final int rndInt = rand.nextInt(imgs.length());
        //final int resID = imgs.getResourceId(rndInt, 0);
        CharactersQuiz qz = (new CharactersQuiz(this));
        ArrayList<String> answ = new ArrayList<>();
        List<String> tmp = new ArrayList<>();
        qz.genAnswers();
        int nr =  qz.getResID();
        answ = qz.getAnswers();
        correct = answ.get(0);
        Collections.shuffle(answ);
        correctAnsw = (Button) findViewById(R.id.button);
        correctAnsw.setText(answ.get(0));
        ans2 = (Button) findViewById(R.id.button2);
        ans2.setText(answ.get(1));
        ans3 = (Button) findViewById(R.id.button3);
        ans3.setText(answ.get(2));

        //timerek

        new CountDownTimer(11000, 1000) {

            public void onTick(long millisUntilFinished) {
                countdown.setText(" " + millisUntilFinished / 1000);
                time = 11000 - millisUntilFinished;
            }

            public void onFinish() {
                countdown.setText("done!");
            }
        }.start();


        //int rndInt = rand.nextInt(3);
       // radioButton = (RadioButton) findViewById(R.id.radioButton);
       // radioButton.setText(answ.get(0));
        appImageView.setImageResource(nr); // set the image to the ImageView
    }
    public int checkAnsw(String answ){
        int point;
        if(answ.equals(correct)){
            point = 1;
            countdown.setText("Punkt!");
        }

        else point=0;
        return point;
    }
    @Override
    public void onClick(View view) {
        String buttonText = (String)((Button)view).getText().toString();
        checkAnsw(buttonText);
    }
}
