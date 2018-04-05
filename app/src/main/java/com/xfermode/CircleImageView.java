package com.xfermode;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/4/5.
 */

public class CircleImageView extends View {

    private int resId;
    private Bitmap bitmap;
    private Paint paint;
    private int bitmapWidth,bitmapHeight;
    private int size;
    private PorterDuffXfermode xfermode;

    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.CircleImageView);
        resId = ta.getResourceId(R.styleable.CircleImageView_src, R.mipmap.ic_launcher_round);
        ta.recycle();
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        bitmap = BitmapFactory.decodeResource(getResources(), resId,options);
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
        size = Math.min(bitmapWidth, bitmapHeight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int index = canvas.saveLayer(0, 0, size, size, paint, Canvas.ALL_SAVE_FLAG);
        Bitmap dst = makeCircle();
        canvas.drawBitmap(dst, 0, 0, paint);
        paint.setXfermode(xfermode);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(index);
    }

    private Bitmap makeCircle() {
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        int radius = size/2;
        canvas.drawCircle(radius, radius, radius, paint);
        return bitmap;
    }
}
