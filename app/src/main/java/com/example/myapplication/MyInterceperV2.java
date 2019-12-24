package com.example.myapplication;

import android.animation.TimeInterpolator;
import android.util.Log;

public class MyInterceperV2 implements TimeInterpolator {
    private String TAG = MyInterceperV2.class.getSimpleName();

    @Override
    public float getInterpolation(float input) {
        return input ;
    }

    private static float o(float t, float s) {
        return t * t * ((s + 1) * t + s);
    }
}
