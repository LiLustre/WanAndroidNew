package com.lize.wanandroid.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.lize.wanandroid.R;

import java.util.List;

/**
 * @author Lize
 * on 2019/7/9
 * 头部添加标签TextView
 */
public class FlagTextView extends AppCompatTextView {

    private StringBuffer content_buffer;
    private int mLayoutResource;
    private int flagID;
    private TextView tv_tag;

    private View view;//标签布局的最外层布局

    private Context mContext;

//必须重写所有的构造器，否则可能会出现无法inflate布局的错误！

    public FlagTextView(Context context) {

        this(context, null);
        mContext = context;
    }

    public FlagTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mContext = context;

    }


    public FlagTextView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FlagTextView);
        mLayoutResource = typedArray.getResourceId(R.styleable.FlagTextView_flagLayout, R.layout.flag);
        flagID = typedArray.getResourceId(R.styleable.FlagTextView_flagTVId, R.id.tv_tag);
        typedArray.recycle();
        mContext = context;
    }

    private static Bitmap convertViewToBitmap(View view) {

        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.buildDrawingCache();

        Bitmap bitmap = view.getDrawingCache();


        return bitmap;

    }

    public void setContentAndTag(String content, List<String> tags) {

        content_buffer = new StringBuffer();
        for (String item : tags) {//将每个tag的内容添加到content后边，之后将用drawable替代这些tag所占的位置
            content_buffer.append(item);
        }
        content_buffer.append(content);
        SpannableString spannableString = new SpannableString(content_buffer);
        for (int i = 0; i < tags.size(); i++) {
            String item = tags.get(i);
            View view = LayoutInflater.from(mContext).inflate(mLayoutResource, null);//R.layout.tag是每个标签的布局
            tv_tag = view.findViewById(flagID);
            tv_tag.setText(item);
            Bitmap bitmap = convertViewToBitmap(view);
            Drawable d = new BitmapDrawable(getResources(), bitmap);
            d.setBounds(0, 0, tv_tag.getWidth(), tv_tag.getHeight());//缺少这句的话，不会报错，但是图片不回显示
            ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);//图片将对齐底部边线
            int startIndex;
            int endIndex;
            startIndex = getLastLength(tags, i);
            endIndex = startIndex + item.length();
            spannableString.setSpan(span, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        setGravity(Gravity.TOP);

        setText(spannableString);
    }

    private int getLastLength(List<String> list, int maxLength) {
        int length = 0;
        for (int i = 0; i < maxLength; i++) {
            length += list.get(i).length();
        }
        return length;
    }
}