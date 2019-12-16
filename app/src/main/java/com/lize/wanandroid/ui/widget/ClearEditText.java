package com.lize.wanandroid.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatEditText;

import com.lize.wanandroid.R;

public class ClearEditText extends AppCompatEditText {

    private static final int DRAWABLE_LEFT = 0;
    private static final int DRAWABLE_TOP = 1;
    private static final int DRAWABLE_RIGHT = 2;
    private static final int DRAWABLE_BOTTOM = 3;

    private Drawable mClearDrawable;
    private Drawable mRightDrawable;
    private boolean isShowClearDrawable = true;
    private boolean isShowRightDrawable;

    private ClearEditText.ClearEditTextListener editTextListener;

    public interface ClearEditTextListener {
        void onRigthBtnClick();
    }

    public void setEditTextListener(ClearEditText.ClearEditTextListener editTextListener) {
        this.editTextListener = editTextListener;
    }

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mClearDrawable = getResources().getDrawable(R.drawable.ic_edit_text_clear);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (hasFocus() && length() > 0) {
            isShowClearDrawable = true;
            setRightCompoundDrawable(mClearDrawable);
        } else {
            isShowClearDrawable = false;
            if (mRightDrawable != null) {
                isShowRightDrawable = true;
                setRightCompoundDrawable(mRightDrawable);
            } else {
                isShowRightDrawable = false;
                setRightCompoundDrawable(null);
            }
        }
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (hasFocus() && length() > 0) {
            isShowClearDrawable = true;
            setRightCompoundDrawable(mClearDrawable);
        } else {
            isShowClearDrawable = false;
            if (mRightDrawable != null) {
                isShowRightDrawable = true;
                setRightCompoundDrawable(mRightDrawable);
            } else {
                isShowRightDrawable = false;
                setRightCompoundDrawable(null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Drawable drawable = getCompoundDrawables()[DRAWABLE_RIGHT];
                if (drawable != null && event.getX() <= (getWidth() - getPaddingRight())
                        && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {
                    if (isShowClearDrawable) {
                        setText("");
                    } else if (isShowRightDrawable) {
                        if (editTextListener != null) {
                            editTextListener.onRigthBtnClick();
                        }
                    }
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setShowRightDrawable(boolean showRightDrawable) {
        isShowRightDrawable = showRightDrawable;
    }

    public void setRightDrawable(Drawable rightDrawable) {
        this.mRightDrawable = rightDrawable;
        setRightCompoundDrawable(rightDrawable);
    }

    private void setRightCompoundDrawable(Drawable drawable) {
        setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[DRAWABLE_LEFT], getCompoundDrawables()[DRAWABLE_TOP], drawable, getCompoundDrawables()[DRAWABLE_BOTTOM]);
    }
}
