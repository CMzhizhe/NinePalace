package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;


import java.util.ArrayList;
import java.util.List;

public class TwelveLuckPanLayoutV2 extends ViewGroup {
    private Paint mPaint;
    private List<RectF> childRectfList;//存储矩形的集合
    private int mStrokWidth = 1;//矩形的描边宽度
    private int itemWidthHeight = 0;//每个Item的宽高
    private int itemSpace = 5;
    private int mRectParentWidth;//矩形的宽和高（矩形为正方形）
    private int[] mItemColor = {Color.GREEN, Color.YELLOW};//矩形的颜色
    private Context mContext;
    private List<View> viewList = null;
    private int[] mImgs = {R.drawable.icon_test3, R.drawable.icon_test1, R.drawable.icon_test2};
    public TwelveLuckPanLayoutV2(Context context) {
        this(context, null);
    }

    public TwelveLuckPanLayoutV2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TwelveLuckPanLayoutV2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    /**
     * 初始化数据
     */
    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mStrokWidth);
        childRectfList = new ArrayList<>();
        viewList = new ArrayList<>();
        itemWidthHeight = DensityUtil.getInstance().dip2px(65);
        itemSpace = DensityUtil.getInstance().dip2px(8);
    }


    /**
     * @date :2019/12/17 0017
     * @author : gaoxiaoxiong
     * @description:创建12个Viwe视图
     **/
    private View createView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_child_tweleveluck, this, false);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        view.setLayoutParams(layoutParams);
        view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        ImageView imageView = view.findViewById(R.id.iv_view_child_tweleveluck);
        imageView.setImageResource(mImgs[position % 3]);
        return view;
    }

    /**
     * @date :2019/12/20 0020
     * @author : gaoxiaoxiong
     * @description:创建12个Viwe视图
     **/
    public void updateView() {
        removeAllViews();
        for (int i = 0; i < 12; i++) {
            viewList.add(createView(i));
            addView(viewList.get(i));
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mRectParentWidth = getMeasuredWidth();
        itemWidthHeight = (mRectParentWidth - itemSpace * 5) / 4;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewList.get(i);
            LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = itemWidthHeight;
            layoutParams.height = itemWidthHeight;
            view.setLayoutParams(layoutParams);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //前3个
        int left = 0, top = 0, right = 0, bottom = 0;
        top = itemSpace;
        left = itemSpace;
        for (int i = 0; i < 3; i++) {
            View view = viewList.get(i);
            view.layout(left, top, left + itemWidthHeight, top + itemWidthHeight);
            left = left + itemWidthHeight + itemSpace;
        }

        //右边4个
        left = 3 * (itemWidthHeight + itemSpace) + itemSpace;
        top = itemSpace;
        bottom = itemSpace + itemWidthHeight;
        for (int i = 3; i < 7; i++) {
            right = left + itemWidthHeight;
            View view = viewList.get(i);
            view.layout(left, top, right, bottom);
            top = top + itemWidthHeight + itemSpace;
            bottom = bottom + itemSpace + itemWidthHeight;
        }

        //底部3个
        top = (itemSpace + itemWidthHeight) * 3 + itemSpace;
        bottom = top + itemWidthHeight;
        left = (itemSpace + itemWidthHeight) * 2 + itemSpace;
        right = left + itemWidthHeight;
        for (int i = 7; i < 10; i++) {
            View view = viewList.get(i);
            view.layout(left, top , right, bottom);
            left = left - (itemSpace + itemWidthHeight);
            right = left + itemWidthHeight;
        }
        //左边2个
        left = itemSpace;
        right = itemSpace + itemWidthHeight;
        top = 2 * (itemWidthHeight + itemSpace) + itemSpace;
        bottom = top + itemWidthHeight;
        for (int i = 10; i < 12; i++) {
            View view = viewList.get(i);
            view.layout(left , top , right, bottom);
            top = top - (itemWidthHeight + itemSpace);
            bottom = top + itemWidthHeight;
        }
    }

    public int getmRectParentWidth() {
        return mRectParentWidth;
    }


    public int getItemSpace() {
        return itemSpace;
    }
}
