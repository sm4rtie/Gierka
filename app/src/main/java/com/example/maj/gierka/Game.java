package com.example.maj.gierka;

import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

public class Game extends AppCompatActivity {
    private ImageView appImageView;
    private Button appButton;
    private Drawable drawable;
    private Random random;
    private Drawable [] drawables = null;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_game);

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
        qz.genAnswers();
        int nr =  qz.getResID();
        answ = qz.getAnswers();
        radioButton = (RadioButton) findViewById(R.id.radioButton);
        radioButton.setText(answ.get(0));
        appImageView.setImageResource(nr); // set the image to the ImageView
    }
}
