package com.xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/4/5.
 */

public class MyLoadingView extends View {

    private Paint paint;
    private Bitmap bitmap;
    private RectF rectF;
    private Xfermode xfermode;
    private int top;

    public MyLoadingView(Context context) {
        super(context);
    }

    public MyLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setDither(true);
        paint.setColor(Color.BLUE);
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round);
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        rectF = new RectF(0, top=bitmap.getHeight(), bitmap.getWidth(), bitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int layerIndex = canvas.saveLayer(0, 0,
                bitmap.getWidth(), bitmap.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(bitmap, 0 , 0, null);
        paint.setXfermode(xfermode);
        canvas.drawRect(rectF, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(layerIndex);
        if(rectF.top>0){
            rectF.top--;
            invalidate();
        }else {
            rectF.top = bitmap.getHeight();
            invalidate();
        }
    }
}
