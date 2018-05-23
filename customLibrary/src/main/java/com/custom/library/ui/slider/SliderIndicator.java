package com.custom.library.ui.slider;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.custom.library.helper.UtilsHelper;

/**
 * Created by Nalpot on 22/05/2018.
 * Email: rifqim035@gmail.com
 */

public class SliderIndicator implements ViewPager.OnPageChangeListener {
    private Context mContext;
    private LinearLayout mContainer;
    private int mDrawable;
    private int mSpacing;
    private int mSize;
    private ViewPager mViewPager;
    private int mPageCount;
    private int mInitialPage = 0;

    private int defaultSizeInDp = 12;
    private int defaultSpacingInDp = 12;
    private int duration = 3000;
    private Handler handler = new Handler();
    private Runnable runnable;
    private SlideViews.OnScrollCallback callback;

    public SliderIndicator(@NonNull Context context,
                           @NonNull LinearLayout containerView,
                           @NonNull ViewPager viewPager,
                           @DrawableRes int drawableRes) {
        if (context == null) {
            throw new IllegalArgumentException("context cannot be null");
        } else if (containerView == null) {
            throw new IllegalArgumentException("containerView cannot be null");
        } else if (viewPager == null) {
            throw new IllegalArgumentException("ViewPager cannot be null");
        } else if (viewPager.getAdapter() == null) {
            throw new IllegalArgumentException("ViewPager does not have an adapter set on it.");
        }
        mContext = context;
        mContainer = containerView;
        mDrawable = drawableRes;
        mViewPager = viewPager;

    }

    public void setPageCount(int pageCount) {
        mPageCount = pageCount;
    }

    public void setInitialPage(int page) {
        mInitialPage = page;
    }

    public void setDrawable(@DrawableRes int drawable) {
        mDrawable = drawable;
    }

    public void setSpacingRes(@DimenRes int spacingRes) {
        mSpacing = spacingRes;
    }

    public void setSize(@DimenRes int dimenRes) {
        mSize = dimenRes;
    }

    public void show() {
        initIndicators();
        setIndicatorAsSelected(mInitialPage);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewPager.setCurrentItem(1);
            }
        }, duration);
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    private void initIndicators() {
        if (mContainer == null || mPageCount <= 0) {
            return;
        }

        mViewPager.addOnPageChangeListener(this);
        Resources res = mContext.getResources();
        mContainer.removeAllViews();
        for (int i = 0; i < mPageCount; i++) {
            View view = new View(mContext);
            int dimen = mSize != 0 ? res.getDimensionPixelSize(mSize) : ((int) res.getDisplayMetrics().density * defaultSizeInDp);
            int margin = mSpacing != 0 ? res.getDimensionPixelSize(mSpacing) : ((int) res.getDisplayMetrics().density * defaultSpacingInDp);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dimen, dimen);
            lp.setMargins(i == 0 ? 0 : margin, 0, 0, 0);
            view.setLayoutParams(lp);
            view.setBackgroundResource(mDrawable);
            view.setSelected(i == 0);
            mContainer.addView(view);
        }
    }

    private void setIndicatorAsSelected(int index) {
        if (mContainer == null) {
            return;
        }
        for (int i = 0; i < mContainer.getChildCount(); i++) {
            View view = mContainer.getChildAt(i);
            view.setSelected(i == index);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (callback != null)
            callback.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        if (callback != null)
            callback.onPageSelected(position);

        int index = position % mPageCount;
        UtilsHelper.LogCatAsset("Page ", index + "");
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }
        setIndicatorAsSelected(index);
        try {
            final int moveTo = position + 1;
            runnable = new Runnable() {
                @Override
                public void run() {
                    mViewPager.setCurrentItem(moveTo);
                }
            };
            handler.postDelayed(runnable, duration);
        } catch (Exception e) {
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (callback != null)
            callback.onPageScrollStateChanged(state);
    }

    public void cleanup() {
        mViewPager.clearOnPageChangeListeners();
    }

    public void setCallback(SlideViews.OnScrollCallback callback) {
        this.callback = callback;
    }
}
