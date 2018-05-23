package com.custom.library.ui.slider;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.custom.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nalpot on 23/05/2018.
 * Email: rifqim035@gmail.com
 */

public class SlideViews extends FrameLayout {
    private SliderAdapter mAdapter;
    private SliderIndicator mIndicator;
    private ViewPagers viewPagers;
    private LinearLayout mLinearLayout;
    private int duration = 3000;
    private int indicatorView = R.drawable.indicator_circle;
    private OnScrollCallback callback;
    private OnClickItemListener onItemClickListener;

    public SlideViews(@NonNull Context context) {
        this(context, null);
    }

    public SlideViews(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideViews(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.layout_slider, this);
        viewPagers = (ViewPagers) findViewById(R.id.sliderView);
        mLinearLayout = (LinearLayout) findViewById(R.id.pagesContainer);
    }

    public void setUp(List<String> urlImage, @Nullable String args) {
        List<Fragment> fragments = new ArrayList<>();
        for (String url : urlImage) {
            fragments.add(FragmentSlider.newInstance(url));
        }

        setToViewPager(fragments);
    }

    public void setUp(List<Integer> urlImage) {
        List<Fragment> fragments = new ArrayList<>();
        for (Integer url : urlImage) {
            fragments.add(FragmentSlider.newInstance(url));
        }

        setToViewPager(fragments);
    }

    private void setToViewPager(List<Fragment> fragments) {
        viewPagers.setDurationScroll(800);

        viewPagers.setClipToPadding(false);
        viewPagers.setOffscreenPageLimit(3);
        viewPagers.setPadding(0, 0, 20, 0);
        viewPagers.setPageMargin(16);
        mAdapter = new SliderAdapter(((FragmentActivity) getContext()).getSupportFragmentManager(), fragments);
        viewPagers.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ViewPagers.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (onItemClickListener != null)
                    onItemClickListener.OnClick(position);
            }
        });
        mIndicator = new SliderIndicator(getContext(), mLinearLayout, viewPagers, indicatorView);
        mIndicator.setPageCount(fragments.size());
        mIndicator.setCallback(callback);
        mIndicator.setDuration(duration);
        mIndicator.show();
    }

    public void addOnScrollListener(OnScrollCallback callback) {
        this.callback = callback;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mIndicator.cleanup();
    }

    public void setDutation(int duration) {
        this.duration = duration;
    }

    public void setIndicatorView(@DrawableRes int indicatorView) {
        this.indicatorView = indicatorView;
    }

    public void setOnItemClickListener(OnClickItemListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnClickItemListener {
        void OnClick(int position);
    }

    public interface OnScrollCallback {
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        void onPageSelected(int position);

        void onPageScrollStateChanged(int state);
    }
}
