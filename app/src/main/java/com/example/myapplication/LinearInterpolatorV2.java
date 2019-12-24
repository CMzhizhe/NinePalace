package com.example.myapplication;

import android.animation.TimeInterpolator;

public class LinearInterpolatorV2 implements TimeInterpolator {
    @Override
    public float getInterpolation(float input) {
        return input * 2f;
    }
}
