package com.example.maj.gierka;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Maj on 2017-04-17.
 */

public class CharactersQuiz extends ContextWrapper {
    private int resID;
    private ArrayList<String> answers;
    private TypedArray imgs;
Context context;

    public CharactersQuiz(Context context){
    super(context);
        this.context = context;
        imgs = context.getResources().obtainTypedArray(R.array.apptour);


    }
    public void genAnswers(){
        ArrayList<String> arr = new ArrayList<>();
        imgs = context.getResources().obtainTypedArray(R.array.apptour);
        Random rand = new Random();
        int rndInt = rand.nextInt(imgs.length()-1);
        setResID(imgs.getResourceId(rndInt, 0));
        String name = context.getResources().getResourceName(getResID());
        arr.add(name);
        int answ2 = rand.nextInt(imgs.length()-1);
        if(answ2==rndInt) {
            while(answ2==rndInt) rand.nextInt(imgs.length()-1);
        }
        arr.add(context.getResources().getResourceName(imgs.getResourceId(answ2, 0)));
        int answ3 = rand.nextInt(imgs.length()-1);
        if(answ3==rndInt) {
            if (answ3 == answ2) {
                while (answ3 == rndInt) {
                    while (answ3 == answ2)

                        answ3 = rand.nextInt(imgs.length());
                }
            }
        }
        arr.add(context.getResources().getResourceName(imgs.getResourceId(answ3, 0)));
        setAnswers(arr);
        //return getAnswers();
        System.out.println(getResID());

    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

}
