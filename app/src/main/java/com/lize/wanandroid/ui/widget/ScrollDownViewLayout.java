package com.lize.wanandroid.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild3;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;

import java.time.Month;

public class ScrollDownViewLayout extends RelativeLayout implements NestedScrollingParent3 {
    // 触发一定时间最低速度，px/s
    private int mMaxVelocity;
    private int mLastX;
    private int mLastY;
    private Listener listener;
    // 用于追踪手指滑动速度
    private VelocityTracker mVelocityTracker;
    private int mPointerId;
    private NestedScrollingParentHelper nestedScrollingParentHelper;

    public ScrollDownViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollDownViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        nestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        mMaxVelocity = ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity();
    }


    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        initVelocityTracker(event);
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPointerId = event.getPointerId(0);
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = y - mLastY;
                // Log.e("ScrollDownViewLayout", "ScrollX = " + getScrollX() + "   ScrollY = " + getScrollY() + "  dy=" + dy);
                if (getScrollY() >= 0 && dy < 0) {
                    scrollTo(0, 0);
                    return true;
                }

                scrollBy(0, -dy);
                if (getScrollY() >= 0) {
                    scrollTo(0, 0);
                }
                Log.e("ScrollDownViewLayout", "ScrollX = " + getScrollX() + "   ScrollY = " + getScrollY() + "  dy=" + dy);
                //  touchScroll(dy);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                mVelocityTracker.computeCurrentVelocity(1000, mMaxVelocity);
                final float velocityY = mVelocityTracker.getYVelocity(mPointerId);

                releaseVelocityTracker();
                Log.e("ScrollDownViewLayout", "velocityY: " + velocityY);
                if (getScrollY() < 0) {
                    if (Math.abs(getScrollY()) > getHeight() / 3 || velocityY > 1000) {
                        scrollTo(0, -getHeight());
                        return true;
                    } else {
                        scrollTo(0, 0);
                        return true;
                    }

                }

                break;
        }

        mLastY = y;
        return true;

    }


    /**
     * * 释放VelocityTracker
     *
     * @see VelocityTracker#clear()
     * @see VelocityTracker#recycle()
     */
    private void releaseVelocityTracker() {
        if (null != mVelocityTracker) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    /**
     * @param event 向VelocityTracker添加MotionEvent
     * @see VelocityTracker#obtain()
     * @see VelocityTracker#addMovement(MotionEvent)
     */
    private void initVelocityTracker(final MotionEvent event) {
        if (null == mVelocityTracker) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }


    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        Log.e("onNestedScroll", "onNestedScroll: ");

    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        Log.e("onStartNestedScroll", "onStartNestedScroll: axes=" + axes);
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {

        Log.e("onNestedScrollAccepted", "onNestedScrollAccepted: ");
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        Log.e("onStopNestedScroll", "onStopNestedScroll: ");
        if (getScrollY() < 0) {
            if (Math.abs(getScrollY()) > getHeight() / 3) {
                scrollTo(0, -getHeight());
            } else {
                scrollTo(0, 0);
            }

        }
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        Log.e("onNestedScroll", "onNestedScroll: ");
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.e("onNestedFling", "onNestedFling: ");
        if (getScrollY() >= 0)
            return false;
        if (velocityY<-1000){
            scrollTo(0,-getHeight());
        }
        Log.e("onNestedFling", "处理惯性滑动: velocityY="+velocityY);
        return true;
    }
    /*
    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.e("onNestedFling", "onNestedFling: ");
        if (getScrollY() >= 0)
            return false;
        Log.e("onNestedFling", "处理惯性滑动: velocityY="+velocityY);
        return true;
    }*/

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        boolean scrollDown = dy < 0 && !target.canScrollVertically(-1);
        boolean scrollUp = dy > 0 && getScrollY() < 0;
        if (scrollDown) {
            if (getScrollY() > 0) {
                scrollTo(0, 0);
            } else {
                scrollBy(0, dy);
                if (getScrollY() < -getHeight()) {
                    scrollTo(0, -getHeight());
                }
                Log.e("2、onNestedPreScroll", "onNestedPreScroll: dy=" + dy + " scrollDown=" + scrollDown + " getScrollY()=" + getScrollY());
                consumed[1] = dy;
            }
            return;
        }
        if (scrollUp) {
            scrollBy(0, dy);
            if (getScrollY() >= 0) {
                scrollTo(0, 0);
            }
            Log.e("3、onNestedPreScroll", "onNestedPreScroll: dy=" + dy + " scrollUp=" + scrollUp + " getScrollY()=" + getScrollY());
            consumed[1] = dy;
        }
    }

    public interface Listener {
        void onDismissListener();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (t == -getHeight()) {
            if (listener != null) {
                listener.onDismissListener();
            }
        }
    }
}
