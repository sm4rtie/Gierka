package com.example.maj.gierka;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Maj on 2017-05-17.
 */

public class Orto extends ContextWrapper {
    Context context;
    private TypedArray imgs;
    private int resID;
    private String answer;

    public Orto(Context context) {
        super(context);
        this.context = context;
        setImgs(context.getResources().obtainTypedArray(R.array.orto));
    }

    public String generateQuestion(){
        setImgs(context.getResources().obtainTypedArray(R.array.orto));
        Random rand = new Random();
        //losujemy obrazek
        int rndInt = rand.nextInt(getImgs().length()-1);
        setResID(getImgs().getResourceId(rndInt, 0));
        String name = context.getResources().getResourceName(getResID());
        setAnswer(modifyAnsw(name));
        return getAnswer();
    }
    public String modifyAnsw(String line){
        String pattern = "\\_(\\w*)";
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

    public TypedArray getImgs() {
        return imgs;
    }

    public void setImgs(TypedArray imgs) {
        this.imgs = imgs;
    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
