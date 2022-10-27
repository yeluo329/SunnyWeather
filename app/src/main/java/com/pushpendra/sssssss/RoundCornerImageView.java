package com.pushpendra.sssssss;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;


import androidx.annotation.Nullable;

public class RoundCornerImageView extends View {
    private Paint mPaintRoundRect;
    private int mOuterColor = Color.BLACK;

    public RoundCornerImageView(Context context) {
        this(context, null);
    }

    public RoundCornerImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundCornerImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerImageView);

        array.recycle();
        mPaintRoundRect = new Paint();//圆角矩形
        mPaintRoundRect.setColor(mOuterColor);//圆角矩形颜色
        mPaintRoundRect.setAntiAlias(true);// 抗锯齿效果
        mPaintRoundRect.setStyle(Paint.Style.STROKE);//设置画笔样式
        mPaintRoundRect.setStrokeWidth(1.5f);//设置画笔宽度
    }

    @Override
    protected void onDraw(Canvas canvas) {
        RectF rect = new RectF(0, 0, getWidth(), getHeight());

        canvas.drawRoundRect(rect, 10, 10, mPaintRoundRect);

        super.onDraw(canvas);
    }
}
