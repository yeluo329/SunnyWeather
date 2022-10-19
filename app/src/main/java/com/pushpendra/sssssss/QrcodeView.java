package com.pushpendra.sssssss;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class QrcodeView extends RelativeLayout {
    public QrcodeView(Context context) {
        super(context);
        init();
    }

    public QrcodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        inflate(getContext(), R.layout.qrcode_view, this);
    }

    public void refreshQRCode(int width, final LoadCallback callback) {
        if (callback != null) {
            callback.onFinish(getBitmap(width));
        }
    }


    private Bitmap getBitmap(int width) {
        setDrawingCacheEnabled(true);

        measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        layout(0, 0, getMeasuredWidth(), getMeasuredHeight());

        buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(getDrawingCache());
        setDrawingCacheEnabled(false); // clear drawing cache
        return b;
    }
}
