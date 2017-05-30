package com.example.maj.gierka;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton practiceBtn;
    ImageButton playBtn;
    Button settingsBtn;
    PopupWindow popupWindow;
    LayoutInflater layoutInflater;
    RelativeLayout relativeLayout;
    CheckBox soundCheck;

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

        //test wybierania gierek
        //new GamePicker().generateArrayGames();
        gp = new GamePicker().getRandGame();

        //dodawanie gier do listy, potem wywalic do klasy bt

        //activities.add(Game.class);
        activities.add(MyszActivity.class);
        activities.add(KolkaActivity.class);
        activities.add(MinkaActivity.class);
        activities.add(AlfabetActivity.class);


        relativeLayout = (RelativeLayout) findViewById(R.id.relative);
        practiceBtn = (ImageButton) findViewById(R.id.practiceBtn);
        playBtn = (ImageButton) findViewById(R.id.playBtn);
        //soundCheck = (CheckBox) findViewById(R.id.soundCheck);
       // settingsBtn = (Button) findViewById(R.id.setBtn);
       /* settingsBtn.setOnClickListener(new View.OnClickListener() {
    @Override
            public void onClick(View view) {
        layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.settings_popup,null);
        popupWindow = new PopupWindow(container,400,400,true);
        popupWindow.showAtLocation(relativeLayout, Gravity.NO_GRAVITY,500,500);

        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent){
            popupWindow.dismiss();
                return true;
            }
        });
    }
        });*/
    }

    @Override
    public void onClick(View view) {
       // setContentView(R.layout.activity_game);

        Intent i=new Intent(getApplicationContext(),gp);
        startActivity(i);
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
