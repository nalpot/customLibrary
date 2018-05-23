package com.custom.library.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.custom.library.R;
import com.custom.library.activity.SelectPhoneActivity;
import com.custom.library.app.UtilsConstant;
import com.custom.library.helper.UtilsHelper;

/**
 * Created by Nalpot on 05/05/2018.
 * Email: rifqim035@gmail.com
 */

public class EdittextSelectPhoneCountry extends FrameLayout {
    int backgroundActivity;
    private EditText editText;
    private RelativeLayout layoutRel;

    public EdittextSelectPhoneCountry(Context context) {
        this(context, null);
    }

    public EdittextSelectPhoneCountry(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EdittextSelectPhoneCountry(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initAttr(context, attrs, defStyleAttr);
    }

    private void initAttr(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.EdittextSelectPhoneCountry, defStyleAttr, 0);
        float fontSize = styledAttrs.getDimensionPixelSize(R.styleable.EdittextSelectPhoneCountry_ESPtextSize, 0);
        Drawable background = styledAttrs.getDrawable(R.styleable.EdittextSelectPhoneCountry_ESPbackground);
        backgroundActivity = styledAttrs.getResourceId(R.styleable.EdittextSelectPhoneCountry_ESPbackgroundActivity, 0);
        int hintColor = styledAttrs.getColor(R.styleable.EdittextSelectPhoneCountry_ESPhintColor, 0);
        int textColor = styledAttrs.getColor(R.styleable.EdittextSelectPhoneCountry_ESPtextColor, 0);
        String textHint = styledAttrs.getString(R.styleable.EdittextSelectPhoneCountry_ESPhint);
        String text = styledAttrs.getString(R.styleable.EdittextSelectPhoneCountry_ESPtext);

        editText.setHint(textHint);
        editText.setText(text);
        if (textColor != 0) editText.setTextColor(textColor);
        if (hintColor != 0) editText.setHintTextColor(hintColor);
        if (background != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                layoutRel.setBackground(background);
            } else {
                layoutRel.setBackgroundDrawable(background);
            }
        }
        if (fontSize != 0) {
            editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, Double.valueOf(fontSize).intValue());
        }
        styledAttrs.recycle();
    }

    private void init(Context context) {
        View.inflate(context, R.layout.edittext_phone, this);
        editText = (EditText) findViewById(R.id.phone);
        layoutRel = (RelativeLayout) findViewById(R.id.layout);
        editText.setFocusable(false);
        editText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getContext(), SelectPhoneActivity.class);
                if (backgroundActivity != 0) {
                    mIntent.putExtra("background", getContext().getResources().getResourceName(backgroundActivity));
                }
                ((FragmentActivity) getContext()).startActivityForResult(mIntent, UtilsConstant.SELECT_PHONE_NUMBER);
            }
        });
    }

    public Editable getText() {
        return editText.getText();
    }

    public void setText(CharSequence text) {
        editText.setText(text, TextView.BufferType.EDITABLE);
    }

    public void setError(String error) {
        editText.setError(error);
    }

    public void setTypeface(Typeface typeface) {
        editText.setTypeface(typeface);
    }
}
