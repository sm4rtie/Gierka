package com.example.maj.gierka;

import android.content.Intent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Maj on 2017-05-21.
 */

public class GamePicker {
    result res = new result();
    int point = res.getPoint();
    private static ArrayList<Class> activities = new ArrayList<Class>(Arrays.asList(MyszActivity.class, MinkaActivity.class, KolkaActivity.class, AlfabetActivity.class, KoloryActivity.class, Kolory2Activity.class, LodyActivity.class, PorownanieActivity.class, RownaniaActivity.class, OrtoActivity.class));
    private static Class randGame;
    public GamePicker(){

        if(getActivities().size() == 1){
            setRandGame(result.class);
            activities = new ArrayList<Class>(Arrays.asList(MyszActivity.class, MinkaActivity.class, KolkaActivity.class, AlfabetActivity.class, KoloryActivity.class, Kolory2Activity.class, LodyActivity.class, PorownanieActivity.class, RownaniaActivity.class, OrtoActivity.class));
           // Intent intent = new Intent(getApplicationContext(), result.class);
           // intent.putExtra("SCORE", point);
           // startActivity(intent);
        }
        else {
            Class that = getActivities().get(new Random().nextInt(getActivities().size()));
            getActivities().remove(that); // after using, remove from list
            setRandGame(that);
        }

    }


    public ArrayList<Class> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<Class> activities) {
        GamePicker.activities = activities;
    }

    public Class getRandGame() {
        return randGame;
    }

    public void setRandGame(Class randGame) {
        GamePicker.randGame = randGame;
    }
}
