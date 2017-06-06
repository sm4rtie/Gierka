package com.example.maj.gierka;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class result extends AppCompatActivity {

    private static int point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_result);

        TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        //TextView highScoreLabel = (TextView) findViewById(R.id.highScoreLabel);

        point = getIntent().getIntExtra("SCORE", 0);
        scoreLabel.setText("Score : " + point);
/*
        SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int hightScore = settings.getInt("HIGH_SCORE", 0);

        if (point > hightScore){

            highScoreLabel.setText("High Score : " + point);

            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE", point);
            editor.commit();
        } else{
            highScoreLabel.setText("High Score : " + hightScore);
        }

        */

    }

    public void tryAgain(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


    public static int getPoint() {
        return point;
    }

    public static void setPoint(int point) {
        result.point = point;
    }
}
