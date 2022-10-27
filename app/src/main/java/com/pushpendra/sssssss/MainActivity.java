package com.pushpendra.sssssss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView chorusRv;
    private Button button;
    private ChorusGameResultAdapter gameResultAdapter;
    private ConstraintLayout rootLay;
    private ImageView resultIv;
    private ImageView shareIv;
    private ValueAnimator valueAnimator;
    private Animation animation;
    private View view;
    private TextView progressTv;
    private TextView chooseTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chorusRv = findViewById(R.id.result_rv);
        rootLay = findViewById(R.id.root_view);
        resultIv = findViewById(R.id.result_bg);
        shareIv = findViewById(R.id.share_bg);
        chorusRv.setLayoutManager(new LinearLayoutManager(this));
        gameResultAdapter = new ChorusGameResultAdapter();
        chorusRv.setAdapter(gameResultAdapter);
        Button returnBtn = findViewById(R.id.return_btn);


        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        gameResultAdapter.refresh(list);

        button = findViewById(R.id.share_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = getBitmap();
            }
        });
        returnBtn.setOnClickListener(v -> {
            gameResultAdapter.refreshPartItem(1);
        });
        ConstraintLayout constraintLayout = findViewById(R.id.view);
        constraintLayout.post(() -> {
            initAnimator(constraintLayout.getWidth());
        });

        view = findViewById(R.id.choose_view);
        progressTv = findViewById(R.id.progress_tv);
        chooseTv = findViewById(R.id.choose_tv);
        constraintLayout.setOnClickListener(v -> {
            constraintLayout.setBackgroundResource(R.drawable.layer_e0ffee);
            view.setVisibility(View.VISIBLE);
            progressTv.setVisibility(View.VISIBLE);
            valueAnimator.start();
            startAnimationTv();
        });
    }

    private void startAnimationTv() {
        int[] location = new int[2];
        chooseTv.getLocationOnScreen(location);
        int x = location[0];
        animation = new TranslateAnimation(Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, -(x - dip2px(28)), Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, 0.0f);
        animation.setDuration(1000);
        animation.setRepeatCount(0);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setFillAfter(true);
        chooseTv.startAnimation(animation);
    }

    private void initAnimator(int width) {
        valueAnimator = ObjectAnimator.ofFloat(0, width * 0.6f);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(animation -> {
            float currentValue = (float) animation.getAnimatedValue();
            String progress = (int) (currentValue / width * 100) + "%";
            progressTv.setText(progress);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
            layoutParams.width = (int) (currentValue);
            view.setLayoutParams(layoutParams);
        });
    }


    private Bitmap getBitmap() {
        Bitmap bigBitmap = null;
        gameResultAdapter.setMode(ChorusGameResultAdapter.TYPE_SHARE);
        int size = gameResultAdapter.getItemCount();
        bigBitmap = Bitmap.createBitmap(rootLay.getMeasuredWidth(), dip2px(453), Bitmap.Config.ARGB_8888);
        Bitmap topBitmap = Bitmap.createBitmap(rootLay.getMeasuredWidth(), dip2px(105), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bigBitmap);
        Canvas topCanvas = new Canvas(topBitmap);
        shareIv.draw(canvas);
        rootLay.draw(topCanvas);
        int top = dip2px(105);
        int left = dip2px(12);
        Paint paint = new Paint();
        for (int i = 0; i < size; i++) {
            ChorusGameResultAdapter.ResultViewHolder holder = gameResultAdapter.createViewHolder(chorusRv, gameResultAdapter.getItemViewType(i));
            holder.itemView.setDrawingCacheEnabled(true);
            holder.itemView.buildDrawingCache();
            gameResultAdapter.onBindViewHolder(holder, i);
            holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(chorusRv.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
            Bitmap drawingCache = holder.itemView.getDrawingCache();
            if (drawingCache != null) {
                canvas.drawBitmap(drawingCache, left, top, paint);
                top += dip2px(88);
                drawingCache.recycle();
            }
        }
        gameResultAdapter.setMode(ChorusGameResultAdapter.TYPE_NORMAL);
        canvas.drawBitmap(topBitmap, 0, 0, null);
//        Bitmap qrcodeBitmap = qrCodeView.getBitmap(rootLay.getMeasuredWidth());
//        qrcodeView.refreshQRCode(rootLay.getWidth(), new LoadCallback() {
//            @Override
//            public void onFinish(Bitmap bitmap) {
//                canvas.drawBitmap(bitmap, 0, dip2px(105) + dip2px(88) * 3 + dip2px(32), null);
//            }
//        });

        return bigBitmap;


    }


    public Bitmap thumbBmp(Bitmap bitmap, int width, int height) {
        int srcWidth = bitmap.getWidth();
        int srcHeight = bitmap.getHeight();
        double sx = (double) width / srcWidth;
        double sy = (double) height / srcHeight;
        int newWidth = width;
        int newHeight = height;
        if (sx > sy) {
            sx = sy;
            newWidth = (int) (sx * srcWidth);
        } else {
            sy = sx;
            newHeight = (int) (sy * srcHeight);
        }

        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        return thumbBmp;
    }

    public int dip2px(float dpValue) {
        float density = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }


}