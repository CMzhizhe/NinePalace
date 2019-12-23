package com.example.myapplication;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.util.Log;

public class MyInterceper implements TimeInterpolator {
    private String TAG = MyInterceper.class.getSimpleName();
    private Animator animator;

    public MyInterceper(Animator animator) {
        this.animator = animator;
    }

    @Override
    public float getInterpolation(float input) {
        float resultValue = input;
        if (input>=0.5f){
        resultValue = (float) (Math.cos((input + 2) * Math.PI) / 2.0f) + 0.5f;
        resultValue = 1- resultValue;
        }
        Log.i(TAG, "resultValue:" + resultValue);
        return resultValue;
  /*      float resultValue = input;
       if (input >=0.5f){
           Log.i(TAG, "input:" + input);
           resultValue = 0.5f * (o(input * 2.0f - 2.0f,  2.0f * 1.5f) + 2.0f);
           Log.i(TAG, "resultValue:" + resultValue);
       }
       return resultValue;*/
    }

    private static float o(float t, float s) {
        return t * t * ((s + 1) * t + s);
    }
}
