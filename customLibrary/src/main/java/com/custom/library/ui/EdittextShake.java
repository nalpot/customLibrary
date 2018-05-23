package com.custom.library.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.custom.library.helper.UtilsHelper;

/**
 * Created by Nalpot on 05/05/2018.
 * Email: rifqim035@gmail.com
 */

public class EdittextShake extends EditText {
    public EdittextShake(Context context) {
        super(context);
    }

    public EdittextShake(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EdittextShake(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setError(CharSequence error) {
        super.setError(error);
        UtilsHelper.showErrorEditText(getContext(), this);
    }
}
