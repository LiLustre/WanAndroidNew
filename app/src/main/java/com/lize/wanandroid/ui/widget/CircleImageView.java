package com.lize.wanandroid.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;


/**
 * Created by Sky on 2017-12-5.
 */

public class CircleImageView extends AppCompatImageView {
    private Paint mPaint;
    private Xfermode xfermode;
    private int width,height;
    private int mRadius;
    private Bitmap mCircleBitmap;
    public CircleImageView(Context context) {
        super(context);
        init();
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        if (width>height){
            mRadius = height/2;
        }else {
            mRadius = width/2;
        }
        setMeasuredDimension(mRadius * 2, mRadius * 2);
    }
    //生成一个实心圆形Bitmap,这个Bitmap宽高要与当前的View的宽高相同
    private Bitmap getCircleBitmap(){
        if (mCircleBitmap==null){
            mCircleBitmap = Bitmap.createBitmap(2 * mRadius, 2 * mRadius,
                                                Bitmap.Config.ARGB_8888);
           /* Canvas canvas = new Canvas(mCircleBitmap);
            mPaint.reset();
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);*/
           Canvas canvas = new Canvas(mCircleBitmap);
            mPaint.reset();
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(mRadius,mRadius,mRadius,mPaint);
        }
            return mCircleBitmap;
    }
    //将两张图片以XferMode（DST_IN）的方式组合到一张照片中
    private Bitmap combineBitmap(Drawable drawable, Bitmap maskBitmap){
        int width = drawable.getIntrinsicWidth();//获取图片完事宽
        int height = drawable.getIntrinsicWidth();//获取图片完事宽

        Bitmap bitmap  = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0,0,mRadius*2,mRadius*2);
        drawable.draw(canvas);

        mPaint.reset();
        mPaint.setXfermode(xfermode);
        canvas.drawBitmap(maskBitmap,0,0,mPaint);
        mPaint.setXfermode(null);
        return bitmap;
    }
    @Override
    protected void onDraw(Canvas canvas) {
       // super.onDraw(canvas);
/*
        setLayerType(LAYER_TYPE_SOFTWARE, null); //关闭硬件加速
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.one);
        Bitmap mOut = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas1 = new Canvas(mOut);
        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        RectF rt = new RectF();
        rt.left = 0; //矩形的左起点
        rt.top = 0; //矩形的顶部起点
        rt.right = mBitmap.getWidth(); //矩形的右终点
        rt.bottom = mBitmap.getHeight(); //矩形的底部终点

        //Dat
        canvas.drawRoundRect(rt, 50, 50, mPaint);
        //设置Xfermode
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //Src
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);

        //还原Xfermode
        mPaint.setXfermode(null);*/
        //获取设置的src图片
        Drawable drawable = getDrawable();
        //获取盖在src上面的实心圆形Bitmap
        Bitmap circleBitmap = getCircleBitmap();

        //两张图片以XferMode（DST_IN）的方式组合
        Bitmap bitmap = combineBitmap(drawable, circleBitmap);

        //将最终的bitmap画到画板上面
        canvas.drawBitmap(bitmap, 0, 0, mPaint);

    }
}
