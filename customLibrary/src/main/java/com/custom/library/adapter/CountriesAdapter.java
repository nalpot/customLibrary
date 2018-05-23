package com.custom.library.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.custom.library.R;
import com.custom.library.helper.UtilsHelper;
import com.custom.library.model.CountriesModel;

import java.util.List;

/**
 * Created by Abderrahim El imame on 11/03/2016.
 * Email : abderrahim.elimame@gmail.com
 */
public class CountriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity mActivity;
    private List<CountriesModel> mCountriesModel;

    private LayoutInflater mInflater;
    private String SearchQuery;
    private boolean forCountry;


    public CountriesAdapter(Activity mActivity, boolean forCountry) {
        this.mActivity = mActivity;
        this.forCountry = forCountry;
        mInflater = LayoutInflater.from(mActivity);
    }

    public List<CountriesModel> getCountries() {
        return mCountriesModel;
    }

    public void setCountries(List<CountriesModel> mCountriesModel) {
        this.mCountriesModel = mCountriesModel;
        notifyDataSetChanged();
    }

    //Methods for search start
    public void setString(String SearchQuery) {
        this.SearchQuery = SearchQuery;
        notifyDataSetChanged();
    }

    public void animateTo(List<CountriesModel> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<CountriesModel> newModels) {
        int arraySize = mCountriesModel.size();
        for (int i = arraySize - 1; i >= 0; i--) {
            final CountriesModel model = mCountriesModel.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<CountriesModel> newModels) {
        int arraySize = newModels.size();
        for (int i = 0, count = arraySize; i < count; i++) {
            final CountriesModel model = newModels.get(i);
            if (!mCountriesModel.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<CountriesModel> newModels) {
        int arraySize = newModels.size();
        for (int toPosition = arraySize - 1; toPosition >= 0; toPosition--) {
            final CountriesModel model = newModels.get(toPosition);
            final int fromPosition = mCountriesModel.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    private CountriesModel removeItem(int position) {
        final CountriesModel model = mCountriesModel.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    private void addItem(int position, CountriesModel model) {
        mCountriesModel.add(position, model);
        notifyItemInserted(position);
    }

    private void moveItem(int fromPosition, int toPosition) {
        final CountriesModel model = mCountriesModel.remove(fromPosition);
        mCountriesModel.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }
    //Methods for search end

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (forCountry)
            view = mInflater.inflate(R.layout.row_countries, parent, false);
        else
            view = mInflater.inflate(R.layout.row_countries_phones, parent, false);
        return new CountriesViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final CountriesViewHolder countriesViewHolder = (CountriesViewHolder) holder;
        final CountriesModel countriesModel = this.mCountriesModel.get(position);

        countriesViewHolder.setCountryName(countriesModel.getName());
        if (!forCountry)
            countriesViewHolder.setCountryCode(countriesModel.getDial_code());
        SpannableString countryName = SpannableString.valueOf(countriesModel.getName());
        if (SearchQuery == null) {
            countriesViewHolder.countryName.setText(countryName, TextView.BufferType.NORMAL);
        } else {
            int index = TextUtils.indexOf(countriesModel.getName().toLowerCase(), SearchQuery.toLowerCase());
            if (index >= 0) {
                countryName.setSpan(new ForegroundColorSpan(UtilsHelper.getColor(mActivity, R.color.colorSpanSearch)), index, index + SearchQuery.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                countryName.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), index, index + SearchQuery.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }

            countriesViewHolder.countryName.setText(countryName, TextView.BufferType.SPANNABLE);
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        if (mCountriesModel != null) {
            return mCountriesModel.size();
        } else {
            return 0;
        }
    }


    public class CountriesViewHolder extends RecyclerView.ViewHolder {
        TextView countryName;
        TextView countryCode;

        CountriesViewHolder(View itemView) {
            super(itemView);
            countryName = (TextView) itemView.findViewById(R.id.country_name);
            countryCode = (TextView) itemView.findViewById(R.id.country_code);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (forCountry) {
                            CountriesModel countriesModel = mCountriesModel.get(CountriesViewHolder.this.getAdapterPosition());
                            Intent mIntent = new Intent();
                            mIntent.putExtra("countryName", countriesModel.getName());
                            mActivity.setResult(Activity.RESULT_OK, mIntent);
                            mActivity.finish();
                        } else {
                            CountriesModel countriesModel = mCountriesModel.get(CountriesViewHolder.this.getAdapterPosition());
                            Intent mIntent = new Intent();
                            mIntent.putExtra("countryIso", countriesModel.getCode());
                            mActivity.setResult(Activity.RESULT_OK, mIntent);
                            mActivity.finish();
                        }
                    } catch (Exception e) {
                        UtilsHelper.LogCat("Exception CountriesAdapter " + e.getMessage());
                    }
                }
            });
        }

        void setCountryName(String countryN) {
            countryName.setText(countryN);
        }

        void setCountryCode(String countryC) {
            countryCode.setText(countryC);
        }

    }


}

