package com.custom.library.ui.slider;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Created by Nalpot on 22/05/2018.
 * Email: rifqim035@gmail.com
 */

public class ViewPagers extends ViewPager {

    public static final int DEFAULT_SCROLL_DURATION = 200;
    public static final int SLIDE_MODE_SCROLL_DURATION = 1000;

    public ViewPagers(Context context) {
        super(context);
        init();
    }

    public ViewPagers(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setDurationScroll(DEFAULT_SCROLL_DURATION);
    }


    public void setDurationScroll(int millis) {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(this, new OwnScroller(getContext(), millis));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class OwnScroller extends Scroller {

        private int durationScrollMillis = 1;

        public OwnScroller(Context context, int durationScroll) {
            super(context, new DecelerateInterpolator());
            this.durationScrollMillis = durationScroll;
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, durationScrollMillis);
        }
    }
}
