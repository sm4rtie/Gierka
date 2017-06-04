package com.example.maj.gierka;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.media.MediaPlayer;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton optionsBtn;
    ImageButton playBtn;
    RelativeLayout relativeLayout;

    //lista klas gier, potem trzeba podmienic z bluetoothem
    ArrayList<Class> activities = new ArrayList<>();
    private Class gp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.setContentView(R.layout.activity_main);
        gp = new GamePicker().getRandGame();

        //dodawanie gier do listy, potem wywalic do klasy bt
        activities.add(MyszActivity.class);
        activities.add(KolkaActivity.class);
        activities.add(MinkaActivity.class);
        activities.add(AlfabetActivity.class);

        //button sound
        final MediaPlayer clickButton = MediaPlayer.create(this, R.raw.click);

        relativeLayout = (RelativeLayout) findViewById(R.id.relative);
        optionsBtn = (ImageButton) findViewById(R.id.optionsBtn);
        playBtn = (ImageButton) findViewById(R.id.playBtn);

        optionsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent options = new Intent(MainActivity.this, Options.class);
                startActivity(options);
                clickButton.start();
            }
        });
    }

    @Override
    public void onClick(View view) {

        final MediaPlayer clickButton = MediaPlayer.create(this, R.raw.click);

        Intent i=new Intent(getApplicationContext(),gp);
        startActivity(i);
        clickButton.start();
    }
    public void openActivity(Class class_) {
        Intent intent = new Intent(getApplicationContext(), class_);
        startActivity(intent);
    }

    public void openRandomActivity(View view){

        Class that = activities.get(new Random().nextInt(activities.size()));

        activities.remove(that); // after using, remove from list

        openActivity(that);
    }
}
