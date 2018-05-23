package com.custom.library.ui.slider;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.custom.library.R;

/**
 * Created by Nalpot on 22/05/2018.
 * Email: rifqim035@gmail.com
 */

public class FragmentSlider extends Fragment {
    private static final String ARG_PARAM1 = "params";
    private static final String ARG_PARAM2 = "paramsRes";

    private String imageUrls;
    private int imageRes;
    private OnClick click;
    public FragmentSlider() {
    }

    public static FragmentSlider newInstance(String params) {
        FragmentSlider fragment = new FragmentSlider();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, params);
        fragment.setArguments(args);
        return fragment;
    }

    public static FragmentSlider newInstance(int res) {
        FragmentSlider fragment = new FragmentSlider();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM2, res);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getArguments().getString(ARG_PARAM1) != null)
            imageUrls = getArguments().getString(ARG_PARAM1);
        else if (getArguments().getInt(ARG_PARAM2) != 0)
            imageRes = getArguments().getInt(ARG_PARAM2);
        View view = inflater.inflate(R.layout.item_slider, container, false);
        ImageView img = (ImageView) view.findViewById(R.id.image);
        Glide.with(getActivity()).load(imageUrls != null ? imageUrls : imageRes).asBitmap().thumbnail(0.1f).into(img);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click != null)
                    click.onClick(v);
            }
        });
        return view;
    }

    public void setOnClickListener(OnClick onClick) {
        this.click = onClick;
    }

    public interface OnClick {
        void onClick(View view);
    }
}
