package com.lize.wanandroid.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import com.lize.wanandroid.util.DisplayUtil;

public class RoundImageView extends AppCompatImageView {

    float width, height;
    int radius;
    Path path = new Path();

    public RoundImageView(Context context) {
        this(context, null);
        init(context);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        radius = DisplayUtil.dip2px(context, 5);
    }

    private void init(Context context){
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (width > 0 && height > 0) {
            path.reset();
            path.moveTo(radius, 0);
            path.lineTo(width - radius, 0);
            path.quadTo(width, 0, width, radius);
            path.lineTo(width, height - radius);
            path.quadTo(width, height, width - radius, height);
            path.lineTo(radius, height);
            path.quadTo(0, height, 0, height - radius);
            path.lineTo(0, radius);
            path.quadTo(0, 0, radius, 0);
            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }
}