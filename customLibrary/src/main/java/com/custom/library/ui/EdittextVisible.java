package com.custom.library.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.custom.library.R;
import com.custom.library.helper.UtilsHelper;

/**
 * Created by Nalpot on 12/04/2018.
 * Email: rifqim035@gmail.com
 */

public class EdittextVisible extends FrameLayout {
    private EditText editText;
    private RelativeLayout layoutRel;
    private CheckBox visiblePass;

    public EdittextVisible(Context context) {
        this(context, null);
    }

    public EdittextVisible(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EdittextVisible(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initAttr(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.edittext_toggle, this);
        editText = (EditText) findViewById(R.id.edittext_text);
        layoutRel = (RelativeLayout) findViewById(R.id.layout);
        visiblePass = (CheckBox) findViewById(R.id.edittext_visiblePass);
        visiblePass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onCheckedChangeds(buttonView, isChecked);
            }
        });
    }

    private void initAttr(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.EdittextVisible, defStyleAttr, 0);
        float fontSize = styledAttrs.getDimensionPixelSize(R.styleable.EdittextVisible_EVtextSize, 0);

        Drawable background;
        background = styledAttrs.getDrawable(R.styleable.EdittextVisible_EVbackground);
        if (background != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                layoutRel.setBackground(background);
            } else {
                layoutRel.setBackgroundDrawable(background);
            }
        }
        int hintColor = styledAttrs.getColor(R.styleable.EdittextVisible_EVhintColor, 0);
        if (hintColor != 0) {
            editText.setHintTextColor(hintColor);
        }
        int textColor = styledAttrs.getColor(R.styleable.EdittextVisible_EVtextColor, 0);
        if (textColor != 0) {
            editText.setTextColor(textColor);
        }
        String text = styledAttrs.getString(R.styleable.EdittextVisible_EVhint);
        editText.setHint(text);
        if (fontSize != 0) {
            editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, Double.valueOf(fontSize).intValue());
        }
        styledAttrs.recycle();
    }

    public void onCheckedChangeds(CompoundButton buttonView, boolean isChecked) {
        int start, end;
        if (!isChecked) {
            buttonView.setButtonDrawable(R.drawable.ic_visibility_blue);
            start = editText.getSelectionStart();
            end = editText.getSelectionEnd();
            editText.setTransformationMethod(new PasswordTransformationMethod());
            editText.setSelection(start, end);
        } else {
            buttonView.setButtonDrawable(R.drawable.ic_visibility);
            start = editText.getSelectionStart();
            end = editText.getSelectionEnd();
            editText.setTransformationMethod(null);
            editText.setSelection(start, end);
        }
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
