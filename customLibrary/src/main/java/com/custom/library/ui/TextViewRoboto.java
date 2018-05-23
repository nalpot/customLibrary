package com.custom.library.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.custom.library.R;
import com.custom.library.helper.UtilsHelper;

/**
 * Created by Nalpot on 19/03/2018.
 * Email: rifqim035@gmail.com
 */

public class TextViewRoboto extends AppCompatTextView {
    private String typeFace;

    public TextViewRoboto(Context context) {
        this(context, null);
    }

    public TextViewRoboto(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextViewRoboto(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray styledAttrs = getContext().obtainStyledAttributes(attrs, R.styleable.TextViewRoboto);
        int fontName = styledAttrs.getInt(R.styleable.TextViewRoboto_typeFace, 0);
        typeFace = typeFaces.getName(fontName);

        if (typeFace == null) typeFace = "Roboto-Medium.ttf";

        setTypeface(UtilsHelper.setTypeFace(getContext(), typeFace));
        styledAttrs.recycle();
    }

    public void setCustomTypeFace(typeFaces typeFacea) {
        typeFace = typeFaces.getName(typeFacea.id);
        setTypeface(UtilsHelper.setTypeFace(getContext(), typeFace));
    }

    public enum typeFaces {
        Roboto_Black(1),
        Roboto_BlackItalic(2),
        Roboto_Bold(3),
        Roboto_BoldItalic(4),
        Roboto_Italic(5),
        Roboto_Light(6),
        Roboto_LightItalic(7),
        Roboto_Medium(8),
        Roboto_MediumItalic(9),
        Roboto_Regular(10),
        Roboto_Thin(11),
        Roboto_ThinItalic(12),
        RobotoCondensed_Bold(13),
        RobotoCondensed_BoldItalic(14),
        RobotoCondensed_Italic(15),
        RobotoCondensed_Light(16),
        RobotoCondensed_LightItalic(17),
        RobotoCondensed_Regular(18);

        private int id;

        typeFaces(int id) {
            this.id = id;
        }

        public static String getName(int id) {
            for (typeFaces c : values()) {
                if (c.id == id) {
                    return c.name().replace("_", "-").concat(".ttf");
                }
            }
            return null;
        }
    }
}
