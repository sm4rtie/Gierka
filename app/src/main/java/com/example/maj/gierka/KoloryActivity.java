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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Maj on 2017-04-17.
 */

public class KoloryActivity extends ContextWrapper {
    private int resID;
    private ArrayList<String> answers;
    private TypedArray imgs;
    Context context;

    public KoloryActivity(Context context){
        super(context);
        this.context = context;
        imgs = context.getResources().obtainTypedArray(R.array.kolory);


    }
    public void genAnswers(){
        //wydzielamy odpowiedz
        String pattern = "\\/(\\w*)";
        ArrayList<String> arr = new ArrayList<>();
        String modAnsw;
        imgs = context.getResources().obtainTypedArray(R.array.kolory);
        Random rand = new Random();
        //losujemy obrazek
        int rndInt = rand.nextInt(imgs.length()-1);
        setResID(imgs.getResourceId(rndInt, 0));
        //bierzemy nazwe obrazka
        String name = context.getResources().getResourceName(getResID());
        //przerabiamy na odpowiedz na buttona
        modAnsw = modifyAnsw(pattern, name);
        arr.add(modAnsw);
        int answ2 = rand.nextInt(imgs.length()-1);
        if(answ2==rndInt) {
            while(answ2==rndInt) rand.nextInt(imgs.length()-1);
        }
        //arr.add(context.getResources().getResourceName(imgs.getResourceId(answ2, 0)));
        modAnsw = modifyAnsw(pattern, context.getResources().getResourceName(imgs.getResourceId(answ2, 0)));
        arr.add(modAnsw);
        int answ3 = rand.nextInt(imgs.length()-1);
        if(answ3==rndInt || answ3 == answ2) {
            while (answ3 == rndInt || answ3 == answ2) {
                answ3 = rand.nextInt(imgs.length());
            }

        }
        //arr.add(context.getResources().getResourceName(imgs.getResourceId(answ3, 0)));
        modAnsw = modifyAnsw(pattern, context.getResources().getResourceName(imgs.getResourceId(answ3, 0)));
        arr.add(modAnsw);
        setAnswers(arr);
        //return getAnswers();
        System.out.println(getResID());

    }
    public String modifyAnsw(String pattern, String line){
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(line);
        String answ = null;
        if (m.find( )) {
            System.out.println("Found value: " + m.group(1) );
            answ = (m.group(1)).replace("_", " ");
            //answ = m.group(1);
        }else {
            System.out.println("NO MATCH");
        }
        return answ;
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
