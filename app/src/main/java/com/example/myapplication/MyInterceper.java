package com.example.myapplication;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.util.Log;

public class MyInterceper implements TimeInterpolator {
    private String TAG = MyInterceper.class.getSimpleName();

    @Override
    public float getInterpolation(float input) {
        float resultValue = input;
        if (input>=0.5f){
            input = input + 1;
            resultValue = (float) (Math.cos(input * Math.PI) / 2.0f) + 0.5f;
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
