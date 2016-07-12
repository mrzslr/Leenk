package com.salari.mohammadreza.mobile.leenk.Views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by MohammadReza on 31/01/2016.
 */
public class MyTextView extends TextView {

    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public MyTextView(Context context) {
        super(context);
        init();
    }


    public void init() {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/ubuntu_reg.ttf");
        setTypeface(tf);

    }
}