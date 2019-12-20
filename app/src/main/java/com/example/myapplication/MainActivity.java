package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = MainActivity.class.getSimpleName();
    private TwelveLuckPanLayoutV2 twelveLuckPanLayout;
    ImageView luckStart;
    FrameLayout flWorkKuangLayout;
    ImageView ivKuang;
    private boolean isSetting = false;
    private int repecount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        twelveLuckPanLayout = this.findViewById(R.id.luck_drawa_twelevluck);
        luckStart = this.findViewById(R.id.luck_drawa_startchou);
        flWorkKuangLayout = this.findViewById(R.id.fl_luck_drawa_work_kuang);
        ivKuang = this.findViewById(R.id.iv_luck_drawa_work_kuang);

        twelveLuckPanLayout.updateView();
        luckStart.setOnClickListener(this);

        twelveLuckPanLayout.post(new Runnable() {
            @Override
            public void run() {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) flWorkKuangLayout.getLayoutParams();
                layoutParams.width = twelveLuckPanLayout.getmRectParentWidth() / 4 + twelveLuckPanLayout.getItemSpace() * 2 / 3;
                layoutParams.height = twelveLuckPanLayout.getmRectParentWidth() / 4 + twelveLuckPanLayout.getItemSpace();
                flWorkKuangLayout.setLayoutParams(layoutParams);
            }
        });

        flWorkKuangLayout.post(new Runnable() {
            @Override
            public void run() {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) ivKuang.getLayoutParams();
                layoutParams.width = flWorkKuangLayout.getWidth() - DensityUtil.getInstance().dip2px(3);
                layoutParams.height = flWorkKuangLayout.getHeight() - DensityUtil.getInstance().dip2px(3);
                ivKuang.setLayoutParams(layoutParams);
            }
        });
    }

    /**
    * @date: 创建时间:2019/12/20
    * @author: gaoxiaoxiong
    * @descripion:需要中奖的View
    **/
    public void onPosition(final int position) {
       final int totalWidth = twelveLuckPanLayout.getmRectParentWidth() - twelveLuckPanLayout.getmRectParentWidth() / 4;//一条直线上的
       final int oneItem = twelveLuckPanLayout.getmRectParentWidth() / 4;//一个的Item
       final   ValueAnimator valueAnimator = ValueAnimator.ofInt(0, oneItem * 12);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (int) animation.getAnimatedValue();
                float percent = (float) currentValue / ((float) totalWidth * 4);
                if (currentValue >= 0 && currentValue <= totalWidth) {//第一排
                    flWorkKuangLayout.setTranslationX(percent * totalWidth * 4);
                    flWorkKuangLayout.setTranslationY(0);
                } else if (currentValue > totalWidth && currentValue <= totalWidth * 2) {//第二
                    flWorkKuangLayout.setTranslationX(twelveLuckPanLayout.getmRectParentWidth() - flWorkKuangLayout.getWidth());
                    float release = percent * totalWidth * 4 - totalWidth;
                    flWorkKuangLayout.setTranslationY(release);
                } else if (currentValue > totalWidth * 2 && currentValue <= totalWidth * 3) {//第三
                    flWorkKuangLayout.setTranslationY(totalWidth);
                    float release = percent * totalWidth * 4 - totalWidth * 3;
                    flWorkKuangLayout.setTranslationX(-release);
                } else if (currentValue > totalWidth * 3 && currentValue <= totalWidth * 4) {//第四
                    float release = percent * totalWidth * 4 - totalWidth * 4;
                    flWorkKuangLayout.setTranslationY(-release);
                    flWorkKuangLayout.setTranslationX(0);
                }
                if (currentValue == totalWidth * 4) {
                    flWorkKuangLayout.setTranslationY(0);
                    flWorkKuangLayout.setTranslationX(0);
                }

                if (repecount == 2 && !isSetting) {
                    int trans = oneItem * position;
                    valueAnimator.setIntValues(trans);
                    isSetting = true;
                }
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                int trans = oneItem * position;
                if (trans >= 0 && trans <= totalWidth) {//第一排
                    flWorkKuangLayout.setTranslationX(twelveLuckPanLayout.getChildAt(position).getLeft() - twelveLuckPanLayout.getItemSpace());
                } else if (trans > totalWidth && trans <= totalWidth * 2) {//第二
                    flWorkKuangLayout.setTranslationY(twelveLuckPanLayout.getChildAt(position).getTop() - twelveLuckPanLayout.getItemSpace());
                } else if (trans > totalWidth * 2 && trans <= totalWidth * 3) {//第三
                    flWorkKuangLayout.setTranslationY(twelveLuckPanLayout.getChildAt(position).getTop() - twelveLuckPanLayout.getItemSpace());
                    flWorkKuangLayout.setTranslationX(twelveLuckPanLayout.getChildAt(position).getLeft() - twelveLuckPanLayout.getItemSpace());
                } else if (trans > totalWidth * 3 && trans <= totalWidth * 4) {//第四
                    flWorkKuangLayout.setTranslationY(twelveLuckPanLayout.getChildAt(position).getTop() - twelveLuckPanLayout.getItemSpace());
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                repecount = repecount + 1;
                flWorkKuangLayout.setTranslationY(0);
                flWorkKuangLayout.setTranslationX(0);
            }
        });
        valueAnimator.setRepeatCount(2);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(1000);
        valueAnimator.start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.luck_drawa_startchou:{
                isSetting = false;
                repecount = 0;
                onPosition(5);
            }
            break;
        }
    }
}
