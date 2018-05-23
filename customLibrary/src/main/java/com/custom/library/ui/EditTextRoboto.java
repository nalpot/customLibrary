package com.custom.library.ui;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.custom.library.helper.UtilsHelper;

/**
 * Created by Nalpot on 18/05/2018.
 * Email: rifqim035@gmail.com
 */

public class EditTextRoboto extends AppCompatEditText {
    public EditTextRoboto(Context context) {
        this(context, null);
    }

    public EditTextRoboto(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public EditTextRoboto(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setTypeface(UtilsHelper.setTypeFace(getContext(),"Roboto-Regular.ttf"));
    }
}
