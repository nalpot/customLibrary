package com.custom.library.ui.slider;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nalpot on 22/05/2018.
 * Email: rifqim035@gmail.com
 */

public class SliderAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = "SliderPagerAdapter";

    List<Fragment> mFrags = new ArrayList<>();
    FragmentSlider fragment;
    private ViewPagers.OnItemClickListener mOnItemClickListener;

    public SliderAdapter(FragmentManager fm, List<Fragment> frags) {
        super(fm);
        mFrags = frags;
    }

    @Override
    public float getPageWidth(int position) {
        return 0.95f;
    }

    @Override
    public Fragment getItem(int position) {
        final int index = position % mFrags.size();
        if (mFrags.get(index).getArguments().getString("params") != null)
            fragment = FragmentSlider.newInstance(mFrags.get(index).getArguments().getString("params"));
        else
            fragment = FragmentSlider.newInstance(mFrags.get(index).getArguments().getInt("paramsRes"));
        fragment.setOnClickListener(new FragmentSlider.OnClick() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(index);
            }
        });
        return fragment;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    public void setOnItemClickListener(ViewPagers.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
