package com.pushpendra.sssssss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
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
                getBitmap();
            }
        });
        returnBtn.setOnClickListener(v -> {
            thumbBmp(getBitmap(), 300, 300);
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
        //当前bitmap的高度
        int top = dip2px(105);
        int left = dip2px(12);
        Paint paint = new Paint();
        for (int i = 0; i < size; i++) {
            ChorusGameResultAdapter.ResultViewHolder holder = gameResultAdapter.createViewHolder(chorusRv, gameResultAdapter.getItemViewType(i));
            holder.itemView.setDrawingCacheEnabled(true);
            holder.itemView.buildDrawingCache();
            List mList = new ArrayList();
            gameResultAdapter.onBindViewHolder(holder, i, mList);
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
        QrcodeView qrcodeView = new QrcodeView(this);
        qrcodeView.refreshQRCode(rootLay.getWidth(), new LoadCallback() {
            @Override
            public void onFinish(Bitmap bitmap) {
                canvas.drawBitmap(bitmap, 0, dip2px(105) + dip2px(88) * 3 + dip2px(32), null);
            }
        });

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