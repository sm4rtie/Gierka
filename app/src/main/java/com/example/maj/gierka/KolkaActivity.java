package com.example.maj.gierka;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

public class KolkaActivity extends AppCompatActivity implements View.OnClickListener{
    private Button gameBtn2;
    private TextView countdownTxt;
    private ImageView gameImage;
    private String answer;
    private long time;
    private Class gp;
    CountDownTimer cTimer = null;
    private int point;
BluetoothConnectionService mBluetoothConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent();

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_kolka_activity);
        gameBtn2 = (Button) findViewById(R.id.gameBtn2);
        countdownTxt = (TextView) findViewById(R.id.countdownTxt);
        gameImage = (ImageView) findViewById(R.id.gameImg2);
       /* Random rand = new Random();
        Mysz mysz = new Mysz(this);
        answer = mysz.generateQuestion();
        int nr =  mysz.getResID();
        //answer = mysz.getAnswer();

        gameImage.setImageResource(nr);*/
        setScreen();




    }
    public int checkAnsw(){
        //int point;

        if(answer.equalsIgnoreCase("tak")){
            point = 1;
            countdownTxt.setText("Punkt!");
            gp = new GamePicker().getRandGame();
            openActivity(gp);
        }

        else {
            point=0;
            countdownTxt.setText("Zle!");
            setScreen();

        }

        return point;
    }
    @Override
    public void onClick(View view) {
        //String buttonText = (String)((Button)view).getText().toString();
        cancelTimer();
        checkAnsw();
        byte[] bytes = "Hello".getBytes(Charset.defaultCharset());
        try {

            mBluetoothConnection.write(bytes);
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }
    public void openActivity(Class class_) {
        Intent intent = new Intent(getApplicationContext(), class_);
        startActivity(intent);
    }
    public void setScreen(){
        /** new CountDownTimer(11000, 1000) {

         public void onTick(long millisUntilFinished) {
         countdownTxt.setText(" " + millisUntilFinished / 1000);
         time = 11000 - millisUntilFinished;

         }


         public void onFinish() {
         //countdownTxt.setText("done!");
         setScreen();
         }
         }.start();**/
        startTimer();
        Random rand = new Random();
        Kolka kolka = new Kolka(this);
        answer = kolka.generateQuestion();
        int nr =  kolka.getResID();
        //answer = mysz.getAnswer();

        gameImage.setImageResource(nr);

    }
    void startTimer() {
        cTimer = new CountDownTimer(11000, 1000) {
            public void onTick(long millisUntilFinished) {
                countdownTxt.setText(" " + millisUntilFinished / 1000);
                time = 11000 - millisUntilFinished;

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

  /*  public void openRandomActivity(){

        Class that = activities.get(new Random().nextInt(activities.size()));

        activities.remove(that); // after using, remove from list

        openActivity(that);
    }*/
}
